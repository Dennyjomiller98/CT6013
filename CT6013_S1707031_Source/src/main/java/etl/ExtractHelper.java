package etl;

import beans.operational.*;
import beans.operational.dimensions.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import oracle.ExtractConnections;

/**
 * @author Denny-Jo
 * ExtractHelper - Helper Class that creates Connections to Operational Database(s), retrieving required data, generating Lists of Beans
 *
 * Helper Class has been extracted so it can be expanded to extract different types of Data Sources (in the event of new features requiring this use)
 * */
public class ExtractHelper
{
	public ExtractHelper()
	{
		//Empty Constructor
	}

	public List<AssignmentsBean> retrieveAssignmentsTable()
	{
		List<AssignmentsBean> allAssignments = new ArrayList<>();
		ExtractConnections conn = new ExtractConnections();
		List<AssignmentsBean> assignmentsBeans = conn.retrieveAssignmentsTable();
		if(assignmentsBeans != null && !assignmentsBeans.isEmpty())
		{
			allAssignments.addAll(assignmentsBeans);
		}
		return allAssignments;
	}

	public List<CoursesBean> retrieveCoursesTable()
	{
		List<CoursesBean> allCourses = new ArrayList<>();
		ExtractConnections conn = new ExtractConnections();
		List<CoursesBean> coursesBeans = conn.retrieveCoursesTable();
		if(coursesBeans != null && !coursesBeans.isEmpty())
		{
			allCourses.addAll(coursesBeans);
		}
		return allCourses;
	}
	public List<DimCoursesBean> retrieveDimCoursesTable()
	{
		List<DimCoursesBean> allCourses = new ArrayList<>();
		ExtractConnections conn = new ExtractConnections();
		List<DimCoursesBean> coursesBeans = conn.retrieveDimCoursesTable();
		if(coursesBeans != null && !coursesBeans.isEmpty())
		{
			allCourses.addAll(coursesBeans);
		}
		return allCourses;
	}

	public List<EnrollmentsBean> retrieveEnrollmentsTable()
	{
		List<EnrollmentsBean> allEnrollments = new ArrayList<>();
		ExtractConnections conn = new ExtractConnections();
		List<EnrollmentsBean> enrollmentsBeans = conn.retrieveEnrollmentsTable();
		if(enrollmentsBeans != null && !enrollmentsBeans.isEmpty())
		{
			allEnrollments.addAll(enrollmentsBeans);
		}
		return allEnrollments;
	}
	public List<DimEnrollmentsBean> retrieveDimEnrollmentsTable()
	{
		List<DimEnrollmentsBean> allEnrollments = new ArrayList<>();
		ExtractConnections conn = new ExtractConnections();
		List<DimEnrollmentsBean> enrollmentsBeans = conn.retrieveDimEnrollmentsTable();
		if(enrollmentsBeans != null && !enrollmentsBeans.isEmpty())
		{
			allEnrollments.addAll(enrollmentsBeans);
		}
		return allEnrollments;
	}

	public List<ModulesBean> retrieveModulesTable()
	{
		List<ModulesBean> allModules = new ArrayList<>();
		ExtractConnections conn = new ExtractConnections();
		List<ModulesBean> modulesBeans = conn.retrieveModulesTable();
		if(modulesBeans != null && !modulesBeans.isEmpty())
		{
			allModules.addAll(modulesBeans);
		}
		return allModules;
	}
	public List<DimModulesBean> retrieveDimModulesTable()
	{
		List<DimModulesBean> allModules = new ArrayList<>();
		ExtractConnections conn = new ExtractConnections();
		List<DimModulesBean> modulesBeans = conn.retrieveDimModulesTable();
		if(modulesBeans != null && !modulesBeans.isEmpty())
		{
			allModules.addAll(modulesBeans);
		}
		return allModules;
	}

	public List<StudentsBean> retrieveStudentsTable()
	{
		List<StudentsBean> allStudents = new ArrayList<>();
		ExtractConnections conn = new ExtractConnections();
		List<StudentsBean> studentsBeans = conn.retrieveStudentsTable();
		if(studentsBeans != null && !studentsBeans.isEmpty())
		{
			allStudents.addAll(studentsBeans);
		}
		return allStudents;
	}
	public List<DimStudentsBean> retrieveDimStudentsTable()
	{
		List<DimStudentsBean> allStudents = new ArrayList<>();
		ExtractConnections conn = new ExtractConnections();
		List<DimStudentsBean> studentsBeans = conn.retrieveDimStudentsTable();
		if(studentsBeans != null && !studentsBeans.isEmpty())
		{
			allStudents.addAll(studentsBeans);
		}
		return allStudents;
	}

	public List<SubjectsBean> retrieveSubjectsTable()
	{
		List<SubjectsBean> allSubjects = new ArrayList<>();
		ExtractConnections conn = new ExtractConnections();
		List<SubjectsBean> subjectsBeans = conn.retrieveSubjectsTable();
		if(subjectsBeans != null && !subjectsBeans.isEmpty())
		{
			allSubjects.addAll(subjectsBeans);
		}
		return allSubjects;
	}
	public List<DimSubjectsBean> retrieveDimSubjectsTable()
	{
		List<DimSubjectsBean> allSubjects = new ArrayList<>();
		ExtractConnections conn = new ExtractConnections();
		List<DimSubjectsBean> subjectsBeans = conn.retrieveDimSubjectsTable();
		if(subjectsBeans != null && !subjectsBeans.isEmpty())
		{
			allSubjects.addAll(subjectsBeans);
		}
		return allSubjects;
	}

	public List<TutorsBean> retrieveTutorsTable()
	{
		List<TutorsBean> allTutors = new ArrayList<>();
		ExtractConnections conn = new ExtractConnections();
		List<TutorsBean> tutorsBeans = conn.retrieveTutorsTable();
		if(tutorsBeans != null && !tutorsBeans.isEmpty())
		{
			allTutors.addAll(tutorsBeans);
		}
		return allTutors;
	}
	public List<DimTutorsBean> retrieveDimTutorsTable()
	{
		List<DimTutorsBean> allTutors = new ArrayList<>();
		ExtractConnections conn = new ExtractConnections();
		List<DimTutorsBean> tutorsBeans = conn.retrieveDimTutorsTable();
		if(tutorsBeans != null && !tutorsBeans.isEmpty())
		{
			allTutors.addAll(tutorsBeans);
		}
		return allTutors;
	}
}
