package com.adoumadje;

import com.mysql.cj.jdbc.Driver;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private Connection connection;
    private PreparedStatement preparedStatement;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        try {
            Class.forName(servletContext.getInitParameter("mysql-driver"));
            this.connection = DriverManager.getConnection(servletContext.getInitParameter("dbUrl"),
                    servletContext.getInitParameter("dbUser"), servletContext.getInitParameter("dbPass"));
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            this.preparedStatement = this.connection.prepareStatement(sql);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            URL url = this.getClass().getResource("views/login.html");
            Path path = Paths.get(url.toURI());
            String view = new String(Files.readAllBytes(path));
            PrintWriter out = resp.getWriter();
            out.println(view);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            this.preparedStatement.setString(1, email);
            this.preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/home");
                String message = "Welcome home: " + resultSet.getString("username");
                System.out.println("message: " + message);
                req.setAttribute("message", message);
                requestDispatcher.forward(req, resp);
            } else {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
                requestDispatcher.include(req, resp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        try {
            this.preparedStatement.close();
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
