package org.wicketstuff.urlfragment.example;

import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.urlfragment.example.asyncpage.AsyncHomePage;
import org.wicketstuff.urlfragment.example.asyncpanel.HomePage;

public class WicketApplication extends WebApplication
{

	@Override
	public Class<AsyncHomePage> getHomePage()
	{
		return AsyncHomePage.class;
	}

	@Override
	public void init()
	{
		super.init();
		this.mountPage("asyncpage", AsyncHomePage.class);
		this.mountPage("asyncpanel", HomePage.class);
	}
}
