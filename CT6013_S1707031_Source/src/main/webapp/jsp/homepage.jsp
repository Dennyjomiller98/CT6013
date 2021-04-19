<%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 11/11/2020
  Time: 22:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en-GB">
<link href=${pageContext.request.contextPath}/css/navbar.css rel="stylesheet" type="text/css">
<link href=${pageContext.request.contextPath}/css/mainbody.css rel="stylesheet" type="text/css">
    <head>
        <title>Home</title>
    </head>
    <body>
        <%--Navbar--%>
        <div class="navbar">
            <div class="bottomnavdiv"></div>
            <div class="topnav">
                <h2>Main Home Page</h2>
            </div>
            <div class="topnavdiv"></div>
        </div>

        <div class="mainBody">

            <span>CT6013</span>
            <span>S1707031</span>
            <span>Denny-Jo Miller</span>

            <p>
                Welcome. From here, please select your action. <br/><br/>

                <strong>Note:</strong> Data is automatically updated daily at midnight. You can also manually update data below.<br/>
            </p>

            <form style="display:inline;" action="${pageContext.request.contextPath}/servlets/etl/Extract" method="POST">
                <label for="update"></label>
                <input style="display: none" type="text" name="update" id="update" value="update" hidden>
                <input type="submit" value="Update Data">
            </form>
            <form style="display:inline;" action="${pageContext.request.contextPath}/servlets/HomepageServlet" method="POST">
                <label for="login"></label>
                <input style="display: none" type="text"  name="login" id="login" value="login" hidden>
                <input type="submit" value="Login">
            </form>
            <form style="display:inline;" action="${pageContext.request.contextPath}/servlets/HomepageServlet" method="POST">
                <label for="register"></label>
                <input style="display: none" type="text"  name="register" id="register" value="register" hidden>
                <input type="submit" value="Register">
            </form>

            <p>
                <strong>'Register' is only available for testing purposes. In production, accounts and roles would be created separately for Stakeholders and decision makers </strong><br/>
                (It is purely used to create an account with the desired conditions to test against)
            </p>
        </div>
    </body>
</html>


