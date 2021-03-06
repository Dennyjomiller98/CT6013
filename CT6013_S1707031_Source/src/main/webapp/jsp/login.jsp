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
    <head>
        <title>Login</title>
        <link rel="icon" type="image/gif" href="${pageContext.request.contextPath}/imgs/favicon.ico">
    </head>
        <body>
        <jsp:include page="required.jsp"/>
        <link rel="stylesheet" href="../css/main.css">
        <div class="content">
            <%--Navbar--%>
            <div class="navbar">
                <div class="bottomnavdiv"></div>
                <div class="topnav">
                    <h2>Login Page</h2>
                </div>
                <div class="topnavdiv"></div>
            </div>

            <% String errors = (String) session.getAttribute("errors");
                if(errors != null) { %>
            <div class="alert alert-danger myalert" role="alert" id="formErrors"><%=errors%></div>
            <%}%>
            <% String success = (String) session.getAttribute("success");
                if(success != null) { %>
            <div class="alert alert-success mysuccess" role="alert" id="formSuccess"><%=success%></div>
            <%}%>

            <div class="mainBody">
                <p>
                    Welcome. From here, please login to your account. <br/><br/>
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
        </div>
    </body>
</html>
