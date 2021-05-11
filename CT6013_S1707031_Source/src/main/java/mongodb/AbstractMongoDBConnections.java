package mongodb;

import org.apache.log4j.Logger;

/**
 * @author Denny-Jo
 * @deprecated - We do not have MongoDB datasource in Semester 2, so AbstractMongoDBConnections no longer provides use.
 * */
@Deprecated
public abstract class AbstractMongoDBConnections implements IMongoDBConnections{
    static final Logger LOG = Logger.getLogger(AbstractMongoDBConnections.class);

    public static final String MONGO_HOST = "localhost";
    public static final int MONGO_PORT = 27017;
    public static final String DBNAME = "db_ct6013_s1707031";
    public static final String STUDENTS_COLLECTION = "students";
    public static final String TEACHERS_COLLECTION = "teachers";
    public static final String MODULES_COLLECTION = "modules";
    public static final String COURSES_COLLECTION = "courses";
    public static final String MARKS_COLLECTION = "marks";
    public static final String ENROLLMENTS_COLLECTION = "enrollments";

    protected AbstractMongoDBConnections()
    {
        //Empty constructor, required as Abstract class
    }
}
