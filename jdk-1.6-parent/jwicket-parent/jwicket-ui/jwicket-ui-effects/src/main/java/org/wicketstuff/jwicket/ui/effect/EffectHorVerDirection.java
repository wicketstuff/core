package org.wicketstuff.jwicket.ui.effect;


public enum EffectHorVerDirection {

	DEFAULT(null),
	HORIZONTAL("horizontal"),
	VERTICAL("vertical");

	private final String direction;

	private EffectHorVerDirection(final String direction) {
		this.direction = direction;
	}

	public String getDirection() {
		return direction;
	}

	public String toString() {
		return direction==null?"default":direction;
	}

}
