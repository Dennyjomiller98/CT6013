package oracle;

import beans.StudentBean;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import servlets.users.PasswordEncryptDecrypt;

public class StudentConnections extends AbstractOracleConnections
{
	public StudentConnections()
	{
		//Empty Constructor
	}

	public void registerStudentToDB(Document student) {
		LOG.debug("Beginning Student Registration");
		//Create Mongo Client, access DB and Retrieve Collection to insert new student Document into DB
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if(oracleClient != null)
			{
				//Add Student to DB
				String values = "'" +student.getString("First_Name")
						+ "','" + student.getString("Surname")
						+ "','" + student.getString("Email")
						+ "','" + student.getString("DOB")
						+ "','" + student.getString("Address")
						+ "','" + student.getString("Password")
						+ "','" + student.getString("Is_Enrolled")
						+ "','" + student.getString("Is_Teacher") + "'";
				String query = "INSERT INTO " + STUDENTS_COLLECTION +
						"(First_Name, Surname, Email, DOB, Address, Password, Is_Enrolled, Is_Teacher)" + " VALUES (" + values + ")";

				//Execute query
				executeStudentUpdateQuery(oracleClient, query);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to register student to Oracle", e);
		}
	}

	private void executeStudentUpdateQuery(Connection oracleClient, String query) throws SQLException
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

	public List<StudentBean> retrieveAllStudents()
	{
		ArrayList<StudentBean> allStudents = new ArrayList<>();

		LOG.debug("Beginning all Student retrieval");
		//Create Mongo Client, access DB and Retrieve Collection to insert new student Document into
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + STUDENTS_COLLECTION;

				//Execute query
				allStudentsRetrievalQuery(oracleClient, query, allStudents);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to retrieve all students from Oracle", e);
		}
		return allStudents;
	}

	private void allStudentsRetrievalQuery(Connection oracleClient, String query, ArrayList<StudentBean> allStudents) throws SQLException
	{
		try (Statement statement = oracleClient.createStatement())
		{
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
			{
				StudentBean bean = new StudentBean();
				bean.setFirstName(resultSet.getString("First_Name"));
				bean.setSurname(resultSet.getString("Surname"));
				bean.setEmail(resultSet.getString("Email"));
				bean.setDOB(resultSet.getString("DOB"));
				bean.setAddress(resultSet.getString("Address"));
				bean.setPassword(resultSet.getString("Password"));
				bean.setEnrolled(Boolean.parseBoolean(resultSet.getString("Is_Enrolled")));
				bean.setTeacher(Boolean.parseBoolean(resultSet.getString("Is_Teacher")));

				//Add bean to list of students
				allStudents.add(bean);
			}
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();
	}

	public StudentBean retrieveSingleStudent(String studentEmail)
	{
		LOG.debug("Attempting Retrieval of single student: " + studentEmail);
		StudentBean beanToReturn = null;
		List<StudentBean> studentBeans = retrieveAllStudents();
		for (StudentBean studentBean : studentBeans)
		{
			if(studentBean.getEmail().equalsIgnoreCase(studentEmail))
			{
				beanToReturn = studentBean;
			}
		}
		return beanToReturn;
	}

	public boolean attemptLogin(StudentBean studentBean)
	{
		LOG.debug("Attempting Student Login Oracle");
		//Find the associated email in DB and check login credentials are correct
		boolean isCorrectCredentials = false;
		String email = studentBean.getEmail();
		String password = studentBean.getPassword();
		StudentBean potentialStudent = retrieveSingleStudent(email);
		String hashedPassword = PasswordEncryptDecrypt.encryptPasswordToStore(password);
		if(potentialStudent != null && hashedPassword.equals(potentialStudent.getPassword()))
		{
			isCorrectCredentials = true;
		}
		return isCorrectCredentials;
	}

	public void updateStudentDetails(Document studentToUpdate, String email)
	{
		LOG.debug("attempting to update student details");
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if (oracleClient != null)
			{
				String query = "UPDATE " + STUDENTS_COLLECTION + " SET First_Name ='" + studentToUpdate.getString("First_Name")
						+"', Surname='"+ studentToUpdate.getString("Surname")
						+"', Email='"+ studentToUpdate.getString("Email")
						+"', DOB='" + studentToUpdate.getString("DOB")
						+"', Address='"+ studentToUpdate.getString("Address")
						+"', Password='"+ studentToUpdate.getString("Password")
						+"', Is_Enrolled='"+ studentToUpdate.getString("Is_Enrolled")
						+"', Is_Teacher='"+ studentToUpdate.getString("Is_Teacher")
						+"' WHERE EMAIL='"+ email +"'";

				executeStudentUpdateQuery(oracleClient, query);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to change student profile information");
		}
	}

	public void enrollStudent(String studentEmail)
	{
		LOG.debug("Attempting to change student enrollment");
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if (oracleClient != null)
			{
				String query = "UPDATE " + STUDENTS_COLLECTION + " SET IS_ENROLLED='true' WHERE EMAIL='" + studentEmail + "'";
				executeStudentUpdateQuery(oracleClient, query);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to change student Enrollment information");
		}
	}

	public void deleteStudent(String studentEmail)
	{
		LOG.debug("Attempting to delete student");
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if (oracleClient != null)
			{
				String query = "DELETE FROM " + STUDENTS_COLLECTION
						+ " WHERE Email='" + studentEmail + "';";
				executeStudentUpdateQuery(oracleClient, query);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to delete student in oracle DB");
		}
	}
}
