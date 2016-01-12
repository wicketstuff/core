package org.wicketstuff.foundation.topbar;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for top bar items.
 * @author ilkka
 *
 */
public interface TopBarItem extends Serializable {

	String getItemId();
	String getText();
	boolean isActive();
	boolean isLabel();
	List<TopBarItem> getChildren();
	boolean hasChildren();
	void addChild(TopBarItem item);
	
}
