package org.wicketstuff.openlayers3.api.proj;

import org.wicketstuff.openlayers3.api.JavascriptObject;

import java.io.Serializable;

/**
 * Provides an object that models a map projection. An instance is created for each projection supported in the
 * application and stored in the ol.proj namespace. You can use these in applications, but this is not required, as API
 * params and options use ol.proj.ProjectionLike which means the simple string code will suffice.
 */
public class Projection extends JavascriptObject implements Serializable {

    /**
     * Default units for new projections.
     */
    private final static String DEFAULT_UNITS = "degrees";

    /**
     * Default axis orientation for new projections.
     */
    private final static String DEFAULT_AXIS_ORIENTATION = "enu";

    /**
     * SRS identification code, e.g. "EPSG:4326".
     */
    private String code;

    /**
     * The units for this projection.
     */
    private String units;

    /**
     * The axis orientation as specified in Proj4.
     */
    private String axisOrientation;

    /**
     * Creates a new instance.
     *
     * @param code
     *         SRS identification code
     */
    public Projection(final String code) {
        this(code, DEFAULT_UNITS, DEFAULT_AXIS_ORIENTATION);
    }

    /**
     * Creates a new instance.
     *
     * @param code
     *         SRS identification code
     * @param units
     *         Units for the projection
     * @param axisOrientation
     *         Axis orientation
     */
    public Projection(final String code, final String units, final String axisOrientation) {
        super();

        this.code = code;
        this.units = units;
        this.axisOrientation = axisOrientation;
    }

    /**
     * Returns the SRS identification code for this projection.
     *
     * @return String with the SRS identification code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the SRS identification code.
     *
     * @param code
     *         New value
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets the SRS identification code.
     *
     * @param code
     *         New value
     * @return This instance
     */
    public Projection code(String code) {
        setCode(code);
        return this;
    }

    /**
     * Returns the units for this projection.
     *
     * @return String with the units
     */
    public String getUnits() {
        return units;
    }

    /**
     * Sets the units for this projection.
     *
     * @param units
     *         New value
     */
    public void setUnits(String units) {
        this.units = units;
    }

    /**
     * Sets the units for this projection.
     *
     * @param units
     *         New value
     * @return This instance
     */
    public Projection units(String units) {
        setUnits(units);
        return this;
    }

    /**
     * Returns the axis orientation for this projection.
     *
     * @return Axis orientation for this projection
     */
    public String getAxisOrientation() {
        return axisOrientation;
    }

    /**
     * Sets the axis orientation for this projection.
     *
     * @param axisOrientation
     *         New value
     */
    public void setAxisOrientation(String axisOrientation) {
        this.axisOrientation = axisOrientation;
    }

    /**
     * Sets the axis orientation for this projection.
     *
     * @param axisOrientation
     *         New value
     * @return This instance
     */
    public Projection axisOrientation(String axisOrientation) {
        setAxisOrientation(axisOrientation);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.proj.Projection";
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
        builder.append("{");

        if (code != null) {
            builder.append("'code': '" + code + "',");
        }

        if (units != null) {
            builder.append("'units': '" + units + "',");
        }

        if (units != null) {
            builder.append("'axisOrientation': '" + axisOrientation + "',");
        }

        builder.append("}");
        return builder.toString();
    }
}
