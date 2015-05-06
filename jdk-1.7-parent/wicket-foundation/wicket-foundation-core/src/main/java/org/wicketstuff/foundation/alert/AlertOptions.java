package org.wicketstuff.foundation.alert;

import java.io.Serializable;

import org.wicketstuff.foundation.button.ButtonRadius;

/**
 * Options for FoundationAlert.
 * @author ilkka
 *
 */
public class AlertOptions implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private AlertColor color;
	private ButtonRadius radius;
	
	public AlertOptions() {
	}

	public AlertOptions(AlertColor color) {
		this.color = color;
	}

	public AlertOptions(ButtonRadius radius) {
		this.radius = radius;
	}

	public AlertOptions(AlertColor color, ButtonRadius radius) {
		this.color = color;
		this.radius = radius;
	}
	
	public AlertOptions(AlertOptions other) {
		this.color = other.color;
		this.radius = other.radius;
	}
	
	public AlertColor getColor() {
		return color;
	}
	
	public AlertOptions setColor(AlertColor color) {
		this.color = color;
		return this;
	}
	
	public ButtonRadius getRadius() {
		return radius;
	}
	
	public AlertOptions setRadius(ButtonRadius radius) {
		this.radius = radius;
		return this;
	}
}
