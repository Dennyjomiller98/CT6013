package managers.dataview;

import beans.dw.DWResultsBean;

/**
 * @author Denny-Jo
 * Interface for ETLQueryManager, used in the DecisionMakerDataManager
 * Calls The Required Connections to retrieve DW SQL data and return as DWResultsBean
 *
 * @implNote Methods are grouped within their Decision Maker Questions for readability or eventual change of a separate Class for each Decision Maker Question
 * */
public interface IQueryManager
{
	//Q1
	DWResultsBean getTotalStudentsAllCoursesAllYears();
	DWResultsBean getTotalStudentsAllCoursesSelectedYear(String yearSelect);
	DWResultsBean getTotalStudentsSelectedCourseAllYears(String courseSelect);
	DWResultsBean getTotalStudentsSelectedCourseSelectedYear(String courseSelect, String yearSelect);

	//Q2
	DWResultsBean getTotalDropoutsAllCoursesAllYears();
	DWResultsBean getTotalDropoutsAllCoursesSelectedYear(String yearSelect);
	DWResultsBean getTotalDropoutsSelectedCourseAllYears(String courseSelect);
	DWResultsBean getTotalDropoutsSelectedCourseSelectedYear(String courseSelect, String yearSelect);

	//Q3
	DWResultsBean getAverageGradeAllCoursesAllYears();
	DWResultsBean getAverageGradeAllCoursesSelectedYear(String yearSelect);
	DWResultsBean getAverageGradeSelectedCourseAllYears(String courseSelect);
	DWResultsBean getAverageGradeSelectedCourseSelectedYear(String courseSelect, String yearSelect);

	//Q4
	DWResultsBean getPassRateAllTutorsAllCoursesAllYears();
	DWResultsBean getPassRateAllTutorsAllCoursesSelectedYear(String yearSelect);
	DWResultsBean getPassRateAllTutorsSelectedCourseAllYears(String courseSelect);
	DWResultsBean getPassRateAllTutorsSelectedCourseSelectedYear(String courseSelect, String yearSelect);
	DWResultsBean getPassRateSelectedTutorAllCoursesAllYears(String tutorSelect);
	DWResultsBean getPassRateSelectedTutorAllCoursesSelectedYear(String tutorSelect, String yearSelect);
	DWResultsBean getPassRateSelectedTutorSelectedCourseAllYears(String tutorSelect, String courseSelect);
	DWResultsBean getPassRateSelectedTutorSelectedCourseSelectedYear(String tutorSelect, String courseSelect, String yearSelect);

	//Q5
	DWResultsBean getTotalResitsAllCoursesAllYears();
	DWResultsBean getTotalResitsAllCoursesSelectedYear(String yearSelect);
	DWResultsBean getTotalResitsSelectedCourseAllYears(String courseSelect);
	DWResultsBean getTotalResitsSelectedCourseSelectedYear(String courseSelect, String yearSelect);

	//Q6
	DWResultsBean getTotalEnrollmentsAllYears();
	DWResultsBean getTotalEnrollmentsSelectedYear(String yearSelect);

	//Q7
	DWResultsBean getCovidFiguresAgainstAllYears();
	DWResultsBean getCovidFiguresAgainstSelectedYear(String yearSelect);

	//Q8
	DWResultsBean getTotalInternationalStudentsAllCoursesAllYears();
	DWResultsBean getTotalInternationalStudentsAllCoursesSelectedYear(String yearSelect);
	DWResultsBean getTotalInternationalStudentsSelectedCourseAllYears(String courseSelect);
	DWResultsBean getTotalInternationalStudentsSelectedCourseSelectedYear(String courseSelect, String yearSelect);

	//Q9
	DWResultsBean getTotalCourseChangesAllCoursesAllYears();
	DWResultsBean getTotalCourseChangesAllCoursesSelectedYear(String yearSelect);
	DWResultsBean getTotalCourseChangesSelectedCourseAllYears(String courseSelect);
	DWResultsBean getTotalCourseChangesSelectedCourseSelectedYear(String courseSelect, String yearSelect);

	//Q10
	DWResultsBean getCovidInternationalStudentEnrollmentsAgainstAllYears();
	DWResultsBean getCovidInternationalStudentAgainstSelectedYear(String yearSelect);
}
