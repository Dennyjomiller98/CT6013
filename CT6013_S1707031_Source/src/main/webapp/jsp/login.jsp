<%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 17/04/2021
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en-GB">
<link href=${pageContext.request.contextPath}/css/navbar.css rel="stylesheet" type="text/css">
<link href=${pageContext.request.contextPath}/css/mainbody.css rel="stylesheet" type="text/css">
    <head>
        <title>Login</title>
    </head>
        <body>
        <%--Navbar--%>
        <div class="navbar">
            <div class="bottomnavdiv"></div>
            <div class="topnav">
                <h2>Login Page</h2>
            </div>
            <div class="topnavdiv"></div>
        </div>

        <div class="mainBody">
            <p>
                Welcome. From here, please login to your account. <br/><br/>
                If you do not have an account, <strong><a href=${pageContext.request.contextPath}/servlets/redirects/Redirects?action=register>&nbsp;register an account here.&nbsp;</a></strong> (This is only available for testing procedures)
            </p>
            <form action="${pageContext.request.contextPath}/servlets/Login" method="GET">
                <label for="email">Email:</label>
                <input type="email" name="email" id="email" required/>
                <br/>
                <label for="pword">Password:</label>
                <input type="password" name="pword" id="pword" minlength="8" required/>
                <br/>
                <input type="reset" value="Clear">
                <input type="submit" value="Submit">
            </form>
        </div>
    </body>
</html>
