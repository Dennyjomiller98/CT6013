package etl;

import beans.dw.DWResultsBean;
import beans.operational.*;
import beans.operational.dimensions.*;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * @author Denny-Jo
 * DataTransformer is used to ensure values placed into the DWResultsBean are valid
 * e.g. A student enrolled on a course, the Course Id present MUST be a valid course found in the CourseBeans/DimCourseBeans
 * */
public class DataTransformer
{
	static final Logger LOG = Logger.getLogger(DataTransformer.class);

	public DataTransformer()
	{
		//Empty Constructor
	}

	public DWResultsBean validateAndTransformData(DWResultsBean bean)
	{
		validateTables(bean);
		validateDimensionTables(bean);
		return bean;
	}

	//Validates the current data tables (used for DW_RESULTS)
	private void validateTables(DWResultsBean bean)
	{
		List<SubjectsBean> subjects = bean.getSubjects();
		List<ModulesBean> modules = bean.getModules();
		List<CoursesBean> courses = bean.getCourses();
		List<EnrollmentsBean> enrollments = bean.getEnrollments();
		List<StudentsBean> students = bean.getStudents();

		//Validate each data piece
		validateModuleCourseLinks(modules, courses);
		validateCourseSubjectLinks(courses, subjects);
		validateEnrollmentLinks(enrollments, students, courses);

		//Put values back in
		bean.setModules(modules);
		bean.setCourses(courses);
		bean.setEnrollments(enrollments);
	}

	//Validates the 2SCD Tables
	private void validateDimensionTables(DWResultsBean bean)
	{
		List<DimSubjectsBean> dimSubjects = bean.getDimSubjects();
		List<DimModulesBean> dimModules = bean.getDimModules();
		List<DimCoursesBean> dimCourses = bean.getDimCourses();
		List<DimEnrollmentsBean> dimEnrollments = bean.getDimEnrollments();

		//Validate each data piece
		validateDimModuleCourseLinks(dimModules, dimCourses);
		validateDimCourseSubjectLinks(dimCourses, dimSubjects);

		//Put values back in
		bean.setDimModules(dimModules);
		bean.setDimCourses(dimCourses);
		bean.setDimEnrollments(dimEnrollments);
	}

	private void validateEnrollmentLinks(List<EnrollmentsBean> enrollments, List<StudentsBean> students, List<CoursesBean> courses)
	{
		if(enrollments != null && !enrollments.isEmpty() && students != null && !students.isEmpty() && courses != null && !courses.isEmpty())
		{
			for (EnrollmentsBean enrollment : enrollments)
			{
				String studentId = enrollment.getStudentId();
				if(!studentId.equalsIgnoreCase("Unknown") && !studentId.equalsIgnoreCase("None"))
				{
					checkEnrollmentStudentIdExists(studentId, students, enrollment);
				}
				String courseId = enrollment.getCourseId();
				if(!courseId.equalsIgnoreCase("Unknown") && !courseId.equalsIgnoreCase("None"))
				{
					checkEnrollmentCourseIdExists(courseId, courses, enrollment);
				}
			}
		}
	}

	private void checkEnrollmentStudentIdExists(String studentId, List<StudentsBean> students, EnrollmentsBean enrollment)
	{
		boolean found = false;
		for (StudentsBean student : students)
		{
			if(student.getStudentId().equalsIgnoreCase(studentId))
			{
				found = true;
				break;
			}
		}
		if(!found)
		{
			LOG.error("Student ID: " + studentId + " has no matching Student User");
			enrollment.setStudentId("Unknown");
		}
	}

	private void checkEnrollmentCourseIdExists(String courseId, List<CoursesBean> courses, EnrollmentsBean enrollment)
	{
		boolean found = false;
		for (CoursesBean course : courses)
		{
			if(course.getCourseId().equals(courseId))
			{
				found = true;
				break;
			}
		}
		if(!found)
		{
			LOG.error("Course ID: " + courseId + " has no matching Course");
			enrollment.setCourseId("Unknown");
		}
	}

