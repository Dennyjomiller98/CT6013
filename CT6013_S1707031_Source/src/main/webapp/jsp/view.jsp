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
        <%  String email = null;
            if(session.getAttribute("email") != null)
            {
                email = session.getAttribute("email").toString();
            }
            String role = null;
            if(session.getAttribute("role") != null)
            {
                role = session.getAttribute("role").toString();
            }
        %>
        <%--Navbar--%>
        <div class="navbar">
            <div class="bottomnavdiv">
                <strong><a href=${pageContext.request.contextPath}/servlets/etl/Extract>&nbsp;Re-Load DW&nbsp;</a></strong>
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
            <div id="data-selection">
                <p>
                    You are successfully logged in.<br/><br/>
                    Please select the data you wish to view from below
                </p>

                <form action="${pageContext.request.contextPath}/servlets/etl/Load" method="POST">
                    <label for="email"></label>
                    <input style="display: none" type="text" name="email" id="email" value="<%=email%>">

                    <label for="role"></label>
                    <input style="display: none" type="text" name="role" id="role" value="<%=role%>">

                    <label for="select">Select what you would like to view:</label>
                    <select style="display: inline-block" name="select" id="select">
                        <option value="enrollments">Enrollments</option>
                        <option value="assignments">Assignment Results</option>
                        <option value="dropouts">Dropouts</option>
                        <option value="changes">Course Changes</option>
                        <option value="resits">Resits</option>
                        <option value="all">All</option>
                    </select>
                    <br/>
                    <br/>

                    <%--TODO - radio buttons select one?-->
                    <label for="3months">Last 3 months</label><input type="checkbox" name="3months" id="3months">
                    <label for="6months">Last 6 months</label><input type="checkbox" name="6months" id="6months">
                    <label for="12months">Last 12 months</label><input type="checkbox" name="12months" id="12months">
                    <br/>
                    <br/>

                    <label for="datefrom">Custom Date (From)</label>
                    <input  type="date" name="datefrom" id="datefrom">

                    <label for="dateto">Custom Date (To)</label>
                    <input  type="date" name="dateto" id="dateto">

                    <br/>
                    <p><strong>Note:</strong> You must select both dates to view a span of all data between selected dates. Selecting dates will override the above fixed-value selections.</p>
                    <input type="submit" value="View">
                </form>
            </div>

            <div id="data-retrieved">
                <%--TODO - TABLE OF DW info here, graphs etc (maybe a popup? that way can re-select after?) --%>
            </div>
        </div>
    </body>
</html>
