package com.mywebsite.www;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class PathFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String referer = request.getHeader("referer");
        String method = request.getMethod();
        StringBuffer requestURL = request.getRequestURL();
        System.out.print("referer = " + referer);
        System.out.print(", method = " + method);
        System.out.println(", requestURL = " + requestURL);

        filterChain.doFilter(servletRequest, servletResponse);

    }
}
