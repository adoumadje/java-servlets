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
import java.util.Enumeration;

@WebServlet(urlPatterns = "/create-user")
public class CreateUserServletContext extends HttpServlet {

    private Connection connection;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        try {
            Class.forName(servletContext.getInitParameter("mysql-driver"));
            this.connection = DriverManager.getConnection(servletContext.getInitParameter("dbUrl"),
                    servletContext.getInitParameter("dbUser"), servletContext.getInitParameter("dbPass"));
            Enumeration<String> params = servletContext.getInitParameterNames();
            while (params.hasMoreElements()) {
                String pn = params.nextElement();
                System.out.println("{" + pn + ": " + servletContext.getInitParameter(pn) + "}");
            }
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
