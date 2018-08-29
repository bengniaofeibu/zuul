package com.jiujiu.filter;

import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseFilter {

    public static boolean isFilter() {
        RequestContext cx = RequestContext.getCurrentContext();
        HttpServletRequest request = cx.getRequest();

        String requestURI = request.getRequestURI();

        //访问swagger api文档时不进行校验
        if (StringUtils.isNotBlank(requestURI) && requestURI.endsWith("api-docs")) {
            return false;
        }
        return true;
    }
}
