<%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 17/04/2021
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en-GB">
    <head>
        <title>Authentication</title>
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
                <h2>2FA Authentication</h2>
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

        <%  String email = null;
            if(session.getAttribute("email") != null)
            {
                email = session.getAttribute("email").toString();
            }
            String pinError = null;
            if(session.getAttribute("errors") != null)
            {
                pinError = session.getAttribute("errors").toString();
            }
        %>
        <div class="mainBody">
            <p>
                You should have just received an email containing your secure authentication pin. Enter the code below to gain access. <br/><br/>
                If you do not receive your authentication pin within a few minutes, <strong><a href=${pageContext.request.contextPath}/servlets/Authentication2Factor?user=<%=email%>>&nbsp;request a new pin here.&nbsp;</a></strong>
            </p>

            <form action="${pageContext.request.contextPath}/servlets/AuthenticateLogin" method="GET">
                <label for="email"></label>
                <input type="text" name="email" id="email" style="display: none" value="<%=email%>"/>
                <label for="pin">Authentication Pin:</label>
                <input type="text" name="pin" id="pin" required minlength="4" maxlength="4"/>
                <br/>
                <% if(pinError != null)
                { %>
                <span style="display: block; text-align: center"><strong><%=pinError%></strong></span>
                <% } %>
                <input type="reset" value="Clear">
                <input type="submit" value="Submit">
            </form>
        </div>
    </div>
    </body>
</html>

