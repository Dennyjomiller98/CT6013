package oracle;

import beans.dw.DWResultsBean;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class LoadConnections extends AbstractOracleConnections
{
	public LoadConnections()
	{
		//Empty Constructor
	}

	public boolean removeOldData() throws SQLException
	{
		boolean deleteSuccessful = false;
		AbstractOracleConnections conn = new AbstractOracleConnections();
		Connection oracleClient = conn.getDWClient();
		if(oracleClient != null)
		{
			String query = "TRUNCATE TABLE " + TBL_DW_RESULTS
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
		//TODO - add bean to DW
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

	public boolean setStudentsData(DWResultsBean loadBean)
	{
		//TODO - load this info into DW
		return true;
	}

	public boolean setTutorsData(DWResultsBean loadBean)
	{
		//TODO - load this info into DW
		return true;
	}

	public boolean setModulesData(DWResultsBean loadBean)
	{
		//TODO - load this info into DW
		return true;
	}

	public boolean setCoursesData(DWResultsBean loadBean)
	{
		//TODO - load this info into DW
		return true;
	}

	public boolean setSubjectData(DWResultsBean loadBean)
	{
		//TODO - load this info into DW
		return true;
	}
}
