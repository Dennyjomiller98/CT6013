package mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import mongodbbeans.StudentBean;
import org.bson.Document;

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

    public List<StudentBean> retrieveAllStudents()
    {
        ArrayList<StudentBean> allStudents = new ArrayList<>();
        try (MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT))
        {
            MongoDatabase db = mongo.getDatabase(DBNAME);
            MongoCollection<Document> allRetrievedDocuments = db.getCollection(STUDENTS_COLLECTION);
            FindIterable<Document> documents = allRetrievedDocuments.find();
            for (Document aStudent : documents)
            {
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
        List<StudentBean> allStudentBeans = retrieveAllStudents();
        for (StudentBean allStudentBean : allStudentBeans)
        {
            if (allStudentBean.getEmail().equals(studentID))
            {
                beanToReturn = allStudentBean;
            }
        }
        return beanToReturn;
    }

    public boolean attemptLogin(StudentBean studentBean)
    {
        //Find the associated email in DB and check login credentials are correct
        boolean isCorrectCredentials = false;
        String email = studentBean.getEmail();
        String password = studentBean.getPassword();

        StudentBean potentialStudent = retrieveSingleStudent(email);
        //Email will match at this point, only need to assert Password value to email to authenticate login
        if(password.equals(potentialStudent.getPassword()))
        {
            isCorrectCredentials = true;
        }
        return isCorrectCredentials;
    }

    public void updateStudentDetails(Document studentToUpdate)
    {
        //Store values into the

    }
}
