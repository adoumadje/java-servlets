package com.adoumadje;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/target")
public class TargetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = session.getAttribute("username").toString();
        String jssid = session.getId();
        System.out.println("In target...");
        Cookie[] cookies = req.getCookies();
        for(Cookie cookie: cookies) {
            String s = String.format("{ name: \"%s\", value: \"%s\" }",
                    cookie.getName(), cookie.getValue());
            System.out.println(s);
        }
        resp.setContentType("text/html");
        PrintWriter out  = resp.getWriter();
        out.println(String.format("The username is: %s and JSESSIONID = %s", username, jssid));
    }
}
