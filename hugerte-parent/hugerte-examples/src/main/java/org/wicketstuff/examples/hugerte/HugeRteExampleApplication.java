package org.wicketstuff.examples.hugerte;

import org.apache.wicket.protocol.http.WebApplication;

import de.agilecoders.wicket.webjars.WicketWebjars;

/**
 * @author Iulian-Corneliu COSTAN
 */
public class HugeRteExampleApplication extends WebApplication
{

	public HugeRteExampleApplication()
	{
	}

	@Override
	protected void init()
	{
		super.init();
		WicketWebjars.install(this);

		getCspSettings().blocking().clear();
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class getHomePage()
	{
		return HugeRteBasePage.class;
	}
}
