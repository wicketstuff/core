package org.wicketstuff.shiro.example.pages;

import org.wicketstuff.shiro.component.LoginPanel;


/**
 * Simple index page
 */
public class LoginPage extends BasePage
{
	private static final long serialVersionUID = 1L;

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
