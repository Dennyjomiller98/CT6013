package mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import beans.TeacherBean;
import org.bson.Document;
import org.bson.conversions.Bson;
import servlets.users.PasswordEncryptDecrypt;

public class TeacherConnections extends AbstractMongoDBConnections
{
	public TeacherConnections()
	{
		//Empty Constructor
	}

	public void registerTeacherToDB(Document teacher)
	{
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

	public TeacherBean retrieveSingleTeacher(String teacherEmail)
	{
		TeacherBean beanToReturn = null;
		List<TeacherBean> allTeacherBeans = retrieveAllTeachers();
		for (TeacherBean allTeacherBean : allTeacherBeans)
		{
			if (allTeacherBean.getEmail().equalsIgnoreCase(teacherEmail))
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
		String hashedPassword = PasswordEncryptDecrypt.encryptPasswordToStore(password);
		if (potentialTeacher != null && hashedPassword.equals(potentialTeacher.getPassword()))
		{
			isCorrectCredentials = true;
		}
		return isCorrectCredentials;
	}

	public void updateTeacherDetails(Document teacherToUpdate, String email)
	{
		LOG.debug("attempting to update teacher details");
		//Store values into the DB
		try (MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT))
		{
			MongoDatabase db = mongo.getDatabase(DBNAME);
			MongoCollection<Document> collection = db.getCollection(TEACHERS_COLLECTION);
			Bson bson = Filters.eq("Email", email);
			collection.replaceOne(bson, teacherToUpdate);
		} catch (Exception e)
		{
			LOG.error("Error Occurred during Teacher Update", e);
		}
	}

	public void deleteTeacher(String teacherEmail)
	{
		LOG.debug("attempting to delete teacher details of teacher email: " + teacherEmail);
		try (MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT))
		{
			MongoDatabase db = mongo.getDatabase(DBNAME);
			MongoCollection<Document> collection = db.getCollection(TEACHERS_COLLECTION);
			Bson bson = Filters.eq("Email", teacherEmail);
			collection.findOneAndDelete(bson);
		} catch (Exception e)
		{
			LOG.error("Error Occurred during teacher Deletion", e);
		}
	}
}
