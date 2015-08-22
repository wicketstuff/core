package ${packageName}.app;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.authentication.LoginException;
import ${packageName}.app.WicketApplication.MySession;
import ${packageName}.app.authentication.ApplicationLoginContext;

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
				// try to authenticate the user using the supplied context
				((WaspSession) getSession()).login(new ApplicationLoginContext(username, password));
				// store username in session
				((MySession) Session.get()).setUsername(username);
			}
		};
	}
}
