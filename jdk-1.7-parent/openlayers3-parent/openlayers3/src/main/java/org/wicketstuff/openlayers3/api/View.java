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
     * The projection for the map.
     */
    public String projection;

    /**
     * Creates a new instance.
     *
     * @param center
     *         The coordinate at the center of this view
     * @param zoom
     *         The zoom factor for this view
     */
    public View(Coordinate center, Integer zoom) {
        this(center, zoom, DEFAULT_PROJECTION);
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
        this.center = center;
        this.zoom = zoom;
        this.projection = projection;
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

        if (getProjection() != null) {
            builder.append("'projection': '" + getProjection() + "',");
        }

        builder.append("}");

        return builder.toString();
    }
}
