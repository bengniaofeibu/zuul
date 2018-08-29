package com.jiujiu.filter;

import com.jiujiuwisdom.utils.JSONUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class AccessFilter extends ZuulFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessFilter.class);

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

    /**
     *  过滤逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {

        RequestContext cx = RequestContext.getCurrentContext();
        HttpServletRequest request = cx.getRequest();

        LOGGER.info(" ip {} send {} request to {}",request.getRemoteAddr(),request.getMethod(),request.getRequestURI().toString());

        try (BufferedReader reader = request.getReader()) {

            String data;
            StringBuilder sb = new StringBuilder();
            while ((data=reader.readLine()) != null){
               sb.append(data).toString();
            }

            Map map = JSONUtil.parseObject(sb.toString(), Map.class);

            String accessToken = (String) map.get("accessToken");

            if (StringUtils.isBlank(accessToken)){
                cx.setSendZuulResponse(false);
                cx.setResponseStatusCode(401);
                cx.setResponseBody("{\"msg\":\"accessToken为空!\"}");
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
