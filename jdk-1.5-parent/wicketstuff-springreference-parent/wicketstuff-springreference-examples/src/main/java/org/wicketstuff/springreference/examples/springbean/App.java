package org.wicketstuff.springreference.examples.springbean;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.wicketstuff.springreference.examples.AbstractApp;
import org.wicketstuff.springreference.examples.AbstractFinalPage;
import org.wicketstuff.springreference.examples.AbstractPrivatePage;

/**
 * Class representing a wicket web application. This application uses exclusively wicket-spring,
 * <code>&#64;{@link SpringBean}</code> and {@link SpringComponentInjector} for spring integration.
 * 
 * @author akiraly
 */
public class App extends AbstractApp
{

	@Override
	protected void init()
	{
		super.init();

		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
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
