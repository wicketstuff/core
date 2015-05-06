package org.wicketstuff.foundation.buttongroup;

import java.io.Serializable;

import org.wicketstuff.foundation.button.ButtonColor;
import org.wicketstuff.foundation.button.ButtonRadius;

/**
 * Options for FoundationButtonGroup.
 * @author ilkka
 *
 */
public class ButtonGroupOptions implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ButtonRadius radius;
	private ButtonColor color;
	private ButtonGroupStacking stacking;
	
	public ButtonGroupOptions() {
	}

	public ButtonGroupOptions(ButtonRadius radius) {
		this.radius = radius;
	}
	
	public ButtonGroupOptions(ButtonColor color) {
		this.color = color;
	}
	
	public ButtonGroupOptions(ButtonGroupStacking stacking) {
		this.stacking = stacking;
	}
	
	public ButtonGroupOptions(ButtonGroupOptions other) {
		this.radius = other.radius;
		this.color = other.color;
		this.stacking = other.stacking;
	}
	
	public ButtonRadius getRadius() {
		return radius;
	}
	public ButtonGroupOptions setRadius(ButtonRadius radius) {
		this.radius = radius;
		return this;
	}
	public ButtonColor getColor() {
		return color;
	}
	public ButtonGroupOptions setColor(ButtonColor color) {
		this.color = color;
		return this;
	}
	public ButtonGroupStacking getStacking() {
		return stacking;
	}
	public ButtonGroupOptions setStacking(ButtonGroupStacking stacking) {
		this.stacking = stacking;
		return this;
	}
	
}
