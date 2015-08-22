package org.wicketstuff.foundation.sidenav;

/**
 * SideNavItem implementation for header items.
 * @author ilkka
 *
 */
public class SideNavHeaderItem implements SideNavItem {

	private static final long serialVersionUID = 1L;
	
	private String title;

	public SideNavHeaderItem(String title) {
		this.title = title;
	}
	
	@Override
	public boolean isDivider() {
		return false;
	}

	@Override
	public boolean isHeader() {
		return true;
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public String getTitle() {
		return title;
	}
}