	private void validateModuleCourseLinks(List<ModulesBean> modules, List<CoursesBean> courses)
	{
		if(modules != null && !modules.isEmpty() && courses != null && !courses.isEmpty())
		{
			for (ModulesBean module : modules)
			{
				String moduleId = module.getModuleId();
				if(!moduleId.equalsIgnoreCase("Unknown") && !moduleId.equalsIgnoreCase("None"))
				{
					checkModuleIdExists(courses, module, moduleId);
				}
			}
		}
	}

	private void checkModuleIdExists(List<CoursesBean> courses, ModulesBean module, String moduleId)
	{
		boolean found = false;
		for (CoursesBean course : courses)
		{
			if(course.getModuleIds().contains(moduleId))
			{
				found = true;
				break;
			}
		}
		if(!found)
		{
			LOG.error("Module ID: " + moduleId + " has no matching Course found");
			module.setModuleId("Unknown");
		}
	}

	private void validateCourseSubjectLinks(List<CoursesBean> courses, List<SubjectsBean> subjects)
	{
		if(courses != null && !courses.isEmpty() && subjects != null && !subjects.isEmpty())
		{
			for (CoursesBean course : courses)
			{
				String courseId = course.getCourseId();
				if(!courseId.equalsIgnoreCase("Unknown") && !courseId.equalsIgnoreCase("None"))
				{
					checkCourseIdExists(subjects, course, courseId);
				}
			}
		}
	}

	private void checkCourseIdExists(List<SubjectsBean> subjects, CoursesBean course, String courseId)
	{
		boolean found = false;
		for (SubjectsBean subject : subjects)
		{
			if(subject.getCourses().contains(courseId))
			{
				found = true;
				break;
			}
		}
		if(!found)
		{
			LOG.error("Course ID: " + courseId + " has no matching Subject found");
			course.setCourseId("Unknown");
		}
	}

	private void validateDimCourseSubjectLinks(List<DimCoursesBean> dimCourses, List<DimSubjectsBean> dimSubjects)
	{
		if(dimCourses != null && !dimCourses.isEmpty() && dimSubjects != null && !dimSubjects.isEmpty())
		{
			for (DimCoursesBean course : dimCourses)
			{
				String courseId = course.getCourseId();
				if(!courseId.equalsIgnoreCase("Unknown") && !courseId.equalsIgnoreCase("None"))
				{
					checkDimCourseIdExists(dimSubjects, course, courseId);
				}
			}
		}
	}

	private void checkDimCourseIdExists(List<DimSubjectsBean> dimSubjects, DimCoursesBean course, String courseId)
	{
		boolean found = false;
		for (DimSubjectsBean subject : dimSubjects)
		{
			if(subject.getCourses().contains(courseId))
			{
				found = true;
				break;
			}
		}
		if(!found)
		{
			LOG.error("Course ID: " + courseId + " has no matching Subject found");
			course.setCourseId("Unknown");
		}
	}

	private void validateDimModuleCourseLinks(List<DimModulesBean> dimModules, List<DimCoursesBean> dimCourses)
	{
		if(dimModules != null && !dimModules.isEmpty() && dimCourses != null && !dimCourses.isEmpty())
		{
			for (DimModulesBean dimModule : dimModules)
			{
				String moduleId = dimModule.getModuleId();
				if(!moduleId.equalsIgnoreCase("Unknown") && !moduleId.equalsIgnoreCase("None"))
				{
					checkDimModuleIdExists(dimCourses, dimModule, moduleId);
				}
			}
		}
	}

	private void checkDimModuleIdExists(List<DimCoursesBean> dimCourses, DimModulesBean dimModule, String moduleId)
	{
		boolean found = false;
		for (DimCoursesBean course : dimCourses)
		{
			if(course.getModuleIds().contains(moduleId))
			{
				found = true;
				break;
			}
		}
		if(!found)
		{
			LOG.error("Module ID: " + moduleId + " has no matching Course found");
			dimModule.setModuleId("Unknown");
		}
	}
}
