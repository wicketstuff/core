package wicket.contrib.tinymce4;

import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;

public class TinyMceInitializer implements IInitializer
{

    @Override
	public void init(Application application)
	{
		application.getRootRequestMapperAsCompound().add(new TinyMceRequestMapper());
	}

    @Override
	public void destroy(Application application)
	{
	}
}
