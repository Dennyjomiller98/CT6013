package servlets.course;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.CourseConnections;
import beans.CourseBean;
import org.apache.log4j.Logger;
import org.bson.Document;

@WebServlet(name = "courseAddition")
public class CourseAddition extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(CourseAddition.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		//Save Course Details
		if(request.getSession(true).getAttribute("DBSELECTION") != null)
		{
			String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
			Document courseToAdd = new Document();
			courseToAdd.append("Course_Code", request.getParameter("courseCode"));
			courseToAdd.append("Course_Name", request.getParameter("courseName"));
			courseToAdd.append("Course_Tutor", request.getParameter("courseTutor"));
			courseToAdd.append("Course_Start", request.getParameter("courseStart"));
			courseToAdd.append("Course_End", request.getParameter("courseEnd"));
			if(dbSelection.equalsIgnoreCase("MONGODB"))
			{
				mongoCourseAddition(request, response, courseToAdd);
			}
			else if (dbSelection.equalsIgnoreCase("ORACLE"))
			{
				oracleCourseAddition(request, response, courseToAdd);
			}
			else
			{
				//No DB selection
				LOG.error("Unknown database choice, returning to DB select page.");
				redirectToDBSelect(request, response);
			}
		}
		else
		{
			LOG.error("No DB selected");
			redirectToDBSelect(request, response);
		}
	}

	private void oracleCourseAddition(HttpServletRequest request, HttpServletResponse response, Document courseToAdd)
	{
		LOG.debug("Submitting Course Addition form Oracle");
		oracle.CourseConnections courseConn = new oracle.CourseConnections();
		//Check course doesn't exist
		CourseBean courseBean = courseConn.retrieveSingleCourse(courseToAdd.getString("Course_Code"));
		if (courseBean == null)
		{
			LOG.debug("Attempting to add new course");
			courseConn.addCourse(courseToAdd);
			try
			{
				request.getSession(true).removeAttribute("courseErrors");
				request.getSession(true).setAttribute("courseSuccess", "Course Added Successfully");
				response.sendRedirect(request.getContextPath() + "/jsp/courses/courseaddition.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Failure to redirect after course successfully added", e);
			}
		}
		else
		{
			LOG.debug("Course already exists");
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

	private void mongoCourseAddition(HttpServletRequest request, HttpServletResponse response, Document courseToAdd)
	{
		LOG.debug("Submitting Course Addition form Mongo DB");

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
			}
			catch (IOException e)
			{
				LOG.error("Failure to redirect after course successfully added", e);
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

	private void redirectToDBSelect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after course addition failure", e);
		}
	}
}