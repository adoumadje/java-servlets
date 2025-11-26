package com.adoumadje.controller;

import com.adoumadje.service.AverageService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/average-controller")
public class AverageController extends HttpServlet {
    private AverageService averageService;

    @Override
    public void init() throws ServletException {
        this.averageService = new AverageService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int num1 = Integer.valueOf(req.getParameter("num1"));
        int num2 = Integer.valueOf(req.getParameter("num2"));

        int average = this.averageService.calculateAverage(num1, num2);

        req.setAttribute("average", average);

        RequestDispatcher dispatcher = req.getRequestDispatcher("result.jsp");
        dispatcher.forward(req, resp);
    }
}
