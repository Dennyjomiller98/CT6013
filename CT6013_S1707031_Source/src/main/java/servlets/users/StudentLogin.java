package servlets.users;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.StudentConnections;
import beans.StudentBean;
import org.apache.log4j.Logger;

@WebServlet(name = "studentLogin")
public class StudentLogin extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(StudentLogin.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		if(request.getSession(true).getAttribute("DBSELECTION") != null)
		{
			String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
			StudentBean student = new StudentBean();
			student.setEmail(request.getParameter("email"));
			student.setPassword(request.getParameter("pword"));
			if(dbSelection.equalsIgnoreCase("MONGODB"))
			{
				//MongoDB Selection
				mongoStudentLogin(request, response, student);
			}
			else if (dbSelection.equalsIgnoreCase("ORACLE"))
			{
				//Oracle DB
				oracleStudentLogin(request, response, student);
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
			LOG.error("No DB selected");
			redirectToDBSelect(request, response);
		}
	}

	private void oracleStudentLogin(HttpServletRequest request, HttpServletResponse response, StudentBean student)
	{
		LOG.debug("Oracle Student Login");
		oracle.StudentConnections studentConnections = new oracle.StudentConnections();
		boolean successfulLogin = studentConnections.attemptLogin(student);
		if(successfulLogin)
		{
			try
			{
				LOG.debug("Successful login from Oracle.");
				StudentBean studentBean = studentConnections.retrieveSingleStudent(student.getEmail());
				addSessionAttributes(request, studentBean);
				request.getSession(true).removeAttribute("loginErrors");
				response.sendRedirect(request.getContextPath() + "/jsp/students/studentindex.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect after successful student login", e);
			}
		}
		else
		{
			request.getSession(true).setAttribute("loginErrors", "Incorrect login details, please try again.");
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/students/studentlogin.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect back to login page after student login failure", e);
			}
		}
	}

	private void mongoStudentLogin(HttpServletRequest request, HttpServletResponse response, StudentBean student)
	{
		//Try to log in, return StudentBean
		LOG.debug("MongoDB Student Login");
		StudentConnections studentConn = new StudentConnections();
		boolean successfulLogin = studentConn.attemptLogin(student);
		if (successfulLogin)
		{
			try
			{
				StudentBean studentBean = studentConn.retrieveSingleStudent(student.getEmail());
				addSessionAttributes(request, studentBean);
				request.getSession(true).removeAttribute("loginErrors");
				response.sendRedirect(request.getContextPath() + "/jsp/students/studentindex.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect after successful student login", e);
			}
		}
		else
		{
			request.getSession(true).setAttribute("loginErrors", "Incorrect login details, please try again.");
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/students/studentlogin.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect back to login page after student login failure", e);
			}
		}
	}

	private void redirectToDBSelect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after student Login failure", e);
		}
	}

	private void addSessionAttributes(HttpServletRequest request, StudentBean retrievedStudent)
	{
		//Populate Bean
		request.getSession(true).setAttribute("firstname", retrievedStudent.getFirstName());
		request.getSession(true).setAttribute("surname", retrievedStudent.getSurname());
		request.getSession(true).setAttribute("email", retrievedStudent.getEmail());
		request.getSession(true).setAttribute("dob", retrievedStudent.getDOB());
		String fullAddress = retrievedStudent.getAddress();
		if (fullAddress != null)
		{
			String[] split = fullAddress.split(",");
			request.getSession(true).setAttribute("address1", split[0]);
			request.getSession(true).setAttribute("address2", split[1]);
			request.getSession(true).setAttribute("city", split[2]);
			request.getSession(true).setAttribute("postcode", split[3]);
		}
		request.getSession(true).setAttribute("pword", retrievedStudent.getPassword());
		request.getSession(true).setAttribute("isEnrolled", retrievedStudent.isEnrolled());
		request.getSession(true).setAttribute("isTeacher", retrievedStudent.isTeacher());
	}
}