package org.wicketstuff.theme.tester;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.maven.MavenDevResourceStreamLocator;

public class WicketThemeTestApp extends WebApplication
{
	@Override
	protected void init()
	{
		super.init();
		
		if (DEVELOPMENT.equals(getConfigurationType()))
		{
			getResourceSettings().setResourceStreamLocator(new MavenDevResourceStreamLocator());
		}
	}
	
	@Override
	public Class<? extends Page> getHomePage()
	{
		return ThemeTestPage.class;
	}
}
