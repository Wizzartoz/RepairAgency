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
                                    href="${pageContext.request.contextPath}/GeneralCustomerServlet">RepairAgency</a></li>
            </ul>
            <ul class="nav">
                <li class="mx-2">
                    <div class="dropdown">
                        <a class="btn btn-outline-warning dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                            <fmt:message key = "customer.header.button.len" bundle = "${lang}"/>
                        </a>

                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/jsp/Customer/profile.jsp?locale=en">En</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/jsp/Customer/profile.jsp?locale=ru">Ru</a></li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/GeneralCustomerServlet" method="get">
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
                        <label for="password"><fmt:message key = "register.label.password" bundle = "${lang}"/></label>
                        <input oninput="changePassword()" disabled type="password" name="Password" class="form-control" id="password" placeholder="<fmt:message key = "register.label.password" bundle = "${lang}"/>">
                        <a class="btn btn-outline-warning my-1" onclick="checkLogin('password')" role="button"><fmt:message key = "customer.profile.change" bundle = "${lang}"/></a>
                    </div>
                    <div class="form-group">
                        <label for="email"><fmt:message key = "register.label.email" bundle = "${lang}"/></label>
                        <input oninput="changeEmail()" disabled type="email" name="Email" class="form-control" id="email" placeholder="<fmt:message key = "register.label.email" bundle = "${lang}"/>">
                        <a class="btn btn-outline-warning my-1" onclick="checkLogin('email')" role="button"><fmt:message key = "customer.profile.change" bundle = "${lang}"/></a>
                    </div>
                    <script>
                        function checkLogin(id){
                            document.getElementById(id).disabled = false;
                        }
                    </script>
                    <input hidden name="command" value="editProfile">
                    <div class="form-group">
                        <label for="phone"><fmt:message key = "register.label.phone" bundle = "${lang}"/></label>
                        <input oninput="changeNumber()" disabled name="Phone" type="number" class="form-control my-1" id="phone" placeholder="<fmt:message key = "register.label.phone" bundle = "${lang}"/>">
                        <a class="btn btn-outline-warning my-1" onclick="checkLogin('phone')" role="button"><fmt:message key = "customer.profile.change" bundle = "${lang}"/></a>
                    </div>

                    <button type="submit" id="btn" class="btn btn-warning"><fmt:message key = "customer.profile.button.change" bundle = "${lang}"/></button>
                <script>
                    function  changePassword() {
                        let x = document.getElementById("password").value;
                        const reg = new RegExp("[A-Za-z0-9]{"+x.length+"}");
                        if (!reg.test(x)){
                            document.getElementById("password").style.color = "inherit";
                            document.getElementById("password").style.backgroundColor = "rgba(250,233,233,0.51)";
                            document.getElementById("btn").disabled = true;
                        }
                        else if (x.length < 6 || x.length > 12){
                            document.getElementById("password").style.color = "inherit";
                            document.getElementById("password").style.backgroundColor = "rgba(250,233,233,0.51)";
                            document.getElementById("btn").disabled = true;
                        }
                        else{
                            document.getElementById("password").style.color = "inherit";
                            document.getElementById("password").style.backgroundColor = "rgba(207,243,210,0.5)";
                            document.getElementById("btn").disabled = false;
                            return true;
                        }
                    }
                    function changeEmail() {
                        let x = document.getElementById("email").value;
                        const reg = new RegExp("[a-zA-z]{1,15}[-_.]?[A-Za-z\\d]{1,15}@[a-z]{2,5}[.][a-z]{2,4}");
                        if (!reg.test(x)){
                            document.getElementById("email").style.color = "inherit";
                            document.getElementById("email").style.backgroundColor = "rgba(250,233,233,0.51)";
                            document.getElementById("btn").disabled = true;
                        }
                        else{
                            document.getElementById("email").style.color = "inherit";
                            document.getElementById("email").style.backgroundColor = "rgba(207,243,210,0.5)";
                            document.getElementById("btn").disabled = false;
                            return true;
                        }

                    }
                    function changeNumber() {
                        let x = document.getElementById("phone").value;
                        const reg = new RegExp("\\d{10}")
                        if (x.length !== 10) {
                            document.getElementById("phone").style.color = "inherit";
                            document.getElementById("phone").style.backgroundColor = "rgba(250,233,233,0.51)";
                            document.getElementById("btn").disabled = true;
                        } else if (!reg.test(x)) {
                            document.getElementById("phone").style.color = "inherit";
                            document.getElementById("phone").style.backgroundColor = "rgba(250,233,233,0.51)";
                            document.getElementById("btn").disabled = true;
                        } else {
                            document.getElementById("phone").style.color = "inherit";
                            document.getElementById("phone").style.backgroundColor = "rgba(207,243,210,0.5)";
                            document.getElementById("btn").disabled = false;
                            return true;
                        }
                    }
                </script>
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
