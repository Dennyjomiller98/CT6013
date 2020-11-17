package servlets.redirects;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(name = "homeToTeacherLogin")
public class HomeToTeacherLogin extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomeToTeacherLogin.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Just A Redirect
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/teachers/teacherlogin.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect to teacher login page.",e);
		}
	}
}