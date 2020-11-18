package servlets.redirects;

import beans.EnrollmentBean;
import beans.ModuleBean;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.EnrollmentConnections;
import mongodb.ModuleConnections;
import org.apache.log4j.Logger;

@WebServlet(name = "homeToDeleteModule")
public class HomeToDeleteModule extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomeToDeleteModule.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		LOG.debug("Attempting Module Deletion");

		String moduleCode = request.getParameter("moduleCode");
		if (moduleCode != null)
		{
			//Find related ModuleBean
			if (request.getSession(true).getAttribute("DBSELECTION") != null)
			{
				String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
				attemptModuleDeletion(request, response, dbSelection, moduleCode);
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
			//No module to delete?
			LOG.debug("Cannot delete module when no module provided");
			request.getSession(true).setAttribute("moduleErrors", "Unable to Delete module.");
			request.getSession(true).removeAttribute("moduleSuccess");
			redirectMe(request, response);
		}
	}

	private void attemptModuleDeletion(HttpServletRequest request, HttpServletResponse response, String dbSelection, String moduleCode)
	{
		//Find module data
		ModuleBean moduleBean = null;
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			ModuleConnections conn = new ModuleConnections();
			moduleBean = conn.retrieveSingleModule(moduleCode);
		}
		else if(dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.ModuleConnections conn = new oracle.ModuleConnections();
			moduleBean = conn.retrieveSingleModule(moduleCode);
		}
		else
		{
			//No DB selection
			LOG.error("Unknown database choice, returning to DB select page.");
			redirectToDBSelect(request, response);
		}

		if (moduleBean != null)
		{
			moduleBeanChecks(request, response, dbSelection, moduleBean);
		}
		else
		{
			LOG.debug("No Module found");
			request.getSession(true).setAttribute("moduleErrors", "Unable to Delete module.");
			request.getSession(true).removeAttribute("moduleSuccess");
			redirectMe(request, response);
		}
	}

	private void moduleBeanChecks(HttpServletRequest request, HttpServletResponse response, String dbSelection, ModuleBean moduleBean)
	{
		//Check Enrollments DB, if module code is in Module_Selections, cannot delete
		boolean canDelete = true;
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			EnrollmentConnections conn = new EnrollmentConnections();
			List<EnrollmentBean> enrollmentBeans = conn.retrieveAllEnrollments();
			for (EnrollmentBean enrollmentBean : enrollmentBeans)
			{
				if(enrollmentBean.getModuleSelections().contains(moduleBean.getModuleCode()))
				{
					canDelete = false;
					break;
				}
			}
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.EnrollmentConnections conn = new oracle.EnrollmentConnections();
			List<EnrollmentBean> enrollmentBeans = conn.retrieveAllEnrollments();
			for (EnrollmentBean enrollmentBean : enrollmentBeans)
			{
				if(enrollmentBean.getModuleSelections().contains(moduleBean.getModuleCode()))
				{
					canDelete = false;
					break;
				}
			}
		}

		if(canDelete)
		{
			deleteModuleFromDB(request, response, dbSelection, moduleBean);
		}
		else
		{
			LOG.debug("Unable to delete, Module is active with student enrollments.");
			request.getSession(true).setAttribute("moduleErrors", "Students are enrolled in this module. Unable to delete module.");
			request.getSession(true).removeAttribute("moduleSuccess");
		}

	}

	private void deleteModuleFromDB(HttpServletRequest request, HttpServletResponse response, String dbSelection, ModuleBean moduleBean)
	{
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			ModuleConnections conn = new ModuleConnections();
			conn.deleteModule(moduleBean.getModuleCode());
			request.getSession(true).removeAttribute("moduleErrors");
			request.getSession(true).setAttribute("deleteSuccess", "Module Successfully Deleted.");
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/oraclehomepage.jsp");
			} catch (IOException e) {
				LOG.error("Failure to redirect after module deletion redirect success", e);
			}
		}
		else if(dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.ModuleConnections conn = new oracle.ModuleConnections();
			conn.deleteModule(moduleBean.getModuleCode());
			request.getSession(true).removeAttribute("moduleErrors");
			request.getSession(true).setAttribute("deleteSuccess", "Module Successfully Deleted.");

			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/mongohomepage.jsp");
			} catch (IOException e) {
				LOG.error("Failure to redirect after module deletion redirect success", e);
			}
		}
	}

	private void redirectToDBSelect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after module delete failure", e);
		}
	}

	private void redirectMe(HttpServletRequest request, HttpServletResponse response)
	{
		LOG.debug("Redirecting to module edit page");
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/modules/moduleedit.jsp");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
