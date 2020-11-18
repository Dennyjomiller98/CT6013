package servlets.module;

import beans.ModuleBean;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.ModuleConnections;
import org.apache.log4j.Logger;
import org.bson.Document;

@WebServlet(name = "moduleAddition")
public class ModuleAddition extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(ModuleAddition.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
		if(request.getSession(true).getAttribute("DBSELECTION") != null)
		{
			String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
			ModuleBean beanOfExistingModule = null;
			if(dbSelection.equalsIgnoreCase("MONGODB"))
			{
				ModuleConnections moduleConn = new ModuleConnections();
				beanOfExistingModule = moduleConn.retrieveSingleModule(request.getParameter("moduleCode"));
			}
			else if (dbSelection.equalsIgnoreCase("ORACLE"))
			{
				oracle.ModuleConnections moduleConn = new oracle.ModuleConnections();
				beanOfExistingModule = moduleConn.retrieveSingleModule(request.getParameter("moduleCode"));
			}
			else
			{
				//No DB selection
				LOG.error("Unknown database choice, returning to DB select page.");
				redirectToDBSelect(request, response);
			}

			if (beanOfExistingModule == null)
			{
				addModuleToCorrectDB(request, response, moduleToAdd, dbSelection);
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
				catch (IOException e)
				{
					LOG.error("Failure redirecting after module addition failure (Module already existed)");
				}
			}
		}
		else
		{
			LOG.error("No DB selected");
			redirectToDBSelect(request, response);
		}
	}

	private void addModuleToCorrectDB(HttpServletRequest request, HttpServletResponse response, Document moduleToAdd, String dbSelection)
	{
		//Store values into DB
		LOG.debug("Attempting to store new Module in DB");
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			ModuleConnections moduleConn = new ModuleConnections();
			moduleConn.addModule(moduleToAdd);
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.ModuleConnections moduleConn = new oracle.ModuleConnections();
			moduleConn.addModule(moduleToAdd);
		}

		try
		{
			request.getSession(true).removeAttribute("moduleErrors");
			request.getSession(true).setAttribute("moduleSuccess", "Module Added Successfully");
			response.sendRedirect(request.getContextPath() + "/jsp/modules/moduleaddition.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Failure redirecting after module successfully added to DB");
		}
	}

	private void redirectToDBSelect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after Module Addition failure", e);
		}
	}
}