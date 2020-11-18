package servlets.redirects;

import beans.StudentBean;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.EnrollmentConnections;
import mongodb.MarkConnections;
import mongodb.StudentConnections;
import org.apache.log4j.Logger;

@WebServlet(name = "homeToDeleteStudent")
public class HomeToDeleteStudent extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(HomeToDeleteStudent.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		LOG.debug("Attempting Student Deletion");
		String studentEmail = request.getParameter("studentEmail");
		if (studentEmail != null)
		{
			//Find related student
			if (request.getSession(true).getAttribute("DBSELECTION") != null)
			{
				String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
				attemptStudentDeletion(request, response, dbSelection, studentEmail);
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
			LOG.debug("Cannot delete student when no student provided");
			request.getSession(true).setAttribute("editErrors", "Unable to Delete student.");
			request.getSession(true).removeAttribute("updateSuccess");
			redirectMe(request, response);
		}
	}

	private void attemptStudentDeletion(HttpServletRequest request, HttpServletResponse response, String dbSelection, String studentEmail)
	{
		//Find student
		StudentBean studentBean = null;
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			StudentConnections conn = new StudentConnections();
			studentBean = conn.retrieveSingleStudent(studentEmail);
		}
		else if(dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.StudentConnections conn = new oracle.StudentConnections();
			studentBean = conn.retrieveSingleStudent(studentEmail);
		}
		else
		{
			//No DB selection
			LOG.error("Unknown database choice, returning to DB select page.");
			redirectToDBSelect(request, response);
		}

		if(studentBean != null)
		{
			studentBeanChecks(request, response, dbSelection, studentBean);
		}
		else
		{
			LOG.error("No Student Found");
			request.getSession(true).setAttribute("editErrors", "Unable to Delete student.");
			request.getSession(true).removeAttribute("updateSuccess");
			redirectMe(request, response);
		}
	}

	private void studentBeanChecks(HttpServletRequest request, HttpServletResponse response, String dbSelection, StudentBean studentBean)
	{
		//If enrolled, remove Enrollment data and remove Mark Data if found
		boolean isEnrolled = studentBean.isEnrolled();
		if(isEnrolled)
		{
			//Remove Enrollment and Marks
			deleteStudentMarksAndEnrollment(dbSelection, studentBean);
		}
		//Remove Student
		deleteStudent(dbSelection, studentBean);

		//Session Attributes and redirect to HomePage
		removeStudentSessionAttributes(request);
		request.getSession(true).removeAttribute("editErrors");
		request.getSession(true).setAttribute("deleteSuccess", "Student Successfully Deleted.");

		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/mongohomepage.jsp");
			} catch (IOException e) {
				LOG.error("Failure to redirect after student deletion redirect success", e);
			}
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/oraclehomepage.jsp");
			} catch (IOException e) {
				LOG.error("Failure to redirect after student deletion redirect success", e);
			}
		}
	}

	private void removeStudentSessionAttributes(HttpServletRequest request)
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

	private void deleteStudentMarksAndEnrollment(String dbSelection, StudentBean studentBean)
	{
		LOG.debug("Removing enrolled student details");
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			MarkConnections markConn = new MarkConnections();
			markConn.deleteMarksForStudent(studentBean.getEmail());

			EnrollmentConnections enrollConn = new EnrollmentConnections();
			enrollConn.deleteEnrollmentForStudent(studentBean.getEmail());
		}
		else if(dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.MarkConnections markConn = new oracle.MarkConnections();
			markConn.deleteMarksForStudent(studentBean.getEmail());

			oracle.EnrollmentConnections enrollConn = new oracle.EnrollmentConnections();
			enrollConn.deleteEnrollmentForStudent(studentBean.getEmail());
		}
	}

	private void deleteStudent(String dbSelection, StudentBean studentBean)
	{
		LOG.debug("Removing student details from student table");
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			StudentConnections studentConn = new StudentConnections();
			studentConn.deleteStudent(studentBean.getEmail());
		}
		else if(dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.StudentConnections studentConn = new oracle.StudentConnections();
			studentConn.deleteStudent(studentBean.getEmail());
		}
	}

	private void redirectToDBSelect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after student delete failure", e);
		}
	}

	private void redirectMe(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/students/studentindex.jsp");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
