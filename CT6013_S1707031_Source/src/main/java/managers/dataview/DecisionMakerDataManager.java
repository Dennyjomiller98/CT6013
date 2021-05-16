package managers.dataview;

import beans.UserBean;
import beans.dw.DWResultsBean;
import oracle.UserConnections;
import org.apache.log4j.Logger;

/**
 * @author Denny-Jo
 * DecisionMakerDataManager - Manager Class used to assist retrieving the relevant information/Calling DW SQL Queries for Decision Makers
 *
 * NOTE: This is called in the dwpreview package, when view.vm form is filled out.
 * @implNote Roles and Privilege checking is done here, ensuring the user is authorised to view that data
 * */
public class DecisionMakerDataManager
{
	static final Logger LOG = Logger.getLogger(DecisionMakerDataManager.class);

	private static final String YEAR = "YEAR";
	private static final String YEAR_ERROR = "Year not Selected/Found";
	private static final String TUTOR = "TUTOR";
	private static final String TUTOR_ERROR = "Tutor not Selected/Found";
	private static final String COURSE = "COURSE";
	private static final String COURSE_ERROR = "Course not Selected/Found";

	public DecisionMakerDataManager()
	{
		//Empty Constructor
	}

	public DWResultsBean attemptDataRetrieval(String querySelected, String yearSelect, String courseSelect, String tutorSelect)
	{
		DWResultsBean results = new DWResultsBean();
		IQueryManager queryManager = new ETLQueryManager();
		switch (querySelected)
		{
			case "q1":
				results = q1QuerySelection(yearSelect, courseSelect, queryManager);
				break;

			case "q2":
				results = q2QuerySelection(yearSelect, courseSelect, queryManager);
				break;

			case "q3":
				results = q3QuerySelection(yearSelect, courseSelect, queryManager);
				break;

			case "q4":
				results = q4QuerySelection(yearSelect, courseSelect, tutorSelect, queryManager);
				break;

			case "q5":
				results = q5QuerySelection(yearSelect, courseSelect, queryManager);
				break;

			case "q6":
				results = q6QuerySelection(yearSelect, queryManager);
				break;

			case "q7":
				results = q7QuerySelection(yearSelect, queryManager);
				break;

			case "q8":
				results = q8QuerySelection(yearSelect, courseSelect, queryManager);
				break;

			case "q9":
				results = q9QuerySelection(yearSelect, courseSelect, queryManager);
				break;

			case "q10":
				results = q10QuerySelection(yearSelect, queryManager);
				break;

			default:
				LOG.error("Unknown Query Selection, no query can be performed against DW");
		}
		return results;
	}

	private DWResultsBean q1QuerySelection(String yearSelect, String courseSelect, IQueryManager queryManager)
	{
		DWResultsBean bean;
		if(courseSelect != null)
		{
			if(courseSelect.equals("all"))
			{
				if(yearSelect != null)
				{
					if(yearSelect.equals("all"))
					{
						bean = queryManager.getTotalStudentsAllCoursesAllYears();
					}
					else
					{
						bean = queryManager.getTotalStudentsAllCoursesSelectedYear(yearSelect);
					}
				}
				else
				{
					LOG.error("Year not found/selected");
					bean = new DWResultsBean();
					bean.addErrors(YEAR, YEAR_ERROR);
				}
			}
			else
			{
				if(yearSelect != null)
				{
					if (yearSelect.equals("all"))
					{
						bean = queryManager.getTotalStudentsSelectedCourseAllYears(courseSelect);
					}
					else
					{
						bean = queryManager.getTotalStudentsSelectedCourseSelectedYear(courseSelect, yearSelect);
					}
				}
				else
				{
					LOG.error("Year not found/selected");
					bean = new DWResultsBean();
					bean.addErrors(YEAR, YEAR_ERROR);
				}
			}
		}
		else
		{
			LOG.error("Course not found/selected");
			bean = new DWResultsBean();
			bean.addErrors(COURSE, COURSE_ERROR);
		}
		return bean;
	}

