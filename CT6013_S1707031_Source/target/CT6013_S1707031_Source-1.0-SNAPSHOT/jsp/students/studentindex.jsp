<%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 13/11/2020
  Time: 00:25
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en-GB">
<link href=${pageContext.request.contextPath}/css/navbar.css rel="stylesheet" type="text/css">
<link href=${pageContext.request.contextPath}/css/mainbody.css rel="stylesheet" type="text/css">
<head>
    <title>Student Portal</title>
</head>

<body>
    <%--Navbar--%>
    <div class="navbar">
        <div class="bottomnavdiv"></div>
        <div class="topnav">
            <h2>Student Portal</h2>
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
            boolean amIEnrolled=false;
            if(session.getAttribute("isEnrolled") != null)
            {
                String isEnrolled = session.getAttribute("isEnrolled").toString();
                amIEnrolled = isEnrolled.equals("true");
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
        <%if(amITeacher){%>
            <p>
                Teachers should not access the student portal.<br/>
            </p>
            <ul>
                <li>
                    <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToTeacherIndex>&nbsp;<u>Return to teacher portal.</u>&nbsp;</a> <br/>
                </li>
                <li>
                    <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEnrollmentView>&nbsp;<u>View courses.</u>&nbsp;</a> <br/>
                </li>
                <li>
                    <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToModuleView>&nbsp;<u>View available modules.</u>&nbsp;</a> <br/>
                </li>
            </ul>
        <%}
        if(!amITeacher && !amIEnrolled)
        {
        	String email = null;
        	if(session.getAttribute("email") != null)
            {
            	email = session.getAttribute("email").toString();
            }
        %>
            <p>
                You are currently not enrolled. <br/>
            </p>
            <ul>
                <li>
                    <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEnrollment?studentEmail=<%=email%>>&nbsp;<u>Enroll in a course.</u>&nbsp;</a> <br/>
                </li>
                <li>
                    <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEnrollmentView>&nbsp;<u>View available courses.</u>&nbsp;</a> <br/>
                </li>
                <li>
                    <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToModuleView>&nbsp;<u>View available modules.</u>&nbsp;</a> <br/>
                </li>
            </ul>
        <%}else if(!amITeacher){
            String email = null;
            if(session.getAttribute("email") != null)
            {
                email = session.getAttribute("email").toString();
            }%>
            <p>
            </p>
            <ul>
                <li>
                    <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToMarksView?studentEmail=<%=email%>>&nbsp;<u>View your enrollment and marks.</u>&nbsp;</a> <br/>
                </li>
                <li>
                    <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEnrollmentView>&nbsp;<u>View courses.</u>&nbsp;</a> <br/>
                </li>
                <li>
                    <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToModuleView>&nbsp;<u>View available modules.</u>&nbsp;</a> <br/>
                </li>
            </ul>
        <%}%>
    </div>
</body>
</html>