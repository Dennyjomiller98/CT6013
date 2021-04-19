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
		//Logout session attr's
		request.getSession(true).removeAttribute("pword");
		request.getSession(true).removeAttribute("email");
		request.getSession(true).removeAttribute("error");

		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/homepage.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after logout", e);
		}
	}
}
