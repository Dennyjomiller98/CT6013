package etl;

import beans.operational.AssignmentsBean;
import java.util.ArrayList;
import java.util.List;

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
}
