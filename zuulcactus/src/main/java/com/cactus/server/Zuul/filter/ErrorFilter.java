package com.cactus.server.Zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * Zuul
 * Class: ErrorFilter
 * Created by hapo on 2019-11-21.
 * Description:
 */

public class ErrorFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        Throwable throwable = RequestContext.getCurrentContext().getThrowable();
        System.out.println("Exception was thrown in filters:"+throwable.toString());

        return null;
    }

}
