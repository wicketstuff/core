package org.wicketstuff.logback.examples;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.crypt.CharEncoding;
import org.wicketstuff.logback.ConfiguratorPage;

/**
 * Example wicket application.
 * 
 * @author akiraly
 */
public class App extends WebApplication
{
	@Override
	public Class<? extends Page> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	protected void init()
	{
		super.init();

		getMarkupSettings().setDefaultMarkupEncoding(CharEncoding.UTF_8);

		mountPage("logback/config", ConfiguratorPage.class);
	}

}
