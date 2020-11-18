package mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import beans.StudentBean;
import org.bson.Document;
import org.bson.conversions.Bson;

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
        }
        catch (Exception e)
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
            LOG.error("Error Occurred trying to retrieve all student", e);
        }
        return allStudents;
    }

    public StudentBean retrieveSingleStudent(String studentEmail)
    {
        LOG.debug("Attempting Retrieval of single student: " + studentEmail);
        StudentBean beanToReturn = null;
        List<StudentBean> allStudentBeans = retrieveAllStudents();
        for (StudentBean allStudentBean : allStudentBeans)
        {
            if (allStudentBean.getEmail().equalsIgnoreCase(studentEmail))
            {
                beanToReturn = allStudentBean;
            }
        }
        return beanToReturn;
    }

    public boolean attemptLogin(StudentBean studentBean)
    {
        LOG.debug("Attempting Student Login");
        //Find the associated email in DB and check login credentials are correct
        boolean isCorrectCredentials = false;
        String email = studentBean.getEmail();
        String password = studentBean.getPassword();

        StudentBean potentialStudent = retrieveSingleStudent(email);
        //Email will match at this point, only need to assert Password value to email to authenticate login
        if (potentialStudent != null && password.equals(potentialStudent.getPassword()))
        {
            isCorrectCredentials = true;
        }
        return isCorrectCredentials;
    }

    public void updateStudentDetails(Document studentToUpdate, String email)
    {
        LOG.debug("attempting to update student details");
        //Store values into the DB
        try (MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT))
        {
            MongoDatabase db = mongo.getDatabase(DBNAME);
            MongoCollection<Document> collection = db.getCollection(STUDENTS_COLLECTION);
            Bson bson = Filters.eq("Email", email);
            collection.replaceOne(bson, studentToUpdate);
        } catch (Exception e)
        {
            LOG.error("Error Occurred during Student Update", e);
        }
    }

    public void enrollStudent(String studentEmail)
    {
        LOG.debug("Attempting to enroll student in student DB");
        try (MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT))
        {
            MongoDatabase db = mongo.getDatabase(DBNAME);
            MongoCollection<Document> collection = db.getCollection(STUDENTS_COLLECTION);
            Bson bson = Filters.eq("Email", studentEmail);

            StudentBean studentBean = retrieveSingleStudent(studentEmail);
            if (studentBean != null)
            {
                Document studentToEnroll = new Document();
                studentToEnroll.append("First_Name", studentBean.getFirstName());
                studentToEnroll.append("Surname", studentBean.getSurname());
                studentToEnroll.append("Email", studentBean.getEmail());
                studentToEnroll.append("DOB", studentBean.getDOB());
                studentToEnroll.append("Address", studentBean.getAddress());
                studentToEnroll.append("Password", studentBean.getPassword());
                studentToEnroll.append("Is_Enrolled", "true");
                studentToEnroll.append("Is_Teacher", "false");
                collection.replaceOne(bson, studentToEnroll);
            }
            else
            {
                LOG.error("Student not found");
            }
        } catch (Exception e)
        {
            LOG.error("Error Occurred during Student Update", e);
        }
    }
}
