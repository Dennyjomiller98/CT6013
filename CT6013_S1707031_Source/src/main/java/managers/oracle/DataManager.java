package managers.oracle;

import java.sql.Timestamp;
import java.util.Date;
import oracle.sql.DATE;

/**
 * @author Denny-Jo
 * DataManager - handles data from Beans, converting to the correct type (e.g. Retrieving String of a Date Format, Loading into DW requires the String value as a Date once again after transformation)
 * */
public class DataManager
{
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
		DATE ret;
		if(!dateAsString.equalsIgnoreCase("unknown") && !dateAsString.equalsIgnoreCase("none"))
		{
			ret = new DATE(dateAsString);
		}
		else
		{
			//Not Ideal, but send a new date
			Date date = new Date();
			Timestamp t = new Timestamp(date.getTime());
			ret = new DATE(t);
		}
		return ret;
	}
}
