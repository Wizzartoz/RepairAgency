<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 17.04.22
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet" type="text/css">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>register</title>
</head>
<body>
<main>
    <div class="row">
        <div class="colm-form">
            <div class="form-container">
                <form action="RegisterServlet" method="post">
                    <b>${requestScope.result}</b>
                    <input type="text" placeholder="Name" name="name">
                    <input type="text" placeholder="Surname" name="surname">
                    <input type="text" placeholder="Login" name="login">
                    <input type="password" placeholder="Password" name="pass">
                    <input type="text" placeholder="Email" name="email">
                    <input type="text" placeholder="Phone" name="phone">
                    <button class="btn-login">Register</button>
                </form>
            </div>
        </div>
    </div>
</main>
</body>
</html>
