package org.wicketstuff.openlayers3.api.coordinate;

/**
 * Provides an object that models a longitude and latitude pair.
 */
public class LongLat extends CoordinateProjected {

    /**
     * Creates a new instance.
     *
     * @param longitude
     *         Longitude for the coordinate
     * @param latitude
     *         Latitude for the coordinate
     */
    public LongLat(final Number longitude, final Number latitude, String projection) {
        super(longitude, latitude, projection);
    }

    /**
     * Creates a new instance.
     *
     * @param coordinate
     *         Coordinate with for the longitude and latitude
     * @param projection
     *         Projection for the coordinate
     */
    public LongLat(final Coordinate coordinate, String projection) {
        super(coordinate.getX(), coordinate.getY(), projection);
    }

    /**
     * Returns the latitude for the coordinate.
     *
     * @return Number with the coordinate
     */
    public Number getLatitude() {
        return valueY;
    }

    /**
     * Sets the latitude for this coordinate.
     *
     * @param latitude
     *         New value
     */
    public void setLatitude(Number latitude) {
        setY(latitude);
    }

    /**
     * Returns the longitude for the coordinate.
     *
     * @return Number with the coordinate
     */
    public Number getLongitude() {
        return valueX;
    }

    /**
     * Sets the longitude for this coordinate.
     *
     * @param longitude
     *         New value
     */
    public void setLongitude(Number longitude) {
        setX(longitude);
    }

    /**
     * Sets the longitude of the coordinate.
     *
     * @param longitude
     *         New value
     * @return This instance
     */
    public LongLat longitude(Number longitude) {
        setX(longitude);
        return this;
    }

    /**
     * Sets the latitude of the coordinate.
     *
     * @param latitude
     *         New value
     * @return this instance
     */
    public LongLat latitude(Number latitude) {
        setY(latitude);
        return this;
    }

    /**
     * Sets the target projection for the coordinate.
     *
     * @param targetProjection
     *         New value
     * @return This instance
     */
    @Override
    public LongLat targetProjection(String targetProjection) {
        setTargetProjection(targetProjection);
        return this;
    }

    /**
     * Sets the target projection for the coordinate.
     *
     * @param targetProjection
     *         New value
     * @return This instance
     */
    @Override
    public LongLat transform(String targetProjection) {
        return targetProjection(targetProjection);
    }

    /**
     * Sets the "X" coordinate.
     *
     * @param valueX
     *         New value
     * @return This instance
     */
    @Override
    public LongLat x(Number valueX) {
        setX(valueX);
        return this;
    }

    /**
     * Sets the "Y" coordinate.
     *
     * @param valueY
     *         New value
     * @return this instance
     */
    @Override
    public LongLat y(Number valueY) {
        setY(valueY);
        return this;
    }
}
