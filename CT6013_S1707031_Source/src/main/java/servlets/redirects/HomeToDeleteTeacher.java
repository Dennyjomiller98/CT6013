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
		removeTeacherSessionAttributes(request);
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

	private void removeTeacherSessionAttributes(HttpServletRequest request)
	{
		request.getSession(true).removeAttribute("registrationErrors");
		request.getSession(true).removeAttribute("loginErrors");
		request.getSession(true).removeAttribute("firstname");
		request.getSession(true).removeAttribute("surname");
		request.getSession(true).removeAttribute("email");
		request.getSession(true).removeAttribute("dob");
		request.getSession(true).removeAttribute("address1");
		request.getSession(true).removeAttribute("address2");
		request.getSession(true).removeAttribute("city");
		request.getSession(true).removeAttribute("postcode");
		request.getSession(true).removeAttribute("pword");
		request.getSession(true).removeAttribute("isEnrolled");
		request.getSession(true).removeAttribute("isTeacher");
		request.getSession(true).removeAttribute("updateSuccess");
		request.getSession(true).removeAttribute("editErrors");
		request.getSession(true).removeAttribute("courseSuccess");
		request.getSession(true).removeAttribute("courseErrors");
		request.getSession(true).removeAttribute("allCourses");
		request.getSession(true).removeAttribute("courseCode");
		request.getSession(true).removeAttribute("courseName");
		request.getSession(true).removeAttribute("courseTutor");
		request.getSession(true).removeAttribute("courseStart");
		request.getSession(true).removeAttribute("courseEnd");
		request.getSession(true).removeAttribute("allModules");
		request.getSession(true).removeAttribute("moduleSuccess");
		request.getSession(true).removeAttribute("moduleErrors");
		request.getSession(true).removeAttribute("moduleCode");
		request.getSession(true).removeAttribute("moduleName");
		request.getSession(true).removeAttribute("moduleTutor");
		request.getSession(true).removeAttribute("relatedCourse");
		request.getSession(true).removeAttribute("semester");
		request.getSession(true).removeAttribute("isCompulsory");
		request.getSession(true).removeAttribute("moduleStart");
		request.getSession(true).removeAttribute("moduleEnd");
		request.getSession(true).removeAttribute("enrollSuccess");
		request.getSession(true).removeAttribute("enrollErrors");
		request.getSession(true).removeAttribute("enrollStudent");
		request.getSession(true).removeAttribute("enrollCourse");
		request.getSession(true).removeAttribute("enrollModules");
		request.getSession(true).removeAttribute("markSuccess");
		request.getSession(true).removeAttribute("markErrors");
		request.getSession(true).removeAttribute("allMarks");
		request.getSession(true).removeAttribute("markModule");
		request.getSession(true).removeAttribute("markStudent");
		request.getSession(true).removeAttribute("markTeacher");
		request.getSession(true).removeAttribute("markGrade");
		request.getSession(true).removeAttribute("allMarkModules");
		request.getSession(true).removeAttribute("allMarkBeans");
		request.getSession(true).removeAttribute("allEnrollmentToReturn");
		request.getSession(true).removeAttribute("singleMarkBean");
		request.getSession(true).removeAttribute("singleEnrollmentToReturn");
		request.getSession(true).removeAttribute("deleteSuccess");
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
