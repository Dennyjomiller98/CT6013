package servlets.users;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.TeacherConnections;
import beans.TeacherBean;
import org.apache.log4j.Logger;
import org.bson.Document;

@WebServlet(name = "teacherRegistration")
public class TeacherRegistration extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(TeacherRegistration.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		//Retrieve form values for Teacher
		if(request.getSession(true).getAttribute("DBSELECTION") != null)
		{
			String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();

			Document teacherToRegister = new Document();
			teacherToRegister.append("First_Name", request.getParameter("firstname"));
			teacherToRegister.append("Surname", request.getParameter("surname"));
			teacherToRegister.append("Email", request.getParameter("email"));
			teacherToRegister.append("DOB", request.getParameter("dob"));
			String fullAddress = request.getParameter("address1") + "," + request.getParameter("address2") + "," + request.getParameter("city") + "," + request.getParameter("postcode");
			teacherToRegister.append("Address", fullAddress);
			teacherToRegister.append("Password", request.getParameter("pword"));
			teacherToRegister.append("Is_Teacher", "true");
			teacherToRegister.append("Is_Enrolled", "false");

			if(dbSelection.equalsIgnoreCase("MONGODB"))
			{
				//MongoDB Selection
				mongoTeacherRegistration(request, response, teacherToRegister);
			}
			else if (dbSelection.equalsIgnoreCase("ORACLE"))
			{
				//Oracle DB
				oracleTeacherRegistration(request, response, teacherToRegister);
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
			//No attribute found, go back to DB Select page
			LOG.error("Unknown database choice, returning to DB select page.");
			redirectToDBSelect(request, response);
		}
	}

	private void oracleTeacherRegistration(HttpServletRequest request, HttpServletResponse response, Document teacherToRegister)
	{
		oracle.TeacherConnections teacherConn = new oracle.TeacherConnections();

		//Check Teacher does not already exist
		TeacherBean teacherBean = teacherConn.retrieveSingleTeacher(teacherToRegister.getString("Email"));
		if (teacherBean == null)
		{
			//Teacher doesn't exist, register now
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

	private void mongoTeacherRegistration(HttpServletRequest request, HttpServletResponse response, Document teacherToRegister)
	{
		TeacherConnections teacherConn = new TeacherConnections();

		//Check teacher is not already registered.
		TeacherBean teacherEmail = teacherConn.retrieveSingleTeacher(teacherToRegister.getString("Email"));
		if (teacherEmail == null)
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

	private void redirectToDBSelect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after Teacher register failure", e);
		}
	}
}