package ${packageName}.web;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.security.components.ISecurePage;

/**
 * Home page. If your application has a public part that does not require users to login you will want this as your
 * homepage. otherwise the {@link SecureHomePage} might be an alternative. Only pages implementing {@link ISecurePage}
 * or secure {@link Component}s need to be granted permission in application.hive. Even unsecured pages can contain
 * secure components.
 */
public class HomePage extends BasePage
{

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters Page parameters
	 */
	public HomePage(final PageParameters parameters)
	{
		super(parameters);
		// Add the simplest type of label
		add(new Label("message", "If you see this message wicket is properly configured and running"));

		// TODO Add your page's components here
	}
}
