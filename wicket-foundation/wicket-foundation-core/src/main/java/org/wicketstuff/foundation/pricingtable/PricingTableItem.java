package org.wicketstuff.foundation.pricingtable;

/**
 * 
 * Pricing table item.
 *
 */
public class PricingTableItem {

	private PricingTableItemType itemType;
	private String content;

	public PricingTableItem(PricingTableItemType itemType) {
		this(itemType, null);
	}
	
	public PricingTableItem(PricingTableItemType itemType, String content) {
		this.itemType = itemType;
		this.content = content;
	}
	
	public PricingTableItemType getItemType() {
		return itemType;
	}
	
	public String getContent() {
		return content;
	}
}
