package mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;

public class MongoDBConnections implements IMongoDBConnections {
    static final Logger LOG = Logger.getLogger(MongoDBConnections.class);


    public static final String MONGO_HOST = "localhost";
    public static final int MONGO_PORT = 27017;
    public static final String DBNAME = "db_ct6013_s1707031";
    public static final String STUDENTS_COLLECTION = "students";
    public static final String TEACHERS_COLLECTION = "teachers";
    public static final String MODULES_COLLECTION = "modules";
    public static final String COURSES_COLLECTION = "courses";
    public static final String MARKS_COLLECTION = "marks";

    public MongoDBConnections()
    {
        //Empty constructor
    }

    @Override
    public void registerCustomerToDB(Document customer) {
        LOG.debug("Beginning Customer Registration");
        //Create Mongo Client, access DB and Retrieve Collection to insert new customer Document into
        MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
        MongoDatabase db = mongo.getDatabase(DBNAME);
        MongoCollection<Document> collection = db.getCollection(STUDENTS_COLLECTION);
        collection.insertOne(customer);
    }
}
