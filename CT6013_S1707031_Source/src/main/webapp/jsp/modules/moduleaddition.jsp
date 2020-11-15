<%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 14/11/2020
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en-GB">
<link href=${pageContext.request.contextPath}/css/navbar.css rel="stylesheet" type="text/css">
<link href=${pageContext.request.contextPath}/css/mainbody.css rel="stylesheet" type="text/css">
    <head>
        <title>Add Module</title>
    </head>
    <body>
        <%--Navbar--%>
        <div class="navbar">
            <div class="bottomnavdiv"></div>
            <div class="topnav">
                <h2>Add Module</h2>
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

        <%--Main content--%>
        <div class="mainBody">
            <p>
                To add a module, please fill in the following form.
            </p>

            <form action="${pageContext.request.contextPath}/servlets/module/ModuleAddition" method="POST">
                <label for="moduleCode">Module Code:</label>
                <input type="text" name="moduleCode" id="moduleCode" required/>
                <br/>
                <label for="moduleName">Module Name:</label>
                <input type="text" name="moduleName" id="moduleName" required/>
                <br/>
                <label for="moduleTutor">Module Tutor Email:</label>
                <input type="email" name="moduleTutor" id="moduleTutor" required/>
                <br/>
                <label for="relatedCourse">Related Course:</label>
                <input type="text" name="relatedCourse" id="relatedCourse" required/>
                <br/>
                <label for="isCompulsory">Compulsory Module:</label>
                <input type="checkbox" name="isCompulsory" id="isCompulsory" />
                <br/>
                <label for="semester">Semester:</label>
                <select class="select-css" style="width: 50%; display: inline-block" name="semester" id="semester">
                    <option value="1">Semester 1</option>
                    <option value="2">Semester 2</option>
                    <option value="-1">Semester 1 and 2</option>
                    <option value="-2">Placement</option>
                </select>
                <br/>
                <label for="moduleStart">Course Start:</label>
                <input type="date" name="moduleStart" id="moduleStart" required/>
                <br/>
                <label for="moduleEnd">Course End:</label>
                <input type="date" name="moduleEnd" id="moduleEnd" required/>
                <br/>
                <input type="reset" value="Clear">
                <input type="submit" value="Submit">
            </form>

            <% String success = (String) session.getAttribute("moduleSuccess");
                if(success != null){%>
            <p class="success-div" id="successDiv"><%=success%></p>
            <%}%>

            <% String errors = (String) session.getAttribute("moduleErrors");
                if(errors != null){%>
            <p class="error-div" id="errorDiv"><%=errors%></p>
            <%}%>

            <ul>
                <li><a class="bodyA" href=${pageContext.request.contextPath}/servlets/redirects/HomeToModuleView>&nbsp;View Modules&nbsp;</a></li>
                <li><a class="bodyA" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseAddition>&nbsp;Add Courses&nbsp;</a></li>
                <li><a class="bodyA" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEnrollmentView>&nbsp;View Courses&nbsp;</a></li>
            </ul>
        </div>
    </body>
</html>
