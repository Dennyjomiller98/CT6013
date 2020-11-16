<%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 11/11/2020
  Time: 22:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en-GB">
<link href=${pageContext.request.contextPath}/css/navbar.css rel="stylesheet" type="text/css">
<link href=${pageContext.request.contextPath}/css/mainbody.css rel="stylesheet" type="text/css">
    <head>
        <title>MongoDB Home</title>
    </head>
    <body>
        <%--Navbar--%>
        <div class="navbar">
            <div class="bottomnavdiv">
                <a style="display: inline; float:left; margin-left:20px; margin-top:10px; border-radius: 5px; background-color: gainsboro;"  href=${pageContext.request.contextPath}/servlets/redirects/HomeToDatabaseLogout>&nbsp;DB Logout&nbsp;</a>
            </div>
            <div class="topnav">
                <h2>MongoDB Home</h2>
                <%--Only show Profile/Logout if user is already logged in--%>
            </div>
            <% String firstname = null;
                if(session.getAttribute("firstname") != null){
                    firstname = session.getAttribute("firstname").toString();
                }
                String email = null;
                if(session.getAttribute("email") != null)
                {
                	email = session.getAttribute("email").toString();
                }
                boolean amITeacher = false;
                if(session.getAttribute("isTeacher") != null)
                {
                    String isTeacher = session.getAttribute("isTeacher").toString();
                    amITeacher = isTeacher.equals("true");
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

        <div class="mainBody">
            <%--If logged in--%>
            <%  if(firstname != null){%>
                <p>
                    You are already logged in as <%=firstname%>. <br/>
                    <%if(amITeacher){%>
                    To edit your profile, please click <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToTeacherProfile>&nbsp;<u>here.</u>&nbsp;</a><br/>
                    <% } else { %>
                    To edit your profile, please click <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToStudentProfile>&nbsp;<u>here.</u>&nbsp;</a><br/>
                    For Course Enrollment, please click <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEnrollment?studentEmail=<%=email%>>&nbsp;<u>here.</u>&nbsp;</a><br/>
                    To view Course Details, including Assignment Marks, please click <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEnrollmentView>&nbsp;<u>here.</u>&nbsp;</a><br/>
                    <%}%>
                </p>
            <%} else {%>
                <%--not logged in--%>
                <p>
                    Welcome. To progress further, you must first be logged in. Please select the appropriate action. <br/><br/>
                    To register as a student, please click <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToStudentRegistration>&nbsp;<u>here.</u>&nbsp;</a> <br/>
                    To login as a student, please click <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToStudentLogin>&nbsp;<u>here.</u>&nbsp;</a> <br/>
                    To login as a Teacher, please click <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToTeacherLogin>&nbsp;<u>here.</u>&nbsp;</a> <br/><br/>
                    To view available Courses, please click <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEnrollmentView>&nbsp;<u>here.</u>&nbsp;</a> <br/>
                    To view available Modules, please click <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToModuleView>&nbsp;<u>here.</u>&nbsp;</a> <br/><br/>
                    <strong>Please Note:</strong> Teacher Registration can only be performed by teachers. As this project is not in production, please view the related README to gain Teacher access in order to test Teacher features.
                </p>
            <%}%>
        </div>
    </body>
</html>