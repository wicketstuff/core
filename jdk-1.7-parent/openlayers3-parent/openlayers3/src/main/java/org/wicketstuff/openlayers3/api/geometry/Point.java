package org.wicketstuff.openlayers3.api.geometry;

import org.wicketstuff.openlayers3.api.JavascriptObject;
import org.wicketstuff.openlayers3.api.coordinate.Coordinate;

import java.io.Serializable;

/**
 * Provides an object that models a point of geometry on the map.
 */
public class Point extends JavascriptObject implements Serializable {

    /**
     * Coordinates of the point.
     */
    private Coordinate coordinate;

    /**
     * Layout for this point.
     */
    private Geometry.Layout layout;

    /**
     * Creates a new instance.
     *
     * @param coordinate
     *         Coordinates for the point
     */
    public Point(final Coordinate coordinate) {
        this(coordinate, null);
    }

    /**
     * Creates a new instance.
     *
     * @param coordinate
     *         Coordinate for the point
     * @param layout
     *         Layout for the point
     */
    public Point(final Coordinate coordinate, final Geometry.Layout layout) {
        super();
        this.coordinate = coordinate;
        this.layout = layout;
    }

    /**
     * Returns the coordinate for this point.
     *
     * @return Coordinate
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Sets the coordinate for this point.
     *
     * @param coordinate
     *         New value
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Sets the coordinate for this point.
     *
     * @param coordinate
     *         New value
     * @return This instance
     */
    public Point coordinate(Coordinate coordinate) {
        setCoordinate(coordinate);
        return this;
    }

    /**
     * Returns the layout for this point.
     *
     * @return Layout
     */
    public Geometry.Layout getLayout() {
        return layout;
    }

    /**
     * Sets the layout for this point.
     *
     * @param layout
     *         New value
     */
    public void setLayout(Geometry.Layout layout) {
        this.layout = layout;
    }

    /**
     * Sets the layout for this point.
     *
     * @param layout
     *         New value
     * @return This instance
     */
    public Point layout(Geometry.Layout layout) {
        setLayout(layout);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.geom.Point";
    }

    @Override
    public String renderJs() {
        StringBuilder builder = new StringBuilder();

        if (getCoordinate() != null) {
            builder.append(getCoordinate().renderJs());
        }

        if (getLayout() != null) {
            if (getCoordinate() != null) {
                builder.append(",");
            }
            builder.append(layout);
        }
        return builder.toString();
    }
}
