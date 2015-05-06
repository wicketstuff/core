package org.wicketstuff.foundation.sidenav;

import java.io.Serializable;

/**
 * Interface for side nav items.
 * @author ilkka
 *
 */
public interface SideNavItem extends Serializable {

	boolean isDivider();
	boolean isHeader();
	boolean isActive();
	String getTitle();
	
}
