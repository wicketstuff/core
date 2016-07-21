package org.apache.wicket.security.web;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;

/**
 * Home page. If your application does not have a public part for anonymous users you will
 * want to use this as your homepage since at the very least it forces your users to be
 * authenticated before accessing this page. Otherwise the {@link HomePage} might be an
 * alternative.
 */
public class SecureHomePage extends SecurePage
{

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public SecureHomePage(final PageParameters parameters)
	{
		super(parameters);
		// Add the simplest type of label
		add(new Label("message",
			"If you see this message wicket is properly configured and running"));

		// TODO Add your page's components here
	}
}
