package oracle;

import beans.UserBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import oracle.sql.DATE;

public class AuthenticationConnections extends AbstractOracleConnections
{
	private int digit1;
	private int digit2;
	private int digit3;
	private int digit4;
	private Random fRandom;

	public AuthenticationConnections()
	{
		//Empty Constructor
	}

	public String generatePinForUser(UserBean userBean)
	{
		String pin = null;
		LOG.debug("Beginning Pin Generation");
		setOracleDriver();

		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getDWClient();
			if(oracleClient != null)
			{
				//Delete existing key(s)
				deleteUsersPins(userBean);

				//Generate new key
				LOG.debug("Beginning User Registration");
				setOracleDriver();
				pin = generateAndAddKey(userBean);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to generate new Authentication Pin", e);
		}
		return pin;
	}

	private String generateAndAddKey(UserBean userBean)
	{
		String ret = null;
		try
		{
			Date date = new Date();
			Timestamp t = new Timestamp(date.getTime());
			DATE startDate = new DATE(t);
			DATE endDate = new DATE(t);
			endDate.addJulianDays(1, -1);

			AbstractOracleConnections newConn = new AbstractOracleConnections();
			Connection newOracleClient = newConn.getDWClient();
			if(newOracleClient != null)
			{
				//Add New Key to DB
				String authenticationPin = makeNewKey();

				String values = "'" + userBean.getUsername()
						+ "','" + authenticationPin
						+ "','" + startDate
						+ "','" + endDate + "'";
				String query = "INSERT INTO " + AUTH_COLLECTION +
						"(Username, Pin, Pin_Start, Pin_End)" + " VALUES (" + values + ")";

				//Execute query
				executeUpdateQuery(newOracleClient, query);
				ret = authenticationPin;
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to add Auth Key to Oracle DB", e);
		}
		return ret;
	}

	private String makeNewKey()
	{
		//Generates a 4 digit pin to use for 2FA
		randomise();
		//Triple same number in a row and all 4 the same safety
		while((digit1==digit2 && digit2==digit3) || (digit2==digit3 && digit3==digit4))
		{
			randomise();
		}
		StringBuilder pin = new StringBuilder();
		StringBuilder result = pin.append(digit1).append(digit2).append(digit3).append(digit4);
		return result.toString();
	}

	private void randomise()
	{
		fRandom = new Random();
		digit1 = fRandom.nextInt(10);
		digit2 = fRandom.nextInt(10);
		digit3 = fRandom.nextInt(10);
		digit4 = fRandom.nextInt(10);
	}

	public boolean validatePinForUser(UserBean userBean)
	{
		boolean ret = false;
		UserBean returnedBean = retrieveSingleKey(userBean.getUsername(), userBean.getPin());
		if(returnedBean != null)
		{
			ret = true;
		}
		return ret;
	}

	public UserBean retrieveSingleKey(String username, String pin)
	{
		UserBean beanToReturn = null;
		List<UserBean> userBeans = retrieveAllKeys();
		for (UserBean userBean : userBeans)
		{
			if(userBean.getUsername().equalsIgnoreCase(username) && userBean.getPin().equals(pin))
			{
				Date date = new Date();
				Timestamp t = new Timestamp(date.getTime());
				DATE now = new DATE(t);
				String pinStart = userBean.getPinStart();
				String pinEnd = userBean.getPinEnd();
				DATE s = new DATE(pinStart);
				DATE e = new DATE(pinEnd);
				if((now.compareTo(e) == -1) && (now.compareTo(s) == 1))
				{
					beanToReturn = userBean;
				}
			}
		}
		return beanToReturn;
	}

	public List<UserBean> retrieveAllKeys()
	{
		ArrayList<UserBean> allUsers = new ArrayList<>();

		LOG.debug("Beginning all Users retrieval");
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getDWClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + AUTH_COLLECTION;

				//Execute query
				allKeysRetrievalQuery(oracleClient, query, allUsers);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to retrieve all Users from Oracle", e);
		}
		return allUsers;
	}

	private void allKeysRetrievalQuery(Connection oracleClient, String query, ArrayList<UserBean> allUsers) throws SQLException
	{
		try (Statement statement = oracleClient.createStatement())
		{
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
			{
				UserBean bean = new UserBean();
				bean.setUsername(resultSet.getString("Username"));
				bean.setPin(resultSet.getString("Pin"));
				bean.setPinStart(resultSet.getString("Pin_Start"));
				bean.setPinEnd(resultSet.getString("Pin_End"));
				//Add bean to list of users
				allUsers.add(bean);
			}
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();
	}

	private void deleteUsersPins(UserBean userBean)
	{
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getDWClient();
			if (oracleClient != null)
			{
				String query = "DELETE FROM " + AUTH_COLLECTION
						+ " WHERE Username='" + userBean.getUsername() + "'";
				executeUpdateQuery(oracleClient, query);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to delete Authentication Keys in oracle DB");
		}
	}

	private void executeUpdateQuery(Connection oracleClient, String query) throws SQLException
	{
		try (Statement statement = oracleClient.createStatement())
		{
			statement.executeUpdate(query);
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();
	}
}
