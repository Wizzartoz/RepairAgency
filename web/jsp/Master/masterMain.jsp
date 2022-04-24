<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 20.04.22
  Time: 02:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/customerRequest.css" rel="stylesheet" type="text/css">
    <title>Master</title>
</head>
<body>
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
