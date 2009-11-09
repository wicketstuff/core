package org.wicketstuff.theme.tester;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.maven.MavenDevResourceStreamLocator;

public class WicketThemeTestApp extends WebApplication
{
	private static final Logger _logger = LoggerFactory.getLogger(WicketThemeTestApp.class);
	
	@Override
	protected void init()
	{
		super.init();
		
		if (DEVELOPMENT.equals(getConfigurationType()))
		{
			_logger.error("Debug Mode");
			getResourceSettings().setResourceStreamLocator(new MavenDevResourceStreamLocator());
		}
		else
		{
			_logger.error("NO Debug Mode");
		}
	}
	
	@Override
	public Class<? extends Page> getHomePage()
	{
		return ThemeTestPage.class;
	}
}
