package servlets.redirects;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.ModuleConnections;
import mongodbbeans.ModuleBean;
import org.apache.log4j.Logger;

@WebServlet(name = "homeToModuleUpdate")
public class HomeToModuleUpdate extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomeToModuleUpdate.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Get module details passed through params (HomeToModuleUpdate?value=moduleCode)
		String moduleCode = request.getParameter("moduleCode");
		ModuleConnections moduleConn = new ModuleConnections();
		ModuleBean beanToEdit = moduleConn.retrieveSingleModule(moduleCode);
		if (beanToEdit != null)
		{
			request.getSession(true).removeAttribute("moduleErrors");
			request.getSession(true).removeAttribute("moduleSuccess");
			request.getSession(true).setAttribute("moduleCode", moduleCode);
			request.getSession(true).setAttribute("moduleName", beanToEdit.getModuleName());
			request.getSession(true).setAttribute("moduleTutor", beanToEdit.getModuleTutor());
			request.getSession(true).setAttribute("relatedCourse", beanToEdit.getRelatedCourse());
			request.getSession(true).setAttribute("semester", beanToEdit.getSemester());
			request.getSession(true).setAttribute("isCompulsory", beanToEdit.isCompulsory());
			request.getSession(true).setAttribute("moduleStart", beanToEdit.getModuleStart());
			request.getSession(true).setAttribute("moduleEnd", beanToEdit.getModuleEnd());
		}
		else
		{
			request.getSession(true).removeAttribute("moduleErrors");
			request.getSession(true).removeAttribute("moduleSuccess");
			request.getSession(true).removeAttribute("moduleCode");
			request.getSession(true).removeAttribute("moduleName");
			request.getSession(true).removeAttribute("moduleTutor");
			request.getSession(true).removeAttribute("relatedCourse");
			request.getSession(true).removeAttribute("semester");
			request.getSession(true).removeAttribute("isCompulsory");
			request.getSession(true).removeAttribute("moduleStart");
			request.getSession(true).removeAttribute("moduleEnd");
		}
		//Just A Redirect
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/modules/moduleedit.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect to module edit page.",e);
		}
	}
}