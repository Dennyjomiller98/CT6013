package servlets.module;

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

@WebServlet(name = "moduleView")
public class ModuleView extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(ModuleView.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		LOG.debug("retrieving modules");
		//Retrieve Module details based on user input
		ModuleBean moduleBean = new ModuleBean();
		moduleBean.setModuleCode(request.getParameter("moduleSelect"));

		if(request.getSession(true).getAttribute("DBSELECTION") != null)
		{
			String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();

			if (moduleBean.getModuleCode().equalsIgnoreCase("*"))
			{
				//Retrieve ALL modules
				retrieveAllModules(request, response, dbSelection);
			}
			else
			{
				//Retrieve single module
				retrieveSingleModule(request, response, moduleBean, dbSelection);
			}

			//Redirect to view module page
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/modules/moduleview.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect back to module view page after module retrieval", e);
			}
		}
		else
		{
			LOG.error("No DB selected");
			redirectToDBSelect(request, response);
		}
	}

	private void retrieveSingleModule(HttpServletRequest request, HttpServletResponse response, ModuleBean moduleBean, String dbSelection)
	{
		ModuleBean bean = null;
		LOG.debug("Single module to be retrieved: " + moduleBean.getModuleCode());
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			ModuleConnections moduleConn = new ModuleConnections();
			bean = moduleConn.retrieveSingleModule(moduleBean.getModuleCode());
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.ModuleConnections moduleConn = new oracle.ModuleConnections();
			bean = moduleConn.retrieveSingleModule(moduleBean.getModuleCode());
		}
		else
		{
			//No DB selection
			LOG.error("Unknown database choice, returning to DB select page.");
			redirectToDBSelect(request, response);
		}

		if (bean != null)
		{
			//Set session attributes
			request.getSession(true).removeAttribute("allModules");
			request.getSession(true).setAttribute("moduleCode", bean.getModuleCode());
			request.getSession(true).setAttribute("moduleName", bean.getModuleName());
			request.getSession(true).setAttribute("moduleTutor", bean.getModuleTutor());
			request.getSession(true).setAttribute("relatedCourse", bean.getRelatedCourse());
			request.getSession(true).setAttribute("semester", bean.getSemester());
			request.getSession(true).setAttribute("isCompulsory", bean.isCompulsory());
			request.getSession(true).setAttribute("moduleStart", bean.getModuleStart());
			request.getSession(true).setAttribute("moduleEnd", bean.getModuleEnd());
			request.getSession(true).removeAttribute("moduleErrors");
			request.getSession(true).setAttribute("moduleSuccess", "Module retrieved successfully");
		}
		else
		{
			LOG.error("Unable to find module in DB despite being shown in the view module dropdown");
			request.getSession(true).removeAttribute("allModules");
			request.getSession(true).removeAttribute("moduleSuccess");
			request.getSession(true).setAttribute("moduleErrors", "Unable to retrieve selected module");
		}
	}

	private void retrieveAllModules(HttpServletRequest request, HttpServletResponse response, String dbSelection)
	{
		LOG.debug("All modules to be retrieved");
		List<ModuleBean> allModules = new ArrayList<>();
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			ModuleConnections moduleConn = new ModuleConnections();
			allModules = moduleConn.retrieveAllModules();
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.ModuleConnections moduleConn = new oracle.ModuleConnections();
			allModules = moduleConn.retrieveAllModules();
		}
		else
		{
			//No DB selection
			LOG.error("Unknown database choice, returning to DB select page.");
			redirectToDBSelect(request, response);
		}

		//Remove single module attributes, add allModules
		request.getSession(true).removeAttribute("moduleCode");
		request.getSession(true).removeAttribute("moduleName");
		request.getSession(true).removeAttribute("moduleTutor");
		request.getSession(true).removeAttribute("relatedCourse");
		request.getSession(true).removeAttribute("semester");
		request.getSession(true).removeAttribute("isCompulsory");
		request.getSession(true).removeAttribute("moduleStart");
		request.getSession(true).removeAttribute("moduleEnd");
		request.getSession(true).setAttribute("allModules", allModules);
		request.getSession(true).removeAttribute("moduleErrors");
		request.getSession(true).setAttribute("moduleSuccess", "All Modules retrieved successfully");
	}

	private void redirectToDBSelect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after Module View failure", e);
		}
	}
}