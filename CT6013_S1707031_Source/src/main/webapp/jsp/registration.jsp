<%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 10/11/2020
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration Page</title>
</head>
<body>

<%--Test form--%>
<H2>Registration</H2>
<form action="/CT6013_S1707031_Source-1.0-SNAPSHOT/registration" method="POST">
    <label for="firstname">Firstname:</label>
    <input type="text" name="firstname" id="firstname"/>
    <br/>
    <label for="surname">Surname:</label>
    <input type="text" name="surname" id="surname"/>
    <br/>
    <label for="email">Email:</label>
    <input type="email" name="email" id="email"/>
    <br/>
    <label for="pword">Password</label>
    <input type="text" name="pword" id="pword"/>
    <br/>
    <input type="reset" value="Clear">
    <input type="submit" value="Submit">
</form>

</body>
</html>
