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
    <head>
        <title>Home</title>
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
                <h2>Main Home Page</h2>
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
        <% String exception = (String) session.getAttribute("exception");
            if(exception != null) { %>
        <div class="alert alert-success mysuccess" role="alert" id="formSuccess"><%=exception%></div>
        <%}%>
        <% String exception2 = (String) session.getAttribute("exception2");
            if(exception2 != null) { %>
        <div class="alert alert-success mysuccess" role="alert" id="formSuccess"><%=exception2%></div>
        <%}%>

        <div class="mainBody">

            <span>CT6013</span>
            <span>S1707031</span>
            <span>Denny-Jo Miller</span>

            <p>
                Welcome. From here, please select your action. <br/><br/>

                <strong>Note:</strong> Data is automatically updated daily at midnight. You can also manually update data below.<br/>
            </p>

            <form style="display:inline;" action="${pageContext.request.contextPath}/servlets/etl/Extract" method="GET">
                <label for="update"></label>
                <input style="display: none" type="text" name="update" id="update" value="update" hidden>
                <input type="submit" value="Update DataBase Warehouse">
            </form>
            <form style="display:inline;" action="${pageContext.request.contextPath}/servlets/HomepageServlet" method="POST">
                <label for="login"></label>
                <input style="display: none" type="text"  name="login" id="login" value="login" hidden>
                <input type="submit" value="Login">
            </form>
        </div>
    </div>
    </body>
</html>


