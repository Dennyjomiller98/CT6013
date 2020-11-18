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

@WebServlet(name = "teacherProfile")
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
		if(request.getSession(true).getAttribute("DBSELECTION") != null)
		{
			String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
			//Get Form Submission
			Document teacherToUpdate = new Document();
			teacherToUpdate.append("First_Name", request.getParameter("firstname"));
			teacherToUpdate.append("Surname", request.getParameter("surname"));
			teacherToUpdate.append("Email", request.getParameter("email"));
			teacherToUpdate.append("DOB", request.getParameter("dob"));
			String fullAddress = request.getParameter("address1") + "," + request.getParameter("address2") + "," + request.getParameter("city") + "," + request.getParameter("postcode");
			teacherToUpdate.append("Address", fullAddress);
			teacherToUpdate.append("Password", request.getParameter("pword"));
			teacherToUpdate.append("Is_Enrolled", request.getParameter("isEnrolled"));
			teacherToUpdate.append("Is_Teacher", "false");

			if(dbSelection.equalsIgnoreCase("MONGODB"))
			{
				//MongoDB
				LOG.debug("Mongo DB Update Teacher profile");
				mongoTeacherUpdateProfile(request, response, teacherToUpdate);
			}
			else if (dbSelection.equalsIgnoreCase("ORACLE"))
			{
				//Oracle
				LOG.debug("Mongo DB Update Teacher profile");
				oracleTeacherUpdateProfile(request, response, teacherToUpdate);
			}
			else
			{
				LOG.error("Unknown DB Selection, returning to DB Select Page");
				redirectToDBSelect(request, response);
			}
		}
		else
		{
			LOG.error("No DB selected");
			redirectToDBSelect(request, response);
		}
	}

	private void oracleTeacherUpdateProfile(HttpServletRequest request, HttpServletResponse response, Document teacherToUpdate)
	{
		oracle.TeacherConnections teacherConn = new oracle.TeacherConnections();

		//Check teacher still exists
		TeacherBean teacherBean = teacherConn.retrieveSingleTeacher(teacherToUpdate.getString("Email"));
		if(teacherBean != null)
		{
			//Update DB
			LOG.debug("Teacher found, updating details");
			teacherConn.updateTeacherDetails(teacherToUpdate, teacherBean.getEmail());
			request.getSession(true).removeAttribute("editErrors");
			request.getSession(true).setAttribute("updateSuccess", "Details Updated Successfully.");
			//Set new values or old values will be retained
			addUpdatedSessionAttributes(request);
		}
		else
		{
			//Teacher not found, unable to update
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

	private void mongoTeacherUpdateProfile(HttpServletRequest request, HttpServletResponse response, Document teacherToUpdate)
	{
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

	private void redirectToDBSelect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after teacher Profile Update failure", e);
		}
	}
}