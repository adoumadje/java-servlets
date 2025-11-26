<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error Handler</title>
</head>
<body>
    <P>Sorry an error has occured</P>
    <p><%= exception.getMessage() %></p>
</body>
</html>