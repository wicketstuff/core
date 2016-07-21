package org.apache.wicket.security.examples.springsecurity;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

/**
 * Homepage
 * 
 * @author Olger Warnier
 */
public class HomePage extends WebPage
{

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public HomePage(final PageParameters parameters)
	{

		// Add the simplest type of label
		add(new Label("message",
			"If you see this message wicket is properly configured and running"));
		add(new BookmarkablePageLink<Void>("to_firstsecure", FirstSecurePage.class));
		add(new BookmarkablePageLink<Void>("to_login", LoginPage.class));

	}
}
