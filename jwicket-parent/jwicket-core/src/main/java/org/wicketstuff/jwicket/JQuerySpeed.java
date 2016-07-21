package org.wicketstuff.jwicket;

public enum JQuerySpeed {

	SLOW("slow"),
	NORMAL("normal"),
	FAST("fast");


	private final String name;

	private JQuerySpeed(final String speed) {
		this.name = speed;
	}

	public String getSpeed() {
		return name;
	}

	public String toString() {
		return getSpeed();
	}
}