	private DWResultsBean q2QuerySelection(String yearSelect, String courseSelect, IQueryManager queryManager)
	{
		DWResultsBean bean;
		if (courseSelect != null)
		{
			if(courseSelect.equals("all"))
			{
				if(yearSelect != null)
				{
					if(yearSelect.equals("all"))
					{
						bean = queryManager.getTotalDropoutsAllCoursesAllYears();
					}
					else
					{
						bean = queryManager.getTotalDropoutsAllCoursesSelectedYear(yearSelect);
					}
				}
				else
				{
					LOG.error("Year not found/selected");
					bean = new DWResultsBean();
					bean.addErrors(YEAR, YEAR_ERROR);
				}
			}
			else
			{
				if(yearSelect != null)
				{
					if(yearSelect.equals("all"))
					{
						bean = queryManager.getTotalDropoutsSelectedCourseAllYears(courseSelect);
					}
					else
					{
						bean = queryManager.getTotalDropoutsSelectedCourseSelectedYear(courseSelect, yearSelect);

					}
				}
				else
				{
					LOG.error("Year not found/selected");
					bean = new DWResultsBean();
					bean.addErrors(YEAR, YEAR_ERROR);
				}
			}
		}
		else
		{
			LOG.error("Course not found/selected");
			bean = new DWResultsBean();
			bean.addErrors(COURSE, COURSE_ERROR);
		}
		return bean;
	}

	private DWResultsBean q3QuerySelection(String yearSelect, String courseSelect, IQueryManager queryManager)
	{
		DWResultsBean bean;
		if (courseSelect != null)
		{
			if(courseSelect.equals("all"))
			{
				if (yearSelect != null)
				{
					if(yearSelect.equals("all"))
					{
						bean = queryManager.getAverageGradeAllCoursesAllYears();
					}
					else
					{
						bean = queryManager.getAverageGradeAllCoursesSelectedYear(yearSelect);
					}
				}
				else
				{
					LOG.error("Year not found/selected");
					bean = new DWResultsBean();
					bean.addErrors(YEAR, YEAR_ERROR);
				}
			}
			else
			{
				if (yearSelect != null)
				{
					if(yearSelect.equals("all"))
					{
						bean = queryManager.getAverageGradeSelectedCourseAllYears(courseSelect);
					}
					else
					{
						bean = queryManager.getAverageGradeSelectedCourseSelectedYear(courseSelect, yearSelect);
					}
				}
				else
				{
					LOG.error("Year not found/selected");
					bean = new DWResultsBean();
					bean.addErrors(YEAR, YEAR_ERROR);
				}
			}
		}
		else
		{
			LOG.error("Course not found/selected");
			bean = new DWResultsBean();
			bean.addErrors(COURSE, COURSE_ERROR);
		}
		return bean;
	}

	private DWResultsBean q4QuerySelection(String yearSelect, String courseSelect, String tutorSelect, IQueryManager queryManager)
	{
		DWResultsBean bean;
		if(tutorSelect != null)
		{
			if(tutorSelect.equals("all"))
			{
				bean = q4AllTutorQuery(yearSelect, courseSelect, queryManager);
			}
			else
			{
				bean = q4SelectedTutorQuery(yearSelect, courseSelect, tutorSelect, queryManager);
			}
		}
		else
		{
			LOG.error("Tutor not found/selected");
			bean = new DWResultsBean();
			bean.addErrors(TUTOR, TUTOR_ERROR);
		}
		return bean;
	}

	private DWResultsBean q4SelectedTutorQuery(String yearSelect, String courseSelect, String tutorSelect, IQueryManager queryManager)
	{
		DWResultsBean bean;
		if (courseSelect != null)
		{
			if(courseSelect.equals("all"))
			{
				if(yearSelect != null)
				{
					if(yearSelect.equals("all"))
					{
						bean = queryManager.getPassRateSelectedTutorAllCoursesAllYears(tutorSelect);
					}
					else
					{
						bean = queryManager.getPassRateSelectedTutorAllCoursesSelectedYear(tutorSelect, yearSelect);
					}
				}
				else
				{
					LOG.error("Year not found/selected");
					bean = new DWResultsBean();
					bean.addErrors(YEAR, YEAR_ERROR);
				}
			}
			else
			{
				if (yearSelect != null)
				{
					if(yearSelect.equals("all"))
					{
						bean = queryManager.getPassRateSelectedTutorSelectedCourseAllYears(tutorSelect, courseSelect);
					}
					else
					{
						bean = queryManager.getPassRateSelectedTutorSelectedCourseSelectedYear(tutorSelect, courseSelect, yearSelect);
					}
				}
				else
				{
					LOG.error("Year not found/selected");
					bean = new DWResultsBean();
					bean.addErrors(YEAR, YEAR_ERROR);
				}
			}
		}
		else
		{
			LOG.error("Course not found/selected");
			bean = new DWResultsBean();
			bean.addErrors(COURSE, COURSE_ERROR);
		}
		return bean;
	}

