/*
 * This piece of code is dedicated to the wicket project (http://www.wicketframework.org).
 */
package org.wicketstuff.menu;


import java.io.Serializable;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.markup.html.panel.Panel;


/**
 * This is a internal class. It is responsible for rendering a visible {@link MenuItem}, it's link and the associated label.
 *
 * @author Stefan Lindner (lindner@visionet.de)
 */
class MenuLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	protected MenuLinkPanel(final String id, final MenuItem menuItem) {
		super(id);
		if (menuItem == null) {
			throw new IllegalArgumentException("argument [menuItem] cannot be null");
		}

		switch (menuItem.getMenuItemType()) {
			case PAGE_LINK:
				Link<Serializable> pageLink = new Link<Serializable>("link"){
					private static final long serialVersionUID = 1L;
					@Override
					public void onClick() {
						setResponsePage(menuItem.getPageLink().getPage());
					}
					
				};
				pageLink.add(new Label("linkLabel", menuItem.getModel().getObject())/*.setRenderBodyOnly(true)*/.setEscapeModelStrings(menuItem.getEscapeModelStrings()));
				add(pageLink);
				break;
			case LINK_LISTENER:
				Link<String> linkListener = new Link<String>("link", menuItem.getModel()) {
					private static final long serialVersionUID = 1L;
					public void onClick() {
						menuItem.getLinkListener().onLinkClicked();
					}
				};
				linkListener.add(new Label("linkLabel", menuItem.getModel().getObject())/*.setRenderBodyOnly(true)*/.setEscapeModelStrings(menuItem.getEscapeModelStrings()));
				add(linkListener);
				break;
			case EXTERNAL_URL:
				PopupSettings popupSettings = new PopupSettings(PopupSettings.RESIZABLE | PopupSettings.SCROLLBARS | PopupSettings.MENU_BAR | PopupSettings.STATUS_BAR);
				ExternalLink externalLink = new ExternalLink("link", menuItem.getExternalUrl()).setPopupSettings(popupSettings);
				externalLink.add(new Label("linkLabel", menuItem.getModel().getObject())/*.setRenderBodyOnly(true)*/.setEscapeModelStrings(menuItem.getEscapeModelStrings()));
				add(externalLink);
				break;
		}
		this.setRenderBodyOnly(true);
	}

}
