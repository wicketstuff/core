package org.wicketstuff.yav.examples;

import org.apache.wicket.protocol.http.WebApplication;


/**
 * @author Zenika
 */
public class WicketApplication extends WebApplication
{

	/**
	 * Constructor
	 */
	public WicketApplication()
	{
	}

	@Override
	protected void init()
	{
		super.init();

		getRequestLoggerSettings().setRequestLoggerEnabled(true);
	}

	/**
	 * @see wicket.Application#getHomePage()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Class getHomePage()
	{
		return TestPage.class;
	}

}