	private DWResultsBean q4AllTutorQuery(String yearSelect, String courseSelect, IQueryManager queryManager)
	{
		DWResultsBean bean;
		if (courseSelect != null)
		{
			if(courseSelect.equals("all"))
			{
				if(yearSelect != null)
				{
					if(yearSelect.equals("all"))
					{
						bean = queryManager.getPassRateAllTutorsAllCoursesAllYears();
					}
					else
					{
						bean = queryManager.getPassRateAllTutorsAllCoursesSelectedYear(yearSelect);
					}
				}
				else
				{
					LOG.error("Year not found/selected");
					bean = new DWResultsBean();
					bean.addErrors(YEAR, YEAR_ERROR);
				}
			}
			else
			{
				if(yearSelect != null)
				{
					if(yearSelect.equals("all"))
					{
						bean = queryManager.getPassRateAllTutorsSelectedCourseAllYears(courseSelect);
					}
					else
					{
						bean = queryManager.getPassRateAllTutorsSelectedCourseSelectedYear(courseSelect, yearSelect);
					}
				}
				else
				{
					LOG.error("Year not found/selected");
					bean = new DWResultsBean();
					bean.addErrors(YEAR, YEAR_ERROR);
				}
			}
		}
		else
		{
			LOG.error("Course not found/selected");
			bean = new DWResultsBean();
			bean.addErrors(COURSE, COURSE_ERROR);
		}
		return bean;
	}

	private DWResultsBean q5QuerySelection(String yearSelect, String courseSelect, IQueryManager queryManager)
	{
		DWResultsBean bean;
		if(courseSelect != null)
		{
			if(courseSelect.equals("all"))
			{
				if(yearSelect != null)
				{
					if(yearSelect.equals("all"))
					{
						bean = queryManager.getTotalResitsAllCoursesAllYears();
					}
					else
					{
						bean = queryManager.getTotalResitsAllCoursesSelectedYear(yearSelect);
					}
				}
				else
				{
					LOG.error("Year not found/selected");
					bean = new DWResultsBean();
					bean.addErrors(YEAR, YEAR_ERROR);
				}
			}
			else
			{
				if(yearSelect != null)
				{
					if(yearSelect.equals("all"))
					{
						bean = queryManager.getTotalResitsSelectedCourseAllYears(courseSelect);
					}
					else
					{
						bean = queryManager.getTotalResitsSelectedCourseSelectedYear(courseSelect, yearSelect);
					}
				}
				else
				{
					LOG.error("Year not found/selected");
					bean = new DWResultsBean();
					bean.addErrors(YEAR, YEAR_ERROR);
				}
			}
		}
		else
		{
			LOG.error("Course not found/selected");
			bean = new DWResultsBean();
			bean.addErrors(COURSE, COURSE_ERROR);
		}
		return bean;
	}

	private DWResultsBean q6QuerySelection(String yearSelect, IQueryManager queryManager)
	{
		DWResultsBean bean;
		if(yearSelect != null)
		{
			if(yearSelect.equals("all"))
			{
				bean = queryManager.getTotalEnrollmentsAllYears();
			}
			else
			{
				bean = queryManager.getTotalEnrollmentsSelectedYear(yearSelect);
			}
		}
		else
		{
			LOG.error("Year not found/selected");
			bean = new DWResultsBean();
			bean.addErrors(YEAR, YEAR_ERROR);
		}
		return bean;
	}

	private DWResultsBean q7QuerySelection(String yearSelect, IQueryManager queryManager)
	{
		DWResultsBean bean;
		if(yearSelect != null)
		{
			if(yearSelect.equals("all"))
			{
				bean = queryManager.getCovidFiguresAgainstAllYears();
			}
			else
			{
				bean = queryManager.getCovidFiguresAgainstSelectedYear(yearSelect);
			}
		}
		else
		{
			LOG.error("Year not found/selected");
			bean = new DWResultsBean();
			bean.addErrors(YEAR, YEAR_ERROR);
		}
		return bean;
	}

	private DWResultsBean q8QuerySelection(String yearSelect, String courseSelect, IQueryManager queryManager)
	{
		DWResultsBean bean;
		if(courseSelect != null)
		{
			if(courseSelect.equals("all"))
			{
				if(yearSelect != null)
				{
					if(yearSelect.equals("all"))
					{
						bean = queryManager.getTotalInternationalStudentsAllCoursesAllYears();
					}
					else
					{
						bean = queryManager.getTotalInternationalStudentsAllCoursesSelectedYear(yearSelect);
					}
				}
				else
				{
					LOG.error("Year not found/selected");
					bean = new DWResultsBean();
					bean.addErrors(YEAR, YEAR_ERROR);
				}
			}
			else
			{
				if(yearSelect != null)
				{
					if(yearSelect.equals("all"))
					{
						bean = queryManager.getTotalInternationalStudentsSelectedCourseAllYears(courseSelect);
					}
					else
					{
						bean = queryManager.getTotalInternationalStudentsSelectedCourseSelectedYear(courseSelect, yearSelect);
					}
				}
				else
				{
					LOG.error("Year not found/selected");
					bean = new DWResultsBean();
					bean.addErrors(YEAR, YEAR_ERROR);
				}
			}
		}
		else
		{
			LOG.error("Course not found/selected");
			bean = new DWResultsBean();
			bean.addErrors(COURSE, COURSE_ERROR);
		}
		return bean;
	}

