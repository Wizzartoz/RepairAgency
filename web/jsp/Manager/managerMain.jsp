<%--
  Created by IntelliJ IDEA.
  User: misha
  Date: 20.04.22
  Time: 02:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <script>
        /* When the user clicks on the button,
toggle between hiding and showing the dropdown content */
        function myFunction() {
            document.getElementById("myDropdown").classList.toggle("show");
        }

        // Close the dropdown menu if the user clicks outside of it
        window.onclick = function (event) {
            if (!event.target.matches('.dropbtn')) {
                var dropdowns = document.getElementsByClassName("dropdown-content");
                var i;
                for (i = 0; i < dropdowns.length; i++) {
                    var openDropdown = dropdowns[i];
                    if (openDropdown.classList.contains('show')) {
                        openDropdown.classList.remove('show');
                    }
                }
            }
        }
    </script>
    <script>
        /* When the user clicks on the button,
toggle between hiding and showing the dropdown content */
        function myFunction2() {
            document.getElementById("myDropdown2").classList.toggle("show");
        }

        // Close the dropdown menu if the user clicks outside of it
        window.onclick = function (event) {
            if (!event.target.matches('.dropbtn')) {
                var dropdowns = document.getElementsByClassName("dropdown-content");
                var i;
                for (i = 0; i < dropdowns.length; i++) {
                    var openDropdown = dropdowns[i];
                    if (openDropdown.classList.contains('show')) {
                        openDropdown.classList.remove('show');
                    }
                }
            }
        }
    </script>
    <script>
        /* When the user clicks on the button,
toggle between hiding and showing the dropdown content */
        function myFunction3() {
            document.getElementById("myDropdown3").classList.toggle("show");
        }

        // Close the dropdown menu if the user clicks outside of it
        window.onclick = function (event) {
            if (!event.target.matches('.dropbtn')) {
                var dropdowns = document.getElementsByClassName("dropdown-content");
                var i;
                for (i = 0; i < dropdowns.length; i++) {
                    var openDropdown = dropdowns[i];
                    if (openDropdown.classList.contains('show')) {
                        openDropdown.classList.remove('show');
                    }
                }
            }
        }
    </script>



    <div class="dropdown">
        <button onclick="myFunction()" class="dropbtn">Filter by status</button>
        <div id="myDropdown" class="dropdown-content">
            <a href="ManagerServlet?status=progress">in progress</a>
            <a href="ManagerServlet?status=done">done</a>
            <a href="ManagerServlet?status=consideration">under consideration</a>
        </div>
    </div>
    <div class="dropdown">
        <button onclick="myFunction2()" class="dropbtn">Sort</button>
        <div id="myDropdown2" class="dropdown-content">
            <a href="ManagerServlet?sort=date">sorting by date</a>
            <a href="ManagerServlet?sort=status">sorting by complication status</a>
            <a href="ManagerServlet?sort=payStatus">sorting by payment status</a>
            <a href="ManagerServlet?sort=price&order=increase">sorting by price increase</a>
            <a href="ManagerServlet?sort=price&order=decrease">sorting by decreasing price</a>
        </div>
    </div>
    <div class="dropdown">
        <button onclick="myFunction3()" class="dropbtn">Filter by master</button>
        <div id="myDropdown3" class="dropdown-content">
            <c:forEach var="user" items="${requestScope.users}">
                <a href="ManagerServlet?master=${user.login}">${user.login}</a>
            </c:forEach>
        </div>
    </div>
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
            <c:forEach var="request" items="${requestScope.table}">
                <tr>
                    <td><c:out value="${request.requestID}"/></td>
                    <td><c:out value="${request.description}"/></td>
                    <td><c:out value="${request.price}"/></td>
                    <td><c:out value="${request.paymentStatus}"/></td>
                    <td><c:out value="${request.complicationStatus}"/></td>
                    <td><c:out value="${request.date}"/></td>
                    <td>
                        <form method="post" action=${pageContext.request.contextPath}/ManagerEditServlet?req=${request}>
                            <input type="submit" value="Edit"/></form>
                    </td>
                </tr>
            </c:forEach>
        </table>

    </div>
    <div>
        <b>${requestScope.result}</b>
    </div>
</section>
</body>
</html>
