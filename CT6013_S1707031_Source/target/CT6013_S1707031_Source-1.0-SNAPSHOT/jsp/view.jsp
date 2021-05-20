<%@ page import="beans.dw.DWEnrollmentsBean" %>
<%@ page import="java.util.List" %>
<%@ page import="beans.operational.dimensions.DimTutorsBean" %>
<%@ page import="beans.operational.dimensions.DimCoursesBean" %><%--
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
        <link href="../js/view.js">

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
                <% String haveQueried = (String) session.getAttribute("doQuery");
                    if(haveQueried == null) { %>
                    <div id="data-selection">
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

                            <label for="courseSelect">Select the Course you would like to view Information on:</label>
                            <select style="display: inline-block" name="courseSelect" id="courseSelect">
                                <option value="all">All Courses</option>
                                <%if(session.getAttribute("allCourses") != null)
                                {
                                    List<DimCoursesBean> allCourses = (List<DimCoursesBean>) session.getAttribute("allCourses");
                                    if(allCourses != null)
                                    {
                                        for (DimCoursesBean course : allCourses)
                                        {%>
                                            <option value="<%=course.getCourseId()%>"><%=course.getCourseName()%></option>
                                        <% } %>
                                   <% } %>
                                <% } %>
                            </select>
                            <br/>
                            <label for="tutorSelect">Select the Tutor you would like to view Information on:</label>
                            <select style="display: inline-block" name="tutorSelect" id="tutorSelect">
                                <option value="all">All Tutors</option>
                                <%if(session.getAttribute("allTutors") != null)
                                {
                                    List<DimTutorsBean> allTutors = (List<DimTutorsBean>) session.getAttribute("allTutors");
                                    if(allTutors != null)
                                    {
                                        for (DimTutorsBean tutor : allTutors)
                                        { %>
                                            <option value="<%=tutor.getTutorId()%>"><%=tutor.getFirstname() + " " + tutor.getSurname()%></option>
                                        <% } %>
                                   <% } %>
                                <% } %>
                            </select>
                            <br/>
                            <label for="yearSelect">Select the Year you would like to view Information on:</label>
                            <select style="display: inline-block" name="yearSelect" id="yearSelect">
                                <option value="all">All Years</option>
                                <option value="2021">2021</option>
                                <option value="2020">2020</option>
                                <option value="2019">2019</option>
                                <option value="2018">2018</option>
                                <option value="2017">2017</option>
                                <option value="2016">2016</option>
                                <option value="2015">2015</option>
                            </select>
                            <br/>

                            <input type="submit" value="View">
                        </form>
                    </div>
                <%} else {%>
                <%--View Table section--%>
                    <div id="data-retrieved" style="display: inline-block">
                        <%if(session.getAttribute("selectedQuery").equals("q1"))
                        {

                        }%>

                        <%if(session.getAttribute("selectedQuery").equals("q2"))
                        {%>
                            <%if(session.getAttribute("enrollmentsBeans") != null)
                            {
                                List<DWEnrollmentsBean> enrollments = (List<DWEnrollmentsBean>) session.getAttribute("enrollmentsBeans");
                                if(enrollments != null && !enrollments.isEmpty()) {%>
                                    <h3>Results for: "Total Dropouts on a Specific Course"</h3>
                                    <%String year = "Unknown";
                                    String course = "Unknown";
                                    if(session.getAttribute("selectedYear") != null)
                                    {
                                        year = (String) session.getAttribute("selectedYear");
                                    }
                                    if(session.getAttribute("selectedCourse") != null)
                                    {
                                    	course = (String) session.getAttribute("selectedCourse");
                                    }
                                    %>
                                    <br/>
                                    <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, a Total of <strong><%=enrollments.size()%></strong> dropout(s) were found.</h4>
                                    <br/>
                                    <table class="table table-striped">
                                        <caption style="  display: table-caption; text-align: center;">Total Dropouts</caption>
                                        <thead>
                                        <tr>
                                            <th scope="col">#ID</th>
                                            <th scope="col">Student Number</th>
                                            <th scope="col">Student Name</th>
                                            <th scope="col">Course #ID</th>
                                            <th scope="col">Course Name</th>
                                            <th scope="col">Date of Enrollment</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <%for (DWEnrollmentsBean enrollment : enrollments)
                                        {%>
                                            <tr>
                                                <th scope="row"><%=enrollment.getId()%></th>
                                                <td><%=enrollment.getStudentId()%></td>
                                                <td><%=enrollment.getStudentFirstname() + " " + enrollment.getStudentSurname()%> </td>
                                                <td><%=enrollment.getCourseId()%></td>
                                                <td><%=enrollment.getCourseName()%></td>
                                                <td><%=enrollment.getEnrollmentDate()%></td>
                                            </tr>
                                        <%}%>
                                        </tbody>
                                    </table>
                                <%}%>
                            <%}%>
                        <%}%>

                        <%if(session.getAttribute("selectedQuery").equals("q3"))
                        {

                        }%>
                        <%if(session.getAttribute("selectedQuery").equals("q4"))
                        {

                        }%>
                        <%if(session.getAttribute("selectedQuery").equals("q5"))
                        {

                        }%>
                        <%if(session.getAttribute("selectedQuery").equals("q6"))
                        {

                        }%>
                        <%if(session.getAttribute("selectedQuery").equals("q7"))
                        {

                        }%>
                        <%if(session.getAttribute("selectedQuery").equals("q8"))
                        {

                        }%>
                        <%if(session.getAttribute("selectedQuery").equals("q9"))
                        {

                        }%>
                        <%if(session.getAttribute("selectedQuery").equals("q10"))
                        {

                        }%>

                        <%--Button to hide table, show form and try again--%>
                        <button type="button" class="btn btn-primary newSearch">New Search</button>
                    </div>
                <%}%>
            </div>
        </div>
    </body>
</html>
