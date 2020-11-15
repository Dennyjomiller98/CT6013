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
                Welcome. From here, please select your database choice (Oracle or MongoDB). <br/>
                <strong>Note:</strong> Data is not persistent between each Database (Registering a student account in MongoDB will NOT register a student in Oracle) <br/>
                To swap databases, you must first log out of the Student/Teacher account, taking you back to the relevant database homepage (OracleHomePage/MongoHomePage). In the top left of the page you can exit the database. <br>
            </p>

            <form style="display:inline-block; float: left" action="${pageContext.request.contextPath}/servlets/DatabaseSelection" method="POST">
                <label for="oracle"></label>
                <input style="display: none" type="text" name="oracle" id="oracle" value="oracle" hidden>
                <input type="submit" value="Select Oracle">
            </form>
            <form style="display:inline-block; float: right" action="${pageContext.request.contextPath}/servlets/DatabaseSelection" method="POST">
                <label for="mongodb"></label>
                <input style="display: none" type="text"  name="mongodb" id="mongodb" value="mongodb" hidden>
                <input type="submit" value="Select MongoDB">
            </form>
        </div>
    </body>
</html>


