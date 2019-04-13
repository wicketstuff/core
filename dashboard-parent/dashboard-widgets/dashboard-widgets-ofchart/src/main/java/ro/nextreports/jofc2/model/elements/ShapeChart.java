/*
This file is part of jofc2.

jofc2 is free software: you can redistribute it and/or modify
it under the terms of the Lesser GNU General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

jofc2 is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

See <http://www.gnu.org/licenses/lgpl-3.0.txt>.
 */
package ro.nextreports.jofc2.model.elements;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import ro.nextreports.jofc2.model.metadata.Converter;
import ro.nextreports.jofc2.util.ShapePointConverter;

public class ShapeChart extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5491121778724754279L;
	private String colour;

	public ShapeChart() {
		this(null);
	}

	public ShapeChart(String colour) {
		super("shape");
		setColour(colour);
	}

	public ShapeChart addPoints(Point... points) {
		getValues().addAll(Arrays.asList(points));
		return this;
	}

	public ShapeChart addPoints(List<Point> points) {
		getValues().addAll(points);
		return this;
	}

	public String getColour() {
		return colour;
	}

	public ShapeChart setColour(String colour) {
		this.colour = colour;
		return this;
	}

	@Converter(ShapePointConverter.class)
	public static class Point implements Serializable {

		private final Number x;
		private final Number y;

		public Point(Number x, Number y) {
			this.x = x;
			this.y = y;
		}

		public Number getX() {
			return x;
		}

		public Number getY() {
			return y;
		}
	}
}
