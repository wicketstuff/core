package ${packageName}.app.authentication;

import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.security.hive.authentication.DefaultSubject;
import org.apache.wicket.security.hive.authentication.LoginContext;
import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.hive.authentication.UsernamePasswordContext;
import org.apache.wicket.security.hive.authorization.SimplePrincipal;

/**
 * {@link LoginContext} that uses a simple username and password to authenticate users. A logincontext is used for both
 * logging in and off.
 * 
 * @author marrink
 */
public class ApplicationLoginContext extends UsernamePasswordContext
{

	/**
	 * Use this constructor to logoff.
	 */
	public ApplicationLoginContext()
	{
	}

	/**
	 * Constructor if you want to use this context to authenticate users.
	 * 
	 * @param username
	 * @param password
	 */
	public ApplicationLoginContext(String username, String password)
	{
		super(username, password);
	}

	/**
	 * @see org.apache.wicket.security.hive.authentication.UsernamePasswordContext#getSubject(java.lang.String,
	 *      java.lang.String)
	 */
	protected Subject getSubject(String username, String password) throws LoginException
	{
		// TODO verify username, password, if user is not authenticated throw a LoginException
		DefaultSubject subject = new DefaultSubject();
		// grant principals to the user based on .....
		subject.addPrincipal(new SimplePrincipal("something"));
		return subject;
	}

}
