package com.cactus.server.Zuul.filter;

import com.cactus.server.Zuul.service.AuthService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * Zuul
 * Class: PreFilter
 * Created by hapo on 2019-11-21.
 * Description:
 */

public class PreFilter extends ZuulFilter {

    @Autowired
    AuthService authService;

    public final static String AUTHENTICATION_HEADER_NAME = "Authorization";
    public final static String TOKEN_HEADER_PREFIX = "Bearer ";

    @Override
    public String filterType() {
        return "pre";
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
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        System.out.println(
                "Request Method : " + request.getMethod() + " Request URL : " + request.getRequestURL().toString());

        String requestUri = request.getRequestURI();
        if(requestUri.contains("/user/login") || requestUri.contains("/user/signup")||
                requestUri.contains("/auth/login")||requestUri.contains("/auth/token")||
                requestUri.contains("/message/push")||requestUri.contains("/location/add")||
                requestUri.contains("/user/idcheck")){
            ctx.addZuulRequestHeader("AUTH-OWNER","CACTUS");
            return null;
        }else{
            if(request.getHeader(AUTHENTICATION_HEADER_NAME) == null ||
                    !request.getHeader(AUTHENTICATION_HEADER_NAME).startsWith(TOKEN_HEADER_PREFIX)){

                ctx.setSendZuulResponse(false);
                ctx.setResponseBody("API key empty");
                ctx.getResponse().setHeader("Content-Type", "text/plain;charset=UTF-8");
                ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());

                return null;
            }

            //token test
            ResponseEntity<String> result = authService.authCheck(request.getHeader(AUTHENTICATION_HEADER_NAME));

            if(result.getStatusCodeValue() != 200){
                ctx.setSendZuulResponse(false);
                ctx.setResponseBody("API key not authorized");
                ctx.getResponse().setHeader("Content-Type", "text/plain;charset=UTF-8");
                ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());

                return null;
            }


            ctx.addZuulRequestHeader("AUTH-OWNER","CACTUS");
            ctx.addZuulRequestHeader(AUTHENTICATION_HEADER_NAME,request.getHeader(AUTHENTICATION_HEADER_NAME));

        }

        return null;
    }

}