<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 18.04.22
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>main-customer</title>
    <link href="${pageContext.request.contextPath}/css/maine_page.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/customer_main.css" rel="stylesheet" type="text/css">
</head>
<body>
<nav class="navbar background">
    <ul class="nav-list">
        <li><h3>Customer</h3></li>
        <li>
            <div class="pill-nav">
                <a href="RequestServlet">Requests</a>
            </div>
        </li>
        <li class="count">${requestScope.bank} - Balance</li>
    </ul>
</nav>
<section class="firstsection">
    <div class="form_text">
        ${requestScope.sendRequest}<br>
        <h1>Enter your request</h1>
        <form class="request" action="GeneralCustomerServlet" method="post">
            <input id="request" type="text" name="user_message">
            <button class="btn-login" type="submit">Send</button>
        </form>
    </div>
    <div class="form_text">
        <br>Здесь ты можешь пополнить свой счёт<br>
        <b>${requestScope.replenishment}</b>
        <form action="GeneralCustomerServlet" method="post">
            <label>money:
                <input type="number" name="money"><br/>
            </label>
            <button class="btn-login" type="submit">replenishment</button>
        </form>
    </div>
</section>
<footer class="background">
    <p class="text-footer">
        Copyright © Made by Maznichko
    </p>
</footer>
</body>
</html>
