<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 25.04.22
  Time: 03:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
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
                                    href="${pageContext.request.contextPath}/ManagerServlet">RepairAgency</a></li>
            </ul>
            <ul class="nav">
                <c:if test="${requestScope.table == null}">
                    <li class="nav-item mx-3 my-2"><b class="text-white">${requestScope.totalSum} - total sum</b></li>
                </c:if>
                <li class="mx-2">
                    <div class="dropdown">
                        <a class="btn btn-outline-warning dropdown-toggle" href="#" role="button" id="dropdownMenuLink"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            <fmt:message key="customer.header.button.len" bundle="${lang}"/>
                        </a>

                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ManagerReportServlet?locale=en">En</a></li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ManagerReportServlet?locale=ru">Ru</a></li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/ManagerReportServlet" method="post">
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
<section class="my-5">
    <div class="container-fluid align-items-top">
        <div class="row">
            <c:if test="${!requestScope.result.equals('null')}">
                <b class="text-center text-danger ali">${requestScope.result}</b>
            </c:if>
            <div class="col-1">
                <form action="ManagerReportServlet" method="get">
                    <div class="btn-group-vertical my-2 mx-2" role="group" aria-label="Basic radio toggle button group">
                        <input onchange="checkBtn()" type="radio" class="btn-check" name="repo" value="master" id="masterReport">
                        <label class="btn btn-outline-warning my-2" for="masterReport">Master</label>
                        <input onchange="checkBtn()" type="radio" class="btn-check" id="customerReport" >
                        <label class="btn btn-outline-warning" for="customerReport">Customer</label>
                        <script>
                            function checkBtn(){
                                if (document.getElementById("masterReport").checked
                                    || document.getElementById("customerReport").checked){
                                    document.getElementById("reportBtn").click();
                                }
                            }
                        </script>
                    </div>
                    <input hidden type="submit" id="reportBtn">
                </form>
            </div>
            <div class="col-11">
                <c:if test="${requestScope.get('table') != null}">
                    <table class="table table-hover">
                        <thead>
                        <tr class="border-dark">
                            <th><fmt:message key = "manager.report.table.user_login" bundle = "${lang}"/></th>
                            <th><fmt:message key = "manager.report.table.done_orders" bundle = "${lang}"/></th>
                            <th><fmt:message key = "manager.report.table.orders" bundle = "${lang}"/></th>
                            <th><fmt:message key = "manager.report.table.bank" bundle = "${lang}"/></th>
                            <th><fmt:message key = "manager.report.table.statement" bundle = "${lang}"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="request" items="${requestScope.table}">
                            <tr>
                                <td><c:out value="${request.login}"/></td>
                                <td><c:out value="${request.amountOfOrdersDone}"/></td>
                                <td><c:out value="${request.amountOfOrders}"/></td>
                                <td><c:out value="${request.bank}"/></td>
                                <c:if test="${request.role.equals('CUSTOMER')}">
                                    <td>unlock</td>
                                </c:if>
                                <c:if test="${request.role.equals('BLOCK')}">
                                    <td>block</td>
                                </c:if>
                                <td>
                                    <form action="${pageContext.request.contextPath}/ManagerReportServlet" method="post">
                                    <input onclick="checkBtn()" class="btn btn-outline-danger" name="block" id="block" value="Block" type="submit"/>
                                        <input  class="btn btn-outline-success"  name="block" value="Unblock" type="submit"/>
                                        <input type="hidden" name="login" value="${request.login}">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${requestScope.get('table') == null}">
                    <table class="table table-hover">
                        <thead>
                        <tr class="border-dark">
                            <th><fmt:message key = "manager.report.master_table.master_login" bundle = "${lang}"/></th>
                            <th><fmt:message key = "manager.report.master_table.take_order" bundle = "${lang}"/></th>
                            <th><fmt:message key = "manager.report.master_table.done_order" bundle = "${lang}"/></th>
                            <th><fmt:message key = "manager.report.master_table.orders" bundle = "${lang}"/></th>
                            <th><fmt:message key = "manager.report.master_table.earnings" bundle = "${lang}"/></th>
                            <th><fmt:message key = "manager.report.master_table.rating" bundle = "${lang}"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="request" items="${requestScope.report}">
                            <tr>
                                <td><c:out value="${request.masterLogin}"/></td>
                                <td><c:out value="${request.takeOrders}"/></td>
                                <td><a href="${pageContext.request.contextPath}/ManagerReportServlet?requests=${request.masterLogin}"><c:out value="${request.doneOrders}"/></a></td>
                                <td><c:out value="${request.amountOfOrders}"/></td>
                                <td><c:out value="${request.earnings}"/></td>
                                <td><a href="${pageContext.request.contextPath}/ManagerReportServlet?rating=${request.masterLogin}"><c:out value="${request.rate}"/></a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
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
