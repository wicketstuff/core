package org.wicketstuff.gae;

import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;
import org.apache.wicket.serialize.ISerializer;

/**
 * A Wicket initializer that configures the application that way so it is possible to run it in
 * Google AppEngine
 */
public class GaeInitializer implements IInitializer
{

	public void init(final Application application)
	{

		// disable ModificationWatcher
		application.getResourceSettings().setResourcePollFrequency(null);

		// use plain JDK Object(Input|Output)Stream
		ISerializer serializer = new GaeObjectSerializer(application.getApplicationKey());
		application.getFrameworkSettings().setSerializer(serializer);

		// save older version of pages in the HttpSession
		int maxPages = 10;
		if (application instanceof GaeApplication) {
			maxPages = ((GaeApplication)application).getMaxPages();
		}
		application.setPageManagerProvider(new GaePageManagerProvider(application, maxPages));

		// disable file cleaning because it starts a new thread
		application.getResourceSettings().setFileCleaner(null);
	}

	public void destroy(Application application)
	{
	}
}
