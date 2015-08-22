package org.wicketstuff.foundation.progressbar;

import java.io.Serializable;

import org.wicketstuff.foundation.button.ButtonColor;
import org.wicketstuff.foundation.button.ButtonRadius;

/**
 * Options for the FoundationProgressBar.
 * @author ilkka
 *
 */
public class ProgressBarOptions implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ButtonColor color;
	private ButtonRadius radius;
	
	public ProgressBarOptions() {
	}
	
	public ProgressBarOptions(ButtonColor color) {
		this.color = color;
	}
	
	public ProgressBarOptions(ButtonRadius radius) {
		this.radius = radius;
	}

	public ButtonColor getColor() {
		return color;
	}

	public ProgressBarOptions setColor(ButtonColor color) {
		this.color = color;
		return this;
	}

	public ButtonRadius getRadius() {
		return radius;
	}

	public ProgressBarOptions setRadius(ButtonRadius radius) {
		this.radius = radius;
		return this;
	}	
}
