package org.wicketstuff.yui.markup.html.menu2;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

public class YuiMenuItem extends AbstractYuiMenuItem {
	private static final long serialVersionUID = 1L;
	public static final String MENU_ITEM_ID = "menuItem";
	public static final String LINK_ID = "link";

	public YuiMenuItem(final IYuiMenuAction action) {
		super(MENU_ITEM_ID);

		AbstractLink link = null;
		if (action instanceof AbstractLink) {
			link = (AbstractLink) action;
			if (link.getId().equals(LINK_ID) == false) {
				throw new RuntimeException("Link's id needs to be 'link' ");
			}
		} else {
			link = new Link(LINK_ID, action.getName()) {
				private static final long serialVersionUID = 1L;

				public void onClick() {
					action.onClick();
				}
			};
		}
		add( link );
		link.add(new Label("linkLabel", action.getName())
				.setRenderBodyOnly(true));
		newSubMenu("emptyMenu").setVisible(false);
	}

	public YuiMenuItem(final String label, final AbstractLink link) {
		super(MENU_ITEM_ID);
		if (link.getId().equals(LINK_ID) == false) {
			throw new RuntimeException("Link's id needs to be 'link' ");
		}

		add(link);
		link.add(new Label("linkLabel", new Model(label))
				.setRenderBodyOnly(true));
		newSubMenu("emptyMenu").setVisible(false);
	}

	@Override
	public String getMenuClass() {
		return "yuimenuitem";
	}

}
