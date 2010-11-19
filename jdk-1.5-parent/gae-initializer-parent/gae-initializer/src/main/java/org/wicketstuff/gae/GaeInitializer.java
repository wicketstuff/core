package org.wicketstuff.gae;

import org.apache.wicket.Application;
import org.apache.wicket.IInitializer;
import org.apache.wicket.pageStore.memory.DataStoreEvictionStrategy;
import org.apache.wicket.pageStore.memory.PageNumberEvictionStrategy;
import org.apache.wicket.util.lang.WicketObjects;

public class GaeInitializer implements IInitializer {

	public void init(Application application) {
		
		application.getResourceSettings().setResourcePollFrequency(null);
		
		WicketObjects.setObjectStreamFactory(new GaeObjectStreamFactory());
		
		final DataStoreEvictionStrategy evictionStrategy;
		if (application instanceof GaeApplication) {
			evictionStrategy = ((GaeApplication)application).getEvictionStrategy();
		} else {
			evictionStrategy = new PageNumberEvictionStrategy(10);
		}
		
		application.setPageManagerProvider(new GaePageManagerProvider(application, evictionStrategy));
	}

}
