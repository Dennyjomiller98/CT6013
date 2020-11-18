package servlets.course;

import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.CourseConnections;
import beans.CourseBean;
import org.apache.log4j.Logger;

@WebServlet(name = "courseEnrollmentView")
public class CourseView extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(CourseView.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Retrieve Course details on selected course and set all modules available
		if(request.getSession(true).getAttribute("DBSELECTION") != null)
		{
			String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
			CourseBean courseBean = new CourseBean();
			courseBean.setCourseCode(request.getParameter("courseSelect"));
			if(dbSelection.equalsIgnoreCase("MONGODB"))
			{
				mongoCourseView(request, response, courseBean);
			}
			else if (dbSelection.equalsIgnoreCase("ORACLE"))
			{
				oracleCourseView(request, response, courseBean);
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

	private void oracleCourseView(HttpServletRequest request, HttpServletResponse response, CourseBean courseBean)
	{
		oracle.CourseConnections courseConn = new oracle.CourseConnections();
		if(courseBean.getCourseCode().equalsIgnoreCase("*"))
		{
			//Retrieve ALL courses
			List<CourseBean> beansToReturn = courseConn.retrieveAllCourses();
			request.getSession(true).removeAttribute("courseCode");
			request.getSession(true).removeAttribute("courseName");
			request.getSession(true).removeAttribute("courseTutor");
			request.getSession(true).removeAttribute("courseStart");
			request.getSession(true).removeAttribute("courseEnd");
			request.getSession(true).removeAttribute("courseErrors");
			request.getSession(true).setAttribute("allCourses", beansToReturn);
			request.getSession(true).setAttribute("courseSuccess", "Course Data successfully retrieved.");
			//Direct to view course page
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/courses/courseenrollmentview.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect back to course view page after ALL course retrieval",e);
			}
		}
		else
		{
			CourseBean beanToReturn = courseConn.retrieveSingleCourse(courseBean.getCourseCode());
			if (beanToReturn != null)
			{
				//Get CourseBean values, set in session
				request.getSession(true).setAttribute("courseCode", beanToReturn.getCourseCode());
				request.getSession(true).setAttribute("courseName", beanToReturn.getCourseName());
				request.getSession(true).setAttribute("courseTutor", beanToReturn.getCourseTutor());
				request.getSession(true).setAttribute("courseStart", beanToReturn.getCourseStart());
				request.getSession(true).setAttribute("courseEnd", beanToReturn.getCourseEnd());
				request.getSession(true).removeAttribute("courseErrors");
				request.getSession(true).removeAttribute("allCourses");
				request.getSession(true).setAttribute("courseSuccess", "Course Data successfully retrieved.");
				//Direct to view Courses page
				try
				{
					response.sendRedirect(request.getContextPath() + "/jsp/courses/courseenrollmentview.jsp");
				}
				catch (IOException e)
				{
					LOG.error("Unable to redirect back to course view page after course retrieval",e);
				}
			}
			else
			{
				LOG.error("Course not found despite being listed in dropdown. CourseConnection can retrieve all but failure at retrieve single using Course ID: " + courseBean.getCourseCode());
				request.getSession(true).removeAttribute("courseSuccess");
				request.getSession(true).setAttribute("courseErrors", "An error has occurred, Course data unavailable.");
				//Redirect back to page
				try
				{
					response.sendRedirect(request.getContextPath() + "/jsp/courses/courseenrollmentview.jsp");
				}
				catch (IOException e)
				{
					LOG.error("Unable to redirect back to course page after failing to retrieve data",e);
				}
			}
		}
	}

	private void mongoCourseView(HttpServletRequest request, HttpServletResponse response, CourseBean courseBean)
	{
		CourseConnections conn = new CourseConnections();
		if(courseBean.getCourseCode().equalsIgnoreCase("*"))
		{
			//Retrieve ALL courses
			List<CourseBean> beansToReturn = conn.retrieveAllCourses();
			request.getSession(true).removeAttribute("courseCode");
			request.getSession(true).removeAttribute("courseName");
			request.getSession(true).removeAttribute("courseTutor");
			request.getSession(true).removeAttribute("courseStart");
			request.getSession(true).removeAttribute("courseEnd");
			request.getSession(true).removeAttribute("courseErrors");
			request.getSession(true).setAttribute("allCourses", beansToReturn);
			request.getSession(true).setAttribute("courseSuccess", "Course Data successfully retrieved.");
			//Direct to view course page
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/courses/courseenrollmentview.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect back to course view page after ALL course retrieval",e);
			}
		}
		else
		{
			CourseBean beanToReturn = conn.retrieveSingleCourse(courseBean.getCourseCode());
			if (beanToReturn != null)
			{
				//Get CourseBean values, set in session
				request.getSession(true).setAttribute("courseCode", beanToReturn.getCourseCode());
				request.getSession(true).setAttribute("courseName", beanToReturn.getCourseName());
				request.getSession(true).setAttribute("courseTutor", beanToReturn.getCourseTutor());
				request.getSession(true).setAttribute("courseStart", beanToReturn.getCourseStart());
				request.getSession(true).setAttribute("courseEnd", beanToReturn.getCourseEnd());
				request.getSession(true).removeAttribute("courseErrors");
				request.getSession(true).removeAttribute("allCourses");
				request.getSession(true).setAttribute("courseSuccess", "Course Data successfully retrieved.");
				//Direct to view Courses page
				try
				{
					response.sendRedirect(request.getContextPath() + "/jsp/courses/courseenrollmentview.jsp");
				}
				catch (IOException e)
				{
					LOG.error("Unable to redirect back to course view page after course retrieval",e);
				}
			}
			else
			{
				LOG.error("Course not found despite being listed in dropdown. CourseConnection can retrieve all but failure at retrieve single using Course ID: " + courseBean.getCourseCode());
				request.getSession(true).removeAttribute("courseSuccess");
				request.getSession(true).setAttribute("courseErrors", "An error has occurred, Course data unavailable.");
				//Redirect back to page
				try
				{
					response.sendRedirect(request.getContextPath() + "/jsp/courses/courseenrollmentview.jsp");
				}
				catch (IOException e)
				{
					LOG.error("Unable to redirect back to course page after failing to retrieve data",e);
				}
			}
		}
	}

	private void redirectToDBSelect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after Course Retrieval failure", e);
		}
	}
}