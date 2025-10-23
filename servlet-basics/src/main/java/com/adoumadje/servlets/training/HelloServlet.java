package com.adoumadje.servlets.training;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse)
            throws ServletException, IOException {
        servletResponse.setContentType("text/html");
        PrintWriter out = servletResponse.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>Hello Java Servlet!</h1>");
        out.println("</body>");
        out.println("</html>");
    }
}
