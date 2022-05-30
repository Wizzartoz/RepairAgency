<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 30.05.22
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
</head>
<body>
<c:if test="${sessionScope.get('locale') == null}">
    <fmt:setBundle basename = "base" var = "lang"/>
</c:if>
<c:if test="${sessionScope.get('locale').equals('en')}">
    <fmt:setBundle basename = "EN-en" var = "lang"/>
</c:if>
<c:if test="${sessionScope.get('locale').equals('ru')}">
    <fmt:setBundle basename = "RU-ru" var = "lang"/>
</c:if>
<header>
    <nav class="navbar navbar-dark bg-dark"  style="height: 55px">
        <div class="container-xxl align-items-start">
            <ul class="nav">
                <li class="my-1"><a class="navbar-brand text-white"
                                    href="${pageContext.request.contextPath}/MasterServlet">RepairAgent</a></li>
            </ul>
            <ul class="nav">
                <li class="mx-2">
                    <div class="dropdown">
                        <a class="btn btn-outline-warning dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                            <fmt:message key = "customer.header.button.len" bundle = "${lang}"/>
                        </a>

                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <li><a class="dropdown-item" href="MasterServlet?locale=en">En</a></li>
                            <li><a class="dropdown-item" href="MasterServlet?locale=ru">Ru</a></li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item">
                    <form action="GeneralCustomerServlet" method="post">
                        <input name="logout" type="hidden" value="" class="btn btn-outline-warning">
                        <input value="<fmt:message key = "customer.header.button.log_out" bundle = "${lang}"/>" type="submit" class="btn btn-outline-warning">
                    </form>
                </li>
            </ul>
        </div>
    </nav>
</header>
<section>
    <div class="container-sm">
        <div class="row">
            <div class="col-12">
                <form action="${pageContext.request.contextPath}/GeneralCustomerServlet" method="post">
                    <div class="form-group">
                        <label for="name">Name</label>
                        <input disabled type="text" class="form-control"  id="name" aria-describedby="emailHelp" placeholder="Name">
                    </div>
                    <div class="form-group">
                        <label for="surname">Surname</label>
                        <input disabled type="password" class="form-control" id="surname" placeholder="Surname">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input disabled type="password" name="Password" class="form-control" id="password" placeholder="Password">
                        <a class="btn btn-outline-warning" onclick="checkLogin('password')" role="button">Change</a>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input disabled type="email" name="Email" class="form-control" id="email" placeholder="Email">
                        <a class="btn btn-outline-warning" onclick="checkLogin('email')" role="button">Change</a>
                    </div>
                    <script>
                        function checkLogin(id){
                            document.getElementById(id).disabled = false;
                        }
                    </script>
                    <input hidden name="command" value="editProfile">
                    <div class="form-group">
                        <label for="phone">Phone</label>
                        <input disabled name="Phone" type="number" class="form-control" id="phone" placeholder="Phone">
                        <a class="btn btn-outline-warning" onclick="checkLogin('phone')" role="button">Change</a>
                    </div>

                    <button type="submit" class="btn btn-warning">Change profile</button>
                </form>
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
