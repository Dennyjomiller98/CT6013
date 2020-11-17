package servlets.mark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.EnrollmentConnections;
import mongodb.MarkConnections;
import mongodb.ModuleConnections;
import mongodbbeans.EnrollmentBean;
import mongodbbeans.MarkBean;
import mongodbbeans.ModuleBean;
import org.apache.log4j.Logger;

@WebServlet(name = "markView")
public class MarkView extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(MarkView.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		List<EnrollmentBean> enrollmentToReturn = new ArrayList<>();

		LOG.debug("retrieving students in module/course for marks addition/view");
		//Retrieve Module students based on user input
		ModuleBean moduleBean = new ModuleBean();
		moduleBean.setModuleCode(request.getParameter("moduleSelect"));
		ModuleConnections moduleConn = new ModuleConnections();
		if(moduleBean.getModuleCode().equalsIgnoreCase("*"))
		{
			//Retrieve ALL modules
			retrieveAllModules(request, enrollmentToReturn, moduleConn);
		}
		else
		{
			retrieveSingleModule(request, enrollmentToReturn, moduleBean, moduleConn);
		}

		//Redirect to marks addition page to let teachers see students marks/not marked yet
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/marks/marksaddition.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect back to marks addition page after module retrieval",e);
		}
	}

	private void retrieveSingleModule(HttpServletRequest request, List<EnrollmentBean> enrollmentToReturn, ModuleBean moduleBean, ModuleConnections moduleConn)
	{
		ModuleBean module = moduleConn.retrieveSingleModule(moduleBean.getModuleCode());
		if(module != null)
		{
			getModule(request, enrollmentToReturn, module);

		}
		else
		{
			LOG.debug("Unable to find module with code:" + moduleBean.getModuleCode());
			//Remove session attributes
			request.getSession(true).removeAttribute("singleMarkBean");
			request.getSession(true).removeAttribute("singleEnrollmentToReturn");
			request.getSession(true).removeAttribute("allMarkBeans");
			request.getSession(true).removeAttribute("allEnrollmentToReturn");
			request.getSession(true).removeAttribute("markSuccess");
			request.getSession(true).setAttribute("markErrors", "Unable to find module enrollment information");
		}
	}

	private void getModule(HttpServletRequest request, List<EnrollmentBean> enrollmentToReturn, ModuleBean module)
	{
		//Now get enrollment values for all module codes
		EnrollmentConnections enrollmentConn = new EnrollmentConnections();
		List<EnrollmentBean> allEnrollments = enrollmentConn.retrieveAllEnrollments();
		for (EnrollmentBean enrollBean : allEnrollments)
		{
			String moduleSelections = enrollBean.getModuleSelections();
			String[] split = moduleSelections.split(",");
			for (String s : split)
			{
				if(module.getModuleCode().equalsIgnoreCase(s))
				{
					//Add the enrollment details.
					enrollmentToReturn.add(enrollBean);
				}
			}
		}

		//Get markBeans for single module enrollments
		List<MarkBean> allMarkBeans = new ArrayList<>();
		for (EnrollmentBean enrollmentBean : enrollmentToReturn)
		{
			String studentEmail = enrollmentBean.getStudentEmail();
			String moduleSelections = enrollmentBean.getModuleSelections();
			String[] split = moduleSelections.split(",");
			MarkConnections markConn = new MarkConnections();
			for (String s : split)
			{
				MarkBean markBean = markConn.retrieveMarksForStudentInModule(studentEmail, s);
				allMarkBeans.add(markBean);
			}
		}

		//Session attributes
		request.getSession(true).removeAttribute("allMarkBeans");
		request.getSession(true).removeAttribute("allEnrollmentToReturn");
		request.getSession(true).setAttribute("singleMarkBean", allMarkBeans);
		request.getSession(true).setAttribute("singleEnrollmentToReturn", enrollmentToReturn);
		request.getSession(true).setAttribute("markSuccess", "Student marks retrieved successfully");
		request.getSession(true).removeAttribute("markErrors");
	}

	private void retrieveAllModules(HttpServletRequest request, List<EnrollmentBean> enrollmentToReturn, ModuleConnections moduleConn)
	{
		LOG.debug("All modules to be retrieved");
		List<ModuleBean> allModules = moduleConn.retrieveAllModules();
		EnrollmentConnections enrollmentConn = new EnrollmentConnections();
		List<EnrollmentBean> allEnrollments = enrollmentConn.retrieveAllEnrollments();
		for (ModuleBean aModule : allModules)
		{
			//Now get enrollment values for all module codes
			for (EnrollmentBean enrollBean : allEnrollments)
			{
				String moduleSelections = enrollBean.getModuleSelections();
				String[] split = moduleSelections.split(",");
				for (String s : split)
				{
					if(aModule.getModuleCode().equalsIgnoreCase(s))
					{
						//Add the enrollment details.
						enrollmentToReturn.add(enrollBean);
					}
				}
			}
		}

		//Check if there are any mark details linked to the student
		List<MarkBean> allMarkBeans = new ArrayList<>();
		LOG.debug("Added enrollmentBeans, now finding existing Mark data");
		for (EnrollmentBean enrollmentBean : enrollmentToReturn)
		{
			String studentEmail = enrollmentBean.getStudentEmail();
			String moduleSelections = enrollmentBean.getModuleSelections();
			String[] split = moduleSelections.split(",");
			MarkConnections markConn = new MarkConnections();
			for (String module : split)
			{
				MarkBean markBean = markConn.retrieveMarksForStudentInModule(studentEmail, module);
				allMarkBeans.add(markBean);
			}
		}

		//Put enrollment and mark data into session attributes
		request.getSession(true).setAttribute("allMarkBeans", allMarkBeans);
		request.getSession(true).setAttribute("allEnrollmentToReturn", enrollmentToReturn);
		request.getSession(true).removeAttribute("singleMarkBean");
		request.getSession(true).removeAttribute("singleEnrollmentToReturn");
		request.getSession(true).setAttribute("markSuccess", "Student marks for all modules retrieved successfully");
		request.getSession(true).removeAttribute("markErrors");
	}
}