package servlets;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class Logout extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(Logout.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		request.getSession(true).removeAttribute("success");
		request.getSession(true).removeAttribute("errors");

		//Logout session attr's
		request.getSession(true).removeAttribute("pword");
		request.getSession(true).removeAttribute("email");

		//Logout view page attr's
		request.getSession(true).removeAttribute("allTutors");
		request.getSession(true).removeAttribute("allCourses");

		request.getSession(true).removeAttribute("doQuery");
		request.getSession(true).removeAttribute("results");

		request.getSession(true).removeAttribute("selectedQuery");
		request.getSession(true).removeAttribute("selectedYear");
		request.getSession(true).removeAttribute("selectedCourse");
		request.getSession(true).removeAttribute("selectedTutor");

		request.getSession(true).removeAttribute("studentsBeans");
		request.getSession(true).removeAttribute("assignmentsBeans");
		request.getSession(true).removeAttribute("enrollmentsBeans");

		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/homepage.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after logout", e);
		}
	}
}
