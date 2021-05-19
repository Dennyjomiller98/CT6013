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
				ArrayList<DimStudentsBean> allStudents = executeStudentsQuery(oracleClient, query);
				if(!allStudents.isEmpty())
				{
					ret = allStudents;
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
		//TODO
		return null;
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

	public List<DimTutorsBean> getDWTutors()
	{
		//TODO
		return null;
	}

	public List<AssignmentsBean> getDWResults()
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
}
