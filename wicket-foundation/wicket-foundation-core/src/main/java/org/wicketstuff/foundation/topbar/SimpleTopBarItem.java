package org.wicketstuff.foundation.topbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple TopBarItem implementation.
 * @author ilkka
 *
 */
public class SimpleTopBarItem implements TopBarItem {

	private static final long serialVersionUID = 1L;
	
	private String itemId;
	private String text;
	private boolean active;
	private boolean isLabel;
	
	private List<TopBarItem> children = new ArrayList<>();

	public SimpleTopBarItem(String itemId, String text) {
		this(itemId, text, false, false);
	}

	public SimpleTopBarItem(String itemId, String text, boolean active, boolean isLabel) {
		this.itemId = itemId;
		this.text = text;
		this.active = active;
		this.isLabel = isLabel;
	}
	
	@Override
	public String getItemId() {
		return itemId;
	}
	
	@Override
	public String getText() {
		return text;
	}

	@Override
	public List<TopBarItem> getChildren() {
		return children;
	}

	@Override
	public boolean hasChildren() {
		return children.size() > 0;
	}

	@Override
	public void addChild(TopBarItem item) {
		children.add(item);
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public boolean isLabel() {
		return isLabel;
	}
}
