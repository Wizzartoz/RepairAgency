<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 20.05.22
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link href="/css/maine_page.css" rel="stylesheet" type="text/css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
    <title>
        repair.ua
    </title>
</head>

<body>
<header>
    <c:if test="${sessionScope.get('locale') == null}">
        <fmt:setBundle basename = "base" var = "lang"/>
    </c:if>
    <c:if test="${sessionScope.get('locale').equals('en')}">
        <fmt:setBundle basename = "EN-en" var = "lang"/>
    </c:if>
    <c:if test="${sessionScope.get('locale').equals('ru')}">
        <fmt:setBundle basename = "RU-ru" var = "lang"/>
    </c:if>
    <nav class="navbar navbar-dark bg-dark">
        <div class="container-xxl">
            <ul class="nav">
                <li><a class="navbar-brand text-white"
                       href="${pageContext.request.contextPath}/index.jsp">RepairAgency</a></li>
            </ul>
            <ul class="nav my-2 mx-1">
                <c:if test="${sessionScope.get('role') != null}">
                    <li><a href="LoginServlet" style="color: #f1f1f1">Hi ${sessionScope.get('login')}!</a></li>
                </c:if>
                <c:if test="${sessionScope.get('role') == null}">
                <li class="nav-item">
                    <!-- Button trigger modal -->
                    <a href="login" class="btn btn-outline-warning mx-2" role="button"> <fmt:message key = "main.header.button.login" bundle = "${lang}"/></a>
                </li>
                <li class="nav-item">
                    <!-- Button trigger modal -->
                    <a href="register" class="btn btn-outline-warning" role="button"> <fmt:message key = "main.header.button.register" bundle = "${lang}"/></a>
                </li>
                </c:if>
                <li class="mx-2">
                    <div class="dropdown">
                        <a class="btn btn-outline-warning dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                            <fmt:message key = "customer.header.button.len" bundle = "${lang}"/>
                        </a>

                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <li><a class="dropdown-item" href="index.jsp?locale=en">En</a></li>
                            <li><a class="dropdown-item" href="index.jsp?locale=ru">Ru</a></li>
                        </ul>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
</header>
<section class="firstsection">
    <div class="container-xxl">
        <div class="box-main">
            <div class="firstHalf">
                <h1 class="text-big" id="web"><fmt:message key = "main.body.title" bundle = "${lang}"/></h1>
                <p class="text-small">
                    <fmt:message key = "main.body.text" bundle = "${lang}"/>
                </p>
            </div>
        </div>
    </div>
</section>
<footer class="bg-dark text-center text-white fixed-bottom">
    <!-- Copyright -->
    <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2);">
        © 2022 Copyright: made by maznichko
    </div>
    <!-- Copyright -->
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous">

</script>
</body>
</html>
