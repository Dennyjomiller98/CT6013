package beans;

import java.io.Serializable;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import org.bson.Document;

@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class CourseBean implements Serializable
{
	protected String fCourseCode;
	protected String fCourseName;
	protected String fCourseTutor;
	protected String fCourseStart;
	protected String fCourseEnd;

	public CourseBean()
	{
		//Empty No Args Constructor
	}

	public CourseBean(Document courseDoc)
	{
		fCourseCode = String.valueOf(courseDoc.get("Course_Code"));
		fCourseName = String.valueOf(courseDoc.get("Course_Name"));
		fCourseTutor = String.valueOf(courseDoc.get("Course_Tutor"));
		fCourseStart = String.valueOf(courseDoc.get("Course_Start"));
		fCourseEnd = String.valueOf(courseDoc.get("Course_End"));
	}

	public String getCourseCode()
	{
		return fCourseCode;
	}
	public void setCourseCode(String courseCode)
	{
		fCourseCode = courseCode;
	}

	public String getCourseName()
	{
		return fCourseName;
	}
	public void setCourseName(String courseName)
	{
		fCourseName = courseName;
	}

	public String getCourseTutor()
	{
		return fCourseTutor;
	}
	public void setCourseTutor(String courseTutor)
	{
		fCourseTutor = courseTutor;
	}

	public String getCourseStart()
	{
		return fCourseStart;
	}
	public void setCourseStart(String courseStart)
	{
		fCourseStart = courseStart;
	}

	public String getCourseEnd()
	{
		return fCourseEnd;
	}
	public void setCourseEnd(String courseEnd)
	{
		fCourseEnd = courseEnd;
	}
}
