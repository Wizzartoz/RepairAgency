<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 17.04.22
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet" type="text/css">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>log in</title>
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
                <form action="LoginServlet" method="post">
                    <b>${requestScope.result}</b>
                    <input type="text" oninput="changeLogin();" id="login" placeholder="<fmt:message key = "register.label.login" bundle = "${lang}"/>" name="login">
                    <input oninput="changePassword();" id="password" type="password" placeholder="<fmt:message key = "register.label.password" bundle = "${lang}"/>"  name="pass">
                    <input  type="hidden" name="command" value="login"/>
                    <button onmousemove="checkButton()" id="button" disabled class="btn-login"><fmt:message key = "main.header.button.login" bundle = "${lang}"/></button>
                </form>
                <script>
                    function changeLogin() {
                        let x = document.getElementById("login").value;
                        if (x.length < 3 || x.length > 12){
                            document.getElementById("login").style.color = "inherit";
                            document.getElementById("login").style.backgroundColor = "rgba(250,233,233,0.51)";
                        }else{
                            document.getElementById("login").style.color = "inherit";
                            document.getElementById("login").style.backgroundColor = "rgba(207,243,210,0.5)";
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
                    function checkButton(){
                        if(changePassword() && changeLogin()){
                            document.getElementById("button").disabled = false;
                        }
                    }

                </script>
            </div>
        </div>
    </div>
</main>
</body>
</html>
