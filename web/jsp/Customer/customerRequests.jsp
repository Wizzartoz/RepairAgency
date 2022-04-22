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
<h4>${requestScope.error}</h4><br>
<h4>${requestScope.paid}</h4>
<h4>${requestScope.resultFeedback}</h4>
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
