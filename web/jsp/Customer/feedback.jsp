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
    <link href="${pageContext.request.contextPath}/css/feedback.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="rating">
    <b>${requestScope.result}</b>
    <h4>Можете оставить отзыв о работе</h4>
    <form action="FeedbackServlet" method="post">
        <div class="rating">
            <input type="radio" name="rating" value="5" id="5"><label for="5">☆</label>
            <input type="radio" name="rating" value="4" id="4"><label for="4">☆</label>
            <input type="radio" name="rating" value="3" id="3"><label for="3">☆</label>
            <input type="radio" name="rating" value="2" id="2"><label for="2">☆</label>
            <input type="radio" name="rating" value="1" id="1"><label for="1">☆</label>
            <textarea id="msg" name="feedback"></textarea>
            <button type="submit">Send your feedback</button>
        </div>
    </form>
</div>
</body>
</html>
