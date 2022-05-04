<%@ page import="java.util.List" %>
<%@ page import="com.maznichko.DAO.entity.Request" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.stream.Collectors" %><%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 20.04.22
  Time: 02:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>main-page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">

</head>
<body onload="setCheckboxes()">
<header>
    <nav class="navbar navbar-dark bg-dark">
        <div class="container-xxl">
            <ul class="nav">
                <li><a class="navbar-brand text-white"
                       href="${pageContext.request.contextPath}/ManagerServlet">RepairAgent</a></li>
            </ul>
            <ul class="nav">
                <li class="nav-item mx-3 my-2"><b class="text-white">${requestScope.bank} - count</b></li>
                <li class="nav-item mx-2">
                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-outline-warning" data-bs-toggle="modal"
                            data-bs-target="#ReplenishmentModal">
                        Replenishment
                    </button>
                    <!-- Modal -->
                    <div class="modal fade" id="ReplenishmentModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="ReplenishmentModalLabel">Replenishment</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form action="ManagerServlet" method="post">
                                        <select name="login" class="form-select" aria-label="Default select example">
                                            <c:forEach var="user" items="${requestScope.users}">
                                                <option value="${user.login}">${user.login}</option>
                                            </c:forEach>
                                        </select>
                                        <label>money:
                                            <input type="number" name="money"><br/>
                                        </label>
                                        <input type="hidden" name="command" value="replenishment">
                                        <button type="submit" class="btn btn-outline-warning">Replenishment</button>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
                <li class="nav-item">
                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-outline-warning" data-bs-toggle="modal"
                            data-bs-target="#exampleModal">
                        Log out
                    </button>
                </li>
            </ul>
        </div>
    </nav>
