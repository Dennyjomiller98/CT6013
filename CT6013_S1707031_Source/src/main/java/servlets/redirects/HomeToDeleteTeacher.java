package servlets.redirects;

import beans.CourseBean;
import beans.ModuleBean;
import beans.TeacherBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.CourseConnections;
import mongodb.ModuleConnections;
import mongodb.TeacherConnections;
import org.apache.log4j.Logger;

@WebServlet(name = "homeToDeleteTeacher")
public class HomeToDeleteTeacher extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomeToDeleteTeacher.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		LOG.debug("Attempting Teacher Deletion");
		String teacherEmail = request.getParameter("teacherEmail");
		if (teacherEmail != null)
		{
			//Find related teacher
			if (request.getSession(true).getAttribute("DBSELECTION") != null)
			{
				String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
				attemptTeacherDeletion(request, response, dbSelection, teacherEmail);
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
			//No student to delete?
			LOG.debug("Cannot delete teacher when no teacher provided");
			request.getSession(true).setAttribute("editErrors", "Unable to Delete teacher.");
			request.getSession(true).removeAttribute("updateSuccess");
			redirectMe(request, response);
		}
	}

	private void attemptTeacherDeletion(HttpServletRequest request, HttpServletResponse response, String dbSelection, String teacherEmail)
	{
		//Find Teacher
		LOG.debug("Finding teacher linked to: " + teacherEmail);
		TeacherBean teacherBean = null;
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			TeacherConnections conn = new TeacherConnections();
			teacherBean = conn.retrieveSingleTeacher(teacherEmail);
		}
		else if(dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.TeacherConnections conn = new oracle.TeacherConnections();
			teacherBean = conn.retrieveSingleTeacher(teacherEmail);
		}
		else
		{
			//No DB selection
			LOG.error("Unknown database choice, returning to DB select page.");
			redirectToDBSelect(request, response);
		}
		if(teacherBean != null)
		{
			teacherBeanChecks(request, response, dbSelection, teacherBean);
		}
		else
		{
			LOG.error("No Teacher Found");
			request.getSession(true).setAttribute("editErrors", "Unable to Delete Teacher.");
			request.getSession(true).removeAttribute("updateSuccess");
			redirectMe(request, response);
		}

	}

	private void teacherBeanChecks(HttpServletRequest request, HttpServletResponse response, String dbSelection, TeacherBean teacherBean)
	{
		//Must check if they are Module Tutor or Course Tutor. If yes, can not delete
		boolean amITutor = false;
		List<ModuleBean> allModules = new ArrayList<>();
		List<CourseBean> allCourses = new ArrayList<>();
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			ModuleConnections moduleConn = new ModuleConnections();
			allModules = moduleConn.retrieveAllModules();

			CourseConnections courseConn = new CourseConnections();
			allCourses = courseConn.retrieveAllCourses();
		}
		else if(dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.ModuleConnections moduleConn = new oracle.ModuleConnections();
			allModules = moduleConn.retrieveAllModules();

			oracle.CourseConnections courseConn = new oracle.CourseConnections();
			allCourses = courseConn.retrieveAllCourses();
		}
		for (CourseBean courseBean : allCourses)
		{
			if (courseBean.getCourseTutor().equalsIgnoreCase(teacherBean.getEmail()))
			{
				amITutor = true;
				break;
			}
		}
		for (ModuleBean moduleBean : allModules)
		{
			if(moduleBean.getModuleTutor().equalsIgnoreCase(teacherBean.getEmail()))
			{
				amITutor = true;
				break;
			}
		}

		if(!amITutor)
		{
			LOG.debug("Safe to delete Teacher");
			deleteTeacher(request, response, dbSelection, teacherBean);
		}
		else
		{
			//Unable to delete teacher
			LOG.debug("Unable to delete teacher, they are still a tutor");
			request.getSession(true).setAttribute("editErrors", "Unable to Delete Teacher, is a Course or Module Tutor.");
			redirectMe(request, response);
		}
	}

	private void deleteTeacher(HttpServletRequest request, HttpServletResponse response, String dbSelection, TeacherBean teacherBean)
	{
		LOG.debug("Removing teacher details from teacher table");
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			TeacherConnections teacherConn = new TeacherConnections();
			teacherConn.deleteTeacher(teacherBean.getEmail());
		}
		else if(dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.TeacherConnections teacherConn = new oracle.TeacherConnections();
			teacherConn.deleteTeacher(teacherBean.getEmail());
		}
		request.getSession(true).removeAttribute("editErrors");
		request.getSession(true).setAttribute("deleteSuccess", "Teacher Successfully Deleted.");
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/mongohomepage.jsp");
			} catch (IOException e) {
				LOG.error("Failure to redirect after teacher deletion redirect", e);
			}
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/oraclehomepage.jsp");
			} catch (IOException e) {
				LOG.error("Failure to redirect after teacher deletion redirect", e);
			}
		}
	}

	private void redirectMe(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/teachers/teacherindex.jsp");
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
			LOG.error("Failure to redirect after teacher delete failure", e);
		}
	}
}
