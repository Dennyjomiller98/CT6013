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

@WebServlet(name = "mongoTeacherProfile")
public class TeacherProfile extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(TeacherProfile.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Retrieve Teacher details
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		//Get Form Submission
		Document teacherToUpdate = new Document();
		teacherToUpdate.append("Teacher_ID()", request.getParameter("teacherID"));
		teacherToUpdate.append("First_Name", request.getParameter("firstname"));
		teacherToUpdate.append("Surname", request.getParameter("surname"));
		teacherToUpdate.append("Email", request.getParameter("email"));
		teacherToUpdate.append("DOB", request.getParameter("dob"));
		String fullAddress = request.getParameter("address1") + "," + request.getParameter("address2") + "," + request.getParameter("city") + "," + request.getParameter("postcode");
		teacherToUpdate.append("Address", fullAddress);
		teacherToUpdate.append("Password", request.getParameter("pword"));
		teacherToUpdate.append("Is_Enrolled", request.getParameter("isEnrolled"));
		teacherToUpdate.append("Is_Teacher", "false");

		//Connect to DB and Store updated values
		TeacherConnections teacherConn = new TeacherConnections();

		//Check teacher still exists.
		TeacherBean teacherBean = teacherConn.retrieveSingleTeacher(teacherToUpdate.getString("Email"));
		if(teacherBean != null)
		{
			//Update DB
			teacherConn.updateTeacherDetails(teacherToUpdate, teacherBean.getEmail());
			LOG.debug("Teacher Update completed successfully");
			request.getSession(true).removeAttribute("editErrors");
			request.getSession(true).setAttribute("updateSuccess", "Details Updated Successfully.");
			//Set new values or old values will be retained
			addUpdatedSessionAttributes(request);
		}
		else
		{
			//Teacher not found, cannot update
			request.getSession(true).removeAttribute("updateSuccess");
			request.getSession(true).setAttribute("editErrors", "An Error has occurred, the changes were unable to be saved.");
		}

		//Return to Profile page regardless of outcome, required attributes are already set.
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/teachers/teacherprofile.jsp");
		}
		catch (IOException e)
		{
			LOG.error("Unable to redirect back to Profile page after teacher update failure.",e);
		}
	}

	private void addUpdatedSessionAttributes(HttpServletRequest request)
	{
		//Populate Bean
		request.getSession(true).setAttribute("teacherID", request.getParameter("teacherID"));
		request.getSession(true).setAttribute("firstname", request.getParameter("firstname"));
		request.getSession(true).setAttribute("surname", request.getParameter("surname"));
		request.getSession(true).setAttribute("email", request.getParameter("email"));
		request.getSession(true).setAttribute("dob", request.getParameter("dob"));
		request.getSession(true).setAttribute("address1", request.getParameter("address1"));
		request.getSession(true).setAttribute("address2", request.getParameter("address2"));
		request.getSession(true).setAttribute("city", request.getParameter("city"));
		request.getSession(true).setAttribute("pword", request.getParameter("pword"));
		request.getSession(true).setAttribute("isEnrolled", request.getParameter("isEnrolled"));
		request.getSession(true).setAttribute("isTeacher", "false");
	}
}