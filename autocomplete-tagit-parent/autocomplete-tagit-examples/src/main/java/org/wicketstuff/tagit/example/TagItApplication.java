package org.wicketstuff.tagit.example;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class TagItApplication extends WebApplication
{
	@Override
	protected void init() {
		getCspSettings().blocking().disabled();
		super.init();
	}

	@Override
	public Class<? extends Page> getHomePage()
	{
		return TagItPage.class;
	}

}
