package etl;

import beans.dw.DWLoadBean;
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
			//Remove results data and delete indexes
			conn.removeAllIndexes();
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

	/*Index the newly populated table, to speed up SELECT/WHERE SQL queries within the DW*/
	public boolean indexDW()
	{
		boolean indexSuccess = false;
		LoadConnections conn = new LoadConnections();
		boolean students = conn.indexDimStudentsData();
		boolean tutors = conn.indexDimTutorsData();
		boolean modules = conn.indexDimModulesData();
		boolean courses = conn.indexDimCoursesData();
		boolean subjects = conn.indexDimSubjectData();
		boolean enrollments = conn.indexDimEnrollmentData();
		boolean results = conn.indexResultsData();
		if(students && tutors && modules && courses && subjects && enrollments && results)
		{
			indexSuccess = true;
		}
		else
		{
			LOG.error("An error has occurred whilst attempting to index the database");
		}
		return indexSuccess;
	}

	public boolean updateDW(DWLoadBean loadBean)
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

	public void prepareAssignmentData(List<AssignmentsBean> assignmentsBeanList, DWLoadBean loadBean)
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

	public void prepareCourseData(List<CoursesBean> coursesBeans, DWLoadBean loadBean)
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

	public void prepareEnrollmentData(List<EnrollmentsBean> enrollmentsBeans, DWLoadBean loadBean)
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

	public void prepareModuleData(List<ModulesBean> modulesBeans, DWLoadBean loadBean)
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

	public void prepareStudentData(List<StudentsBean> studentsBeans, DWLoadBean loadBean)
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

	public void prepareSubjectData(List<SubjectsBean> subjectsBeans, DWLoadBean loadBean)
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

	public void prepareTutorData(List<TutorsBean> tutorsBeans, DWLoadBean loadBean)
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

	public void prepareDimCourseData(List<DimCoursesBean> dimCoursesBeans, DWLoadBean loadBean)
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

	public void prepareDimEnrollmentData(List<DimEnrollmentsBean> dimEnrollmentsBeans, DWLoadBean loadBean)
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

	public void prepareDimModuleData(List<DimModulesBean> dimModulesBeans, DWLoadBean loadBean)
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

	public void prepareDimStudentData(List<DimStudentsBean> dimStudentsBeans, DWLoadBean loadBean)
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

	public void prepareDimSubjectData(List<DimSubjectsBean> dimSubjectsBeans, DWLoadBean loadBean)
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

	public void prepareDimTutorData(List<DimTutorsBean> dimTutorsBeans, DWLoadBean loadBean)
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