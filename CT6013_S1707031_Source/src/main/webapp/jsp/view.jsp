<%@ page import="beans.dw.DWEnrollmentsBean" %>
<%@ page import="java.util.List" %>
<%@ page import="beans.operational.dimensions.DimTutorsBean" %>
<%@ page import="beans.operational.dimensions.DimCoursesBean" %>
<%@ page import="beans.dw.DWAssignmentsBean" %><%--
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
        <script type="text/javascript" src="../js/view.js"></script>

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

            <%String haveQueried = (String) session.getAttribute("doQuery");%>
            <div class="mainBody">
                <div id="data-selection" <%if(haveQueried != null){%>style="display: none"<%}%> style="display: inline-block">
                    <form action="${pageContext.request.contextPath}/servlets/dwpreview/Load" method="POST">
                        <label for="email"></label>
                        <input style="display: none" type="text" name="email" id="email" value="<%=email%>">

                        <label for="select">Select the question you would like to view Information on:</label>
                        <select style="display: inline-block" name="select" id="select">
                            <option value="none">Select Question</option>
                            <option value="q1">1. Total Current Students on a Specific Course (Select Course Below)</option>
                            <option value="q2">2. Total Dropouts on a Specific Course (Select Course Below)</option>
                            <option value="q3">3. Average Grade on a Specific Course (Select Course Below)</option>
                            <option value="q4">4. Student Grades based on the Tutor Teaching (Select Tutor Below)</option>
                            <option value="q5">5. Number of Resits (Select Course Below)</option>
                            <option value="q6">6. Number of Enrollments in a Specific Year (Select Year Below)</option>
                            <option value="q7">7. Has COVID-19 affected our Enrollment intake?</option>
                            <option value="q8">8. Total Number of International Students on a Specific Course (Select Course Below)</option>
                            <option value="q9">9. Number of Students That Changed Courses</option>
                            <option value="q10">10. COVID-19 Affect on Enrollment of International Students</option>
                        </select>
                        <br/>

                        <label for="courseSelect" class="courseSelect" style="display: none">Select the Course you would like to view Information on:</label>
                        <select style="display: none" name="courseSelect" id="courseSelect" class="courseSelect">
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
                        <label for="tutorSelect" class="tutorSelect" style="display: none">Select the Tutor you would like to view Information on:</label>
                        <select style="display: none" name="tutorSelect" id="tutorSelect" class="tutorSelect">
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
                        <label for="yearSelect" class="yearSelect" style="display: none">Select the Year you would like to view Information on:</label>
                        <select style="display: none" name="yearSelect" id="yearSelect" class="yearSelect">
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

                        <button type="submit" class="btn btn-primary submitButton" style="display: none">View</button>
                    </form>
                </div>

                <%if(haveQueried != null) {%>
                <%--View Table section--%>
                    <div id="data-retrieved" style="display: inline-block">
                        <%--Q1: Total Students--%>
                        <%if(session.getAttribute("selectedQuery").equals("q1"))
                        {%>
                            <h3>Results for: "Total Current Students Enrolled on a Specific Course"</h3>
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
                            <%if(session.getAttribute("enrollmentsBeans") != null)
                            {%>
                            <%List<DWEnrollmentsBean> enrollments = (List<DWEnrollmentsBean>) session.getAttribute("enrollmentsBeans");
                                if(enrollments != null && !enrollments.isEmpty()) {%>
                            <br/>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, a Total of <strong><%=enrollments.size()%></strong> Current Student Enrollment(s) were found.</h4>
                            <br/>
                            <table class="table table-striped">
                                <caption style="  display: table-caption; text-align: center;">Total Current Student Enrollments</caption>
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
                            <%}else{ %>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, no Current Student Enrollment(s) were found.</h4>
                            <%}%>
                        <%}%>

                        <%--Q2: Total Dropouts--%>
                        <%if(session.getAttribute("selectedQuery").equals("q2"))
                        {%>
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
                            <%if(session.getAttribute("enrollmentsBeans") != null)
                            {%>
                                <%List<DWEnrollmentsBean> enrollments = (List<DWEnrollmentsBean>) session.getAttribute("enrollmentsBeans");
                                if(enrollments != null && !enrollments.isEmpty()) {%>
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
                            <%}else{ %>
                                <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, no dropout(s) were found.</h4>
                            <%}%>
                        <%}%>

                        <%--Q3: Average Grades--%>
                        <%if(session.getAttribute("selectedQuery").equals("q3"))
                        {%>
                            <h3>Results for: "Average Grade on a Specific Course"</h3>
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

                            <%if(session.getAttribute("assignmentsBeans") != null)
                            {%>
                            <%List<DWAssignmentsBean> assignments = (List<DWAssignmentsBean>) session.getAttribute("assignmentsBeans");
                                if(assignments != null && !assignments.isEmpty()) {%>
                            <br/>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, a Total of <strong><%=assignments.size()%></strong> results were found.</h4>
                            <br/>
                            <table class="table table-striped">
                                <caption style="  display: table-caption; text-align: center;">Average Marks</caption>
                                <thead>
                                <tr>
                                    <th scope="col">#ID</th>
                                    <th scope="col">Student Number</th>
                                    <th scope="col">Student Name</th>
                                    <th scope="col">Course #ID</th>
                                    <th scope="col">Course Name</th>
                                    <th scope="col">Module #ID</th>
                                    <th scope="col">Semester</th>
                                    <th scope="col">Grade</th>
                                    <th scope="col">Did Resit</th>
                                    <th scope="col">Resist Grade</th>
                                    <th scope="col">Year</th>
                                </tr>
                                </thead>
                                <tbody>
                                <%for (DWAssignmentsBean assignment : assignments)
                                {%>
                                <tr>
                                    <th scope="row"><%=assignment.getAssignmentId()%></th>
                                    <td><%=assignment.getStudentId()%></td>
                                    <td><%=assignment.getStudentFirstname() + " " + assignment.getStudentSurname()%> </td>
                                    <td><%=assignment.getCourseId()%></td>
                                    <td><%=assignment.getCourseName()%></td>
                                    <td><%=assignment.getModule()%></td>
                                    <td><%=assignment.getSemester()%></td>
                                    <%if(!assignment.getGrade().equalsIgnoreCase("none"))
                                    {%>
                                       <td><%=assignment.getGrade() + "%"%></td>
                                    <%}
                                        else
                                    {%>
                                        <td><%=assignment.getGrade()%></td>
                                    <%}%>
                                    <td><%=assignment.getResit()%></td>
                                    <%if(!assignment.getResitGrade().equalsIgnoreCase("none"))
                                    {%>
                                    <td><%=assignment.getResitGrade() + "%"%></td>
                                    <%}
                                    else
                                    {%>
                                    <td><%=assignment.getResitGrade()%></td>
                                    <%}%>
                                    <td><%=assignment.getAcademicYear()%></td>
                                </tr>
                                <%}%>
                                </tbody>
                            </table>
                            <br/>
                            <%
                                int size = assignments.size();
                                int total = 0;
                                for (DWAssignmentsBean assignment : assignments)
                                {
                                	if(assignment.getResit().equalsIgnoreCase("true"))
                                    {
                                        //Take Resat Marks
                                        total = total + Integer.parseInt(assignment.getResitGrade());
                                    }
                                	else
                                    {
                                        //Take original marks
                                        total = total + Integer.parseInt(assignment.getGrade());
                                    }
                                }
                                int average = total/size;
                            %>
                            <h4>The Average Grade for the following Results is: <strong><%=average + "%"%></strong></h4>
                            <%}%>
                            <%}else{ %>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, no Grades were found.</h4>
                            <%}%>
                        <%}%>

                        <%--Q4: Grades based against Tutor Teaching--%>
                        <%if(session.getAttribute("selectedQuery").equals("q4"))
                        {%>
                            <h3>Results for: "Student Grades based on the Tutor Teaching"</h3>
                            <%String year = "Unknown";
                                String course = "Unknown";
                                String tutor = "Unknown";
                                if(session.getAttribute("selectedYear") != null)
                                {
                                    year = (String) session.getAttribute("selectedYear");
                                }
                                if(session.getAttribute("selectedCourse") != null)
                                {
                                    course = (String) session.getAttribute("selectedCourse");
                                }
                                if(session.getAttribute("selectedTutor") != null)
                                {
                                    tutor = (String) session.getAttribute("selectedTutor");
                                }
                            %>

                            <%if(session.getAttribute("assignmentsBeans") != null)
                            {%>
                            <%List<DWAssignmentsBean> assignments = (List<DWAssignmentsBean>) session.getAttribute("assignmentsBeans");
                                if(assignments != null && !assignments.isEmpty()) {%>
                            <br/>
                            <h4>Using Tutor #:<strong><%=tutor%></strong>, Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, a Total of <strong><%=assignments.size()%></strong> results were found.</h4>
                            <br/>
                            <table class="table table-striped">
                                <caption style="  display: table-caption; text-align: center;">Marks Based on Tutor</caption>
                                <thead>
                                <tr>
                                    <th scope="col">#ID</th>
                                    <th scope="col">Student Number</th>
                                    <th scope="col">Student Name</th>
                                    <th scope="col">Course #ID</th>
                                    <th scope="col">Course Name</th>
                                    <th scope="col">Module #ID</th>
                                    <th scope="col">Semester</th>
                                    <th scope="col">Grade</th>
                                    <th scope="col">Did Resit</th>
                                    <th scope="col">Resist Grade</th>
                                    <th scope="col">Year</th>
                                    <th scope="col">Tutor #ID</th>
                                    <th scope="col">Tutor Name</th>
                                </tr>
                                </thead>
                                <tbody>
                                <%for (DWAssignmentsBean assignment : assignments)
                                {%>
                                <tr>
                                    <th scope="row"><%=assignment.getAssignmentId()%></th>
                                    <td><%=assignment.getStudentId()%></td>
                                    <td><%=assignment.getStudentFirstname() + " " + assignment.getStudentSurname()%> </td>
                                    <td><%=assignment.getCourseId()%></td>
                                    <td><%=assignment.getCourseName()%></td>
                                    <td><%=assignment.getModule()%></td>
                                    <td><%=assignment.getSemester()%></td>
                                    <%if(!assignment.getGrade().equalsIgnoreCase("none"))
                                    {%>
                                    <td><%=assignment.getGrade() + "%"%></td>
                                    <%}
                                    else
                                    {%>
                                    <td><%=assignment.getGrade()%></td>
                                    <%}%>
                                    <td><%=assignment.getResit()%></td>
                                    <%if(!assignment.getResitGrade().equalsIgnoreCase("none"))
                                    {%>
                                    <td><%=assignment.getResitGrade() + "%"%></td>
                                    <%}
                                    else
                                    {%>
                                    <td><%=assignment.getResitGrade()%></td>
                                    <%}%>
                                    <td><%=assignment.getAcademicYear()%></td>
                                    <td><%=assignment.getTutorId()%></td>
                                    <td><%=assignment.getTutorFirstname() + " " + assignment.getTutorSurname()%></td>
                                </tr>
                                <%}%>
                                </tbody>
                            </table>
                            <br/>
                            <%
                                int size = assignments.size();
                                int total = 0;
                                for (DWAssignmentsBean assignment : assignments)
                                {
                                    if(assignment.getResit().equalsIgnoreCase("true"))
                                    {
                                        //Take Resat Marks
                                        total = total + Integer.parseInt(assignment.getResitGrade());
                                    }
                                    else
                                    {
                                        //Take original marks
                                        total = total + Integer.parseInt(assignment.getGrade());
                                    }
                                }
                                int average = total/size;
                            %>
                            <h4>The Average Student Grades for the following Tutor is: <strong><%=average + "%"%></strong></h4>
                            <%}%>
                            <%}else{ %>
                            <h4>Using Tutor #: <strong><%=tutor%></strong>, Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, no Results were found.</h4>
                            <%}%>
                        <%}%>

                        <%--Q5: Total Resits--%>
                        <%if(session.getAttribute("selectedQuery").equals("q5"))
                        {%>
                            <h3>Results for: "Total Resits on a Specific Course"</h3>
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

                            <%if(session.getAttribute("assignmentsBeans") != null)
                            {%>
                            <%List<DWAssignmentsBean> assignments = (List<DWAssignmentsBean>) session.getAttribute("assignmentsBeans");
                                if(assignments != null && !assignments.isEmpty()) {%>
                            <br/>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, a Total of <strong><%=assignments.size()%></strong> Resits were found.</h4>
                            <br/>
                            <table class="table table-striped">
                                <caption style="  display: table-caption; text-align: center;">Total Resits</caption>
                                <thead>
                                <tr>
                                    <th scope="col">#ID</th>
                                    <th scope="col">Student Number</th>
                                    <th scope="col">Student Name</th>
                                    <th scope="col">Course #ID</th>
                                    <th scope="col">Course Name</th>
                                    <th scope="col">Module #ID</th>
                                    <th scope="col">Semester</th>
                                    <th scope="col">Grade</th>
                                    <th scope="col">Did Resit</th>
                                    <th scope="col">Resist Grade</th>
                                    <th scope="col">Year</th>
                                </tr>
                                </thead>
                                <tbody>
                                <%for (DWAssignmentsBean assignment : assignments)
                                {%>
                                <tr>
                                    <th scope="row"><%=assignment.getAssignmentId()%></th>
                                    <td><%=assignment.getStudentId()%></td>
                                    <td><%=assignment.getStudentFirstname() + " " + assignment.getStudentSurname()%> </td>
                                    <td><%=assignment.getCourseId()%></td>
                                    <td><%=assignment.getCourseName()%></td>
                                    <td><%=assignment.getModule()%></td>
                                    <td><%=assignment.getSemester()%></td>
                                    <%if(!assignment.getGrade().equalsIgnoreCase("none"))
                                    {%>
                                    <td><%=assignment.getGrade() + "%"%></td>
                                    <%}
                                    else
                                    {%>
                                    <td><%=assignment.getGrade()%></td>
                                    <%}%>
                                    <td><%=assignment.getResit()%></td>
                                    <%if(!assignment.getResitGrade().equalsIgnoreCase("none"))
                                    {%>
                                    <td><%=assignment.getResitGrade() + "%"%></td>
                                    <%}
                                    else
                                    {%>
                                    <td><%=assignment.getResitGrade()%></td>
                                    <%}%>
                                    <td><%=assignment.getAcademicYear()%></td>
                                </tr>
                                <%}%>
                                </tbody>
                            </table>
                            <br/>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, a total number of <%=assignments.size()%> Assignments were failed and required resitting.</h4>
                            <%}%>
                            <%}else{ %>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, no Resit Grades were found.</h4>
                            <%}%>
                        <%}%>

                        <%--Q6: Total Enrollments in a Specific Year--%>
                        <%if(session.getAttribute("selectedQuery").equals("q6"))
                        {%>
                            <h3>Results for: "Total Students Enrolled on a Specific Course"</h3>
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
                            <%if(session.getAttribute("enrollmentsBeans") != null)
                            {%>
                            <%List<DWEnrollmentsBean> enrollments = (List<DWEnrollmentsBean>) session.getAttribute("enrollmentsBeans");
                                if(enrollments != null && !enrollments.isEmpty()) {%>
                            <br/>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, a Total of <strong><%=enrollments.size()%></strong> Student Enrollment(s) were found.</h4>
                            <br/>
                            <table class="table table-striped">
                                <caption style="  display: table-caption; text-align: center;">Total Enrollments Specified Year</caption>
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
                            <br/>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, a total of <%=enrollments.size()%> Enrollment(s) were found.</h4>
                            <%}%>
                            <%}else{ %>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, no Student Enrollment(s) were found.</h4>
                            <%}%>
                        <%}%>

                        <%--Q7: Has COVID-19 affected Enrollment (Compared against 2020/2021)--%>
                        <%if(session.getAttribute("selectedQuery").equals("q7"))
                        {%>
                            <h3>Results for: "Has COVID-19 affected our Enrollment Intake?"</h3>
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
                            <%if(session.getAttribute("enrollmentsBeans") != null)
                            {%>
                            <%List<DWEnrollmentsBean> enrollments = (List<DWEnrollmentsBean>) session.getAttribute("enrollmentsBeans");
                                if(enrollments != null && !enrollments.isEmpty()) {%>
                            <br/>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, a Total of <strong><%=enrollments.size()%></strong> Student Enrollment(s) were found.</h4>
                            <br/>
                            <%
                                int enroll2015 = 0;
                                int enroll2016 = 0;
                                int enroll2017 = 0;
                                int enroll2018 = 0;
                                int enroll2019 = 0;
                                int enroll2020 = 0;
                                int enroll2021 = 0;

                                for (DWEnrollmentsBean enrollment : enrollments)
                                {
                                	if(enrollment.getEnrollmentDate() != null){
                                        String enrollmentDate = enrollment.getEnrollmentDate();
                                        if(enrollmentDate.startsWith("2021"))
                                        {
                                        	enroll2021++;
                                        }
                                        else if(enrollmentDate.startsWith("2020"))
                                        {
                                            enroll2020++;
                                        }
                                        else if(enrollmentDate.startsWith("2019"))
                                        {
                                        	enroll2019++;
                                        }
                                        else if(enrollmentDate.startsWith("2018"))
                                        {
                                        	enroll2018++;
                                        }
                                        else if(enrollmentDate.startsWith("2017"))
                                        {
                                            enroll2017++;
                                        }
                                        else if(enrollmentDate.startsWith("2016"))
                                        {
                                            enroll2016++;
                                        }
                                        else if(enrollmentDate.startsWith("2015"))
                                        {
                                            enroll2015++;
                                        }
                                    }
                                }%>
                            <label for="2015Enrollments"></label><input style="display: none" type="text" name="2015Enrollments" id="2015Enrollments" value="<%=enroll2015%>">
                            <label for="2016Enrollments"></label><input style="display: none" type="text" name="2016Enrollments" id="2016Enrollments" value="<%=enroll2016%>">
                            <label for="2017Enrollments"></label><input style="display: none" type="text" name="2017Enrollments" id="2017Enrollments" value="<%=enroll2017%>">
                            <label for="2018Enrollments"></label><input style="display: none" type="text" name="2018Enrollments" id="2018Enrollments" value="<%=enroll2018%>">
                            <label for="2019Enrollments"></label><input style="display: none" type="text" name="2019Enrollments" id="2019Enrollments" value="<%=enroll2019%>">
                            <label for="2020Enrollments"></label><input style="display: none" type="text" name="2020Enrollments" id="2020Enrollments" value="<%=enroll2020%>">
                            <label for="2021Enrollments"></label><input style="display: none" type="text" name="2021Enrollments" id="2021Enrollments" value="<%=enroll2021%>">

                            <canvas id="cv19Enrollment" style="width:50%;max-width:700px; display: inline-block"></canvas>
                            <script>
                                let enroll2015 = $("#2015Enrollments");
                                let enroll2016 = $("#2016Enrollments");
                                let enroll2017 = $("#2017Enrollments");
                                let enroll2018 = $("#2018Enrollments");
                                let enroll2019 = $("#2019Enrollments");
                                let enroll2020 = $("#2020Enrollments");
                                let enroll2021 = $("#2021Enrollments");

                                let xValues = ["2015", "2016", "2017", "2018", "2019", "2020(COVID-19)", "2021(COVID-19)"];
                                let yValues = [enroll2015.val(), enroll2016.val(), enroll2017.val(), enroll2018.val(), enroll2019.val(), enroll2020.val(), enroll2021.val()];
                                let barColors = ["red", "green","blue","orange","brown", "red", "green"];

                                new Chart("cv19Enrollment", {
                                    type: "bar",
                                    data: {
                                        labels: xValues,
                                        datasets: [{
                                            backgroundColor: barColors,
                                            data: yValues
                                        }]
                                    },
                                    options: {
                                        legend: {display: false},
                                        title: {
                                            display: true,
                                            text: "Total Enrollments Per Year against COVID-19 enrollments"
                                        }
                                    }
                                });
                            </script>

                            <br/>
                            <h4>The following Table shows the Total Enrollments for selected Year(s): <strong><%=year%></strong></h4>
                            <table class="table table-striped">
                                <caption style="  display: table-caption; text-align: center;">Total Enrollments Against COVID-19 Specified Year</caption>
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
                                <%
                                    int totalSelected = 0;
                                    for (DWEnrollmentsBean enrollment : enrollments)
                                {
                                if(year.equals("all") || enrollment.getEnrollmentDate().startsWith(year)){
                                totalSelected++;
                                %>
                                    <tr>
                                        <th scope="row"><%=enrollment.getId()%></th>
                                        <td><%=enrollment.getStudentId()%></td>
                                        <td><%=enrollment.getStudentFirstname() + " " + enrollment.getStudentSurname()%> </td>
                                        <td><%=enrollment.getCourseId()%></td>
                                        <td><%=enrollment.getCourseName()%></td>
                                        <td><%=enrollment.getEnrollmentDate()%></td>
                                    </tr>
                                <%}%>
                                <%}%>
                                </tbody>
                            </table>
                            <br/>
                            <h4>Using Year(s): <strong><%=year%></strong>, a total of <%=totalSelected%> Enrollment(s) were found.</h4>
                            <%}%>
                            <%}else{ %>
                            <h4>Using Year(s): <strong><%=year%></strong>, no Student Enrollment(s) were found.</h4>
                            <%}%>
                        <%}%>

                        <%--Q8: Total International Students--%>
                        <%if(session.getAttribute("selectedQuery").equals("q8"))
                        {%>
                            <h3>Results for: "Total Current International Students Enrolled on a Specific Course"</h3>
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
                            <%if(session.getAttribute("enrollmentsBeans") != null)
                            {%>
                            <%List<DWEnrollmentsBean> enrollments = (List<DWEnrollmentsBean>) session.getAttribute("enrollmentsBeans");
                                if(enrollments != null && !enrollments.isEmpty()) {%>
                            <br/>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, a Total of <strong><%=enrollments.size()%></strong> Current International Student Enrollment(s) were found.</h4>
                            <br/>
                            <table class="table table-striped">
                                <caption style="  display: table-caption; text-align: center;">Total Current International Student Enrollments</caption>
                                <thead>
                                <tr>
                                    <th scope="col">#ID</th>
                                    <th scope="col">Student Number</th>
                                    <th scope="col">Student Name</th>
                                    <th scope="col">Course #ID</th>
                                    <th scope="col">Course Name</th>
                                    <th scope="col">Date of Enrollment</th>
                                    <th scope="col">Is International</th>
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
                                    <td><%=enrollment.getInternational()%></td>
                                </tr>
                                <%}%>
                                </tbody>
                            </table>
                            <%}%>
                            <%}else{ %>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, no Current International Student Enrollment(s) were found.</h4>
                            <%}%>
                        <%}%>

                        <%--Q9: Total Course Changes--%>
                        <%if(session.getAttribute("selectedQuery").equals("q9"))
                        {%>
                            <h3>Results for: "Number of Students That Changed Courses"</h3>
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
                            <%if(session.getAttribute("enrollmentsBeans") != null)
                            {%>
                            <%List<DWEnrollmentsBean> enrollments = (List<DWEnrollmentsBean>) session.getAttribute("enrollmentsBeans");
                                if(enrollments != null && !enrollments.isEmpty()) {%>
                            <br/>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, a Total of <strong><%=enrollments.size()%></strong> result(s) were found.</h4>
                            <br/>
                            <table class="table table-striped">
                                <caption style="  display: table-caption; text-align: center;">Total Students Course Changes</caption>
                                <thead>
                                <tr>
                                    <th scope="col">#ID</th>
                                    <th scope="col">Student Number</th>
                                    <th scope="col">Student Name</th>
                                    <th scope="col">Date of Enrollment</th>
                                    <th scope="col">Course #ID</th>
                                    <th scope="col">Course Name</th>
                                </tr>
                                </thead>
                                <tbody>
                                <%for (DWEnrollmentsBean enrollment : enrollments)
                                {%>
                                <tr>
                                    <th scope="row"><%=enrollment.getId()%></th>
                                    <td><%=enrollment.getStudentId()%></td>
                                    <td><%=enrollment.getStudentFirstname() + " " + enrollment.getStudentSurname()%> </td>
                                    <td><%=enrollment.getEnrollmentDate()%></td>
                                    <td><%=enrollment.getCourseId()%></td>
                                    <td><%=enrollment.getCourseName()%></td>
                                </tr>
                                <%}%>
                                </tbody>
                            </table>
                            <br/>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, a total of <%=enrollments.size()/2%> Course Change(s) were found.</h4>
                            <%}%>
                            <%}else{ %>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, no Course Changes were found.</h4>
                            <%}%>
                        <%}%>

                        <%--Q10: COVID-19 Affect on International Student Enrollment--%>
                        <%if(session.getAttribute("selectedQuery").equals("q10"))
                        {%>
                            <h3>Results for: "Total Current International Students Enrolled on a Specific Course"</h3>
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
                            <%if(session.getAttribute("enrollmentsBeans") != null)
                            {%>
                            <%List<DWEnrollmentsBean> enrollments = (List<DWEnrollmentsBean>) session.getAttribute("enrollmentsBeans");
                                if(enrollments != null && !enrollments.isEmpty()) {%>
                            <br/>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, a Total of <strong><%=enrollments.size()%></strong> International Student Enrollment(s) were found (Including COVID-19 Years).</h4>
                            <br/>
                            <br/>
                            <%
                                int enroll2015 = 0;
                                int enroll2016 = 0;
                                int enroll2017 = 0;
                                int enroll2018 = 0;
                                int enroll2019 = 0;
                                int enroll2020 = 0;
                                int enroll2021 = 0;

                                for (DWEnrollmentsBean enrollment : enrollments)
                                {
                                    if(enrollment.getEnrollmentDate() != null){
                                        String enrollmentDate = enrollment.getEnrollmentDate();
                                        if(enrollmentDate.startsWith("2021"))
                                        {
                                            enroll2021++;
                                        }
                                        else if(enrollmentDate.startsWith("2020"))
                                        {
                                            enroll2020++;
                                        }
                                        else if(enrollmentDate.startsWith("2019"))
                                        {
                                            enroll2019++;
                                        }
                                        else if(enrollmentDate.startsWith("2018"))
                                        {
                                            enroll2018++;
                                        }
                                        else if(enrollmentDate.startsWith("2017"))
                                        {
                                            enroll2017++;
                                        }
                                        else if(enrollmentDate.startsWith("2016"))
                                        {
                                            enroll2016++;
                                        }
                                        else if(enrollmentDate.startsWith("2015"))
                                        {
                                            enroll2015++;
                                        }
                                    }
                                }%>
                            <label for="2015Enrollments"></label><input style="display: none" type="text" name="2015Enrollments" id="2015Enrollments" value="<%=enroll2015%>">
                            <label for="2016Enrollments"></label><input style="display: none" type="text" name="2016Enrollments" id="2016Enrollments" value="<%=enroll2016%>">
                            <label for="2017Enrollments"></label><input style="display: none" type="text" name="2017Enrollments" id="2017Enrollments" value="<%=enroll2017%>">
                            <label for="2018Enrollments"></label><input style="display: none" type="text" name="2018Enrollments" id="2018Enrollments" value="<%=enroll2018%>">
                            <label for="2019Enrollments"></label><input style="display: none" type="text" name="2019Enrollments" id="2019Enrollments" value="<%=enroll2019%>">
                            <label for="2020Enrollments"></label><input style="display: none" type="text" name="2020Enrollments" id="2020Enrollments" value="<%=enroll2020%>">
                            <label for="2021Enrollments"></label><input style="display: none" type="text" name="2021Enrollments" id="2021Enrollments" value="<%=enroll2021%>">

                            <canvas id="cv19Enrollment" style="width:50%;max-width:700px; display: inline-block"></canvas>
                            <script>
                                let enroll2015 = $("#2015Enrollments");
                                let enroll2016 = $("#2016Enrollments");
                                let enroll2017 = $("#2017Enrollments");
                                let enroll2018 = $("#2018Enrollments");
                                let enroll2019 = $("#2019Enrollments");
                                let enroll2020 = $("#2020Enrollments");
                                let enroll2021 = $("#2021Enrollments");

                                let xValues = ["2015", "2016", "2017", "2018", "2019", "2020(COVID-19)", "2021(COVID-19)"];
                                let yValues = [enroll2015.val(), enroll2016.val(), enroll2017.val(), enroll2018.val(), enroll2019.val(), enroll2020.val(), enroll2021.val()];
                                let barColors = ["red", "green","blue","orange","brown", "red", "green"];

                                new Chart("cv19Enrollment", {
                                    type: "bar",
                                    data: {
                                        labels: xValues,
                                        datasets: [{
                                            backgroundColor: barColors,
                                            data: yValues
                                        }]
                                    },
                                    options: {
                                        legend: {display: false},
                                        title: {
                                            display: true,
                                            text: "Total International Student Enrollments Per Year against COVID-19 enrollments"
                                        }
                                    }
                                });
                            </script>

                            <br/>
                            <h4>The following Table shows the Total International Enrollments for selected Year(s): <strong><%=year%></strong></h4>
                            <br/>
                            <table class="table table-striped">
                                <caption style="  display: table-caption; text-align: center;">Total Current International Student Enrollments</caption>
                                <thead>
                                <tr>
                                    <th scope="col">#ID</th>
                                    <th scope="col">Student Number</th>
                                    <th scope="col">Student Name</th>
                                    <th scope="col">Course #ID</th>
                                    <th scope="col">Course Name</th>
                                    <th scope="col">Date of Enrollment</th>
                                    <th scope="col">Is International</th>
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
                                    <td><%=enrollment.getInternational()%></td>
                                </tr>
                                <%}%>
                                </tbody>
                            </table>
                            <%}%>
                            <%}else{ %>
                            <h4>Using Year(s): <strong><%=year%></strong> and Course #: <strong><%=course%></strong>, no International Student Enrollment(s) were found.</h4>
                            <%}%>
                        <%}%>

                        <%--Button to hide table, show form and try again--%>
                        <br/>
                            <div>
                                <button type="button" class="btn btn-primary newSearch">New Search</button>
                            </div>
                        <br/>
                    </div>
                <%}%>
            </div>
        </div>
    </body>
</html>