</header>
<section>
    <div class="container-xxl">
        <div class="row my-5">
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
                <div class="my-2">
                        <div class="mx-1">
                            <div class="form-check">
                                <input onchange="filterContent1();" class="form-check-input" type="checkbox"
                                       value="done"
                                       id="done" name="done">
                                <label class="form-check-label" for="done">
                                    done
                                </label>
                            </div>
                            <div class="form-check">
                                <input onchange="filterContent2();" class="form-check-input" type="checkbox"
                                       name="progress"
                                       value="progress"
                                       id="progress">
                                <label class="form-check-label" for="progress">
                                    progress
                                </label>
                            </div>
                            <div class="form-check">
                                <input onchange="filterContent3();" class="form-check-input" type="checkbox"
                                       name="consideration" value="consideration"
                                       id="consideration">
                                <label class="form-check-label" for="consideration">
                                    consideration
                                </label>
                            </div>

                            <div class="dropdown">
                                <a class="btn btn-outline-warning dropdown-toggle" href="#" role="button"
                                   id="dropdownMenuLink"
                                   data-bs-toggle="dropdown" aria-expanded="false">
                                    Select masters
                                </a>

                                <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                    <c:forEach var="user" items="${requestScope.masters}">
                                        <li class="mx-1">
                                            <input class="form-check-input"
                                                   onchange="filterMaster${user.userID}();"
                                                   id="master${user.userID}" type="checkbox"
                                                   name="masterLogin"
                                                   value="${user.login}"/> ${user.login}
                                        </li>
                                        <script>
                                            function filterMaster${user.userID}() {
                                                var checkbox1 = document.getElementById("master${user.userID}");
                                                if (checkbox1.checked) {
                                                    sessionStorage.setItem('master${user.userID}', 'true');
                                                } else {
                                                    sessionStorage.setItem('master${user.userID}', 'false');
                                                }
                                                sendFilter();
                                            }
                                        </script>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                        <input type="hidden" name="command" value="filter">
                    </form>
                </div>
                <script>
                    function filterContent1() {
                        var checkbox1 = document.getElementById("done");
                        if (checkbox1.checked) {
                            sessionStorage.setItem('checkbox1', 'true');
                        } else {
                            sessionStorage.setItem('checkbox1', 'false');
                        }
                        sendFilter();
                    }

                    function filterContent2() {
                        var checkbox1 = document.getElementById("progress");
                        if (checkbox1.checked) {
                            sessionStorage.setItem('checkbox2', 'true');
                        } else {
                            sessionStorage.setItem('checkbox2', 'false');
                        }
                        sendFilter();
                    }

                    function filterContent3() {
                        var checkbox1 = document.getElementById("consideration");
                        if (checkbox1.checked) {
                            sessionStorage.setItem('checkbox3', 'true');
                        } else {
                            sessionStorage.setItem('checkbox3', 'false');
                        }
                        sendFilter();
                    }

                    function SortingContent1() {
                        var checkbox1 = document.getElementById("sorted").value;
                        if (checkbox1 === "date") {
                            sessionStorage.setItem('sorted', 'date');
                        } else if (checkbox1 === "status") {
                            sessionStorage.setItem('sorted', 'status');
                        } else if (checkbox1 === "payStatus") {
                            sessionStorage.setItem('sorted', 'payStatus');
                        } else if (checkbox1 === "ascending") {
                            sessionStorage.setItem('sorted', 'ascending');
                        } else if (checkbox1 === "descending") {
                            sessionStorage.setItem('sorted', 'descending');
                        }
                        sendFilter()
                    }

                    function setCheckboxes() {
                        if (sessionStorage.getItem("checkbox1") === "true") {
                            document.getElementById("done").checked = "true";
                        }
                        if (sessionStorage.getItem("checkbox2") === "true") {
                            document.getElementById("progress").checked = "true";
                        }
                        if (sessionStorage.getItem("checkbox3") === "true") {
                            document.getElementById("consideration").checked = "true";
                        }
                        if (sessionStorage.getItem("sorted") != null) {
                            document.getElementById("sorted").value = sessionStorage.getItem("sorted");
                        }
                        <c:forEach var="user"  items="${requestScope.masters}">
                        if (sessionStorage.getItem("master${user.userID}") === "true") {
                            document.getElementById("master${user.userID}").checked = "true"
                        }
                        </c:forEach>
                    }
                    function sendFilter(){
                        var reqAttribute = "";
                        if (sessionStorage.getItem("checkbox1") === "true") {
                            reqAttribute += "&"+document.getElementById("done").name+"="+document.getElementById("done").value;
                        }
                        if (sessionStorage.getItem("checkbox2") === "true") {
                            reqAttribute += "&"+document.getElementById("progress").name+"="+document.getElementById("progress").value;
                        }
                        if (sessionStorage.getItem("checkbox3") === "true") {
                            reqAttribute += "&"+document.getElementById("consideration").name+"="+document.getElementById("consideration").value;
                        }
                        if (sessionStorage.getItem("sorted") != null) {
                            reqAttribute += "&"+document.getElementById("sorted").name+"="+document.getElementById("sorted").value;
                        }
                        <c:forEach var="user"  items="${requestScope.masters}">
                        if (sessionStorage.getItem("master${user.userID}") === "true") {
                            reqAttribute += "&"+document.getElementById("master${user.userID}").name + "=" + document.getElementById("master${user.userID}").value;
                        }
                        </c:forEach>
                        <c:forEach var="i" begin="0" end="${requestScope.pages}">
                        if(document.getElementById("pagination${i}").checked){
                            reqAttribute += "&offset="+document.getElementById("pagination${i}").value;
                        }
                        </c:forEach>
                        document.getElementById("sendFilterForm").href = "/ManagerServlet?" + reqAttribute.substring(1) + "&command=filter";
                        document.getElementById("sendFilterForm").click();
                    }
                </script>
                <a id="sendFilterForm" href="" ></a>
            </div>
            <div class="col-6">
                <select id="sorted" name="sort" onchange="SortingContent1();" class="form-select"
                        aria-label="Default select example">
                    <option value="date">sorting by date</option>
                    <option value="status">sorting by complication</option>
                    <option value="payStatus">sorting by payment</option>
                    <option value="ascending">price sorting in ascending order</option>
                    <option value="descending">price sorting in descending order</option>
                </select>
                <ul class="pagination my-2">
                    <c:forEach var="i" begin="0" end="${requestScope.pages}">
                        <li class="page-item">
                            <div class="form-check form-check-inline">
                                <input onclick="sendFilter()" class="form-check-input" type="radio"
                                       name="offset" id="pagination${i}" value="${i*8}">
                                <label class="form-check-label" for="pagination${i}">${i+1}</label>
                            </div>
                        </li>
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
                        <th scope="col"></th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="request" items="${requestScope.table}">
                        <tr class="border-dark">
                            <td><c:out value="${request.requestID}"/></td>
                            <td><c:out value="${request.description}"/></td>
                            <td><c:out value="${request.price}"/></td>
                            <td><c:out value="${request.paymentStatus}"/></td>
                            <td><c:out value="${request.complicationStatus}"/></td>
                            <td><c:out value="${request.date}"/></td>
                            <td>
                                <div>
                                    <button  type="button" class="btn btn-outline-dark" data-bs-toggle="modal"
                                            data-bs-target="#editModal">
                                        Edit
                                    </button>
                                </div>
                                <div>
                                    <button onclick="setReq(${request.requestID});" type="button" class="btn btn-outline-warning" data-bs-toggle="modal"
                                            data-bs-target="#setModal">
                                        Edit
                                    </button>
                                </div>
                                <!-- Modal -->
                                <div class="modal fade" id="setModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                                     aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="setModalLabel">Set master</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="ManagerServlet" method="get">
                                                    <select name="usr" class="form-select"
                                                            aria-label="Default select example">
                                                        <c:forEach var="user" items="${requestScope.users}">
                                                            <option value="${user.login}">${user.login}</option>
                                                        </c:forEach>
                                                    </select>
                                                    <input type="submit" class="btn btn-outline-warning" value="Set">
                                                    <input type="hidden" name="command" value="setMaster">
                                                    <input id="req" type="hidden" name="ReqID" value="${request.requestID}">
                                                </form>
                                                <script>
                                                    function setReq(req){
                                                        document.getElementById("req").value = req;
                                                    }
                                                </script>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                    Close
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Modal -->
                                <div class="modal fade" id="editModal" tabindex="-1"
                                     aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-xl">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">Edit</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body" style="padding: 6rem">
                                                <table class="table table-hover">
                                                    <tr>
                                                        <th>id</th>
                                                        <th>description</th>
                                                        <th>price</th>
                                                        <th>payment status</th>
                                                        <th>complication status</th>
                                                        <th>date</th>
                                                        <th scope="col"></th>
                                                        <th scope="col"></th>
                                                        <th scope="col"></th>
                                                    </tr>
                                                    <form action="ManagerServlet" method="get">
                                                        <tr>
                                                            <td><c:out value="${request.requestID}"/></td>
                                                            <td><c:out value="${request.description}"/></td>
                                                            <td><input type="number" name="price"></td>
                                                            <c:if test="${request.paymentStatus.equals('unpaid')}">
                                                                <td>
                                                                    <select onchange="changeStatus();"
                                                                            class="form-select"
                                                                            aria-label="Default select example">
                                                                        <option value="1">unpaid</option>
                                                                        <option value="2">waiting for payment</option>
                                                                    </select>
                                                                </td>
                                                            </c:if>
                                                            <c:if test="${request.paymentStatus.equals('waiting for payment') ||
                                                                request.paymentStatus.equals('paid')}">
                                                                <td>
                                                                    <select class="form-select"
                                                                            aria-label="Default select example">
                                                                        <option value="1">${request.paymentStatus}</option>
                                                                    </select>
                                                                </td>
                                                            </c:if>
                                                            <td>
                                                                <select id="statusf" class="form-select"
                                                                        aria-label="Default select example">
                                                                    <option value="unconsideration">under
                                                                        consideration
                                                                    </option>
                                                                    <option value="ref">refuse</option>
                                                                </select>

                                                            </td>
                                                            <td><c:out value="${request.date}"/></td>
                                                        </tr>
                                                    </form>
                                                </table>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-outline-warning"
                                                        data-bs-dismiss="modal">Close
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div>
                    <b>${requestScope.result}</b>
                </div>
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
