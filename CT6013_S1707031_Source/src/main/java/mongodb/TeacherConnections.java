package mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import mongodbbeans.TeacherBean;
import org.bson.Document;

public class TeacherConnections extends AbstractMongoDBConnections
{
	public TeacherConnections()
	{
		//Empty Constructor
	}

	public void registerTeacherToDB(Document teacher) {
		LOG.debug("Beginning Teacher Registration");
		//Create Mongo Client, access DB and Retrieve Collection to insert new teacher Document into
		try (MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT))
		{
			MongoDatabase db = mongo.getDatabase(DBNAME);
			MongoCollection<Document> collection = db.getCollection(TEACHERS_COLLECTION);
			collection.insertOne(teacher);
		} catch (Exception e)
		{
			LOG.error("Error Occurred during teacher Registration", e);
		}
	}

	public List<TeacherBean> retrieveAllTeachers()
	{
		ArrayList<TeacherBean> allTeachers = new ArrayList<>();
		try (MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT))
		{
			MongoDatabase db = mongo.getDatabase(DBNAME);
			MongoCollection<Document> collection = db.getCollection(TEACHERS_COLLECTION);
			FindIterable<Document> allRetrievedDocuments = collection.find();
			for (Document aTeacher : allRetrievedDocuments) {
				TeacherBean sBean = new TeacherBean(aTeacher);
				allTeachers.add(sBean);
			}
			LOG.debug("All teachers retrieved, returning value of: " + allTeachers);
		}
		catch (Exception e)
		{
			LOG.error("Error Occurred during Teacher Registration", e);
		}
		return allTeachers;
	}

	public TeacherBean retrieveSingleTeacher(String teacherID)
	{
		TeacherBean beanToReturn = null;
		List<TeacherBean> allTeacherBeans = retrieveAllTeachers();
		for (TeacherBean allTeacherBean : allTeacherBeans)
		{
			if (allTeacherBean.getTeacherID().equals(teacherID))
			{
				beanToReturn = allTeacherBean;
			}
		}
		return beanToReturn;
	}

	public boolean attemptLogin(TeacherBean teacherBean)
	{
		//Find the associated email in DB and check login credentials are correct
		boolean isCorrectCredentials = false;
		String email = teacherBean.getEmail();
		String password = teacherBean.getPassword();

		TeacherBean potentialTeacher = retrieveSingleTeacher(email);
		//Email will match at this point, only need to assert Password value to email to authenticate login
		if(password.equals(potentialTeacher.getPassword()))
		{
			isCorrectCredentials = true;
		}
		return isCorrectCredentials;
	}
}
