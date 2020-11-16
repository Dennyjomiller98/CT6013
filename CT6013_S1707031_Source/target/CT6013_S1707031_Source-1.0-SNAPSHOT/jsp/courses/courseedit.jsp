<%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 14/11/2020
  Time: 18:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en-GB">
<link href=${pageContext.request.contextPath}/css/navbar.css rel="stylesheet" type="text/css">
<link href=${pageContext.request.contextPath}/css/mainbody.css rel="stylesheet" type="text/css">
    <head>
        <title>Edit Course</title>
    </head>
    <body>
        <%--Navbar--%>
        <div class="navbar">
            <div class="bottomnavdiv"></div>
            <div class="topnav">
                <h2>Add Course</h2>
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

        <%--Main Content--%>
        <div class="mainBody">
            <p>
                Edit the required details and submit to save changes.
            </p>
            <form action="${pageContext.request.contextPath}/servlets/course/CourseUpdate" method="POST">
                <% String courseCode = null;
                    if (session.getAttribute("courseCode") != null)
                    {
                        courseCode = session.getAttribute("courseCode").toString();
                    }%>
                <label for="courseCode">Course Code:</label>
                <input readonly type="text" name="courseCode" id="courseCode" <% if(courseCode != null){%> value="<%=courseCode%>" <%}%> required/>
                <br/>
                <% String courseName = null;
                    if (session.getAttribute("courseName") != null)
                    {
                        courseName = session.getAttribute("courseName").toString();
                    }%>
                <label for="courseName">Course Name:</label>
                <input type="text" name="courseName" id="courseName" <% if(courseName != null){%> value="<%=courseName%>" <%}%> required/>
                <br/>
                <% String courseTutor = null;
                    if (session.getAttribute("courseTutor") != null)
                    {
                        courseTutor = session.getAttribute("courseTutor").toString();
                    }%>
                <label for="courseTutor">Course Tutor Email:</label>
                <input type="email" name="courseTutor" id="courseTutor" <% if(courseTutor != null){%> value="<%=courseTutor%>" <%}%> required/>
                <br/>
                <% String courseStart = null;
                    if (session.getAttribute("courseStart") != null)
                    {
                        courseStart = session.getAttribute("courseStart").toString();
                    }%>
                <label for="courseStart">Course Start Date:</label>
                <input type="date" name="courseStart" id="courseStart" <% if(courseStart != null){%> value="<%=courseStart%>" <%}%> required/>
                <br/>
                <% String courseEnd = null;
                    if (session.getAttribute("courseEnd") != null)
                    {
                        courseEnd = session.getAttribute("courseEnd").toString();
                    }%>
                <label for="courseEnd">Course End Date</label>
                <input type="date" name="courseEnd" id="courseEnd" <% if(courseEnd != null){%> value="<%=courseEnd%>" <%}%> required/>
                <br/>
                <input type="reset" value="Clear">
                <input type="submit" value="Submit">
            </form>
            <br/>

            <% String success = (String) session.getAttribute("courseSuccess");
                if(success != null){%>
            <p class="success-div" id="successDiv"><%=success%></p>
            <%}%>

            <% String errors = (String) session.getAttribute("courseErrors");
                if(errors != null){%>
            <p class="error-div" id="errorDiv"><%=errors%></p>
            <%}%>

            <ul>
                <li><a class="bodyA" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEnrollmentView>&nbsp;View Courses&nbsp;</a></li>
                <li><a class="bodyA" href=${pageContext.request.contextPath}/servlets/redirects/HomeToModuleAddition>&nbsp;Add Modules&nbsp;</a></li>
                <li><a class="bodyA" href=${pageContext.request.contextPath}/servlets/redirects/HomeToModuleView>&nbsp;View Modules&nbsp;</a></li>
            </ul>
        </div>
    </body>
</html>
