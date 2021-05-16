package managers.dataview;

import beans.dw.DWResultsBean;

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

	//TODO - ALL QUERIES TO DW

	@Override
	public DWResultsBean getTotalStudentsAllCoursesAllYears()
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalStudentsAllCoursesSelectedYear(String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalStudentsSelectedCourseAllYears(String courseSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalStudentsSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalDropoutsAllCoursesAllYears()
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalDropoutsAllCoursesSelectedYear(String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalDropoutsSelectedCourseAllYears(String courseSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalDropoutsSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getAverageGradeAllCoursesAllYears()
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getAverageGradeAllCoursesSelectedYear(String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getAverageGradeSelectedCourseAllYears(String courseSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getAverageGradeSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getPassRateAllTutorsAllCoursesAllYears()
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getPassRateAllTutorsAllCoursesSelectedYear(String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getPassRateAllTutorsSelectedCourseAllYears(String courseSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getPassRateAllTutorsSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getPassRateSelectedTutorAllCoursesAllYears(String tutorSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getPassRateSelectedTutorAllCoursesSelectedYear(String tutorSelect, String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getPassRateSelectedTutorSelectedCourseAllYears(String tutorSelect, String courseSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getPassRateSelectedTutorSelectedCourseSelectedYear(String tutorSelect, String courseSelect, String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalResitsAllCoursesAllYears()
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalResitsAllCoursesSelectedYear(String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalResitsSelectedCourseAllYears(String courseSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalResitsSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalEnrollmentsAllYears()
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalEnrollmentsSelectedYear(String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getCovidFiguresAgainstAllYears()
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getCovidFiguresAgainstSelectedYear(String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalInternationalStudentsAllCoursesAllYears()
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalInternationalStudentsAllCoursesSelectedYear(String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalInternationalStudentsSelectedCourseAllYears(String courseSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalInternationalStudentsSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalCourseChangesAllCoursesAllYears()
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalCourseChangesAllCoursesSelectedYear(String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalCourseChangesSelectedCourseAllYears(String courseSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getTotalCourseChangesSelectedCourseSelectedYear(String courseSelect, String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getCovidInternationalStudentEnrollmentsAgainstAllYears()
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}

	@Override
	public DWResultsBean getCovidInternationalStudentAgainstSelectedYear(String yearSelect)
	{
		DWResultsBean ret= new DWResultsBean();
		return ret;
	}
}
