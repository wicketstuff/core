package org.wicketstuff.minis;

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
		getCspSettings().blocking().disabled();
	    super.init();

	    getComponentInstantiationListeners().add(component -> {
			if (component instanceof WebPage) {
				component.add(DocumentCompatibilityBehavior.ieEdge());
				component.add(PersistedCacheBehavior.prevent());
			}
		});
	}
}
