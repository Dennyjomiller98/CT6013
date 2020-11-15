package servlets.mongoservlets.redirects;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(name = "homeToMarksAddition")
public class HomeToMarksAddition extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomeToMarksAddition.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/marks/marksaddition.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect to add Marks page.", e);
		}
	}
}