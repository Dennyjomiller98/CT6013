package managers.scheduler;

import beans.dw.DWResultsBean;
import beans.operational.*;
import beans.operational.dimensions.*;
import etl.DataTransformer;
import etl.ExtractHelper;
import etl.LoadHelper;
import etl.TransformHelper;
import java.util.*;
import org.apache.log4j.Logger;

public class ETLScheduleManager extends TimerTask
{
	static final Logger LOG = Logger.getLogger(ETLScheduleManager.class);

	public void run() {
		LOG.debug("Generating report running ETLScheduleManager");
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
			DWResultsBean loadBean = new DWResultsBean();

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
				if(loadSuccess)
				{
					LOG.debug("Database Warehouse ETL Process Complete");
				}
				else
				{
					LOG.error("An error has occurred whilst Loading Data for the Database Warehouse");
				}
			}
			else
			{
				LOG.error("An error has occurred whilst Purging the old data for the Database Warehouse");
			}
		}
		catch(Exception e)
		{
			LOG.error("An error has occurred during the Database Warehouse ETL Process", e);
		}
	}

}

class MainApplication {

	public static void main(String[] args) {
		Timer timer = new Timer();
		Calendar date = Calendar.getInstance();
		date.set(
				Calendar.DAY_OF_WEEK,
				Calendar.SUNDAY
		);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		// Schedule to run every Sunday in midnight
		timer.schedule(
				new ETLScheduleManager(),
				date.getTime(),
				1000 * 60 * 60 * 24 * 7
		);
	}//Main method ends
}//MainApplication ends