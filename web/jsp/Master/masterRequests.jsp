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
    <nav class="navbar navbar-dark bg-dark">
        <div class="container-xxl">
            <ul class="nav">
                <li><a class="navbar-brand text-white"
                       href="${pageContext.request.contextPath}/MasterServlet">RepairAgent</a></li>
            </ul>
            <ul class="nav">
                <li class="nav-item mx-3 my-2"><b class="text-white">${requestScope.bank} - count</b></li>
                <li class="nav-item">
                    <button type="button" class="btn btn-outline-warning" data-bs-toggle="modal"
                            data-bs-target="#">
                        Log out
                    </button>
                </li>
            </ul>
        </div>
    </nav>
</header>
<body>
<section class="my-5">
    <div class="container-xxl align-items-top">
        <div class="row">
            <div class="col-6">
                <a class="btn btn-dark" data-bs-toggle="collapse" href="#collapseExample" role="button"
                   aria-expanded="false" aria-controls="collapseExample">
                    How to
                </a>
                <a class="btn btn-dark" data-bs-toggle="collapse" href="#collapseExample2" role="button"
                   aria-expanded="false" aria-controls="collapseExample">
                    Send request
                </a>
                <div class="collapse" id="collapseExample">
                    <div class="card card-body">
                        <div class="accordion accordion-flush" id="accordionFlushExample">
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="flush-headingOne">
                                    <button class="accordion-button collapsed" type="button"
                                            data-bs-toggle="collapse"
                                            data-bs-target="#flush-collapseOne" aria-expanded="false"
                                            aria-controls="flush-collapseOne">
                                        Step 1
                                    </button>
                                </h2>
                                <div id="flush-collapseOne" class="accordion-collapse collapse"
                                     aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
                                    <div class="accordion-body">First you need to leave a request in the form on the
                                        left,
                                        describe in detail what you need to fix
                                    </div>
                                </div>
                            </div>
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="flush-headingTwo">
                                    <button class="accordion-button collapsed" type="button"
                                            data-bs-toggle="collapse"
                                            data-bs-target="#flush-collapseTwo" aria-expanded="false"
                                            aria-controls="flush-collapseTwo">
                                        Step 2
                                    </button>
                                </h2>
                                <div id="flush-collapseTwo" class="accordion-collapse collapse"
                                     aria-labelledby="flush-headingTwo" data-bs-parent="#accordionFlushExample">
                                    <div class="accordion-body">After sending the application, the manager will view
                                        it,
                                        assign
                                        a price and a master to it, you can see all applications in the "Request"
                                        tab
                                    </div>
                                </div>
                            </div>
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="flush-headingThree">
                                    <button class="accordion-button collapsed" type="button"
                                            data-bs-toggle="collapse"
                                            data-bs-target="#flush-collapseThree" aria-expanded="false"
                                            aria-controls="flush-collapseThree">
                                        Step 3
                                    </button>
                                </h2>
                                <div id="flush-collapseThree" class="accordion-collapse collapse"
                                     aria-labelledby="flush-headingThree" data-bs-parent="#accordionFlushExample">
                                    <div class="accordion-body">Next, you need to pay for the application, at the
                                        top
                                        left you
                                        can see your invoice, after paying for it, the master will take
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="collapse" id="collapseExample2">
                    <div class="card card-body">
                        Some
                    </div>
                </div>
                <div>
                    <b class="text-center text-danger ali">${requestScope.result}</b>
                </div>
            </div>
            <div class="col-6">
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
