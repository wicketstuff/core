package org.apache.wicket.security.web;

import org.apache.wicket.Application;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.security.WaspApplication;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.app.authentication.ApplicationLoginContext;

/**
 * Basic page. all other pages extend this page either directly or indirectly. Although a
 * few exceptions, like the login page might occur. You can use it to define a common
 * layout, add components that are present on all pages. Feel free to use other super
 * constructors as required by your pages.
 */
public class BasePage extends WebPage
{

	private static final long serialVersionUID = 1L;

	// TODO Add any common page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public BasePage(final PageParameters parameters)
	{
		super(parameters);
		add(new Label("username", new PropertyModel<String>(this, "session.username"))
			.setRenderBodyOnly(true));
		add(new Link<Void>("login")
		{

			private static final long serialVersionUID = 1L;

			/**
			 * @see org.apache.wicket.markup.html.link.Link#onClick()
			 */
			@Override
			public void onClick()
			{
				// by using this exception instead of a setResponsePage we will
				// automatically be returned to this page
				// after the login
				throw new RestartResponseAtInterceptPageException(((WaspApplication) Application
					.get()).getLoginPage());
			}

			/**
			 * @see org.apache.wicket.Component#isVisible()
			 */
			@Override
			public boolean isVisible()
			{
				return !((WaspSession) Session.get()).isUserAuthenticated();
			}
		});
		add(new Link<Void>("logoff")
		{

			private static final long serialVersionUID = 1L;

			/**
			 * @see org.apache.wicket.markup.html.link.Link#onClick()
			 */
			@Override
			public void onClick()
			{
				if (((WaspSession) Session.get()).logoff(new ApplicationLoginContext()))
					setResponsePage(((WaspApplication) Application.get()).getLoginPage());
				else
					error(getLocalizer().getString("exception.logoff", this));

			}

			/**
			 * @see org.apache.wicket.Component#isVisible()
			 */
			@Override
			public boolean isVisible()
			{
				return ((WaspSession) Session.get()).isUserAuthenticated();
			}
		});
		// TODO Add components every page should have here
	}
}
