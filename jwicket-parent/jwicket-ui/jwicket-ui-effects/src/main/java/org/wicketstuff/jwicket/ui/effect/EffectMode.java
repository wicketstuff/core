package org.wicketstuff.jwicket.ui.effect;


public enum EffectMode {

	DEFAULT(null),
	SHOW("show"),
	HIDE("hide");

	private final String mode;

	private EffectMode(final String mode) {
		this.mode = mode;
	}

	public String getMode() {
		return mode;
	}

	public String toString() {
		return mode==null?"default":mode;
	}

}
