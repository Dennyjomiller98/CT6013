package servlets.mongoservlets.module;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.ModuleConnections;
import mongodbbeans.ModuleBean;
import org.apache.log4j.Logger;
import org.bson.Document;

@WebServlet(name = "mongoModuleAddition")
public class ModuleAddition extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(ModuleAddition.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//Get form values to save module details to DB
		LOG.debug("Begin Module Addition");
		Document moduleToAdd = new Document();
		moduleToAdd.append("Module_Code", request.getParameter("moduleCode"));
		moduleToAdd.append("Module_Name", request.getParameter("moduleName"));
		moduleToAdd.append("Module_Tutor", request.getParameter("moduleTutor"));
		moduleToAdd.append("Related_Course", request.getParameter("relatedCourse"));
		moduleToAdd.append("Semester", request.getParameter("semester"));
		moduleToAdd.append("Is_Compulsory", request.getParameter("isCompulsory"));
		moduleToAdd.append("Module_Start", request.getParameter("moduleStart"));
		moduleToAdd.append("Module_End", request.getParameter("moduleEnd"));

		//Connect to DB and assert module does not exist
		ModuleConnections moduleConn = new ModuleConnections();
		ModuleBean beanOfExistingModule = moduleConn.retrieveSingleModule(request.getParameter("moduleCode"));
		if(beanOfExistingModule == null)
		{
			//Store values into DB
			LOG.debug("Attempting to store new Module in DB");
			moduleConn.addModule(moduleToAdd);
			try
			{
				request.getSession(true).removeAttribute("moduleErrors");
				request.getSession(true).setAttribute("moduleSuccess", "Module Added Successfully");
				response.sendRedirect(request.getContextPath() + "/jsp/modules/moduleaddition.jsp");
			}
			catch(IOException e)
			{
				LOG.error("Failure redirecting after module successfully added to DB");
			}
		}
		else
		{
			LOG.error("Module already exists");
			try
			{
				request.getSession(true).removeAttribute("moduleSuccess");
				request.getSession(true).setAttribute("moduleErrors", "Module Code already exists in Database");
				response.sendRedirect(request.getContextPath() + "/jsp/modules/moduleaddition.jsp");
			}
			catch(IOException e)
			{
				LOG.error("Failure redirecting after module addition failure (Module already existed)");
			}
		}
	}
}
