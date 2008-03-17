package ${packageName}.web;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;

/**
 * Basic page. all other pages extend this page either directly or indirectly. Although a few exceptions, like the login page might occur. You can use
 * it to define a common layout, add components that are present on all pages. Feel free to use other super constructors
 * as required by your pages.
 */
public class BasePage extends WebPage
{

	private static final long serialVersionUID = 1L;

	// TODO Add any common page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters Page parameters
	 */
	public BasePage(final PageParameters parameters)
	{
		super(parameters);

		// TODO Add components every page should have here
	}
}
