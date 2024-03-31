package org.wicketstuff.gae;

import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for your web application. If you want to run this application without
 * deploying, run the Start class (in tests).
 */
public class GaeWicketApplication extends WebApplication implements GaeApplication
{
	/**
	 * Constructor
	 */
	public GaeWicketApplication()
	{
	}

	@Override
	public int getMaxPages()
	{
		return 10;
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}
}