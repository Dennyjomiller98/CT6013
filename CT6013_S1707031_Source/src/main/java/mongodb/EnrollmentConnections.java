package mongodb;

import java.util.ArrayList;
import java.util.List;
import mongodbbeans.EnrollmentBean;
import org.bson.Document;

public class EnrollmentConnections extends AbstractMongoDBConnections
{
	public EnrollmentConnections()
	{
		//Empty Constructor
	}

	public void addEnrollment(Document enrollment)
	{

	}

	public List<EnrollmentBean> retrieveAllEnrollments()
	{
		List<EnrollmentBean> allEnrollments = new ArrayList<>();
		return allEnrollments;
	}

	public EnrollmentBean retrieveSingleEnrollment(EnrollmentBean enrollmentBean)
	{
		EnrollmentBean beanToReturn = new EnrollmentBean();
		return beanToReturn;
	}

	public EnrollmentBean retrieveEnrollmentForStudent(String studentEmail)
	{
		EnrollmentBean beanToReturn = new EnrollmentBean();
		return beanToReturn;
	}
}
