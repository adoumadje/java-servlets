package com.adoumadje;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(urlPatterns = "/app")
public class AppFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("Filter Initialized");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("Before Servlet<br>");
        chain.doFilter(req, resp);
        out.println("<br>After Servlet");
    }

    @Override
    public void destroy() {
        System.out.println("Filter Destroyed");
        Filter.super.destroy();
    }
}
