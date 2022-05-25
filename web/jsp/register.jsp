<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 17.04.22
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet" type="text/css">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>register</title>
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
<main>
    <div class="row">
        <div class="colm-form">
            <div class="form-container">
                <form action="RegisterServlet" method="post">
                    <b>${requestScope.result}</b>
                    <input oninput="changeName()" id="name" type="text" placeholder="<fmt:message key = "register.label.name" bundle = "${lang}"/>" name="name">
                    <input oninput="changeSurname()" id="surname" type="text" placeholder="<fmt:message key = "register.label.surname" bundle = "${lang}"/>" name="surname">
                    <input oninput="changeLogin()" id="login" type="text" placeholder="<fmt:message key = "register.label.login" bundle = "${lang}"/>" name="login">
                    <input oninput="changePassword()" id="password"  type="password" placeholder="<fmt:message key = "register.label.password" bundle = "${lang}"/>" name="pass">
                    <input oninput="changeEmail()" id="email" type="text" placeholder="<fmt:message key = "register.label.email" bundle = "${lang}"/>"  name="email">
                    <input oninput="changeNumber()" id="phone" type="text" placeholder="<fmt:message key = "register.label.phone" bundle = "${lang}"/>" name="phone">
                    <input type="hidden" name="command" value="register"/>
                    <button disabled onmousemove="checkButton()" id="button" class="btn-login"><fmt:message key = "register.button.register" bundle = "${lang}"/></button>
                    <script>
                        function changeName() {
                            let x = document.getElementById("name").value;
                            const reg = new RegExp("[A-Za-zА-Яa-я]{"+x.length+"}");
                            if (x.length < 3 || x.length > 12){
                                document.getElementById("name").style.color = "inherit";
                                document.getElementById("name").style.backgroundColor = "rgba(250,233,233,0.51)";
                            }
                            else if (!reg.test(x)){
                                document.getElementById("name").style.color = "inherit";
                                document.getElementById("name").style.backgroundColor = "rgba(250,233,233,0.51)";
                            }
                            else{
                                document.getElementById("name").style.color = "inherit";
                                document.getElementById("name").style.backgroundColor = "rgba(207,243,210,0.5)";
                                return true;
                            }

                        }
                        function changeSurname() {
                            let x = document.getElementById("surname").value;
                            const reg = new RegExp("[A-Za-zА-Яa-я]{"+x.length+"}");
                            if (x.length < 3 || x.length > 12){
                                document.getElementById("surname").style.color = "inherit";
                                document.getElementById("surname").style.backgroundColor = "rgba(250,233,233,0.51)";
                            }
                            else if (!reg.test(x)){
                                document.getElementById("surname").style.color = "inherit";
                                document.getElementById("surname").style.backgroundColor = "rgba(250,233,233,0.51)";
                            }
                            else{
                                document.getElementById("surname").style.color = "inherit";
                                document.getElementById("surname").style.backgroundColor = "rgba(207,243,210,0.5)";
                                return true;
                            }

                        }
                        function  changePassword() {
                            let x = document.getElementById("password").value;
                            const reg = new RegExp("[A-Za-z0-9]{"+x.length+"}");
                            if (!reg.test(x)){
                                document.getElementById("password").style.color = "inherit";
                                document.getElementById("password").style.backgroundColor = "rgba(250,233,233,0.51)";
                            }
                            else if (x.length < 6 || x.length > 12){
                                document.getElementById("password").style.color = "inherit";
                                document.getElementById("password").style.backgroundColor = "rgba(250,233,233,0.51)";
                            }
                            else{
                                document.getElementById("password").style.color = "inherit";
                                document.getElementById("password").style.backgroundColor = "rgba(207,243,210,0.5)";
                                return true;
                            }
                        }
                        function changeLogin() {
                            let x = document.getElementById("login").value;
                            const reg = new RegExp("[A-Za-z0-9]{"+x.length+"}");
                            if (x.length < 3 || x.length > 12){
                                document.getElementById("login").style.color = "inherit";
                                document.getElementById("login").style.backgroundColor = "rgba(250,233,233,0.51)";
                            }
                            else if(!reg.test(x)){
                                document.getElementById("login").style.color = "inherit";
                                document.getElementById("login").style.backgroundColor = "rgba(250,233,233,0.51)";
                            }
                            else{
                                document.getElementById("login").style.color = "inherit";
                                document.getElementById("login").style.backgroundColor = "rgba(207,243,210,0.5)";
                                return true;
                            }

                        }
                        function changeEmail() {
                            let x = document.getElementById("email").value;
                            const reg = new RegExp("[a-zA-z]{1,15}[-_.]?[A-Za-z\\d]{1,15}@[a-z]{2,5}[.][a-z]{2,4}");
                            if (!reg.test(x)){
                                document.getElementById("email").style.color = "inherit";
                                document.getElementById("email").style.backgroundColor = "rgba(250,233,233,0.51)";
                            }
                            else{
                                document.getElementById("email").style.color = "inherit";
                                document.getElementById("email").style.backgroundColor = "rgba(207,243,210,0.5)";
                                return true;
                            }

                        }
                        function changeNumber() {
                            let x = document.getElementById("phone").value;
                            const reg = new RegExp("\\d{10}")
                            if (x.length !== 10){
                                document.getElementById("phone").style.color = "inherit";
                                document.getElementById("phone").style.backgroundColor = "rgba(250,233,233,0.51)";
                            }
                            else if(!reg.test(x)){
                                document.getElementById("phone").style.color = "inherit";
                                document.getElementById("phone").style.backgroundColor = "rgba(250,233,233,0.51)";
                            }
                            else{
                                document.getElementById("phone").style.color = "inherit";
                                document.getElementById("phone").style.backgroundColor = "rgba(207,243,210,0.5)";
                                return true;
                            }

                        }
                        function checkButton(){
                            if ( changeEmail() && changeNumber() && changeSurname() && changeName() && changePassword() && changeLogin()){
                                document.getElementById("button").disabled = false;
                            }
                        }

                    </script>
                </form>
            </div>
        </div>
    </div>
</main>
</body>
</html>
