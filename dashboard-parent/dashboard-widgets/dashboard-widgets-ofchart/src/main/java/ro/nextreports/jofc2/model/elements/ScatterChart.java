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
import java.util.Arrays;
import java.util.Collection;

import ro.nextreports.jofc2.model.metadata.Alias;
import ro.nextreports.jofc2.model.metadata.Converter;
import ro.nextreports.jofc2.util.ScatterChartPointConverter;

public class ScatterChart extends AnimatedElement {

	private static final String TYPE = "scatter";
	private static final long serialVersionUID = 3029567780918048503L;
	private String colour;
	@Alias("dot-size")
	private Integer dotSize;

	public ScatterChart() {
		super(TYPE);
	}

    public ScatterChart(Style style){
        super(style.getStyle());
    }

	public ScatterChart addPoints(Point... points) {
		getValues().addAll(Arrays.asList(points));
		return this;
	}

	public ScatterChart addPoint(Number x, Number y) {
		return addPoints(new Point(x, y));
	}

	public ScatterChart addPoints(Collection<Point> points) {
		getValues().addAll(points);
		return this;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public Integer getDotSize() {
		return dotSize;
	}

	public void setDotSize(Integer dotSize) {
		this.dotSize = dotSize;
	}

	@Converter(ScatterChartPointConverter.class)
	public static class Point implements Serializable {

		private Number x;
		private Number y;

		public Point(Number x, Number y) {
			this.x = x;
			this.y = y;
		}

		public Number getX() {
			return x;
		}

		public void setX(Number x) {
			this.x = x;
		}

		public Number getY() {
			return y;
		}

		public void setY(Number y) {
			this.y = y;
		}
	}

    public static enum Style {
		NORMAL("scatter"), LINE("scatter_line");

		private String style;

		Style(String style) {
			this.style = style;
		}

		public String getStyle() {
			return style;
		}
	}
}
