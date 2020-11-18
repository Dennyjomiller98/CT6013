package oracle;

import beans.CourseBean;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class CourseConnections extends AbstractOracleConnections
{
	public CourseConnections()
	{
		//Empty Constructor
	}

	public void addCourse(Document course)
	{
		LOG.debug("Beginning Course Addition to Oracle DB");
		//Create Mongo Client, access DB and Retrieve Collection to insert new course data into DB

		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if(oracleClient != null)
			{
				//Add Course to DB
				String values = "'" +course.getString("Course_Code")
						+ "','" + course.getString("Course_Name")
						+ "','" + course.getString("Course_Tutor")
						+ "','" + course.getString("Course_Start")
						+ "','" + course.getString("Course_End") + "'";
				String query = "INSERT INTO " + COURSES_COLLECTION +
						"(Course_Code, Course_Name, Course_Tutor, Course_Start, Course_End)" + " VALUES (" + values + ")";

				//Execute query
				executeCourseUpdateQuery(oracleClient, query);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to add course to Oracle", e);
		}
	}

	private void executeCourseUpdateQuery(Connection oracleClient, String query) throws SQLException
	{
		try (Statement statement = oracleClient.createStatement())
		{
			statement.executeUpdate(query);
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();
	}

	public List<CourseBean> retrieveAllCourses()
	{
		ArrayList<CourseBean> allCourses = new ArrayList<>();

		LOG.debug("Beginning all Courses retrieval from Oracle DB");
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + COURSES_COLLECTION;

				//Execute query
				allCoursesRetrievalQuery(oracleClient, query, allCourses);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to retrieve all courses from Oracle", e);
		}
		return allCourses;
	}

	private void allCoursesRetrievalQuery(Connection oracleClient, String query, ArrayList<CourseBean> allCourses) throws SQLException
	{
		try (Statement statement = oracleClient.createStatement())
		{
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
			{
				CourseBean bean = new CourseBean();
				bean.setCourseCode(resultSet.getString("Course_Code"));
				bean.setCourseName(resultSet.getString("Course_Name"));
				bean.setCourseTutor(resultSet.getString("Course_Tutor"));
				bean.setCourseStart(resultSet.getString("Course_Start"));
				bean.setCourseEnd(resultSet.getString("Course_End"));

				//Add bean to list of students
				allCourses.add(bean);
			}
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();
	}

	public CourseBean retrieveSingleCourse(String courseCode)
	{
		LOG.debug("Attempting Retrieval of single course with course code: " + courseCode);
		CourseBean beanToReturn = null;
		List<CourseBean> courseBeans = retrieveAllCourses();
		for (CourseBean courseBean : courseBeans)
		{
			if(courseBean.getCourseCode().equalsIgnoreCase(courseCode))
			{
				beanToReturn = courseBean;
			}
		}
		return beanToReturn;
	}

	public void updateCourse(Document courseToUpdate, String courseCode)
	{
		LOG.debug("Attempting to update Course");
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if (oracleClient != null)
			{
				String query = "UPDATE " + COURSES_COLLECTION + " SET Course_Code ='" + courseToUpdate.getString("COURSE_CODE")
						+"', Course_Name='"+ courseToUpdate.getString("COURSE_NAME")
						+"', Course_Tutor='"+ courseToUpdate.getString("COURSE_TUTOR")
						+"', DOB='" + courseToUpdate.getString("COURSE_START")
						+"', Address='"+ courseToUpdate.getString("COURSE_END")
						+"' WHERE COURSE_CODE='"+ courseCode +"'";

				executeCourseUpdateQuery(oracleClient, query);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to update course information in oracle DB");
		}
	}
}
