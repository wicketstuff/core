package org.wicketstuff.jwicket;


public enum CssPosition {

	TOP("top"),
	LEFT("left"),
	RIGHT("right"),
	BOTTOM("bottom");
	
	private final String name;
	
	private CssPosition(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return this.name;
	}
}
