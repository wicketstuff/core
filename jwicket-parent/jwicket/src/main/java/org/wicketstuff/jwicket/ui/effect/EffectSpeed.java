package org.wicketstuff.jwicket.ui.effect;

public enum EffectSpeed {

	SLOW("slow"),
	NORMAL("normal"),
	FAST("fast");


	private final String name;

	private EffectSpeed(final String speed) {
		this.name = speed;
	}

	public String getSpeed() {
		return name;
	}

	public String toString() {
		return getSpeed();
	}
}
