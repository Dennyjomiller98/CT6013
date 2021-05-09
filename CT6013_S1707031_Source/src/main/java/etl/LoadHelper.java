package etl;

import beans.dw.DWResultsBean;
import beans.operational.AssignmentsBean;
import java.sql.SQLException;
import java.util.List;
import oracle.LoadConnections;
import org.apache.log4j.Logger;

public class LoadHelper
{
	static final Logger LOG = Logger.getLogger(LoadHelper.class);

	public LoadHelper()
	{
		//Empty Constructor
	}

	public boolean emptyDW()
	{
		boolean wasTruncated = false;
		try
		{
			LoadConnections conn = new LoadConnections();
			wasTruncated = conn.removeOldData();
		}
		catch (SQLException e)
		{
			LOG.error("Unable to remove old Data", e);
		}
		return wasTruncated;
	}

	public boolean updateDW(DWResultsBean loadBean)
	{
		boolean loadSuccess = false;
		LoadConnections conn = new LoadConnections();
		//TODO - all DB tables (preparing dimension tables for date, and ID tables for storing module/course/student/subject/tutor information)
		boolean students = conn.setStudentsData(loadBean);
		boolean tutors = conn.setTutorsData(loadBean);
		boolean modules = conn.setModulesData(loadBean);
		boolean courses = conn.setCoursesData(loadBean);
		boolean subjects = conn.setSubjectData(loadBean);
		if(students && tutors && modules && courses && subjects)
		{
			boolean wasSuccess = conn.setResultsData(loadBean);
			if(wasSuccess)
			{
				loadSuccess = true;
			}
		}
		else
		{
			LOG.error("Data was not prepared in the DW correctly, so could not create results Fact Table");
		}
		return loadSuccess;
	}

	public void prepareAssignmentData(List<AssignmentsBean> assignmentsBeanList, DWResultsBean loadBean)
	{
		//Return if DW Load/Re-Load of Data was complete
		if (!assignmentsBeanList.isEmpty())
		{
			for (AssignmentsBean bean : assignmentsBeanList)
			{
				try
				{
					DWDataParser helper = new DWDataParser();
					helper.setAssignmentsData(loadBean, bean);
				}
				catch(Exception e)
				{
					LOG.error("Error loading data into DW");
				}
			}
		}
	}
}