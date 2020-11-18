package mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import beans.EnrollmentBean;
import org.bson.Document;
import org.bson.conversions.Bson;

public class EnrollmentConnections extends AbstractMongoDBConnections
{
	public EnrollmentConnections()
	{
		//Empty Constructor
	}

	public void addEnrollment(Document enrollment)
	{
		LOG.debug("Attempting to enroll student and store in DB");
		try (MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT))
		{
			MongoDatabase db = mongo.getDatabase(DBNAME);
			MongoCollection<Document> collection = db.getCollection(ENROLLMENTS_COLLECTION);
			collection.insertOne(enrollment);
		} catch (Exception e)
		{
			LOG.error("Error Occurred during Student Enrollment", e);
		}

	}

	public List<EnrollmentBean> retrieveAllEnrollments()
	{
		LOG.debug("Retrieving all stored student enrollments");
		List<EnrollmentBean> allEnrollments = new ArrayList<>();
		try (MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT))
		{
			MongoDatabase db = mongo.getDatabase(DBNAME);
			MongoCollection<Document> allRetrievedDocuments = db.getCollection(ENROLLMENTS_COLLECTION);
			FindIterable<Document> documents = allRetrievedDocuments.find();
			for (Document enroll : documents)
			{
				EnrollmentBean enRollBean = new EnrollmentBean(enroll);
				allEnrollments.add(enRollBean);
			}
			LOG.debug("All student enrollments retrieved, returning value of: " + allEnrollments);
		}
		catch (Exception e)
		{
			LOG.error("Error Occurred trying to retrieve all student enrollments", e);
		}
		return allEnrollments;
	}

	public EnrollmentBean retrieveSingleEnrollment(String studentEmail)
	{
		LOG.debug("Attempting to retrieve student enrollment");
		EnrollmentBean beanToReturn = null;
		List<EnrollmentBean> allEnrollments = retrieveAllEnrollments();
		for (EnrollmentBean anEnrollment : allEnrollments)
		{
			//Student can only have 1 enrollment, so 1st retrieved is the correct one
			if(anEnrollment.getStudentEmail().equalsIgnoreCase(studentEmail))
			{
				beanToReturn = anEnrollment;
			}
		}
		return beanToReturn;
	}

	public void deleteEnrollmentForStudent(String studentEmail)
	{
		LOG.debug("attempting to delete student details of student: " + studentEmail);
		try (MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT))
		{
			MongoDatabase db = mongo.getDatabase(DBNAME);
			MongoCollection<Document> collection = db.getCollection(ENROLLMENTS_COLLECTION);
			Bson bson = Filters.eq("Email", studentEmail);
			collection.findOneAndDelete(bson);
		} catch (Exception e)
		{
			LOG.error("Error Occurred during Student Deletion", e);
		}
	}
}
