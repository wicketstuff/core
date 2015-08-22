package org.wicketstuff.foundation.subnav;

import java.io.Serializable;

/**
 * Item for FoundationSubNav.
 * @author ilkka
 *
 */
public class SubNavItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String title;
	private boolean active;

	public SubNavItem(String title) {
		this.title = title;
	}	
	
	public SubNavItem(String title, boolean active) {
		this.title = title;
		this.active = active;
	}

	public String getTitle() {
		return title;
	}

	public SubNavItem setTitle(String title) {
		this.title = title;
		return this;
	}

	public boolean isActive() {
		return active;
	}

	public SubNavItem setActive(boolean active) {
		this.active = active;
		return this;
	}
}
