package servlets.redirects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.ModuleConnections;
import beans.ModuleBean;
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
		if (courseCode != null)
		{
			if(request.getSession(true).getAttribute("DBSELECTION") != null)
			{
				String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
				List<ModuleBean> allModules = new ArrayList<>();
				if(dbSelection.equalsIgnoreCase("MONGODB"))
				{
					ModuleConnections moduleConn = new ModuleConnections();
					allModules = moduleConn.retrieveAllModulesOnCourse(courseCode);

				}
				else if (dbSelection.equalsIgnoreCase("ORACLE"))
				{
					oracle.ModuleConnections moduleConn = new oracle.ModuleConnections();
					allModules = moduleConn.retrieveAllModulesOnCourse(courseCode);
				}
				else
				{
					//No DB selection
					LOG.error("Unknown database choice, returning to DB select page.");
					redirectToDBSelect(request, response);
				}

				request.getSession(true).setAttribute("allModules", allModules);
				request.getSession(true).removeAttribute("moduleCode");
				request.getSession(true).removeAttribute("moduleSuccess");
				request.getSession(true).removeAttribute("moduleErrors");
			}
			else
			{
				//No DB selection
				LOG.error("Unknown database choice, returning to DB select page.");
				redirectToDBSelect(request, response);
			}
		}
		else
		{
			request.getSession(true).removeAttribute("allModules");
			request.getSession(true).removeAttribute("moduleCode");
			request.getSession(true).removeAttribute("moduleSuccess");
			request.getSession(true).removeAttribute("moduleErrors");
		}

		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/modules/moduleview.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect to module view page.",e);
		}
	}

	private void redirectToDBSelect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after module view redirect failure", e);
		}
	}
}