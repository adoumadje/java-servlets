<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Display Product</title>
</head>
<body>
    <jsp:useBean id="product" class="com.adoumadje.Product">
        <jsp:setProperty name="product" property="*" />
    </jsp:useBean>

    <h2>Product Details</h2>
    <p>Name: <jsp:getProperty name="product" property="name" /></p>
    <p>Description: <jsp:getProperty name="product" property="description" /></p>
    <p>Price: <jsp:getProperty name="product" property="price" /></p>
</body>
</html>