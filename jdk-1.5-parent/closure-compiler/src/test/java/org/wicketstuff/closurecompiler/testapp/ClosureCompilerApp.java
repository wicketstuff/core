package org.wicketstuff.closurecompiler.testapp;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.mapper.HomePageMapper;

public class ClosureCompilerApp extends WebApplication
{

	@Override
	public Class<? extends Page> getHomePage()
	{
		return HomePage.class;
	}
}
