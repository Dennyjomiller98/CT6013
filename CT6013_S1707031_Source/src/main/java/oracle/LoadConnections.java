package oracle;

import beans.dw.DWResultsBean;
import beans.operational.*;
import beans.operational.dimensions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import managers.oracle.DataManager;
import oracle.sql.DATE;

/**
 * @author Denny-Jo
 * LoadConnections - Provides Extended connectivity For DW Load of the ETL Process
 * */
public class LoadConnections extends AbstractOracleConnections
{
	public LoadConnections()
	{
		//Empty Constructor
	}

	public boolean removeResultsData() throws SQLException
	{
		return removeDataFromTable(TBL_DW_RESULTS);
	}

	public boolean removeDimCourseData() throws SQLException
	{
		return removeDataFromTable(TBL_DW_DIM_COURSE);
	}

	public boolean removeDimModuleData() throws SQLException
	{
		return removeDataFromTable(TBL_DW_DIM_MODULE);
	}

	public boolean removeDimSubjectData() throws SQLException
	{
		return removeDataFromTable(TBL_DW_DIM_SUBJECT);
	}

	public boolean removeDimStudentData() throws SQLException
	{
		return removeDataFromTable(TBL_DW_DIM_STUDENT);
	}

	public boolean removeDimTutorData() throws SQLException
	{
		return removeDataFromTable(TBL_DW_DIM_TUTOR);
	}

	public boolean removeDimEnrollmentData() throws SQLException
	{
		return removeDataFromTable(TBL_DW_DIM_ENROLLMENT);
	}

	private boolean removeDataFromTable(String tblDwDimCourse) throws SQLException
	{
		boolean deleteSuccessful = false;
		AbstractOracleConnections conn = new AbstractOracleConnections();
		Connection oracleClient = conn.getDWClient();
		if(oracleClient != null)
		{
			String query = "TRUNCATE TABLE " + tblDwDimCourse
					+ " CASCADE";
			executeUpdateQuery(oracleClient, query);
			deleteSuccessful = true;
		}
		else
		{
			LOG.error("connection failure");
		}
		return deleteSuccessful;
	}

	public boolean setResultsData(DWResultsBean loadBean)
	{
		boolean successfulLoad = false;
		List<AssignmentsBean> assignments = loadBean.getAssignments();
		if(assignments != null && !assignments.isEmpty())
		{
			for (AssignmentsBean result : assignments)
			{
				AbstractOracleConnections conn = new AbstractOracleConnections();
				Connection oracleClient = conn.getDWClient();
				if(oracleClient != null)
				{
					try
					{
						Map<String, String> factTableAdditionalData = initExtraDataForFactTable(loadBean, result);
						String courseId = factTableAdditionalData.get("courseId");
						String subjectId = factTableAdditionalData.get("subjectId");
						String tutorId = factTableAdditionalData.get("tutorId");
						String isEnrolled = factTableAdditionalData.get("isEnrolled");
						String hasDropped = factTableAdditionalData.get("hasDropped");

						String values = "'" + result.getAssignmentId()
								+ "','" + result.getStudentId()
								+ "','" + courseId
								+ "','" + subjectId
								+ "','" + result.getModule()
								+ "','" + tutorId
								+ "','" + result.getAcademicYear()
								+ "','" + result.getSemester()
								+ "','" + result.getGrade()
								+ "','" + result.getResit()
								+ "','" + result.getResitGrade()
								+ "','" + isEnrolled
								+ "','" + hasDropped
								+ "','" + result.getInternational() + "'";
						String query = "INSERT INTO " + TBL_DW_RESULTS +
								"(Assignment_Id, Student_Id, Course_Id, Subject_Id, Module_Id, Tutor_Id, Academic_Year, Semester, Grade, Resit, Resit_Grade, Enrolled, Dropped, International)"
								+ " VALUES (" + values + ")";
						//Execute query
						executeAdditionQuery(oracleClient, query);
						successfulLoad = true;
					}
					catch (SQLException e)
					{
						LOG.error("Error adding entry to DW", e);
						successfulLoad = false;
						break;
					}
				}
				else
				{
					LOG.error("connection failure");
					successfulLoad = false;
				}
			}
		}
		else
		{
			//No Bean data to add, so the load (that never happened) did not fail
			successfulLoad = true;
		}
		return successfulLoad;
	}

