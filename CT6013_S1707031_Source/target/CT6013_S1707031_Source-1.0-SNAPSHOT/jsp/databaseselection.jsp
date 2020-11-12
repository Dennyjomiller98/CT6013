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
</head>
<body>
    <H1>Main Home Page</H1>
    <span>CT6013</span>
    <span>S1707031</span>
    <span>Denny-Jo Miller</span>
    <p>
        Welcome. From here, please select your database choice (Oracle or MongoDB). <br/>
        <strong>Note:</strong> Data is not persistent between each Database (Registering a student account in MongoDB will NOT register a student in Oracle)
    </p>

    <form style="display:inline-block" action="${pageContext.request.contextPath}/servlets/DatabaseSelection" method="POST">
        <label for="oracle"></label>
        <input type="text"  name="oracle" id="oracle" value="oracle" hidden>
        <input type="submit" value="Select Oracle">
    </form>
    <form style="display:inline-block" action="${pageContext.request.contextPath}/servlets/DatabaseSelection" method="POST">
        <label for="mongodb"></label>
        <input type="text"  name="mongodb" id="mongodb" value="mongodb" hidden>
        <input type="submit" value="Select MongoDB">
    </form>
</body>
</html>


