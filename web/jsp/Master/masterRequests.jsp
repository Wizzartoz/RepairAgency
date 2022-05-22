<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 25.04.22
  Time: 03:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
</head>
<header>
    <nav class="navbar navbar-dark bg-dark"  style="height: 55px">
        <div class="container-xxl align-items-start">
            <ul class="nav">
                <li class="my-1"><a class="navbar-brand text-white"
                       href="${pageContext.request.contextPath}/MasterServlet">RepairAgent</a></li>
            </ul>
            <ul class="nav">
                <li class="nav-item mx-3 my-2"><b class="text-white">${requestScope.bank} - count</b></li>
                <li class="nav-item">
                    <form action="GeneralCustomerServlet" method="post">
                        <input name="logout" type="hidden" value="" class="btn btn-outline-warning">
                        <input value="Log out" type="submit" class="btn btn-outline-warning">
                    </form>
                </li>
            </ul>
        </div>
    </nav>
</header>
<body>
<section class="my-5">
    <div class="container-xxl align-items-top">
        <div class="row">
            <c:if test="${!requestScope.result.equals('null')}">
                <b class="text-center text-danger ali">${requestScope.result}</b>
            </c:if>
            <div class="col-12">
                <ul class="pagination">
                    <c:forEach var="i" begin="0" end="${requestScope.pages -1}">
                        <li class="page-item"><a style="color: #1c1c1c" class="page-link"
                                                 href="${pageContext.request.contextPath}/MasterServlet?offset=${i*8}">${i+1}</a></li>
                    </c:forEach>
                </ul>
                <table class="table table-hover">
                    <thead>
                    <tr class="border-dark">
                        <th>id</th>
                        <th>description</th>
                        <th>price</th>
                        <th>payment status</th>
                        <th>complication status</th>
                        <th>date</th>
                        <th ></th>
                        <th ></th>
                        <th ></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="request" items="${requestScope.table}">
                        <tr>
                            <td><c:out value="${request.requestID}"/></td>
                            <td><c:out value="${request.description}"/></td>
                            <td><c:out value="${request.price}"/></td>
                            <td><c:out value="${request.paymentStatus}"/></td>
                            <td><c:out value="${request.complicationStatus}"/></td>
                            <td><c:out value="${request.date}"/></td>
                            <td>
                                <form method="post"
                                      action=${pageContext.request.contextPath}/MasterServlet>
                                    <input type="submit" class="btn btn-outline-dark" value="Take"/>
                                    <input type="hidden" name="id" value="${request.requestID}">
                                    <input type="hidden" name="command" value="takeRequest">
                                </form>
                            </td>
                            <td>
                                <form method="post"
                                      action=${pageContext.request.contextPath}/MasterServlet>
                                    <input type="submit" class="btn btn-outline-warning" value="Done"/>
                                <input type="hidden" name="doneID" value="${request.requestID}">
                                <input type="hidden" name="command" value="doneRequest">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
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
