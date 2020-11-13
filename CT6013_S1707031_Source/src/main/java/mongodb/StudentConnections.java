package mongodb;

import mongodbbeans.StudentBean;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

public class StudentConnections extends AbstractMongoDBConnections
{

    public StudentConnections()
    {
        //Empty Constructor
    }

    public void registerStudentToDB(Document student) {
        LOG.debug("Beginning Student Registration");
        //Create Mongo Client, access DB and Retrieve Collection to insert new student Document into
        try (MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT))
        {
            MongoDatabase db = mongo.getDatabase(DBNAME);
            MongoCollection<Document> collection = db.getCollection(STUDENTS_COLLECTION);
            collection.insertOne(student);
        } catch (Exception e)
        {
            LOG.error("Error Occurred during Student Registration", e);
        }
    }

    public ArrayList<StudentBean> retrieveAllStudents()
    {
        ArrayList<StudentBean> allStudents = new ArrayList<>();
        try (MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT))
        {
            MongoDatabase db = mongo.getDatabase(DBNAME);
            MongoCollection<Document> collection = db.getCollection(STUDENTS_COLLECTION);
            FindIterable<Document> allRetrievedDocuments = collection.find();
            for (Document aStudent : allRetrievedDocuments) {
                StudentBean sBean = new StudentBean(aStudent);
                allStudents.add(sBean);
            }
            LOG.debug("All students retrieved, returning value of: " + allStudents);
        }
        catch (Exception e)
        {
            LOG.error("Error Occurred during Student Registration", e);
        }
        return allStudents;
    }

    public StudentBean retrieveSingleStudent(String studentID)
    {
        StudentBean beanToReturn = null;
        ArrayList<StudentBean> allStudentBeans = retrieveAllStudents();
        for (StudentBean allStudentBean : allStudentBeans)
        {
            if (allStudentBean.getStudentID().equals(studentID))
            {
                beanToReturn = allStudentBean;
            }
        }
        return beanToReturn;
    }

}
