package oracle;

import beans.MarkBean;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class MarkConnections extends AbstractOracleConnections
{
	public MarkConnections()
	{
		//Empty constructor
	}

	public void addMarks(Document mark)
	{
		LOG.debug("Beginning Mark Addition to Oracle DB");
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if(oracleClient != null)
			{
				//Add Student to DB
				String values = "'" +mark.getString("Module_Code")
						+ "','" + mark.getString("Student_Email")
						+ "','" + mark.getString("Marker_Email")
						+ "','" + mark.getString("Final_Mark") + "'";
				String query = "INSERT INTO " + MARKS_COLLECTION +
						"(First_Name, Student_Email, Marker_Email, Final_Mark)" + " VALUES (" + values + ")";

				//Execute query
				executeMarkUpdateQuery(oracleClient, query);
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

	private void executeMarkUpdateQuery(Connection oracleClient, String query) throws SQLException
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

	public List<MarkBean> retrieveAllMarks()
	{
		ArrayList<MarkBean> allMarks = new ArrayList<>();

		LOG.debug("Beginning all Mark retrieval");
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + MARKS_COLLECTION;

				//Execute query
				allMarksRetrievalQuery(oracleClient, query, allMarks);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to retrieve all marks from Oracle", e);
		}
		return allMarks;
	}

	private void allMarksRetrievalQuery(Connection oracleClient, String query, ArrayList<MarkBean> allMarks) throws SQLException
	{
		try (Statement statement = oracleClient.createStatement())
		{
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
			{
				MarkBean bean = new MarkBean();
				bean.setModuleCode(resultSet.getString("Module_Code"));
				bean.setStudentEmail(resultSet.getString("Student_Email"));
				bean.setMarkerEmail(resultSet.getString("Marker_Email"));
				bean.setFinalMark(Integer.parseInt(resultSet.getString("Final_Mark")));

				//Add bean to list of students
				allMarks.add(bean);
			}
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();
	}

	public MarkBean retrieveMarksForStudentInModule(String studentEmail, String moduleCode)
	{
		MarkBean beanToReturn = null;
		List<MarkBean> markBeans = retrieveAllMarks();
		for (MarkBean markBean : markBeans)
		{
			if(markBean.getStudentEmail().equalsIgnoreCase(studentEmail) && markBean.getModuleCode().equalsIgnoreCase(moduleCode))
			{
				beanToReturn = markBean;
			}
		}
		return beanToReturn;
	}

	public List<MarkBean> retrieveAllMarksForStudent(String studentEmail)
	{
		List<MarkBean> beansToReturn = new ArrayList<>();
		List<MarkBean> markBeans = retrieveAllMarks();
		for (MarkBean markBean : markBeans)
		{
			if(markBean.getStudentEmail().equalsIgnoreCase(studentEmail))
			{
				beansToReturn.add(markBean);
			}
		}
		return beansToReturn;
	}
}
