package managers.oracle;

import java.sql.Timestamp;
import java.util.Date;
import oracle.sql.DATE;
import org.apache.log4j.Logger;

/**
 * @author Denny-Jo
 * DataManager - handles data from Beans, converting to the correct type (e.g. Retrieving String of a Date Format, Loading into DW requires the String value as a Date once again after transformation)
 * */
public class DataManager
{
	static final Logger LOG = Logger.getLogger(DataManager.class);

	public DataManager()
	{
		//Empty Constructor
	}

	/**
	 * @param dateAsString Extracted Bean Date value from Operational Database
	 * @return DATE to be loaded into the Database Warehouse
	 * */
	public DATE convertValueToDate(String dateAsString)
	{
		DATE ret = null;
		try
		{
			if(dateAsString != null && !dateAsString.equalsIgnoreCase("unknown") && !dateAsString.equalsIgnoreCase("none"))
			{
				ret = new DATE(dateAsString);
			}
			else if(dateAsString != null)
			{
				//Not Ideal, but send a new date
				Date date = new Date();
				Timestamp t = new Timestamp(date.getTime());
				ret = new DATE(t);
			}
		}
		catch (Exception e)
		{
			LOG.error("Error using value: " + dateAsString, e);
		}
		return ret;
	}
}