	private Map<String, String> initExtraDataForFactTable(DWResultsBean loadBean, AssignmentsBean result)
	{
		String tutorId = null;
		String courseId = null;
		String subjectId = null;
		String isEnrolled = null;
		String hasDropped = null;
		String moduleId = result.getModule();
		Map<String, String> additionalParams = new HashMap<>();

		//Get Course using the module information
		List<CoursesBean> courses = loadBean.getCourses();
		boolean foundCourse = false;
		if(courses != null && !courses.isEmpty())
		{
			for (CoursesBean course : courses)
			{
				String moduleIds = course.getModuleIds();
				if(moduleIds != null)
				{
					String[] module = moduleIds.split(",");
					for (String modId : module)
					{
						if(modId.equals(moduleId))
						{
							courseId = course.getCourseId();
							foundCourse = true;
							break;
						}
					}
					if(foundCourse)
					{
						break;
					}
				}
			}
		}

		//Get Subject using the Course information
		if(foundCourse)
		{
			boolean foundSubject = false;
			List<SubjectsBean> subjects = loadBean.getSubjects();
			if(subjects != null && !subjects.isEmpty())
			{
				for (SubjectsBean subject : subjects)
				{
					String courseIds = subject.getCourses();
					if(courseIds != null)
					{
						String[] course = courseIds.split(",");
						for (String id : course)
						{
							if(id.equals(courseId))
							{
								subjectId = subject.getSubjectId();
								foundSubject = true;
								break;
							}
						}
						if(foundSubject)
						{
							break;
						}
					}
				}
			}
		}

		List<ModulesBean> modules = loadBean.getModules();
		if (modules != null && !modules.isEmpty())
		{
			for (ModulesBean module : modules)
			{
				if(moduleId.equals(module.getModuleId()))
				{
					tutorId = module.getModuleTutor();
					break;
				}
			}
		}

		//Now get extra enrollment information
		List<EnrollmentsBean> enrollments = loadBean.getEnrollments();
		if(enrollments != null && !enrollments.isEmpty())
		{
			for (EnrollmentsBean enrollment : enrollments)
			{
				if(enrollment.getStudentId().equals(result.getStudentId()))
				{
					hasDropped = enrollment.getHasDropped();
					isEnrolled = enrollment.getIsEnrolled();
					break;
				}
			}
		}

		if (tutorId == null)
		{
			tutorId = "Unknown";
		}
		if (courseId == null)
		{
			courseId = "Unknown";
		}
		if (subjectId == null)
		{
			subjectId = "Unknown";
		}
		if (isEnrolled == null)
		{
			isEnrolled = "Unknown";
		}
		if (hasDropped == null)
		{
			hasDropped = "Unknown";
		}

		additionalParams.put("courseId", courseId);
		additionalParams.put("subjectId", subjectId);
		additionalParams.put("tutorId", tutorId);
		additionalParams.put("isEnrolled", isEnrolled);
		additionalParams.put("hasDropped", hasDropped);

		return additionalParams;
	}

