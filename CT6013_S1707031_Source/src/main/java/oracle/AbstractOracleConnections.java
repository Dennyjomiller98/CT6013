package oracle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * @author Denny-Jo
 * AbstractOracleConnections - Class provides use for Oracle DB Connectivity, for both the Operational and DW Connections
 * */
public class AbstractOracleConnections implements IOracleConnections
{
	static final Logger LOG = Logger.getLogger(AbstractOracleConnections.class);

	//TODO - Remove these static final variables, in exchange for a properties file containing TBL name information
	
	private String oracleDriver;
	private String oracleDriverUrl;
	private String oracleOpUsername;
	private String oracleOpPassword;
	private String oracleDwUsername;
	private String oracleDwPassword;

	public static final String TBL_ASSIGNMENTS = "CT6013_ASSIGNMENTS";
	public static final String TBL_COURSES = "CT6013_COURSES";
	public static final String TBL_ENROLLMENTS = "CT6013_ENROLLMENTS";
	public static final String TBL_MODULES = "CT6013_MODULES";
	public static final String TBL_STUDENTS = "CT6013_STUDENTS";
	public static final String TBL_SUBJECTS = "CT6013_SUBJECTS";
	public static final String TBL_TUTORS = "CT6013_TUTORS";

	public static final String TBL_DIM_COURSES = "CT6013_DIM_COURSES";
	public static final String TBL_DIM_ENROLLMENTS = "CT6013_DIM_ENROLLMENTS";
	public static final String TBL_DIM_MODULES = "CT6013_DIM_MODULES";
	public static final String TBL_DIM_STUDENTS = "CT6013_DIM_STUDENTS";
	public static final String TBL_DIM_SUBJECTS = "CT6013_DIM_SUBJECTS";
	public static final String TBL_DIM_TUTORS = "CT6013_DIM_TUTORS";

	public static final String TBL_DW_RESULTS = "DW_RESULTS";
	public static final String TBL_DW_DIM_COURSE = "DW_DIM_COURSE";
	public static final String TBL_DW_DIM_ENROLLMENT = "DW_DIM_ENROLLMENT";
	public static final String TBL_DW_DIM_SUBJECT = "DW_DIM_SUBJECT";
	public static final String TBL_DW_DIM_MODULE = "DW_DIM_MODULE";
	public static final String TBL_DW_DIM_STUDENT = "DW_DIM_STUDENT";
	public static final String TBL_DW_DIM_TUTOR = "DW_DIM_TUTOR";
	public static final String AUTH_COLLECTION = "dw_auth";
	public static final String USERS_COLLECTION = "dw_users";

	private Connection oracleClient;

	protected AbstractOracleConnections()
	{
		//Empty constructor, required as Abstract class
		Properties props = new Properties();
		try
		{
			InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("/config.properties");
			if(resourceAsStream != null)
			{
				props.load(resourceAsStream);
				oracleDriver = props.getProperty("oracledriver");
				oracleDriverUrl = props.getProperty("oracledriverurl");
				oracleOpUsername = props.getProperty("opuser");
				oracleOpPassword = props.getProperty("oppword");
				oracleDwUsername = props.getProperty("dwuser");
				oracleDwPassword = props.getProperty("dwpword");
			}
			else
			{
				throw new FileNotFoundException("Could not find properties file for configuring smtp mail");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public Connection getOPClient()
	{
		init();
		return oracleClient;
	}

	public Connection getDWClient()
	{
		initDW();
		return oracleClient;
	}

	public void initDW()
	{
		try
		{
			oracleClient = DriverManager.getConnection(oracleDriverUrl, oracleDwUsername, oracleDwPassword);
		}
		catch (SQLException throwables)
		{
			LOG.error(throwables);
		}
	}

	public void init()
	{
		try
		{
			oracleClient = DriverManager.getConnection(oracleDriverUrl, oracleOpUsername, oracleOpPassword);
		}
		catch (SQLException throwables)
		{
			LOG.error(throwables);
		}
	}

	public void setOracleDriver()
	{
		try
		{
			//Is required or oracleClient Connection is always null
			Class.forName(oracleDriver);
		}
		catch (Exception e)
		{
			LOG.error("Driver class not found", e);
		}
	}
}
