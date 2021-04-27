package oracle;

import beans.UserBean;
import encryption.PasswordHasher;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserConnections extends AbstractOracleConnections
{
	public UserConnections()
	{
		//Empty Constructor
	}

	public void registerUser(UserBean bean)
	{
		LOG.debug("Beginning User Registration");
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getDWClient();
			if(oracleClient != null)
			{
				PasswordHasher hasher = new PasswordHasher();
				//Add User to DB
				String values = "'" +bean.getUsername()
						+ "','" + hasher.encrypt(bean.getPword())
						+ "','" + bean.getRole() + "'";
				String query = "INSERT INTO " + USERS_COLLECTION +
						"(Username, Pword, Role)" + " VALUES (" + values + ")";

				//Execute query
				executeUserUpdateQuery(oracleClient, query);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to register User to Oracle", e);
		}
	}

	public UserBean retrieveSingleUser(String username, String pword)
	{
		PasswordHasher hasher = new PasswordHasher();
		UserBean beanToReturn = null;
		List<UserBean> userBeans = retrieveAllUsers();
		for (UserBean userBean : userBeans)
		{
			//TODO - remove (should check DB reg values)
			String encrypt = hasher.encrypt(pword);
			if(hasher.encrypt(pword).equals(encrypt))
			{
				if(userBean.getUsername().equalsIgnoreCase(username) && userBean.getPword().equals(hasher.encrypt(pword)))
				{
					beanToReturn = userBean;
				}
			}
		}
		return beanToReturn;
	}

	public UserBean retrieveSingleUser(String username)
	{
		UserBean beanToReturn = null;
		List<UserBean> userBeans = retrieveAllUsers();
		for (UserBean userBean : userBeans)
		{
			if(userBean.getUsername().equalsIgnoreCase(username))
			{
				beanToReturn = userBean;
			}
		}
		return beanToReturn;
	}

	public List<UserBean> retrieveAllUsers()
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
				String query = "SELECT * FROM " + USERS_COLLECTION;

				//Execute query
				allUsersRetrievalQuery(oracleClient, query, allUsers);
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

	private void allUsersRetrievalQuery(Connection oracleClient, String query, ArrayList<UserBean> allUsers) throws SQLException
	{
		try (Statement statement = oracleClient.createStatement())
		{
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
			{
				UserBean bean = new UserBean();
				bean.setUsername(resultSet.getString("Username"));
				bean.setPword(resultSet.getString("Pword"));
				bean.setRole(resultSet.getString("Role"));

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

	public boolean attemptLogin(UserBean userBean)
	{
		LOG.debug("Attempting User Login Oracle");
		//Find the associated email in DB and check login credentials are correct
		boolean isCorrectCredentials = false;
		String email = userBean.getUsername();
		String password = userBean.getPword();
		UserBean potentialUser = retrieveSingleUser(email, password);
		if(potentialUser != null)
		{
			isCorrectCredentials = true;
		}
		return isCorrectCredentials;
	}

	private void executeUserUpdateQuery(Connection oracleClient, String query) throws SQLException
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
