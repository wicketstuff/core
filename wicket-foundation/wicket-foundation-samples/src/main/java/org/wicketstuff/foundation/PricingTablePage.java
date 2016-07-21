package org.wicketstuff.foundation;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.foundation.pricingtable.FoundationPricingTable;
import org.wicketstuff.foundation.pricingtable.PricingTableButtonPanel;
import org.wicketstuff.foundation.pricingtable.PricingTableItem;
import org.wicketstuff.foundation.pricingtable.PricingTableItemType;

public class PricingTablePage extends BasePage {

	private static final long serialVersionUID = 1L;

	public PricingTablePage(PageParameters params) {
		super(params);
		List<PricingTableItem> items = new ArrayList<>();
		items.add(new PricingTableItem(PricingTableItemType.TITLE, "Hammer"));
		items.add(new PricingTableItem(PricingTableItemType.PRICE, "12€"));
		items.add(new PricingTableItem(PricingTableItemType.DESCRIPTION, "A really good hammer"));
		items.add(new PricingTableItem(PricingTableItemType.BULLET_ITEM, "Weight 3kg"));
		items.add(new PricingTableItem(PricingTableItemType.BULLET_ITEM, "Wooden handle"));
		items.add(new PricingTableItem(PricingTableItemType.BULLET_ITEM, "Good grip"));
		items.add(new PricingTableItem(PricingTableItemType.CTA_BUTTON));
		add(new FoundationPricingTable("pricingTable", new ListModel<>(items)) {
			@Override
			public WebMarkupContainer createContent(int idx, String id,
					IModel<PricingTableItem> model) {
				return new PricingTableButtonPanel(id, Model.of("Buy now")) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						target.appendJavaScript("alert('clicked');");
					}
				};
			}
		});
	}
}
