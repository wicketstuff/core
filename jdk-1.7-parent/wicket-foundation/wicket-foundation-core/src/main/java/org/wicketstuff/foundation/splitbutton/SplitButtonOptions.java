package org.wicketstuff.foundation.splitbutton;

import java.io.Serializable;

import org.wicketstuff.foundation.button.ButtonColor;
import org.wicketstuff.foundation.button.ButtonRadius;
import org.wicketstuff.foundation.button.ButtonSize;

public class SplitButtonOptions implements Serializable {

	private ButtonSize size;
	private ButtonColor color;
	private ButtonRadius radius;

	public SplitButtonOptions() {
	}
	
	public SplitButtonOptions(ButtonSize size) {
		this.size = size;
	}
	
	public SplitButtonOptions(ButtonColor color) {
		this.color = color;
	}
	
	public SplitButtonOptions(ButtonRadius radius) {
		this.radius = radius;
	}

	public ButtonSize getSize() {
		return size;
	}

	public SplitButtonOptions setSize(ButtonSize size) {
		this.size = size;
		return this;
	}

	public ButtonColor getColor() {
		return color;
	}

	public SplitButtonOptions setColor(ButtonColor color) {
		this.color = color;
		return this;
	}

	public ButtonRadius getRadius() {
		return radius;
	}

	public SplitButtonOptions setRadius(ButtonRadius radius) {
		this.radius = radius;
		return this;
	}
}
