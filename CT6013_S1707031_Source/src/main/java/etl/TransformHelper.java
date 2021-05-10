package etl;

import beans.operational.*;
import beans.operational.dimensions.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Denny-Jo
 * TransformHelper Class - Used to help ensure the data retrieved from the Operational Database Sources are non-null and presented in the Correct Format.
 * Date/Timestamps are updated to be consistent (if not already), and empty values are replaced to ensure SQL queries do not fail when querying the DW.
 *
 * Note: If a value can be Nullable (E.g. Resit grades) and does not have a value from the Operational data, it's Transformed value will be "None".
 * Note: If a value can't be Nullable (E.g. ID's) and does not have a value from the Operational data, it's Transformed value will be "Unknown".
 *
 * TransformHelper is called after ExtractHelper retrieves the OP data, and before LoadHelper prepares the data for DW load.
 * */
public class TransformHelper
{
	public TransformHelper()
	{
		//Empty Constructor
	}

	public List<AssignmentsBean> transformAssignmentsData(List<AssignmentsBean> beans)
	{
		List<AssignmentsBean> updatedBeans = new ArrayList<>();
		if(!beans.isEmpty())
		{
			for (AssignmentsBean assignmentRowBean : beans)
			{
				AssignmentsBean transformedData = transformAssignmentRow(assignmentRowBean);
				//If returned value is null, there is no data to add
				if (transformedData != null)
				{
					updatedBeans.add(transformedData);
				}
			}
		}
		return updatedBeans;
	}

	public List<CoursesBean> transformCoursesData(List<CoursesBean> beans)
	{
		List<CoursesBean> updatedBeans = new ArrayList<>();
		if(!beans.isEmpty())
		{
			for (CoursesBean coursesBean : beans)
			{
				CoursesBean transformedData = transformCourseRow(coursesBean);
				//If returned value is null, there is no data to add
				if (transformedData != null)
				{
					updatedBeans.add(transformedData);
				}
			}
		}
		return updatedBeans;
	}

	public List<EnrollmentsBean> transformEnrollmentsData(List<EnrollmentsBean> beans)
	{
		List<EnrollmentsBean> updatedBeans = new ArrayList<>();
		if(!beans.isEmpty())
		{
			for (EnrollmentsBean enrollmentsBean : beans)
			{
				EnrollmentsBean transformedData = transformEnrollmentRow(enrollmentsBean);
				//If returned value is null, there is no data to add
				if (transformedData != null)
				{
					updatedBeans.add(transformedData);
				}
			}
		}
		return updatedBeans;
	}

	public List<ModulesBean> transformModulesData(List<ModulesBean> beans)
	{
		List<ModulesBean> updatedBeans = new ArrayList<>();
		if(!beans.isEmpty())
		{
			for (ModulesBean modulesBean : beans)
			{
				ModulesBean transformedData = transformModuleRow(modulesBean);
				//If returned value is null, there is no data to add
				if (transformedData != null)
				{
					updatedBeans.add(transformedData);
				}
			}
		}
		return updatedBeans;
	}

	public List<StudentsBean> transformStudentsData(List<StudentsBean> beans)
	{
		List<StudentsBean> updatedBeans = new ArrayList<>();
		if(!beans.isEmpty())
		{
			for (StudentsBean studentsBean : beans)
			{
				StudentsBean transformedData = transformStudentRow(studentsBean);
				//If returned value is null, there is no data to add
				if (transformedData != null)
				{
					updatedBeans.add(transformedData);
				}
			}
		}
		return updatedBeans;
	}

	public List<SubjectsBean> transformSubjectsData(List<SubjectsBean> beans)
	{
		List<SubjectsBean> updatedBeans = new ArrayList<>();
		if(!beans.isEmpty())
		{
			for (SubjectsBean subjectsBean : beans)
			{
				SubjectsBean transformedData = transformSubjectRow(subjectsBean);
				//If returned value is null, there is no data to add
				if (transformedData != null)
				{
					updatedBeans.add(transformedData);
				}
			}
		}
		return updatedBeans;
	}

	public List<TutorsBean> transformTutorsData(List<TutorsBean> beans)
	{
		List<TutorsBean> updatedBeans = new ArrayList<>();
		if(!beans.isEmpty())
		{
			for (TutorsBean tutorsBean : beans)
			{
				TutorsBean transformedData = transformTutorRow(tutorsBean);
				//If returned value is null, there is no data to add
				if (transformedData != null)
				{
					updatedBeans.add(transformedData);
				}
			}
		}
		return updatedBeans;
	}

	public List<DimCoursesBean> transformDimCoursesData(List<DimCoursesBean> beans)
	{
		List<DimCoursesBean> updatedBeans = new ArrayList<>();
		if(!beans.isEmpty())
		{
			for (DimCoursesBean dimCoursesBean : beans)
			{
				DimCoursesBean transformedData = transformDimCourseRow(dimCoursesBean);
				//If returned value is null, there is no data to add
				if (transformedData != null)
				{
					updatedBeans.add(transformedData);
				}
			}
		}
		return updatedBeans;
	}

