package oracle;

import beans.ModuleBean;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class ModuleConnections extends AbstractOracleConnections
{
	public ModuleConnections()
	{
		//Empty Constructor
	}

	public void addModule(Document module)
	{
		//Attempt to add Module Document into database
		LOG.debug("Attempting to add Module into Database: " + module);
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if(oracleClient != null)
			{
				//Add Module to DB
				String values = "'" +module.getString("Module_Code")
						+ "','" + module.getString("Module_Name")
						+ "','" + module.getString("Module_Tutor")
						+ "','" + module.getString("Related_Course")
						+ "','" + module.getString("Semester")
						+ "','" + module.getString("Module_Start")
						+ "','" + module.getString("Module_End") + "'";
				String query = "INSERT INTO " + MODULES_COLLECTION +
						"(Module_Code, Module_Name, Module_Tutor, Related_Course, Semester, Module_Start, Module_End)" + " VALUES (" + values + ")";

				//Execute query
				executeModuleUpdateQuery(oracleClient, query);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to add module to Oracle", e);
		}
	}

	private void executeModuleUpdateQuery(Connection oracleClient, String query) throws SQLException
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

	public List<ModuleBean> retrieveAllModules()
	{
		ArrayList<ModuleBean> allModules = new ArrayList<>();

		LOG.debug("Beginning all Module retrieval");
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if(oracleClient != null)
			{
				//Select Query
				String query = "SELECT * FROM " + MODULES_COLLECTION;

				//Execute query
				allModulesRetrievalQuery(oracleClient, query, allModules);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to retrieve all modules from Oracle", e);
		}
		return allModules;
	}

	private void allModulesRetrievalQuery(Connection oracleClient, String query, ArrayList<ModuleBean> allModules) throws SQLException
	{
		try (Statement statement = oracleClient.createStatement())
		{
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next())
			{
				ModuleBean bean = new ModuleBean();
				bean.setModuleCode(resultSet.getString("Module_Code"));
				bean.setModuleName(resultSet.getString("Module_Name"));
				bean.setModuleTutor(resultSet.getString("Module_Tutor"));
				bean.setModuleStart(resultSet.getString("Module_Start"));
				bean.setModuleEnd(resultSet.getString("Module_End"));
				bean.setSemester(Integer.parseInt(resultSet.getString("Semester")));
				bean.setCompulsory(Boolean.parseBoolean(resultSet.getString("Is_Compulsory")));
				bean.setRelatedCourse(resultSet.getString("Related_Course"));

				//Add bean to list of Modules
				allModules.add(bean);
			}
		}
		catch(Exception e)
		{
			LOG.error("Query failure, using query: " + query, e);
		}
		oracleClient.close();
	}

	public ModuleBean retrieveSingleModule(String moduleCode)
	{
		LOG.debug("Attempting Retrieval of single module with code: " + moduleCode);
		ModuleBean beanToReturn = null;
		List<ModuleBean> moduleBeans = retrieveAllModules();
		for (ModuleBean moduleBean : moduleBeans)
		{
			if(moduleBean.getModuleCode().equalsIgnoreCase(moduleCode))
			{
				beanToReturn = moduleBean;
			}
		}
		return beanToReturn;
	}

	public void updateModule(Document moduleToUpdate, String moduleCode)
	{
		LOG.debug("attempting to update module details for module: " + moduleCode);
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if (oracleClient != null)
			{
				String query = "UPDATE " + MODULES_COLLECTION + " SET Module_Code ='" + moduleToUpdate.getString("Module_Code")
						+"', Module_Name='"+ moduleToUpdate.getString("Module_Name")
						+"', Module_Tutor='"+ moduleToUpdate.getString("Module_Tutor")
						+"', Related_Course='" + moduleToUpdate.getString("Related_Course")
						+"', Semester='"+ moduleToUpdate.getString("Semester")
						+"', Module_Start='"+ moduleToUpdate.getString("Module_Start")
						+"', Module_End='"+ moduleToUpdate.getString("Module_End")
						+"', Is_Compulsory='"+ moduleToUpdate.getString("Is_Compulsory")
						+"' WHERE MODULE_CODE='"+ moduleCode +"'";

				executeModuleUpdateQuery(oracleClient, query);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to update module information");
		}
	}

	public List<ModuleBean> retrieveAllModulesOnCourse(String courseCode)
	{
		LOG.debug("Attempting oracle retrieval of all modules on course:" + courseCode);
		List<ModuleBean> moduleBeans = retrieveAllModules();
		List<ModuleBean> allModules = new ArrayList<>();
		for (ModuleBean moduleBean : moduleBeans)
		{
			if(moduleBean.getRelatedCourse().equalsIgnoreCase(courseCode))
			{
				allModules.add(moduleBean);
			}
		}
		return allModules;
	}

	public void deleteModule(String moduleCode)
	{
		LOG.debug("Attempting to delete Module");
		setOracleDriver();
		try
		{
			AbstractOracleConnections conn = new AbstractOracleConnections();
			Connection oracleClient = conn.getOracleClient();
			if (oracleClient != null)
			{
				String query = "DELETE FROM " + MODULES_COLLECTION
						+ " WHERE Module_Code='" + moduleCode + "';";
				executeModuleUpdateQuery(oracleClient, query);
			}
			else
			{
				LOG.error("connection failure");
			}
		}
		catch(Exception e)
		{
			LOG.error("Unable to delete Module in oracle DB");
		}
	}
}