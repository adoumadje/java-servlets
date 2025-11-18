package com.adoumadje;

import com.adoumadje.utils.Cryptographer;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/create-user")
public class CreateUserServletPreparedStatement extends HttpServlet {

    private Connection connection;
    private PreparedStatement preparedStatement;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        try {
            Class.forName(servletContext.getInitParameter("mysql-driver"));
            this.connection = DriverManager.getConnection(servletContext.getInitParameter("dbUrl"),
                    servletContext.getInitParameter("dbUser"), servletContext.getInitParameter("dbPass"));
            String sql = "INSERT INTO users (firstname, lastname, email, password) " +
                    "VALUES (?, ?, ?, ?)";
            this.preparedStatement = this.connection.prepareStatement(sql);
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


        try {
            this.preparedStatement.setString(1, firstName);
            this.preparedStatement.setString(2, lastName);
            this.preparedStatement.setString(3, email);
            this.preparedStatement.setString(4, password);
            this.preparedStatement.execute();
            resp.sendRedirect(req.getContextPath() + "/list-users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
        try {
            this.preparedStatement.close();
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
