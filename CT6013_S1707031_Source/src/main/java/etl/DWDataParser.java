package etl;

import beans.dw.DWResultsBean;
import beans.operational.*;
import beans.operational.dimensions.*;

/**
 * @author Denny-Jo
 * DWDataParser is a helper Class used to load Data into the DWResultsBean, which is passed on to load data in to the DW
 * */
public class DWDataParser
{
	public DWDataParser()
	{
		//Empty Constructor
	}

	public void setAssignmentsData(DWResultsBean loadBean, AssignmentsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addAssignments(beanToLoad);
		}
	}

	public void setCoursesData(DWResultsBean loadBean, CoursesBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addCourses(beanToLoad);
		}
	}

	public void setEnrollmentsData(DWResultsBean loadBean, EnrollmentsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addEnrollments(beanToLoad);
		}
	}

	public void setModulesData(DWResultsBean loadBean, ModulesBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addModules(beanToLoad);
		}
	}

	public void setStudentsData(DWResultsBean loadBean, StudentsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addStudents(beanToLoad);
		}
	}

	public void setSubjectsData(DWResultsBean loadBean, SubjectsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addSubjects(beanToLoad);
		}
	}

	public void setTutorsData(DWResultsBean loadBean, TutorsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addTutors(beanToLoad);
		}
	}

	public void setDimCoursesData(DWResultsBean loadBean, DimCoursesBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addDimCourses(beanToLoad);
		}
	}

	public void setDimEnrollmentsData(DWResultsBean loadBean, DimEnrollmentsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addDimEnrollments(beanToLoad);
		}
	}

	public void setDimModulesData(DWResultsBean loadBean, DimModulesBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addDimModules(beanToLoad);
		}
	}

	public void setDimStudentsData(DWResultsBean loadBean, DimStudentsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addDimStudents(beanToLoad);
		}
	}

	public void setDimSubjectsData(DWResultsBean loadBean, DimSubjectsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addDimSubjects(beanToLoad);
		}
	}

	public void setDimTutorsData(DWResultsBean loadBean, DimTutorsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addDimTutors(beanToLoad);
		}
	}
}
