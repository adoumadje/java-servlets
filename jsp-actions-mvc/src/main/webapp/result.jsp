<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Result</title>
</head>
<body>
<%
    int num1 = Integer.valueOf(request.getParameter("num1"));
    int num2 = Integer.valueOf(request.getParameter("num2"));
    int average = (int) request.getAttribute("average");
%>
    <h2>Average(<%= num1 + ", " + num2 %>) = <%= average %></h2>
</body>
</html>