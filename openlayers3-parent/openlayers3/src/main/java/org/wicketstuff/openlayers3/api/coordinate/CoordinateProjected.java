package org.wicketstuff.openlayers3.api.coordinate;

/**
 * Provides a coordinate that includes a projection.
 */
public class CoordinateProjected extends Coordinate {

    /**
     * The projection for the coordinate.
     */
    private String projection;

    /**
     * The target projection for the coordinate.
     */
    private String targetProjection = null;

    /**
     * Creates a new instance.
     *
     * @param valueX
     *         The "X" coordinate
     * @param valueY
     *         The "Y" coordinate
     * @param projection
     *         The projection for the coordinate
     */
    public CoordinateProjected(final Number valueX, final Number valueY, String projection) {
        super(valueX, valueY);
        this.projection = projection;
    }

    /**
     * Creates a new instance.
     *
     * @param valueX
     *         The "X" coordinate
     * @param valueY
     *         The "Y" coordinate
     * @param projection
     *         The projection for the coordinate
     * @param targetProjection
     *         The target projection for the coordinate
     */
    public CoordinateProjected(final Number valueX, final Number valueY, String projection, String targetProjection) {
        super(valueX, valueY);
        this.projection = projection;
        this.targetProjection = targetProjection;
    }

    /**
     * Returns the projection for the coordinate.
     *
     * @return Projection for this coordinate
     */
    public String getProjection() {
        return projection;
    }

    /**
     * Sets the projection for this coordinate.
     *
     * @param projection
     *         New value
     */
    public void setProjection(String projection) {
        this.projection = projection;
    }

    /**
     * Returns the target projection.
     *
     * @return Target projection for the coordinate
     */
    public String getTargetProjection() {
        return targetProjection;
    }

    /**
     * Sets the target projection for the coordinate.
     *
     * @param targetProjection
     *         New value
     */
    public void setTargetProjection(String targetProjection) {
        this.targetProjection = targetProjection;
    }

    /**
     * Sets the target projection for the coordinate.
     *
     * @param targetProjection
     *         New value
     * @return This instance
     */
    public CoordinateProjected targetProjection(String targetProjection) {
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
    public CoordinateProjected transform(String targetProjection) {
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
    public CoordinateProjected x(Number valueX) {
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
    public CoordinateProjected y(Number valueY) {
        setY(valueY);
        return this;
    }

    @Override
    public String renderJs() {

        if (targetProjection == null) {
            return super.renderJs();
        }

        return " ol.proj.transform(" + super.renderJs() + ", '" + getProjection() + "', '"
                + getTargetProjection() + "')";
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + getProjection() + ",[" + getX() + "," + getY() + "]]";
    }
}
