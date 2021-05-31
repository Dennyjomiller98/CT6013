package managers.dataview;

import beans.dw.DWEnrollmentsBean;
import beans.dw.DWLoadBean;
import beans.operational.dimensions.DimCoursesBean;
import beans.operational.dimensions.DimEnrollmentsBean;
import beans.operational.dimensions.DimStudentsBean;
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
}
