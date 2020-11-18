package servlets.redirects;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.CourseConnections;
import beans.CourseBean;
import org.apache.log4j.Logger;

@WebServlet(name = "homeToCourseEnrollmentView")
public class HomeToCourseEnrollmentView extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomeToCourseEnrollmentView.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Remove any course attributes or table(s) will show regardless.
		removeLingeringCourseSessionAttributes(request);
		String courseCode = request.getParameter("courseCode");
		CourseConnections courseConn = new CourseConnections();
		CourseBean beanToEdit = courseConn.retrieveSingleCourse(courseCode);
		if (beanToEdit != null)
		{
			request.getSession(true).removeAttribute("allCourses");
			request.getSession(true).setAttribute("courseCode", courseCode);
			request.getSession(true).setAttribute("courseName", beanToEdit.getCourseName());
			request.getSession(true).setAttribute("courseTutor", beanToEdit.getCourseTutor());
			request.getSession(true).setAttribute("courseStart", beanToEdit.getCourseStart());
			request.getSession(true).setAttribute("courseEnd", beanToEdit.getCourseEnd());
		}
		else
		{
			request.getSession(true).removeAttribute("allCourses");
			request.getSession(true).removeAttribute("courseCode");
			request.getSession(true).removeAttribute("courseName");
			request.getSession(true).removeAttribute("courseTutor");
			request.getSession(true).removeAttribute("courseStart");
			request.getSession(true).removeAttribute("courseEnd");
		}

		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/courses/courseenrollmentview.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect to view course enrollment page.",e);
		}
	}

	private void removeLingeringCourseSessionAttributes(HttpServletRequest request)
	{
		request.removeAttribute("allCourses");
		request.removeAttribute("courseCode");
		request.removeAttribute("courseName");
		request.removeAttribute("courseTutor");
		request.removeAttribute("courseStart");
		request.removeAttribute("courseEnd");
		request.getSession(true).removeAttribute("courseSuccess");
		request.getSession(true).removeAttribute("courseErrors");
	}
}