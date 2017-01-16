package org.wicketstuff.minis;

import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.minis.behavior.ie.DocumentCompatibilityBehavior;
import org.wicketstuff.minis.behavior.safari.PersistedCacheBehavior;

/**
 * Application object for your web application. If you want to run this application without
 * deploying, run the Start class.
 * 
 * @see org.wicketstuff.minis.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
	/**
	 * Constructor
	 */
	public WicketApplication()
	{
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	protected void init() 
	{
	    super.init();

	    getComponentInstantiationListeners().add(new IComponentInstantiationListener() {
			@Override
			public void onInstantiation(Component component) {
				if (component instanceof WebPage) {
					component.add(DocumentCompatibilityBehavior.ieEdge());
					component.add(PersistedCacheBehavior.prevent());
				}
			}
		});
	}
}
