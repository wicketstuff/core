package org.wicketstuff.facebook.plugins;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.facebook.FacebookPermission;
import org.wicketstuff.facebook.FacebookSdk;

/**
 * 
 * @author Till Freier
 * 
 */
public class LoginButtonPage extends WebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public LoginButtonPage()
	{
		add(new FacebookSdk("fb-root"));

		add(new LoginButton("loginButton", FacebookPermission.EMAIL));
	}
}
