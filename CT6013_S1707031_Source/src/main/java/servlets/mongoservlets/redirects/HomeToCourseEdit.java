package servlets.mongoservlets.redirects;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.CourseConnections;
import mongodbbeans.CourseBean;
import org.apache.log4j.Logger;

@WebServlet(name = "homeToCourseEdit")
public class HomeToCourseEdit extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomeToCourseEdit.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Get course details passed through params (HomeToCourseEdit?value=courseCode)
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
		//Just A Redirect
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/courses/courseedit.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect to course edit page.",e);
		}
	}
}