package org.wicketstuff.shiro.example.pages;

import org.wicketstuff.shiro.component.LoginPanel;


/**
 * Simple index page
 */
public class LoginPage extends BasePage
{
	/**
	 * Constructor.
	 */
	public LoginPage()
	{
		add(new LoginPanel("login", true));
	}

	@Override
	public String getTitle()
	{
		return "Login Page";
	}
}
