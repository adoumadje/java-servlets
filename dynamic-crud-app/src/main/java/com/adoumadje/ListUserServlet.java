package com.adoumadje;

import com.adoumadje.interfaces.ResultSetExtractor;
import com.adoumadje.mapper.UserResultSetExtractor;
import com.adoumadje.model.User;
import com.adoumadje.utils.Constants;
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
import java.util.List;

@WebServlet(urlPatterns = "/list-users")
public class ListUserServlet extends HttpServlet {
    private Connection connection;

    public void init() {
        try {
            Class.forName(Constants.MYSQL_DRIVER);
            this.connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASS);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Statement statement = this.connection.createStatement();
            String sql = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetExtractor<User> extractor = this.getResultSetExtractor();
            List<User> users = extractor.extractList(resultSet);
            resultSet.close();
            statement.close();
            StringBuilder rows = new StringBuilder();
            URL rowUrl = this.getClass().getResource("views/row.html");
            Path rowRath = Paths.get(rowUrl.toURI());
            users.forEach(user -> {
                try {
                    String row = String.format(new String(Files.readAllBytes(rowRath)),
                            user.getId(), user.getFirstName(), user.getLastName(),
                            user.getEmail(), user.getPassword(), user.getId(), user.getId());
                    rows.append(row).append('\n');
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            URL viewUrl = this.getClass().getResource("views/list-users.html");
            Path viewPath = Paths.get(viewUrl.toURI());
            String view = String.format(new String(Files.readAllBytes(viewPath)),
                    rows);
            PrintWriter out = resp.getWriter();
            out.println(view);
        } catch (SQLException | URISyntaxException e) {
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

    private ResultSetExtractor<User> getResultSetExtractor() {
        return new UserResultSetExtractor();
    }
}