	private void executeUpdateQuery(Connection oracleClient, String query) throws SQLException
	{
		try (Statement statement = oracleClient.createStatement())
		{
			statement.executeUpdate(query);
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();
	}

	public boolean setDimStudentsData(DWResultsBean loadBean) throws Exception
	{
		boolean successfulLoad = false;
		List<DimStudentsBean> studentBeans = loadBean.getDimStudents();
		if(studentBeans != null && !studentBeans.isEmpty())
		{
			for (DimStudentsBean bean : studentBeans)
			{
				AbstractOracleConnections conn = new AbstractOracleConnections();
				Connection oracleClient = conn.getDWClient();
				if(oracleClient != null)
				{
					try
					{
						DataManager dataManager = new DataManager();
						DATE effectiveDate;
						DATE expiredDate;
						DATE dob;
						if (bean.getEffectiveDate() == null || bean.getEffectiveDate().equals("Unknown") || bean.getEffectiveDate().equals("None"))
						{
							effectiveDate = null;
						}
						else
						{
							effectiveDate = dataManager.convertValueToDate(bean.getEffectiveDate());
						}

						if(bean.getExpiredDate() == null || bean.getExpiredDate().equals("Unknown") || bean.getExpiredDate().equals("None"))
						{
							expiredDate = null;
						}
						else
						{
							expiredDate = dataManager.convertValueToDate(bean.getExpiredDate());
						}

						if(bean.getDob() == null || bean.getDob().equals("Unknown") || bean.getDob().equals("None"))
						{
							dob = null;
						}
						else
						{
							dob = dataManager.convertValueToDate(bean.getDob());
						}

						String values = "'" + bean.getDimensionId()
								+ "','" + bean.getStudentId()
								+ "','" + effectiveDate
								+ "','" + expiredDate
								+ "','" + bean.getIsCurrent()
								+ "','" + bean.getEmail()
								+ "','" + bean.getPword()
								+ "','" + bean.getFirstname()
								+ "','" + bean.getSurname()
								+ "','" + dob
								+ "','" + bean.getAddress() + "'";
						String query = "INSERT INTO " + TBL_DW_DIM_STUDENT +
								"(Dimension_Id, Student_Id, Date_Effective, Date_Expired, Is_Current, Email, Pword, Firstname, Surname, Dob, Address)" + " VALUES (" + values + ")";
						//Execute query
						executeAdditionQuery(oracleClient, query);
						successfulLoad = true;
					}
					catch (Exception e)
					{
						LOG.error("Error adding entry to DW", e);
						successfulLoad = false;
						throw e;
					}
				}
				else
				{
					LOG.error("connection failure");
					successfulLoad = false;
				}
			}
		}
		else
		{
			//No Bean data to add, so the load (that never happened) did not fail
			successfulLoad = true;
		}
		return successfulLoad;
	}

	public boolean setDimTutorsData(DWResultsBean loadBean)
	{
		boolean successfulLoad = false;
		List<DimTutorsBean> tutorBeans = loadBean.getDimTutors();
		if(tutorBeans != null && !tutorBeans.isEmpty())
		{
			for (DimTutorsBean bean : tutorBeans)
			{
				AbstractOracleConnections conn = new AbstractOracleConnections();
				Connection oracleClient = conn.getDWClient();
				if(oracleClient != null)
				{
					try
					{
						DataManager dataManager = new DataManager();
						DATE effectiveDate = dataManager.convertValueToDate(bean.getEffectiveDate());
						DATE expiredDate = dataManager.convertValueToDate(bean.getExpiredDate());
						DATE dob = dataManager.convertValueToDate(bean.getDob());
						String values = "'" + bean.getDimensionId()
								+ "','" + bean.getTutorId()
								+ "','" + effectiveDate
								+ "','" + expiredDate
								+ "','" + bean.getIsCurrent()
								+ "','" + bean.getEmail()
								+ "','" + bean.getPword()
								+ "','" + bean.getFirstname()
								+ "','" + bean.getSurname()
								+ "','" + dob
								+ "','" + bean.getAddress() + "'";
						String query = "INSERT INTO " + TBL_DW_DIM_TUTOR +
								"(Dimension_Id, Tutor_Id, Date_Effective, Date_Expired, Is_Current, Email, Pword, Firstname, Surname, Dob, Address)" + " VALUES (" + values + ")";
						//Execute query
						executeAdditionQuery(oracleClient, query);
						successfulLoad = true;
					}
					catch (Exception e)
					{
						LOG.error("Error adding entry to DW", e);
						successfulLoad = false;
						break;
					}
				}
				else
				{
					LOG.error("connection failure");
					successfulLoad = false;
				}
			}
		}
		else
		{
			//No Bean data to add, so the load (that never happened) did not fail
			successfulLoad = true;
		}
		return successfulLoad;
	}

	public boolean setDimModulesData(DWResultsBean loadBean)
	{
		boolean successfulLoad = false;
		List<DimModulesBean> modulesBeans = loadBean.getDimModules();
		if(modulesBeans != null && !modulesBeans.isEmpty())
		{
			for (DimModulesBean bean : modulesBeans)
			{
				AbstractOracleConnections conn = new AbstractOracleConnections();
				Connection oracleClient = conn.getDWClient();
				if(oracleClient != null)
				{
					try
					{
						DataManager dataManager = new DataManager();
						DATE effectiveDate = dataManager.convertValueToDate(bean.getEffectiveDate());
						DATE expiredDate = dataManager.convertValueToDate(bean.getExpiredDate());
						String values = "'" + bean.getDimensionId()
								+ "','" + bean.getModuleId()
								+ "','" + effectiveDate
								+ "','" + expiredDate
								+ "','" + bean.getIsCurrent()
								+ "','" + bean.getModuleName()
								+ "','" + bean.getModuleTutor()
								+ "','" + bean.getSemesterLength()
								+ "','" + bean.getSemester()
								+ "','" + bean.getCats() + "'";
						String query = "INSERT INTO " + TBL_DW_DIM_MODULE +
								"(Dimension_Id, Module_Id, Date_Effective, Date_Expired, Is_Current, Module_Name, Module_Tutor, Semester_Length, Semester, Cats)" + " VALUES (" + values + ")";
						//Execute query
						executeAdditionQuery(oracleClient, query);
						successfulLoad = true;
					}
					catch (Exception e)
					{
						LOG.error("Error adding entry to DW", e);
						successfulLoad = false;
						break;
					}
				}
				else
				{
					LOG.error("connection failure");
					successfulLoad = false;
				}
			}
		}
		else
		{
			//No Bean data to add, so the load (that never happened) did not fail
			successfulLoad = true;
		}
		return successfulLoad;
	}

	public boolean setDimCoursesData(DWResultsBean loadBean)
	{
		boolean successfulLoad = false;
		List<DimCoursesBean> coursesBeans = loadBean.getDimCourses();
		if(coursesBeans != null && !coursesBeans.isEmpty())
		{
			for (DimCoursesBean bean : coursesBeans)
			{
				AbstractOracleConnections conn = new AbstractOracleConnections();
				Connection oracleClient = conn.getDWClient();
				if(oracleClient != null)
				{
					try
					{
						DataManager dataManager = new DataManager();
						DATE effectiveDate = dataManager.convertValueToDate(bean.getEffectiveDate());
						DATE expiredDate = dataManager.convertValueToDate(bean.getExpiredDate());
						String values = "'" + bean.getDimensionId()
								+ "','" + bean.getCourseId()
								+ "','" + effectiveDate
								+ "','" + expiredDate
								+ "','" + bean.getIsCurrent()
								+ "','" + bean.getCourseName()
								+ "','" + bean.getModuleIds()
								+ "','" + bean.getTutor()
								+ "','" + bean.getSubjectId() + "'";
						String query = "INSERT INTO " + TBL_DW_DIM_COURSE +
								"(Dimension_Id, Course_Id, Date_Effective, Date_Expired, Is_Current, Course_Name, Module_Ids, Course_Tutor, Subject_Id)" + " VALUES (" + values + ")";
						//Execute query
						executeAdditionQuery(oracleClient, query);
						successfulLoad = true;
					}
					catch (Exception e)
					{
						LOG.error("Error adding entry to DW", e);
						successfulLoad = false;
						break;
					}
				}
				else
				{
					LOG.error("connection failure");
					successfulLoad = false;
				}
			}
		}
		else
		{
			//No Bean data to add, so the load (that never happened) did not fail
			successfulLoad = true;
		}
		return successfulLoad;
	}

	public boolean setDimSubjectData(DWResultsBean loadBean)
	{
		boolean successfulLoad = false;
		List<DimSubjectsBean> subjectsBeans = loadBean.getDimSubjects();
		if(subjectsBeans != null && !subjectsBeans.isEmpty())
		{
			for (DimSubjectsBean bean : subjectsBeans)
			{
				AbstractOracleConnections conn = new AbstractOracleConnections();
				Connection oracleClient = conn.getDWClient();
				if(oracleClient != null)
				{
					try
					{
						DataManager dataManager = new DataManager();
						DATE effectiveDate = dataManager.convertValueToDate(bean.getEffectiveDate());
						DATE expiredDate = dataManager.convertValueToDate(bean.getExpiredDate());
						String values = "'" + bean.getDimensionId()
								+ "','" + bean.getSubjectId()
								+ "','" + effectiveDate
								+ "','" + expiredDate
								+ "','" + bean.getIsCurrent()
								+ "','" + bean.getName()
								+ "','" + bean.getCourses() + "'";
						String query = "INSERT INTO " + TBL_DW_DIM_SUBJECT +
								"(Dimension_Id, Subject_Id, Date_Effective, Date_Expired, Is_Current, Subject_Name, Subject_Courses)" + " VALUES (" + values + ")";
						//Execute query
						executeAdditionQuery(oracleClient, query);
						successfulLoad = true;
					}
					catch (Exception e)
					{
						LOG.error("Error adding entry to DW", e);
						successfulLoad = false;
						break;
					}
				}
				else
				{
					LOG.error("connection failure");
					successfulLoad = false;
				}
			}
		}
		else
		{
			//No Bean data to add, so the load (that never happened) did not fail
			successfulLoad = true;
		}
		return successfulLoad;
	}

	public boolean setDimEnrollmentData(DWResultsBean loadBean)
	{
		boolean successfulLoad = false;
		List<DimEnrollmentsBean> enrollmentsBeans = loadBean.getDimEnrollments();
		if(enrollmentsBeans != null && !enrollmentsBeans.isEmpty())
		{
			for (DimEnrollmentsBean bean : enrollmentsBeans)
			{
				AbstractOracleConnections conn = new AbstractOracleConnections();
				Connection oracleClient = conn.getDWClient();
				if(oracleClient != null)
				{
					try
					{
						DataManager dataManager = new DataManager();
						DATE effectiveDate = dataManager.convertValueToDate(bean.getEffectiveDate());
						DATE expiredDate = dataManager.convertValueToDate(bean.getExpiredDate());
						String values = "'" + bean.getDimensionId()
								+ "','" + bean.getEnrollmentId()
								+ "','" + effectiveDate
								+ "','" + expiredDate
								+ "','" + bean.getIsCurrent()
								+ "','" + bean.getStudentId()
								+ "','" + bean.getCourseId()
								+ "','" + bean.getEnrollmentDate()
								+ "','" + bean.getHasDropped() + "'";
						String query = "INSERT INTO " + TBL_DW_DIM_ENROLLMENT +
								"(Dimension_Id, Enrollment_Id, Date_Effective, Date_Expired, Is_Current, Student_Id, Is_Enrolled, Course_Id, Enrollment_Date, Has_Dropped)" + " VALUES (" + values + ")";
						//Execute query
						executeAdditionQuery(oracleClient, query);
						successfulLoad = true;
					}
					catch (Exception e)
					{
						LOG.error("Error adding entry to DW", e);
						successfulLoad = false;
						break;
					}
				}
				else
				{
					LOG.error("connection failure");
					successfulLoad = false;
				}
			}
		}
		else
		{
			//No Bean data to add, so the load (that never happened) did not fail
			successfulLoad = true;
		}
		return successfulLoad;
	}

	private void executeAdditionQuery(Connection oracleClient, String query) throws SQLException
	{
		try (PreparedStatement preparedStatement = oracleClient.prepareStatement(query))
		{
			preparedStatement.executeUpdate(query);
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
			throw new SQLException("Exception Loading data using Query [" + query + "]. Exception:", e);
		}
		oracleClient.close();
	}
}
