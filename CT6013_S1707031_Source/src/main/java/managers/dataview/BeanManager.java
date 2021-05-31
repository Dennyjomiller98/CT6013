package managers.dataview;

import beans.dw.DWAssignmentsBean;
import beans.dw.DWEnrollmentsBean;
import beans.dw.DWLoadBean;
import beans.operational.AssignmentsBean;
import beans.operational.dimensions.DimCoursesBean;
import beans.operational.dimensions.DimEnrollmentsBean;
import beans.operational.dimensions.DimStudentsBean;
import beans.operational.dimensions.DimTutorsBean;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * @author Denny-Jo
 * BeanManager - Class used to retrieve DW Dimension Results and Convert the information into the correct DW Bean format
 * */
public class BeanManager
{
	static final Logger LOG = Logger.getLogger(BeanManager.class);

	public BeanManager()
	{
		//Empty Constructor
	}

	/*For Q1*/
	public DWLoadBean convertTotalStudents(List<DimStudentsBean> allStudentsBeans, List<DimEnrollmentsBean> allEnrollmentBeans, List<DimCoursesBean> allCourseBeans)
	{
		DWLoadBean ret = new DWLoadBean();
		if(allStudentsBeans != null && !allStudentsBeans.isEmpty() && allEnrollmentBeans != null && !allEnrollmentBeans.isEmpty())
		{
			//Loop enrollment beans
			for (DimEnrollmentsBean enrollmentBean : allEnrollmentBeans)
			{
				//Filter for enrolled only (excluding dropped out students
				if(enrollmentBean.getIsEnrolled().equals("true") && !enrollmentBean.getHasDropped().equalsIgnoreCase("true"))
				{
					DimStudentsBean matchingStudent = null;
					DimCoursesBean matchingCourse = null;
					String studentId = enrollmentBean.getStudentId();
					for (DimStudentsBean studentBean : allStudentsBeans)
					{
						if(studentBean.getStudentId().equalsIgnoreCase(studentId))
						{
							matchingStudent = studentBean;
						}
					}
					for (DimCoursesBean courseBean : allCourseBeans)
					{
						if(courseBean.getCourseId().equalsIgnoreCase(enrollmentBean.getCourseId()))
						{
							matchingCourse = courseBean;
						}
					}

					//Check matching student exists in DB
					if(matchingStudent != null)
					{
						DWEnrollmentsBean bean = new DWEnrollmentsBean();
						bean.setId(enrollmentBean.getEnrollmentId());
						bean.setStudentId(enrollmentBean.getStudentId());
						bean.setStudentFirstname(matchingStudent.getFirstname());
						bean.setStudentSurname(matchingStudent.getSurname());
						bean.setCourseId(enrollmentBean.getCourseId());
						if(matchingCourse != null)
						{
							bean.setCourseName(matchingCourse.getCourseName());
						}
						else
						{
							bean.setCourseName("Unknown");
						}
						bean.setEnrollmentDate(enrollmentBean.getEnrollmentDate());
						ret.addDWEnrollments(bean);
					}
					else
					{
						LOG.error("No matching information for student");
					}
				}

			}
		}
		return ret;
	}

	/*For Q2*/
	public DWLoadBean convertTotalDropouts(List<DimStudentsBean> allStudentsBeans, List<DimEnrollmentsBean> allEnrollmentsBeans, List<DimCoursesBean> allCoursesBeans)
	{
		DWLoadBean ret = new DWLoadBean();
		if(allEnrollmentsBeans != null && !allEnrollmentsBeans.isEmpty())
		{
			//Loop the enrollments
			for (DimEnrollmentsBean enrollment : allEnrollmentsBeans)
			{
				//Filter for Dropouts ONLY
				if(enrollment.getHasDropped().equalsIgnoreCase("true"))
				{
					//Attempt to find matching Student Dimension info
					DimStudentsBean matchingStudent = null;
					DimCoursesBean matchingCourse = null;
					for (DimStudentsBean studentBean : allStudentsBeans)
					{
						if(enrollment.getStudentId().equalsIgnoreCase(studentBean.getStudentId()))
						{
							matchingStudent = studentBean;
						}
					}
					for (DimCoursesBean courseBean : allCoursesBeans)
					{
						if(courseBean.getCourseId().equalsIgnoreCase(enrollment.getCourseId()))
						{
							matchingCourse = courseBean;
						}
					}

					if(matchingStudent != null)
					{
						//Match, add and return (for adding to session to display to user)
						DWEnrollmentsBean bean = new DWEnrollmentsBean();
						bean.setId(enrollment.getEnrollmentId());
						bean.setStudentId(enrollment.getStudentId());
						bean.setEnrollmentDate(enrollment.getEnrollmentDate());
						bean.setHasDropped(enrollment.getHasDropped());
						bean.setCourseId(enrollment.getCourseId());
						bean.setStudentFirstname(matchingStudent.getFirstname());
						bean.setStudentSurname(matchingStudent.getSurname());
						if (matchingCourse != null)
						{
							bean.setCourseName(matchingCourse.getCourseName());
						}
						else
						{
							bean.setCourseName("Unknown");
						}
						ret.addDWEnrollments(bean);
					}
					else
					{
						LOG.error("No matching information for student");
					}
				}
			}
		}
		return ret;
	}