	public List<DimEnrollmentsBean> transformDimEnrollmentsData(List<DimEnrollmentsBean> beans)
	{
		List<DimEnrollmentsBean> updatedBeans = new ArrayList<>();
		if(!beans.isEmpty())
		{
			for (DimEnrollmentsBean dimEnrollmentsBean : beans)
			{
				DimEnrollmentsBean transformedData = transformDimEnrollmentRow(dimEnrollmentsBean);
				//If returned value is null, there is no data to add
				if (transformedData != null)
				{
					updatedBeans.add(transformedData);
				}
			}
		}
		return updatedBeans;
	}

	public List<DimModulesBean> transformDimModulesData(List<DimModulesBean> beans)
	{
		List<DimModulesBean> updatedBeans = new ArrayList<>();
		if(!beans.isEmpty())
		{
			for (DimModulesBean dimModulesBean : beans)
			{
				DimModulesBean transformedData = transformDimModuleRow(dimModulesBean);
				//If returned value is null, there is no data to add
				if (transformedData != null)
				{
					updatedBeans.add(transformedData);
				}
			}
		}
		return updatedBeans;
	}

	public List<DimStudentsBean> transformDimStudentsData(List<DimStudentsBean> beans)
	{
		List<DimStudentsBean> updatedBeans = new ArrayList<>();
		if(!beans.isEmpty())
		{
			for (DimStudentsBean dimStudentsBean : beans)
			{
				DimStudentsBean transformedData = transformDimStudentRow(dimStudentsBean);
				//If returned value is null, there is no data to add
				if (transformedData != null)
				{
					updatedBeans.add(transformedData);
				}
			}
		}
		return updatedBeans;
	}

	public List<DimSubjectsBean> transformDimSubjectsData(List<DimSubjectsBean> beans)
	{
		List<DimSubjectsBean> updatedBeans = new ArrayList<>();
		if(!beans.isEmpty())
		{
			for (DimSubjectsBean dimSubjectsBean : beans)
			{
				DimSubjectsBean transformedData = transformDimSubjectRow(dimSubjectsBean);
				//If returned value is null, there is no data to add
				if (transformedData != null)
				{
					updatedBeans.add(transformedData);
				}
			}
		}
		return updatedBeans;
	}

	public List<DimTutorsBean> transformDimTutorsData(List<DimTutorsBean> beans)
	{
		List<DimTutorsBean> updatedBeans = new ArrayList<>();
		if(!beans.isEmpty())
		{
			for (DimTutorsBean dimTutorsBean : beans)
			{
				DimTutorsBean transformedData = transformDimTutorRow(dimTutorsBean);
				//If returned value is null, there is no data to add
				if (transformedData != null)
				{
					updatedBeans.add(transformedData);
				}
			}
		}
		return updatedBeans;
	}

	private AssignmentsBean transformAssignmentRow(AssignmentsBean assignmentRowBean)
	{
		AssignmentsBean transformedBean;
		if(assignmentRowBean != null)
		{
			transformedBean = new AssignmentsBean();
			String assignmentId = assignmentRowBean.getAssignmentId();
			if(assignmentId != null)
			{
				transformedBean.setAssignmentId(assignmentId);
			}
			else
			{
				transformedBean.setAssignmentId("Unknown");
			}
			String studentId = assignmentRowBean.getStudentId();
			if(studentId != null)
			{
				transformedBean.setStudentId(studentId);
			}
			else
			{
				transformedBean.setStudentId("Unknown");
			}
			String academicYear = assignmentRowBean.getAcademicYear();
			if(academicYear != null)
			{
				transformedBean.setAcademicYear(academicYear);
			}
			else
			{
				transformedBean.setAcademicYear("Unknown");
			}
			String module = assignmentRowBean.getModule();
			if(module != null)
			{
				transformedBean.setModule(module);
			}
			else
			{
				transformedBean.setModule("Unknown");
			}
			String semester = assignmentRowBean.getSemester();
			if(semester != null)
			{
				transformedBean.setSemester(semester);
			}
			else
			{
				transformedBean.setSemester("Unknown");
			}
			String grade = assignmentRowBean.getGrade();
			if(grade != null)
			{
				transformedBean.setGrade(grade);
			}
			else
			{
				transformedBean.setGrade("Unknown");
			}
			String resit = assignmentRowBean.getResit();
			if(resit != null)
			{
				transformedBean.setResit(resit);
			}
			else
			{
				transformedBean.setResit("Unknown");
			}
			String resitGrade = assignmentRowBean.getResitGrade();
			if(resitGrade != null)
			{
				//Only add in resit grade if resit is true
				if(!transformedBean.getResit().equalsIgnoreCase("True"))
				{
					transformedBean.setResitGrade(resitGrade);
				}
				else
				{
					transformedBean.setResitGrade("None");
				}
			}
			else
			{
				transformedBean.setResitGrade("Unknown");
			}
		}
		else
		{
			transformedBean = null;
		}
		return transformedBean;
	}

