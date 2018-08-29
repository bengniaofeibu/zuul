package com.jiujiu.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

/*
    获取请求返回值
 */
public class ResponseFilter extends ZuulFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseFilter.class);


    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return BaseFilter.isFilter();
    }

    @Override
    public Object run() throws ZuulException {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String responseBody = RequestContext.getCurrentContext().getResponseBody();

        LOGGER.info("request {}, responseBody {}",request.getRequestURI(),responseBody);

        return null;
    }
}
