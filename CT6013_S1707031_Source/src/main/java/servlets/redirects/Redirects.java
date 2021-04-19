package servlets.redirects;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class Redirects extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(Redirects.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Just A Redirect
		try
		{
			String action = request.getParameter("action");
			if(action.equals("login"))
			{
				response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
			}
			else if(action.equals("register"))
			{
				response.sendRedirect(request.getContextPath() + "/jsp/register.jsp");
			}
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect.",e);
		}
	}
}
