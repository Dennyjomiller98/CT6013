package beans.dw;

import beans.operational.*;
import beans.operational.dimensions.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Denny-Jo
 * DWResultsBean JavaBean - Assists with storing DB data for the DW Assignments Fact Table
 * */
public class DWResultsBean
{
	private List<AssignmentsBean> fAssignments;
	private List<CoursesBean> fCourses;
	private List<EnrollmentsBean> fEnrollments;
	private List<ModulesBean> fModules;
	private List<StudentsBean> fStudents;
	private List<SubjectsBean> fSubjects;
	private List<TutorsBean> fTutors;

	private List<DimCoursesBean> fDimCourses;
	private List<DimEnrollmentsBean> fDimEnrollments;
	private List<DimModulesBean> fDimModules;
	private List<DimStudentsBean> fDimStudents;
	private List<DimSubjectsBean> fDimSubjects;
	private List<DimTutorsBean> fDimTutors;

	public DWResultsBean()
	{
		//Empty Constructor
	}

	public void setAssignments(List<AssignmentsBean> beans)
	{
		fAssignments = beans;
	}
	public List<AssignmentsBean> getAssignments()
	{
		return fAssignments;
	}
	public void addAssignments(AssignmentsBean bean)
	{
		if (fAssignments == null || fAssignments.isEmpty())
		{
			fAssignments = new ArrayList<>();
		}
		fAssignments.add(bean);
	}

	public void setCourses(List<CoursesBean> beans)
	{
		fCourses = beans;
	}
	public List<CoursesBean> getCourses()
	{
		return fCourses;
	}
	public void addCourses(CoursesBean bean)
	{
		if (fCourses == null || fCourses.isEmpty())
		{
			fCourses = new ArrayList<>();
		}
		fCourses.add(bean);
	}

	public void setModules(List<ModulesBean> beans)
	{
		fModules = beans;
	}
	public List<ModulesBean> getModules()
	{
		return fModules;
	}
	public void addModules(ModulesBean bean)
	{
		if (fModules == null || fModules.isEmpty())
		{
			fModules = new ArrayList<>();
		}
		fModules.add(bean);
	}

	public void setEnrollments(List<EnrollmentsBean> beans)
	{
		fEnrollments = beans;
	}
	public List<EnrollmentsBean> getEnrollments()
	{
		return fEnrollments;
	}
	public void addEnrollments(EnrollmentsBean bean)
	{
		if (fEnrollments == null || fEnrollments.isEmpty())
		{
			fEnrollments = new ArrayList<>();
		}
		fEnrollments.add(bean);
	}

	public void setStudents(List<StudentsBean> beans)
	{
		fStudents = beans;
	}
	public List<StudentsBean> getStudents()
	{
		return fStudents;
	}
	public void addStudents(StudentsBean bean)
	{
		if (fStudents == null || fStudents.isEmpty())
		{
			fStudents = new ArrayList<>();
		}
		fStudents.add(bean);
	}

	public void setSubjects(List<SubjectsBean> beans)
	{
		fSubjects = beans;
	}
	public List<SubjectsBean> getSubjects()
	{
		return fSubjects;
	}
	public void addSubjects(SubjectsBean bean)
	{
		if (fSubjects == null || fSubjects.isEmpty())
		{
			fSubjects = new ArrayList<>();
		}
		fSubjects.add(bean);
	}

	public void setTutors(List<TutorsBean> beans)
	{
		fTutors = beans;
	}
	public List<TutorsBean> getTutors()
	{
		return fTutors;
	}
	public void addTutors(TutorsBean bean)
	{
		if (fTutors == null || fTutors.isEmpty())
		{
			fTutors = new ArrayList<>();
		}
		fTutors.add(bean);
	}

	public void setDimCourses(List<DimCoursesBean> beans)
	{
		fDimCourses = beans;
	}
	public List<DimCoursesBean> getDimCourses()
	{
		return fDimCourses;
	}
	public void addDimCourses(DimCoursesBean bean)
	{
		if (fDimCourses == null || fDimCourses.isEmpty())
		{
			fDimCourses = new ArrayList<>();
		}
		fDimCourses.add(bean);
	}

	public void setDimModules(List<DimModulesBean> beans)
	{
		fDimModules = beans;
	}
	public List<DimModulesBean> getDimModules()
	{
		return fDimModules;
	}
	public void addDimModules(DimModulesBean bean)
	{
		if (fDimModules == null || fDimModules.isEmpty())
		{
			fDimModules = new ArrayList<>();
		}
		fDimModules.add(bean);
	}

	public void setDimEnrollments(List<DimEnrollmentsBean> beans)
	{
		fDimEnrollments = beans;
	}
	public List<DimEnrollmentsBean> getDimEnrollments()
	{
		return fDimEnrollments;
	}
	public void addDimEnrollments(DimEnrollmentsBean bean)
	{
		if (fDimEnrollments == null || fDimEnrollments.isEmpty())
		{
			fDimEnrollments = new ArrayList<>();
		}
		fDimEnrollments.add(bean);
	}

	public void setDimStudents(List<DimStudentsBean> beans)
	{
		fDimStudents = beans;
	}
	public List<DimStudentsBean> getDimStudents()
	{
		return fDimStudents;
	}
	public void addDimStudents(DimStudentsBean bean)
	{
		if (fDimStudents == null || fDimStudents.isEmpty())
		{
			fDimStudents = new ArrayList<>();
		}
		fDimStudents.add(bean);
	}

	public void setDimSubjects(List<DimSubjectsBean> beans)
	{
		fDimSubjects = beans;
	}
	public List<DimSubjectsBean> getDimSubjects()
	{
		return fDimSubjects;
	}
	public void addDimSubjects(DimSubjectsBean bean)
	{
		if (fDimSubjects == null || fDimSubjects.isEmpty())
		{
			fDimSubjects = new ArrayList<>();
		}
		fDimSubjects.add(bean);
	}

	public void setDimTutors(List<DimTutorsBean> beans)
	{
		fDimTutors = beans;
	}
	public List<DimTutorsBean> getDimTutors()
	{
		return fDimTutors;
	}
	public void addDimTutors(DimTutorsBean bean)
	{
		if (fDimTutors == null || fDimTutors.isEmpty())
		{
			fDimTutors = new ArrayList<>();
		}
		fDimTutors.add(bean);
	}
}
