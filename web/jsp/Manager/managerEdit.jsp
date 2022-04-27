<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 26.04.22
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>edit</title>
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
                <tr>
                    <td><c:out value="${requestScope.request.requestID}"/></td>
                    <td><c:out value="${requestScope.request.description}"/></td>
                    <td><c:out value="${requestScope.request.price}"/></td>
                    <td>
                    <select name="payment">
                        <c:if test="${request.paymentStatus.equals('unpaid')}">
                        <option value="t"><c:out value="unpaid"/></option>
                        <option value="t"><c:out value="waiting for payment"/></option>
                        </c:if>
                        <c:if test="${request.paymentStatus.equals('waiting for payment')}">
                            <option value="t"><c:out value="waitig for payment"/></option>
                        </c:if>
                        <c:if test="${request.paymentStatus.equals('paid')}">
                            <option value="t"><c:out value="paid"/></option>
                        </c:if>
                    </select>
                    </td>
                    <td>
                        <select name="payment">
                            <c:if test="${request.complicationStatus.equals('under consideration')}">
                                <option value="t"><c:out value="consideration"/></option>
                            </c:if>
                            <c:if test="${!request.complicationStatus.equals('consideration')}">
                                <option value="t"><c:out value="${request.complicationStatus}"/></option>
                            </c:if>

                        </select>
                    </td>
                    <td><c:out value="${requestScope.request.date}"/></td>
                    <td>
                        <form method="post" action=${pageContext.request.contextPath}/ManagerEditServlet?req=>
                            <input type="submit" value="Edit"/></form>
                    </td>
                </tr>
        </table>

    </div>
    <div>
        <b>${requestScope.result}</b>
    </div>
</section>

</body>
</html>
