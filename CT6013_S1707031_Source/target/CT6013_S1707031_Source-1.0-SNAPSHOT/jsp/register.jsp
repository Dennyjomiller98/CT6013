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
        <title>Register</title>
    </head>
    <body>
        <%--Navbar--%>
        <div class="navbar">
            <div class="bottomnavdiv"></div>
            <div class="topnav">
                <h2>Register Page</h2>
            </div>
            <div class="topnavdiv"></div>
        </div>

        <div class="mainBody">
            <p>
                Welcome. From here, please Register an account. <br/><br/>
                If you already have an account, <strong><a href=${pageContext.request.contextPath}/servlets/redirects/Redirects?action=login>&nbsp;login here.&nbsp;</a></strong>
            </p>
            <form action="${pageContext.request.contextPath}/servlets/Register" method="POST">
                <label for="email">Email:</label>
                <input type="email" name="email" id="email" required/>
                <br/>
                <label for="pword">Password:</label>
                <input type="password" name="pword" id="pword" minlength="8" required/>
                <br/>
                <label for="role">User Relevance:</label>
                <select style="display: inline-block;" name="role" id="role">
                    <option value="head">Vice-Chancellor</option>
                    <option value="head">Department Head</option>
                    <option value="subjects">Subject or Course Leader</option>
                    <option value="figures">Finance and Enrollment</option>
                    <option value="other">Other (Restricted Access)</option>
                </select>
                <br/>
                <input type="reset" value="Clear">
                <input type="submit" value="Submit">
            </form>
        </div>
    </body>
</html>