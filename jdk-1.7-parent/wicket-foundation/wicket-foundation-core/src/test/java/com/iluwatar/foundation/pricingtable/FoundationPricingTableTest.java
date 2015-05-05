package com.iluwatar.foundation.pricingtable;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Test;

import com.iluwatar.pricingtable.FoundationPricingTable;
import com.iluwatar.pricingtable.PricingTableButtonPanel;
import com.iluwatar.pricingtable.PricingTableItem;
import com.iluwatar.pricingtable.PricingTableItemType;

/**
 * 
 * Unit test for pricing table component.
 *
 */
public class FoundationPricingTableTest {

	@Test
	public void test() {
		WicketTester tester = new WicketTester();
		List<PricingTableItem> items = new ArrayList<>();
		items.add(new PricingTableItem(PricingTableItemType.TITLE, "Hammer"));
		items.add(new PricingTableItem(PricingTableItemType.PRICE, "12€"));
		items.add(new PricingTableItem(PricingTableItemType.DESCRIPTION, "A really good hammer"));
		items.add(new PricingTableItem(PricingTableItemType.BULLET_ITEM, "Weight 3kg"));
		items.add(new PricingTableItem(PricingTableItemType.CTA_BUTTON));
		FoundationPricingTable table = new FoundationPricingTable("table", new ListModel<>(items)) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer createContent(int idx, String id,
					IModel<PricingTableItem> model) {
				return new PricingTableButtonPanel(id, Model.of("Buy now")) {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						// TODO Auto-generated method stub
					}
				};
			}
			
		};
		tester.startComponentInPage(table);
		tester.debugComponentTrees();
		tester.dumpPage();
		Assert.assertEquals("pricing-table", tester.getTagByWicketId("container").getAttribute("class"));
		Assert.assertEquals(5, tester.getTagsByWicketId("item").size());
	}
}
