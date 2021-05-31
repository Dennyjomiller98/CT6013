package managers.dataview;

import beans.dw.DWLoadBean;
import beans.operational.AssignmentsBean;
import beans.operational.dimensions.DimCoursesBean;
import beans.operational.dimensions.DimEnrollmentsBean;
import beans.operational.dimensions.DimStudentsBean;
import java.util.List;
import oracle.DataViewConnections;

/**
 * @author Denny-Jo
 * ETLQueryManager - Manager Class used to Perform Decision Makers Queries once selected from the view.vm form.
 *
 * @implNote DecisionMakerDataManager has already authorized/validated the data provided.
 * ETLQueryManager simply creates connections/runs pre-made SQL Queries to the DW
 * */
public class ETLQueryManager implements IQueryManager
{
	public ETLQueryManager()
	{
		//Empty Constructor
	}

	@Override
	public DWLoadBean getTotalStudentsAllCoursesAllYears()
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<DimEnrollmentsBean> allEnrollmentBeans = connections.getDWEnrollments(null, null);
		List<DimStudentsBean> allStudentsBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(allStudentsBeans != null && !allStudentsBeans.isEmpty() && allEnrollmentBeans != null && !allEnrollmentBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalStudents(allStudentsBeans, allEnrollmentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalStudentsAllCoursesSelectedYear(String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<DimEnrollmentsBean> allEnrollmentBeans = connections.getDWEnrollments(yearSelect, null);
		List<DimStudentsBean> allStudentsBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(allStudentsBeans != null && !allStudentsBeans.isEmpty() && allEnrollmentBeans != null && !allEnrollmentBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalStudents(allStudentsBeans, allEnrollmentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalStudentsSelectedCourseAllYears(String courseSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<DimEnrollmentsBean> allEnrollmentBeans = connections.getDWEnrollments(null, courseSelect);
		List<DimStudentsBean> allStudentsBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(allStudentsBeans != null && !allStudentsBeans.isEmpty() && allEnrollmentBeans != null && !allEnrollmentBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalStudents(allStudentsBeans, allEnrollmentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalStudentsSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<DimEnrollmentsBean> allEnrollmentBeans = connections.getDWEnrollments(yearSelect, courseSelect);
		List<DimStudentsBean> allStudentsBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(allStudentsBeans != null && !allStudentsBeans.isEmpty() && allEnrollmentBeans != null && !allEnrollmentBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalStudents(allStudentsBeans, allEnrollmentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalDropoutsAllCoursesAllYears()
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<DimEnrollmentsBean> allEnrollmentBeans = connections.getDWEnrollments(null, null);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(allEnrollmentBeans != null && !allEnrollmentBeans.isEmpty() && allStudentBeans != null && !allStudentBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalDropouts(allStudentBeans, allEnrollmentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalDropoutsAllCoursesSelectedYear(String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<DimEnrollmentsBean> allEnrollmentBeans = connections.getDWEnrollments(yearSelect, null);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(allEnrollmentBeans != null && !allEnrollmentBeans.isEmpty() && allStudentBeans != null && !allStudentBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalDropouts(allStudentBeans, allEnrollmentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalDropoutsSelectedCourseAllYears(String courseSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<DimEnrollmentsBean> allEnrollmentBeans = connections.getDWEnrollments(null, courseSelect);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(allEnrollmentBeans != null && !allEnrollmentBeans.isEmpty() && allStudentBeans != null && !allStudentBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalDropouts(allStudentBeans, allEnrollmentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalDropoutsSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<DimEnrollmentsBean> allEnrollmentBeans = connections.getDWEnrollments(yearSelect, courseSelect);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(allEnrollmentBeans != null && !allEnrollmentBeans.isEmpty() && allStudentBeans != null && !allStudentBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalDropouts(allStudentBeans, allEnrollmentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getAverageGradeAllCoursesAllYears()
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResults(null, null);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(dwResults != null && !dwResults.isEmpty() && allStudentBeans != null && !allStudentBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertAverageGrade(dwResults, allStudentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getAverageGradeAllCoursesSelectedYear(String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResults(yearSelect, null);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(dwResults != null && !dwResults.isEmpty() && allStudentBeans != null && !allStudentBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertAverageGrade(dwResults, allStudentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getAverageGradeSelectedCourseAllYears(String courseSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResults(null, courseSelect);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(dwResults != null && !dwResults.isEmpty() && allStudentBeans != null && !allStudentBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertAverageGrade(dwResults, allStudentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getAverageGradeSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResults(yearSelect, courseSelect);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(dwResults != null && !dwResults.isEmpty() && allStudentBeans != null && !allStudentBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertAverageGrade(dwResults, allStudentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getPassRateAllTutorsAllCoursesAllYears()
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getPassRateAllTutorsAllCoursesSelectedYear(String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getPassRateAllTutorsSelectedCourseAllYears(String courseSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getPassRateAllTutorsSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getPassRateSelectedTutorAllCoursesAllYears(String tutorSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getPassRateSelectedTutorAllCoursesSelectedYear(String tutorSelect, String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getPassRateSelectedTutorSelectedCourseAllYears(String tutorSelect, String courseSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getPassRateSelectedTutorSelectedCourseSelectedYear(String tutorSelect, String courseSelect, String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getTotalResitsAllCoursesAllYears()
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getTotalResitsAllCoursesSelectedYear(String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getTotalResitsSelectedCourseAllYears(String courseSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getTotalResitsSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getTotalEnrollmentsAllYears()
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getTotalEnrollmentsSelectedYear(String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getCovidFiguresAgainstAllYears()
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getCovidFiguresAgainstSelectedYear(String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getTotalInternationalStudentsAllCoursesAllYears()
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getTotalInternationalStudentsAllCoursesSelectedYear(String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getTotalInternationalStudentsSelectedCourseAllYears(String courseSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getTotalInternationalStudentsSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getTotalCourseChangesAllCoursesAllYears()
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getTotalCourseChangesAllCoursesSelectedYear(String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getTotalCourseChangesSelectedCourseAllYears(String courseSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getTotalCourseChangesSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getCovidInternationalStudentEnrollmentsAgainstAllYears()
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}

	@Override
	public DWLoadBean getCovidInternationalStudentAgainstSelectedYear(String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		return ret;
	}
}
