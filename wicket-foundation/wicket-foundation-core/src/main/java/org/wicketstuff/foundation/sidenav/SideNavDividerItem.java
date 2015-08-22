package org.wicketstuff.foundation.sidenav;

/**
 * SideNavItem implementation for divider items.
 * @author ilkka
 *
 */
public class SideNavDividerItem implements SideNavItem {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean isDivider() {
		return true;
	}

	@Override
	public boolean isHeader() {
		return false;
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public String getTitle() {
		return "";
	}

}
