package org.wicketstuff.datetime.examples;

import org.apache.wicket.Page;
import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.crypt.NoCryptFactory;
import org.wicketstuff.datetime.examples.dates.DatesPage;

public class Application extends WebApplication
{
	/**
	 * prevent wicket from launching a java application window on the desktop <br/>
	 * once someone uses awt-specific classes java will automatically do so and allocate a window
	 * unless you tell java to run in 'headless-mode'
	 */
	static
	{
		System.setProperty("java.awt.headless", "true");
	}

	@Override
	protected void init()
	{
		getCspSettings().blocking().disabled();
		// WARNING: DO NOT do this on a real world application unless
		// you really want your app's passwords all passed around and
		// stored in unencrypted browser cookies (BAD IDEA!)!!!

		// The NoCrypt class is being used here because not everyone
		// has the java security classes required by Crypt installed
		// and we want them to be able to run the examples out of the
		// box.
		getSecuritySettings().setCryptFactory(new NoCryptFactory());

		getDebugSettings().setDevelopmentUtilitiesEnabled(true);
		new BeanValidationConfiguration().configure(this);
	}

	@Override
	public Class<? extends Page> getHomePage()
	{
		return DatesPage.class;
	}
}
