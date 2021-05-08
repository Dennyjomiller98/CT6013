package etl;

import beans.operational.AssignmentsBean;
import java.util.ArrayList;
import java.util.List;

public class TransformHelper
{
	public TransformHelper()
	{
		//Empty Constructor
	}

	public List<AssignmentsBean> transformAssignmentsData(List<AssignmentsBean> beans)
	{
		List<AssignmentsBean> sanitized = new ArrayList<>();
		//TODO - Sanitise the data, remove nulls, timestamp changes etc (If bean fails, we still need to add data in or we lose data)
		return sanitized;
	}
}
