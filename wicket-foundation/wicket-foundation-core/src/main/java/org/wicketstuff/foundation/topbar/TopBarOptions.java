package org.wicketstuff.foundation.topbar;

import java.io.Serializable;
import java.util.EnumSet;

/**
 * Options for the FoundationTopBar.
 * @author ilkka
 *
 */
public class TopBarOptions implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean fixed;
	private boolean containToGrid;
	private boolean sticky;
	private EnumSet<TopBarStickySize> stickySizes = EnumSet.noneOf(TopBarStickySize.class);
	private boolean clickable;

	public boolean isFixed() {
		return fixed;
	}
	
	public TopBarOptions setFixed(boolean fixed) {
		this.fixed = fixed;
		return this;
	}
	
	public boolean isContainToGrid() {
		return containToGrid;
	}
	
	public TopBarOptions setContainToGrid(boolean containToGrid) {
		this.containToGrid = containToGrid;
		return this;
	}

	public boolean isSticky() {
		return sticky;
	}

	public TopBarOptions setSticky(boolean sticky) {
		this.sticky = sticky;
		return this;
	}

	public EnumSet<TopBarStickySize> getStickySizes() {
		return stickySizes;
	}

	public TopBarOptions setStickySizes(EnumSet<TopBarStickySize> stickySizes) {
		this.stickySizes = stickySizes;
		return this;
	}

	public boolean isClickable() {
		return clickable;
	}

	public TopBarOptions setClickable(boolean clickable) {
		this.clickable = clickable;
		return this;
	}
}
