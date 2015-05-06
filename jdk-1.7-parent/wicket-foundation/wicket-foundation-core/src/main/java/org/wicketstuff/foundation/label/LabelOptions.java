package org.wicketstuff.foundation.label;

import java.io.Serializable;

import org.wicketstuff.foundation.button.ButtonColor;
import org.wicketstuff.foundation.button.ButtonRadius;

/**
 * Options for FoundationLabel.
 * @author ilkka
 *
 */
public class LabelOptions implements Serializable {

	private static final long serialVersionUID = 1L;

	private ButtonColor color;
	private ButtonRadius radius;

	public LabelOptions() {
	}
	
	public LabelOptions(ButtonColor color) {
		this.color = color;
	}

	public LabelOptions(ButtonRadius radius) {
		this.radius = radius;
	}

	public LabelOptions(ButtonColor color, ButtonRadius radius) {
		this.color = color;
		this.radius = radius;
	}
	
	public ButtonColor getColor() {
		return color;
	}
	
	public LabelOptions setColor(ButtonColor color) {
		this.color = color;
		return this;
	}
	
	public ButtonRadius getRadius() {
		return radius;
	}
	
	public LabelOptions setRadius(ButtonRadius radius) {
		this.radius = radius;
		return this;
	}	
}
