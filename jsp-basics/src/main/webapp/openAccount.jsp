<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.sql.*" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>open account</title>
</head>
<body>
<%!
    private Connection connection;
    private PreparedStatement preparedStatement;

    public void jspInit() {
        String url = "jdbc:mysql://localhost:3306/servletDb";
        String user = "servletUser";
        String pwd = "password";
        String sql = "INSERT INTO account (firstname, lastname, email, salary, password)"
            + " VALUES (?, ?, ?, ?, ?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, user, pwd);
            this.preparedStatement = this.connection.prepareStatement(sql);
        } catch(Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void jspDestroy() {
        try {
            this.preparedStatement.close();
            this.connection.close();
        } catch(Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
%>

<%
    String firstname = request.getParameter("firstname");
    String lastname = request.getParameter("lastname");
    String email = request.getParameter("email");
    Integer salary = Integer.valueOf(request.getParameter("salary"));
    String password = request.getParameter("password");
    preparedStatement.setString(1, firstname);
    preparedStatement.setString(2, lastname);
    preparedStatement.setString(3, email);
    preparedStatement.setInt(4, salary);
    preparedStatement.setString(5, password);
    preparedStatement.execute();
%>
    <h2>Account created successfully...</h2>
</body>
</html>