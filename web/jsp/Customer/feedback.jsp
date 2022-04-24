<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 22.04.22
  Time: 00:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>feedback</title>
</head>
<body>
${requestScope.result}
<h4>Можете оставить отзыв о работе</h4>
<form action="RequestServlet" method="post">
    <label>rating:
        <input type="number" name="rating"><br/>
    </label>
    <label for="msg">
        Description:</label>
    <textarea id="msg" name="feedback"></textarea>
    <button type="submit">Send your feedback</button>
</form>

</body>
</html>
