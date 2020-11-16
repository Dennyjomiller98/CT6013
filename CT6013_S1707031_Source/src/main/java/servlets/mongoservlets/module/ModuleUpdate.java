package servlets.mongoservlets.module;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.CourseConnections;
import mongodb.ModuleConnections;
import mongodb.TeacherConnections;
import mongodbbeans.CourseBean;
import mongodbbeans.ModuleBean;
import mongodbbeans.TeacherBean;
import org.apache.log4j.Logger;
import org.bson.Document;

@WebServlet(name = "mongoModuleUpdate")
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
		ModuleConnections moduleConn = new ModuleConnections();
		ModuleBean moduleBean = moduleConn.retrieveSingleModule(moduleToUpdate.getString("Module_Code"));
		if (moduleBean != null)
		{
			//Check relatedCourse exists
			CourseConnections courseConn = new CourseConnections();
			CourseBean courseBean = courseConn.retrieveSingleCourse(moduleToUpdate.getString("Related_Course"));
			if (courseBean != null)
			{
				//Check Module Tutor exists as teacher
				TeacherConnections teacherConn = new TeacherConnections();
				TeacherBean moduleTutor = teacherConn.retrieveSingleTeacher(moduleToUpdate.getString("Module_Tutor"));
				if (moduleTutor != null)
				{
					//Update DB
					moduleConn.updateModule(moduleToUpdate, moduleBean.getModuleCode());
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
			else
			{
				LOG.error("Course selected does not exist, can not add module without related course");
				request.getSession(true).removeAttribute("moduleSuccess");
				request.getSession(true).setAttribute("moduleErrors", "Selected Course does not exist in database");
			}
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
			LOG.error("Unable to redirect back to Edit Course page after course update failure.",e);
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

}