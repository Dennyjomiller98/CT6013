package etl;

import beans.operational.AssignmentsBean;
import java.util.List;

public class LoadHelper
{
	public LoadHelper()
	{
		//Empty Constructor
	}

	public boolean loadData(List<AssignmentsBean> assignmentsBeanList)
	{
		//Return if DW Load/Re-Load of Data was complete
		boolean loadSuccess = false;
		//TODO - Get ready to reload or overwrite DW data with new information (Bulk clear and recreate is simplest but costly?)

		return loadSuccess;
	}
}
