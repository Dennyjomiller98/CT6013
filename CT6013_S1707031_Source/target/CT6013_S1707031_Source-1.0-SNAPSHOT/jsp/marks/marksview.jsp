<%@ page import="mongodbbeans.MarkBean" %>
<%@ page import="java.util.List" %>
<%@ page import="mongodbbeans.ModuleBean" %>
<%@ page import="mongodb.ModuleConnections" %>
<%@ page import="mongodb.CourseConnections" %>
<%@ page import="mongodbbeans.CourseBean" %><%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 14/11/2020
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en-GB">
<link href=${pageContext.request.contextPath}/css/navbar.css rel="stylesheet" type="text/css">
<link href=${pageContext.request.contextPath}/css/mainbody.css rel="stylesheet" type="text/css">
    <head>
        <title>Marks and Enrollment</title>
    </head>
    <body>
        <%--Navbar--%>
        <div class="navbar">
            <div class="bottomnavdiv"></div>
            <div class="topnav">
                <h2>View Marks and Enrollment</h2>
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

        <%--Main content--%>
        <div class="mainBody">
            <% if (amIEnrolled) {%>
            <%--Enrolled student, view enrolment table and table of marks--%>
            <h3>My Enrolment</h3>
            <p>
                You are currently enrolled. Please see your enrollment details below.
            </p>
            <%  String courseEnrolled;
                CourseBean courseBean = null;
                if(session.getAttribute("enrollCourse") != null){
                    courseEnrolled = session.getAttribute("enrollCourse").toString();
                    if (courseEnrolled != null)
                    {
                        CourseConnections courseConn = new CourseConnections();
                        courseBean = courseConn.retrieveSingleCourse(courseEnrolled);
                    }
                }

                if (courseBean != null)
                {%>
                    <%--Course Enrollment table--%>
                    <table id="singleCourse">
                        <caption></caption>
                        <tr>
                            <th id="tblCourseCode">Course Code</th>
                            <th id="tblCourseName">Name</th>
                            <th id="tblCourseTutor">Course Leader</th>
                            <th id="tblCourseStart">Start Date</th>
                            <th id="tblCourseEnd">End Date</th>
                        </tr>
                        <tr>
                            <td><%=courseBean.getCourseCode()%></td>
                            <td><%=courseBean.getCourseName()%></td>
                            <td><%=courseBean.getCourseTutor()%></td>
                            <td><%=courseBean.getCourseStart()%></td>
                            <td><%=courseBean.getCourseEnd()%></td>
                        </tr>
                    </table>
                <%}%>

            <p>
                Below is a list of your module choices, with details on your assignment marks. <br/>
                If a teacher has submitted results for your module, you will see them in the table below.
            </p>
            <%--Table of modules with marks--%>
            <%  List<MarkBean> myMarks = null;
                if (session.getAttribute("allMarks") != null)
                {
                	myMarks = (List<MarkBean>) session.getAttribute("allMarks");
                }
                if(myMarks != null){
                    for (MarkBean myMark : myMarks)
                    {%>
                        Your marks are: <%=myMark.getModuleCode()%> <%=myMark.getStudentEmail()%> <%=myMark.getMarkerEmail()%> <%=myMark.getFinalMark()%>
                    <%}
                }%>

            <%if(session.getAttribute("enrollModules") != null){
                String allModules = session.getAttribute("enrollModules").toString();
                String[] split = allModules.split(",");
                for (String module : split)
                {
                    ModuleConnections modConn = new ModuleConnections();
                    ModuleBean moduleBean = modConn.retrieveSingleModule(module);%>
            Module data: <%=moduleBean.getModuleCode()%> <%=moduleBean.getModuleStart()%>
            <% }
            }%>

            <%} else {%>
            <%--Not logged in--%>
            <p>You do not have access to view this page.</p>
            <%if (amITeacher){%>
            <%--Teacher access wrong page--%>
            <p>
                As a teacher, you can add marks <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToMarksAddition>&nbsp;<u>here.</u>&nbsp;</a>
            </p>
            <%} else {%>
            <p>
                Please try logging in before returning to this page.
            </p>
            <%}%>
            <%}%>
        </div>
    </body>
</html>
