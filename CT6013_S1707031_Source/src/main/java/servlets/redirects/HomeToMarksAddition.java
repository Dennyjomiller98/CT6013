package servlets.redirects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.CourseConnections;
import mongodb.ModuleConnections;
import beans.CourseBean;
import beans.ModuleBean;
import org.apache.log4j.Logger;

@WebServlet(name = "homeToMarksAddition")
public class HomeToMarksAddition extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomeToMarksAddition.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		if(request.getSession(true).getAttribute("DBSELECTION") != null)
		{
			String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
			if(!dbSelection.equalsIgnoreCase("ORACLE") && !dbSelection.equalsIgnoreCase("MONGODB"))
			{
				//No DB selection
				LOG.error("Unknown database choice, returning to DB select page.");
				redirectToDBSelect(request, response);
			}

			//Marks/Reports page. Only list valid modules that the tutor is in charge of.
			String email = request.getParameter("email");
			if (email != null)
			{
				List<ModuleBean> beansToReturn = new ArrayList<>();
				List<String> courseCodesLinkedToTeacher = new ArrayList<>();
				LOG.debug("Getting all modules relating to Teacher:" + email);
				//Assert if email is a course teacher (access to all modules on course) or a module teacher
				checkCourseTutorAccess(email, beansToReturn, courseCodesLinkedToTeacher, dbSelection);

				//Now assert if they are a module tutor, but NOT on a module involved in a course already added

				LOG.debug("Getting single modules that teacher may be a Module leader for");
				checkModuleTutorAccess(email, beansToReturn, courseCodesLinkedToTeacher, dbSelection);

				//Add Modules into session attributes
				request.getSession(true).setAttribute("allMarkModules", beansToReturn);
			}

			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/marks/marksaddition.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect to add Marks page.", e);
			}
		}
		else
		{
			//No DB selection
			LOG.error("Unknown database choice, returning to DB select page.");
			redirectToDBSelect(request, response);
		}
	}

	private void checkModuleTutorAccess(String email, List<ModuleBean> beansToReturn, List<String> courseCodesLinkedToTeacher, String dbSelection)
	{
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

		for (ModuleBean moduleBean : allModules)
		{
			if (moduleBean.getModuleTutor().equalsIgnoreCase(email) && !courseCodesLinkedToTeacher.contains(moduleBean.getRelatedCourse()))
			{
				beansToReturn.add(moduleBean);
			}
		}
	}

	private void checkCourseTutorAccess(String email, List<ModuleBean> beansToReturn, List<String> courseCodesLinkedToTeacher, String dbSelection)
	{
		List<CourseBean> courseBeans;
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			CourseConnections courseConn = new CourseConnections();
			courseBeans = courseConn.retrieveAllCourses();
			for (CourseBean courseBean : courseBeans)
			{
				if (courseBean.getCourseTutor().equalsIgnoreCase(email))
				{
					//Match, get all modules
					ModuleConnections moduleConn = new ModuleConnections();
					List<ModuleBean> allModules = moduleConn.retrieveAllModulesOnCourse(courseBean.getCourseCode());
					beansToReturn.addAll(allModules);
					courseCodesLinkedToTeacher.add(courseBean.getCourseCode());
				}
			}
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.CourseConnections courseConn = new oracle.CourseConnections();
			courseBeans = courseConn.retrieveAllCourses();
			for (CourseBean courseBean : courseBeans)
			{
				if (courseBean.getCourseTutor().equalsIgnoreCase(email))
				{
					//Match, get all modules
					oracle.ModuleConnections moduleConn = new oracle.ModuleConnections();
					List<ModuleBean> allModules = moduleConn.retrieveAllModulesOnCourse(courseBean.getCourseCode());
					beansToReturn.addAll(allModules);
					courseCodesLinkedToTeacher.add(courseBean.getCourseCode());
				}
			}
		}
	}

	private void redirectToDBSelect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after Marks addition redirect failure", e);
		}
	}
}