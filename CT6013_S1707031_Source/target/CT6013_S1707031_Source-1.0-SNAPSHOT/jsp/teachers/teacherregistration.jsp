<%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 11/11/2020
  Time: 22:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en-GB">
    <head>
        <title>Teacher Login</title>
    </head>
    <body>
        <H2>Teacher Login</H2>
        <form action="${pageContext.request.contextPath}/servlets/TeacherRegistration" method="POST">

            <label for="firstname">Firstname:</label>
            <input type="text" name="firstname" id="firstname" required/>
            <br/>
            <label for="surname">Surname:</label>
            <input type="text" name="surname" id="surname" required/>
            <br/>
            <label for="email">Email:</label>
            <input type="email" name="email" id="email" required/>
            <br/>
            <label for="dob">Date of Birth</label>
            <input type="date" name="dob" id="dob" required/>
            <br/>
            <label for="address1">Address Line 1</label>
            <input type="text" name="address1" id="address1" required/>
            <br/>
            <label for="address2">Address Line 2</label>
            <input type="text" name="address2" id="address2" required/>
            <br/>
            <label for="city">City</label>
            <input type="text" name="city" id="city" required/>
            <br/>
            <label for="postcode">Postcode</label>
            <input type="text" name="postcode" id="postcode" required/>
            <br/>
            <label for="pword">Password</label>
            <input type="text" name="pword" id="pword" required/>
            <br/>
            <input type="reset" value="Clear">
            <input type="submit" value="Submit">
        </form>

        <p class="error-div" id="errorDiv"><%session.getAttribute("registrationErrors");%></p>
    </body>
</html>
