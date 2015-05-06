package org.wicketstuff.foundation.tooltip;

import java.io.Serializable;

import org.wicketstuff.foundation.button.ButtonRadius;

/**
 * Options for tooltip.
 * @author ilkka
 *
 */
public class TooltipOptions implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean disableForTouch;
	private TooltipPosition position;
	private ButtonRadius radius;
	private TooltipVisibility visibility;
	
	public TooltipOptions() {
	}

	public TooltipOptions(boolean disableForTouch) {
		this.disableForTouch = disableForTouch;
	}

	public TooltipOptions(TooltipPosition position) {
		this.position = position;
	}

	public TooltipOptions(ButtonRadius radius) {
		this.radius = radius;
	}

	public TooltipOptions(TooltipVisibility visibility) {
		this.visibility = visibility;
	}
	
	public boolean isDisableForTouch() {
		return disableForTouch;
	}

	public TooltipOptions setDisableForTouch(boolean disableForTouch) {
		this.disableForTouch = disableForTouch;
		return this;
	}

	public TooltipPosition getPosition() {
		return position;
	}

	public TooltipOptions setPosition(TooltipPosition position) {
		this.position = position;
		return this;
	}

	public ButtonRadius getRadius() {
		return radius;
	}

	public TooltipOptions setRadius(ButtonRadius radius) {
		this.radius = radius;
		return this;
	}

	public TooltipVisibility getVisibility() {
		return visibility;
	}

	public TooltipOptions setVisibility(TooltipVisibility visibility) {
		this.visibility = visibility;
		return this;
	}	
}
