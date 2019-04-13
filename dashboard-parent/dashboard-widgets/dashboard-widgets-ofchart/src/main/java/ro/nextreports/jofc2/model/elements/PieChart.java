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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import ro.nextreports.jofc2.model.metadata.Alias;
import ro.nextreports.jofc2.model.metadata.Converter;
import ro.nextreports.jofc2.util.PieChartSliceConverter;

public class PieChart extends Element {

	private static final long serialVersionUID = 8853434988212173862L;
	@Alias("start-angle")
	private Integer startAngle;
	private Collection<String> colours;
	private List<Object> animate = new ArrayList<Object>();
	private Integer border;
	private Integer radius;
	@Alias("no-labels")
	private Boolean noLabels;

	public PieChart() {
		super("pie");
	}

	public Integer getStartAngle() {
		return startAngle;
	}

	public PieChart setStartAngle(Integer startAngle) {
		this.startAngle = startAngle;
		return this;
	}

	public Collection<String> getColours() {
		return colours;
	}

	public PieChart setColours(Collection<String> colours) {
		checkColours();
		this.colours = colours;
		return this;
	}

	public PieChart setColours(String... colours) {
		checkColours();
		this.colours.addAll(Arrays.asList(colours));
		return this;
	}

	public PieChart setColours(List<String> colours) {
		checkColours();
		this.colours.addAll(colours);
		return this;
	}

	public Integer getBorder() {
		return border;
	}

	public PieChart setBorder(Integer border) {
		this.border = border;
		return this;
	}

	public PieChart addValues(Number... values) {
		getValues().addAll(Arrays.asList(values));
		return this;
	}

	public PieChart addValues(List<Number> values) {
		for (Number number : values) {
			// Ignore null values cause they don't make sense in pie Charts
			if (number != null) {
				getValues().add(number);
			}
		}
		return this;
	}

	public PieChart addSlice(Number value, String text) {
		return addSlices(new Slice(value, text));
	}

	public PieChart addSlices(Slice... s) {
		getValues().addAll(Arrays.asList(s));
		return this;
	}

	public PieChart addSlices(List<Slice> values) {
		getValues().addAll(values);
		return this;
	}

	/**
	 * Add a default fade animation 
	 * @param _animate
	 */
	public void setAnimate(boolean _animate) {
		if (_animate && getAnimate().size() == 0) {
			getAnimate().add(new PieChart.AnimationPie.Fade());
		}
	}

	public PieChart addAnimations(AnimationPie... animations) {
		return addAnimations(Arrays.asList(animations));
	}

	public PieChart addAnimations(List<AnimationPie> animations) {
		getAnimate().addAll(animations);
		return this;
	}

	@Converter(PieChartSliceConverter.class)
	public static class Slice implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6961394996186973937L;
		private final String label;
		private String tip;
		private String highlight = "alpha";
		private String text;
		private final Number value;
		private String onClick;

		public Slice(Number value, String label) {
			this.label = label;
			this.value = value;
			this.text = label;
		}

		/**
		 * The slice changes alpha on mouse over instead of breaking out the pie
		 */
		public void setOnMouseOverAlpha() {
			this.highlight = "alpha";
		}

		/**
		 * The slice breaks out of the pie on mouse over instead of changing its
		 * alpha
		 */
		public void setOnMouseOverBreakout() {
			this.highlight = null;
		}

		public Number getValue() {
			return value;
		}

		public String getLabel() {
			return label;
		}

		public String getText() {
			return text;
		}

		/**
		 * @return the tip Text for the slice. If Tip isset it overrides the
		 *         label
		 */
		public String getTip() {
			return tip;
		}

		/**
		 * Sets the tip Text for the slice. If Tip isset it overrides the label
		 * 
		 * @param tip
		 *            the Text to set
		 */
		public void setTip(String tip) {
			this.tip = tip;
		}

		public String getHighlight() {
			return highlight;
		}

		/**
		 * This value is the Representation of the Slice in the legend (if it is
		 * rendered)
		 */
		public void setText(String text) {
			this.text = text;
		}

		public String getOnClick() {
			return onClick;
		}

		public void setOnClick(String onClick) {
			this.onClick = onClick;
		}
	}

	private synchronized void checkColours() {
		if (colours == null)
			colours = new ArrayList<String>();
	}

	public Boolean getNoLabels() {
		return noLabels;
	}

	public void setNoLabels(Boolean noLabels) {
		this.noLabels = noLabels;
	}

	public Integer getRadius() {
		return radius;
	}

	/**
	 * "radius" allows you to force the radius of the pie to a certain size. If
	 * this is left out of the JSON then the pie will resize itself so that it
	 * and all of its labels fit in the display area (as best as it can).
	 * 
	 */
	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	private List<Object> getAnimate() {
		return animate;
	}
	
	
	public static abstract class AnimationPie {

		private String type;
		private Integer distance;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Integer getDistance() {
			return distance;
		}

		public void setDistance(Integer distance) {
			this.distance = distance;
		}

		public static class Fade extends AnimationPie implements Serializable {
		
			private static final long serialVersionUID = -3878420396240140754L;

			public Fade() {
				setType("fade");
			}
		}

		public static class Bounce extends AnimationPie implements Serializable {

			private static final long serialVersionUID = -2951410274832180986L;

			public Bounce(Integer distance) {
				setType("bounce");
				setDistance(distance);
			}
		}
	}
}
