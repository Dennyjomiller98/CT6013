package servlets.mongoservlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.StudentConnections;
import mongodbbeans.StudentBean;
import org.apache.log4j.Logger;
import org.bson.Document;

@WebServlet(name = "mongoStudentProfile")
public class StudentProfile extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(StudentProfile.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		//Get Form Submission
		Document studentToUpdate = new Document();
		studentToUpdate.append("First_Name", request.getParameter("firstname"));
		studentToUpdate.append("Surname", request.getParameter("surname"));
		studentToUpdate.append("Email", request.getParameter("email"));
		studentToUpdate.append("DOB", request.getParameter("dob"));
		String fullAddress = request.getParameter("address1") + "," + request.getParameter("address2") + "," + request.getParameter("city") + "," + request.getParameter("postcode");
		studentToUpdate.append("Address", fullAddress);
		studentToUpdate.append("Password", request.getParameter("pword"));
		studentToUpdate.append("Is_Enrolled", request.getParameter("isEnrolled"));
		studentToUpdate.append("Is_Teacher", "false");

		//Connect to DB and Store updated values
		StudentConnections studentConn = new StudentConnections();

		//Check student still exists.
		StudentBean studentBean = studentConn.retrieveSingleStudent(studentToUpdate.getString("Email"));
		if(studentBean != null)
		{
			//Update DB
			studentConn.updateStudentDetails(studentToUpdate, studentBean.getEmail());
			LOG.debug("Student Update completed successfully");
			request.getSession(true).removeAttribute("editErrors");
			request.getSession(true).setAttribute("updateSuccess", "Details Updated Successfully.");
			//Set new values or old values will be retained
			addUpdatedSessionAttributes(request);
		}
		else
		{
			//Student not found, cannot update
			request.getSession(true).removeAttribute("updateSuccess");
			request.getSession(true).setAttribute("editErrors", "An Error has occurred, the changes were unable to be saved.");
		}

		//Return to Profile page regardless of outcome, required attributes are already set.
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/students/studentprofile.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect back to Profile page after student update failure.",e);
		}
	}

	private void addUpdatedSessionAttributes(HttpServletRequest request)
	{
		//Populate Bean
		request.getSession(true).setAttribute("firstname", request.getParameter("firstname"));
		request.getSession(true).setAttribute("surname", request.getParameter("surname"));
		request.getSession(true).setAttribute("email", request.getParameter("email"));
		request.getSession(true).setAttribute("dob", request.getParameter("dob"));
		request.getSession(true).setAttribute("address1", request.getParameter("address1"));
		request.getSession(true).setAttribute("address2", request.getParameter("address2"));
		request.getSession(true).setAttribute("city", request.getParameter("city"));
		request.getSession(true).setAttribute("postcode", request.getParameter("postcode"));
		request.getSession(true).setAttribute("pword", request.getParameter("pword"));
		request.getSession(true).setAttribute("isEnrolled", request.getParameter("isEnrolled"));
		request.getSession(true).setAttribute("isTeacher", "false");
	}
}