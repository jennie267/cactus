package com.cactus.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MyAuthFilter extends OncePerRequestFilter {

@Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response, FilterChain filterChain) throws
        ServletException, IOException {

    System.out.println("여기봐");
    System.out.println(request.getRequestURI());
    System.out.println(request.getContextPath());

if((request.getHeader("AUTH-OWNER") != null &&
request.getHeader("AUTH-OWNER").equals("CACTUS"))
        || request.getRequestURI().contains("/product/img/")
        || request.getRequestURI().contains("/favicon.ico")
        || request.getRequestURI().contains("swagger")	// 추후 주석
        || request.getRequestURI().contains("/v2")	// 추후 주석
        ){
System.out.println("!!!!! enter "); filterChain.doFilter(request, response);
}else{ throw new SecurityException(); }

}

}

