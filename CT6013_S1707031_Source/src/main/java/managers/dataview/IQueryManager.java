package managers.dataview;

import beans.dw.DWLoadBean;

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
	DWLoadBean getTotalStudentsAllCoursesAllYears();
	DWLoadBean getTotalStudentsAllCoursesSelectedYear(String yearSelect);
	DWLoadBean getTotalStudentsSelectedCourseAllYears(String courseSelect);
	DWLoadBean getTotalStudentsSelectedCourseSelectedYear(String courseSelect, String yearSelect);

	//Q2
	DWLoadBean getTotalDropoutsAllCoursesAllYears();
	DWLoadBean getTotalDropoutsAllCoursesSelectedYear(String yearSelect);
	DWLoadBean getTotalDropoutsSelectedCourseAllYears(String courseSelect);
	DWLoadBean getTotalDropoutsSelectedCourseSelectedYear(String courseSelect, String yearSelect);

	//Q3
	DWLoadBean getAverageGradeAllCoursesAllYears();
	DWLoadBean getAverageGradeAllCoursesSelectedYear(String yearSelect);
	DWLoadBean getAverageGradeSelectedCourseAllYears(String courseSelect);
	DWLoadBean getAverageGradeSelectedCourseSelectedYear(String courseSelect, String yearSelect);

	//Q4
	DWLoadBean getPassRateAllTutorsAllCoursesAllYears();
	DWLoadBean getPassRateAllTutorsAllCoursesSelectedYear(String yearSelect);
	DWLoadBean getPassRateAllTutorsSelectedCourseAllYears(String courseSelect);
	DWLoadBean getPassRateAllTutorsSelectedCourseSelectedYear(String courseSelect, String yearSelect);
	DWLoadBean getPassRateSelectedTutorAllCoursesAllYears(String tutorSelect);
	DWLoadBean getPassRateSelectedTutorAllCoursesSelectedYear(String tutorSelect, String yearSelect);
	DWLoadBean getPassRateSelectedTutorSelectedCourseAllYears(String tutorSelect, String courseSelect);
	DWLoadBean getPassRateSelectedTutorSelectedCourseSelectedYear(String tutorSelect, String courseSelect, String yearSelect);

	//Q5
	DWLoadBean getTotalResitsAllCoursesAllYears();
	DWLoadBean getTotalResitsAllCoursesSelectedYear(String yearSelect);
	DWLoadBean getTotalResitsSelectedCourseAllYears(String courseSelect);
	DWLoadBean getTotalResitsSelectedCourseSelectedYear(String courseSelect, String yearSelect);

	//Q6
	DWLoadBean getTotalEnrollmentsAllYears();
	DWLoadBean getTotalEnrollmentsSelectedYear(String yearSelect);

	//Q7
	DWLoadBean getCovidFiguresAgainstAllYears();
	DWLoadBean getCovidFiguresAgainstSelectedYear(String yearSelect);

	//Q8
	DWLoadBean getTotalInternationalStudentsAllCoursesAllYears();
	DWLoadBean getTotalInternationalStudentsAllCoursesSelectedYear(String yearSelect);
	DWLoadBean getTotalInternationalStudentsSelectedCourseAllYears(String courseSelect);
	DWLoadBean getTotalInternationalStudentsSelectedCourseSelectedYear(String courseSelect, String yearSelect);

	//Q9
	DWLoadBean getTotalCourseChangesAllCoursesAllYears();
	DWLoadBean getTotalCourseChangesAllCoursesSelectedYear(String yearSelect);
	DWLoadBean getTotalCourseChangesSelectedCourseAllYears(String courseSelect);
	DWLoadBean getTotalCourseChangesSelectedCourseSelectedYear(String courseSelect, String yearSelect);

	//Q10
	DWLoadBean getCovidInternationalStudentEnrollmentsAgainstAllYears();
	DWLoadBean getCovidInternationalStudentAgainstSelectedYear(String yearSelect);
}
