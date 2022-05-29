<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 29.05.22
  Time: 20:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<c:if test="${sessionScope.get('locale') == null}">
    <fmt:setBundle basename="base" var="lang"/>
</c:if>
<c:if test="${sessionScope.get('locale').equals('en')}">
    <fmt:setBundle basename="EN-en" var="lang"/>
</c:if>
<c:if test="${sessionScope.get('locale').equals('ru')}">
    <fmt:setBundle basename="RU-ru" var="lang"/>
</c:if>
<header>
    <nav class="navbar navbar-dark bg-dark" style="height: 55px">
        <div class="container-xxl align-items-start">
            <ul class="nav">
                <li class="my-1"><a class="navbar-brand text-white"
                                    href="${pageContext.request.contextPath}/ManagerReportServlet">RepairAgent</a></li>
            </ul>
            <ul class="nav">
                <li class="nav-item mx-3 my-2"><b class="text-white">${requestScope.totalSum} - total sum</b></li>
                <li class="mx-2">
                    <div class="dropdown">
                        <a class="btn btn-outline-warning dropdown-toggle" href="#" role="button" id="dropdownMenuLink"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            <fmt:message key="customer.header.button.len" bundle="${lang}"/>
                        </a>

                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <li><a class="dropdown-item" href="ManagerReportServlet?locale=en">En</a></li>
                            <li><a class="dropdown-item" href="ManagerReportServlet?locale=ru">Ru</a></li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item">
                    <form action="GeneralCustomerServlet" method="post">
                        <input name="logout" type="hidden" value="" class="btn btn-outline-warning">
                        <input value="<fmt:message key = "customer.header.button.log_out" bundle = "${lang}"/>"
                               type="submit" class="btn btn-outline-warning">
                    </form>
                </li>
            </ul>
        </div>
    </nav>
</header>
<body>
<section>
    <div class="container-xl">
        <div class="row">
            <div class="col-12 align-items-center">
                sdfsdfsdflksdfkladf
            </div>
        </div>
    </div>
</section>
</body>
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
</html>
