package org.wicketstuff.browserid.example;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class BrowserIdApplication extends WebApplication
{

	@Override
	public Class<? extends Page> getHomePage()
	{
		return BrowserIdPage.class;
	}

}
