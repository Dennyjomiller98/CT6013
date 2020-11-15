package servlets.mongoservlets.redirects;

import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.ModuleConnections;
import mongodbbeans.ModuleBean;
import org.apache.log4j.Logger;

@WebServlet(name = "homeToModuleView")
public class HomeToModuleView extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomeToModuleView.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Get All Modules under provided course
		String courseCode = request.getParameter("courseCode");
		ModuleConnections moduleConn = new ModuleConnections();
		List<ModuleBean> allModules = moduleConn.retrieveAllModulesOnCourse(courseCode);
		request.getSession(true).setAttribute("allModules", allModules);
		request.getSession(true).removeAttribute("moduleCode");
		request.getSession(true).removeAttribute("moduleSuccess");
		request.getSession(true).removeAttribute("moduleErrors");

		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/modules/moduleview.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect to module view page.",e);
		}
	}
}