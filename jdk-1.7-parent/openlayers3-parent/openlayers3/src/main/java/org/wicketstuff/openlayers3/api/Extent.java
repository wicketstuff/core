package org.wicketstuff.openlayers3.api;

import java.io.Serializable;

import org.wicketstuff.openlayers3.api.coordinate.LongLat;

/**
 * Provides an object that models an extent, or selection of a map.
 */
public class Extent extends JavascriptObject implements Serializable {

	/**
	 * The maximum coordinate for this extent.
	 */
	private LongLat maximum;

	/**
	 * The minimum coordinate for this extent.
	 */
	private LongLat minimum;

	/**
	 * Returns the extent minimum coordinate.
	 *
	 * @return Coordinate with the minimum
	 */
	public LongLat getMinimum() {
		return minimum;
	}

	/**
	 * Sets the extent minimum coordinate.
	 *
	 * @param minimum The minimum coordinate
	 */
	public void setMinimum(LongLat minimum) {
		this.minimum = minimum;
	}

	/**
	 * Sets the extent minimum coordinate.
	 *
	 * @param minimum The minimum coordinate
	 * @return this instance
	 */
	public Extent minimum(LongLat minimum) {
		setMinimum(minimum);
		return this;
	}

	/**
	 * Returns the maximum coodinate.
	 *
	 * @return The coordinate with the maximum
	 */
	public LongLat getMaximum() {
		return maximum;
	}

	/**
	 * Sets the maximum coodinate.
	 *
	 * @param maximum The maximum coordinate
	 */
	public void setMaximum(LongLat maximum) {
		this.maximum = maximum;
	}

	/**
	 * Sets the maximum coodinate.
	 *
	 * @param maximum The maximum coordinate
	 * @return this instance
	 */
	public Extent maximum(LongLat maximum) {
		setMaximum(maximum);
		return this;
	}

	public String renderJsForView(View view) {

		StringBuilder builder = new StringBuilder();
		builder.append("ol.extent.boundingExtent([");
		builder.append(getMinimum().transform(view.getProjection()).renderJs());
		builder.append(",");
		builder.append(getMaximum().transform(view.getProjection()).renderJs());
		builder.append("])");
		return builder.toString();
	}

	@Override
    public String getJsType() {
        return "ol.Extent";
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(getMinimum().getX() + "," + getMinimum().getY() + ",");
		builder.append(getMaximum().getX() + "," + getMaximum().getY());
		builder.append("]");
		return builder.toString();
	}
}
