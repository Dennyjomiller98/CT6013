package oracle;

import beans.operational.AssignmentsBean;
import beans.operational.dimensions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataViewConnections extends AbstractOracleConnections
{
	public DataViewConnections()
	{
		//Empty Constructor
	}

	public List<DimStudentsBean> getDWStudents()
	{
		List<DimStudentsBean> ret = new ArrayList<>();
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getDWClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + TBL_DW_DIM_STUDENT;
				//Execute query
				ArrayList<DimStudentsBean> allBeans = executeStudentsQuery(oracleClient, query);
				if(!allBeans.isEmpty())
				{
					ret = allBeans;
				}
				else
				{
					LOG.debug("No Results retrieved from DW.");
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to retrieve DW Data", e);
		}
		return ret;
	}

	public List<DimEnrollmentsBean> getDWEnrollments(String yearSelect, String courseSelect)
	{
		List<DimEnrollmentsBean> ret = new ArrayList<>();
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getDWClient();
			if(oracleClient != null)
			{
				//Select Query
				String query;
				query = getEnrollmentQuery(yearSelect, courseSelect);

				//Execute query
				ArrayList<DimEnrollmentsBean> allBeans = executeEnrollmentsQuery(oracleClient, query);
				if(!allBeans.isEmpty())
				{
					ret = allBeans;
				}
				else
				{
					LOG.debug("No Results retrieved from DW.");
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to retrieve DW Data", e);
		}
		return ret;
	}

	private String getEnrollmentQuery(String yearSelect, String courseSelect)
	{
		String query;
		if(yearSelect != null || courseSelect != null)
		{
			if(yearSelect != null && courseSelect != null)
			{
				//course select year select
				query = "SELECT * FROM " + TBL_DW_DIM_ENROLLMENT + " WHERE Is_Current='true' AND Is_Enrolled='true' AND Course_Id='" + courseSelect +"' AND Enrollment_Date LIKE '"+ yearSelect +"%'";
			}
			else if(yearSelect != null)
			{
				//all course year select
				query = "SELECT * FROM " + TBL_DW_DIM_ENROLLMENT + " WHERE Is_Current='true' AND Is_Enrolled='true' AND Enrollment_Date LIKE '"+ yearSelect +"%'";
			}
			else
			{
				//Course select all years
				query = "SELECT * FROM " + TBL_DW_DIM_ENROLLMENT + " WHERE Is_Current='true' AND Is_Enrolled='true' AND Course_Id='" + courseSelect +"'";
			}
		}
		else
		{
			//Default ALL
			query = "SELECT * FROM " + TBL_DW_DIM_ENROLLMENT + " WHERE Is_Current='true' AND Is_Enrolled='true'";
		}
		return query;
	}

	public List<DimCoursesBean> getDWCourses()
	{
		List<DimCoursesBean> ret = new ArrayList<>();
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getDWClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + TBL_DW_DIM_COURSE;
				//Execute query
				ArrayList<DimCoursesBean> allBeans = executeCoursesQuery(oracleClient, query);
				if(!allBeans.isEmpty())
				{
					ret = allBeans;
				}
				else
				{
					LOG.debug("No Results retrieved from DW.");
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to retrieve DW Data", e);
		}
		return ret;
	}

	public List<DimTutorsBean> getDWTutors()
	{
		List<DimTutorsBean> ret = new ArrayList<>();
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getDWClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + TBL_DW_DIM_TUTOR;
				//Execute query
				ArrayList<DimTutorsBean> allBeans = executeTutorsQuery(oracleClient, query);
				if(!allBeans.isEmpty())
				{
					ret = allBeans;
				}
				else
				{
					LOG.debug("No Results retrieved from DW.");
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to retrieve DW Data", e);
		}
		return ret;
	}

	public List<AssignmentsBean> getDWResults(String yearSelect, String courseSelect)
	{
		List<AssignmentsBean> ret = new ArrayList<>();
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getDWClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = getResultsQuery(yearSelect, courseSelect);
				//Execute query
				ArrayList<AssignmentsBean> allBeans = executeResultsQuery(oracleClient, query);
				if(!allBeans.isEmpty())
				{
					ret = allBeans;
				}
				else
				{
					LOG.debug("No Results retrieved from DW.");
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to retrieve DW Data", e);
		}
		return ret;
	}

	private String getResultsQuery(String yearSelect, String courseSelect)
	{
		String query;
		if(yearSelect != null || courseSelect != null)
		{
			if(yearSelect != null && courseSelect != null)
			{
				//course select year select
				query = "SELECT * FROM " + TBL_DW_RESULTS + " WHERE Course_Id='" + courseSelect +"' AND Academic_Year='"+ yearSelect +"'";
			}
			else if(yearSelect != null)
			{
				//all course year select
				query = "SELECT * FROM " + TBL_DW_RESULTS + " WHERE Academic_Year='"+ yearSelect +"'";
			}
			else
			{
				//Course select all years
				query = "SELECT * FROM " + TBL_DW_RESULTS + " WHERE Course_Id='" + courseSelect +"'";
			}
		}
		else
		{
			//Default ALL
			query = "SELECT * FROM " + TBL_DW_RESULTS;
		}
		return query;
	}

	public List<DimModulesBean> getDWModules()
	{
		//TODO
		return null;
	}

	public List<DimSubjectsBean> getDWSubjects()
	{
		//TODO
		return null;
	}

	private ArrayList<DimStudentsBean> executeStudentsQuery(Connection oracleClient, String query) throws SQLException
	{
		ArrayList<DimStudentsBean> allBeans = new ArrayList<>();
		try (PreparedStatement preparedStatement = oracleClient.prepareStatement(query))
		{
			ResultSet resultSet = preparedStatement.executeQuery(query);
			while (resultSet.next())
			{
				DimStudentsBean bean = new DimStudentsBean(resultSet);
				allBeans.add(bean);
			}
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();
		return allBeans;
	}

	private ArrayList<DimTutorsBean> executeTutorsQuery(Connection oracleClient, String query) throws SQLException
	{
		ArrayList<DimTutorsBean> allBeans = new ArrayList<>();
		try (PreparedStatement preparedStatement = oracleClient.prepareStatement(query))
		{
			ResultSet resultSet = preparedStatement.executeQuery(query);
			while (resultSet.next())
			{
				DimTutorsBean bean = new DimTutorsBean(resultSet);
				allBeans.add(bean);
			}
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();
		return allBeans;
	}

	private ArrayList<DimCoursesBean> executeCoursesQuery(Connection oracleClient, String query) throws SQLException
	{
		ArrayList<DimCoursesBean> allBeans = new ArrayList<>();
		try (PreparedStatement preparedStatement = oracleClient.prepareStatement(query))
		{
			ResultSet resultSet = preparedStatement.executeQuery(query);
			while (resultSet.next())
			{
				DimCoursesBean bean = new DimCoursesBean(resultSet);
				allBeans.add(bean);
			}
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();
		return allBeans;
	}

	private ArrayList<DimEnrollmentsBean> executeEnrollmentsQuery(Connection oracleClient, String query) throws SQLException
	{
		ArrayList<DimEnrollmentsBean> allBeans = new ArrayList<>();
		try (PreparedStatement preparedStatement = oracleClient.prepareStatement(query))
		{
			ResultSet resultSet = preparedStatement.executeQuery(query);
			while (resultSet.next())
			{
				DimEnrollmentsBean bean = new DimEnrollmentsBean(resultSet);
				allBeans.add(bean);
			}
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();
		return allBeans;
	}

	private ArrayList<AssignmentsBean> executeResultsQuery(Connection oracleClient, String query) throws SQLException
	{
		ArrayList<AssignmentsBean> allBeans = new ArrayList<>();
		try (PreparedStatement preparedStatement = oracleClient.prepareStatement(query))
		{
			ResultSet resultSet = preparedStatement.executeQuery(query);
			while (resultSet.next())
			{
				AssignmentsBean bean = new AssignmentsBean(resultSet);
				allBeans.add(bean);
			}
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();
		return allBeans;
	}
}
