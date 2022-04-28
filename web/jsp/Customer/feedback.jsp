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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous"/>
    <link href="${pageContext.request.contextPath}/css/feedback.css" rel="stylesheet" type="text/css">
</head>
<body>
<section>
    <div class="container-xxl">
        <div class="raw"></div>
        <div class="raw">
            <div class="col-6 align-items-center">
                <div class="rating">
                    <form action="FeedbackServlet" method="post">
                        <div class="rating">
                            <input type="radio" name="rating" value="5" id="5"><label for="5">☆</label>
                            <input type="radio" name="rating" value="4" id="4"><label for="4">☆</label>
                            <input type="radio" name="rating" value="3" id="3"><label for="3">☆</label>
                            <input type="radio" name="rating" value="2" id="2"><label for="2">☆</label>
                            <input type="radio" name="rating" value="1" id="1"><label for="1">☆</label>
                        </div>
                        <div>
                            <textarea style="height: 200px;width: 800px" id="msg" name="feedback"></textarea>
                        </div>
                        <div class="d-grid gap-2">
                            <button class="btn btn-primary" type="submit">Send</button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-6">
                здесь вы можете поблагодорить мастера<br>
                за его работу и оставить отзыв
            </div>
        </div>
    </div>
</section>
</body>
</html>
