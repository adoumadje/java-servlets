package com.adoumadje;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/source")
public class SourceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        Cookie[] cookies = req.getCookies();
        System.out.println("In source...");
        for(Cookie cookie: cookies) {
            String s = String.format("{ name: \"%s\", value: \"%s\" }",
                    cookie.getName(), cookie.getValue());
            System.out.println(s);
        }
        HttpSession session = req.getSession();
        session.setAttribute("username", username);
        resp.setContentType("text/html");
        resp.addCookie(new Cookie("token", "b23ceeca-b2af-40e2-ba31-f83adbfa92ec"));
        PrintWriter out = resp.getWriter();
        out.println("<a href=\"target\">Click here to find out the username</a>");
    }
}
