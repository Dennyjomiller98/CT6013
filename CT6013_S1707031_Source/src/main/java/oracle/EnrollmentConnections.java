package oracle;

import beans.EnrollmentBean;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class EnrollmentConnections extends AbstractOracleConnections
{
	public EnrollmentConnections()
	{
		//Empty Constructor
	}

	public void addEnrollment(Document enrollment)
	{
		LOG.debug("Beginning Student Enrollment to oracle");
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if(oracleClient != null)
			{
				//Add Student Enrollment to DB
				String values = "'" +enrollment.getString("Student_Email")
						+ "','" + enrollment.getString("Course_Code")
						+ "','" + enrollment.getString("Module_Selections") + "'";
				String query = "INSERT INTO " + ENROLLMENTS_COLLECTION +
						"(Student_Email, Course_Code, Module_Selections)" + " VALUES (" + values + ")";

				//Execute query
				executeEnrollmentUpdateQuery(oracleClient, query);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to enroll student to Oracle", e);
		}
	}

	private void executeEnrollmentUpdateQuery(Connection oracleClient, String query) throws SQLException
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

	public List<EnrollmentBean> retrieveAllEnrollments()
	{
		ArrayList<EnrollmentBean> allEnrollments = new ArrayList<>();

		LOG.debug("Beginning all Enrollment retrieval");
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + ENROLLMENTS_COLLECTION;

				//Execute query
				allEnrollmentsRetrievalQuery(oracleClient, query, allEnrollments);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to retrieve all enrollments from Oracle", e);
		}
		return allEnrollments;
	}

	private void allEnrollmentsRetrievalQuery(Connection oracleClient, String query, ArrayList<EnrollmentBean> allEnrollments) throws SQLException
	{
		LOG.debug("Retrieving all enrollments in Oracle");
		try (Statement statement = oracleClient.createStatement())
		{
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
			{
				EnrollmentBean bean = new EnrollmentBean();
				bean.setStudentEmail(resultSet.getString("Student_Email"));
				bean.setCourseCode(resultSet.getString("Course_Code"));
				bean.setModuleSelections(resultSet.getString("Module_Selections"));

				//Add bean to list of enrollments
				allEnrollments.add(bean);
			}
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();
	}

	public EnrollmentBean retrieveSingleEnrollment(String studentEmail)
	{
		EnrollmentBean beanToReturn = null;
		List<EnrollmentBean> enrollmentBeans = retrieveAllEnrollments();
		for (EnrollmentBean enrollmentBean : enrollmentBeans)
		{
			if(enrollmentBean.getStudentEmail().equalsIgnoreCase(studentEmail))
			{
				beanToReturn = enrollmentBean;
			}
		}
		return beanToReturn;
	}
}
