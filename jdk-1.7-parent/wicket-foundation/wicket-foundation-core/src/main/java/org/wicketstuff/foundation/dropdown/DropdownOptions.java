package org.wicketstuff.foundation.dropdown;

import java.io.Serializable;

import org.wicketstuff.foundation.button.ButtonColor;
import org.wicketstuff.foundation.button.ButtonExpansion;
import org.wicketstuff.foundation.button.ButtonRadius;
import org.wicketstuff.foundation.button.ButtonSize;

/**
 * Options for the dropdown.
 * @author ilkka
 *
 */
public class DropdownOptions implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private DropdownType type;
	private ButtonSize size;
	private ButtonColor color;
	private ButtonRadius radius;
	private ButtonExpansion expansion;
	private DropdownListStyle listStyle;
	private DropdownListAlignment listAlignment;
	private DropdownHover hover;

	public DropdownOptions() {
	}

	public DropdownOptions(DropdownType type) {
		this.type = type;
	}
	
	public DropdownOptions(ButtonSize size) {
		this.size = size;
	}
	
	public DropdownOptions(ButtonColor color) {
		this.color = color;
	}
	
	public DropdownOptions(ButtonRadius radius) {
		this.radius = radius;
	}

	public DropdownOptions(ButtonExpansion expansion) {
		this.expansion = expansion;
	}
	
	public DropdownOptions(DropdownListStyle listStyle) {
		this.listStyle = listStyle;
	}
	
	public DropdownOptions(DropdownListAlignment listAlignment) {
		this.listAlignment = listAlignment;
	}
	
	public DropdownOptions(DropdownHover hover) {
		this.hover = hover;
	}
	
	public DropdownOptions(DropdownOptions other) {
		this.type = other.type;
		this.size = other.size;
		this.color = other.color;
		this.radius = other.radius;
		this.expansion = other.expansion;
	}

	public ButtonSize getSize() {
		return size;
	}

	public DropdownOptions setSize(ButtonSize size) {
		this.size = size;
		return this;
	}

	public ButtonColor getColor() {
		return color;
	}

	public DropdownOptions setColor(ButtonColor color) {
		this.color = color;
		return this;
	}

	public ButtonRadius getRadius() {
		return radius;
	}

	public DropdownOptions setRadius(ButtonRadius radius) {
		this.radius = radius;
		return this;
	}

	public ButtonExpansion getExpansion() {
		return expansion;
	}

	public DropdownOptions setExpansion(ButtonExpansion expansion) {
		this.expansion = expansion;
		return this;
	}

	public DropdownType getType() {
		return type;
	}

	public DropdownOptions setType(DropdownType type) {
		this.type = type;
		return this;
	}

	public DropdownListStyle getListStyle() {
		return listStyle;
	}

	public DropdownOptions setListStyle(DropdownListStyle listStyle) {
		this.listStyle = listStyle;
		return this;
	}

	public DropdownListAlignment getListAlignment() {
		return listAlignment;
	}

	public DropdownOptions setListAlignment(DropdownListAlignment listAlignment) {
		this.listAlignment = listAlignment;
		return this;
	}

	public DropdownHover getHover() {
		return hover;
	}

	public DropdownOptions setHover(DropdownHover hover) {
		this.hover = hover;
		return this;
	}
}
