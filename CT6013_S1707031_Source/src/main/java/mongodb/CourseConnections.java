package mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import mongodbbeans.CourseBean;
import mongodbbeans.StudentBean;
import org.bson.Document;

public class CourseConnections extends AbstractMongoDBConnections
{
	public CourseConnections()
	{
		//Empty Constructor
	}

	public void addCourse(Document course)
	{
		//Attempt to add course Document into database
		LOG.debug("Attempting to add Course into Database: " + course);
		try(MongoClient mongoClient = new MongoClient(MONGO_HOST, MONGO_PORT))
		{
			MongoDatabase db = mongoClient.getDatabase(DBNAME);
			MongoCollection<Document> collection = db.getCollection(COURSES_COLLECTION);
			collection.insertOne(course);
		}
		catch (Exception e)
		{
			LOG.debug("An error occurred during Course addition", e);
		}
	}

	public List<CourseBean> retrieveAllCourses()
	{
		ArrayList<CourseBean> allCourses = new ArrayList<>();
		try (MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT))
		{
			MongoDatabase db = mongo.getDatabase(DBNAME);
			MongoCollection<Document> allRetrievedDocuments = db.getCollection(COURSES_COLLECTION);
			FindIterable<Document> documents = allRetrievedDocuments.find();
			for (Document aCourse : documents)
			{
				CourseBean sBean = new CourseBean(aCourse);
				allCourses.add(sBean);
			}
			LOG.debug("All courses retrieved, returning value of: " + allCourses);
		}
		catch (Exception e)
		{
			LOG.error("Error Occurred during retrieval of all Courses", e);
		}
		return allCourses;
	}

	public CourseBean retrieveSingleCourse(String courseCode)
	{
		CourseBean beanToReturn = null;
		LOG.debug("Retrieving Course with code: " + courseCode);
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

	public void updateCourse(Document CourseToUpdate, String CourseCode)
	{

	}
}
