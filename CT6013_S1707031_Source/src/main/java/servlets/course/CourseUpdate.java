package servlets.course;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.CourseConnections;
import mongodb.TeacherConnections;
import mongodbbeans.CourseBean;
import mongodbbeans.TeacherBean;
import org.apache.log4j.Logger;
import org.bson.Document;

@WebServlet(name = "courseUpdate")
public class CourseUpdate extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(CourseUpdate.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		//Get Form Submission
		Document courseToUpdate = new Document();
		courseToUpdate.append("Course_Code", request.getParameter("courseCode"));
		courseToUpdate.append("Course_Name", request.getParameter("courseName"));
		courseToUpdate.append("Course_Tutor", request.getParameter("courseTutor"));
		courseToUpdate.append("Course_Start", request.getParameter("courseStart"));
		courseToUpdate.append("Course_End", request.getParameter("courseEnd"));

		//Connect to DB and Store updated values
		CourseConnections courseConn = new CourseConnections();

		//Check course still exists.
		CourseBean courseBean = courseConn.retrieveSingleCourse(courseToUpdate.getString("Course_Code"));
		if(courseBean != null)
		{
			//Assert Tutor stored actually exists
			TeacherConnections teacherConn = new TeacherConnections();
			TeacherBean existingTeacher = teacherConn.retrieveSingleTeacher(courseToUpdate.getString("Course_Tutor"));
			if (existingTeacher != null)
			{
				//Update DB
				courseConn.updateCourse(courseToUpdate, courseBean.getCourseCode());
				LOG.debug("Course Update completed successfully");
				request.getSession(true).removeAttribute("courseErrors");
				request.getSession(true).setAttribute("courseSuccess", "Details Updated Successfully.");
				//Set new values or old values will be retained
				addUpdatedSessionAttributes(request);
			}
		}
		else
		{
			//Course not found, cannot update
			request.getSession(true).removeAttribute("courseSuccess");
			request.getSession(true).setAttribute("courseErrors", "An Error has occurred, the changes were unable to be saved.");
		}

		//Return to Course Edit page regardless of outcome, required attributes are already set.
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/courses/courseedit.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect back to Edit Course page after course update failure.",e);
		}
	}

	private void addUpdatedSessionAttributes(HttpServletRequest request)
	{
		//Populate session attributes with changed values
		request.getSession(true).setAttribute("courseCode", request.getParameter("courseCode"));
		request.getSession(true).setAttribute("courseName", request.getParameter("courseName"));
		request.getSession(true).setAttribute("courseTutor", request.getParameter("courseTutor"));
		request.getSession(true).setAttribute("courseStart", request.getParameter("courseStart"));
		request.getSession(true).setAttribute("courseEnd", request.getParameter("courseEnd"));

	}
}
