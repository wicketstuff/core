package org.wicketstuff.foundation.sidenav;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.wicketstuff.foundation.component.FoundationBasePanel;
import org.wicketstuff.foundation.util.Attribute;

/**
 * Side nav, like you see on the Foundation docs, is a great way to provide navigation for your entire site, or for 
 * sections of an individual page.
 * http://foundation.zurb.com/docs/components/sidenav.html
 * @author ilkka
 *
 */
public abstract class FoundationSideNav extends FoundationBasePanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create FoundationSideNav.
	 * @param id - Wicket id.
	 * @param items - List of side nav items.
	 */
	public FoundationSideNav(String id, List<SideNavItem> items) {
		this(id, new ListModel<>(items));
	}
	
	/**
	 * Create FoundationSideNav.
	 * @param id - Wicket id.
	 * @param itemModels - Model for side nav items.
	 */
	public FoundationSideNav(String id, IModel<List<SideNavItem>> itemModels) {
		super(id);
		SideNavContainer sideNavContainer = new SideNavContainer("sideNavContainer");
		add(sideNavContainer);
		sideNavContainer.add(new ListView<SideNavItem>("sideNavItem", itemModels) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<SideNavItem> item) {
				SideNavItem sideNavItem = item.getModelObject();
				if (sideNavItem.isDivider()) {
					item.add(new AttributeModifier("class", "divider"));
				}
				else if (sideNavItem.isActive()) {
					item.add(new AttributeModifier("class", "active"));
				}
				else if (sideNavItem.isHeader()) {
					item.add(new AttributeModifier("class", "heading"));
				}
				AbstractLink sideNavItemLink = createLink("sideNavItemLink", item.getIndex());
				item.add(sideNavItemLink);
				sideNavItemLink.add(new Label("sideNavItemText", sideNavItem.getTitle()));
				if (sideNavItem.isDivider() || sideNavItem.isHeader()) {
					sideNavItemLink.setVisible(false);
				}
				Label sideNavHeader = new Label("sideNavHeader", sideNavItem.getTitle());
				sideNavHeader.setVisible(sideNavItem.isHeader());
				item.add(sideNavHeader);
			}
		});
	}
	
	public abstract AbstractLink createLink(String id, int idx);
	
	private static class SideNavContainer extends WebMarkupContainer {

		private static final long serialVersionUID = 1L;

		public SideNavContainer(String id) {
			super(id);
		}
		
		@Override
		protected void onComponentTag(ComponentTag tag) {
			Attribute.setClass(tag, "side-nav");
			super.onComponentTag(tag);
		}
	}
}
