<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 25.04.22
  Time: 03:30
  To change this template use File | Settings | File Templates.
--%>http://java.sun.com/jspa/jstl/core
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/css/customerRequest.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/maine_page.css" rel="stylesheet" type="text/css">
</head>
<body>
<nav class="navbar background">
</nav>
<section class="firstsection">
    <div class="form_text">
        <table>
            <tr>
                <th>id</th>
                <th>description</th>
                <th>price</th>
                <th>payment status</th>
                <th>complication status</th>
                <th>date</th>
            </tr>
            <c:forEach var="request" items="${requestScope.table}">
                <tr>
                    <td><c:out value="${request.requestID}"/></td>
                    <td><c:out value="${request.description}"/></td>
                    <td><c:out value="${request.price}"/></td>
                    <td><c:out value="${request.paymentStatus}"/></td>
                    <td><c:out value="${request.complicationStatus}"/></td>
                    <td><c:out value="${request.date}"/></td>
                    <td><form method="post" action=${pageContext.request.contextPath}/MasterServlet?id=${request.requestID}><input type="submit" value="Take"/></form></td>
                    <td><form method="post" action=${pageContext.request.contextPath}/MasterServlet?doneID=${request.requestID}><input type="submit" value="Done"/></form></td>
                </tr>
            </c:forEach>
        </table>

    </div>
    <div>
        <b>${requestScope.result}</b>
    </div>
</section>
</body>
</html>
