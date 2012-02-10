package org.wicketstuff.springreference.examples.springreference;

import org.wicketstuff.springreference.SpringReference;
import org.wicketstuff.springreference.SpringReferenceSupporter;
import org.wicketstuff.springreference.examples.AbstractApp;
import org.wicketstuff.springreference.examples.AbstractFinalPage;
import org.wicketstuff.springreference.examples.AbstractPrivatePage;

/**
 * Class representing a wicket web application. This application uses exclusively
 * wicketstuff-springreference, {@link SpringReference} and {@link SpringReferenceSupporter} for
 * spring integration.
 * 
 * @author akiraly
 */
public class App extends AbstractApp
{

	@Override
	protected void init()
	{
		super.init();

		SpringReferenceSupporter.register(this);
	}

	@Override
	public Class<? extends AbstractFinalPage> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	public Class<? extends AbstractPrivatePage> getPrivatePage()
	{
		return PrivatePage.class;
	}
}
