package org.apache.wicket.security.examples.springsecurity;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.security.components.SecureWebPage;
import org.apache.wicket.security.components.markup.html.links.SecurePageLink;

/**
 * Second Secure Page
 * 
 * @author Olger Warnier
 */
public class SecondSecurePage extends SecureWebPage
{

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public SecondSecurePage(final PageParameters parameters)
	{

		// Add the simplest type of label
		add(new Label("message", "This is the first secure page"));
		add(new SecurePageLink<Void>("to_firstsecurepage", FirstSecurePage.class));
		add(new SecurePageLink<Void>("to_homepage", HomePage.class));
		// TODO Add your page's components here
	}
}