	/*For Q3*/
	public DWLoadBean convertAverageGrade(List<AssignmentsBean> allGrades, List<DimStudentsBean> allStudentsBeans, List<DimCoursesBean> allCoursesBeans)
	{
		DWLoadBean ret = new DWLoadBean();
		if(allGrades != null && !allGrades.isEmpty())
		{
			//Loop the results
			for (AssignmentsBean assignmentBean : allGrades)
			{
				//Get matching student and course
				DimStudentsBean matchingStudent = null;
				DimCoursesBean matchingCourse = null;
				for (DimStudentsBean studentBean : allStudentsBeans)
				{
					if(assignmentBean.getStudentId().equalsIgnoreCase(studentBean.getStudentId()))
					{
						matchingStudent = studentBean;
					}
				}
				for (DimCoursesBean courseBean : allCoursesBeans)
				{
					if(courseBean.getModuleIds() != null && courseBean.getModuleIds().contains(assignmentBean.getModule()))
					{
						matchingCourse = courseBean;
					}
				}

				DWAssignmentsBean bean = new DWAssignmentsBean();
				bean.setAssignmentId(assignmentBean.getAssignmentId());
				bean.setStudentId(assignmentBean.getStudentId());
				if(matchingStudent != null)
				{
					bean.setStudentFirstname(matchingStudent.getFirstname());
					bean.setStudentSurname(matchingStudent.getSurname());
				}
				else
				{
					bean.setStudentFirstname("Unknown");
					bean.setStudentSurname("Unknown");
				}
				bean.setAcademicYear(assignmentBean.getAcademicYear());
				bean.setModule(assignmentBean.getModule());
				bean.setSemester(assignmentBean.getSemester());
				bean.setGrade(assignmentBean.getGrade());
				bean.setResit(assignmentBean.getResit());
				bean.setResitGrade(assignmentBean.getResitGrade());
				if (matchingCourse != null)
				{
					bean.setCourseId(matchingCourse.getCourseId());
					bean.setCourseName(matchingCourse.getCourseName());
				}
				else
				{
					bean.setCourseId("Unknown");
					bean.setCourseName("Unknown");
				}
				ret.addDWAssignments(bean);
			}
		}
		return ret;
	}

	/*For Q4*/
	public DWLoadBean convertGradeAgainstTutor(List<AssignmentsBean> allGrades, List<DimStudentsBean> allStudentsBeans, List<DimCoursesBean> allCoursesBeans, List<DimTutorsBean> allTutorsBeans)
	{
		DWLoadBean ret = new DWLoadBean();
		if(allGrades != null && !allGrades.isEmpty())
		{
			//Loop the results
			for (AssignmentsBean assignmentBean : allGrades)
			{
				//Get matching student and course
				DimStudentsBean matchingStudent = null;
				DimCoursesBean matchingCourse = null;
				DimTutorsBean matchingTutor = null;
				for (DimStudentsBean studentBean : allStudentsBeans)
				{
					if(assignmentBean.getStudentId().equalsIgnoreCase(studentBean.getStudentId()))
					{
						matchingStudent = studentBean;
					}
				}
				for (DimCoursesBean courseBean : allCoursesBeans)
				{
					if(courseBean.getModuleIds() != null && courseBean.getModuleIds().contains(assignmentBean.getModule()))
					{
						matchingCourse = courseBean;
					}
				}
				for (DimTutorsBean tutorBean : allTutorsBeans)
				{
					if(tutorBean.getTutorId() != null && tutorBean.getTutorId().equals(assignmentBean.getTutorId()))
					{
						matchingTutor = tutorBean;
					}
				}

				DWAssignmentsBean bean = new DWAssignmentsBean();
				bean.setAssignmentId(assignmentBean.getAssignmentId());
				bean.setStudentId(assignmentBean.getStudentId());
				if(matchingStudent != null)
				{
					bean.setStudentFirstname(matchingStudent.getFirstname());
					bean.setStudentSurname(matchingStudent.getSurname());
				}
				else
				{
					bean.setStudentFirstname("Unknown");
					bean.setStudentSurname("Unknown");
				}

				if(matchingTutor != null)
				{
					bean.setTutorId(assignmentBean.getTutorId());
					bean.setTutorFirstname(matchingTutor.getFirstname());
					bean.setTutorSurname(matchingTutor.getSurname());
				}
				else
				{
					bean.setTutorId("Unknown");
					bean.setTutorFirstname("Unknown");
					bean.setTutorSurname("Unknown");
				}

				bean.setAcademicYear(assignmentBean.getAcademicYear());
				bean.setModule(assignmentBean.getModule());
				bean.setSemester(assignmentBean.getSemester());
				bean.setGrade(assignmentBean.getGrade());
				bean.setResit(assignmentBean.getResit());
				bean.setResitGrade(assignmentBean.getResitGrade());
				if (matchingCourse != null)
				{
					bean.setCourseId(matchingCourse.getCourseId());
					bean.setCourseName(matchingCourse.getCourseName());
				}
				else
				{
					bean.setCourseId("Unknown");
					bean.setCourseName("Unknown");
				}
				ret.addDWAssignments(bean);
			}
		}
		return ret;
	}

