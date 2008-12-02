package org.wicketstuff.calendarviews.js;

import org.apache.wicket.Application;
import org.apache.wicket.ResourceReference;

public class Prototype {

	private static final String VERSION = "1.6.0.3";
	private static final String NORMAL = "prototype-" + VERSION + ".js";
	private static final String MINI = "prototype-" + VERSION + ".js";
	
	public static final ResourceReference getResourceReference() {
		String js = NORMAL;
		if (Application.DEPLOYMENT.equals(Application.get().getConfigurationType())) {
			js = MINI;
		}
		return new ResourceReference(Prototype.class, js);
	}
	

}
