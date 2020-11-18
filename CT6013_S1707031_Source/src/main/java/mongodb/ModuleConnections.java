package mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import beans.ModuleBean;
import org.bson.Document;
import org.bson.conversions.Bson;

public class ModuleConnections extends AbstractMongoDBConnections
{
	public ModuleConnections()
	{
		//Empty Constructor
	}

	public void addModule(Document module)
	{
		//Attempt to add Module Document into database
		LOG.debug("Attempting to add Module into Database: " + module);
		try(MongoClient mongoClient = new MongoClient(MONGO_HOST, MONGO_PORT))
		{
			MongoDatabase db = mongoClient.getDatabase(DBNAME);
			MongoCollection<Document> collection = db.getCollection(MODULES_COLLECTION);
			collection.insertOne(module);
		}
		catch (Exception e)
		{
			LOG.debug("An error occurred during Module addition", e);
		}
	}

	public List<ModuleBean> retrieveAllModules()
	{
		LOG.debug("Attempting to retrieve all modules from database");
		ArrayList<ModuleBean> allModules = new ArrayList<>();
		try (MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT))
		{
			MongoDatabase db = mongo.getDatabase(DBNAME);
			MongoCollection<Document> allRetrievedDocuments = db.getCollection(MODULES_COLLECTION);
			FindIterable<Document> documents = allRetrievedDocuments.find();
			for (Document aModule : documents)
			{
				ModuleBean moduleBean = new ModuleBean(aModule);
				allModules.add(moduleBean);
			}
			LOG.debug("All modules retrieved, returning value of: " + allModules);
		}
		catch (Exception e)
		{
			LOG.error("Error Occurred during retrieval of all Modules", e);
		}
		return allModules;
	}

	public ModuleBean retrieveSingleModule(String moduleCode)
	{
		ModuleBean beanToReturn = null;
		List<ModuleBean> moduleBeans = retrieveAllModules();
		for (ModuleBean bean : moduleBeans)
		{
			if(bean.getModuleCode().equalsIgnoreCase(moduleCode))
			{
				beanToReturn = bean;
			}
		}
		return beanToReturn;
	}

	public void updateModule(Document moduleToUpdate, String moduleCode)
	{
		//Update Module found with moduleCode
		LOG.debug("Attempting to update module, moduleCode: " + moduleCode);
		try (MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT))
		{
			MongoDatabase db = mongo.getDatabase(DBNAME);
			MongoCollection<Document> collection = db.getCollection(MODULES_COLLECTION);
			Bson bson = Filters.eq("Module_Code", moduleCode);
			collection.replaceOne(bson, moduleToUpdate);
		} catch (Exception e)
		{
			LOG.error("Error Occurred during Module Update", e);
		}
	}

	public List<ModuleBean> retrieveAllModulesOnCourse(String courseCode)
	{
		List<ModuleBean> modulesToReturn = new ArrayList<>();
		List<ModuleBean> moduleBeans = retrieveAllModules();
		if(moduleBeans != null)
		{
			for (ModuleBean moduleBean : moduleBeans)
			{
				if(moduleBean.getRelatedCourse().equalsIgnoreCase(courseCode))
				{
					//add matching course to return
					modulesToReturn.add(moduleBean);
				}
			}
		}
		return modulesToReturn;
	}
}