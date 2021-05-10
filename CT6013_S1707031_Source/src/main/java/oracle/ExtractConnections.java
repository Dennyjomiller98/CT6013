package oracle;

import beans.operational.*;
import beans.operational.dimensions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
				ResultSet resultSet = executeQuery(oracleClient, query);
				List<AssignmentsBean> assignmentsBeans;
				if(resultSet != null)
				{
					assignmentsBeans = assigmentsBeansCreation(resultSet);
					if(!assignmentsBeans.isEmpty())
					{
						ret = assignmentsBeans;
					}
					else
					{
						LOG.debug("No Assignments Found.");
					}
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

	private List<AssignmentsBean> assigmentsBeansCreation(ResultSet resultSet) throws SQLException
	{
		List<AssignmentsBean> allBeans = new ArrayList<>();
		while (resultSet.next())
		{
			AssignmentsBean bean = new AssignmentsBean(resultSet);
			//Add bean to list
			allBeans.add(bean);
		}
		return allBeans;
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
				ResultSet resultSet = executeQuery(oracleClient, query);
				List<CoursesBean> coursesBeans;
				if(resultSet != null)
				{
					coursesBeans = coursesBeansCreation(resultSet);
					if(!coursesBeans.isEmpty())
					{
						ret = coursesBeans;
					}
					else
					{
						LOG.debug("No Courses Found.");
					}
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

	private List<CoursesBean> coursesBeansCreation(ResultSet resultSet) throws SQLException
	{
		List<CoursesBean> allBeans = new ArrayList<>();
		while (resultSet.next())
		{
			CoursesBean bean = new CoursesBean(resultSet);
			//Add bean to list
			allBeans.add(bean);
		}
		return allBeans;
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
				ResultSet resultSet = executeQuery(oracleClient, query);
				List<DimCoursesBean> coursesBeans;
				if(resultSet != null)
				{
					coursesBeans = dimCoursesBeansCreation(resultSet);
					if(!coursesBeans.isEmpty())
					{
						ret = coursesBeans;
					}
					else
					{
						LOG.debug("No Courses Found.");
					}
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

	private List<DimCoursesBean> dimCoursesBeansCreation(ResultSet resultSet) throws SQLException
	{
		List<DimCoursesBean> allBeans = new ArrayList<>();
		while (resultSet.next())
		{
			DimCoursesBean bean = new DimCoursesBean(resultSet);
			//Add bean to list
			allBeans.add(bean);
		}
		return allBeans;
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
				ResultSet resultSet = executeQuery(oracleClient, query);
				List<EnrollmentsBean> enrollmentsBeans;
				if(resultSet != null)
				{
					enrollmentsBeans = enrollmentsBeansCreation(resultSet);
					if(!enrollmentsBeans.isEmpty())
					{
						ret = enrollmentsBeans;
					}
					else
					{
						LOG.debug("No Enrollments Found.");
					}
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

	private List<EnrollmentsBean> enrollmentsBeansCreation(ResultSet resultSet) throws SQLException
	{
		List<EnrollmentsBean> allBeans = new ArrayList<>();
		while (resultSet.next())
		{
			EnrollmentsBean bean = new EnrollmentsBean(resultSet);
			//Add bean to list
			allBeans.add(bean);
		}
		return allBeans;
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
				ResultSet resultSet = executeQuery(oracleClient, query);
				List<DimEnrollmentsBean> enrollmentsBeans;
				if(resultSet != null)
				{
					enrollmentsBeans = dimEnrollmentsBeansCreation(resultSet);
					if(!enrollmentsBeans.isEmpty())
					{
						ret = enrollmentsBeans;
					}
					else
					{
						LOG.debug("No Enrollments Found.");
					}
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

	private List<DimEnrollmentsBean> dimEnrollmentsBeansCreation(ResultSet resultSet) throws SQLException
	{
		List<DimEnrollmentsBean> allBeans = new ArrayList<>();
		while (resultSet.next())
		{
			DimEnrollmentsBean bean = new DimEnrollmentsBean(resultSet);
			//Add bean to list
			allBeans.add(bean);
		}
		return allBeans;
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
				ResultSet resultSet = executeQuery(oracleClient, query);
				List<ModulesBean> modulesBeans;
				if(resultSet != null)
				{
					modulesBeans = modulesBeansCreation(resultSet);
					if(!modulesBeans.isEmpty())
					{
						ret = modulesBeans;
					}
					else
					{
						LOG.debug("No Modules Found.");
					}
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

	private List<ModulesBean> modulesBeansCreation(ResultSet resultSet) throws SQLException
	{
		List<ModulesBean> allBeans = new ArrayList<>();
		while (resultSet.next())
		{
			ModulesBean bean = new ModulesBean(resultSet);
			//Add bean to list
			allBeans.add(bean);
		}
		return allBeans;
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
				ResultSet resultSet = executeQuery(oracleClient, query);
				List<DimModulesBean> modulesBeans;
				if(resultSet != null)
				{
					modulesBeans = dimModulesBeansCreation(resultSet);
					if(!modulesBeans.isEmpty())
					{
						ret = modulesBeans;
					}
					else
					{
						LOG.debug("No Modules Found.");
					}
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

	private List<DimModulesBean> dimModulesBeansCreation(ResultSet resultSet) throws SQLException
	{
		List<DimModulesBean> allBeans = new ArrayList<>();
		while (resultSet.next())
		{
			DimModulesBean bean = new DimModulesBean(resultSet);
			//Add bean to list
			allBeans.add(bean);
		}
		return allBeans;
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
				ResultSet resultSet = executeQuery(oracleClient, query);
				List<StudentsBean> studentsBeans;
				if(resultSet != null)
				{
					studentsBeans = studentsBeansCreation(resultSet);
					if(!studentsBeans.isEmpty())
					{
						ret = studentsBeans;
					}
					else
					{
						LOG.debug("No Students Found.");
					}
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

	private List<StudentsBean> studentsBeansCreation(ResultSet resultSet) throws SQLException
	{
		List<StudentsBean> allBeans = new ArrayList<>();
		while (resultSet.next())
		{
			StudentsBean bean = new StudentsBean(resultSet);
			//Add bean to list
			allBeans.add(bean);
		}
		return allBeans;
	}

	public List<DimStudentsBean> retrieveDimStudentsTable()
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
				ResultSet resultSet = executeQuery(oracleClient, query);
				List<DimStudentsBean> studentsBeans;
				if(resultSet != null)
				{
					studentsBeans = dimStudentsBeansCreation(resultSet);
					if(!studentsBeans.isEmpty())
					{
						ret = studentsBeans;
					}
					else
					{
						LOG.debug("No Students Found.");
					}
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

	private List<DimStudentsBean> dimStudentsBeansCreation(ResultSet resultSet) throws SQLException
	{
		List<DimStudentsBean> allBeans = new ArrayList<>();
		while (resultSet.next())
		{
			DimStudentsBean bean = new DimStudentsBean(resultSet);
			//Add bean to list
			allBeans.add(bean);
		}
		return allBeans;
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
				ResultSet resultSet = executeQuery(oracleClient, query);
				List<SubjectsBean> subjectsBeans;
				if(resultSet != null)
				{
					subjectsBeans = subjectsBeansCreation(resultSet);
					if(!subjectsBeans.isEmpty())
					{
						ret = subjectsBeans;
					}
					else
					{
						LOG.debug("No Subjects Found.");
					}
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

	private List<SubjectsBean> subjectsBeansCreation(ResultSet resultSet) throws SQLException
	{
		List<SubjectsBean> allBeans = new ArrayList<>();
		while (resultSet.next())
		{
			SubjectsBean bean = new SubjectsBean(resultSet);
			//Add bean to list
			allBeans.add(bean);
		}
		return allBeans;
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
				ResultSet resultSet = executeQuery(oracleClient, query);
				List<DimSubjectsBean> subjectsBeans;
				if(resultSet != null)
				{
					subjectsBeans = dimSubjectsBeansCreation(resultSet);
					if(!subjectsBeans.isEmpty())
					{
						ret = subjectsBeans;
					}
					else
					{
						LOG.debug("No Subjects Found.");
					}
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

	private List<DimSubjectsBean> dimSubjectsBeansCreation(ResultSet resultSet) throws SQLException
	{
		List<DimSubjectsBean> allBeans = new ArrayList<>();
		while (resultSet.next())
		{
			DimSubjectsBean bean = new DimSubjectsBean(resultSet);
			//Add bean to list
			allBeans.add(bean);
		}
		return allBeans;
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
				ResultSet resultSet = executeQuery(oracleClient, query);
				List<TutorsBean> tutorsBeans;
				if(resultSet != null)
				{
					tutorsBeans = tutorsBeansCreation(resultSet);
					if(!tutorsBeans.isEmpty())
					{
						ret = tutorsBeans;
					}
					else
					{
						LOG.debug("No Tutors Found.");
					}
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

	private List<TutorsBean> tutorsBeansCreation(ResultSet resultSet) throws SQLException
	{
		List<TutorsBean> allBeans = new ArrayList<>();
		while (resultSet.next())
		{
			TutorsBean bean = new TutorsBean(resultSet);
			//Add bean to list
			allBeans.add(bean);
		}
		return allBeans;
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
				ResultSet resultSet = executeQuery(oracleClient, query);
				List<DimTutorsBean> tutorsBeans;
				if(resultSet != null)
				{
					tutorsBeans = dimTutorsBeansCreation(resultSet);
					if(!tutorsBeans.isEmpty())
					{
						ret = tutorsBeans;
					}
					else
					{
						LOG.debug("No Tutors Found.");
					}
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

	private List<DimTutorsBean> dimTutorsBeansCreation(ResultSet resultSet) throws SQLException
	{
		List<DimTutorsBean> allBeans = new ArrayList<>();
		while (resultSet.next())
		{
			DimTutorsBean bean = new DimTutorsBean(resultSet);
			//Add bean to list
			allBeans.add(bean);
		}
		return allBeans;
	}

	private ResultSet executeQuery(Connection oracleClient, String query) throws SQLException
	{
		ResultSet ret = null;
		//Executes SQL Query
		try (PreparedStatement preparedStatement = oracleClient.prepareStatement(query))
		{
			ResultSet resultSet = preparedStatement.executeQuery(query);
			if(resultSet != null)
			{
				ret = resultSet;
			}
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();

		return ret;
	}
}
