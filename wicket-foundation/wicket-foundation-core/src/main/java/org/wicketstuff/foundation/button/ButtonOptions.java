package org.wicketstuff.foundation.button;

import java.io.Serializable;

/**
 * Options for different types of Foundation buttons.
 * @author ilkka
 *
 */
public class ButtonOptions implements Serializable {

	private static final long serialVersionUID = 1L;

	private ButtonSize size;
	private ButtonColor color;
	private ButtonRadius radius;
	private ButtonState state;
	private ButtonExpansion expansion;
	
	public ButtonOptions() {
	}
	
	public ButtonOptions(ButtonSize size) {
		this.size = size;
	}

	public ButtonOptions(ButtonColor color) {
		this.color = color;
	}

	public ButtonOptions(ButtonRadius radius) {
		this.radius = radius;
	}

	public ButtonOptions(ButtonState state) {
		this.state = state;
	}

	public ButtonOptions(ButtonExpansion expansion) {
		this.expansion = expansion;
	}
	
	public ButtonOptions(ButtonOptions options) {
		this.size = options.getFoundationButtonSize();
		this.color = options.getFoundationButtonColor();
		this.radius = options.getFoundationButtonRadius();
		this.state = options.getFoundationButtonState();
		this.expansion = options.getFoundationButtonExpansion();
	}
	
	public ButtonSize getFoundationButtonSize() {
		return size;
	}

	public ButtonOptions setFoundationButtonSize(ButtonSize foundationButtonSize) {
		this.size = foundationButtonSize;
		return this;
	}

	public ButtonColor getFoundationButtonColor() {
		return color;
	}

	public ButtonOptions setFoundationButtonColor(ButtonColor foundationButtonColor) {
		this.color = foundationButtonColor;
		return this;
	}

	public ButtonRadius getFoundationButtonRadius() {
		return radius;
	}

	public ButtonOptions setFoundationButtonRadius(ButtonRadius foundationButtonRadius) {
		this.radius = foundationButtonRadius;
		return this;
	}

	public ButtonState getFoundationButtonState() {
		return state;
	}

	public ButtonOptions setFoundationButtonState(ButtonState foundationButtonState) {
		this.state = foundationButtonState;
		return this;
	}

	public ButtonExpansion getFoundationButtonExpansion() {
		return expansion;
	}

	public ButtonOptions setFoundationButtonExpansion(ButtonExpansion foundationButtonExpansion) {
		this.expansion = foundationButtonExpansion;
		return this;
	}
	
	public ButtonOptions reset() {
		size = null;
		color = null;
		radius = null;
		state = null;
		expansion = null;
		return this;
	}
}
