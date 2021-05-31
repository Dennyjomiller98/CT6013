package managers.dataview;

import beans.dw.DWEnrollmentsBean;
import beans.dw.DWLoadBean;
import beans.operational.AssignmentsBean;
import beans.operational.dimensions.DimCoursesBean;
import beans.operational.dimensions.DimEnrollmentsBean;
import beans.operational.dimensions.DimStudentsBean;
import beans.operational.dimensions.DimTutorsBean;
import java.util.ArrayList;
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
		List<AssignmentsBean> dwResults = connections.getDWResults(null, null, null);
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
		List<AssignmentsBean> dwResults = connections.getDWResults(yearSelect, null, null);
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
		List<AssignmentsBean> dwResults = connections.getDWResults(null, courseSelect, null);
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
		List<AssignmentsBean> dwResults = connections.getDWResults(yearSelect, courseSelect, null);
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
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResults(null, null, null);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimTutorsBean> allTutorsBeans = connections.getDWTutors();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(dwResults != null && !dwResults.isEmpty() && allStudentBeans != null &&
				!allStudentBeans.isEmpty() && allTutorsBeans != null && !allTutorsBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertGradeAgainstTutor(dwResults, allStudentBeans, allCourses, allTutorsBeans);
		}
		return ret;
	}

	@Override
	public DWLoadBean getPassRateAllTutorsAllCoursesSelectedYear(String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResults(yearSelect, null, null);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimTutorsBean> allTutorsBeans = connections.getDWTutors();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(dwResults != null && !dwResults.isEmpty() && allStudentBeans != null &&
				!allStudentBeans.isEmpty() && allTutorsBeans != null && !allTutorsBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertGradeAgainstTutor(dwResults, allStudentBeans, allCourses, allTutorsBeans);
		}
		return ret;
	}

	@Override
	public DWLoadBean getPassRateAllTutorsSelectedCourseAllYears(String courseSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResults(null, courseSelect, null);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimTutorsBean> allTutorsBeans = connections.getDWTutors();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(dwResults != null && !dwResults.isEmpty() && allStudentBeans != null &&
				!allStudentBeans.isEmpty() && allTutorsBeans != null && !allTutorsBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertGradeAgainstTutor(dwResults, allStudentBeans, allCourses, allTutorsBeans);
		}
		return ret;
	}

	@Override
	public DWLoadBean getPassRateAllTutorsSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResults(null, courseSelect, yearSelect);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimTutorsBean> allTutorsBeans = connections.getDWTutors();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(dwResults != null && !dwResults.isEmpty() && allStudentBeans != null &&
				!allStudentBeans.isEmpty() && allTutorsBeans != null && !allTutorsBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertGradeAgainstTutor(dwResults, allStudentBeans, allCourses, allTutorsBeans);
		}
		return ret;
	}

	@Override
	public DWLoadBean getPassRateSelectedTutorAllCoursesAllYears(String tutorSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResults(null, null, tutorSelect);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimTutorsBean> allTutorsBeans = connections.getDWTutors();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(dwResults != null && !dwResults.isEmpty() && allStudentBeans != null &&
				!allStudentBeans.isEmpty() && allTutorsBeans != null && !allTutorsBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertGradeAgainstTutor(dwResults, allStudentBeans, allCourses, allTutorsBeans);
		}
		return ret;
	}

	@Override
	public DWLoadBean getPassRateSelectedTutorAllCoursesSelectedYear(String tutorSelect, String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResults(yearSelect, null, tutorSelect);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimTutorsBean> allTutorsBeans = connections.getDWTutors();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(dwResults != null && !dwResults.isEmpty() && allStudentBeans != null &&
				!allStudentBeans.isEmpty() && allTutorsBeans != null && !allTutorsBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertGradeAgainstTutor(dwResults, allStudentBeans, allCourses, allTutorsBeans);
		}
		return ret;
	}

	@Override
	public DWLoadBean getPassRateSelectedTutorSelectedCourseAllYears(String tutorSelect, String courseSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResults(null, courseSelect, tutorSelect);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimTutorsBean> allTutorsBeans = connections.getDWTutors();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(dwResults != null && !dwResults.isEmpty() && allStudentBeans != null &&
				!allStudentBeans.isEmpty() && allTutorsBeans != null && !allTutorsBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertGradeAgainstTutor(dwResults, allStudentBeans, allCourses, allTutorsBeans);
		}
		return ret;
	}

	@Override
	public DWLoadBean getPassRateSelectedTutorSelectedCourseSelectedYear(String tutorSelect, String courseSelect, String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResults(yearSelect, courseSelect, tutorSelect);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimTutorsBean> allTutorsBeans = connections.getDWTutors();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(dwResults != null && !dwResults.isEmpty() && allStudentBeans != null &&
				!allStudentBeans.isEmpty() && allTutorsBeans != null && !allTutorsBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertGradeAgainstTutor(dwResults, allStudentBeans, allCourses, allTutorsBeans);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalResitsAllCoursesAllYears()
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResitResults(null, null);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(dwResults != null && !dwResults.isEmpty() && allStudentBeans != null &&
				!allStudentBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalResits(dwResults, allStudentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalResitsAllCoursesSelectedYear(String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResitResults(yearSelect, null);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(dwResults != null && !dwResults.isEmpty() && allStudentBeans != null &&
				!allStudentBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalResits(dwResults, allStudentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalResitsSelectedCourseAllYears(String courseSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResitResults(null, courseSelect);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(dwResults != null && !dwResults.isEmpty() && allStudentBeans != null &&
				!allStudentBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalResits(dwResults, allStudentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalResitsSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResitResults(yearSelect, courseSelect);
		List<DimStudentsBean> allStudentBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(dwResults != null && !dwResults.isEmpty() && allStudentBeans != null &&
				!allStudentBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalResits(dwResults, allStudentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalEnrollmentsAllYears()
	{
		return getEnrollmentFigures(null);
	}

	@Override
	public DWLoadBean getTotalEnrollmentsSelectedYear(String yearSelect)
	{
		return getEnrollmentFigures(yearSelect);

	}

	@Override
	public DWLoadBean getCovidFiguresAgainstAllYears()
	{
		return getEnrollmentFigures(null);
	}

	@Override
	public DWLoadBean getCovidFiguresAgainstSelectedYear(String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DWLoadBean enrollmentFigures = getEnrollmentFigures(null);
		List<DWEnrollmentsBean> dwEnrollments = enrollmentFigures.getDWEnrollments();
		if(dwEnrollments != null && !dwEnrollments.isEmpty())
		{
			for (DWEnrollmentsBean enrollment : dwEnrollments)
			{
				//Add enrollment if selected year, or if 2020/2021 for covid figures
				if(enrollment.getEnrollmentDate().startsWith(yearSelect)
						|| enrollment.getEnrollmentDate().startsWith("2020")
						|| enrollment.getEnrollmentDate().startsWith("2021"))
				{
					ret.addDWEnrollments(enrollment);
				}
			}
		}
		return ret;
	}

	private DWLoadBean getEnrollmentFigures(String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<DimEnrollmentsBean> allEnrollmentBeans;
		if(yearSelect != null)
		{
			allEnrollmentBeans = connections.getDWEnrollmentsSpecifiedYear(yearSelect);
		}
		else
		{
			allEnrollmentBeans = connections.getDWEnrollmentsSpecifiedYear(null);
		}
		List<DimStudentsBean> allStudentsBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(allStudentsBeans != null && !allStudentsBeans.isEmpty() && allEnrollmentBeans != null && !allEnrollmentBeans.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalEnrollments(allStudentsBeans, allEnrollmentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalInternationalStudentsAllCoursesAllYears()
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<DimEnrollmentsBean> allEnrollmentBeans = connections.getDWEnrollments(null, null);
		List<DimStudentsBean> allStudentsBeans = connections.getDWStudents();
		List<AssignmentsBean> dwResults = connections.getDWResultsInternational(null, null);
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(allStudentsBeans != null && !allStudentsBeans.isEmpty() && allEnrollmentBeans != null
				&& !allEnrollmentBeans.isEmpty() && dwResults != null && !dwResults.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalInternationalStudents(dwResults, allStudentsBeans, allEnrollmentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalInternationalStudentsAllCoursesSelectedYear(String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<DimEnrollmentsBean> allEnrollmentBeans = connections.getDWEnrollments(yearSelect, null);
		List<DimStudentsBean> allStudentsBeans = connections.getDWStudents();
		List<AssignmentsBean> dwResults = connections.getDWResultsInternational(yearSelect, null);
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(allStudentsBeans != null && !allStudentsBeans.isEmpty() && allEnrollmentBeans != null
				&& !allEnrollmentBeans.isEmpty() && dwResults != null && !dwResults.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalInternationalStudents(dwResults, allStudentsBeans, allEnrollmentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalInternationalStudentsSelectedCourseAllYears(String courseSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<DimEnrollmentsBean> allEnrollmentBeans = connections.getDWEnrollments(null, courseSelect);
		List<DimStudentsBean> allStudentsBeans = connections.getDWStudents();
		List<AssignmentsBean> dwResults = connections.getDWResultsInternational(null, courseSelect);
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(allStudentsBeans != null && !allStudentsBeans.isEmpty() && allEnrollmentBeans != null
				&& !allEnrollmentBeans.isEmpty() && dwResults != null && !dwResults.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalInternationalStudents(dwResults, allStudentsBeans, allEnrollmentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalInternationalStudentsSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<DimEnrollmentsBean> allEnrollmentBeans = connections.getDWEnrollments(yearSelect, courseSelect);
		List<DimStudentsBean> allStudentsBeans = connections.getDWStudents();
		List<AssignmentsBean> dwResults = connections.getDWResultsInternational(yearSelect, courseSelect);
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(allStudentsBeans != null && !allStudentsBeans.isEmpty() && allEnrollmentBeans != null
				&& !allEnrollmentBeans.isEmpty() && dwResults != null && !dwResults.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalInternationalStudents(dwResults, allStudentsBeans, allEnrollmentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalCourseChangesAllCoursesAllYears()
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<DimEnrollmentsBean> allEnrollmentBeans = connections.getDWEnrollmentsCourseChanges(null, null);
		List<DimStudentsBean> allStudentsBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(allStudentsBeans != null && !allStudentsBeans.isEmpty() && allEnrollmentBeans != null
				&& !allEnrollmentBeans.isEmpty() && allCourses != null && !allCourses.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalCourseChanges(allStudentsBeans, allEnrollmentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalCourseChangesAllCoursesSelectedYear(String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<DimEnrollmentsBean> allEnrollmentBeans = connections.getDWEnrollmentsCourseChanges(yearSelect, null);
		List<DimStudentsBean> allStudentsBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(allStudentsBeans != null && !allStudentsBeans.isEmpty() && allEnrollmentBeans != null
				&& !allEnrollmentBeans.isEmpty() && allCourses != null && !allCourses.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalCourseChanges(allStudentsBeans, allEnrollmentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalCourseChangesSelectedCourseAllYears(String courseSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<DimEnrollmentsBean> allEnrollmentBeans = connections.getDWEnrollmentsCourseChanges(null, courseSelect);
		allEnrollmentBeans = filterCourseSelect(allEnrollmentBeans, courseSelect);
		List<DimStudentsBean> allStudentsBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(allStudentsBeans != null && !allStudentsBeans.isEmpty() && !allEnrollmentBeans.isEmpty() && allCourses != null && !allCourses.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalCourseChanges(allStudentsBeans, allEnrollmentBeans, allCourses);
		}
		return ret;
	}

	@Override
	public DWLoadBean getTotalCourseChangesSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<DimEnrollmentsBean> allEnrollmentBeans = connections.getDWEnrollmentsCourseChanges(yearSelect, courseSelect);
		allEnrollmentBeans = filterCourseSelect(allEnrollmentBeans, courseSelect);
		List<DimStudentsBean> allStudentsBeans = connections.getDWStudents();
		List<DimCoursesBean> allCourses = connections.getDWCourses();
		if(allStudentsBeans != null && !allStudentsBeans.isEmpty() && !allEnrollmentBeans.isEmpty() && allCourses != null && !allCourses.isEmpty())
		{
			BeanManager beanManager = new BeanManager();
			ret = beanManager.convertTotalCourseChanges(allStudentsBeans, allEnrollmentBeans, allCourses);
		}
		return ret;
	}

	/*This should handle both Changing from and Changing to Selected Course*/
	private List<DimEnrollmentsBean> filterCourseSelect(List<DimEnrollmentsBean> allEnrollmentBeans, String courseSelect)
	{
		List<DimEnrollmentsBean> ret = new ArrayList<>();
		List<String> notCurrentIds = new ArrayList<>();
		for (DimEnrollmentsBean allEnrollmentBean : allEnrollmentBeans)
		{
			//First get IDs of all Changed fields
			if(allEnrollmentBean.getIsCurrent().equals("false"))
			{
				notCurrentIds.add(allEnrollmentBean.getEnrollmentId());
			}
		}

		if(!notCurrentIds.isEmpty())
		{
			//check if changes contain course in question
			for (String id : notCurrentIds)
			{
				boolean shouldKeep = false;
				for (DimEnrollmentsBean enrollBean : allEnrollmentBeans)
				{
					if(enrollBean.getCourseId().equals(courseSelect))
					{
						shouldKeep = true;
						break;
					}
				}
				if (shouldKeep)
				{
					for (DimEnrollmentsBean allEnrollmentBean : allEnrollmentBeans)
					{
						if(allEnrollmentBean.getCourseId().equals(id))
						{
							ret.add(allEnrollmentBean);
						}
					}
				}
			}
		}
		return ret;
	}

	@Override
	public DWLoadBean getCovidInternationalStudentEnrollmentsAgainstAllYears()
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResultsInternational(null, null);
		DWLoadBean enrollmentFigures = getEnrollmentFigures(null);
		List<DWEnrollmentsBean> dwEnrollments = enrollmentFigures.getDWEnrollments();
		if(dwEnrollments != null && !dwEnrollments.isEmpty() && dwResults != null && !dwResults.isEmpty())
		{
			for (DWEnrollmentsBean enrollment : dwEnrollments)
			{
				AssignmentsBean match = null;
				for (AssignmentsBean dwResult : dwResults)
				{
					if(enrollment.getStudentId().equals(dwResult.getStudentId()) && dwResult.getInternational().equals("true"))
					{
						match = dwResult;
						break;
					}
				}
				if(match != null)
				{
					enrollment.setInternational("true");
					ret.addDWEnrollments(enrollment);
				}
			}
		}
		return ret;
	}

	@Override
	public DWLoadBean getCovidInternationalStudentAgainstSelectedYear(String yearSelect)
	{
		DWLoadBean ret= new DWLoadBean();
		DataViewConnections connections = new DataViewConnections();
		List<AssignmentsBean> dwResults = connections.getDWResultsInternational(null, null);
		DWLoadBean enrollmentFigures = getEnrollmentFigures(null);
		List<DWEnrollmentsBean> dwEnrollments = enrollmentFigures.getDWEnrollments();
		if(dwEnrollments != null && !dwEnrollments.isEmpty() && dwResults != null && !dwResults.isEmpty())
		{
			for (DWEnrollmentsBean enrollment : dwEnrollments)
			{
				getCovidInternationalStudentsFromBeans(yearSelect, ret, dwResults, enrollment);
			}
		}
		return ret;
	}

	private void getCovidInternationalStudentsFromBeans(String yearSelect, DWLoadBean ret, List<AssignmentsBean> dwResults, DWEnrollmentsBean enrollment)
	{
		if ((enrollment.getEnrollmentDate().startsWith(yearSelect) || enrollment.getEnrollmentDate().startsWith("2020") || enrollment.getEnrollmentDate().startsWith("2021")) )
		{
			AssignmentsBean match = null;
			for (AssignmentsBean dwResult : dwResults)
			{
				if(enrollment.getStudentId().equals(dwResult.getStudentId()) && dwResult.getInternational().equals("true"))
				{
					match = dwResult;
					break;
				}
			}
			if(match != null)
			{
				enrollment.setInternational("true");
				ret.addDWEnrollments(enrollment);
			}
		}
	}
}
