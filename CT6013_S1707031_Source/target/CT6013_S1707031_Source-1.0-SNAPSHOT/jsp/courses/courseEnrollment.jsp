<%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 13/11/2020
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en-GB">
<link href=${pageContext.request.contextPath}/css/navbar.css rel="stylesheet" type="text/css">
<link href=${pageContext.request.contextPath}/css/mainbody.css rel="stylesheet" type="text/css">
    <head>
        <title>Course Enrollment</title>
    </head>
    <body>
        <%--Navbar--%>
        <div class="navbar">
            <div class="bottomnavdiv"></div>
            <div class="topnav">
                <h2>Enroll on a Course</h2>
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

        <div class="mainBody">
            <%--Logged in Student--%>
            <%String email = (String) session.getAttribute("email");
            if(!amITeacher && !amIEnrolled && email != null){%>
            On this page, you will be able to enroll on a Course. <br/>
            More information on available courses can be found <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEnrollmentView>&nbsp;<u>here.</u>&nbsp;</a> <br/>

            After selecting an option from the Course Selector, you will be prompted to also select modules, totalling to 120 CATS. <br/>
            <form action="${pageContext.request.contextPath}/servlets/course/CourseEnrollment" method="GET">
                <%--TODO - student enroll on course--%>
            </form>

            <%--Student already enrolled--%>
            <%} else if(!amITeacher && amIEnrolled && email != null){%>
            You have already enrolled.<br/>
            To view course, <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEnrollmentView>&nbsp;<u>click here.</u>&nbsp;</a> <br/>
            To return to the Student Portal, <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToStudentIndex>&nbsp;<u>click here.</u>&nbsp;</a>
            <%--Teacher--%>
            <%} else if(amITeacher && email != null) {%>
            Teachers are not able to enroll on a course. <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToTeacherIndex>&nbsp;<u>Return to Teacher Portal.</u>&nbsp;</a>
            <%} else {%>
                You are currently not logged in.<br/>
                To log in, please click <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToStudentLogin>&nbsp;<u>here.</u>&nbsp;</a> <br/>
                To view courses available for enrolling, <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEnrollmentView>&nbsp;<u>click here.</u>&nbsp;</a>
            <%}%>
        </div>
    </body>
</html>