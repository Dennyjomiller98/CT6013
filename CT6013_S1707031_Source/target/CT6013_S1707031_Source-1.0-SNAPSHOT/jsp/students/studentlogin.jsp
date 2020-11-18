<%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 11/11/2020
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en-GB">
<link href=${pageContext.request.contextPath}/css/navbar.css rel="stylesheet" type="text/css">
<link href=${pageContext.request.contextPath}/css/mainbody.css rel="stylesheet" type="text/css">
    <head>
        <title>Student Login</title>
    </head>
    <body>
        <%--Navbar--%>
        <div class="navbar">
            <div class="bottomnavdiv"></div>
            <div class="topnav">
                <h2>Student Login</h2>
                <%--Only show Profile/Logout if user is already logged in--%>
            </div>
            <% String firstname = null;
                if(session.getAttribute("firstname") != null){
                    firstname = session.getAttribute("firstname").toString();
                }
                boolean amITeacher = false;
                if(session.getAttribute("isTeacher") != null)
                {
                    String isTeacher = session.getAttribute("isTeacher").toString();
                    amITeacher = isTeacher.equals("true");
                }
                String email=null;
                if(session.getAttribute("email") != null)
                {
                    email = session.getAttribute("email").toString();
                }
                if(firstname != null){%>
            <div class="topnavdiv">
                <strong>Logged in as: <%=firstname%></strong><br/>
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

        <%--Login Form--%>
        <div class="mainBody">
            <%if(email != null){
                if(amITeacher){ %>
                    You are already logged in.  <a class="bodyA" href=${pageContext.request.contextPath}/servlets/redirects/HomeToTeacherIndex>&nbsp;Return to Teacher Portal&nbsp;</a>
                <% } else { %>
                    You are already logged in.  <a class="bodyA" href=${pageContext.request.contextPath}/servlets/redirects/HomeToStudentIndex>&nbsp;Return to Student Portal&nbsp;</a>
                <% } %>
            <% } else { %>
                <form action="${pageContext.request.contextPath}/servlets/StudentLogin" method="GET">
                    <label for="email">Email:</label>
                    <input type="text" name="email" id="email" required/>
                    <br/>
                    <label for="pword">Password:</label>
                    <input type="password" name="pword" id="pword" minlength="8" required/>
                    <br/>
                    <input type="reset" value="Clear">
                    <input type="submit" value="Submit">
                </form>
                <br/>

                <% String errors = (String) session.getAttribute("loginErrors");
                    if(errors != null){%>
                <p class="error-div" id="errorDiv"><%=errors%></p>
                <%}%>
            <% } %>
        </div>
    </body>
</html>
