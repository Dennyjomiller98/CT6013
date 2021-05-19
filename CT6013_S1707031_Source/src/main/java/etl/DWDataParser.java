package etl;

import beans.dw.DWLoadBean;
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

	public void setAssignmentsData(DWLoadBean loadBean, AssignmentsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addAssignments(beanToLoad);
		}
	}

	public void setCoursesData(DWLoadBean loadBean, CoursesBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addCourses(beanToLoad);
		}
	}

	public void setEnrollmentsData(DWLoadBean loadBean, EnrollmentsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addEnrollments(beanToLoad);
		}
	}

	public void setModulesData(DWLoadBean loadBean, ModulesBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addModules(beanToLoad);
		}
	}

	public void setStudentsData(DWLoadBean loadBean, StudentsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addStudents(beanToLoad);
		}
	}

	public void setSubjectsData(DWLoadBean loadBean, SubjectsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addSubjects(beanToLoad);
		}
	}

	public void setTutorsData(DWLoadBean loadBean, TutorsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addTutors(beanToLoad);
		}
	}

	public void setDimCoursesData(DWLoadBean loadBean, DimCoursesBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addDimCourses(beanToLoad);
		}
	}

	public void setDimEnrollmentsData(DWLoadBean loadBean, DimEnrollmentsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addDimEnrollments(beanToLoad);
		}
	}

	public void setDimModulesData(DWLoadBean loadBean, DimModulesBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addDimModules(beanToLoad);
		}
	}

	public void setDimStudentsData(DWLoadBean loadBean, DimStudentsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addDimStudents(beanToLoad);
		}
	}

	public void setDimSubjectsData(DWLoadBean loadBean, DimSubjectsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addDimSubjects(beanToLoad);
		}
	}

	public void setDimTutorsData(DWLoadBean loadBean, DimTutorsBean beanToLoad)
	{
		if(beanToLoad != null)
		{
			loadBean.addDimTutors(beanToLoad);
		}
	}
}
