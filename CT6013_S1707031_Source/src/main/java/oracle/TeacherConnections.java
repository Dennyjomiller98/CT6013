package oracle;

import beans.TeacherBean;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class TeacherConnections extends AbstractOracleConnections
{
	public TeacherConnections()
	{
		//Empty Constructor
	}

	public void registerTeacherToDB(Document teacher)
	{
		LOG.debug("Beginning Teacher Registration");
		//Create Mongo Client, access DB and Retrieve Collection to insert new Teacher Document into
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if(oracleClient != null)
			{
				//Add Teacher to DB
				String values = "'" +teacher.getString("First_Name")
						+ "','" + teacher.getString("Surname")
						+ "','" + teacher.getString("Email")
						+ "','" + teacher.getString("DOB")
						+ "','" + teacher.getString("Address")
						+ "','" + teacher.getString("Password")
						+ "','" + teacher.getString("Is_Enrolled")
						+ "','" + teacher.getString("Is_Teacher") + "'";
				String query = "INSERT INTO " + TEACHERS_COLLECTION +
						"(First_Name, Surname, Email, DOB, Address, Password, Is_Enrolled, Is_Teacher)" + " VALUES (" + values + ")";

				//Execute query
				executeTeacherUpdateQuery(oracleClient, query);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to register teacher to Oracle", e);
		}
	}

	private void executeTeacherUpdateQuery(Connection oracleClient, String query) throws SQLException
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

	public List<TeacherBean> retrieveAllTeachers()
	{
		ArrayList<TeacherBean> allTeachers = new ArrayList<>();

		LOG.debug("Beginning all Teacher retrieval");
		//Create Mongo Client, access DB and Retrieve Teachers
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + TEACHERS_COLLECTION;

				//Execute query
				allTeachersRetrievalQuery(oracleClient, query, allTeachers);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to retrieve all teachers from Oracle", e);
		}
		return allTeachers;
	}

	private void allTeachersRetrievalQuery(Connection oracleClient, String query, ArrayList<TeacherBean> allTeachers) throws SQLException
	{
		try (Statement statement = oracleClient.createStatement())
		{
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
			{
				TeacherBean bean = new TeacherBean();
				bean.setFirstName(resultSet.getString("First_Name"));
				bean.setSurname(resultSet.getString("Surname"));
				bean.setEmail(resultSet.getString("Email"));
				bean.setDOB(resultSet.getString("DOB"));
				bean.setAddress(resultSet.getString("Address"));
				bean.setPassword(resultSet.getString("Password"));
				bean.setEnrolled(Boolean.parseBoolean(resultSet.getString("Is_Enrolled")));
				bean.setTeacher(Boolean.parseBoolean(resultSet.getString("Is_Teacher")));

				//Add bean to list of students
				allTeachers.add(bean);
			}
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();
	}

	public TeacherBean retrieveSingleTeacher(String teacherEmail)
	{
		LOG.debug("Attempting Retrieval of single Teacher: " + teacherEmail);
		TeacherBean beanToReturn = null;
		List<TeacherBean> teacherBeans = retrieveAllTeachers();
		for (TeacherBean teacherBean : teacherBeans)
		{
			if(teacherBean.getEmail().equalsIgnoreCase(teacherEmail))
			{
				beanToReturn = teacherBean;
			}
		}
		return beanToReturn;
	}

	public boolean attemptLogin(TeacherBean teacherBean)
	{
		LOG.debug("Attempting Teacher Login Oracle");
		//Find the associated email in DB and check login credentials are correct
		boolean isCorrectCredentials = false;
		String email = teacherBean.getEmail();
		String password = teacherBean.getPassword();
		TeacherBean potentialTeacher = retrieveSingleTeacher(email);
		if(potentialTeacher != null && password.equals(potentialTeacher.getPassword()))
		{
			isCorrectCredentials = true;
		}
		return isCorrectCredentials;
	}

	public void updateTeacherDetails(Document teacherToUpdate, String email)
	{
		LOG.debug("attempting to update Teacher details (Oracle)");
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if (oracleClient != null)
			{
				String query = "UPDATE " + TEACHERS_COLLECTION + " SET First_Name ='" + teacherToUpdate.getString("First_Name")
						+"', Surname='"+ teacherToUpdate.getString("Surname")
						+"', Email='"+ teacherToUpdate.getString("Email")
						+"', DOB='" + teacherToUpdate.getString("DOB")
						+"', Address='"+ teacherToUpdate.getString("Address")
						+"', Password='"+ teacherToUpdate.getString("Password")
						+"', Is_Enrolled='"+ teacherToUpdate.getString("Is_Enrolled")
						+"', Is_Teacher='"+ teacherToUpdate.getString("Is_Teacher")
						+"' WHERE EMAIL='"+ email +"'";

				executeTeacherUpdateQuery(oracleClient, query);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to update Teacher information");
		}
	}
}