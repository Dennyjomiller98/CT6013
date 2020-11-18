<%@ page import="mongodb.ModuleConnections" %>
<%@ page import="java.util.List" %>
<%@ page import="beans.ModuleBean" %><%--
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
        <title>Module View</title>
    </head>
    <body>
        <%--Navbar--%>
        <div class="navbar">
            <div class="bottomnavdiv"></div>
            <div class="topnav">
                <h2>View Modules</h2>
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
                Please select a module from the following available. If left on "Show ALL modules", all modules will be shown below.
            </p>
            <form action="${pageContext.request.contextPath}/servlets/module/ModuleView" method="GET">
                <label for="moduleSelect">Select a Module:</label>
                <select class="select-css" style="width: 50%; display: inline-block" name="moduleSelect" id="moduleSelect">
                    <option value="*">Show ALL modules</option>
                    <%
                        ModuleConnections moduleConn = new ModuleConnections();
                        List<ModuleBean> moduleBeans = moduleConn.retrieveAllModules();
                        if (moduleBeans != null)
                        {
                            for (ModuleBean moduleBean : moduleBeans)
                            { %>
                    <option value="<%=moduleBean.getModuleCode()%>"><%=moduleBean.getModuleCode()%> : <%=moduleBean.getModuleName()%></option>
                    <%}
                    }%>
                </select>
                <input type="submit" value="Search">
            </form>
            <br/>

            <%if(session.getAttribute("moduleErrors") != null)
            {
                String moduleErrors = session.getAttribute("moduleErrors").toString(); %>
            <p class="error-div" id="errorDiv"><%=moduleErrors%></p>
            <% } %>
            <%if(session.getAttribute("moduleSuccess") != null)
            {
                String moduleSuccess = session.getAttribute("moduleSuccess").toString(); %>
            <p class="success-div" id="successDiv"><%=moduleSuccess%></p>
            <% } %>

            <%--All module details--%>
            <%if(session.getAttribute("allModules") != null){ %>
            <table id="AllModule">
                <caption></caption>
                <tr>
                    <th id="allTblModuleCode">Module Code</th>
                    <th id="allTblModuleName">Module Name</th>
                    <th id="allTblModuleTutor">Module Leader</th>
                    <th id="allTblRelatedCourse">Related Course</th>
                    <th id="allTblSemester">Semester</th>
                    <th id="allTblIsCompulsory">Is Compulsory Module</th>
                    <th id="allTblModuleStart">Start Date</th>
                    <th id="allTblModuleEnd">End Date</th>
                    <th id="allTblActions">Actions</th>
                </tr>
                <%--Guaranteed to be null (out of scope here) or List<ModuleBean>--%>
                <%for (ModuleBean moduleBean : (List<ModuleBean>) session.getAttribute("allModules")) {
                    String moduleCode = moduleBean.getModuleCode();
                    String moduleName = moduleBean.getModuleName();
                    String moduleTutor = moduleBean.getModuleTutor();
                    String relatedCourse = moduleBean.getRelatedCourse();
                    int semester = moduleBean.getSemester();
                    String isCompulsory = String.valueOf(moduleBean.isCompulsory());
                    String moduleStart = moduleBean.getModuleStart();
                    String moduleEnd = moduleBean.getModuleEnd();
                %>
                <tr>
                    <td><%=moduleCode%></td>
                    <td><%=moduleName%></td>
                    <td><%=moduleTutor%></td>
                    <td><%=relatedCourse%></td>
                    <td><%if(semester == 1){%> Semester 1
                        <% } else if(semester == 2){%> Semester 2
                        <% } else if (semester == -1){%> Semester 1 and 2
                        <% } else if (semester == -2){%> Placement <% }%>
                    </td>
                    <td><%if(isCompulsory.equalsIgnoreCase("true")){%> Yes <% }else{ %> No <% } %></td>
                    <td><%=moduleStart%></td>
                    <td><%=moduleEnd%></td>
                    <td>
                        <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEnrollmentView?courseCode=<%=relatedCourse%>>&nbsp;<u>View Course</u>&nbsp;</a>
                        <%if(amITeacher){%> &verbar; <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToModuleUpdate?moduleCode=<%=moduleCode%>>&nbsp;<u>Edit</u>&nbsp;</a> <%}%>
                    </td>
                </tr>
                <% } %>
            </table>
            <%}%>

            <%--Single Module Details--%>
            <%  String moduleCode = null;
                String moduleName =null;
                String moduleTutor =null;
                String relatedCourse =null;
                int semester = 0;
                String isCompulsory = null;
                String moduleStart = null;
                String moduleEnd = null;
                if (session.getAttribute("moduleCode") != null)
                {
                    moduleCode = session.getAttribute("moduleCode").toString();
                }
                if (session.getAttribute("moduleName") != null)
                {
                    moduleName = session.getAttribute("moduleName").toString();
                }
                if (session.getAttribute("moduleTutor") != null)
                {
                    moduleTutor = session.getAttribute("moduleTutor").toString();
                }
                if (session.getAttribute("relatedCourse") != null)
                {
                    relatedCourse = session.getAttribute("relatedCourse").toString();
                }
                if (session.getAttribute("semester") != null)
                {
                    semester = Integer.parseInt(String.valueOf(session.getAttribute("semester")));
                }
                if (session.getAttribute("isCompulsory") != null)
                {
                    isCompulsory = session.getAttribute("isCompulsory").toString();
                }
                if (session.getAttribute("moduleStart") != null)
                {
                    moduleStart = session.getAttribute("moduleStart").toString();
                }
                if (session.getAttribute("moduleEnd") != null)
                {
                    moduleEnd = session.getAttribute("moduleEnd").toString();
                }
                if(moduleCode != null){%>
            <table id="singleModule">
                <caption></caption>
                <tr>
                    <th id="tblModuleCode">Module Code</th>
                    <th id="tblModuleName">Module Name</th>
                    <th id="tblModuleTutor">Module Leader</th>
                    <th id="tblRelatedCourse">Related Course</th>
                    <th id="tblSemester">Semester</th>
                    <th id="tblIsCompulsory">Is Compulsory Module</th>
                    <th id="tblModuleStart">Start Date</th>
                    <th id="tblModuleEnd">End Date</th>
                    <th id="tblActions">Actions</th>
                </tr>
                <tr>
                    <td><%=moduleCode%></td>
                    <td><%=moduleName%></td>
                    <td><%=moduleTutor%></td>
                    <td><%=relatedCourse%></td>
                    <td><%if(semester == 1){%> Semester 1
                        <% } else if(semester == 2){%> Semester 2
                        <% } else if (semester == -1){%> Semester 1 and 2
                        <% } else if (semester == -2){%> Placement <% }%>
                    </td>
                    <td><%if(isCompulsory.equalsIgnoreCase("true")){%> Yes <% }else{ %> No <% } %></td>
                    <td><%=moduleStart%></td>
                    <td><%=moduleEnd%></td>
                    <td>
                        <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEnrollmentView?courseCode=<%=relatedCourse%>>&nbsp;<u>View Course</u>&nbsp;</a>
                        <%if(amITeacher){%> &verbar; <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToModuleUpdate?moduleCode=<%=moduleCode%>>&nbsp;<u>Edit</u>&nbsp;</a><%}%>
                    </td>
                </tr>
            </table>
            <%}%>
        </div>
    </body>
</html>