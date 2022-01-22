package com.cactus.server.Zuul.filter;

import com.netflix.zuul.ZuulFilter;

/**
 * Zuul
 * Class: PostFilter
 * Created by hapo on 2019-11-21.
 * Description:
 */

public class PostFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "post";
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
        System.out.println("Using Post Filter");

        return null;
    }

}