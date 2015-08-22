package org.wicketstuff.openlayers3.api;

import org.wicketstuff.openlayers3.api.coordinate.Coordinate;

import java.io.Serializable;

/**
 * Provides an object that models a view of a map.
 */
public class View extends JavascriptObject implements Serializable {

    /**
     * Default projection.
     */
    public final static String DEFAULT_PROJECTION = "EPSG:3857";

    /**
     * The coordinate that represents the center of this view. May be in any coordinate space.
     */
    public Coordinate center;

    /**
     * The zoom factor for this view.
     */
    public Integer zoom;

    /**
     * The minimum zoom level for this view.
     */
    public Integer minZoom;

    /**
     * The maximum zoom level for this view.
     */
    public Integer maxZoom;

    /**
     * The projection for the map.
     */
    public String projection;

	/**
	 * Extent for this map.
	 */
	private Extent extent;

    /**
     * Creates a new instance.
     *
     * @param center
     *         The coordinate at the center of this view
     * @param zoom
     *         The zoom factor for this view
     */
    public View(Coordinate center, Integer zoom) {
        this(center, zoom, null, DEFAULT_PROJECTION);
    }

    /**
     * Creates a new instance.
     *
     * @param center
     *         The coordinate at the center of this view
     * @param zoom
     *         The zoom factor for this view
     * @param maxZoom
     *         The maximum zoom factor for this view
     */
    public View(Coordinate center, Integer zoom, Integer maxZoom) {
        this(center, zoom, maxZoom, DEFAULT_PROJECTION);
    }

    /**
     * Creates a new instance.
     *
     * @param center
     *         The coordinate at the center of this view
     * @param zoom
     *         The zoom factor for this view
     * @param projection
     *         The projection for this view
     */
    public View(Coordinate center, Integer zoom, String projection) {
        this(center, zoom, null, projection);
    }

    /**
     * Creates a new instance.
     *
     * @param center
     *         The coordinate at the center of this view
     * @param zoom
     *         The zoom factor for this view
     * @param maxZoom
     *         The maximum zoom factor for this view
     * @param projection
     *         The projection for this view
     */
    public View(Coordinate center, Integer zoom, Integer maxZoom, String projection) {
		this(center, zoom, maxZoom, projection, null);
    }

	/**
     * Creates a new instance.
     *
     * @param center
     *         The coordinate at the center of this view
     * @param zoom
     *         The zoom factor for this view
     * @param maxZoom
     *         The maximum zoom factor for this view
     * @param projection
     *         The projection for this view
     * @param extent
     *         The extent for this view
     */
    public View(Coordinate center, Integer zoom, Integer maxZoom, String projection, Extent extent) {
        super();

        this.center = center;
        this.zoom = zoom;
        this.maxZoom = maxZoom;
        this.projection = projection;
		this.extent = extent;
    }

    /**
     * Returns the center coordinate for this view.
     *
     * @return Coordinate with the view center
     */
    public Coordinate getCenter() {
        return center;
    }

    /**
     * Sets the center coordinate for this view.
     *
     * @param center
     *         New value
     */
    public void setCenter(Coordinate center) {
        this.center = center;
    }

    /**
     * Sets the center coordinate for this view.
     *
     * @param center
     *         New value
     * @return This instance
     */
    public View center(Coordinate center) {
        setCenter(center);
        return this;
    }

    /**
     * Returns the zoom level for this view.
     *
     * @return Integer with the zoom level
     */
    public Integer getZoom() {
        return zoom;
    }

    /**
     * Sets the zoom level for this view.
     *
     * @param zoom
     *         New value
     */
    public void setZoom(Integer zoom) {
        this.zoom = zoom;
    }

    /**
     * Sets the zoom level for this view.
     *
     * @param zoom
     *         New value
     * @return This instance
     */
    public View zoom(Integer zoom) {
        setZoom(zoom);
        return this;
    }

    /**
     * Returns the projection for this view.
     *
     * @return String with the view projection
     */
    public String getProjection() {
        return projection;
    }

    /**
     * Sets the projection for this view.
     *
     * @param projection
     *         New value
     */
    public void setProjection(String projection) {
        this.projection = projection;
    }

    /**
     * Sets the projection for this view.
     *
     * @param projection
     *         New value
     * @return This instance
     */
    public View projection(String projection) {
        setProjection(projection);
        return this;
    }

    /**
     * Returns the minimum zoom level for this instance.
     *
     * @return Integer with the minimum zoom level
     */
    public Integer getMinZoom() {
        return minZoom;
    }

    /**
     * Sets the minimum zoom level for this instance.
     *
     * @param minZoom
     *         New value
     */
    public void setMinZoom(Integer minZoom) {
        this.minZoom = minZoom;
    }

    /**
     * Sets the minimum zoom level for this instance.
     *
     * @param minZoom
     *         New value
     * @return This instance
     */
    public View minZoom(Integer minZoom) {
        setMaxZoom(minZoom);
        return this;
    }

    /**
     * Returns the maximum zoom level for this instance.
     *
     * @return Integer with the zoom level
     */
    public Integer getMaxZoom() {
        return maxZoom;
    }

    /**
     * Sets the maximum zoom level for this instance.
     *
     * @param maxZoom
     *         Integer with the zoom level
     */
    public void setMaxZoom(Integer maxZoom) {
        this.maxZoom = maxZoom;
    }

    /**
     * Sets the maximum zoom level for this instance.
     *
     * @param maxZoom
     *         Integer with the zoom level
     * @return This instance
     */
    public View maxZoom(Integer maxZoom) {
        setMaxZoom(maxZoom);
        return this;
    }

	/**
	 * Returns the extent for this view.
	 *
	 * @return Extent for this view
	 */
	public Extent getExtent() {
		return extent;
	}

	/**
	 * Sets the extent for this view.
	 *
	 * @param extent The extent for this view
	 */
	public void setExtent(Extent extent) {
		this.extent = extent;
	}

	/**
	 * Sets the extent for this view.
	 *
	 * @param extent The extent for this view
	 * @return this instance
	 */
	public View extent(Extent extent) {
		setExtent(extent);
		return this;
	}

    @Override
    public String getJsType() {
        return "ol.View";
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();

        builder.append("{");

        if (getCenter() != null) {
            builder.append("'center': " + getCenter().renderJs() + ",");
        }

        if (getZoom() != null) {
            builder.append("'zoom': " + getZoom() + ",");
        }

        if (getMinZoom() != null) {
            builder.append("'minZoom': " + getMinZoom() + ",");
        }

        if (getMaxZoom() != null) {
            builder.append("'maxZoom': " + getMaxZoom() + ",");
        }

        if (getProjection() != null) {
            builder.append("'projection': '" + getProjection() + "',");
        }

        builder.append("}");

        return builder.toString();
    }
}
