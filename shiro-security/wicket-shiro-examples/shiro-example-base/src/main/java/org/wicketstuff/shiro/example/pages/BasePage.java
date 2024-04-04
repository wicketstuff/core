package org.wicketstuff.shiro.example.pages;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.wicketstuff.shiro.component.ShiroConfigInfoPanel;
import org.wicketstuff.shiro.example.ExampleApplication;

/**
 * Simple index page
 */
public abstract class BasePage extends WebPage
{
	private static final long serialVersionUID = 1L;


	/**
	 * Constructor.
	 */
	public BasePage()
	{
		add(new Label("title", getTitle()));
// add(new SimpleAuthHeader("headerAuth", LoginPage.class));
		add(((ExampleApplication)getApplication()).getAuthHeaderPanel("headerAuth"));

		WebMarkupContainer links = new WebMarkupContainer("links");
		links.add(new BookmarkablePageLink<Void>("home", IndexPage.class));
		links.add(new BookmarkablePageLink<Void>("admin", RequireAdminRolePage.class));
		links.add(new BookmarkablePageLink<Void>("auth", RequireAuthPage.class));
		links.add(new BookmarkablePageLink<Void>("view", RequireViewPermissionPage.class));
		add(links);

		add(new Label("appname", getApplication().getClass().getName()));
		add(((ExampleApplication)getApplication()).getExampleInfoPanel("example"));
		add(new ShiroConfigInfoPanel("info"));
	}


	abstract String getTitle();

}
