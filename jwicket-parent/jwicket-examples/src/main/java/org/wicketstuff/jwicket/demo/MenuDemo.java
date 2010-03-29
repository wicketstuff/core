package org.wicketstuff.jwicket.demo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.PageLink;
import org.apache.wicket.model.Model;
import org.wicketstuff.menu.IMenuLink;
import org.wicketstuff.menu.Menu;
import org.wicketstuff.menu.MenuBarPanel;


public class MenuDemo extends MenuBarPanel implements Serializable {

	private static final long serialVersionUID = 1L;


	public MenuDemo(final String id) {
		super(id, new ArrayList<Menu>());
		
		List<IMenuLink> itemsForMenu1 = new ArrayList<IMenuLink>();

		// Link to Homepage
		itemsForMenu1.add(new IMenuLink() {
			private static final long serialVersionUID = 1L;
			@Override
			public Component getDisplayComponent(String id) {
				//return new Label(id, "Home");
				return new Image(id, new ResourceReference(MenuDemo.class, "P_orange_81x81.gif"));
			}
			@Override
			public AbstractLink getLink(String id) {
				return new BookmarkablePageLink<Void>(id, Application.get().getHomePage());
			}
		});

		// Link to Apache Wicket
		itemsForMenu1.add(new IMenuLink() {
			private static final long serialVersionUID = 1L;
			@Override
			public Component getDisplayComponent(String id) {
				return new Label(id, "Apache Wicket");
			}
			@Override
			public AbstractLink getLink(String id) {
				return new ExternalLink(id, "http://www.wicketframework.org");
			}
		});

		// Link to Wicketstuff
		itemsForMenu1.add(new IMenuLink() {
			private static final long serialVersionUID = 1L;
			@Override
			public Component getDisplayComponent(String id) {
				return new Label(id, "Wicketstuff");
			}
			@Override
			public AbstractLink getLink(String id) {
				return new ExternalLink(id, "http://www.wicketstuff.org");
			}
		});
		
		
		Menu menu1 = new Menu(new Model<String>("Pages"), itemsForMenu1);
		menus.add(menu1);

		
		
		
		
		
		
		
		
		
		List<IMenuLink> itemsForMenu2 = new ArrayList<IMenuLink>();

		// Link to Wicketstuff
		itemsForMenu2.add(new IMenuLink() {
			private static final long serialVersionUID = 1L;
			@Override
			public Component getDisplayComponent(String id) {
				return new Label(id, "AjaxLink");
			}
			@Override
			public AbstractLink getLink(String id) {
				return new AjaxFallbackLink<Void>(id) {
					private static final long serialVersionUID = 1L;
					@Override
					public void onClick(AjaxRequestTarget target) {
						System.out.println("AjaxKlick");
					}
				};
			}
		});
		
		
		Menu menu2 = new Menu(new Model<String>("Ajax"), itemsForMenu2);
		menus.add(menu2);
		
	}
}
