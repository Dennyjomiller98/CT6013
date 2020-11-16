<%@ page import="mongodb.CourseConnections" %>
<%@ page import="mongodbbeans.CourseBean" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 14/11/2020
  Time: 06:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en-GB">
<link href=${pageContext.request.contextPath}/css/navbar.css rel="stylesheet" type="text/css">
<link href=${pageContext.request.contextPath}/css/mainbody.css rel="stylesheet" type="text/css">
    <head>
        <title>View Courses</title>
    </head>
    <body>
        <%--Navbar--%>
        <div class="navbar">
            <div class="bottomnavdiv"></div>
            <div class="topnav">
                <h2>View Courses</h2>
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
                Please select a course from the following available. If left on "Show ALL courses", all courses will be shown below.
            </p>
            <form action="${pageContext.request.contextPath}/servlets/course/CourseEnrollmentView" method="GET">
                <label for="courseSelect">Select a Course:</label>
                <select class="select-css" style="width: 50%; display: inline-block" name="courseSelect" id="courseSelect">
                    <option value="*">Show ALL courses</option>
                        <%CourseConnections conn = new CourseConnections();
                        List<CourseBean> courseBeans = conn.retrieveAllCourses();
                        if (courseBeans != null)
                        {
                            for (CourseBean courseBean : courseBeans)
                            { %>
                                <option value="<%=courseBean.getCourseCode()%>"><%=courseBean.getCourseCode()%> : <%=courseBean.getCourseName()%></option>
                            <%}
                        }%>
                </select>
                <input type="submit" value="Search">
            </form>
            <br/>

            <%if(session.getAttribute("courseErrors") != null)
            {
            String courseErrors = session.getAttribute("courseErrors").toString(); %>
            <p class="error-div" id="errorDiv"><%=courseErrors%></p>
            <% } %>
            <%if(session.getAttribute("courseSuccess") != null)
                {
                String courseSuccess = session.getAttribute("courseSuccess").toString(); %>
            <p class="success-div" id="successDiv"><%=courseSuccess%></p>
            <% } %>

            <%--All course details--%>
            <%if(session.getAttribute("allCourses") != null){ %>
                <table id="AllCourse">
                    <caption></caption>
                    <tr>
                        <th id="allTblCourseCode">Course Code</th>
                        <th id="allTblCourseName">Name</th>
                        <th id="allTblCourseTutor">Course Leader</th>
                        <th id="allTblCourseStart">Start Date</th>
                        <th id="allTblCourseEnd">End Date</th>
                        <th id="allTblActions">Actions</th>
                    </tr>
                <%--Guaranteed to be null (out of scope here) or List<CourseBean>--%>
            	<%for (CourseBean courseBean : (List<CourseBean>) session.getAttribute("allCourses")) {
                        String courseCode = courseBean.getCourseCode();
                        String courseName = courseBean.getCourseName();
                        String courseTutor = courseBean.getCourseTutor();
                        String courseStart = courseBean.getCourseStart();
                        String courseEnd = courseBean.getCourseEnd();
                %>
                    <tr>
                        <td><%=courseCode%></td>
                        <td><%=courseName%></td>
                        <td><%=courseTutor%></td>
                        <td><%=courseStart%></td>
                        <td><%=courseEnd%></td>
                        <td>
                            <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToModuleView?courseCode=<%=courseCode%>>&nbsp;<u>Modules</u>&nbsp;</a>
                            <%if(amITeacher){%> &verbar; <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEdit?courseCode=<%=courseCode%>>&nbsp;<u>Edit</u>&nbsp;</a> <%}%>
                        </td>
                    </tr>
                <% } %>
                </table>
            <%}%>

            <%--Single Course Details--%>
            <%  String courseCode=null;
                String courseName=null;
                String courseTutor=null;
                String courseStart=null;
                String courseEnd=null;
                if (session.getAttribute("courseCode") != null)
                {
                    courseCode = session.getAttribute("courseCode").toString();
                }
                if (session.getAttribute("courseName") != null)
                {
                    courseName = session.getAttribute("courseName").toString();
                }
                if (session.getAttribute("courseTutor") != null)
                {
                    courseTutor = session.getAttribute("courseTutor").toString();
                }
                if (session.getAttribute("courseStart") != null)
                {
                    courseStart = session.getAttribute("courseStart").toString();
                }
                if (session.getAttribute("courseEnd") != null)
                {
                    courseEnd = session.getAttribute("courseEnd").toString();
                }
                if(courseCode != null){%>
            <table id="singleCourse">
                <caption></caption>
                <tr>
                    <th id="tblCourseCode">Course Code</th>
                    <th id="tblCourseName">Name</th>
                    <th id="tblCourseTutor">Course Leader</th>
                    <th id="tblCourseStart">Start Date</th>
                    <th id="tblCourseEnd">End Date</th>
                    <th id="tblActions">Actions</th>
                </tr>
                <tr>
                    <td><%=courseCode%></td>
                    <td><%=courseName%></td>
                    <td><%=courseTutor%></td>
                    <td><%=courseStart%></td>
                    <td><%=courseEnd%></td>
                    <td>
                        <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToModuleView?courseCode=<%=courseCode%>>&nbsp;<u>Modules</u>&nbsp;</a>
                        <%if(amITeacher){%> &verbar; <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEdit?courseCode=<%=courseCode%>>&nbsp;<u>Edit</u>&nbsp;</a><%}%>
                    </td>
                </tr>
            </table>
            <%}%>
        </div>
    </body>
</html>
