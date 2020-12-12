<%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 13/11/2020
  Time: 00:27
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en-GB">
<link href=${pageContext.request.contextPath}/css/navbar.css rel="stylesheet" type="text/css">
<link href=${pageContext.request.contextPath}/css/mainbody.css rel="stylesheet" type="text/css">
    <head>
        <title>Teacher Portal</title>
    </head>
    <body>
        <%--Navbar--%>
        <div class="navbar">
            <div class="bottomnavdiv"></div>
            <div class="topnav">
                <h2>Teacher Portal</h2>
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
                String email = null;
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

        <%--Content--%>
        <div class="mainBody">
            <%if (amITeacher)
            {
                email = null;
                if(session.getAttribute("email") != null){
                    email = session.getAttribute("email").toString();
                }%>
            <a class="bodyA" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseAddition>&nbsp;Add Courses&nbsp;</a> <br/>
            <a class="bodyA" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEnrollmentView>&nbsp;View and Edit Courses&nbsp;</a> <br/>
            <a class="bodyA" href=${pageContext.request.contextPath}/servlets/redirects/HomeToModuleAddition>&nbsp;Add Modules&nbsp;</a> <br/>
            <a class="bodyA" href=${pageContext.request.contextPath}/servlets/redirects/HomeToModuleView>&nbsp;View and Edit Modules&nbsp;</a> <br/>
            <a class="bodyA" href=${pageContext.request.contextPath}/servlets/redirects/HomeToMarksAddition?email=<%=email%>>&nbsp;Add Assignment Marks&nbsp;</a> <br/>
            <%} else {
                if(email != null){ %>
                    <p>
                        Students do not have access to view this page. <a class="bodyA" href=${pageContext.request.contextPath}/servlets/redirects/HomeToStudentIndex>&nbsp;Return To Student Portal&nbsp;</a>
                    </p>
                <% }else{%>
                    You are not logged in. <a class="bodyA" href=${pageContext.request.contextPath}/servlets/redirects/HomeToDatabaseLogout>&nbsp;Return To Home Page&nbsp;</a>
                <%}%>
            <%}%>
        </div>
    </body>
</html>
