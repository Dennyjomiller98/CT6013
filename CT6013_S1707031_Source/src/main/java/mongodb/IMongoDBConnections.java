package mongodb;

import mongodbbeans.StudentBean;
import org.bson.Document;

import java.util.List;

public interface IMongoDBConnections {

    void registerCustomerToDB(Document customer);

    public abstract List<StudentBean> retrieveAllStudents();

    public abstract StudentBean retrieveSingleStudent(String studentID);
}
