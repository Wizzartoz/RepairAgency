<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 18.04.22
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>main-customer</title>
    <link href="${pageContext.request.contextPath}/css/customer_main.css" rel="stylesheet" type="text/css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">

</head>
<body>
<header>
    <nav class="navbar navbar-dark bg-dark">
        <div class="container-xxl">
            <a class="navbar-brand text-white" href="GeneralCustomerServlet">RepairAgent</a>
            <ul class="nav">
                <li class="nav-item mx-3 my-2"><b class="text-white">${requestScope.bank} - count</b></li>
                <li class="nav-item">
                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-outline-warning" data-bs-toggle="modal"
                            data-bs-target="#exampleModal">
                        Replenishment
                    </button>
                </li>
            </ul>
            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="GeneralCustomerServlet" method="post">
                                <label>money:
                                    <input type="number" name="money"><br/>
                                </label>
                                <button type="submit" class="btn btn-outline-warning">Replenishment</button>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </nav>
</header>
<section>
    <div class="container-xxl">
        <div class="row align-items-top">
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
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
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
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                            data-bs-target="#flush-collapseTwo" aria-expanded="false"
                                            aria-controls="flush-collapseTwo">
                                        Step 2
                                    </button>
                                </h2>
                                <div id="flush-collapseTwo" class="accordion-collapse collapse"
                                     aria-labelledby="flush-headingTwo" data-bs-parent="#accordionFlushExample">
                                    <div class="accordion-body">After sending the application, the manager will view it,
                                        assign
                                        a price and a master to it, you can see all applications in the "Request" tab
                                    </div>
                                </div>
                            </div>
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="flush-headingThree">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                            data-bs-target="#flush-collapseThree" aria-expanded="false"
                                            aria-controls="flush-collapseThree">
                                        Step 3
                                    </button>
                                </h2>
                                <div id="flush-collapseThree" class="accordion-collapse collapse"
                                     aria-labelledby="flush-headingThree" data-bs-parent="#accordionFlushExample">
                                    <div class="accordion-body">Next, you need to pay for the application, at the top
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
                <form action="GeneralCustomerServlet" method="post">
                    <label for="exampleFormControlTextarea1" class="form-label">Request</label>
                    <textarea style="height: 600px" name="user_message" class="form-control" id="exampleFormControlTextarea1"
                              rows="3"></textarea>
                    <div class="d-grid gap-2">
                        <button class="btn btn-warning" type="submit">Send request</button>
                    </div>
                </form>
                <b class="text-center text-danger ali">${requestScope.result}</b>
            </div>
            <div class="col-6">
                <table class="table table-hover">
                    <tr class="border-dark">
                        <th>id</th>
                        <th>description</th>
                        <th>price</th>
                        <th>payment status</th>
                        <th>complication status</th>
                        <th>date</th>
                        <th></th>
                        <th></th>
                        <th></th>
                    </tr>
                    <c:forEach var="request" items="${requestScope.table}">
                        <tr class="border-dark">
                            <td><c:out value="${request.requestID}"/></td>
                            <td><c:out value="${request.description}"/></td>
                            <td><c:out value="${request.price}"/></td>
                            <td><c:out value="${request.paymentStatus}"/></td>
                            <td><c:out value="${request.complicationStatus}"/></td>
                            <td><c:out value="${request.date}"/></td>
                            <td>
                                <form method="post"
                                      action=${pageContext.request.contextPath}/RequestServlet?id=${request.requestID}>
                                    <input class="btn btn-outline-dark" type="submit" value="Delete"/></form>
                            </td>
                            <td>
                                <form method="post"
                                      action=${pageContext.request.contextPath}/RequestServlet?price=${request.price}&payment=${request.paymentStatus}&paymentID=${request.requestID}>
                                    <input class="btn btn-outline-dark" type="submit" value="Paid"/></form>
                            </td>
                            <td>
                                <form method="post"
                                      action=${pageContext.request.contextPath}/FeedbackServlet?feedbackID=${request.requestID}&comp=${request.complicationStatus}>
                                    <input class="btn btn-outline-dark" type="submit" value="Feedback"/></form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</section>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous">

</script>
</body>
</html>
