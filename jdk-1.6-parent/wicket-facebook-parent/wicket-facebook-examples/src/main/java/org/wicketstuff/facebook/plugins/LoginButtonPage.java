package org.wicketstuff.facebook.plugins;

import org.apache.wicket.markup.html.WebPage;
import org.wicketstuff.facebook.FacebookAppCredentials;
import org.wicketstuff.facebook.FacebookPermission;
import org.wicketstuff.facebook.behaviors.FacebookSdkBehavior;

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
		add(new FacebookSdkBehavior(new FacebookAppCredentials("142662635778399", "secret")));

		add(new LoginButton("loginButton", FacebookPermission.EMAIL));
	}

}
