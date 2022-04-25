<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 25.04.22
  Time: 03:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            ${requestScope.table}
        </table>

    </div>
    <div>
        <b>${requestScope.result}</b>
    </div>
</section>
</body>
</html>
