<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 21.04.22
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/customerRequest.css" rel="stylesheet" type="text/css">
    <title>requests</title>
</head>
<body>
<form action="GeneralCustomerServlet" method="post">
    <button class="btn-login" type="submit">back</button>
</form>
<h4>${requestScope.result}</h4><br>

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
            <td>
                <form method="post" action=${pageContext.request.contextPath}/RequestServlet?id=${request.requestID}>
                    <input type="submit" value="Delete"/></form>
            </td>
            <td>
                <form method="post"
                      action=${pageContext.request.contextPath}/RequestServlet?price=${request.price}&payment=${request.paymentStatus}&paymentID=${request.requestID}>
                    <input type="submit" value="Paid"/></form>
            </td>
            <td>
                <form method="post"
                      action=${pageContext.request.contextPath}/FeedbackServlet?feedbackID=${request.requestID}&comp=${request.complicationStatus}>
                    <input type="submit" value="Feedback"/></form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
