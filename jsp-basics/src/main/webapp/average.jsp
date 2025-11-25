<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Average</title>
</head>
<body>
  <%
    int num1 = Integer.valueOf(request.getParameter("num1"));
    int num2 = Integer.valueOf(request.getParameter("num2"));
    int average = (num1 + num2) / 2;
  %>
  <h2><%= num1 + " + " + num2 + " = " + average %></h2>
</body>
</html>