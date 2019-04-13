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

import ro.nextreports.jofc2.model.metadata.Alias;
import ro.nextreports.jofc2.model.metadata.Converter;
import ro.nextreports.jofc2.util.DotConverter;
import ro.nextreports.jofc2.util.TypeDotConverter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class LineChart extends AnimatedElement {

	private static final long serialVersionUID = 8807130855547088579L;
	private static transient final Integer DEFAULT_FONTSIZE = 10;

	private Integer width;
	@Alias("dot-size")
	private Integer dotSize;
	@Alias("halo-size")
	private Integer haloSize;
	private String colour;
	private String axis;
	@Alias("dot-style")
	private Style dotStyle = new Style(Style.Type.SOLID_DOT); //default

	public String getYaxis() {
		return axis;
	}

	public void setYAxis(String yAxis) {
		this.axis = yAxis;
	}

	/**
	 * Tells the LineChart to Use the right YAxis
	 */
	public void setRightYAxis() {
		setYAxis("right");
	}

	public LineChart() {
		this("line");
	}
	
	public LineChart(Style dotStyle) {
		this();
		this.setDotStyle(dotStyle);
	}

	protected LineChart(String type) {
		super(type);
		setFontSize(DEFAULT_FONTSIZE);
	}

	public Integer getWidth() {
		return width;
	}

	public LineChart setWidth(Integer width) {
		this.width = width;
		return this;
	}

	public Integer getDotSize() {
		return dotSize;
	}

	public LineChart setDotSize(Integer dotSize) {
		this.dotSize = dotSize;
		return this;
	}

	public String getColour() {
		return colour;
	}

	public LineChart setColour(String colour) {
		this.colour = colour;
		return this;
	}

	public LineChart addValues(Number... values) {
		return addValues(Arrays.asList(values));
	}

	public LineChart addValues(List<Number> values) {
		// We convert all Numbers to Dots except Null Values they are Converted
		// to a Null Element
		// as Dots with the value null are interpreted as Zero from OFC and null
		// Values themself are interpolated
		for (Number number : values) {
			if (number == null) {
				getValues().add(new NullElement());
			} else {
				getValues().add(number);
			}
		}
		return this;
	}

	public LineChart addDots(Dot... dots) {
		return addDots(Arrays.asList(dots));
	}

	public LineChart addDots(List<Dot> dots) {
		for (Dot dot : dots) {
			if (dot == null || dot.getValue() == null) {
				getValues().add(new NullElement());
			} else {
				getValues().add(dot);
			}
		}
		return this;
	}

	public Integer getHaloSize() {
		return haloSize;
	}

	public LineChart setHaloSize(Integer haloSize) {
		this.haloSize = haloSize;
		return this;
	}

	@Converter(DotConverter.class)
	public static class Dot implements Serializable {

		@Alias("halo-size")
		private Integer haloSize;
		@Alias("dot-size")
		private Integer dotSize;
		private Number value;
		private String colour;
		@Alias("tip")
		private String tooltip;

		@Alias("on-click")
		private String onClick;

		public String getOnClick() {
			return onClick;
		}

		public void setOnClick(String onClick) {
			this.onClick = onClick;
		}
		

		public Dot(Number value) {
			this(value, null, null, null);
		}

		public Dot(Number value, String colour) {
			this(value, colour, null, null);
		}

		public Dot(Number value, String colour, Integer dotSize,
				Integer haloSize) {
			setValue(value);
			setColour(colour);
			setDotSize(dotSize);
			setHaloSize(haloSize);
		}

		public Integer getHaloSize() {
			return haloSize;
		}

		public Dot setHaloSize(Integer haloSize) {
			this.haloSize = haloSize;
			return this;
		}

		public Integer getDotSize() {
			return dotSize;
		}

		public Dot setDotSize(Integer dotSize) {
			this.dotSize = dotSize;
			return this;
		}

		public Number getValue() {
			return value;
		}

		public Dot setValue(Number value) {
			this.value = value;
			return this;
		}

		public String getColour() {
			return colour;
		}

		public Dot setColour(String colour) {
			this.colour = colour;
			return this;
		}

		public String getTooltip() {
			return tooltip;
		}

		public void setTooltip(String tooltip) {
			this.tooltip = tooltip;
		}
	}

	@Converter(TypeDotConverter.class)
	public static class Style implements Serializable {

		private static final long serialVersionUID = 7167564063732384112L;

		public enum Type {
			BOW("bow"), DOT("dot"), HALLOW_DOT("hollow-dot"), ANCHOR("anchor"), STAR(
					"star"), SOLID_DOT("solid-dot");

			private String type;

			Type(String type) {
				this.type = type;
			}

			public String getType() {
				return type;
			}
		}

		private String type = "solid-dot";
		private Integer rotation = 90;
		private Boolean hallow = false;
		@Alias("halo-size")
		private Integer haloSize = 2;
		@Alias("dot-size")
		private Integer dotSize = 2;
		private String colour;
		private Integer sides = 2;

		// "dot-style": { "type": "dot", "dot-size": 4, "halo-size": 2 }

		/**
		 * Build a simple LineChart with a Style.
		 * 
		 * The examples <Code>LineChart lc = new LineChart();</code> Type:
		 * Hallow-Dot Code: lc.setDotStyle(new LineChart.Style(Type.HALLOW_DOT,
		 * "#111111",9,9));
		 * 
		 * Type: Anchor Code: lc.setDotStyle(new LineChart.Style(Type.ANCHOR,
		 * "#111111",9,9,90,true).setSides(3));
		 * 
		 * Type: Star Code: lc.setDotStyle(new LineChart.Style(Type.STAR,
		 * "#111111",9,9).setRotation(90));
		 * 
		 * Type: Solid-Dot Code: lc.setDotStyle(new
		 * LineChart.Style(Type.SOLID_DOT, "#111111",9,9));
		 * 
		 * Type: bow Code: lc.setDotStyle(new LineChart.Style(Type.BOW,
		 * "#111111",9,9).setRotation(90));
		 * 
		 * 
		 * @param type
		 *            <class>Type</class> object BOW, DOT, HALLOW_DOT, ANCHOR,
		 *            STAR and SOLID_DOT
		 * @param colour
		 *            colour in #RGB
		 */
		public Style(Type type, String colour) {
			this(type, colour, 2, 2, null, null);
		}
		/**
		 * Another Constructor
		 *
		 * @param type
		 *            <class>Type</class> object BOW, DOT, HALLOW_DOT, ANCHOR,
		 *            STAR and SOLID_DOT
		 */
		public Style(Type type) {
			this(type, null, 2, 2, null, null);
		}

		/**
		 * Another constructor
		 * 
		 * @param type
		 *            <class>Type</class> object BOW, DOT, HALLOW_DOT, ANCHOR,
		 *            STAR and SOLID_DOT
		 * @param colour
		 *            <code>String</code> colour
		 * @param dotSize
		 *            <code>Integer</code> that represent the dot size
		 * @param haloSize
		 *            <code>Integer</code> that represent the halo size
		 */
		public Style(Type type, String colour, Integer dotSize, Integer haloSize) {
			this(type, colour, dotSize, haloSize, null, null);
		}

		/**
		 * Another Constructor
		 * 
		 * @param type
		 *            <class>Type</class> object BOW, DOT, HALLOW_DOT, ANCHOR,
		 *            STAR and SOLID_DOT
		 * @param colour
		 *            <code>String</code> colour
		 * @param dotSize
		 *            <code>Integer</code> that represent the dot size
		 * @param haloSize
		 *            <code>Integer</code> that represent the halo size
		 * @param rotation
		 *            <code>Integer</code> that represent the angle
		 * @param hallow
		 *            <code>Boolean</code>
		 */
		public Style(Type type, String colour, Integer dotSize,
				Integer haloSize, Integer rotation, Boolean hallow) {
			if (Type.HALLOW_DOT == type) {
				// "dot-style": { "type": "hollow-dot", "dot-size": 5,
				// "halo-size":
				// 0,"colour": "#3D5C56" }
				setType(Type.HALLOW_DOT.getType());
				setColour(colour);
				setDotSize(dotSize);
				setHaloSize(haloSize);
				setRotation(null);
				setSides(null);
				setHallow(null);
			} else if (Type.ANCHOR == type) {
				// "dot-style": { "type": "anchor", "dot-size": 6, "halo-size":
				// 1,"colour": "#3D5C56", "rotation": 90, "sides": 3 }
				setType(Type.ANCHOR.getType());
				setColour(colour);
				setDotSize(dotSize);
				setHaloSize(haloSize);
				setRotation(rotation);
				setSides(sides);
				setHallow(null);
			} else if (Type.STAR == type) {
				// "dot-style": { "type": "star", "dot-size": 6, "halo-size":
				// 2,"colour": "#f00000", "rotation": 180, "hollow": false }
				setType(Type.STAR.getType());
				setColour(colour);
				setDotSize(dotSize);
				setHaloSize(haloSize);
				setRotation(rotation);
				setHallow(hallow);
				setSides(null);
			} else if (Type.SOLID_DOT == type) {
				// "dot-style": { "type": "solid-dot", "dot-size": 3,
				// "halo-size":
				// 1, "colour": "#3D5C56" }
				setType(Type.SOLID_DOT.getType());
				setColour(colour);
				setDotSize(dotSize);
				setHaloSize(haloSize);
				setRotation(null);
				setSides(null);
				setHallow(null);
			} else if (Type.BOW == type) {
				// "dot-style": { "type": "bow", "dot-size": 6, "halo-size":
				// 0,"colour":
				// "#3D5C56", "rotation": 90
				setType(Type.BOW.getType());
				setColour(colour);
				setDotSize(dotSize);
				setHaloSize(haloSize);
				setRotation(rotation);
				setSides(null);
				setHallow(null);
			}
		}

		public Integer getHaloSize() {
			return haloSize;
		}

		public Style setHaloSize(Integer haloSize) {
			this.haloSize = haloSize;
			return this;
		}

		public Integer getDotSize() {
			return dotSize;
		}

		public Style setDotSize(Integer dotSize) {
			this.dotSize = dotSize;
			return this;
		}

		public String getColour() {
			return colour;
		}

		public Style setColour(String colour) {
			this.colour = colour;
			return this;
		}

		public String getType() {
			return type;
		}

		public Style setType(String type) {
			this.type = type;
			return this;
		}

		public Integer getRotation() {
			return rotation;
		}

		public Style setRotation(Integer rotation) {
			this.rotation = rotation;
			return this;
		}

		public Boolean getHallow() {
			return hallow;
		}

		public Style setHallow(Boolean hallow) {
			this.hallow = hallow;
			return this;
		}

		public Integer getSides() {
			return sides;
		}

		public Style setSides(Integer sides) {
			this.sides = sides;
			return this;
		}
	}

	public Style getDotStyle() {
		return dotStyle;
	}

	public LineChart setDotStyle(Style dotStyle) {
		this.dotStyle = dotStyle;
		return this;
	}

}
