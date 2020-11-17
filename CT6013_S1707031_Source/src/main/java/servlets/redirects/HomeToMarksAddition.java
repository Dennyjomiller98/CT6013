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
import mongodbbeans.CourseBean;
import mongodbbeans.ModuleBean;
import org.apache.log4j.Logger;

@WebServlet(name = "homeToMarksAddition")
public class HomeToMarksAddition extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomeToMarksAddition.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Marks/Reports page. Only list valid modules that the tutor is in charge of.
		String email = request.getParameter("email");
		if(email != null)
		{
			List<ModuleBean> beansToReturn = new ArrayList<>();
			List<String> courseCodesLinkedToTeacher = new ArrayList<>();
			LOG.debug("Getting all modules relating to Teacher:" + email);
			//Assert if email is a course teacher (access to all modules on course) or a module teacher
			CourseConnections courseConn = new CourseConnections();
			List<CourseBean> courseBeans = courseConn.retrieveAllCourses();
			for (CourseBean courseBean : courseBeans)
			{
				if(courseBean.getCourseTutor().equalsIgnoreCase(email))
				{
					//Match, get all modules
					ModuleConnections moduleConn = new ModuleConnections();
					List<ModuleBean> allModules = moduleConn.retrieveAllModulesOnCourse(courseBean.getCourseCode());
					beansToReturn.addAll(allModules);
					courseCodesLinkedToTeacher.add(courseBean.getCourseCode());
				}
			}

			//Now assert if they are a module tutor, but NOT on a module involved in a course already added

			LOG.debug("Getting single modules that teacher may be a Module leader for");
			ModuleConnections moduleConn = new ModuleConnections();
			List<ModuleBean> allModules = moduleConn.retrieveAllModules();
			for (ModuleBean moduleBean : allModules)
			{
				if(moduleBean.getModuleTutor().equalsIgnoreCase(email) && !courseCodesLinkedToTeacher.contains(moduleBean.getRelatedCourse()))
				{
					beansToReturn.add(moduleBean);
				}
			}

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
}