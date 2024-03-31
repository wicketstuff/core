package org.wicketstuff.osgi.test.library.web;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.wicketstuff.osgi.OsgiClassResolver;
import org.wicketstuff.osgi.inject.OsgiComponentInjector;

public class LibraryApplication extends WebApplication
{

	@Override
	protected void init()
	{
		super.init();
		getComponentInstantiationListeners().add(new OsgiComponentInjector());

		/*
		 * Not really needed, at least on Jetty.
		 */
		getApplicationSettings().setClassResolver(new OsgiClassResolver());
	}

	@Override
	public Class<? extends Page> getHomePage()
	{
		return AddAuthorPage.class;
	}
}
