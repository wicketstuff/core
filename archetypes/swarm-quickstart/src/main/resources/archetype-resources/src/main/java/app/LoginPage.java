package ${packageName}.app;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.hive.authorization.SimplePrincipal;
import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.security.hive.authentication.DefaultSubject;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.hive.authentication.UsernamePasswordContext;

/**
 * Login Page. Can be customized by changing the login panel.
 * 
 * @author marrink
 */
public class LoginPage extends WebPage
{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public LoginPage()
	{
		// stateless so the login page will not throw a timeout exception
		// note that is only a hint we need to have stateless components on the
		// page for this to work, like a statelessform
		setStatelessHint(true);
		add(new FeedbackPanel("feedback")
		{

			private static final long serialVersionUID = 1L;

			/**
			 * @see org.apache.wicket.Component#isVisible()
			 */
			public boolean isVisible()
			{
				return anyMessage();
			}
		});
		// The panel contains it's own form
		add(newSignInPanel("signInPanel"));
	}

	/**
	 * Creates a sign in panel with a username and a password field.
	 * 
	 * @param panelId
	 * @return component used for authenticating the user
	 */
	protected Component newSignInPanel(String panelId)
	{
		return new UsernamePasswordSignInPanel(panelId)
		{

			private static final long serialVersionUID = 1L;
			/**
			 * @see ${packageName}.app.UsernamePasswordSignInPanel#signIn(java.lang.String, java.lang.String)
			 */
			public void signIn(String username, String password) throws LoginException
			{
				//a context specialized in handling usernames and passwords
				LoginContext ctx = new UsernamePasswordContext(username, password)
				{
					/**
					 * @see org.apache.wicket.security.hive.authentication.UsernamePasswordContext#getSubject(java.lang.String,
					 *      java.lang.String)
					 */
					protected Subject getSubject(String username, String password) throws LoginException
					{
						//TODO verify username, password, if user is not authenticated throw a LoginException
						DefaultSubject subject= new DefaultSubject();
						//grant principals to the user based on .....
						subject.addPrincipal(new SimplePrincipal("something"));
						return subject;
					}
				};
				//try to authenticate the user using the supplied context
				((WaspSession) getSession()).login(ctx);
			}
		};
	}
}
