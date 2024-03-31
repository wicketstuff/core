package org.wicketstuff.foundation.sidenav;

/**
 * SideNavItem implementation for links.
 * @author ilkka
 *
 */
public class SideNavLinkItem implements SideNavItem {

	private static final long serialVersionUID = 1L;

	private boolean active;
	private String title;

	public SideNavLinkItem(String title) {
		this.title = title;
	}

	public SideNavLinkItem(String title, boolean active) {
		this.active = active;
		this.title = title;
	}

	@Override
	public boolean isDivider() {
		return false;
	}

	@Override
	public boolean isHeader() {
		return false;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public String getTitle() {
		return title;
	}
}
