package etl;

import beans.dw.DWResultsBean;

/**
 * @author Denny-Jo
 * DataTransformer is used to ensure values placed into the DWResultsBean are valid
 * e.g. A student enrolled on a course, the Course Id present MUST be a valid course found in the CourseBeans/DimCourseBeans
 * */
public class DataTransformer
{
	public DataTransformer()
	{
		//Empty Constructor
	}

	public DWResultsBean validateAndTransformData(DWResultsBean bean)
	{
		//TODO - update, validate values, check IDs etc, return sanitised bean
		return bean;
	}
}
