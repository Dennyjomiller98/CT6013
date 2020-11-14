package servlets.mongoservlets.redirects;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(name = "homeToCourseEnrollmentView")
public class HomeToCourseEnrollmentView extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomeToCourseEnrollmentView.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Just A Redirect
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/courses/courseenrollmentview.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect to view course enrollment page.",e);
		}
	}
}