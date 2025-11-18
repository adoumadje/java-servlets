package com.adoumadje;

import com.adoumadje.utils.Cryptographer;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//@WebServlet(urlPatterns = "/create-user",
//initParams = {
//        @WebInitParam(name = "mysql-driver", value = "com.mysql.cj.jdbc.Driver"),
//        @WebInitParam(name = "dbUrl", value = "jdbc:mysql://localhost:3306/servletDb"),
//        @WebInitParam(name = "dbUser", value = "servletUser"),
//        @WebInitParam(name = "dbPass", value = "password")
//})
public class CreateUserServletConfig extends HttpServlet {

    private Connection connection;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            Class.forName(config.getInitParameter("mysql-driver"));
            this.connection = DriverManager.getConnection(config.getInitParameter("dbUrl"),
                    config.getInitParameter("dbUser"), config.getInitParameter("dbPass"));
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            URL url = this.getClass().getResource("views/create-user.html");
            Path path = Paths.get(url.toURI());
            resp.setContentType("text/html");
            String html = new String(Files.readAllBytes(path));
            PrintWriter out = resp.getWriter();
            out.println(html);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = Cryptographer.encode(req.getParameter("password"));

        String sql = "INSERT INTO users (firstname, lastname, email, password) " +
                "VALUES (?, ?, ?, ?)";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.execute();
            preparedStatement.close();
            resp.sendRedirect(req.getContextPath() + "/list-users");
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
