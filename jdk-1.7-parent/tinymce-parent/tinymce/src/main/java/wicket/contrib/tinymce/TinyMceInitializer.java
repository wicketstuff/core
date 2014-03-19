package wicket.contrib.tinymce;

import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;
import org.apache.wicket.markup.html.IPackageResourceGuard;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;

public class TinyMceInitializer implements IInitializer
{

	public void init(Application application)
	{
		application.getRootRequestMapperAsCompound().add(new TinyMceRequestMapper());

		// Resource Package Guard security settings to fix .htm file problem
		IPackageResourceGuard packageResourceGuard = application.getResourceSettings().getPackageResourceGuard();
		if (packageResourceGuard instanceof SecurePackageResourceGuard)
		{
			SecurePackageResourceGuard guard = (SecurePackageResourceGuard) packageResourceGuard;
			guard.addPattern("+wicket/contrib/tinymce/tiny_mce/**/*.htm");
		}
	}

	public void destroy(Application application)
	{
	}
}
