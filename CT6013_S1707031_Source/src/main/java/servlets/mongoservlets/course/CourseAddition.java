package servlets.mongoservlets.course;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.CourseConnections;
import mongodbbeans.CourseBean;
import org.apache.log4j.Logger;
import org.bson.Document;

@WebServlet(name = "mongoCourseAddition")
public class CourseAddition extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(CourseAddition.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		//Save Course Details
		LOG.debug("Submitting Course Addition form");
		Document courseToAdd = new Document();
		courseToAdd.append("Course_Code", request.getParameter("courseCode"));
		courseToAdd.append("Course_Name", request.getParameter("courseName"));
		courseToAdd.append("Course_Tutor", request.getParameter("courseTutor"));
		courseToAdd.append("Course_Start", request.getParameter("courseStart"));
		courseToAdd.append("Course_End", request.getParameter("courseEnd"));

		//Establish connection
		CourseConnections courseConn = new CourseConnections();
		//Check course does not already exist (Course Code)
		CourseBean cB = courseConn.retrieveSingleCourse(courseToAdd.getString("Course_Code"));
		if (cB == null)
		{
			//Add Course
			courseConn.addCourse(courseToAdd);
			try
			{
				request.getSession(true).removeAttribute("courseErrors");
				request.getSession(true).setAttribute("courseSuccess", "Course Added Successfully");
				response.sendRedirect(request.getContextPath() + "/jsp/courses/courseaddition.jsp");
			} catch (IOException e) {
				LOG.error("Failure to redirect after student successfully registered", e);
			}
		}
		else
		{
			//Failure to add
			LOG.error("Unable to add course, this course code already exists");
			request.getSession(true).removeAttribute("courseSuccess");
			request.getSession(true).setAttribute("courseErrors", "Unable to add course, the course code " + courseToAdd.getString("Course_Code") + " already exists.");
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/courses/courseaddition.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect after Course addition Failure", e);
			}
		}
	}
}