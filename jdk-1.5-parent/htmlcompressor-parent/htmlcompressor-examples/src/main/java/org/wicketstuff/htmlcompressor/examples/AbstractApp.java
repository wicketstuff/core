package org.wicketstuff.htmlcompressor.examples;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public abstract class AbstractApp extends WebApplication
{

	@Override
	public Class<? extends Page> getHomePage()
	{
		return HomePage.class;
	}

}
