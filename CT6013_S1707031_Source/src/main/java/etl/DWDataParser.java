package etl;

import beans.dw.DWResultsBean;
import beans.operational.AssignmentsBean;

public class DWDataParser
{
	public DWDataParser()
	{
		//Empty Constructor
	}

	//TODO - set all information into results bean ready for storing into DW

	public void setAssignmentsData(DWResultsBean loadBean, AssignmentsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addAssignments(beanToLoad);
		}
	}
}
