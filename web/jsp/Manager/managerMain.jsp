<%@ page import="java.util.List" %>
<%@ page import="com.maznichko.dao.entity.Request" %>
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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>main-page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">

</head>
<body onload="setCheckboxes()">
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
                                    href="${pageContext.request.contextPath}/ManagerServlet">RepairAgent</a></li>
            </ul>
            <ul class="nav">
                <li class="nav-item mx-3 my-2"><b class="text-white">${requestScope.bank} - <fmt:message
                        key="customer.header.count" bundle="${lang}"/></b></li>
                <li class="nav-item">
                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-outline-warning" data-bs-toggle="modal"
                            data-bs-target="#ReplenishmentModal">
                        <fmt:message key="customer.header.button.replenishment" bundle="${lang}"/>
                    </button>
                    <!-- Modal -->
                    <div class="modal fade" id="ReplenishmentModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="ReplenishmentModalLabel"><fmt:message
                                            key="customer.main.modal_title.replenishment" bundle="${lang}"/></h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form action="ManagerServlet" method="post">
                                        <select name="login" class="form-select my-2"
                                                aria-label="Default select example">
                                            <c:forEach var="user" items="${requestScope.users}">
                                                <option value="${user.login}">${user.login}</option>
                                            </c:forEach>
                                        </select>
                                        <label><fmt:message key="customer.main.modal_body.money" bundle="${lang}"/>:
                                            <input oninput="checkSum()" id="money" type="number" name="money"><br/>
                                        </label>
                                        <input  type="hidden" name="command" value="replenishment">
                                        <button id="payment" disabled onclick="offCheckBox()" type="submit" class="btn btn-outline-warning">
                                            <fmt:message key="customer.main.modal_button.replenishment"
                                                         bundle="${lang}"/></button>
                                        <script>
                                            function checkSum() {
                                                let x = document.getElementById("money").value;
                                                if (x <= 0) {
                                                    document.getElementById("money").style.color = "inherit";
                                                    document.getElementById("money").style.backgroundColor = "rgba(250,233,233,0.51)";
                                                    document.getElementById("payment").disabled = true;
                                                } else {
                                                    document.getElementById("money").style.color = "inherit";
                                                    document.getElementById("money").style.backgroundColor = "rgba(207,243,210,0.5)";
                                                    document.getElementById("payment").disabled = false;
                                                }
                                            }
                                        </script>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-outline-dark" data-bs-dismiss="modal">
                                        <fmt:message key="customer.main.modal_footer.close" bundle="${lang}"/>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
                <li class="mx-2">
                    <div class="dropdown">
                        <a class="btn btn-outline-warning dropdown-toggle" href="#" role="button" id="dropdownMenu"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            <fmt:message key="customer.header.button.len" bundle="${lang}"/>
                        </a>

                        <ul class="dropdown-menu" aria-labelledby="dropdownMenu">
                            <li><a class="dropdown-item" href="ManagerServlet?locale=en">En</a></li>
                            <li><a class="dropdown-item" href="ManagerServlet?locale=ru">Ru</a></li>
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
<section>
    <div class="container-fluid d-flex justify-content-center">
        <div class="row my-5">
            <div class="col-1"  style="width: 250px">
                <!-- Button trigger modal -->
                <button onclick="offCheckBox()" type="button" class="btn btn-dark my-2" data-bs-toggle="modal"
                        data-bs-target="#registerModal">
                    <fmt:message key="register.button.register_master" bundle="${lang}"/>
                </button>
                <form action="ManagerReportServlet" method="get">
                <button style="width: 176px" type="submit" class="btn btn-dark"> <fmt:message key="register.button.statistic" bundle="${lang}"/></button>
                </form>


                <!-- Modal -->
                <div class="modal fade" id="registerModal" tabindex="-1" aria-labelledby="registerModalLabel"
                     aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="registerModalLabel"><fmt:message
                                        key="register.label.register" bundle="${lang}"/></h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                            </div>
                            <div class="modal-body" style="padding: 3rem">
                                <div class="row">
                                    <div class="colm-form">
                                        <div class="form-container">
                                            <form action="ManagerServlet" method="post">
                                                <div class="form-floating">
                                                    <input oninput="changeName()" type="text" name="name"
                                                           class="form-control"
                                                           id="Name" placeholder="Name">
                                                    <label for="Name"><fmt:message key="register.label.name"
                                                                                   bundle="${lang}"/></label>
                                                </div>
                                                <div class="form-floating">
                                                    <input oninput="changeSurname()" type="text" name="surname"
                                                           class="form-control" id="Surname"
                                                           placeholder="Surname">
                                                    <label for="Surname"><fmt:message key="register.label.surname"
                                                                                      bundle="${lang}"/></label>
                                                </div>
                                                <div class="form-floating">
                                                    <input oninput="changeLogin()" type="text" name="login"
                                                           class="form-control" id="Login"
                                                           placeholder="Login">
                                                    <label for="Login"><fmt:message key="register.label.login"
                                                                                    bundle="${lang}"/></label>
                                                </div>
                                                <div class="form-floating">
                                                    <input oninput="changePassword()" type="password" name="pass"
                                                           class="form-control"
                                                           id="Password" placeholder="Password">
                                                    <label for="Password"><fmt:message key="register.label.password"
                                                                                       bundle="${lang}"/></label>
                                                </div>
                                                <div class="form-floating">
                                                    <input oninput="changeEmail()" type="email" name="email"
                                                           class="form-control" id="Email"
                                                           placeholder="Email">
                                                    <label for="Email"><fmt:message key="register.label.email"
                                                                                    bundle="${lang}"/></label>
                                                </div>
                                                <div class="form-floating">
                                                    <input oninput="changeNumber()" type="text" name="phone"
                                                           class="form-control" id="Phone"
                                                           placeholder="Phone">
                                                    <label for="Phone"><fmt:message key="register.label.phone"
                                                                                    bundle="${lang}"/></label>
                                                </div>
                                                <div onmousemove="checkButton()" class="form-floating">
                                                    <button id="button"
                                                            class="w-100 btn btn-lg btn-warning" disabled type="submit">
                                                        <fmt:message key="register.button.register" bundle="${lang}"/>

                                                    </button>
                                                </div>
                                                <script>
                                                    function changeName() {
                                                        let x = document.getElementById("Name").value;
                                                        const reg = new RegExp("[A-Za-zА-Яa-я]{" + x.length + "}");
                                                        if (x.length < 3 || x.length > 12) {
                                                            document.getElementById("Name").style.color = "inherit";
                                                            document.getElementById("Name").style.backgroundColor = "rgba(250,233,233,0.51)";
                                                        } else if (!reg.test(x)) {
                                                            document.getElementById("Name").style.color = "inherit";
                                                            document.getElementById("Name").style.backgroundColor = "rgba(250,233,233,0.51)";
                                                        } else {
                                                            console.log("#NAMEtrue")
                                                            document.getElementById("Name").style.color = "inherit";
                                                            document.getElementById("Name").style.backgroundColor = "rgba(207,243,210,0.5)";
                                                            return true;
                                                        }

                                                    }

                                                    function changeSurname() {
                                                        let x = document.getElementById("Surname").value;
                                                        const reg = new RegExp("[A-Za-zА-Яa-я]{" + x.length + "}");
                                                        if (x.length < 3 || x.length > 12) {
                                                            document.getElementById("Surname").style.color = "inherit";
                                                            document.getElementById("Surname").style.backgroundColor = "rgba(250,233,233,0.51)";
                                                        } else if (!reg.test(x)) {
                                                            document.getElementById("Surname").style.color = "inherit";
                                                            document.getElementById("Surname").style.backgroundColor = "rgba(250,233,233,0.51)";
                                                        } else {
                                                            console.log("#SURNAMEtrue")
                                                            document.getElementById("Surname").style.color = "inherit";
                                                            document.getElementById("Surname").style.backgroundColor = "rgba(207,243,210,0.5)";
                                                            return true;
                                                        }

                                                    }

                                                    function changePassword() {
                                                        let x = document.getElementById("Password").value;
                                                        const reg = new RegExp("[A-Za-z0-9]{" + x.length + "}");
                                                        if (!reg.test(x)) {
                                                            document.getElementById("Password").style.color = "inherit";
                                                            document.getElementById("Password").style.backgroundColor = "rgba(250,233,233,0.51)";
                                                        } else if (x.length < 6 || x.length > 12) {
                                                            document.getElementById("Password").style.color = "inherit";
                                                            document.getElementById("Password").style.backgroundColor = "rgba(250,233,233,0.51)";
                                                        } else {
                                                            console.log("#Passwordtrue")
                                                            document.getElementById("Password").style.color = "inherit";
                                                            document.getElementById("Password").style.backgroundColor = "rgba(207,243,210,0.5)";
                                                            return true;
                                                        }
                                                    }

                                                    function changeLogin() {
                                                        let x = document.getElementById("Login").value;
                                                        const reg = new RegExp("[A-Za-z0-9]{" + x.length + "}");
                                                        if (x.length < 3 || x.length > 12) {
                                                            document.getElementById("Login").style.color = "inherit";
                                                            document.getElementById("Login").style.backgroundColor = "rgba(250,233,233,0.51)";
                                                        } else if (!reg.test(x)) {
                                                            document.getElementById("Login").style.color = "inherit";
                                                            document.getElementById("Login").style.backgroundColor = "rgba(250,233,233,0.51)";
                                                        } else {
                                                            console.log("#LOGINtrue")
                                                            document.getElementById("Login").style.color = "inherit";
                                                            document.getElementById("Login").style.backgroundColor = "rgba(207,243,210,0.5)";
                                                            return true;
                                                        }

                                                    }

                                                    function changeEmail() {
                                                        let x = document.getElementById("Email").value;
                                                        const reg = new RegExp("[a-zA-z]{1,15}[-_.]?[A-Za-z\\d]{1,15}@[a-z]{2,5}[.][a-z]{2,4}");
                                                        if (!reg.test(x)) {
                                                            document.getElementById("Email").style.color = "inherit";
                                                            document.getElementById("Email").style.backgroundColor = "rgba(250,233,233,0.51)";
                                                        } else {
                                                            console.log("#Emailtrue")
                                                            document.getElementById("Email").style.color = "inherit";
                                                            document.getElementById("Email").style.backgroundColor = "rgba(207,243,210,0.5)";
                                                            return true;
                                                        }

                                                    }

                                                    function changeNumber() {
                                                        let x = document.getElementById("Phone").value;
                                                        const reg = new RegExp("\\d{10}")
                                                        if (x.length !== 10) {
                                                            document.getElementById("Phone").style.color = "inherit";
                                                            document.getElementById("Phone").style.backgroundColor = "rgba(250,233,233,0.51)";
                                                        } else if (!reg.test(x)) {
                                                            document.getElementById("Phone").style.color = "inherit";
                                                            document.getElementById("Phone").style.backgroundColor = "rgba(250,233,233,0.51)";
                                                        } else {
                                                            console.log("#PHONEtrue")
                                                            document.getElementById("Phone").style.color = "inherit";
                                                            document.getElementById("Phone").style.backgroundColor = "rgba(207,243,210,0.5)";
                                                            return true;
                                                        }

                                                    }
                                                    function checkButton() {
                                                        if (changeEmail() && changeNumber() && changeSurname() && changeName() && changePassword() && changeLogin()) {
                                                            console.log("#true")
                                                            document.getElementById("button").disabled = false;
                                                        }
                                                        console.log("#false")
                                                    }

                                                </script>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-outline-dark" data-bs-dismiss="modal"><fmt:message
                                        key="customer.main.modal_footer.close" bundle="${lang}"/>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="my-2">
                    <div class="mx-1">
                        <fmt:message key="manager.filter.complication.label" bundle="${lang}"/>:
                        <div class="form-check my-1">
                            <input onchange="filterContent1();" class="form-check-input" type="checkbox"
                                   value="done"
                                   id="done" name="compStatus">
                            <label class="form-check-label" for="done">
                                <fmt:message key="manager.filter.complication.done" bundle="${lang}"/>
                            </label>
                        </div>
                        <div class="form-check my-1">
                            <input onchange="filterContent2();" class="form-check-input" type="checkbox"
                                   name="compStatus"
                                   value="in progress"
                                   id="progress">
                            <label class="form-check-label" for="progress">
                                <fmt:message key="manager.filter.complication.in_progress" bundle="${lang}"/>
                            </label>
                        </div>
                        <div class="form-check my-1">
                            <input onchange="filterContent3();" class="form-check-input" type="checkbox"
                                   name="compStatus" value="under consideration"
                                   id="unconsideration">
                            <label class="form-check-label" for="unconsideration">
                                <fmt:message key="manager.filter.complication.under_consideration" bundle="${lang}"/>
                            </label>
                        </div>
                        <div class="form-check my-1">
                            <input onchange="filterContent4();" class="form-check-input" type="checkbox"
                                   name="compStatus" value="consideration"
                                   id="consideration">
                            <label class="form-check-label" for="consideration">
                                <fmt:message key="manager.filter.complication.consideration" bundle="${lang}"/>
                            </label>
                        </div>
                        <div class="form-check border-bottom border-dark my-1">
                            <input onchange="filterContent5();" class="form-check-input" type="checkbox"
                                   name="compStatus" value="refuse"
                                   id="refuse">
                            <label class="form-check-label" for="refuse">
                                <fmt:message key="manager.filter.complication.refuse" bundle="${lang}"/>
                            </label>
                        </div>
                        <fmt:message key="manager.filter.payment.label" bundle="${lang}"/>:
                        <div class="form-check my-1">
                            <input onchange="filterContent6();" class="form-check-input" type="checkbox"
                                   name="payStatus" value="unpaid"
                                   id="unpaid">
                            <label class="form-check-label" for="unpaid">
                                <fmt:message key="manager.filter.payment.unpaid" bundle="${lang}"/>
                            </label>
                        </div>
                        <div class="form-check my-1">
                            <input onchange="filterContent7();" class="form-check-input" type="checkbox"
                                   name="payStatus" value="waiting for payment"
                                   id="waiting">
                            <label class="form-check-label" for="waiting">
                                <fmt:message key="manager.filter.payment.waiting_for_payment" bundle="${lang}"/>
                            </label>
                        </div>
                        <div class="form-check my-1">
                            <input onchange="filterContent8();" class="form-check-input" type="checkbox"
                                   name="payStatus" value="paid"
                                   id="paid">
                            <label class="form-check-label" for="paid">
                                <fmt:message key="manager.filter.payment.paid" bundle="${lang}"/>
                            </label>
                        </div>

                        <div class="dropdown my-1">
                            <a class="btn btn-outline-warning dropdown-toggle" href="#" role="button"
                               id="dropdownMenuLink"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                <fmt:message key="manager_dropdown_masters" bundle="${lang}"/>
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
                <c:if test="${!requestScope.result.equals('null')}">
                    <b class="text-center text-danger ali">${requestScope.result}</b>
                </c:if>
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
                        var checkbox1 = document.getElementById("unconsideration");
                        if (checkbox1.checked) {
                            sessionStorage.setItem('checkbox3', 'true');
                        } else {
                            sessionStorage.setItem('checkbox3', 'false');
                        }
                        sendFilter();

                    }

                    function filterContent4() {
                        var checkbox1 = document.getElementById("consideration");
                        if (checkbox1.checked) {
                            sessionStorage.setItem('checkbox4', 'true');
                        } else {
                            sessionStorage.setItem('checkbox4', 'false');
                        }
                        sendFilter();

                    }

                    function filterContent5() {
                        var checkbox1 = document.getElementById("refuse");
                        if (checkbox1.checked) {
                            sessionStorage.setItem('checkbox5', 'true');
                        } else {
                            sessionStorage.setItem('checkbox5', 'false');
                        }
                        sendFilter();

                    }

                    function filterContent6() {
                        var checkbox1 = document.getElementById("unpaid");
                        if (checkbox1.checked) {
                            sessionStorage.setItem('checkbox6', 'true');
                        } else {
                            sessionStorage.setItem('checkbox6', 'false');
                        }
                        sendFilter();


                    }

                    function filterContent7() {
                        var checkbox1 = document.getElementById("waiting");
                        if (checkbox1.checked) {
                            sessionStorage.setItem('checkbox7', 'true');
                        } else {
                            sessionStorage.setItem('checkbox7', 'false');
                        }
                        sendFilter();
                    }

                    function filterContent8() {
                        var checkbox1 = document.getElementById("paid");
                        if (checkbox1.checked) {
                            sessionStorage.setItem('checkbox8', 'true');
                        } else {
                            sessionStorage.setItem('checkbox8', 'false');
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
                        sendFilter();

                    }


                    function setCheckboxes() {

                        if (sessionStorage.getItem("checkbox1") === "true") {
                            document.getElementById("done").checked = "true";
                        }
                        if (sessionStorage.getItem("checkbox2") === "true") {
                            document.getElementById("progress").checked = "true";
                        }
                        if (sessionStorage.getItem("checkbox3") === "true") {
                            document.getElementById("unconsideration").checked = "true";
                        }
                        if (sessionStorage.getItem("checkbox4") === "true") {
                            document.getElementById("consideration").checked = "true";
                        }
                        if (sessionStorage.getItem("checkbox5") === "true") {
                            document.getElementById("refuse").checked = "true";
                        }
                        if (sessionStorage.getItem("checkbox6") === "true") {
                            document.getElementById("unpaid").checked = "true";
                        }
                        if (sessionStorage.getItem("checkbox7") === "true") {
                            document.getElementById("waiting").checked = "true";
                        }
                        if (sessionStorage.getItem("checkbox8") === "true") {
                            document.getElementById("paid").checked = "true";
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

                    function sendFilter() {
                        var reqAttribute = "";
                        if (sessionStorage.getItem("checkbox1") === "true") {
                            reqAttribute += "&" + document.getElementById("done").name + "=" + document.getElementById("done").value;
                        }
                        if (sessionStorage.getItem("checkbox2") === "true") {
                            reqAttribute += "&" + document.getElementById("progress").name + "=" + document.getElementById("progress").value;
                        }
                        if (sessionStorage.getItem("checkbox3") === "true") {
                            reqAttribute += "&" + document.getElementById("unconsideration").name + "=" + document.getElementById("unconsideration").value;
                        }
                        if (sessionStorage.getItem("checkbox4") === "true") {
                            reqAttribute += "&" + document.getElementById("consideration").name + "=" + document.getElementById("consideration").value;
                        }
                        if (sessionStorage.getItem("checkbox5") === "true") {
                            reqAttribute += "&" + document.getElementById("refuse").name + "=" + document.getElementById("refuse").value;
                        }
                        if (sessionStorage.getItem("checkbox6") === "true") {
                            reqAttribute += "&" + document.getElementById("unpaid").name + "=" + document.getElementById("unpaid").value;
                        }
                        if (sessionStorage.getItem("checkbox7") === "true") {
                            reqAttribute += "&" + document.getElementById("waiting").name + "=" + document.getElementById("waiting").value;
                        }
                        if (sessionStorage.getItem("checkbox8") === "true") {
                            reqAttribute += "&" + document.getElementById("paid").name + "=" + document.getElementById("paid").value;
                        }
                        if (sessionStorage.getItem("sorted") != null) {
                            reqAttribute += "&" + document.getElementById("sorted").name + "=" + document.getElementById("sorted").value;
                        } else {
                            reqAttribute += "&sort=date";
                        }
                        <c:forEach var="user"  items="${requestScope.masters}">
                        if (sessionStorage.getItem("master${user.userID}") === "true") {
                            reqAttribute += "&" + document.getElementById("master${user.userID}").name + "=" + document.getElementById("master${user.userID}").value;
                        }
                        </c:forEach>
                        <c:forEach var="i" begin="0" end="${requestScope.pages -1 }">
                        if (document.getElementById("pagination${i}").checked) {
                            reqAttribute += "&offset=" + document.getElementById("pagination${i}").value;
                        }
                        </c:forEach>
                        document.getElementById("sendFilterForm").href = "/ManagerServlet?" + reqAttribute.substring(1) + "&command=filter";
                        document.getElementById("sendFilterForm").click();
                    }

                </script>
                <a id="sendFilterForm" href=""></a>
            </div>
            <div class="col-11" style="width: 1550px">
                <select id="sorted" name="sort" onchange="SortingContent1();" class="form-select"
                        aria-label="Default select example">
                    <option value="date"><fmt:message key="manager.sorting.date" bundle="${lang}"/></option>
                    <option value="status"><fmt:message key="manager.sorting.complication" bundle="${lang}"/></option>
                    <option value="payStatus"><fmt:message key="manager.sorting.payment" bundle="${lang}"/></option>
                    <option value="ascending"><fmt:message key="manager.sorting.ascending" bundle="${lang}"/></option>
                    <option value="descending"><fmt:message key="manager.sorting.descending" bundle="${lang}"/></option>
                </select>
                <ul class="pagination my-2">
                    <c:forEach var="i" begin="0" end="${requestScope.pages - 1}">
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
                        <th><fmt:message key="customer.main.table_title.id" bundle="${lang}"/></th>
                        <th><fmt:message key="customer.main.table_title.description" bundle="${lang}"/></th>
                        <th><fmt:message key="customer.main.table_title.price" bundle="${lang}"/></th>
                        <th><fmt:message key="customer.main.table_title.payment_status" bundle="${lang}"/></th>
                        <th><fmt:message key="customer.main.table_title.complication_status" bundle="${lang}"/></th>
                        <th><fmt:message key="customer.main.table_title.master_login" bundle="${lang}"/></th>
                        <th><fmt:message key="customer.main.table_title.date" bundle="${lang}"/></th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="request" items="${requestScope.table}">
                        <c:if test="${!request.complicationStatus.equals('refuse')}">
                            <form action="ManagerServlet" method="post">
                                <tr class="border-dark">
                                    <td><c:out value="${request.requestID}"/></td>
                                    <td><c:out value="${request.description}"/></td>
                                    <c:if test="${request.price == 0}">
                                    <td><input  type="number" min="1" max="100000" name="price" value="price"/></td>
                                    </c:if>
                                    <c:if test="${request.price != 0}">
                                    <td><c:out value="${request.price}"/></td>
                                    </c:if>
                                    <c:if test="${request.paymentStatus.equals('waiting for payment')}">

                                    <td>
                                        <p>waiting for payment</p>
                                    </td>
                                    </c:if>
                                    <c:if test="${request.paymentStatus.equals('paid')}">

                                    <td>
                                        <p>paid</p>
                                    </td>
                                    </c:if>
                                    <c:if test="${request.paymentStatus.equals('unpaid')}">

                                    <td>
                                        <select name="pStatus" class="form-select"
                                                aria-label="Default select example">
                                            <option value="unpaid">unpaid</option>
                                            <option value="waiting for payment">waiting for payment</option>
                                        </select>
                                    </td>
                                    </c:if>


                                    <c:if test="${request.complicationStatus.equals('consideration')
                                                           }">
                                    <td>
                                        <p>consideration</p>
                                    </td>
                                    </c:if>
                                    <c:if test="${request.complicationStatus.equals('in progress')}">
                                    <td>
                                        <p>in progress</p>
                                    </td>
                                    </c:if>
                                    <c:if test="${request.complicationStatus.equals('done')}">
                                    <td>
                                        <p>done</p>
                                    </td>
                                    </c:if>
                                    <c:if test="${request.complicationStatus.equals('under consideration')}">

                                    <td>
                                        <select id="cStatus"  name="cStatus" class="form-select"
                                                aria-label="Default select example">
                                            <option value="under consideration">${request.complicationStatus}</option>
                                            <option value="consideration">consideration</option>
                                            <option value="refuse">refuse</option>
                                        </select>
                                    </td>
                                    </c:if>
                                    <c:if test="${request.complicationStatus.equals('refuse')}">

                                    <td>
                                        <p>refuse</p>
                                    </td>
                                    </c:if>
                                    <c:if test="${request.masterLogin == null}">
                                    <td>
                                        <select name="usr" class="form-select my-2"
                                                aria-label="Default select example">
                                            <option>мастер не назначен</option>
                                            <c:forEach var="user" items="${requestScope.masters}">
                                                <option value="${user.login}">${user.login}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    </c:if>
                                    <c:if test="${request.masterLogin != null}">
                                    <td><c:out value="${request.masterLogin}"/></td>
                                    </c:if>

                                    <td><c:out value="${request.date}"/></td>
                                    <td>
                                        <input type="hidden" name="id" value="${request.requestID}">
                                        <input   type="hidden" name="command" value="editRequest">
                                        <input  onclick="offCheckBox()"
                                               value="<fmt:message key = "manager.table.edit_request" bundle = "${lang}"/>"
                                               type="submit" class="btn btn-outline-warning">
                                    </td>
                                    <td>
                            </form>
                        </c:if>
                        <c:if test="${request.complicationStatus.equals('refuse')}">
                            <tr class="border-dark">
                                <td scope="col"><c:out value="${request.requestID}"/></td>
                                <td scope="col"><c:out value="${request.description}"/></td>
                                <td scope="col"><c:out value="${request.price}"/></td>
                                <td scope="col"><c:out value="${request.paymentStatus}"/></td>
                                <td scope="col"><c:out value="${request.complicationStatus}"/></td>
                                <td scope="col"><c:out value="не назначен"/></td>
                                <td scope="col"><c:out value="${request.date}"/></td>
                                <td>
                                    <form action="ManagerServlet" method="post">
                                        <input type="hidden" name="id" value="${request.requestID}">
                                        <input type="hidden" name="command" value="editRequest">
                                        <input onclick="offCheckBox()"
                                               value="<fmt:message key = "manager.table.edit_request" bundle = "${lang}"/>"
                                               type="submit" class="btn btn-outline-warning">
                                    </form>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
                <div>
                </div>
            </div>
        </div>
    </div>
    <script>
        function offCheckBox() {
            sessionStorage.setItem("checkbox1", "false");
            sessionStorage.setItem("checkbox2", "false");
            sessionStorage.setItem("checkbox3", "false");
            sessionStorage.setItem("checkbox4", "false");
            sessionStorage.setItem("checkbox5", "false");
            sessionStorage.setItem("checkbox6", "false");
            sessionStorage.setItem("checkbox7", "false");
            sessionStorage.setItem("checkbox8", "false");
            sessionStorage.setItem('sorted', 'date');
            <c:forEach var="user"  items="${requestScope.masters}">
            sessionStorage.setItem("master${user.userID}", "false");
            </c:forEach>
        }
    </script>
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
