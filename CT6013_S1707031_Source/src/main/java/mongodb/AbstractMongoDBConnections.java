package mongodb;

import org.apache.log4j.Logger;

public abstract class AbstractMongoDBConnections implements IMongoDBConnections{
    static final Logger LOG = Logger.getLogger(AbstractMongoDBConnections.class);

    public static final String MONGO_HOST = "localhost";
    public static final int MONGO_PORT = 27017;
    public static final String DBNAME = "db_ct6013_s1707031";
    public static final String STUDENTS_COLLECTION = "students";
    public static final String STUDENTID_COLLECTION = "students_ids"; //For Auto-Increment?
    public static final String TEACHERS_COLLECTION = "teachers";
    public static final String MODULES_COLLECTION = "modules";
    public static final String COURSES_COLLECTION = "courses";
    public static final String MARKS_COLLECTION = "marks";

    public AbstractMongoDBConnections()
    {
        //Empty constructor
    }
}
