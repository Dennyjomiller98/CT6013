<%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 15/11/2020
  Time: 07:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en-GB">
<link href=${pageContext.request.contextPath}/css/navbar.css rel="stylesheet" type="text/css">
<link href=${pageContext.request.contextPath}/css/mainbody.css rel="stylesheet" type="text/css">
    <head>
        <title>Edit Module</title>
    </head>
    <body>
        <%--Navbar--%>
        <div class="navbar">
            <div class="bottomnavdiv"></div>
            <div class="topnav">
                <h2>Edit Module</h2>
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
                Edit the required details and submit to save changes.
            </p>

            <form action="${pageContext.request.contextPath}/servlets/module/ModuleUpdate" method="POST">
                <% String moduleCode = null;
                    if (session.getAttribute("moduleCode") != null)
                    {
                        moduleCode = session.getAttribute("moduleCode").toString();
                    }%>
                <label for="moduleCode">Module Code:</label>
                <input readonly type="text" name="moduleCode" id="moduleCode" <% if(moduleCode != null){%> value="<%=moduleCode%>" <%}%> required/>
                <br/>
                <% String moduleName = null;
                    if (session.getAttribute("moduleName") != null)
                    {
                        moduleName = session.getAttribute("moduleName").toString();
                    }%>
                <label for="moduleName">Module Name:</label>
                <input type="text" name="moduleName" id="moduleName" <% if(moduleName != null){%> value="<%=moduleName%>" <%}%> required/>
                <br/>
                <% String moduleTutor = null;
                    if (session.getAttribute("moduleTutor") != null)
                    {
                        moduleTutor = session.getAttribute("moduleTutor").toString();
                    }%>
                <label for="moduleTutor">Module Tutor Email:</label>
                <input type="email" name="moduleTutor" id="moduleTutor" <% if(moduleTutor != null){%> value="<%=moduleTutor%>" <%}%> required/>
                <br/>
                <% String relatedCourse = null;
                    if (session.getAttribute("relatedCourse") != null)
                    {
                        relatedCourse = session.getAttribute("relatedCourse").toString();
                    }%>
                <label for="relatedCourse">Related Course:</label>
                <input type="text" name="relatedCourse" id="relatedCourse" <% if(relatedCourse != null){%> value="<%=relatedCourse%>" <%}%> required/>
                <br/>
                <% String isCompulsory = null;
                    if (session.getAttribute("isCompulsory") != null)
                    {
                        isCompulsory = session.getAttribute("isCompulsory").toString();
                    }%>
                <label for="isCompulsory">Compulsory Module:</label>
                <input type="checkbox" name="isCompulsory" id="isCompulsory" <% if(isCompulsory != null){%> value="<%=isCompulsory%>" <%}%> />
                <br/>
                <% String semester = null;
                    if (session.getAttribute("semester") != null)
                    {
                        semester = session.getAttribute("semester").toString();
                    }%>
                <label for="semester">Semester:</label>
                <select class="select-css" style="width: 50%; display:inline-block" name="semester" id="semester">
                    <option value="1" <% if(semester != null && semester.equals("1")){%> selected="selected" <%}%>>Semester 1</option>
                    <option value="2" <% if(semester != null && semester.equals("2")){%> selected="selected" <%}%>>Semester 2</option>
                    <option value="-1" <% if(semester != null && semester.equals("-1")){%> selected="selected" <%}%> >Semester 1 and 2</option>
                    <option value="-2" <% if(semester != null && semester.equals("-2")){%> selected="selected" <%}%> >Placement</option>
                </select>
                <br/>
                <% String moduleStart = null;
                    if (session.getAttribute("moduleStart") != null)
                    {
                        moduleStart = session.getAttribute("moduleStart").toString();
                    }%>
                <label for="moduleStart">Course Start:</label>
                <input type="date" name="moduleStart" id="moduleStart" <% if(moduleStart != null){%> value="<%=moduleStart%>" <%}%> required/>
                <br/>
                <% String moduleEnd = null;
                    if (session.getAttribute("moduleEnd") != null)
                    {
                        moduleEnd = session.getAttribute("moduleEnd").toString();
                    }%>
                <label for="moduleEnd">Course End:</label>
                <input type="date" name="moduleEnd" id="moduleEnd" <% if(moduleEnd != null){%> value="<%=moduleEnd%>" <%}%> required/>
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
                <li><a class="bodyA" href=${pageContext.request.contextPath}/servlets/redirects/HomeToModuleAddition>&nbsp;Add Modules&nbsp;</a></li>
                <li><a class="bodyA" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEnrollmentView>&nbsp;View Courses&nbsp;</a></li>
            </ul>
        </div>
    </body>
</html>
