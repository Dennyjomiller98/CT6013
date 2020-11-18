package servlets.course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mongodb.*;
import beans.*;
import org.apache.log4j.Logger;
import org.bson.Document;

@WebServlet(name = "courseEnrollment")
public class CourseEnrollment extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(CourseEnrollment.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		//Empty Constructor
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		//student course enrollment method
		LOG.debug("Attempting to enroll student");
		if(request.getSession(true).getAttribute("DBSELECTION") != null)
		{
			//Get form values/data to be stored.
			Document studentEnrollmentDoc = new Document();
			String allModuleSelections = request.getParameter("allCheckboxSelections");
			String allCATS = request.getParameter("allCATS");
			String studentEmail = request.getParameter("studentEmail");
			String selectedCourse = request.getParameter("selectedCourse");

			boolean wasSuccessful = validateAndEnroll(request, studentEnrollmentDoc, allModuleSelections, allCATS, studentEmail, selectedCourse, response);

			//Redirect to enrollment page regardless of outcome
			try
			{
				if(wasSuccessful)
				{
					response.sendRedirect(request.getContextPath() + "/jsp/marks/marksview.jsp");
				}
				else
				{
					response.sendRedirect(request.getContextPath() + "/jsp/courses/courseenrollment.jsp");
				}
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect back to enrollment page or marks page",e);
			}
		}
		else
		{
			LOG.error("No DB selected");
			redirectToDBSelect(request, response);
		}
	}

	private boolean validateAndEnroll(HttpServletRequest request, Document studentEnrollmentDoc, String allModuleSelections, String allCATS, String studentEmail, String selectedCourse, HttpServletResponse response)
	{
		//Assert student exists
		String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
		boolean wasSuccessful = false;
		LOG.debug("Asserting student exists");
		StudentBean studentBean = null;
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			StudentConnections studentConn = new StudentConnections();
			 studentBean = studentConn.retrieveSingleStudent(studentEmail);
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.StudentConnections studentConnections = new oracle.StudentConnections();
			studentBean = studentConnections.retrieveSingleStudent(studentEmail);
		}
		else
		{
			//No DB selection
			LOG.error("Unknown database choice, returning to DB select page.");
			redirectToDBSelect(request, response);
		}

		if(studentBean != null)
		{
			wasSuccessful = assertCourseExists(request, studentEnrollmentDoc, allModuleSelections, allCATS, studentEmail, selectedCourse);
		}
		else
		{
			LOG.error("Student does not exist in DB, studentEmail: " + studentEmail);
			//Remove Session attributes, add error
			request.getSession(true).removeAttribute("enrollStudent");
			request.getSession(true).removeAttribute("enrollCourse");
			request.getSession(true).removeAttribute("enrollModules");
			request.getSession(true).removeAttribute("enrollSuccess");
			request.getSession(true).setAttribute("enrollErrors", "Unable to enroll student, this student is not linked to an account.");
		}
		return wasSuccessful;
	}

	private boolean assertCourseExists(HttpServletRequest request, Document studentEnrollmentDoc, String allModuleSelections, String allCATS, String studentEmail, String selectedCourse)
	{
		boolean wasSuccessful = false;
		//Assert Course exists
		String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
		LOG.debug("Asserting course exists");
		CourseBean courseBean = null;
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			CourseConnections courseConn = new CourseConnections();
			courseBean = courseConn.retrieveSingleCourse(selectedCourse);
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.CourseConnections courseConnections = new oracle.CourseConnections();
			courseBean = courseConnections.retrieveSingleCourse(selectedCourse);
		}

		if (courseBean != null)
		{
			//Assert CATS is 120
			LOG.debug("Asserting module choices is 120");
			if(allCATS.equals("120"))
			{
				wasSuccessful = courseValidationAndEnrollment(request, studentEnrollmentDoc, allModuleSelections, studentEmail, selectedCourse);
			}
			else
			{
				LOG.error("Module CATS total not 120, total CATS: " + allCATS);
				//Remove Session attributes, add error
				request.getSession(true).removeAttribute("enrollStudent");
				request.getSession(true).removeAttribute("enrollCourse");
				request.getSession(true).removeAttribute("enrollModules");
				request.getSession(true).removeAttribute("enrollSuccess");
				request.getSession(true).setAttribute("enrollErrors", "Unable to enroll student, you have not selected a total of 120 CATS.");
			}
		}
		else
		{
			LOG.error("Course does not exist in DB, selected course: " + selectedCourse);
			//Remove Session attributes, add error
			request.getSession(true).removeAttribute("enrollStudent");
			request.getSession(true).removeAttribute("enrollCourse");
			request.getSession(true).removeAttribute("enrollModules");
			request.getSession(true).removeAttribute("enrollSuccess");
			request.getSession(true).setAttribute("enrollErrors", "Unable to enroll student, the Course selected does not exist.");
		}
		return wasSuccessful;
	}

	private boolean courseValidationAndEnrollment(HttpServletRequest request, Document studentEnrollmentDoc, String allModuleSelections, String studentEmail, String selectedCourse)
	{
		//Assert modules exist
		LOG.debug("Asserting module choices exist in module DB");
		String[] moduleSplit = allModuleSelections.split(",");
		String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			ModuleConnections moduleConn = new ModuleConnections();
			for (String module : moduleSplit)
			{
				ModuleBean bean = moduleConn.retrieveSingleModule(module);
				if (bean == null)
				{
					LOG.error("Module selections csv contains modules not stored in DB, allModules: " + allModuleSelections);
					//Remove Session attributes, add error
					request.getSession(true).removeAttribute("enrollStudent");
					request.getSession(true).removeAttribute("enrollCourse");
					request.getSession(true).removeAttribute("enrollModules");
					request.getSession(true).removeAttribute("enrollSuccess");
					request.getSession(true).setAttribute("enrollErrors", "Unable to enroll student, not all modules selected actually exist.");
				}
			}
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.ModuleConnections moduleConn = new oracle.ModuleConnections();
			for (String module : moduleSplit)
			{
				ModuleBean bean = moduleConn.retrieveSingleModule(module);
				if (bean == null)
				{
					LOG.error("Module selections csv contains modules not stored in DB, allModules: " + allModuleSelections);
					//Remove Session attributes, add error
					request.getSession(true).removeAttribute("enrollStudent");
					request.getSession(true).removeAttribute("enrollCourse");
					request.getSession(true).removeAttribute("enrollModules");
					request.getSession(true).removeAttribute("enrollSuccess");
					request.getSession(true).setAttribute("enrollErrors", "Unable to enroll student, not all modules selected actually exist.");
				}
			}
		}

		//Validations successful
		return addStudentEnrollment(request, studentEnrollmentDoc, allModuleSelections, studentEmail, selectedCourse);
	}

	private boolean addStudentEnrollment(HttpServletRequest request, Document studentEnrollmentDoc, String allModuleSelections, String studentEmail, String selectedCourse)
	{
		//Finally validated values exist. Attempting to store in Document and add to Enrollment DB
		boolean wasSuccessful = false;
		studentEnrollmentDoc.append("Student_Email", studentEmail);
		studentEnrollmentDoc.append("Course_Code", selectedCourse);
		studentEnrollmentDoc.append("Module_Selections", allModuleSelections);

		//check student email not already existing in the database.
		String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
		EnrollmentBean enrollmentBean = null;
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			EnrollmentConnections enrollmentConn = new EnrollmentConnections();
			enrollmentBean = enrollmentConn.retrieveSingleEnrollment(studentEmail);
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.EnrollmentConnections enrollmentConn = new oracle.EnrollmentConnections();
			enrollmentBean = enrollmentConn.retrieveSingleEnrollment(studentEmail);
		}

		if(enrollmentBean != null)
		{
			LOG.error("Student enrollment already exists, student email: " + studentEmail);
			//Remove Session attributes, add error
			request.getSession(true).removeAttribute("enrollStudent");
			request.getSession(true).removeAttribute("enrollCourse");
			request.getSession(true).removeAttribute("enrollModules");
			request.getSession(true).removeAttribute("enrollSuccess");
			request.getSession(true).setAttribute("enrollErrors", "Unable to enroll student, not all modules selected actually exist.");
		}
		else
		{
			//Add student
			LOG.debug("inserting enrollment data into DB");
			if(dbSelection.equalsIgnoreCase("MONGODB"))
			{
				//Add Enrollment
				EnrollmentConnections enrollmentConn = new EnrollmentConnections();
				enrollmentConn.addEnrollment(studentEnrollmentDoc);

				//Change student isEnrolled to true in student DB
				StudentConnections studentConn = new StudentConnections();
				studentConn.enrollStudent(studentEmail);
			}
			else if (dbSelection.equalsIgnoreCase("ORACLE"))
			{
				//Add Enrollment
				oracle.EnrollmentConnections enrollmentConn = new oracle.EnrollmentConnections();
				enrollmentConn.addEnrollment(studentEnrollmentDoc);

				//Change student isEnrolled to true in student DB
				oracle.StudentConnections studentConn = new oracle.StudentConnections();
				studentConn.enrollStudent(studentEmail);
			}

			//Get enrolled session attributes so the student can view them.
			request.getSession(true).removeAttribute("enrollErrors");
			request.getSession(true).setAttribute("enrollSuccess", "Student Enrolled Successfully.");
			request.getSession(true).setAttribute("enrollStudent", studentEmail);
			request.getSession(true).setAttribute("enrollCourse", selectedCourse);
			request.getSession(true).setAttribute("enrollModules", allModuleSelections);
			//Get module details and marks details for the student (displayed on marksview.jsp in seperate tables)
			addSessionAttributesForMarks(request, studentEmail);
			//Retrieve student enrollment attribute
			request.getSession(true).setAttribute("isEnrolled", "true");
			wasSuccessful = true;
		}
		return wasSuccessful;
	}

	private void addSessionAttributesForMarks(HttpServletRequest request, String studentEmail)
	{
		//Retrieve all marks for student
		String dbSelection = request.getSession(true).getAttribute("DBSELECTION").toString();
		List<MarkBean> markBeans = new ArrayList<>();
		if(dbSelection.equalsIgnoreCase("MONGODB"))
		{
			MarkConnections markConn = new MarkConnections();
			markBeans = markConn.retrieveAllMarksForStudent(studentEmail);
		}
		else if (dbSelection.equalsIgnoreCase("ORACLE"))
		{
			oracle.MarkConnections markConn = new oracle.MarkConnections();
			markBeans = markConn.retrieveAllMarksForStudent(studentEmail);
		}

		if (markBeans != null)
		{
			request.getSession(true).setAttribute("allMarks", markBeans);
		}
		else
		{
			LOG.debug("No Marks found for student: " + studentEmail + ". This is okay, may mean no module deadlines have passed.");
		}
	}

	private void redirectToDBSelect(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendRedirect(request.getContextPath() + "/jsp/databaseselection.jsp");
		} catch (IOException e) {
			LOG.error("Failure to redirect after Enrollment failure", e);
		}
	}
}