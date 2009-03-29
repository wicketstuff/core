package org.wicketstuff.ki.example.pages;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.wicketstuff.ki.component.KiConfigInfoPanel;
import org.wicketstuff.ki.component.SimpleAuthHeader;
import org.wicketstuff.ki.example.ExampleApplication;

/**
 * Simple index page
 */
public abstract class BasePage extends WebPage
{
	/**
	 * Constructor.
	 */
	public BasePage()
	{
		add(new Label("title", getTitle()));
		add(new SimpleAuthHeader("headerAuth", LoginPage.class));

		WebMarkupContainer links = new WebMarkupContainer("links");
		links.add(new BookmarkablePageLink<Void>("home", IndexPage.class));
		links.add(new BookmarkablePageLink<Void>("user", RequireLoginPage.class));
		links.add(new BookmarkablePageLink<Void>("admin", RequireAdminRolePage.class));
    links.add(new BookmarkablePageLink<Void>("auth", RequireAuthPage.class));
    links.add(new BookmarkablePageLink<Void>("view", RequireViewPermissionPage.class));
		add(links);

		add( new Label("appname", getApplication().getClass().getName() ) );
    add( ((ExampleApplication)getApplication()).getExampleInfoPanel("example") );
		add(new KiConfigInfoPanel("info"));
	}
	

	abstract String getTitle();
}
