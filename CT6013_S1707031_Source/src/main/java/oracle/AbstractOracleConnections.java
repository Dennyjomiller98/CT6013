package oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class AbstractOracleConnections implements IOracleConnections
{
	static final Logger LOG = Logger.getLogger(AbstractOracleConnections.class);

	public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String ORACLE_DRIVER_URL = "jdbc:oracle:thin:@//oracle.glos.ac.uk:1521/orclpdb.chelt.local";
	public static final String ORACLE_OP_USERNAME = "s1707031_OP";
	public static final String ORACLE_OP_PASSWORD = "s1707031_OP!";
	public static final String ORACLE_DW_USERNAME = "s1707031_DW";
	public static final String ORACLE_DW_PASSWORD = "s1707031_DW!";

	public static final String TBL_ASSIGNMENTS = "CT6013_ASSIGNMENTS";

	public static final String AUTH_COLLECTION = "dw_auth";
	public static final String USERS_COLLECTION = "dw_users";
	private Connection oracleClient;

	protected AbstractOracleConnections()
	{
		//Empty constructor, required as Abstract class
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
			oracleClient = DriverManager.getConnection(ORACLE_DRIVER_URL, ORACLE_DW_USERNAME, ORACLE_DW_PASSWORD);
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
			oracleClient = DriverManager.getConnection(ORACLE_DRIVER_URL, ORACLE_OP_USERNAME, ORACLE_OP_PASSWORD);
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
