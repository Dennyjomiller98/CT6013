package oracle;

import beans.operational.*;
import beans.operational.dimensions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Denny-Jo
 * ExtractConnections - Provides Extended connectivity For Operational Data Extraction of the ETL Process
 * */
public class ExtractConnections extends AbstractOracleConnections
{
	public ExtractConnections()
	{
		//Empty Constructor
	}

	public List<AssignmentsBean> retrieveAssignmentsTable()
	{
		List<AssignmentsBean> ret = new ArrayList<>();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOPClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + TBL_ASSIGNMENTS;
				//Execute query
				List<AssignmentsBean> assignmentsBeans = executeAssignmentsQuery(oracleClient, query);
				if(!assignmentsBeans.isEmpty())
				{
					ret = assignmentsBeans;
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Error, could not retrieve data from table", e);
		}
		return ret;
	}

	public List<CoursesBean> retrieveCoursesTable()
	{
		List<CoursesBean> ret = new ArrayList<>();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOPClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + TBL_COURSES;
				//Execute query
				List<CoursesBean> coursesBeans = executeCoursesQuery(oracleClient, query);
				if(!coursesBeans.isEmpty())
				{
					ret = coursesBeans;
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Error, could not retrieve data from table", e);
		}
		return ret;
	}

	public List<DimCoursesBean> retrieveDimCoursesTable()
	{
		List<DimCoursesBean> ret = new ArrayList<>();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOPClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + TBL_DIM_COURSES;
				//Execute query
				List<DimCoursesBean> coursesBeans = executeDimCoursesQuery(oracleClient, query);
				if(!coursesBeans.isEmpty())
				{
					ret = coursesBeans;
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Error, could not retrieve data from table", e);
		}
		return ret;
	}

	public List<EnrollmentsBean> retrieveEnrollmentsTable()
	{
		List<EnrollmentsBean> ret = new ArrayList<>();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOPClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + TBL_ENROLLMENTS;
				//Execute query
				List<EnrollmentsBean> enrollmentsBeans = executeEnrollmentsQuery(oracleClient, query);
				if(!enrollmentsBeans.isEmpty())
				{
					ret = enrollmentsBeans;
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Error, could not retrieve data from table", e);
		}
		return ret;
	}

	public List<DimEnrollmentsBean> retrieveDimEnrollmentsTable()
	{
		List<DimEnrollmentsBean> ret = new ArrayList<>();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOPClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + TBL_DIM_ENROLLMENTS;
				//Execute query
				List<DimEnrollmentsBean> enrollmentsBeans = executeDimEnrollmentsQuery(oracleClient, query);
				if(!enrollmentsBeans.isEmpty())
				{
					ret = enrollmentsBeans;
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Error, could not retrieve data from table", e);
		}
		return ret;
	}

	public List<ModulesBean> retrieveModulesTable()
	{
		List<ModulesBean> ret = new ArrayList<>();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOPClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + TBL_MODULES;
				//Execute query
				List<ModulesBean> modulesBeans = executeModulesQuery(oracleClient, query);
				if(!modulesBeans.isEmpty())
				{
					ret = modulesBeans;
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Error, could not retrieve data from table", e);
		}
		return ret;
	}

	public List<DimModulesBean> retrieveDimModulesTable()
	{
		List<DimModulesBean> ret = new ArrayList<>();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOPClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + TBL_DIM_MODULES;
				//Execute query
				List<DimModulesBean> modulesBeans = executeDimModulesQuery(oracleClient, query);
				if(!modulesBeans.isEmpty())
				{
					ret = modulesBeans;
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Error, could not retrieve data from table", e);
		}
		return ret;
	}

	public List<StudentsBean> retrieveStudentsTable()
	{
		List<StudentsBean> ret = new ArrayList<>();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOPClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + TBL_STUDENTS;
				//Execute query
				List<StudentsBean> studentsBeans = executeStudentsQuery(oracleClient, query);
				if(!studentsBeans.isEmpty())
				{
					ret = studentsBeans;
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Error, could not retrieve data from table", e);
		}
		return ret;
	}

	public List<DimStudentsBean> retrieveDimStudentsTable(HttpServletRequest request)
	{
		List<DimStudentsBean> ret = new ArrayList<>();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOPClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + TBL_DIM_STUDENTS;
				//Execute query
				List<DimStudentsBean> studentsBeans = executeDimStudentsQuery(oracleClient, query);
				if(!studentsBeans.isEmpty())
				{
					ret = studentsBeans;
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Error, could not retrieve data from table", e);
			request.getSession(true).setAttribute("exception", "exception: " + e + Arrays.toString(e.getStackTrace()));
		}
		return ret;
	}

	public List<SubjectsBean> retrieveSubjectsTable()
	{
		List<SubjectsBean> ret = new ArrayList<>();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOPClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + TBL_SUBJECTS;
				//Execute query
				List<SubjectsBean> subjectsBeans = executeSubjectsQuery(oracleClient, query);
				if(!subjectsBeans.isEmpty())
				{
					ret = subjectsBeans;
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Error, could not retrieve data from table", e);
		}
		return ret;
	}

	public List<DimSubjectsBean> retrieveDimSubjectsTable()
	{
		List<DimSubjectsBean> ret = new ArrayList<>();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOPClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + TBL_DIM_SUBJECTS;
				//Execute query
				List<DimSubjectsBean> subjectsBeans = executeDimSubjectsQuery(oracleClient, query);
				if(!subjectsBeans.isEmpty())
				{
					ret = subjectsBeans;
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Error, could not retrieve data from table", e);
		}
		return ret;
	}

