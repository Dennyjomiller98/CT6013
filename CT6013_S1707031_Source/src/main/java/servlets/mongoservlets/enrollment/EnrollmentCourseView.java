package servlets.mongoservlets.enrollment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.ModuleConnections;
import mongodbbeans.CourseBean;
import mongodbbeans.ModuleBean;
import org.apache.log4j.Logger;

@WebServlet(name = "mongoEnrollmentCourseView")
public class EnrollmentCourseView extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(EnrollmentCourseView.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		LOG.debug("EnrollmentCourseView, checking if chosen course has related modules");
		CourseBean courseBean = new CourseBean();
		courseBean.setCourseCode(request.getParameter("courseSelect"));
		if(courseBean.getCourseCode().equalsIgnoreCase("-"))
		{
			//No course selected
			LOG.error("No Course selected in student enrollment page");
			request.getSession(true).removeAttribute("allModules");
			request.getSession(true).removeAttribute("enrollSuccess");
			request.getSession(true).removeAttribute("selectedCourse");
			request.getSession(true).setAttribute("enrollErrors", "No Course selected. Please try again.");
		}
		else
		{
			//Get module details linked to selected course
			List<ModuleBean> allModules = new ArrayList<>();
			LOG.debug("attempting to retrieve related modules ");
			ModuleConnections moduleConn = new ModuleConnections();
			List<ModuleBean> allModuleBeans = moduleConn.retrieveAllModules();
			for (ModuleBean moduleBean : allModuleBeans)
			{
				if(moduleBean.getRelatedCourse().equalsIgnoreCase(courseBean.getCourseCode()))
				{
					allModules.add(moduleBean);
				}
			}
			//set session attributes
			request.getSession(true).removeAttribute("enrollErrors");
			request.getSession(true).setAttribute("enrollSuccess", "Modules for course " + courseBean.getCourseCode() + " retrieved.");
			request.getSession(true).setAttribute("allModules", allModules);
			request.getSession(true).setAttribute("selectedCourse", courseBean.getCourseCode());
		}

		//Redirect to Enrollment page
		LOG.debug("Returning to student enrollment page");
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/courses/courseenrollment.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect back to student enrollment page",e);
		}
	}
}