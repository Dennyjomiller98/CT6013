package oracle;

import beans.operational.AssignmentsBean;
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