	public List<TutorsBean> retrieveTutorsTable()
	{
		List<TutorsBean> ret = new ArrayList<>();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOPClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + TBL_TUTORS;
				//Execute query
				List<TutorsBean> tutorsBeans = executeTutorsQuery(oracleClient, query);
				if(!tutorsBeans.isEmpty())
				{
					ret = tutorsBeans;
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Error, could not retrieve data from table", e);
		}
		return ret;
	}

	public List<DimTutorsBean> retrieveDimTutorsTable()
	{
		List<DimTutorsBean> ret = new ArrayList<>();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOPClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + TBL_DIM_TUTORS;
				//Execute query
				List<DimTutorsBean> tutorsBeans = executeDimTutorsQuery(oracleClient, query);
				if(!tutorsBeans.isEmpty())
				{
					ret = tutorsBeans;
				}
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Error, could not retrieve data from table", e);
		}
		return ret;
	}

	private List<DimStudentsBean> executeDimStudentsQuery(Connection oracleClient, String query) throws SQLException
	{
		//Executes SQL Query
		List<DimStudentsBean> allBeans = new ArrayList<>();
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

	private List<StudentsBean> executeStudentsQuery(Connection oracleClient, String query) throws SQLException
	{
		//Executes SQL Query
		List<StudentsBean> allBeans = new ArrayList<>();
		try (PreparedStatement preparedStatement = oracleClient.prepareStatement(query))
		{
			ResultSet resultSet = preparedStatement.executeQuery(query);
			while (resultSet.next())
			{
				StudentsBean bean = new StudentsBean(resultSet);
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

	private List<DimSubjectsBean> executeDimSubjectsQuery(Connection oracleClient, String query) throws SQLException
	{
		//Executes SQL Query
		List<DimSubjectsBean> allBeans = new ArrayList<>();
		try (PreparedStatement preparedStatement = oracleClient.prepareStatement(query))
		{
			ResultSet resultSet = preparedStatement.executeQuery(query);
			while (resultSet.next())
			{
				DimSubjectsBean bean = new DimSubjectsBean(resultSet);
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

	private List<SubjectsBean> executeSubjectsQuery(Connection oracleClient, String query) throws SQLException
	{
		//Executes SQL Query
		List<SubjectsBean> allBeans = new ArrayList<>();
		try (PreparedStatement preparedStatement = oracleClient.prepareStatement(query))
		{
			ResultSet resultSet = preparedStatement.executeQuery(query);
			while (resultSet.next())
			{
				SubjectsBean bean = new SubjectsBean(resultSet);
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

	private List<DimEnrollmentsBean> executeDimEnrollmentsQuery(Connection oracleClient, String query) throws SQLException
	{
		//Executes SQL Query
		List<DimEnrollmentsBean> allBeans = new ArrayList<>();
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

	private List<EnrollmentsBean> executeEnrollmentsQuery(Connection oracleClient, String query) throws SQLException
	{
		//Executes SQL Query
		List<EnrollmentsBean> allBeans = new ArrayList<>();
		try (PreparedStatement preparedStatement = oracleClient.prepareStatement(query))
		{
			ResultSet resultSet = preparedStatement.executeQuery(query);
			while (resultSet.next())
			{
				EnrollmentsBean bean = new EnrollmentsBean(resultSet);
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

	private List<DimTutorsBean> executeDimTutorsQuery(Connection oracleClient, String query) throws SQLException
	{
		//Executes SQL Query
		List<DimTutorsBean> allBeans = new ArrayList<>();
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

	private List<TutorsBean> executeTutorsQuery(Connection oracleClient, String query) throws SQLException
	{
		//Executes SQL Query
		List<TutorsBean> allBeans = new ArrayList<>();
		try (PreparedStatement preparedStatement = oracleClient.prepareStatement(query))
		{
			ResultSet resultSet = preparedStatement.executeQuery(query);
			while (resultSet.next())
			{
				TutorsBean bean = new TutorsBean(resultSet);
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

	private List<ModulesBean> executeModulesQuery(Connection oracleClient, String query) throws SQLException
	{
		//Executes SQL Query
		List<ModulesBean> allBeans = new ArrayList<>();
		try (PreparedStatement preparedStatement = oracleClient.prepareStatement(query))
		{
			ResultSet resultSet = preparedStatement.executeQuery(query);
			while (resultSet.next())
			{
				ModulesBean bean = new ModulesBean(resultSet);
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

	private List<DimModulesBean> executeDimModulesQuery(Connection oracleClient, String query) throws SQLException
	{
		//Executes SQL Query
		List<DimModulesBean> allBeans = new ArrayList<>();
		try (PreparedStatement preparedStatement = oracleClient.prepareStatement(query))
		{
			ResultSet resultSet = preparedStatement.executeQuery(query);
			while (resultSet.next())
			{
				DimModulesBean bean = new DimModulesBean(resultSet);
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

	private List<AssignmentsBean> executeAssignmentsQuery(Connection oracleClient, String query) throws SQLException
	{
		//Executes SQL Query
		List<AssignmentsBean> allBeans = new ArrayList<>();
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

	private List<DimCoursesBean> executeDimCoursesQuery(Connection oracleClient, String query) throws SQLException
	{
		//Executes SQL Query
		List<DimCoursesBean> allBeans = new ArrayList<>();
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

	private List<CoursesBean> executeCoursesQuery(Connection oracleClient, String query) throws SQLException
	{
		//Executes SQL Query
		List<CoursesBean> allBeans = new ArrayList<>();
		try (PreparedStatement preparedStatement = oracleClient.prepareStatement(query))
		{
			ResultSet resultSet = preparedStatement.executeQuery(query);
			while (resultSet.next())
			{
				CoursesBean bean = new CoursesBean(resultSet);
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
