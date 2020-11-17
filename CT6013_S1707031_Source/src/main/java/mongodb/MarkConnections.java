package mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import mongodbbeans.MarkBean;
import org.bson.Document;

public class MarkConnections extends AbstractMongoDBConnections
{
	public MarkConnections()
	{
		//Empty constructor
	}

	public void addMarks(Document mark)
	{
		//Add marks to DB
		LOG.debug("Inserting Mark data into DB");
		try (MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT))
		{
			MongoDatabase db = mongo.getDatabase(DBNAME);
			MongoCollection<Document> collection = db.getCollection(MARKS_COLLECTION);
			collection.insertOne(mark);
		}
		catch (Exception e)
		{
			LOG.error("Error Occurred during Mark Registration", e);
		}
	}

	public List<MarkBean> retrieveAllMarks()
	{
		LOG.debug("Retrieving all Marks from DB");
		ArrayList<MarkBean> allMarks = new ArrayList<>();
		try (MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT))
		{
			MongoDatabase db = mongo.getDatabase(DBNAME);
			MongoCollection<Document> allRetrievedDocuments = db.getCollection(MARKS_COLLECTION);
			FindIterable<Document> documents = allRetrievedDocuments.find();
			for (Document aMark : documents)
			{
				MarkBean mBean = new MarkBean(aMark);
				allMarks.add(mBean);
			}
			LOG.debug("All marks retrieved, returning value of: " + allMarks);
		}
		catch (Exception e)
		{
			LOG.error("Error Occurred trying to retrieve all marks", e);
		}
		return allMarks;
	}

	public MarkBean retrieveMarksForStudentInModule(String studentEmail, String moduleCode)
	{
		MarkBean beanToReturn = null;
		LOG.debug("Attempting retrieval of MarkBean using Student Email and Module Code");
		List<MarkBean> markBeans = retrieveAllMarks();
		for (MarkBean markBean : markBeans)
		{
			if(markBean.getStudentEmail().equalsIgnoreCase(studentEmail) && markBean.getModuleCode().equalsIgnoreCase(moduleCode))
			{
				beanToReturn = markBean;
			}
		}
		return beanToReturn;
	}

	public List<MarkBean> retrieveAllMarksForStudent(String studentEmail)
	{
		LOG.debug("Retrieving List<MarkBean> for student: " + studentEmail);
		//Gets All MarkBeans for a specific student
		List<MarkBean> beansToReturn = new ArrayList<>();
		List<MarkBean> markBeans = retrieveAllMarks();
		for (MarkBean markBean : markBeans)
		{
			if(markBean.getStudentEmail().equalsIgnoreCase(studentEmail))
			{
				beansToReturn.add(markBean);
			}
		}
		return beansToReturn;
	}
}
