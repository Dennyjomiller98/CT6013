package mongodb;

import java.util.ArrayList;
import java.util.List;
import mongodbbeans.ModuleBean;
import org.bson.Document;

public class ModuleConnections extends AbstractMongoDBConnections
{
	public ModuleConnections()
	{
		//Empty Constructor
	}

	public void addModule(Document module)
	{

	}

	public List<ModuleBean> retrieveAllModules()
	{
		List<ModuleBean> allModules = new ArrayList<>();
		return allModules;
	}

	public ModuleBean retrieveSingleModule(ModuleBean moduleBean)
	{
		ModuleBean beanToReturn = null;
		return beanToReturn;
	}

	public void updateModule(Document moduleToUpdate, String moduleCode)
	{

	}
}