<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 20.05.22
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <nav class="navbar navbar-dark bg-dark">
        <div class="container-xxl">
            <ul class="nav">
                <li><a class="navbar-brand text-white"
                       href="${pageContext.request.contextPath}/index.jsp">RepairAgent</a></li>
            </ul>
            <ul class="nav">
                <c:if test="${sessionScope.get('role') != null}">
                    <li><a href="LoginServlet" style="color: #f1f1f1">Hi ${sessionScope.get('login')}!</a></li>
                </c:if>
                <c:if test="${sessionScope.get('role') == null}">
                <li class="nav-item">
                    <!-- Button trigger modal -->
                    <a href="login" class="btn btn-outline-warning mx-3" role="button">Log in</a>
                </li>
                <li class="nav-item">
                    <!-- Button trigger modal -->
                    <a href="register" class="btn btn-outline-warning" role="button">Register</a>
                </li>
                </c:if>
            </ul>
        </div>
    </nav>
</header>
<section class="firstsection">
    <div class="container-xxl">
        <div class="box-main">
            <div class="firstHalf">
                <h1 class="text-big" id="web">Repair agent</h1>
                <p class="text-small">
                    Welcome to our cute belt agency, where you can leave a request to our
                    master and he will surely fulfill any of your requests.
                </p>
            </div>
        </div>
    </div>
</section>
<footer class="bg-dark text-center text-white fixed-bottom">
    <!-- Copyright -->
    <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2);">
        © 2022 Copyright:
        <b class="text-white">made by maznichko</b>
    </div>
    <!-- Copyright -->
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous">

</script>
</body>
</html>
