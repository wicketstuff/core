package org.wicketstuff.foundation.breadcrumbs;

import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
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
 * Breadcrumbs come in handy to show a navigation trail for users clicking through a site or app.
 * http://foundation.zurb.com/docs/components/breadcrumbs.html
 * @author ilkka
 *
 */
public abstract class FoundationBreadcrumbs extends FoundationBasePanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create FoundationBreadcrumbs.
	 * @param id - Wicket id.
	 * @param items - List of breadcrumbs items.
	 */
	public FoundationBreadcrumbs(String id, List<BreadcrumbsItem> items) {
		this(id, new ListModel<>(items));
	}
	
	/**
	 * Create FoundationBreadcrumbs.
	 * @param id - Wicket id.
	 * @param itemsModel - Model providing the list of breadcrumbs items.
	 */
	public FoundationBreadcrumbs(String id, IModel<List<BreadcrumbsItem>> itemsModel) {
		super(id);		
		BreadcrumbsContainer breadcrumbsContainer = new BreadcrumbsContainer("breadcrumbsContainer");
		add(breadcrumbsContainer);
		breadcrumbsContainer.add(new ListView<BreadcrumbsItem>("breadcrumbsItem", itemsModel) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<BreadcrumbsItem> item) {
				BreadcrumbsItem breadcrumbsItem = item.getModelObject();
				if (breadcrumbsItem.isCurrent()) {
					item.add(new AttributeAppender("class", "current"));
				}
				if (breadcrumbsItem.isDisabled()) {
					item.add(new AttributeAppender("class", "unavailable"));
				}
				AbstractLink link = createLink("breadcrumbsItemLink", item.getIndex());
				item.add(link);
				link.add(new Label("breadcrumbsItemTitle", breadcrumbsItem.getTitle()));
			}
		});
	}
	
	/**
	 * Create link for FoundationBreadcrumbs.
	 * @param id - Wicket id.
	 * @param idx - Index number of the item.
	 * @return AbstractLink
	 */
	public abstract AbstractLink createLink(String id, int idx);
	
	private static class BreadcrumbsContainer extends WebMarkupContainer {

		private static final long serialVersionUID = 1L;

		public BreadcrumbsContainer(String id) {
			super(id);
		}
		
		@Override
		protected void onComponentTag(ComponentTag tag) {
			Attribute.setClass(tag, "breadcrumbs");
			super.onComponentTag(tag);
		}
	}
}
