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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