	/*For Q5*/
	public DWLoadBean convertTotalResits(List<AssignmentsBean> dwResults, List<DimStudentsBean> allStudentBeans, List<DimCoursesBean> allCourses)
	{
		DWLoadBean ret = new DWLoadBean();
		if(dwResults != null && !dwResults.isEmpty())
		{
			//Loop the results
			for (AssignmentsBean assignmentBean : dwResults)
			{
				//Get matching student and course
				DimStudentsBean matchingStudent = null;
				DimCoursesBean matchingCourse = null;
				for (DimStudentsBean studentBean : allStudentBeans)
				{
					if(assignmentBean.getStudentId().equalsIgnoreCase(studentBean.getStudentId()))
					{
						matchingStudent = studentBean;
					}
				}
				for (DimCoursesBean courseBean : allCourses)
				{
					if(courseBean.getModuleIds() != null && courseBean.getModuleIds().contains(assignmentBean.getModule()))
					{
						matchingCourse = courseBean;
					}
				}

				DWAssignmentsBean bean = new DWAssignmentsBean();
				bean.setAssignmentId(assignmentBean.getAssignmentId());
				bean.setStudentId(assignmentBean.getStudentId());
				if(matchingStudent != null)
				{
					bean.setStudentFirstname(matchingStudent.getFirstname());
					bean.setStudentSurname(matchingStudent.getSurname());
				}
				else
				{
					bean.setStudentFirstname("Unknown");
					bean.setStudentSurname("Unknown");
				}

				bean.setAcademicYear(assignmentBean.getAcademicYear());
				bean.setModule(assignmentBean.getModule());
				bean.setSemester(assignmentBean.getSemester());
				bean.setGrade(assignmentBean.getGrade());
				bean.setResit(assignmentBean.getResit());
				bean.setResitGrade(assignmentBean.getResitGrade());
				if (matchingCourse != null)
				{
					bean.setCourseId(matchingCourse.getCourseId());
					bean.setCourseName(matchingCourse.getCourseName());
				}
				else
				{
					bean.setCourseId("Unknown");
					bean.setCourseName("Unknown");
				}
				ret.addDWAssignments(bean);
			}
		}
		return ret;
	}

