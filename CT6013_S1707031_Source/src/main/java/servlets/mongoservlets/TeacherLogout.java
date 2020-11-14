package servlets.mongoservlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(name = "mongoTeacherLogout")
public class TeacherLogout extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(TeacherLogout.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		removeSessionAttributes(request);
		//Redirect to MongoDB Homepage
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/mongohomepage.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect after Logout", e);
		}

	}

	private void removeSessionAttributes(HttpServletRequest request)
	{
		//Remove Session Atributes/Logout
		request.getSession(true).removeAttribute("teacherID");
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
	}
}