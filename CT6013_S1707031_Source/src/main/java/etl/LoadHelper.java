package etl;

import beans.dw.DWResultsBean;
import beans.operational.*;
import beans.operational.dimensions.*;
import java.sql.SQLException;
import java.util.List;
import oracle.LoadConnections;
import org.apache.log4j.Logger;

/**
 * @author Denny-Jo
 * LoadHelper - Helper Class used during ETL Load process
 * LoadHelper Handles the DW Connections, emptying previous data and preparing the DWResultsBean for a new Load Process into the DW
* */
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
			wasTruncated = conn.removeResultsData();
		}
		catch (SQLException e)
		{
			LOG.error("Unable to remove old Data", e);
		}
		return wasTruncated;
	}

	public boolean emptyDimCourse()
	{
		boolean wasTruncated = false;
		try
		{
			LoadConnections conn = new LoadConnections();
			wasTruncated = conn.removeDimCourseData();
		}
		catch (SQLException e)
		{
			LOG.error("Unable to remove old Data", e);
		}
		return wasTruncated;
	}

	public boolean emptyDimModule()
	{
		boolean wasTruncated = false;
		try
		{
			LoadConnections conn = new LoadConnections();
			wasTruncated = conn.removeDimModuleData();
		}
		catch (SQLException e)
		{
			LOG.error("Unable to remove old Data", e);
		}
		return wasTruncated;
	}

	public boolean emptyDimEnrollment()
	{
		boolean wasTruncated = false;
		try
		{
			LoadConnections conn = new LoadConnections();
			wasTruncated = conn.removeDimEnrollmentData();
		}
		catch (SQLException e)
		{
			LOG.error("Unable to remove old Data", e);
		}
		return wasTruncated;
	}

	public boolean emptyDimSubject()
	{
		boolean wasTruncated = false;
		try
		{
			LoadConnections conn = new LoadConnections();
			wasTruncated = conn.removeDimSubjectData();
		}
		catch (SQLException e)
		{
			LOG.error("Unable to remove old Data", e);
		}
		return wasTruncated;
	}

	public boolean emptyDimStudent()
	{
		boolean wasTruncated = false;
		try
		{
			LoadConnections conn = new LoadConnections();
			wasTruncated = conn.removeDimStudentData();
		}
		catch (SQLException e)
		{
			LOG.error("Unable to remove old Data", e);
		}
		return wasTruncated;
	}

	public boolean emptyDimTutor()
	{
		boolean wasTruncated = false;
		try
		{
			LoadConnections conn = new LoadConnections();
			wasTruncated = conn.removeDimTutorData();
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
		boolean students = conn.setDimStudentsData(loadBean);
		boolean tutors = conn.setDimTutorsData(loadBean);
		boolean modules = conn.setDimModulesData(loadBean);
		boolean courses = conn.setDimCoursesData(loadBean);
		boolean subjects = conn.setDimSubjectData(loadBean);
		boolean enrollments = conn.setDimEnrollmentData(loadBean);
		if(students && tutors && modules && courses && subjects && enrollments)
		{
			//Set main fact table data using current data
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

	public void prepareCourseData(List<CoursesBean> coursesBeans, DWResultsBean loadBean)
	{
		if (!coursesBeans.isEmpty())
		{
			for (CoursesBean bean : coursesBeans)
			{
				try
				{
					DWDataParser helper = new DWDataParser();
					helper.setCoursesData(loadBean, bean);
				}
				catch(Exception e)
				{
					LOG.error("Error loading data into DW");
				}
			}
		}
	}

	public void prepareEnrollmentData(List<EnrollmentsBean> enrollmentsBeans, DWResultsBean loadBean)
	{
		if (!enrollmentsBeans.isEmpty())
		{
			for (EnrollmentsBean bean : enrollmentsBeans)
			{
				try
				{
					DWDataParser helper = new DWDataParser();
					helper.setEnrollmentsData(loadBean, bean);
				}
				catch(Exception e)
				{
					LOG.error("Error loading data into DW");
				}
			}
		}
	}

	public void prepareModuleData(List<ModulesBean> modulesBeans, DWResultsBean loadBean)
	{
		if (!modulesBeans.isEmpty())
		{
			for (ModulesBean bean : modulesBeans)
			{
				try
				{
					DWDataParser helper = new DWDataParser();
					helper.setModulesData(loadBean, bean);
				}
				catch(Exception e)
				{
					LOG.error("Error loading data into DW");
				}
			}
		}
	}

	public void prepareStudentData(List<StudentsBean> studentsBeans, DWResultsBean loadBean)
	{
		if (!studentsBeans.isEmpty())
		{
			for (StudentsBean bean : studentsBeans)
			{
				try
				{
					DWDataParser helper = new DWDataParser();
					helper.setStudentsData(loadBean, bean);
				}
				catch(Exception e)
				{
					LOG.error("Error loading data into DW");
				}
			}
		}
	}

	public void prepareSubjectData(List<SubjectsBean> subjectsBeans, DWResultsBean loadBean)
	{
		if (!subjectsBeans.isEmpty())
		{
			for (SubjectsBean bean : subjectsBeans)
			{
				try
				{
					DWDataParser helper = new DWDataParser();
					helper.setSubjectsData(loadBean, bean);
				}
				catch(Exception e)
				{
					LOG.error("Error loading data into DW");
				}
			}
		}
	}

	public void prepareTutorData(List<TutorsBean> tutorsBeans, DWResultsBean loadBean)
	{
		if (!tutorsBeans.isEmpty())
		{
			for (TutorsBean bean : tutorsBeans)
			{
				try
				{
					DWDataParser helper = new DWDataParser();
					helper.setTutorsData(loadBean, bean);
				}
				catch(Exception e)
				{
					LOG.error("Error loading data into DW");
				}
			}
		}
	}

	public void prepareDimCourseData(List<DimCoursesBean> dimCoursesBeans, DWResultsBean loadBean)
	{
		if (!dimCoursesBeans.isEmpty())
		{
			for (DimCoursesBean bean : dimCoursesBeans)
			{
				try
				{
					DWDataParser helper = new DWDataParser();
					helper.setDimCoursesData(loadBean, bean);
				}
				catch(Exception e)
				{
					LOG.error("Error loading data into DW");
				}
			}
		}
	}

	public void prepareDimEnrollmentData(List<DimEnrollmentsBean> dimEnrollmentsBeans, DWResultsBean loadBean)
	{
		if (!dimEnrollmentsBeans.isEmpty())
		{
			for (DimEnrollmentsBean bean : dimEnrollmentsBeans)
			{
				try
				{
					DWDataParser helper = new DWDataParser();
					helper.setDimEnrollmentsData(loadBean, bean);
				}
				catch(Exception e)
				{
					LOG.error("Error loading data into DW");
				}
			}
		}
	}

	public void prepareDimModuleData(List<DimModulesBean> dimModulesBeans, DWResultsBean loadBean)
	{
		if (!dimModulesBeans.isEmpty())
		{
			for (DimModulesBean bean : dimModulesBeans)
			{
				try
				{
					DWDataParser helper = new DWDataParser();
					helper.setDimModulesData(loadBean, bean);
				}
				catch(Exception e)
				{
					LOG.error("Error loading data into DW");
				}
			}
		}
	}

	public void prepareDimStudentData(List<DimStudentsBean> dimStudentsBeans, DWResultsBean loadBean)
	{
		if (!dimStudentsBeans.isEmpty())
		{
			for (DimStudentsBean bean : dimStudentsBeans)
			{
				try
				{
					DWDataParser helper = new DWDataParser();
					helper.setDimStudentsData(loadBean, bean);
				}
				catch(Exception e)
				{
					LOG.error("Error loading data into DW");
				}
			}
		}
	}

	public void prepareDimSubjectData(List<DimSubjectsBean> dimSubjectsBeans, DWResultsBean loadBean)
	{
		if (!dimSubjectsBeans.isEmpty())
		{
			for (DimSubjectsBean bean : dimSubjectsBeans)
			{
				try
				{
					DWDataParser helper = new DWDataParser();
					helper.setDimSubjectsData(loadBean, bean);
				}
				catch(Exception e)
				{
					LOG.error("Error loading data into DW");
				}
			}
		}
	}

	public void prepareDimTutorData(List<DimTutorsBean> dimTutorsBeans, DWResultsBean loadBean)
	{
		if (!dimTutorsBeans.isEmpty())
		{
			for (DimTutorsBean bean : dimTutorsBeans)
			{
				try
				{
					DWDataParser helper = new DWDataParser();
					helper.setDimTutorsData(loadBean, bean);
				}
				catch(Exception e)
				{
					LOG.error("Error loading data into DW");
				}
			}
		}
	}
}