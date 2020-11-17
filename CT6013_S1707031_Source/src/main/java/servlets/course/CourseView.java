package servlets.course;

import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.CourseConnections;
import mongodbbeans.CourseBean;
import org.apache.log4j.Logger;

@WebServlet(name = "courseEnrollmentView")
public class CourseView extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(CourseView.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Retrieve Course details on selected course and set all modules available
		CourseBean courseBean = new CourseBean();
		courseBean.setCourseCode(request.getParameter("courseSelect"));
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
}