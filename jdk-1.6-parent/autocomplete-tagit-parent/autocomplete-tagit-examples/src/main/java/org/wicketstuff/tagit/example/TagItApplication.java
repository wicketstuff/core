package org.wicketstuff.tagit.example;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class TagItApplication extends WebApplication
{

	@Override
	public Class<? extends Page> getHomePage()
	{
		return TagItPage.class;
	}

}
