package servlets.etl;

import beans.dw.DWLoadBean;
import beans.operational.*;
import beans.operational.dimensions.*;
import etl.DataTransformer;
import etl.ExtractHelper;
import etl.LoadHelper;
import etl.TransformHelper;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracle.DataViewConnections;
import org.apache.log4j.Logger;

public class ETL extends HttpServlet
{
	static final Logger LOG = Logger.getLogger(ETL.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		request.getSession(true).removeAttribute("success");
		request.getSession(true).removeAttribute("errors");

		//If on View page, need to remove previous data
		request.getSession(true).removeAttribute("doQuery");
		request.getSession(true).removeAttribute("results");
		request.getSession(true).removeAttribute("selectedQuery");
		request.getSession(true).removeAttribute("selectedYear");
		request.getSession(true).removeAttribute("selectedCourse");
		request.getSession(true).removeAttribute("selectedTutor");
		request.getSession(true).removeAttribute("studentsBeans");
		request.getSession(true).removeAttribute("assignmentsBeans");
		request.getSession(true).removeAttribute("enrollmentsBeans");

		//Retrieve ALL Operational Database Data, and store in Beans ready for Transform Process
		try
		{
			ExtractHelper extractHelper = new ExtractHelper();
			List<AssignmentsBean> assignmentsBeans = extractHelper.retrieveAssignmentsTable();
			List<CoursesBean> coursesBeans = extractHelper.retrieveCoursesTable();
			List<EnrollmentsBean> enrollmentsBeans = extractHelper.retrieveEnrollmentsTable();
			List<ModulesBean> modulesBeans = extractHelper.retrieveModulesTable();
			List<StudentsBean> studentsBeans = extractHelper.retrieveStudentsTable();
			List<SubjectsBean> subjectsBeans = extractHelper.retrieveSubjectsTable();
			List<TutorsBean> tutorsBeans = extractHelper.retrieveTutorsTable();

			List<DimCoursesBean> dimCoursesBeans = extractHelper.retrieveDimCoursesTable();
			List<DimEnrollmentsBean> dimEnrollmentsBeans = extractHelper.retrieveDimEnrollmentsTable();
			List<DimModulesBean> dimModulesBeans = extractHelper.retrieveDimModulesTable();
			List<DimStudentsBean> dimStudentsBeans = extractHelper.retrieveDimStudentsTable();
			List<DimSubjectsBean> dimSubjectsBeans = extractHelper.retrieveDimSubjectsTable();
			List<DimTutorsBean> dimTutorsBeans = extractHelper.retrieveDimTutorsTable();

			//Now we need to Transform the data, sanitize information for each bit of data before placing in the DW
			TransformHelper transformHelper = new TransformHelper();
			assignmentsBeans = transformHelper.transformAssignmentsData(assignmentsBeans);
			coursesBeans = transformHelper.transformCoursesData(coursesBeans);
			enrollmentsBeans = transformHelper.transformEnrollmentsData(enrollmentsBeans);
			modulesBeans = transformHelper.transformModulesData(modulesBeans);
			studentsBeans = transformHelper.transformStudentsData(studentsBeans);
			subjectsBeans = transformHelper.transformSubjectsData(subjectsBeans);
			tutorsBeans = transformHelper.transformTutorsData(tutorsBeans);

			dimCoursesBeans = transformHelper.transformDimCoursesData(dimCoursesBeans);
			dimEnrollmentsBeans = transformHelper.transformDimEnrollmentsData(dimEnrollmentsBeans);
			dimModulesBeans = transformHelper.transformDimModulesData(dimModulesBeans);
			dimStudentsBeans = transformHelper.transformDimStudentsData(dimStudentsBeans);
			dimSubjectsBeans = transformHelper.transformDimSubjectsData(dimSubjectsBeans);
			dimTutorsBeans = transformHelper.transformDimTutorsData(dimTutorsBeans);

			//Data has been null-safe checked, so now we can Transform Dates/Check ID's before Load
			LoadHelper loadHelper = new LoadHelper();
			DWLoadBean loadBean = new DWLoadBean();

			//Prepare Data
			loadHelper.prepareCourseData(coursesBeans, loadBean);
			loadHelper.prepareEnrollmentData(enrollmentsBeans, loadBean);
			loadHelper.prepareModuleData(modulesBeans, loadBean);
			loadHelper.prepareStudentData(studentsBeans, loadBean);
			loadHelper.prepareSubjectData(subjectsBeans, loadBean);
			loadHelper.prepareTutorData(tutorsBeans, loadBean);
			loadHelper.prepareAssignmentData(assignmentsBeans, loadBean);

			loadHelper.prepareDimCourseData(dimCoursesBeans, loadBean);
			loadHelper.prepareDimEnrollmentData(dimEnrollmentsBeans, loadBean);
			loadHelper.prepareDimModuleData(dimModulesBeans, loadBean);
			loadHelper.prepareDimStudentData(dimStudentsBeans, loadBean);
			loadHelper.prepareDimSubjectData(dimSubjectsBeans, loadBean);
			loadHelper.prepareDimTutorData(dimTutorsBeans, loadBean);

			//check data, ensure that all ID's are present, or we will get errors retrieving data from the DW once loaded
			DataTransformer transformer = new DataTransformer();
			loadBean = transformer.validateAndTransformData(loadBean);

			//Purge old DW data
			boolean wasPurged = loadHelper.emptyDW();
			boolean wasDimCoursePurged = loadHelper.emptyDimCourse();
			boolean wasDimEnrollmentPurged = loadHelper.emptyDimEnrollment();
			boolean wasDimModulePurged = loadHelper.emptyDimModule();
			boolean wasDimSubjectPurged = loadHelper.emptyDimSubject();
			boolean wasDimStudentPurged = loadHelper.emptyDimStudent();
			boolean wasDimTutorPurged = loadHelper.emptyDimTutor();
			if(wasPurged && wasDimCoursePurged && wasDimEnrollmentPurged && wasDimModulePurged
			&& wasDimSubjectPurged && wasDimStudentPurged && wasDimTutorPurged)
			{
				//Load Data
				boolean loadSuccess = loadHelper.updateDW(loadBean);
				boolean wasIndexed = loadHelper.indexDW();
				if(loadSuccess && wasIndexed)
				{
					request.getSession(true).setAttribute("success", "Database Warehouse ETL Process Complete");
				}
				else
				{
					request.getSession(true).setAttribute("errors", "An error has occurred whilst Loading Data for the Database Warehouse" );
				}
			}
			else
			{
				request.getSession(true).setAttribute("errors", "An error has occurred whilst Purging the old data for the Database Warehouse" );
			}
			attemptRedirect(request, response);
		}
		catch(Exception e)
		{
			request.getSession(true).setAttribute("errors", "An error has occurred during the Database Warehouse ETL Process" + e + e.getCause() + Arrays.toString(e.getStackTrace()));
			LOG.error("An error has occurred during the Database Warehouse ETL Process", e);
			attemptRedirect(request, response);
		}
	}

	private void attemptRedirect(HttpServletRequest request, HttpServletResponse response)
	{
		String userEmail = (String) request.getSession(true).getAttribute("email");
		if (userEmail != null)
		{
			//Get View Page Information
			DataViewConnections connections = new DataViewConnections();
			List<DimCoursesBean> dwCourses = connections.getDWCourses();
			if(dwCourses != null && !dwCourses.isEmpty())
			{
				request.getSession(true).setAttribute("allCourses", dwCourses);
			}
			List<DimTutorsBean> dwTutors = connections.getDWTutors();
			if(dwTutors != null && !dwTutors.isEmpty())
			{
				request.getSession(true).setAttribute("allTutors", dwTutors);
			}

			//Take logged in user to view data page
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/view.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect.",e);
			}
		}
		else
		{
			//Take not logged in user to homepage
			try
			{
				response.sendRedirect(request.getContextPath() + "/jsp/homepage.jsp");
			}
			catch (IOException e)
			{
				LOG.error("Unable to redirect.",e);
			}
		}
	}
}
