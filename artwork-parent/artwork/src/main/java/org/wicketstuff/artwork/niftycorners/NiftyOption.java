package org.wicketstuff.artwork.niftycorners;

public enum NiftyOption {
	tl, tr, bl, br, top, bottom, left, right, all, none,small, normal, big, transparent, fixedheight(
			"fixedheight"), sameheight("sameheight");

	private String name = null;

	private NiftyOption() {

	}

	private NiftyOption(String name) {
		this.name = name;

	}

	@Override
	public String toString() {
		if (name != null) {
			return name;
		} else {
			return super.toString();
		}
	}

}
