<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 21.04.22
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    ${requestScope.table}
</table>
</body>
</html>
