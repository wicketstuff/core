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
package ro.nextreports.jofc2.model.axis;

import java.io.Serializable;

import ro.nextreports.jofc2.model.metadata.Converter;
import ro.nextreports.jofc2.util.RotationConverter;

public class Label implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6976582830606939527L;
	/**
	 * 
	 */
	private String text;
	private String colour;
	private Integer size;
	private Rotation rotate;
	private Boolean visible;

	@Converter(RotationConverter.class)
	public static enum Rotation {
		VERTICAL(-90), HALF_DIAGONAL(-24), DIAGONAL(-45), HORIZONTAL(0);

		private final int degrees;

		Rotation(int degrees) {
			this.degrees = degrees;
		}

		@Override
		public String toString() {
			return String.valueOf(degrees);
		}
	}

	public Label() {
		this(null);
	}

	public Label(String text) {
		setText(text);
	}

	public String getText() {
		return text;
	}

	public Label setText(String text) {
		this.text = text;
		return this;
	}

	public String getColour() {
		return colour;
	}

	public Label setColour(String colour) {
		this.colour = colour;
		return this;
	}

	public Integer getSize() {
		return size;
	}

	public Label setSize(Integer size) {
		this.size = size;
		return this;
	}

	public Rotation getRotation() {
		return rotate;
	}

	public Label setRotation(Rotation rotate) {
		this.rotate = rotate;
		return this;
	}

	public Boolean getVisible() {
		return visible;
	}

	public Label setVisible(Boolean visible) {
		this.visible = visible;
		return this;
	}
}
