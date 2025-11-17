package com.adoumadje;

import com.adoumadje.interfaces.ResultSetExtractor;
import com.adoumadje.mapper.UserResultSetExtractor;
import com.adoumadje.model.User;
import com.adoumadje.utils.Constants;
import com.adoumadje.utils.Cryptographer;
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

@WebServlet(urlPatterns = "/update-user")
public class UpdateUserServlet extends HttpServlet {
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName(Constants.MYSQL_DRIVER);
            this.connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetExtractor<User> extractor = this.getResulSetExtractor();
            User user = extractor.extract(resultSet);
            URL url = this.getClass().getResource("views/update-user.html");
            Path path = Paths.get(url.toURI());
            String view = String.format(new String(Files.readAllBytes(path)),
                    user.getId(), user.getFirstName(), user.getLastName(),
                    user.getEmail(), user.getPassword());
            PrintWriter out = resp.getWriter();
            out.println(view);
        } catch (SQLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sql = "UPDATE users " +
                "SET firstname = ?, lastname = ?, email = ?, password = ? " +
                "WHERE id = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            Long id = Long.valueOf(req.getParameter("id"));
            String firstName = req.getParameter("firstname");
            String lastName = req.getParameter("lastname");
            String email = req.getParameter("email");
            String password = Cryptographer.encode(req.getParameter("password"));
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setLong(5, id);
            if(preparedStatement.executeUpdate() > 0) {
                resp.sendRedirect(req.getContextPath() + "/list-users");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ResultSetExtractor<User> getResulSetExtractor() {
        return new UserResultSetExtractor();
    }
}
