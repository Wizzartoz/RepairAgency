<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 21.04.22
  Time: 03:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
          crossorigin="anonymous">
    <title>Error</title>
</head>
<body>
<section>
    <div class="container container-xxl">
        <div class="row">
            <div class="position-absolute top-50 start-50 translate-middle-y">
            <h1>OOOOPS,WHATS'S WRONG!</h1>
                <form class="align-items-center" method="get" action="LoginServlet">
                    <input value="Back to home" type="submit" class="btn btn-warning mx-auto">
                </form>
            </div>

        </div>
    </div>
</section>
</body>
</html>
