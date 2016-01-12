package org.wicketstuff.springreference.examples;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.crypt.CharEncoding;

/**
 * Common {@link WebApplication} ancestor for the example applications. Every application has two
 * example pages: an {@link AbstractFinalPage} subclass that also serves as home page and an
 * {@link AbstractPrivatePage} subclass.
 * 
 * @author akiraly
 */
public abstract class AbstractApp extends WebApplication
{

	@Override
	public abstract Class<? extends AbstractFinalPage> getHomePage();

	/**
	 * @return {@link AbstractPrivatePage} implementation used by this application
	 */
	public abstract Class<? extends AbstractPrivatePage> getPrivatePage();

	@Override
	protected void init()
	{
		super.init();

		getMarkupSettings().setDefaultMarkupEncoding(CharEncoding.UTF_8);
	}

	/**
	 * @return current application instance
	 */
	public static AbstractApp get()
	{
		return (AbstractApp)WebApplication.get();
	}
}
