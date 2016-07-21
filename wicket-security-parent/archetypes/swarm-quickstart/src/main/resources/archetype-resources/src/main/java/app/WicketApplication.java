package ${packageName}.app;

import java.net.MalformedURLException;

import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.security.WaspApplication;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.hive.HiveMind;
import org.apache.wicket.security.hive.config.PolicyFileHiveFactory;
import org.apache.wicket.security.hive.config.SwarmPolicyFileHiveFactory;
import org.apache.wicket.security.swarm.SwarmWebApplication;
import org.apache.wicket.util.string.Strings;
import ${packageName}.web.SecureHomePage;

/**
 * Default settings for a secure wicket application.
 * @author marrink
 */
public class WicketApplication extends SwarmWebApplication
{

	/**
	 * Constructor.
	 */
	public WicketApplication()
	{
		super();
	}
	/**
	 * 
	 * @see org.apache.wicket.security.swarm.SwarmWebApplication#init()
	 */
	protected void init()
	{
		//You must call super!!
		super.init();
		
		//TODO do custom initialization here
	}

	/**
	 * @see org.apache.wicket.security.swarm.SwarmWebApplication#getHiveKey()
	 */
	protected Object getHiveKey()
	{
		// if you are using servlet api 2.5 i would suggest using:
		// return getServletContext().getContextPath();

		// if not you have several options:
		// -an initparam in web.xml
		// -a static object
		// -a random object
		// -whatever you can think of

		// for this quickstart we will be using a fixed string
		return "${artifactId}";
	}

	/**
	 * @see org.apache.wicket.security.swarm.SwarmWebApplication#setUpHive()
	 */
	protected void setUpHive()
	{
		// create factory to read policy files
		PolicyFileHiveFactory factory = new SwarmPolicyFileHiveFactory(getActionFactory());
		try
		{
			// this quickstart uses 1 policy file but you can add as many as you like
			factory.addPolicyFile(getServletContext().getResource("/WEB-INF/application.hive"));
			// to avoid having to type the full packagename we declare an alias
			factory.setAlias("web", "${packageName}.web");
			//alias for the principals
			factory.setAlias("principal","org.apache.wicket.security.hive.authorization.SimplePrincipal");
		}
		catch (MalformedURLException e)
		{
			throw new WicketRuntimeException(e);
		}
		// register hive that will be created by the factory
		HiveMind.registerHive(getHiveKey(), factory);
	}

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class getHomePage()
	{
		return SecureHomePage.class;
		// optionally you can use HomePage.class
	}

	/**
	 * @see org.apache.wicket.security.WaspApplication#getLoginPage()
	 */
	public Class getLoginPage()
	{
		return LoginPage.class;
	}
	/**
	 * Optionally you can override {@link #newSession(Request, Response)} to store information in the session. Just make
	 * sure your session always extends {@link WaspSession}.
	 * 
	 * @see org.apache.wicket.security.WaspWebApplication#newSession(org.apache.wicket.Request,
	 *      org.apache.wicket.Response)
	 */
	public Session newSession(Request request, Response response)
	{
		return new MySession(this, request);
	}
	/**
	 * Custom session to store the username of the user.
	 * 
	 * @author marrink
	 */
	public static final class MySession extends WaspSession
	{

		private static final long serialVersionUID = 1L;

		private String username = "guest";

		/**
		 * Constructor.
		 * 
		 * @param application
		 * @param request
		 */
		public MySession(WaspApplication application, Request request)
		{
			super(application, request);
		}

		/**
		 * @return the username
		 */
		public final String getUsername()
		{
			return username;
		}

		/**
		 * @param username the username to set
		 */
		public final void setUsername(String username)
		{
			if(Strings.isEmpty(username))
				this.username = "guest";
			else
				this.username = username;
		}
		/**
		 * Override logoff to reset the username.
		 * 
		 * @see org.apache.wicket.security.WaspSession#logoff(java.lang.Object)
		 */
		public boolean logoff(Object context)
		{
			// quick check to see if the user logged off
			if(super.logoff(context))
			{
				// then reset username
				setUsername(null);
				return true;
			}
			return false;
		}
	}
}
