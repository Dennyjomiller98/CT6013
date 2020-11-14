package servlets.mongoservlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.TeacherConnections;
import mongodbbeans.TeacherBean;
import org.apache.log4j.Logger;
import org.bson.Document;

@WebServlet(name = "mongoTeacherRegistration")
public class TeacherRegistration extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(TeacherRegistration.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		//Retrieve form values for Teacher
		Document teacherToRegister = new Document();
		teacherToRegister.append("Teacher_ID", request.getParameter("teacherID"));
		teacherToRegister.append("First_Name", request.getParameter("firstname"));
		teacherToRegister.append("Surname", request.getParameter("surname"));
		teacherToRegister.append("Email", request.getParameter("email"));
		teacherToRegister.append("DOB", request.getParameter("dob"));
		String fullAddress = request.getParameter("address1") + "," + request.getParameter("address2") + "," + request.getParameter("city") + "," + request.getParameter("postcode");
		teacherToRegister.append("Address", fullAddress);
		teacherToRegister.append("Password", request.getParameter("pword"));
		teacherToRegister.append("Is_Teacher", "true");
		teacherToRegister.append("Is_Enrolled", "false");

		TeacherConnections teacherConn = new TeacherConnections();

		//Check teacher is not already registered.
		TeacherBean teacherEmail = teacherConn.retrieveSingleTeacher(teacherToRegister.getString("Email"));
		if(teacherEmail == null)
		{
			//Register Teacher
			teacherConn.registerTeacherToDB(teacherToRegister);
			LOG.debug("Teacher Registration completed successfully");
			request.getSession(true).removeAttribute("registrationErrors");
			redirectMe(request, response);
		}
		else
		{
			request.getSession().setAttribute("registrationErrors", "Unable to register teacher, the email already exists.");
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/teachers/teacherregistration.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Error redirecting Teacher to register page after Teacher registration error", e);
			}
		}
	}

	private void redirectMe(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/teachers/teacherlogin.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after teacher successfully registered", e);
		}
	}
}
