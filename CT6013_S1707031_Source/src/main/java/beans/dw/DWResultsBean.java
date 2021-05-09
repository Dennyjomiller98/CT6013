package beans.dw;

import beans.operational.AssignmentsBean;
import java.util.ArrayList;
import java.util.List;

public class DWResultsBean
{
	private List<AssignmentsBean> fAssignments;
	public DWResultsBean()
	{
		//Empty Constructor
	}

	public void setAssignments(List<AssignmentsBean> beans)
	{
		fAssignments = beans;
	}
	public List<AssignmentsBean> getAssignments()
	{
		return fAssignments;
	}
	public void addAssignments(AssignmentsBean bean)
	{
		if (fAssignments == null || fAssignments.isEmpty())
		{
			fAssignments = new ArrayList<>();
		}
		fAssignments.add(bean);
	}
}
