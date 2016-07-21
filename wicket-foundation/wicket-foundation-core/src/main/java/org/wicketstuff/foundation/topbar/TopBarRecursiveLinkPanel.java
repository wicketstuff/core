package org.wicketstuff.foundation.topbar;

import java.util.Iterator;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

abstract class TopBarRecursiveLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public TopBarRecursiveLinkPanel(String id, TopBarItem topBarItem) {
		super(id);
		if (!topBarItem.hasChildren()) {
			// label
			WebMarkupContainer labelContainer = new WebMarkupContainer("labelContainer");
			add(labelContainer);
			labelContainer.add(new Label("label", topBarItem.getText()));
			labelContainer.setVisible(topBarItem.isLabel());
			// link
			AbstractLink link = createLink("link", topBarItem.getItemId());
			add(link);
			link.add(new Label("text", topBarItem.getText()));
			link.setVisible(!topBarItem.isLabel());
			// dropdown
			WebMarkupContainer dropdown = new WebMarkupContainer("dropdown");
			dropdown.setVisible(false);
			add(dropdown);
			dropdown.add(new WebMarkupContainer("item"));
		} else {
			add(new AttributeModifier("class", "has-dropdown"));
			// label
			WebMarkupContainer labelContainer = new WebMarkupContainer("labelContainer");
			add(labelContainer);
			labelContainer.add(new Label("label", topBarItem.getText()));
			labelContainer.setVisible(topBarItem.isLabel());
			// link
			WebMarkupContainer link = new WebMarkupContainer("link");
			add(link);
			link.add(new Label("text", topBarItem.getText()));
			link.setVisible(!topBarItem.isLabel());
			// dropdown
			WebMarkupContainer dropdown = new WebMarkupContainer("dropdown");
			add(dropdown);
			dropdown.setVisible(!topBarItem.isLabel());
			RepeatingView rv = new RepeatingView("item");
			dropdown.add(rv);
			Iterator<TopBarItem> childIterator = topBarItem.getChildren().iterator();
			while (childIterator.hasNext()) {
				final TopBarItem child = childIterator.next();
				rv.add(new TopBarRecursiveLinkPanel(rv.newChildId(), child) {

					private static final long serialVersionUID = 1L;

					@Override
					public AbstractLink createLink(String id, String itemId) {
						return TopBarRecursiveLinkPanel.this.createLink(id, itemId);
					}
				});
			}
		}
		if (topBarItem.isActive()) {
			add(new AttributeAppender("class", "active"));
		}
	}
	
	public abstract AbstractLink createLink(String id, String itemId);
}
