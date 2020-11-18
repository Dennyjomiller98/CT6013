<%@ page import="mongodb.CourseConnections" %>
<%@ page import="beans.CourseBean" %>
<%@ page import="java.util.List" %>
<%@ page import="beans.ModuleBean" %>
<%@ page import="java.util.ArrayList" %><%--
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
            <form action="${pageContext.request.contextPath}/servlets/enrollment/EnrollmentCourseView" method="GET">
                <label for="courseSelect">Select a Course:</label>
                <select class="select-css" style="width: 50%; display: inline-block" name="courseSelect" id="courseSelect">
                    <option value="-">-</option>
                    <%
                        List<CourseBean> courseBeans = new ArrayList<CourseBean>();
                        if(session.getAttribute("DBSELECTION") != null)
                        {
                            String dbSelection = session.getAttribute("DBSELECTION").toString();
                            if(dbSelection.equalsIgnoreCase("MONGODB"))
                            {
                                CourseConnections conn = new CourseConnections();
                                courseBeans = conn.retrieveAllCourses();
                            }
                            else if(dbSelection.equalsIgnoreCase("ORACLE"))
                            {
                            	oracle.CourseConnections conn = new oracle.CourseConnections();
                                courseBeans = conn.retrieveAllCourses();
                            }
                        }
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
            <%if(session.getAttribute("enrollErrors") != null)
            {
                String enrollmentErrors = session.getAttribute("enrollErrors").toString(); %>
            <p class="error-div" id="errorDiv"><%=enrollmentErrors%></p>
            <% } %>
            <%if(session.getAttribute("enrollSuccess") != null)
            {
                String enrollmentSuccess = session.getAttribute("enrollSuccess").toString(); %>
            <p class="success-div" id="successDiv"><%=enrollmentSuccess%></p>
            <% } %>
            <br/>
            <form  style="width:80%" action="${pageContext.request.contextPath}/servlets/course/CourseEnrollment" method="POST">
                <%--student enroll on course--%>
                <% if(session.getAttribute("allModules") != null && session.getAttribute("selectedCourse") != null)
                {
                	String selectedCourse = session.getAttribute("selectedCourse").toString();%>
                    <label for="studentEmail"></label>
                    <input style="display:none" type="text" name="studentEmail" id="studentEmail" value="<%=email%>"/>

                    <label for="selectedCourse"></label>
                    <input style="display:none" type="text" name="selectedCourse" id="selectedCourse" value="<%=selectedCourse%>"/>

                <%--Table of modules--%>
                    <table id="AllModulesOnCourse">
                        <caption></caption>
                        <tr>
                            <th id="allTblEnrollmentOptions">Choices (0/120)</th>
                            <th id="allTblModuleCodeAndName">Module Code</th>
                            <th id="allTblModuleName">Module Name</th>
                            <th id="allTblModuleTutor">Module Leader</th>
                            <th id="allTblSemester">Semester</th>
                            <th id="allTblCATS">CATS</th>
                            <th id="allTblIsCompulsory">Is Compulsory Module</th>
                            <th id="allTblModuleStart">Start Date</th>
                            <th id="allTblModuleEnd">End Date</th>
                        </tr>

                        <label for="allCheckboxSelections"></label>
                        <input style="display:none" type="text" name="allCheckboxSelections" id="allCheckboxSelections"/>

                        <label for="allCATS"></label>
                        <input style="display:none" type="text" name="allCATS" id="allCATS" value="0"/>

                        <%List<ModuleBean> allModules = (List<ModuleBean>) session.getAttribute("allModules");
                        for (ModuleBean module : allModules)
                        {%>
                            <%--Table row of module values iterated--%>
                            <tr>
                                <td>
                                    <label for="isSelected"></label>
                                    <input type="checkbox" name="isSelected" id="isSelected" value="<%=module.getModuleCode()%>" onclick="updateModuleSelection(this.checked, this.value)"/>
                                </td>
                                <td><%=module.getModuleCode()%></td>
                                <td><%=module.getModuleName()%></td>
                                <td><%=module.getModuleTutor()%></td>
                                <td><%if(module.getSemester() == 1){%> Semester 1
                                    <% } else if(module.getSemester() == 2){%> Semester 2
                                    <% } else if (module.getSemester() == -1){%> Semester 1 and 2
                                    <% } else if (module.getSemester() == -2){%> Placement <% }%>
                                </td>
                                <td><%if(module.getSemester() == 1){%> 15
                                    <% } else if(module.getSemester() == 2){%> 15
                                    <% } else if (module.getSemester() == -1){%> 30
                                    <% } else if (module.getSemester() == -2){%> 120 <% }%>
                                    <label for="allCATS<%=module.getModuleCode()%>"></label>
                                    <input style="display:none" type="text" name="allCATS<%=module.getModuleCode()%>" id="allCATS<%=module.getModuleCode()%>" value="<%=module.getSemester()%>"/>
                                </td>
                                <td><%if(module.isCompulsory()){%> Yes <% }else{ %> No <% } %></td>
                                <td><%=module.getModuleStart()%></td>
                                <td><%=module.getModuleEnd()%></td>
                            </tr>
                        <%}%>
                    </table>
                    <input type="submit" value="Submit">
                <%}%>
            </form>

            <%--Student already enrolled--%>
            <%} else if(!amITeacher && amIEnrolled && email != null){%>
            You have already enrolled.<br/>

            To view your enrollment, <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToMarksView?studentEmail=<%=email%>>&nbsp;<u>click here.</u>&nbsp;</a> <br/>
            To view courses, <a class="bodyA" style="display: inline" href=${pageContext.request.contextPath}/servlets/redirects/HomeToCourseEnrollmentView>&nbsp;<u>click here.</u>&nbsp;</a> <br/>
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

    <%--Script for checking which modules are selected. Also asserts total CATS points all selections amount to--%>
    <script type="text/javascript">
        function updateModuleSelection(isSelected, moduleCode)
        {
            if (isSelected) {
                //Add checkbox choice
                let allEls = document.getElementById("allCheckboxSelections").value;
                if (allEls === "") {
                    document.getElementById("allCheckboxSelections").value = document.getElementById("allCheckboxSelections").value + moduleCode;
                } else {
                    document.getElementById("allCheckboxSelections").value = document.getElementById("allCheckboxSelections").value + "," + moduleCode;
                }
            } else {
                //Remove checkbox choice
                let removeEl = document.getElementById("allCheckboxSelections").value;
                let splitEls = removeEl.split(",");
                let resultAfterExtraction = [];
                for(let i = 0; i < splitEls.length; i++){
                    if (splitEls[i] !== moduleCode) {
                        resultAfterExtraction.push(splitEls[i]);
                    }
                }
                document.getElementById("allCheckboxSelections").value = resultAfterExtraction.join();
            }

            getTotalCATS(isSelected, moduleCode);
        }

        function getTotalCATS(isSelected, modulecode) {
            let allCATS = document.getElementById("allCATS" + modulecode).value;
            let totalVal = document.getElementById("allCATS").value;
            let val = "0";
            if (allCATS === "1" || allCATS === "2") {
                //Single Semester
                val = "15";
            } else if (allCATS === "-1") {
                //Two Semesters
                val = "30";
            } else if (allCATS === "-2") {
                //Placement
                val = "120";
            }
            if (isSelected) {
                totalVal = parseInt(totalVal) + parseInt(val);
            } else {
                totalVal = parseInt(totalVal) - parseInt(val);
            }
            document.getElementById("allCATS").value = totalVal;
            document.getElementById("allTblEnrollmentOptions").innerHTML = "Choices ("+totalVal+"/120)";
            document.getElementById("allTblEnrollmentOptions").value = totalVal;
        }
    </script>
</html>