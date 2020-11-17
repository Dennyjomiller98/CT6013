package servlets.users;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.TeacherConnections;
import mongodbbeans.TeacherBean;
import org.apache.log4j.Logger;

@WebServlet(name = "teacherLogin")
public class TeacherLogin extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(TeacherLogin.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		TeacherBean teacher = new TeacherBean();
		teacher.setEmail(request.getParameter("email"));
		teacher.setPassword(request.getParameter("pword"));

		//Try to log in, return TeacherBean
		TeacherConnections teacherConn = new TeacherConnections();
		boolean successfulLogin = teacherConn.attemptLogin(teacher);
		if (successfulLogin)
		{
			try
			{
				request.getSession(true).removeAttribute("loginErrors");
				addSessionAttributes(request, teacher);
				response.sendRedirect(request.getContextPath() + "/jsp/teachers/teacherindex.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect after successful teacher login", e);
			}
		}
		else
		{
			request.getSession(true).setAttribute("loginErrors", "Incorrect login details, please try again.");
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/teachers/teacherlogin.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect back to login page after teacher login failure", e);
			}
		}
	}

	private void addSessionAttributes(HttpServletRequest request, TeacherBean teacher)
	{
		TeacherConnections teacherConn = new TeacherConnections();
		TeacherBean teacherBean = teacherConn.retrieveSingleTeacher(teacher.getEmail());

		//Populate Bean
		request.getSession(true).setAttribute("firstname", teacherBean.getFirstName());
		request.getSession(true).setAttribute("surname", teacherBean.getSurname());
		request.getSession(true).setAttribute("email", teacherBean.getEmail());
		request.getSession(true).setAttribute("dob", teacherBean.getDOB());
		String fullAddress = teacherBean.getAddress();
		if (fullAddress != null)
		{
			String[] split = fullAddress.split(",");
			request.getSession(true).setAttribute("address1", split[0]);
			request.getSession(true).setAttribute("address2", split[1]);
			request.getSession(true).setAttribute("city", split[2]);
			request.getSession(true).setAttribute("postcode", split[3]);
		}

		request.getSession(true).setAttribute("pword", teacherBean.getPassword());
		request.getSession(true).setAttribute("isTeacher", teacherBean.isTeacher());
		request.getSession(true).setAttribute("isEnrolled", teacherBean.isEnrolled());
	}
}