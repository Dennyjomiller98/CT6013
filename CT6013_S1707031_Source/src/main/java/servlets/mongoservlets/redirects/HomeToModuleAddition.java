package servlets.mongoservlets.redirects;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(name = "homeToModuleAddition")
public class HomeToModuleAddition extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomeToModuleAddition.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Just A Redirect
		try
		{
			request.getSession(true).removeAttribute("moduleSuccess");
			request.getSession(true).removeAttribute("moduleErrors");
			response.sendRedirect(request.getContextPath() + "/jsp/modules/moduleaddition.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect to add module page.",e);
		}
	}
}
