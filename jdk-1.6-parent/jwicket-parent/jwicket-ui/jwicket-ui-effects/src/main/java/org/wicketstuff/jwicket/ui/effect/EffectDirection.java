package org.wicketstuff.jwicket.ui.effect;

public enum EffectDirection {

	DEFAULT(null),
	UP("up"),
	DOWN("down"),
	LEFT("left"),
	RIGHT("right");

	private final String direction;

	private EffectDirection(final String direction) {
		this.direction = direction;
	}

	public String getDirection() {
		return direction;
	}

	public String toString() {
		return direction==null?"default":direction;
	}
}
