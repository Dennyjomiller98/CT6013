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
<link href=${pageContext.request.contextPath}/css/navbar.css rel="stylesheet" type="text/css">
<link href=${pageContext.request.contextPath}/css/mainbody.css rel="stylesheet" type="text/css">
    <head>
        <title>Teacher Registration</title>
    </head>
    <body>
        <%--Navbar--%>
        <div class="navbar">
            <div class="bottomnavdiv"></div>
            <div class="topnav">
                <h2>Teacher Registration</h2>
                <%--Only show Profile/Logout if user is already logged in--%>
            </div>
            <% String firstname = (String) session.getAttribute("firstname");
                boolean amITeacher = false;
                if(session.getAttribute("isTeacher") != null)
                {
                    String isTeacher = session.getAttribute("isTeacher").toString();
                    amITeacher = isTeacher.equals("true");
                }
                boolean amIEnrolled=false;
                if(session.getAttribute("isEnrolled") != null)
                {
                    String isEnrolled = session.getAttribute("isEnrolled").toString();
                    amIEnrolled = isEnrolled.equals("true");
                }
                if(firstname != null){%>
            <div class="topnavdiv">
                Logged in as: <%=firstname%><br/>
                <% if(amITeacher)
                {%>
                <a style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToTeacherProfile>&nbsp;Profile&nbsp;</a>
                &verbar;
                <a style="display: inline" href=${pageContext.request.contextPath}/servlets/TeacherLogout>&nbsp;Logout&nbsp;</a>
                <br/>
                <a style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToTeacherRegistration>&nbsp;Register Teacher&nbsp;</a>
                <% } else { %>
                <a style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToStudentProfile>&nbsp;Profile&nbsp;</a>
                &verbar;
                <a style="display: inline" href=${pageContext.request.contextPath}/servlets/StudentLogout>&nbsp;Logout&nbsp;</a>
                <%}%>
            </div>
            <% }else{ %>
            <div class="topnavdiv">
                <a style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToStudentLogin>&nbsp;Student Login&nbsp;</a>
                &verbar;
                <a style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToStudentRegistration>&nbsp;Register&nbsp;</a>
                <br/>
                <a style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToTeacherLogin>&nbsp;Teacher Login&nbsp;</a>
            </div>
            <% } %>
        </div>

        <%--Main Content--%>
        <div class="mainBody">
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
        </div>
        <p class="error-div" id="errorDiv"><%session.getAttribute("registrationErrors");%></p>
    </body>
</html>