	private CoursesBean transformCourseRow(CoursesBean bean)
	{
		CoursesBean transformedBean;
		if(bean != null)
		{
			transformedBean = new CoursesBean();
			String courseId = bean.getCourseId();
			if(courseId != null)
			{
				transformedBean.setCourseId(courseId);
			}
			else
			{
				transformedBean.setCourseId("Unknown");
			}
			String subjectId = bean.getSubjectId();
			if(subjectId != null)
			{
				transformedBean.setSubjectId(subjectId);
			}
			else
			{
				transformedBean.setSubjectId("Unknown");
			}
			String courseName = bean.getCourseName();
			if(courseName != null)
			{
				transformedBean.setCourseName(courseName);
			}
			else
			{
				transformedBean.setCourseName("Unknown");
			}
			String moduleIdsCsv = bean.getModuleIds();
			if(moduleIdsCsv != null)
			{
				transformedBean.setModuleIds(moduleIdsCsv);
			}
			else
			{
				transformedBean.setModuleIds("None");
			}
			String tutor = bean.getCourseTutor();
			if(tutor != null)
			{
				transformedBean.setCourseTutor(tutor);
			}
			else
			{
				transformedBean.setCourseTutor("Unknown");
			}
		}
		else
		{
			transformedBean = null;
		}
		return transformedBean;
	}

	private EnrollmentsBean transformEnrollmentRow(EnrollmentsBean bean)
	{
		EnrollmentsBean transformedBean;
		if(bean != null)
		{
			transformedBean = new EnrollmentsBean();
			//TODO -
		}
		else
		{
			transformedBean = null;
		}
		return transformedBean;
	}

	private ModulesBean transformModuleRow(ModulesBean bean)
	{
		ModulesBean transformedBean;
		if(bean != null)
		{
			transformedBean = new ModulesBean();
			//TODO -
		}
		else
		{
			transformedBean = null;
		}
		return transformedBean;
	}

	private StudentsBean transformStudentRow(StudentsBean bean)
	{
		StudentsBean transformedBean;
		if(bean != null)
		{
			transformedBean = new StudentsBean();
			//TODO -
		}
		else
		{
			transformedBean = null;
		}
		return transformedBean;
	}

	private SubjectsBean transformSubjectRow(SubjectsBean bean)
	{
		SubjectsBean transformedBean;
		if(bean != null)
		{
			transformedBean = new SubjectsBean();
			//TODO -
		}
		else
		{
			transformedBean = null;
		}
		return transformedBean;
	}

	private TutorsBean transformTutorRow(TutorsBean bean)
	{
		TutorsBean transformedBean;
		if(bean != null)
		{
			transformedBean = new TutorsBean();
			//TODO -
		}
		else
		{
			transformedBean = null;
		}
		return transformedBean;
	}

	private DimCoursesBean transformDimCourseRow(DimCoursesBean bean)
	{
		DimCoursesBean transformedBean;
		if(bean != null)
		{
			transformedBean = new DimCoursesBean();
			//TODO -
		}
		else
		{
			transformedBean = null;
		}
		return transformedBean;
	}

	private DimEnrollmentsBean transformDimEnrollmentRow(DimEnrollmentsBean bean)
	{
		DimEnrollmentsBean transformedBean;
		if(bean != null)
		{
			transformedBean = new DimEnrollmentsBean();
			//TODO -
		}
		else
		{
			transformedBean = null;
		}
		return transformedBean;
	}

	private DimModulesBean transformDimModuleRow(DimModulesBean bean)
	{
		DimModulesBean transformedBean;
		if(bean != null)
		{
			transformedBean = new DimModulesBean();
			//TODO -
		}
		else
		{
			transformedBean = null;
		}
		return transformedBean;
	}

	private DimStudentsBean transformDimStudentRow(DimStudentsBean bean)
	{
		DimStudentsBean transformedBean;
		if(bean != null)
		{
			transformedBean = new DimStudentsBean();
			//TODO -
		}
		else
		{
			transformedBean = null;
		}
		return transformedBean;
	}

	private DimSubjectsBean transformDimSubjectRow(DimSubjectsBean bean)
	{
		DimSubjectsBean transformedBean;
		if(bean != null)
		{
			transformedBean = new DimSubjectsBean();
			//TODO -
		}
		else
		{
			transformedBean = null;
		}
		return transformedBean;
	}

	private DimTutorsBean transformDimTutorRow(DimTutorsBean bean)
	{
		DimTutorsBean transformedBean;
		if(bean != null)
		{
			transformedBean = new DimTutorsBean();
			//TODO -
		}
		else
		{
			transformedBean = null;
		}
		return transformedBean;
	}
}
