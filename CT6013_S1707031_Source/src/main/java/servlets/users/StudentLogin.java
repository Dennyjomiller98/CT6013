package servlets.users;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.StudentConnections;
import mongodbbeans.StudentBean;
import org.apache.log4j.Logger;

@WebServlet(name = "studentLogin")
public class StudentLogin extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(StudentLogin.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		StudentBean student = new StudentBean();
		student.setEmail(request.getParameter("email"));
		student.setPassword(request.getParameter("pword"));

		//Try to log in, return StudentBean
		StudentConnections studentConn = new StudentConnections();
		boolean successfulLogin = studentConn.attemptLogin(student);
		if (successfulLogin)
		{
			try
			{
				addSessionAttributes(request, student);
				request.getSession(true).removeAttribute("loginErrors");
				response.sendRedirect(request.getContextPath() + "/jsp/students/studentindex.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect after successful student login",e);
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
				LOG.error("Unable to redirect back to login page after student login failure",e);
			}
		}
	}

	private void addSessionAttributes(HttpServletRequest request, StudentBean student)
	{
		StudentConnections studentConn = new StudentConnections();
		StudentBean studentBean = studentConn.retrieveSingleStudent(student.getEmail());

		//Populate Bean
		request.getSession(true).setAttribute("firstname", studentBean.getFirstName());
		request.getSession(true).setAttribute("surname", studentBean.getSurname());
		request.getSession(true).setAttribute("email", studentBean.getEmail());
		request.getSession(true).setAttribute("dob", studentBean.getDOB());
		String fullAddress = studentBean.getAddress();
		if (fullAddress != null)
		{
			String[] split = fullAddress.split(",");
			request.getSession(true).setAttribute("address1", split[0]);
			request.getSession(true).setAttribute("address2", split[1]);
			request.getSession(true).setAttribute("city", split[2]);
			request.getSession(true).setAttribute("postcode", split[3]);
		}
		request.getSession(true).setAttribute("pword", studentBean.getPassword());
		request.getSession(true).setAttribute("isEnrolled", studentBean.isEnrolled());
		request.getSession(true).setAttribute("isTeacher", studentBean.isTeacher());
	}
}
