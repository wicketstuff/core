package org.apache.wicket.security.examples.springsecurity;

import java.net.MalformedURLException;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.security.examples.springsecurity.security.authorization.MyActionFactory;
import org.apache.wicket.security.hive.HiveMind;
import org.apache.wicket.security.hive.config.PolicyFileHiveFactory;
import org.apache.wicket.security.hive.config.SwarmPolicyFileHiveFactory;
import org.apache.wicket.security.swarm.SwarmWebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * Application object for your web application. If you want to run this application
 * without deploying, run the Start class.
 * 
 * @author Olger Warnier
 */
public class SpringSecureWicketApplication extends SwarmWebApplication implements
		SpringSecureApplication
{
	private static final Logger log = LoggerFactory.getLogger(SpringSecureWicketApplication.class);

	private AuthenticationManager authenticationManager;

	/**
	 * Constructor
	 */
	public SpringSecureWicketApplication()
	{
		mountBookmarkablePage("login", LoginPage.class);
	}

	@Override
	public void init()
	{
		super.init();
		addComponentInstantiationListener(new SpringComponentInjector(this));
	}

	@Override
	protected void setUpHive()
	{
		if (HiveMind.getHive(getHiveKey()) == null)
		{
			PolicyFileHiveFactory factory = new SwarmPolicyFileHiveFactory(getActionFactory());
			try
			{
				factory.addPolicyFile(getServletContext().getResource("/WEB-INF/standard.hive"));
			}
			catch (MalformedURLException e)
			{
				throw new WicketRuntimeException(e);
			}
			HiveMind.registerHive(getHiveKey(), factory);
		}
	}

	public AuthenticationManager getAuthenticationManager()
	{
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager)
	{
		this.authenticationManager = authenticationManager;
	}

	@Override
	protected Object getHiveKey()
	{
		return "sectests";
	}

	/**
	 * @see org.apache.wicket.security.swarm.SwarmWebApplication#setupActionFactory()
	 */
	@Override
	protected void setupActionFactory()
	{
		setActionFactory(new MyActionFactory(getClass().getName() + ":" + getHiveKey()));
		if (log.isDebugEnabled())
		{
			log.debug("action factory is setup");
		}
	}

	/**
	 * @see Application#getHomePage()
	 */
	@Override
	public Class< ? extends Page> getHomePage()
	{
		return HomePage.class;
	}

	public Class< ? extends Page> getLoginPage()
	{
		return LoginPage.class;
	}
}
