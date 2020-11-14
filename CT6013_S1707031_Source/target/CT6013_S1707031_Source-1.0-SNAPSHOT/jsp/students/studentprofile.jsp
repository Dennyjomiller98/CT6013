<%--
  Created by IntelliJ IDEA.
  User: Denny-Jo
  Date: 11/11/2020
  Time: 22:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en-GB">
<link href=${pageContext.request.contextPath}/css/navbar.css rel="stylesheet" type="text/css">
<link href=${pageContext.request.contextPath}/css/mainbody.css rel="stylesheet" type="text/css">
    <head>
        <title>Title</title>
    </head>
    <body>
        <%--Navbar--%>
        <div class="navbar">
            <div class="bottomnavdiv"></div>
            <div class="topnav">
                <h2>Edit Account</h2>
                <%--Only show Profile/Logout if user is already logged in--%>
            </div>
            <% String firstname = (String) session.getAttribute("firstname");
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
                Logged in as: <%=firstname%><br/>
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

        <%--Edit Profile Form--%>
        <div class="mainBody">
            <p>
                If you would like to edit your details, please do so below.
            </p>

            <form action="${pageContext.request.contextPath}/servlets/StudentProfile" method="POST">
                <label for="isEnrolled"></label><input readonly hidden type="text" name="isEnrolled" id="isEnrolled" value="<%=amIEnrolled%>" />
                <label for="isTeacher"></label><input readonly hidden type="text" name="isTeacher" id="isTeacher" value="<%=amITeacher%>" />
                <% String studentID = session.getAttribute("studentID").toString();%>
                <label for="studentID"></label><input readonly hidden type="text" name="studentID" id="studentID" value="<%=studentID%>" />

                <label for="firstname">Firstname:</label>
                <% firstname = session.getAttribute("firstname").toString();%>
                <input type="text" name="firstname" id="firstname" required value="<%=firstname%>" />
                <br/>
                <label for="surname">Surname:</label>
                <% String surname = session.getAttribute("surname").toString();%>
                <input type="text" name="surname" id="surname" required value="<%=surname%>" />
                <br/>
                <label for="email">Email:</label>
                <% String email = session.getAttribute("email").toString();%>
                <input readonly type="email" name="email" id="email" required value="<%=email%>" />
                <br/>
                <label for="dob">Date of Birth</label>
                <% String dob = session.getAttribute("dob").toString();%>
                <input type="date" name="dob" id="dob" required value="<%=dob%>" />
                <br/>
                <label for="address1">Address Line 1</label>
                <% String address1 = session.getAttribute("address1").toString();%>
                <input type="text" name="address1" id="address1" required value="<%=address1%>" />
                <br/>
                <label for="address2">Address Line 2</label>
                <% String address2 = session.getAttribute("address2").toString();%>
                <input type="text" name="address2" id="address2" required value="<%=address2%>" />
                <br/>
                <label for="city">City</label>
                <% String city = session.getAttribute("city").toString();%>
                <input type="text" name="city" id="city" required value="<%=city%>" />
                <br/>
                <label for="postcode">Postcode</label>
                <% String postcode = session.getAttribute("postcode").toString();%>
                <input type="text" name="postcode" id="postcode" required value="<%=postcode%>" />
                <br/>
                <label for="pword">Password</label>
                <% String pword = session.getAttribute("pword").toString();%>
                    <input type="text" name="pword" id="pword" required value="<%=pword%>" />
                <br/>
                <input type="submit" value="Submit">
            </form>

            <% String editErrors = (String) session.getAttribute("editErrors");
                if(editErrors != null){%>
            <p class="error-div" id="errorDiv"><%=editErrors%></p>
            <%}%>
            <% String success = (String) session.getAttribute("updateSuccess");
                if(success != null){%>
            <p class="success-div" id="successDiv"><%=success%></p>
            <%}%>
        </div>
    </body>
</html>
