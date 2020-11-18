package servlets.redirects;

import beans.CourseBean;
import beans.ModuleBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.CourseConnections;
import mongodb.ModuleConnections;
import org.apache.log4j.Logger;

@WebServlet(name = "homeToDeleteCourse")
public class HomeToDeleteCourse extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomeToDeleteCourse.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		LOG.debug("Attempting Course Deletion");

		String courseCode = request.getParameter("courseCode");
		if (courseCode != null)
		{
			//Find related Course
			if (request.getSession(true).getAttribute("DBSELECTION") != null)
			{
				String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
				attemptCourseDeletion(request, response, dbSelection, courseCode);
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
			//No course to delete?
			LOG.debug("Cannot delete course when no course provided");
			request.getSession(true).setAttribute("courseErrors", "Unable to Delete Course.");
			request.getSession(true).removeAttribute("courseSuccess");
			redirectMe(request, response);
		}
	}

	private void attemptCourseDeletion(HttpServletRequest request, HttpServletResponse response, String dbSelection, String courseCode)
	{
		//Find course
		CourseBean courseBean = null;
		if (dbSelection.equalsIgnoreCase("MONGODB"))
		{
			CourseConnections conn = new CourseConnections();
			courseBean = conn.retrieveSingleCourse(courseCode);
		}
		else if(dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.CourseConnections conn = new oracle.CourseConnections();
			courseBean = conn.retrieveSingleCourse(courseCode);
		}
		else
		{
			//No DB selection
			LOG.error("Unknown database choice, returning to DB select page.");
			redirectToDBSelect(request, response);
		}

		if (courseBean != null)
		{
			courseBeanChecks(request, response, dbSelection, courseBean);
		}
		else
		{
			LOG.error("No Course Found");
			request.getSession(true).setAttribute("courseErrors", "Unable to Delete Course.");
			request.getSession(true).removeAttribute("courseSuccess");
			redirectMe(request, response);
		}
	}

	private void courseBeanChecks(HttpServletRequest request, HttpServletResponse response, String dbSelection, CourseBean courseBean)
	{
		LOG.debug("Validating Course information before deletion");
		//Check course modules. If modules exist, unable to delete
		List<ModuleBean> allModules = new ArrayList<>();
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			ModuleConnections conn = new ModuleConnections();
			allModules = conn.retrieveAllModulesOnCourse(courseBean.getCourseCode());
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.ModuleConnections conn = new oracle.ModuleConnections();
			allModules = conn.retrieveAllModulesOnCourse(courseBean.getCourseCode());
		}

		if(allModules != null)
		{
			//Iterate modules,
			boolean linkedModules = false;
			for (ModuleBean moduleBean : allModules)
			{
				if(moduleBean.getRelatedCourse().equalsIgnoreCase(courseBean.getCourseCode()))
				{
					linkedModules = true;
					break;
				}
			}

			if(linkedModules)
			{
				LOG.debug("Unable to delete Course, module is linked");
				LOG.error("No Course Found");
				request.getSession(true).setAttribute("courseErrors", "Unable to Delete Course. Modules exist in course.");
				request.getSession(true).removeAttribute("courseSuccess");
				redirectMe(request, response);
			}
			else
			{
				//No modules linked, delete course
				deleteCourse(request, response, dbSelection, courseBean);
			}
		}
		else
		{
			//No modules linked, delete course
			deleteCourse(request, response, dbSelection, courseBean);
		}

	}

	private void deleteCourse(HttpServletRequest request, HttpServletResponse response, String dbSelection, CourseBean courseBean)
	{
		LOG.debug("Safe deletion of Course with no linked modules");
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			CourseConnections conn = new CourseConnections();
			conn.deleteCourse(courseBean.getCourseCode());
			//Attributes and redirect
			request.getSession(true).removeAttribute("courseErrors");
			request.getSession(true).setAttribute("deleteSuccess", "Course Successfully Deleted.");
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/mongohomepage.jsp");
			} catch (IOException e) {
				LOG.error("Failure to redirect after course deletion redirect success", e);
			}
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.CourseConnections conn = new oracle.CourseConnections();
			conn.deleteCourse(courseBean.getCourseCode());
			//Attributes and redirect
			request.getSession(true).removeAttribute("courseErrors");
			request.getSession(true).setAttribute("deleteSuccess", "Course Successfully Deleted.");
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/mongohomepage.jsp");
			} catch (IOException e) {
				LOG.error("Failure to redirect after course deletion redirect success", e);
			}
		}

	}

	private void redirectMe(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/courses/courseedit.jsp");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void redirectToDBSelect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after course delete failure", e);
		}
	}
}