	/*For Q6 and Q7*/
	public DWLoadBean convertTotalEnrollments(List<DimStudentsBean> allStudentsBeans, List<DimEnrollmentsBean> allEnrollmentBeans, List<DimCoursesBean> allCourses)
	{
		DWLoadBean ret = new DWLoadBean();
		if(allStudentsBeans != null && !allStudentsBeans.isEmpty() && allEnrollmentBeans != null && !allEnrollmentBeans.isEmpty())
		{
			//Loop enrollment beans
			for (DimEnrollmentsBean enrollmentBean : allEnrollmentBeans)
			{
				//Filter for enrolled only
				if(enrollmentBean.getIsEnrolled().equals("true"))
				{
					DimStudentsBean matchingStudent = null;
					DimCoursesBean matchingCourse = null;
					String studentId = enrollmentBean.getStudentId();
					for (DimStudentsBean studentBean : allStudentsBeans)
					{
						if(studentBean.getStudentId().equalsIgnoreCase(studentId))
						{
							matchingStudent = studentBean;
						}
					}
					for (DimCoursesBean courseBean : allCourses)
					{
						if(courseBean.getCourseId().equalsIgnoreCase(enrollmentBean.getCourseId()))
						{
							matchingCourse = courseBean;
						}
					}

					//Check matching student exists in DB
					if(matchingStudent != null)
					{
						DWEnrollmentsBean bean = new DWEnrollmentsBean();
						bean.setId(enrollmentBean.getEnrollmentId());
						bean.setStudentId(enrollmentBean.getStudentId());
						bean.setStudentFirstname(matchingStudent.getFirstname());
						bean.setStudentSurname(matchingStudent.getSurname());
						bean.setCourseId(enrollmentBean.getCourseId());
						if(matchingCourse != null)
						{
							bean.setCourseName(matchingCourse.getCourseName());
						}
						else
						{
							bean.setCourseName("Unknown");
						}
						bean.setEnrollmentDate(enrollmentBean.getEnrollmentDate());

						//Loop the values we have to make sure 2nd SCD data is not duplicating.
						List<DWEnrollmentsBean> currentEnrollments = ret.getDWEnrollments();

						String badEnrollmentId = null;
						if(currentEnrollments != null && !currentEnrollments.isEmpty())
						{
							for (DWEnrollmentsBean dwEnrollment : currentEnrollments)
							{
								//Take most up to date bean
								if (dwEnrollment.getId().equals(bean.getId()) && enrollmentBean.getIsCurrent().equals("true"))
								{
									//Replace with existing one
									badEnrollmentId = dwEnrollment.getId();
								}
							}
						}
						if(badEnrollmentId != null)
						{
							List<DWEnrollmentsBean> newBeans = new ArrayList<>();
							for (DWEnrollmentsBean enrollment : currentEnrollments)
							{
								if(!enrollment.getId().equals(badEnrollmentId))
								{
									newBeans.add(enrollment);
								}
							}
							//Now add new bean and send to DWLoadBean
							newBeans.add(bean);
							ret.setDWEnrollments(newBeans);
						}
						else
						{
							ret.addDWEnrollments(bean);
						}
					}
					else
					{
						LOG.error("No matching information for student");
					}
				}

			}
		}
		return ret;
	}

	/*For Q8*/
	public DWLoadBean convertTotalInternationalStudents(List<AssignmentsBean> dwResults, List<DimStudentsBean> allStudentsBeans, List<DimEnrollmentsBean> allEnrollmentBeans, List<DimCoursesBean> allCourseBeans)
	{
		DWLoadBean ret = new DWLoadBean();
		if(allStudentsBeans != null && !allStudentsBeans.isEmpty() && allEnrollmentBeans != null && !allEnrollmentBeans.isEmpty())
		{
			//Loop enrollment beans
			for (DimEnrollmentsBean enrollmentBean : allEnrollmentBeans)
			{
				//Filter for enrolled international only
				if(enrollmentBean.getIsEnrolled().equals("true") && !enrollmentBean.getHasDropped().equalsIgnoreCase("true"))
				{
					DimStudentsBean matchingStudent = null;
					DimCoursesBean matchingCourse = null;
					String studentId = enrollmentBean.getStudentId();
					for (DimStudentsBean studentBean : allStudentsBeans)
					{
						if(studentBean.getStudentId().equalsIgnoreCase(studentId))
						{
							matchingStudent = studentBean;
						}
					}
					for (DimCoursesBean courseBean : allCourseBeans)
					{
						if(courseBean.getCourseId().equalsIgnoreCase(enrollmentBean.getCourseId()))
						{
							matchingCourse = courseBean;
						}
					}

					//Check matching student exists in DB
					if(matchingStudent != null)
					{
						for (AssignmentsBean assignmentsBean : dwResults)
						{
							if(assignmentsBean.getStudentId().equals(matchingStudent.getStudentId()) && assignmentsBean.getInternational().equals("true"))
							{
								DWEnrollmentsBean bean = new DWEnrollmentsBean();
								bean.setId(enrollmentBean.getEnrollmentId());
								bean.setStudentId(enrollmentBean.getStudentId());
								bean.setStudentFirstname(matchingStudent.getFirstname());
								bean.setStudentSurname(matchingStudent.getSurname());
								bean.setCourseId(enrollmentBean.getCourseId());
								if(matchingCourse != null)
								{
									bean.setCourseName(matchingCourse.getCourseName());
								}
								else
								{
									bean.setCourseName("Unknown");
								}
								bean.setEnrollmentDate(enrollmentBean.getEnrollmentDate());
								bean.setInternational("true");
								ret.addDWEnrollments(bean);
							}
						}
					}
					else
					{
						LOG.error("No matching information for student");
					}
				}

			}
		}
		return ret;
	}
}
