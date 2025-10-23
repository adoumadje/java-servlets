package com.adoumadje.servlets.training;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/add-numbers")
public class AdditionServlet extends GenericServlet {
    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        if(request.getParameter("num1") != null && request.getParameter("num2") != null) {
            Long num1 = Long.parseLong(request.getParameter("num1"));
            Long num2 = Long.parseLong(request.getParameter("num2"));

            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.println("Result  = " + (num1 + num2));
        }
    }
}