	private DWResultsBean q9QuerySelection(String yearSelect, String courseSelect, IQueryManager queryManager)
	{
		DWResultsBean bean;
		if(courseSelect != null)
		{
			if(courseSelect.equals("all"))
			{
				if(yearSelect != null)
				{
					if(yearSelect.equals("all"))
					{
						bean = queryManager.getTotalCourseChangesAllCoursesAllYears();
					}
					else
					{
						bean = queryManager.getTotalCourseChangesAllCoursesSelectedYear(yearSelect);
					}
				}
				else
				{
					LOG.error("Year not found/selected");
					bean = new DWResultsBean();
					bean.addErrors(YEAR, YEAR_ERROR);
				}
			}
			else
			{
				if(yearSelect != null)
				{
					if(yearSelect.equals("all"))
					{
						bean = queryManager.getTotalCourseChangesSelectedCourseAllYears(courseSelect);
					}
					else
					{
						bean = queryManager.getTotalCourseChangesSelectedCourseSelectedYear(courseSelect, yearSelect);
					}
				}
				else
				{
					LOG.error("Year not found/selected");
					bean = new DWResultsBean();
					bean.addErrors(YEAR, YEAR_ERROR);
				}
			}
		}
		else
		{
			LOG.error("Course not found/selected");
			bean = new DWResultsBean();
			bean.addErrors(COURSE, COURSE_ERROR);
		}
		return bean;
	}

	private DWResultsBean q10QuerySelection(String yearSelect, IQueryManager queryManager)
	{
		DWResultsBean bean;
		if(yearSelect != null)
		{
			if(yearSelect.equals("all"))
			{
				bean = queryManager.getCovidInternationalStudentEnrollmentsAgainstAllYears();
			}
			else
			{
				bean = queryManager.getCovidInternationalStudentAgainstSelectedYear(yearSelect);
			}
		}
		else
		{
			LOG.error("Year not found/selected");
			bean = new DWResultsBean();
			bean.addErrors(YEAR, YEAR_ERROR);
		}
		return bean;
	}

	public boolean validateRole(String userEmail, String querySelected)
	{
		boolean canQuery = false;
		UserConnections connections = new UserConnections();
		UserBean userBean = connections.retrieveSingleUser(userEmail);
		if(userBean != null)
		{
			String role = userBean.getRole();
			if(role != null)
			{
				canQuery = validateRoleAgainstSelectedQuery(role, querySelected);
			}
		}
		return canQuery;
	}

	/**
	 * @implNote Roles/Authentication are done here, as we cannot generate new users for each Role below
	 * The following roles are:
	 * SUPER_ADMIN
	 * ENROLLMENT
	 * COURSE
	 *
	 * Comparing roles to the Decision Maker Questions, the following access should be provided:
	 * (Refer to Report for the list of Decision Maker Question, or look in view.vm for the Dropdown)
	 *
	 * SUPER_ADMIN	:	ALL QUESTIONS
	 * ENROLLMENT	:	QUESTIONS 2, 6, 7, 8, 9, 10
	 * COURSE		:	QUESTIONS 1, 2, 3, 4, 5
	 *
	 * */
	private boolean validateRoleAgainstSelectedQuery(String role, String querySelected)
	{
		boolean authorised = false;
		if(role != null && querySelected != null)
		{
			switch (role)
			{
				case "SUPER_ADMIN":
					//Vice-Chancellor/All access
					//No check needed, all access
					authorised = true;
					break;
				case "ENROLLMENT":
					//Enrollment Queries
					if (querySelected.equals("q2") || querySelected.equals("q6")
							|| querySelected.equals("q7") || querySelected.equals("q8")
							|| querySelected.equals("q9") || querySelected.equals("q10"))
					{
						authorised = true;
					}
					else
					{
						LOG.error("Unauthorised Query attempted");
					}
					break;
				case "COURSE":
					if (querySelected.equals("q1") || querySelected.equals("q2")
							|| querySelected.equals("q3") || querySelected.equals("q4")
							|| querySelected.equals("q5"))
					{
						authorised = true;
					}
					else
					{
						LOG.error("Unauthorised Query attempted");
					}
					break;
				default:
					LOG.error("Unknown Role Selection, no authorisation can be given");
					break;
			}
			//Course Queries
		}
		return authorised;
	}
}
