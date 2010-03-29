/*
 * This piece of code is dedicated to the wicket project (http://www.wicketframework.org).
 */
package org.wicketstuff.menu;


import java.util.List;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.panel.Panel;


/**
 * This is a internal class. It is responsible for rendering a visible {@link Menu}.
 *
 * @author Stefan Lindner (lindner@visionet.de)
 */
class MenuPanel extends Panel {

	private static final long serialVersionUID = 1L;
	private static final SimpleAttributeModifier disabledMenuItem = new SimpleAttributeModifier("class", "disabled");

	private List<MenuItem> menuItems;

	protected MenuPanel(final String id, List<MenuItem> menuItems) {
		super(id);
		if (menuItems == null) {
			throw new IllegalArgumentException("argument [menuItems] cannot be null");
		}
		if (menuItems.size() < 1) {
			throw new IllegalArgumentException(
					"argument [menuItems] must contain a list of at least one menuItem");
		}

		this.menuItems = menuItems;

		// add the loop used to generate menu items
		add(new Loop("items", menuItems.size()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(LoopItem item) {
				final int index = item.getIteration();
				final MenuItem menuItem = MenuPanel.this.menuItems.get(index);

				if (menuItem.isVisible()) {
					if (menuItem.isEnabled())
						item.add(new MenuLinkPanel("menuItem", menuItem));
					else {
						item.add(new Label("menuItem", menuItem.getModel()).setRenderBodyOnly(true));
						item.add(disabledMenuItem);
					}
				}
				else
					item.setVisible(false);
			}

		});
	}
}
