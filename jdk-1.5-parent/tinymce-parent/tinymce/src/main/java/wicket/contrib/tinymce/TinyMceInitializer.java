package wicket.contrib.tinymce;

import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;

public class TinyMceInitializer implements IInitializer
{

	public void init(Application application)
	{
		application.getRootRequestMapperAsCompound().add(new TinyMceRequestMapper());
	}

	public void destroy(Application application)
	{

	}

}
