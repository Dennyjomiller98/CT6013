<%@ page import="beans.ModuleBean" %>
<%@ page import="java.util.List" %>
<%@ page import="beans.EnrollmentBean" %>
<%@ page import="beans.MarkBean" %><%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 15/11/2020
  Time: 07:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en-GB">
<link href=${pageContext.request.contextPath}/css/navbar.css rel="stylesheet" type="text/css">
<link href=${pageContext.request.contextPath}/css/mainbody.css rel="stylesheet" type="text/css">
    <head>
        <title>Add Marks</title>
    </head>
    <body>
        <%--Navbar--%>
        <div class="navbar">
            <div class="bottomnavdiv"></div>
            <div class="topnav">
                <h2>Add Marks</h2>
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
            <%if (!amITeacher){%>
                <p>
                    You do not have access to this page.
                </p>
            <%} else {%>
                <%--Teacher Add marks--%>
                <p>
                    As a teacher, you can add marks here. <br/>
                    First select the module you are in charge of, then submit to view a table of all students. From there, you can choose the student you wish to submit marks on.
                </p>

                <%--Module dropdown once again. this time we will use the request to show the table of students on the course--%>
                <form action="${pageContext.request.contextPath}/servlets/mark/MarkView" method="GET">
                    <label for="moduleSelect">Select a Module:</label>
                    <select class="select-css" style="width: 50%; display: inline-block" name="moduleSelect" id="moduleSelect">
                        <option value="*">ALL Students in your Modules</option>
                        <%if(session.getAttribute("allMarkModules") != null)
                        {
                            List<ModuleBean> allMarkModules = (List<ModuleBean>) session.getAttribute("allMarkModules");
                            for (ModuleBean allMarkModule : allMarkModules)
                            {%>
                                <option value="<%=allMarkModule.getModuleCode()%>">Students in <%=allMarkModule.getRelatedCourse()%> : <%=allMarkModule.getModuleCode()%></option>
                           <%}%>
                        <%}%>
                    </select>
                    <input type="submit" value="Search">
                </form>
                <br/>

                <%if(session.getAttribute("markErrors") != null)
                {
                    String markErrors = session.getAttribute("markErrors").toString(); %>
                <p class="error-div" id="errorDiv"><%=markErrors%></p>
                <% } %>
                <%if(session.getAttribute("markSuccess") != null)
                {
                    String markSuccess = session.getAttribute("markSuccess").toString(); %>
                <p class="success-div" id="successDiv"><%=markSuccess%></p>
                <% } %>

                <%--All Students in modules table--%>
            <%if((session.getAttribute("allEnrollmentToReturn") != null && session.getAttribute("allMarkBeans") != null) ||
                    (session.getAttribute("singleEnrollmentToReturn") != null && session.getAttribute("singleMarkBean") != null)){%>
            <table id="moduleAndMarks">
                    <caption></caption>
                    <tr>
                        <th id="allMarksModuleCode">Student Email</th>
                        <th id="allMarksModule">Module</th>
                        <th id="allMarksGrade">Marks (If provided)</th>
                    </tr>

                    <%if(session.getAttribute("allEnrollmentToReturn") != null && session.getAttribute("allMarkBeans") != null){
                        List<MarkBean> allMarkBeans = (List<MarkBean>) session.getAttribute("allMarkBeans");
                        List<EnrollmentBean> allEnrollBeans = (List<EnrollmentBean>) session.getAttribute("allEnrollmentToReturn");

                        int moduleCount = 0;
                        for (EnrollmentBean enrollBean : allEnrollBeans)
                        {
                            String moduleCode;
                            String[] splitModules = enrollBean.getModuleSelections().split(",");
                            if (moduleCount < splitModules.length)
                            {
                                moduleCode = splitModules[moduleCount];
                            }
                            else
                            {
                                moduleCount = 0;
                                moduleCode = splitModules[moduleCount];
                            }
                            moduleCount++;
                    %>
                    <tr>
                        <td><%=enrollBean.getStudentEmail()%></td>
                        <td><%=moduleCode%></td>
                        <td>
                            <%if (allMarkBeans != null)
                            {
                                if(allMarkBeans.size() != 0)
                                {
                                    int finalMark = 0;
                                    for (MarkBean myMark : allMarkBeans)
                                    {
                                        if(myMark != null && myMark.getModuleCode().equalsIgnoreCase(moduleCode) &&
                                                enrollBean.getStudentEmail().equalsIgnoreCase(myMark.getStudentEmail()))
                                        {
                                            finalMark =  myMark.getFinalMark();
                                        }
                                    }
                                    if(finalMark != 0)
                                    {
                                    	String letterGrade = "Unknown";
                                    	if(finalMark >= 70)
                                        {
                                        	letterGrade = "A";
                                        }
                                    	else if(finalMark >= 60)
                                        {
                                        	letterGrade = "B";
                                        }
                                    	else if(finalMark >= 50)
                                        {
                                        	letterGrade = "C";
                                        }
                                    	else if(finalMark >= 40)
                                        {
                                        	letterGrade = "D";
                                        }
                                    	else if(finalMark > 0)
                                        {
                                        	letterGrade = "U";
                                        }
                                    %>
                                        Grade given: <%=finalMark%>% (<%=letterGrade%>)
                                    <%} else { %>
                                        <%--Add Marks form--%>
                                        <form style="width:100%" action="${pageContext.request.contextPath}/servlets/mark/MarkAddition?studentEmail=<%=enrollBean.getStudentEmail()%>&courseTutor=<%=session.getAttribute("email").toString()%>&moduleCode=<%=moduleCode%>" method="POST">
                                            <label for="allGrades"></label><input style="width: 50%" type="text" name="allGrades" id="allGrades" />
                                            <input style="width: 30%" type="submit" value="Add Grade %">
                                        </form>
                                    <% } %>

                            <%} else {%>
                                Grades unavailable
                            <%}
                            }%>
                        </td>
                    </tr>
                    <%}
                    }%>

                <%--Single module Students table--%>
                <%if(session.getAttribute("singleEnrollmentToReturn") != null && session.getAttribute("singleMarkBean") != null){
                    List<EnrollmentBean> enrollBeans = (List<EnrollmentBean>) session.getAttribute("singleEnrollmentToReturn");
                    List<MarkBean> markBeans = (List<MarkBean>) session.getAttribute("singleMarkBean");

                    int moduleCount = 0;
                    for (EnrollmentBean enrollBean : enrollBeans)
                    {
                    	String moduleCode;
                    	String[] splitModules = enrollBean.getModuleSelections().split(",");
                    	if(moduleCount < splitModules.length)
                    	{
                    		moduleCode = splitModules[moduleCount];
                        }
                    	else
                        {
                    	    moduleCount = 0;
                    	    moduleCode = splitModules[moduleCount];
                        }
                    	moduleCount++;
                    %>

                        <tr>
                            <td><%=enrollBean.getStudentEmail()%></td>
                            <td><%=moduleCode%></td>
                            <td>
                                <%if (markBeans != null)
                                {
                                    if(markBeans.size() != 0)
                                    {
                                        int finalMark = 0;
                                        for (MarkBean myMark : markBeans)
                                        {
                                            if(myMark != null && myMark.getModuleCode().equalsIgnoreCase(moduleCode) &&
                                                    enrollBean.getStudentEmail().equalsIgnoreCase(myMark.getStudentEmail()))
                                            {
                                                finalMark =  myMark.getFinalMark();
                                            }
                                        }
                                        if(finalMark != 0)
                                        {%>
                                Grade given: <%=finalMark%>%
                                <%} else { %>
                                <%--Add Marks form--%>
                                <form style="width:100%" action="${pageContext.request.contextPath}/servlets/mark/MarkAddition?studentEmail=<%=enrollBean.getStudentEmail()%>&courseTutor=<%=session.getAttribute("email").toString()%>&moduleCode=<%=moduleCode%>" method="POST">
                                    <label for="singleGrades"></label><input style="width: 50%" type="text" name="singleGrades" id="singleGrades" />
                                    <input style="width: 30%" type="submit" value="Add Grade %">
                                </form>
                                <% } %>
                                <%} else {%>
                                Grades unavailable
                                <%}
                                }%>
                            </td>
                        </tr>
                    <%}%>
                </table>
                <%}%>
            <%}
            }%>
        </div>
    </body>
</html>
