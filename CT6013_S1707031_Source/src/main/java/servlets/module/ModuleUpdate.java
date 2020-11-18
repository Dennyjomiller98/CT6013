package servlets.module;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.CourseConnections;
import mongodb.ModuleConnections;
import mongodb.TeacherConnections;
import beans.CourseBean;
import beans.ModuleBean;
import beans.TeacherBean;
import org.apache.log4j.Logger;
import org.bson.Document;

@WebServlet(name = "moduleUpdate")
public class ModuleUpdate extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(ModuleUpdate.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		//Validate form submission and save updated values
		LOG.debug("Module Update form begin. Attempting to save to DB");
		Document moduleToUpdate = new Document();
		moduleToUpdate.append("Module_Code", request.getParameter("moduleCode"));
		moduleToUpdate.append("Module_Name", request.getParameter("moduleName"));
		moduleToUpdate.append("Module_Tutor", request.getParameter("moduleTutor"));
		moduleToUpdate.append("Related_Course", request.getParameter("relatedCourse"));
		moduleToUpdate.append("Semester", request.getParameter("semester"));
		moduleToUpdate.append("Is_Compulsory", request.getParameter("isCompulsory"));
		moduleToUpdate.append("Module_Start", request.getParameter("moduleStart"));
		moduleToUpdate.append("Module_End", request.getParameter("moduleEnd"));

		//Connect to DB, check module still exists
		if(request.getSession(true).getAttribute("DBSELECTION") != null)
		{
			String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
			ModuleBean moduleBean = null;
			if(dbSelection.equalsIgnoreCase("MONGODB"))
			{
				ModuleConnections moduleConn = new ModuleConnections();
				moduleBean = moduleConn.retrieveSingleModule(moduleToUpdate.getString("Module_Code"));

			}
			else if (dbSelection.equalsIgnoreCase("ORACLE"))
			{
				oracle.ModuleConnections moduleConn = new oracle.ModuleConnections();
				moduleBean = moduleConn.retrieveSingleModule(moduleToUpdate.getString("Module_Code"));
			}
			else
			{
				//No DB selection
				LOG.error("Unknown database choice, returning to DB select page.");
				redirectToDBSelect(request, response);
			}

			if (moduleBean != null)
			{
				validateCourse(request, moduleToUpdate, moduleBean, dbSelection);
			}
			else
			{
				//Unable to update as module can't be found in DB
				LOG.error("Module does not exist in database");
				request.getSession(true).removeAttribute("moduleSuccess");
				request.getSession(true).setAttribute("moduleErrors", "Selected Module was not found in database, unable to update");
			}

			//Redirect to module edit page regardless of outcome
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/modules/moduleedit.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect back to Edit Course page after course update failure.", e);
			}
		}
		else
		{
			LOG.error("No DB selected");
			redirectToDBSelect(request, response);
		}
	}

	private void validateCourse(HttpServletRequest request, Document moduleToUpdate, ModuleBean moduleBean, String dbSelection)
	{
		//Check relatedCourse exists
		CourseBean courseBean = null;
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			CourseConnections courseConn = new CourseConnections();
			courseBean = courseConn.retrieveSingleCourse(moduleToUpdate.getString("Related_Course"));
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.CourseConnections courseConn = new oracle.CourseConnections();
			courseBean = courseConn.retrieveSingleCourse(moduleToUpdate.getString("Related_Course"));
		}

		if (courseBean != null)
		{
			//Check Module Tutor exists as teacher
			validateTeacher(request, moduleToUpdate, moduleBean, dbSelection);
		}
		else
		{
			LOG.error("Course selected does not exist, can not add module without related course");
			request.getSession(true).removeAttribute("moduleSuccess");
			request.getSession(true).setAttribute("moduleErrors", "Selected Course does not exist in database");
		}
	}

	private void validateTeacher(HttpServletRequest request, Document moduleToUpdate, ModuleBean moduleBean, String dbSelection)
	{
		TeacherBean moduleTutor = null;
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			TeacherConnections teacherConn = new TeacherConnections();
			moduleTutor = teacherConn.retrieveSingleTeacher(moduleToUpdate.getString("Module_Tutor"));
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.TeacherConnections teacherConn = new oracle.TeacherConnections();
			moduleTutor = teacherConn.retrieveSingleTeacher(moduleToUpdate.getString("Module_Tutor"));
		}

		if (moduleTutor != null)
		{
			//Update DB
			if(dbSelection.equalsIgnoreCase("MONGODB"))
			{
				ModuleConnections moduleConn = new ModuleConnections();
				moduleConn.updateModule(moduleToUpdate, moduleBean.getModuleCode());
			}
			else if (dbSelection.equalsIgnoreCase("ORACLE"))
			{
				oracle.ModuleConnections moduleConn = new oracle.ModuleConnections();
				moduleConn.updateModule(moduleToUpdate, moduleBean.getModuleCode());
			}
			LOG.debug("Module successfully added");
			request.getSession(true).removeAttribute("moduleErrors");
			request.getSession(true).setAttribute("moduleSuccess", "Details Updated Successfully");
			//Set session attributes with updated values
			addUpdatedSessionAttributes(request);
		}
		else
		{
			LOG.error("Tutor email is not linked to an actual tutor, can not add module with tutor: " + moduleToUpdate.getString("Module_Tutor"));
			request.getSession(true).removeAttribute("moduleSuccess");
			request.getSession(true).setAttribute("moduleErrors", "Tutor Email is not linked to a stored teacher");
		}
	}

	private void addUpdatedSessionAttributes(HttpServletRequest request)
	{
		//Populate the session with updated values
		LOG.debug("Populating module values into request session attributes");
		request.getSession(true).setAttribute("moduleCode", request.getParameter("moduleCode"));
		request.getSession(true).setAttribute("moduleName", request.getParameter("moduleName"));
		request.getSession(true).setAttribute("moduleTutor", request.getParameter("moduleTutor"));
		request.getSession(true).setAttribute("relatedCourse", request.getParameter("relatedCourse"));
		request.getSession(true).setAttribute("semester", request.getParameter("semester"));
		String isCompulsory = request.getParameter("isCompulsory");
		if (isCompulsory != null)
		{
			request.getSession(true).setAttribute("isCompulsory", "true");
		}
		else
		{
			request.getSession(true).setAttribute("isCompulsory", "false");
		}
		request.getSession(true).setAttribute("moduleStart", request.getParameter("moduleStart"));
		request.getSession(true).setAttribute("moduleEnd", request.getParameter("moduleEnd"));
	}

	private void redirectToDBSelect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after Teacher Login failure", e);
		}
	}
}