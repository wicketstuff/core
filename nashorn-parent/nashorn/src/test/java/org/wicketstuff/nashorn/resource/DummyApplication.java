package org.wicketstuff.nashorn.resource;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class DummyApplication extends WebApplication
{

	@Override
	public Class<? extends Page> getHomePage()
	{
		return DummyPage.class;
	}
}
