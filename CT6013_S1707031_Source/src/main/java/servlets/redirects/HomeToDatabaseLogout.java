package servlets.redirects;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(name = "homeToDatabaseLogout")
public class HomeToDatabaseLogout extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomeToDatabaseLogout.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Remove all session attributes
		removeLingeringAttributes(request);
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect to Main DbSelect page.",e);
		}
	}

	private void removeLingeringAttributes(HttpServletRequest request)
	{
		request.getSession(true).removeAttribute("registrationErrors");
		request.getSession(true).removeAttribute("loginErrors");
		request.getSession(true).removeAttribute("firstname");
		request.getSession(true).removeAttribute("surname");
		request.getSession(true).removeAttribute("email");
		request.getSession(true).removeAttribute("dob");
		request.getSession(true).removeAttribute("address1");
		request.getSession(true).removeAttribute("address2");
		request.getSession(true).removeAttribute("city");
		request.getSession(true).removeAttribute("postcode");
		request.getSession(true).removeAttribute("pword");
		request.getSession(true).removeAttribute("isEnrolled");
		request.getSession(true).removeAttribute("isTeacher");
		request.getSession(true).removeAttribute("updateSuccess");
		request.getSession(true).removeAttribute("editErrors");
		request.getSession(true).removeAttribute("courseSuccess");
		request.getSession(true).removeAttribute("courseErrors");
		request.getSession(true).removeAttribute("allCourses");
		request.getSession(true).removeAttribute("courseCode");
		request.getSession(true).removeAttribute("courseName");
		request.getSession(true).removeAttribute("courseTutor");
		request.getSession(true).removeAttribute("courseStart");
		request.getSession(true).removeAttribute("courseEnd");
		request.getSession(true).removeAttribute("allModules");
		request.getSession(true).removeAttribute("moduleSuccess");
		request.getSession(true).removeAttribute("moduleErrors");
		request.getSession(true).removeAttribute("moduleCode");
		request.getSession(true).removeAttribute("moduleName");
		request.getSession(true).removeAttribute("moduleTutor");
		request.getSession(true).removeAttribute("relatedCourse");
		request.getSession(true).removeAttribute("semester");
		request.getSession(true).removeAttribute("isCompulsory");
		request.getSession(true).removeAttribute("moduleStart");
		request.getSession(true).removeAttribute("moduleEnd");
		request.getSession(true).removeAttribute("enrollSuccess");
		request.getSession(true).removeAttribute("enrollErrors");
		request.getSession(true).removeAttribute("enrollStudent");
		request.getSession(true).removeAttribute("enrollCourse");
		request.getSession(true).removeAttribute("enrollModules");
		request.getSession(true).removeAttribute("markSuccess");
		request.getSession(true).removeAttribute("markErrors");
		request.getSession(true).removeAttribute("allMarks");
		request.getSession(true).removeAttribute("markModule");
		request.getSession(true).removeAttribute("markStudent");
		request.getSession(true).removeAttribute("markTeacher");
		request.getSession(true).removeAttribute("markGrade");
		request.getSession(true).removeAttribute("allMarkModules");
		request.getSession(true).removeAttribute("allMarkBeans");
		request.getSession(true).removeAttribute("allEnrollmentToReturn");
		request.getSession(true).removeAttribute("singleMarkBean");
		request.getSession(true).removeAttribute("singleEnrollmentToReturn");
		request.getSession(true).removeAttribute("deleteSuccess");

		request.getSession(true).removeAttribute("DBSELECTION");
	}
}