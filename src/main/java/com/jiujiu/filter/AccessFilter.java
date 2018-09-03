package com.jiujiu.filter;

import com.alibaba.fastjson.JSONObject;
import com.jiujiu.Head.ApiHead;
import com.jiujiu.Verification.ApiVerification;
import com.jiujiuwisdom.utils.JSONUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static javassist.CtClass.version;

public class AccessFilter extends ZuulFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessFilter.class);

    private static final String wrongParam(){
        return "{\"code\":201,\"msg\":\"参数缺失\"}";
    }

    private static final String wrongCheck(){
        return "{\"code\":202,\"msg\":\"签名错误\"}";
    }

    private static final String wrongTime(){
        return "{\"code\":204,\"msg\":\"时间错误\"}";
    }

    private static final String systemError(){
        return "{\"code\":-1,\"msg\":\"系统内部错误\"}";
    }

    public final static String MD5 = "MD5";

    public final static String RSA = "1";

    public final static String APP_KEY="402880496058fbb7016058fc201e0009";

    public final static String RSA_KEY="502880496058fbb7016068fc201e0019";

    public final static String PRIVATE_Key="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMoSUQGunPuVjsAN" +
            "SoMq6wIpYutgdiZE1fkQzW1bx/K7ehFAeI1CoLO+Ij1bz17mT6//jygZ8D/CvnGW" +
            "deOvxkSBngFdBTmqkY/WrOxf1LoxT/AczYYVp2SRcvDHZ39w/ha+Dfmk5CaRcIhP" +
            "4HYtoUEjHTvQHX1eZj02Ei+XL1VPAgMBAAECgYBNUtD4qc6cXtBvISbgJm4jN58I" +
            "nrLXVgPi+NEDBdnvQlole9wlgddosFr9y8IAeHUJzesD11kdrPGfGYonBD4DjtG2" +
            "GGRj0Lz+fPbMyLABtwHv4wPOho6hmrq5jWZMYrSOpLWFK35r1rthnmMMu+ZZnXuF" +
            "cEydFlDxumvBb/tJEQJBAOiTWHkGDGAn27f4GM29GRimYCNCOuYZuISPrl8mjrac" +
            "DoR9YLGMb3eyBnZaKOsnwyuzEAiUq+jLtiK7o9D0RjsCQQDebHcDNVbYxHt8TUxG" +
            "7owuAD2qvYXBpAF/xFNpqfbIDV9UCwkGlDcuQAZQIvHTZhn9UuhOLe5cAFKnKEi6" +
            "2ff9AkBeuFvI9lil9LW8iDN53zDQZDo1Qe0A96q5elb285od7xLOjM2Lofln0z5k" +
            "hzWxCAGp04SNKheVeRnVrXv7RvEvAkBs1nyCKGaf2b1nPNfXWcFkfR+v3d+Gur81" +
            "CxeZ+95TVB/SmzfGbV53FkNAkWjt/ec3y4r7PoSFpm7Ldu3n6OLtAkEAh+SEu2Es" +
            "hYlGtLXfOFHm1mK5/Zdh0BCrEd11a5eZcW643UDSNQjcamK37XLsfweKfbhH7Jcg" +
            "h9UgFOifE6CXHA==";


    /**
     *   pre、route、post、error，对应AOP里的前加强、前后加强、后加强、异常处理
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     *  过滤器的执行顺序，多个过滤器同时存在时根据这个order来决定先后顺序，越小优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     *  过滤器是否被执行，只有true时才会执行run()里的代码
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return BaseFilter.isFilter();
    }

    private RequestContext badResponse(RequestContext cx,String res){
        cx.setSendZuulResponse(false);
        cx.setResponseBody(res);
        cx.getResponse().setContentType("application/json; charset=utf8");
        return cx;
    }

    /**
     *  过滤逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {

        RequestContext cx = RequestContext.getCurrentContext();
        HttpServletRequest request = cx.getRequest();

        LOGGER.info(" ip {} send {} request to {}", request.getRemoteAddr(), request.getMethod(), request.getRequestURI().toString());

        try {

            String version = request.getHeader("version");
            String certType = request.getHeader("certType");
            String certification = request.getHeader("certification");
            String timestamp = request.getHeader("timestamp");
            String isTest = request.getHeader("isTest");
            String plat = request.getHeader("plat");
            String appVersion = request.getHeader("appVersion");


            if (!StringUtils.isBlank(isTest) && Integer.parseInt(isTest) == 99) {
                return null;
            }

            ApiHead head = new ApiHead();
            head.setVersion(version);
            head.setCertType(certType);
            head.setCertification(certification);
            head.setTimestamp(timestamp);
            head.setIsTest(Integer.parseInt(isTest));
            head.setPlat(plat);
            head.setAppVersion(appVersion);
            JSONObject jsonObject;
            jsonObject = this.valideHeadApi(head);
            if (jsonObject.getInteger("code") == 201) {
                return badResponse(cx, wrongParam());
            } else if (jsonObject.getInteger("code") == 202) {
                return badResponse(cx, wrongCheck());
            } else if (jsonObject.getInteger("code") == 204) {
                return badResponse(cx, wrongTime());
            }

        } catch(Exception e){
            LOGGER.error(e.getMessage());
            return badResponse(cx, systemError());
        }
        return null;
    }


    public static JSONObject valideHeadApi(ApiHead head) throws Exception{
        String valideMsg;
        if(RSA.equalsIgnoreCase(head.getCertType())){
            valideMsg= ApiVerification.VerificationHandle(head,RSA_KEY, PRIVATE_Key);
        }else {
            valideMsg= ApiVerification.VerificationHandle(head,APP_KEY,"0");
        }
        return JSONObject.parseObject(valideMsg);
    }
}
