package mongodb;

import java.util.ArrayList;
import java.util.List;
import mongodbbeans.MarkBean;
import org.bson.Document;

public class MarkConnections extends AbstractMongoDBConnections
{
	public MarkConnections()
	{
		//Empty constructor
	}

	public void addMarks(Document mark)
	{

	}

	public List<MarkBean> retrieveAllMarksForModule()
	{
		List<MarkBean> allMarks = new ArrayList<>();
		return allMarks;
	}

	public MarkBean retrieveSingleMarkForCourse(MarkBean markBean)
	{
		//Gets a single MarkBean for the Bean provided (Containing ModuleCode and StudentEmail)
		MarkBean beanToReturn = new MarkBean();
		return beanToReturn;
	}

	public List<MarkBean> retrieveAllMarksForStudent(String studentEmail)
	{
		//Gets All MarkBeans for a specific student
		List<MarkBean> allMarks = new ArrayList<>();
		return allMarks;
	}

}
