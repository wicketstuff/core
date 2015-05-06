package org.wicketstuff.foundation.pricingtable;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.wicketstuff.foundation.component.FoundationBasePanel;
import org.wicketstuff.foundation.util.Attribute;
import org.wicketstuff.foundation.util.StringUtil;

/**
 * 
 * Pricing table component.
 * 
 * Supports simple string content and complex panel content types.
 *
 */
public abstract class FoundationPricingTable extends FoundationBasePanel {

	private static final long serialVersionUID = 1L;

	public FoundationPricingTable(String id, IModel<List<PricingTableItem>> itemsModel) {
		super(id);
		
		PricingTableContainer container = new PricingTableContainer("container");
		add(container);
		
		container.add(new ListView<PricingTableItem>("item", itemsModel) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<PricingTableItem> item) {
				if (item.getModelObject().getContent() != null) {
					item.add(new Label("content", item.getModelObject().getContent()));
				} else {
					item.add(FoundationPricingTable.this.createContent(item.getIndex(), "content", item.getModel()));
				}
				item.add(new AttributeModifier("class", StringUtil.EnumNameToCssClassName(item.getModelObject().getItemType().name())));
			}
		});
	}
	
	public abstract WebMarkupContainer createContent(int idx, String id, IModel<PricingTableItem> model);
	
	private static class PricingTableContainer extends WebMarkupContainer {

		private static final long serialVersionUID = 1L;

		public PricingTableContainer(String id) {
			super(id);
		}
		
		@Override
		protected void onComponentTag(ComponentTag tag) {
			Attribute.addClass(tag, "pricing-table");
			super.onComponentTag(tag);
		}
	}
}
