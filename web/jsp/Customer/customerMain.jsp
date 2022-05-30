<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 18.04.22
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>main-customer</title>
    <link href="${pageContext.request.contextPath}/css/customer_main.css" rel="stylesheet" type="text/css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/feedback.css" rel="stylesheet" type="text/css">
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
    <nav class="navbar navbar-dark bg-dark" style="height: 55px">
        <div class="container-xxl align-items-start">
            <ul class="nav">
                <li class="my-1"><a class="navbar-brand text-white"
                       href="${pageContext.request.contextPath}/GeneralCustomerServlet">RepairAgent</a></li>
            </ul>
            <ul class="nav">
                <li class="nav-item mx-3 my-2"><b class="text-white">${requestScope.bank} - <fmt:message key = "customer.header.count" bundle = "${lang}"/> </b></li>
                <li class="nav-item">
                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-outline-warning" data-bs-toggle="modal"
                            data-bs-target="#exampleModal">
                        <fmt:message key = "customer.header.button.replenishment" bundle = "${lang}"/>
                    </button>
                </li>
                <li class="mx-2">
                    <div class="dropdown">
                        <a class="btn btn-outline-warning dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown" aria-expanded="false">
                            <fmt:message key = "customer.header.button.len" bundle = "${lang}"/>
                        </a>

                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <li><a class="dropdown-item" href="GeneralCustomerServlet?locale=en">En</a></li>
                            <li><a class="dropdown-item" href="GeneralCustomerServlet?locale=ru">Ru</a></li>
                        </ul>
                    </div>
                </li>
                <li class="nav-item mx-2">
                    <form action="GeneralCustomerServlet" method="post">
                        <input name="logout" type="hidden" value="" class="btn btn-outline-warning">
                    <input value="<fmt:message key = "customer.header.button.log_out" bundle = "${lang}"/>" type="submit" class="btn btn-outline-warning">
                    </form>
                </li>
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/jsp/Customer/profile.jsp" method="get">
                        <input type="submit" class="btn btn-outline-warning">
                    </form>
                </li>
            </ul>
            <!-- Modal -->
            <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel"><fmt:message key = "customer.main.modal_title.replenishment" bundle = "${lang}"/></h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="GeneralCustomerServlet" method="post">
                                <label><fmt:message key = "customer.main.modal_body.money" bundle = "${lang}"/>:
                                    <input oninput="checkSum()" id="money" type="number" name="money"><br/>
                                </label>
                                <input  type="hidden" name="command" value="replenishment">
                                <button disabled id="payButton" type="submit" class="btn btn-outline-warning"><fmt:message key = "customer.main.modal_button.replenishment" bundle = "${lang}"/></button>
                                <script>
                                    function checkSum() {
                                        let x = document.getElementById("money").value;
                                        if (x <= 0) {
                                            document.getElementById("money").style.color = "inherit";
                                            document.getElementById("money").style.backgroundColor = "rgba(250,233,233,0.51)";
                                            document.getElementById("payButton").disabled = true;
                                        } else {
                                            document.getElementById("money").style.color = "inherit";
                                            document.getElementById("money").style.backgroundColor = "rgba(207,243,210,0.5)";
                                            document.getElementById("payButton").disabled = false;
                                        }
                                    }
                                </script>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-outline-dark" data-bs-dismiss="modal"><fmt:message key = "customer.main.modal_footer.close" bundle = "${lang}"/></button>
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
                    <fmt:message key = "customer.main.dropdown.how_to" bundle = "${lang}"/>
                </a>
                <div class="collapse" id="collapseExample">
                    <div class="card card-body">
                        <div class="accordion accordion-flush" id="accordionFlushExample">
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="flush-headingOne">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                            data-bs-target="#flush-collapseOne" aria-expanded="false"
                                            aria-controls="flush-collapseOne">
                                        <fmt:message key = "customer.main.dropdown.how_to.title_step_1" bundle = "${lang}"/>
                                    </button>
                                </h2>
                                <div id="flush-collapseOne" class="accordion-collapse collapse"
                                     aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
                                    <div class="accordion-body"><fmt:message key = "customer.main.dropdown.how_to.body_step_1" bundle = "${lang}"/>
                                    </div>
                                </div>
                            </div>
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="flush-headingTwo">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                            data-bs-target="#flush-collapseTwo" aria-expanded="false"
                                            aria-controls="flush-collapseTwo">
                                        <fmt:message key = "customer.main.dropdown.how_to.title_step_2" bundle = "${lang}"/>
                                    </button>
                                </h2>
                                <div id="flush-collapseTwo" class="accordion-collapse collapse"
                                     aria-labelledby="flush-headingTwo" data-bs-parent="#accordionFlushExample">
                                    <div class="accordion-body"><fmt:message key = "customer.main.dropdown.how_to.body_step_2" bundle = "${lang}"/>
                                    </div>
                                </div>
                            </div>
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="flush-headingThree">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                            data-bs-target="#flush-collapseThree" aria-expanded="false"
                                            aria-controls="flush-collapseThree">
                                        <fmt:message key = "customer.main.dropdown.how_to.title_step_3" bundle = "${lang}"/>
                                    </button>
                                </h2>
                                <div id="flush-collapseThree" class="accordion-collapse collapse"
                                     aria-labelledby="flush-headingThree" data-bs-parent="#accordionFlushExample">
                                    <div class="accordion-body"><fmt:message key = "customer.main.dropdown.how_to.body_step_3" bundle = "${lang}"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <form action="GeneralCustomerServlet" method="post">
                    <label for="exampleFormControlTextarea1" class="form-label"><fmt:message key = "customer.main.request_form.label" bundle = "${lang}"/></label>
                    <textarea oninput="changeTextArea()"   style="height: 400px;user-select: none" name="user_message" class="form-control"
                              id="exampleFormControlTextarea1"
                              rows="3"></textarea>
                    <input type="hidden" name="command" value="sendRequest">
                    <div class="d-grid gap-2">
                        <button disabled id="requestButton" class="btn btn-warning" type="submit"><fmt:message key = "customer.main.request_form.button" bundle = "${lang}"/></button>
                    </div>
                    <script>
                        function changeTextArea() {
                            let x = document.getElementById("exampleFormControlTextarea1").value;
                            if (x.length < 5 || x.length > 50) {
                                document.getElementById("exampleFormControlTextarea1").style.color = "inherit";
                                document.getElementById("exampleFormControlTextarea1").style.backgroundColor = "rgba(250,233,233,0.51)";
                                document.getElementById("requestButton").disabled = true;
                            } else {
                                document.getElementById("exampleFormControlTextarea1").style.color = "inherit";
                                document.getElementById("exampleFormControlTextarea1").style.backgroundColor = "rgba(207,243,210,0.5)";
                                document.getElementById("requestButton").disabled = false;
                            }
                        }
                    </script>
                </form>
                <b class="text-center text-danger ali">
                    <c:if test="${!requestScope.result.equals('null')}">
                    ${requestScope.result}
                </c:if></b>
            </div>
            <div class="col-6">
                <ul class="pagination">
                    <c:forEach var="i" begin="0" end="${requestScope.pages -1}">
                        <li class="page-item"><a style="color: #1c1c1c" class="page-link"
                                                 href="GeneralCustomerServlet?offset=${i*8}">${i+1}</a></li>
                    </c:forEach>
                </ul>
                <table class="table table-hover">
                    <thead class="thead-dark">
                    <tr class="border-dark">
                        <th scope="col"><fmt:message key = "customer.main.table_title.id" bundle = "${lang}"/></th>
                        <th scope="col"><fmt:message key = "customer.main.table_title.description" bundle = "${lang}"/></th>
                        <th scope="col"><fmt:message key = "customer.main.table_title.price" bundle = "${lang}"/></th>
                        <th scope="col"><fmt:message key = "customer.main.table_title.payment_status" bundle = "${lang}"/></th>
                        <th scope="col"><fmt:message key = "customer.main.table_title.complication_status" bundle = "${lang}"/></th>
                        <th scope="col"><fmt:message key = "customer.main.table_title.date" bundle = "${lang}"/></th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <c:forEach var="request" items="${requestScope.table}">
                        <tr class="border-dark">
                            <td scope="col"><c:out value="${request.requestID}"/></td>
                            <td scope="col"><c:out value="${request.description}"/></td>
                            <td scope="col"><c:out value="${request.price}"/></td>
                            <td scope="col"><c:out value="${request.paymentStatus}"/></td>
                            <td scope="col"><c:out value="${request.complicationStatus}"/></td>
                            <td scope="col"><c:out value="${request.date}"/></td>
                            <td scope="col">
                                <form method="post"
                                      action=${pageContext.request.contextPath}/GeneralCustomerServlet>
                                    <input class="btn btn-outline-dark" type="hidden" name="id"
                                           value="${request.requestID}"/>
                                    <input class="btn btn-outline-dark" type="hidden" name="command"
                                           value="deleteRequest"/>
                                    <input class="btn btn-outline-dark" type="submit" value="<fmt:message key = "customer.main.table_button.delete" bundle = "${lang}"/>"/>
                                </form>
                            </td>
                            <th scope="col">
                                <form method="post"
                                      action=${pageContext.request.contextPath}/GeneralCustomerServlet>
                                    <input class="btn btn-outline-dark" type="submit" value="<fmt:message key = "customer.main.table_button.paid" bundle = "${lang}"/>"/>
                                    <input class="btn btn-outline-dark" type="hidden" name="price"
                                           value="${request.price}"/>
                                    <input class="btn btn-outline-dark" type="hidden" name="payment"
                                           value="${request.paymentStatus}"/>
                                    <input class="btn btn-outline-dark" type="hidden" name="paymentID"
                                           value="${request.requestID}"/>
                                    <input class="btn btn-outline-dark" type="hidden" name="command" value="paid"/>
                                </form>
                            </th>
                            <th scope="col">
                                <button onclick="setID(${request.requestID});" type="button" class="btn btn-outline-warning" data-bs-toggle="modal"
                                        data-bs-target="#feedback">
                                    <fmt:message key = "customer.main.table_button.feedback" bundle = "${lang}"/>
                                </button>
                                <!-- Modal -->
                                <div class="modal fade" id="feedback" tabindex="-1" aria-labelledby="exampleModalLabel"
                                     aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="feedbackLabel"><fmt:message key = "customer.main.table_button.feedback" bundle = "${lang}"/></h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form method="post"
                                                      action=${pageContext.request.contextPath}/GeneralCustomerServlet>
                                                    <div class="rating">
                                                        <input type="radio" name="rating" value="5" id="5"><label
                                                            for="5">☆</label>
                                                        <input type="radio" name="rating" value="4" id="4"><label
                                                            for="4">☆</label>
                                                        <input type="radio" name="rating" value="3" id="3"><label
                                                            for="3">☆</label>
                                                        <input type="radio" name="rating" value="2" id="2"><label
                                                            for="2">☆</label>
                                                        <input type="radio" name="rating" value="1" id="1"><label
                                                            for="1">☆</label>
                                                    </div>
                                                    <div>
                                                        <textarea oninput="checkFeedback();" style="height: 200px;width: 450px" id="msg"
                                                                  name="feedback"></textarea>
                                                    </div>
                                                    <input disabled id="feedbackButton" class="btn btn-outline-warning my-2" type="submit"
                                                           value="<fmt:message key = "customer.main.table_modal.button_send_feedback" bundle = "${lang}"/>"/>
                                                    <input  type="hidden"
                                                           name="feedbackID"
                                                           id="feedbackID"
                                                    value="1"/>
                                                    <input  type="hidden" name="comp"
                                                           id="comp"
                                                    value="2"/>
                                                    <input  type="hidden" name="command"
                                                           value="leaveFeedback"/>
                                                    <script>
                                                        function checkFeedback() {
                                                            let x = document.getElementById("msg").value;
                                                            if (x.length < 4 || x.length > 20) {
                                                                document.getElementById("msg").style.color = "inherit";
                                                                document.getElementById("msg").style.backgroundColor = "rgba(250,233,233,0.51)";
                                                                document.getElementById("feedbackButton").disabled = true;
                                                            } else {
                                                                document.getElementById("msg").style.color = "inherit";
                                                                document.getElementById("msg").style.backgroundColor = "rgba(207,243,210,0.5)";
                                                                document.getElementById("feedbackButton").disabled = false;
                                                            }
                                                        }
                                                    </script>
                                                </form>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-outline-dark" data-bs-dismiss="modal">
                                                    <fmt:message key = "customer.main.table_modal.close" bundle = "${lang}"/>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </th>
                        </tr>
                        <script>
                            function setID(id) {
                                document.getElementById("feedbackID").value = id;
                            }
                        </script>
                    </c:forEach>
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
