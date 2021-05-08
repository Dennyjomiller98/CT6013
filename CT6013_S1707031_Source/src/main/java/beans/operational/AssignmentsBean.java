package beans.operational;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class AssignmentsBean
{
	static final Logger LOG = Logger.getLogger(AssignmentsBean.class);

	private String fId;

	public AssignmentsBean()
	{
		//Empty Constructor
	}

	public AssignmentsBean(ResultSet resultSet)
	{
		try
		{
			fId = resultSet.getString("Assignment_Id");
		}
		catch (SQLException throwables)
		{
			LOG.error("Unable to create Bean from ResultSet", throwables);
		}
	}

	public void setAssignmentId(String id)
	{
		fId = id;
	}
	public String getAssignmentId()
	{
		return fId;
	}
}
