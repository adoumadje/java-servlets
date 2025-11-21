package com.adoumadje;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(urlPatterns = "/app")
public class ExtraAppFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        resp.setContentType("text/html");
        PrintWriter out  = resp.getWriter();
        out.println("<br>Extra Filter Before<br>");
        chain.doFilter(req, resp);
        out.println("<br>Extra Filter After<br>");
    }
}
