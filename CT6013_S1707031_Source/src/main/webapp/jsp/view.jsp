<%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 18/04/2021
  Time: 20:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en-GB">
    <head>
        <title>View Data</title>
        <link rel="icon" type="image/gif" href="${pageContext.request.contextPath}/imgs/favicon.ico">
    </head>
    <body>
        <jsp:include page="required.jsp"/>
        <link rel="stylesheet" href="../css/main.css">
        <div class="content">
            <%  String email = null;
                if(session.getAttribute("email") != null)
                {
                    email = session.getAttribute("email").toString();
                }
            %>
            <%--Navbar--%>
            <div class="navbar">
                <div class="bottomnavdiv">
                    <strong><a href=${pageContext.request.contextPath}/servlets/etl/ETL>&nbsp;Re-Load DW&nbsp;</a></strong>
                </div>
                <div class="topnav">
                    <h2>View Data</h2>
                </div>
                <div class="topnavdiv">
                    Signed in as: <%=email%>
                    &nbsp;
                    <strong><a href=${pageContext.request.contextPath}/servlets/Logout>&nbsp;Logout&nbsp;</a></strong>
                </div>
            </div>

            <% String errors = (String) session.getAttribute("errors");
                if(errors != null) { %>
            <div class="alert alert-danger myalert" role="alert" id="formErrors"><%=errors%></div>
            <%}%>
            <% String success = (String) session.getAttribute("success");
                if(success != null) { %>
            <div class="alert alert-success mysuccess" role="alert" id="formSuccess"><%=success%></div>
            <%}%>

            <div class="mainBody">
                <%--TODO - hide data-selection and show data-retrieved after selecting, give an option to "select more information" to show this info again--%>
                <div id="data-selection">
                    <p>
                        You are successfully logged in.<br/><br/>
                    </p>

                    <form action="${pageContext.request.contextPath}/servlets/dwpreview/Load" method="POST">
                        <label for="email"></label>
                        <input style="display: none" type="text" name="email" id="email" value="<%=email%>">

                        <label for="select">Select the question you would like to view Information on:</label>
                        <select style="display: inline-block" name="select" id="select">
                            <option value="none">Select Question</option>
                            <option value="q1">Total Students on a Specific Course (Select Course Below)</option>
                            <option value="q2">Total Dropouts on a Specific Course (Select Course Below)</option>
                            <option value="q3">Average Grade on a Specific Course (Select Course Below)</option>
                            <option value="q4">Student Grades based on the Tutor Teaching (Select Tutor Below)</option>
                            <option value="q5">Number of Resits (Select Course Below)</option>
                            <option value="q6">Number of Enrollments in a Specific Year (Select Year Below)</option>
                            <option value="q7">Has COVID-19 affected our Enrollment intake?</option>
                            <option value="q8">Total Number of International Students on a Specific Course (Select Course Below)</option>
                            <option value="q9">Number of Students That Changed Courses</option>
                            <option value="q10">COVID-19 Affect on Enrollment of International Students</option>
                        </select>
                        <br/>

                        <%--TODO // hide these unless Relevant Question has been selected (JQUERY)--%>
                        <label for="courseSelect">Select the Course you would like to view Information on:</label>
                        <select style="display: inline-block" name="courseSelect" id="courseSelect">
                            <option value="all">All Courses</option>
                            <%--TODO - pull Course data in after logging in--%>
                        </select>
                        <br/>
                        <label for="tutorSelect">Select the Tutor you would like to view Information on:</label>
                        <select style="display: inline-block" name="tutorSelect" id="tutorSelect">
                            <option value="all">All Tutors</option>
                            <%--TODO - pull Tutor data in after logging in--%>
                        </select>
                        <br/>
                        <label for="yearSelect">Select the Year you would like to view Information on:</label>
                        <select style="display: inline-block" name="yearSelect" id="yearSelect">
                            <option value="all">All Courses</option>
                            <%--TODO - add Year selection (up to 2000 maybe? also hide if not selected)--%>
                        </select>
                        <br/>

                        <input type="submit" value="View">
                    </form>
                </div>

                <div id="data-retrieved">
                    <%--TODO - TABLE OF DW info here, graphs etc (maybe a popup? that way can re-select after?) --%>
                </div>
            </div>
        </div>
    </body>
</html>
