package etl;

import beans.operational.AssignmentsBean;
import java.util.ArrayList;
import java.util.List;
import oracle.ExtractConnections;

public class ExtractHelper
{
	public ExtractHelper()
	{
		//Empty Constructor
	}

	public List<AssignmentsBean> retrieveAssignmentsTable()
	{
		List<AssignmentsBean> allAssignments = new ArrayList<>();
		ExtractConnections conn = new ExtractConnections();
		List<AssignmentsBean> assignmentsBeans = conn.retrieveAssignmentsTable();
		if(assignmentsBeans != null && !assignmentsBeans.isEmpty())
		{
			allAssignments.addAll(assignmentsBeans);
		}
		return allAssignments;
	}
}
