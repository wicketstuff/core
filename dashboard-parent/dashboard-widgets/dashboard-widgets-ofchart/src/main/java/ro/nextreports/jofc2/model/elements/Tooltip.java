/*
This file is part of jofc2.

jofc2 is free software: you can redistribute it and/or modify
it under the terms of the Lesser GNU General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

jofc2 is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

See <http://www.gnu.org/licenses/lgpl-3.0.txt>.
 */
package ro.nextreports.jofc2.model.elements;

import java.io.Serializable;

import ro.nextreports.jofc2.model.metadata.Alias;
import ro.nextreports.jofc2.model.metadata.Converter;
import ro.nextreports.jofc2.util.TooltipTypeConverter;

public class Tooltip implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6105731120147109557L;
	@Alias(value = "mouse")
	private Type type = null;
	private Boolean shadow;
	private Integer stroke;
	private String colour;
	@Alias(value = "background")
	private String backgroundColour;
	@Alias(value = "title")
	private String titleStyle;
	@Alias(value = "body")
	private String bodyStyle;

	public Tooltip() {}

	public Type getType() {
		return type;
	}

	public Tooltip setHover() {
		this.type = Type.HOVER;
		return this;
	}

	public Tooltip setProximity() {
		this.type = Type.PROXIMITY;
		return this;
	}

	public Boolean getShadow() {
		return shadow;
	}

	public Tooltip setShadow(Boolean shadow) {
		this.shadow = shadow;
		return this;
	}

	public Integer getStroke() {
		return stroke;
	}

	public Tooltip setStroke(Integer stroke) {
		this.stroke = stroke;
		return this;
	}

	public String getColour() {
		return colour;
	}

	public Tooltip setColour(String colour) {
		this.colour = colour;
		return this;
	}

	public String getBackgroundColour() {
		return backgroundColour;
	}

	public Tooltip setBackgroundColour(String backgroundColour) {
		this.backgroundColour = backgroundColour;
		return this;
	}

	public String getTitleStyle() {
		return titleStyle;
	}

	public Tooltip setTitleStyle(String titleStyle) {
		this.titleStyle = titleStyle;
		return this;
	}

	public String getBodyStyle() {
		return bodyStyle;
	}

	public Tooltip setBodyStyle(String bodyStyle) {
		this.bodyStyle = bodyStyle;
		return this;
	}

	@Converter(TooltipTypeConverter.class)
	public enum Type {
		PROXIMITY(1), HOVER(2);

		private final int value;

		private Type(Integer value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}
	}
}
