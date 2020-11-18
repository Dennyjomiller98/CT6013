package oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class AbstractOracleConnections implements IOracleConnections
{
	static final Logger LOG = Logger.getLogger(AbstractOracleConnections.class);

	public static final String ORACLE_USERNAME = "s1707031";
	public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String ORACLE_DRIVER_URL = "jdbc:oracle:thin:@//oracle.glos.ac.uk:1521/orclpdb.chelt.local";
	public static final String ORACLE_PASSWORD = "ct6013s1707031";

	public static final String STUDENTS_COLLECTION = "students";
	public static final String TEACHERS_COLLECTION = "teachers";
	public static final String COURSES_COLLECTION = "courses";
	public static final String MODULES_COLLECTION = "modules";
	public static final String MARKS_COLLECTION = "marks";
	public static final String ENROLLMENTS_COLLECTION = "enrollments";
	private Connection oracleClient;

	protected AbstractOracleConnections()
	{
		//Empty constructor, required as Abstract class
	}

	public Connection getOracleClient()
	{
		init();
		return oracleClient;
	}

	public void init()
	{
		try
		{
			oracleClient = DriverManager.getConnection(ORACLE_DRIVER_URL, ORACLE_USERNAME, ORACLE_PASSWORD);
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
			Class.forName(ORACLE_DRIVER);
		}
		catch (Exception e)
		{
			LOG.error("Driver class not found", e);
		}
	}
}
