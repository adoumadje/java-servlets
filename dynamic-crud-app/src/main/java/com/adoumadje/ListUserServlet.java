package com.adoumadje;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/list-users")
public class ListUserServlet extends HttpServlet {
    private Connection connection;

    public void inti() {
        String dbUrl = "jdbc:mysql://localhost:3306/servletDb";
        String dbUser = "servletUser";
        String dbPass = "password";
        try {
            this.connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void destroy() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
