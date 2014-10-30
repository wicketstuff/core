package org.wicketstuff.openlayers3.api.coordinate;

import org.wicketstuff.openlayers3.api.JavascriptObject;

import java.io.Serializable;

/**
 * Models a coordinate on a map.
 */
public class Coordinate extends JavascriptObject implements Serializable {

    /**
     * The "X" coordinate.
     */
    public Number valueX;

    /**
     * The "Y" coordinate.
     */
    public Number valueY;

    /**
     * Creates a new instance.
     *
     * @param valueX
     *         The "X" coordinate
     * @param valueY
     *         The "Y" coordinate
     */
    public Coordinate(final Number valueX, final Number valueY) {
        super();
        this.valueX = valueX;
        this.valueY = valueY;
    }

    /**
     * Return the "X" coordinate.
     *
     * @return Number with the coordinate
     */
    public Number getX() {
        return valueX;
    }

    /**
     * Sets the "X" coordinate.
     *
     * @param valueX
     *         New value
     */
    public void setX(Number valueX) {
        this.valueX = valueX;
    }

    /**
     * Sets the "X" coordinate.
     *
     * @param valueX
     *         New value
     * @return This instance
     */
    public Coordinate x(Number valueX) {
        setX(valueX);
        return this;
    }

    /**
     * Returns the "Y" coordinate.
     *
     * @return Number with the coordinate
     */
    public Number getY() {
        return valueY;
    }

    /**
     * Sets the "Y" coordinate.
     *
     * @param valueY
     *         New value
     */
    public void setY(Number valueY) {
        this.valueY = valueY;
    }

    /**
     * Sets the "Y" coordinate.
     *
     * @param valueY
     *         New value
     * @return this instance
     */
    public Coordinate y(Number valueY) {
        setY(valueY);
        return this;
    }

    @Override
    public String getJsType() {
        return "";
    }

    @Override
    public String renderJs() {
        return "[" + valueX.toString() + ", " + valueY.toString() + "]";
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + getX() + "," + getY() + "]";
    }
}
