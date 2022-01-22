package com.cactus.api.message.CactusMessageApi.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MyAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if(request.getHeader("AUTH-OWNER") != null &&
                request.getHeader("AUTH-OWNER").equals("CACTUS")){
            System.out.println("!!!!! enter ");
            filterChain.doFilter(request, response);
        }else{
            throw new SecurityException();
        }

    }

}
