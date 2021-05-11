package oracle;

import beans.dw.DWResultsBean;
import beans.operational.dimensions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

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
		//TODO - add bean to DW (Main data, dimensions have already been added)
		AbstractOracleConnections conn = new AbstractOracleConnections();
		Connection oracleClient = conn.getDWClient();
		if(oracleClient != null)
		{
			//TODO - SQL for adding assignments info into DW_RESULTS
			successfulLoad = true;
		}
		else
		{
			LOG.error("connection failure");
		}
		return successfulLoad;
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

	public boolean setDimStudentsData(DWResultsBean loadBean, HttpServletRequest request)
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
						String values = "'" + bean.getDimensionId()
								+ "','" + bean.getStudentId()
								+ "','" + bean.getEffectiveDate()
								+ "','" + bean.getExpiredDate()
								+ "','" + bean.getIsCurrent()
								+ "','" + bean.getEmail()
								+ "','" + bean.getPword()
								+ "','" + bean.getFirstname()
								+ "','" + bean.getSurname()
								+ "','" + bean.getDob()
								+ "','" + bean.getAddress() + "'";
						String query = "INSERT INTO " + TBL_DW_DIM_STUDENT +
								"(Dimension_Id, Student_Id, Date_Effective, Date_Expired, Is_Current, Email, Pword, Firstname, Surname, Dob, Address)" + " VALUES (" + values + ")";
						//Execute query
						executeAdditionQuery(oracleClient, query, request);
						successfulLoad = true;
					}
					catch (SQLException e)
					{
						request.getSession(true).setAttribute("exception2", "DimStudent Load Catch:" + e + Arrays.toString(e.getStackTrace()));
						LOG.error("Error adding entry to DW", e);
						successfulLoad = false;
						break;
					}
				}
				else
				{
					request.getSession(true).setAttribute("exception", "Connection failure");
					LOG.error("connection failure");
					successfulLoad = false;
				}
			}
		}
		else
		{
			request.getSession(true).setAttribute("exception", "No data to add? Bean:" + studentBeans);
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
						String values = "'" + bean.getDimensionId()
								+ "','" + bean.getTutorId()
								+ "','" + bean.getEffectiveDate()
								+ "','" + bean.getExpiredDate()
								+ "','" + bean.getIsCurrent()
								+ "','" + bean.getEmail()
								+ "','" + bean.getPword()
								+ "','" + bean.getFirstname()
								+ "','" + bean.getSurname()
								+ "','" + bean.getDob()
								+ "','" + bean.getAddress() + "'";
						String query = "INSERT INTO " + TBL_DW_DIM_TUTOR +
								"(Dimension_Id, Tutor_Id, Date_Effective, Date_Expired, Is_Current, Email, Pword, Firstname, Surname, Dob, Address)" + " VALUES (" + values + ")";
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
						String values = "'" + bean.getDimensionId()
								+ "','" + bean.getModuleId()
								+ "','" + bean.getEffectiveDate()
								+ "','" + bean.getExpiredDate()
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
						String values = "'" + bean.getDimensionId()
								+ "','" + bean.getCourseId()
								+ "','" + bean.getEffectiveDate()
								+ "','" + bean.getExpiredDate()
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
						String values = "'" + bean.getDimensionId()
								+ "','" + bean.getSubjectId()
								+ "','" + bean.getEffectiveDate()
								+ "','" + bean.getExpiredDate()
								+ "','" + bean.getIsCurrent()
								+ "','" + bean.getName()
								+ "','" + bean.getCourses() + "'";
						String query = "INSERT INTO " + TBL_DW_DIM_SUBJECT +
								"(Dimension_Id, Subject_Id, Date_Effective, Date_Expired, Is_Current, Subject_Name, Subject_Courses)" + " VALUES (" + values + ")";
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
						String values = "'" + bean.getDimensionId()
								+ "','" + bean.getEnrollmentId()
								+ "','" + bean.getEffectiveDate()
								+ "','" + bean.getExpiredDate()
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

	private void executeAdditionQuery(Connection oracleClient, String query) throws SQLException
	{
		try (PreparedStatement preparedStatement = oracleClient.prepareStatement(query))
		{
			preparedStatement.executeUpdate(query);
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();
	}

	private void executeAdditionQuery(Connection oracleClient, String query, HttpServletRequest request) throws SQLException
	{
		try (PreparedStatement preparedStatement = oracleClient.prepareStatement(query))
		{
			preparedStatement.executeUpdate(query);
			request.getSession(true).setAttribute("query", "Load Query is fine?");
		}
		catch(Exception e)
		{
			request.getSession(true).setAttribute("query", "Load Query Except:" + e + Arrays.toString(e.getStackTrace()));
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();
	}
}
