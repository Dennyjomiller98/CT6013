package servlets.mongoservlets.module;

import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.ModuleConnections;
import mongodbbeans.ModuleBean;
import org.apache.log4j.Logger;

@WebServlet(name = "mongoModuleView")
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
		ModuleConnections moduleConn = new ModuleConnections();
		if(moduleBean.getModuleCode().equalsIgnoreCase("*"))
		{
			//Retrieve ALL modules
			LOG.debug("All modules to be retrieved");
			List<ModuleBean> allModules = moduleConn.retrieveAllModules();
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
		else
		{
			//Retrieve single module
			LOG.debug("Single module to be retrieved: " + moduleBean.getModuleCode());
			ModuleBean bean = moduleConn.retrieveSingleModule(moduleBean.getModuleCode());
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

		//Redirect to view module page
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/modules/moduleview.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect back to module view page after module retrieval",e);
		}
	}
}
