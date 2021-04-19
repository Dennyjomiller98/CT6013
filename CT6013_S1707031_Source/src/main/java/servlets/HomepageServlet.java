package servlets;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class HomepageServlet extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomepageServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		handleHomepageSelection(request, response);
	}

	private void handleHomepageSelection(HttpServletRequest request, HttpServletResponse response)
	{
		//Assert selection of homepage
		boolean haveSelected=false;
		String choice = request.getParameter("login");
		if (choice != null && !choice.equals("") && choice.equals("login")) {
			try {
				haveSelected = true;
				response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
			} catch (IOException e) {
				LOG.error("Failure to redirect to Login Page", e);
			}
		}
		String regChoice = request.getParameter("register");
		if (regChoice != null && !regChoice.equals("") && regChoice.equals("register"))
		{
			try {
				haveSelected = true;
				response.sendRedirect(request.getContextPath() + "/jsp/register.jsp");
			} catch (IOException e) {
				LOG.error("Failure to redirect to Register Page", e);
			}
		}

		//Default Fallback - go back to homepage
		if(!haveSelected)
		{
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/homepage.jsp");
			}
			catch (IOException e)
			{
				LOG.error("No Action given. Returning to default homepage", e);
			}
		}
	}
}
