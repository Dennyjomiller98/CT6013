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

	public DWLoadBean convertTotalStudents(List<DimStudentsBean> allStudentsBeans, List<DimEnrollmentsBean> allEnrollmentBeans)
	{
		DWLoadBean ret = new DWLoadBean();
		//TODO - add DWStudentsBean into DWLoadBean
		return ret;
	}

	public DWLoadBean convertTotalDropouts(List<DimStudentsBean> allStudentsBeans, List<DimEnrollmentsBean> allEnrollmentsBeans, List<DimCoursesBean> allCoursesBeans)
	{
		DWLoadBean ret = new DWLoadBean();
		//Loop the enrollments
		if(allEnrollmentsBeans != null && !allEnrollmentsBeans.isEmpty())
		{
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
						bean.setStudentFirstname(matchingStudent.getFirstname());
						bean.setStudentSurname(matchingStudent.getSurname());
						bean.setCourseId(enrollment.getCourseId());
						bean.setCourseName(matchingCourse.getCourseName());
						bean.setEnrollmentDate(enrollment.getEnrollmentDate());
						bean.setHasDropped(enrollment.getHasDropped());
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
