<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 17.04.22
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet" type="text/css">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>log in</title>
</head>
<body>
<main>
    <div class="row">
        <div class="colm-form">
            <div class="form-container">
                <form action="LoginServlet" method="post">
                    <p>${requestScope.result}</p>
                <input  type="text" placeholder="Login" name="login">
                <input  type="password" placeholder="Password" name="pass">
                <button class="btn-login">Login</button>
                </form>
            </div>
        </div>
    </div>
</main>
</body>
</html>
