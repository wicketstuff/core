package org.wicketstuff.facebook;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.crypt.CharEncoding;

/**
 * 
 * @author Till Freier
 * 
 */
public class WicketApplication extends WebApplication
{

	@Override
	public Class<? extends Page> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	protected void init()
	{
		super.init();

		getMarkupSettings().setDefaultMarkupEncoding(CharEncoding.UTF_8);

	}
}