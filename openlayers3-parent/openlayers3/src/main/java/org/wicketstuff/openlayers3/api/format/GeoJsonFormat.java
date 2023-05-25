package org.wicketstuff.openlayers3.api.format;

/**
 * Provides an object that models a GeoJSON format for providing feature data.
 */
public class GeoJsonFormat extends FeatureFormat {

    /**
     * The default projection for this format.
     */
    private String defaultProjection;

    /**
     * The geometry name to use when creating features.
     */
    private String geometryName;

    /**
     * Creates a new instance.
     */
    public GeoJsonFormat() {
        this(null, null);
    }

    /**
     * Creates a new instance.
     *
     * @param defaultProjection
     *         Default projection for this format
     * @param geometryName
     *         The name to use when creating features
     */
    public GeoJsonFormat(final String defaultProjection, final String geometryName) {
        super();

        this.defaultProjection = defaultProjection;
        this.geometryName = geometryName;
    }

    /**
     * Returns the default projection.
     *
     * @return String with the projection
     */
    public String getDefaultProjection() {
        return defaultProjection;
    }

    /**
     * Sets the default projection.
     *
     * @param defaultProjection
     *         New value
     */
    public void setDefaultProjection(String defaultProjection) {
        this.defaultProjection = defaultProjection;
    }

    /**
     * Sets the default projection.
     *
     * @param defaultProjection
     *         New value
     * @return This instance
     */
    public GeoJsonFormat defaultProjection(String defaultProjection) {
        this.defaultProjection = defaultProjection;
        return this;
    }

    /**
     * Returns the geometry name.
     */
    public String getGeometryName() {
        return geometryName;
    }

    /**
     * Sets the geometry name.
     *
     * @param geometryName
     *         New value
     */
    public void setGeometryName(String geometryName) {
        this.geometryName = geometryName;
    }

    /**
     * Sets the geometry name.
     *
     * @param geometryName
     *         New value
     * @return This instance
     */
    public GeoJsonFormat geometryName(String geometryName) {
        this.geometryName = geometryName;
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.format.GeoJSON";
    }

    @Override
    public String renderJs() {
        StringBuilder builder = new StringBuilder();

        if (defaultProjection != null || geometryName != null) {
            builder.append("{");

            if (defaultProjection != null) {
                builder.append("'defaultProjection': '" + defaultProjection + "',");
            }

            if (geometryName != null) {
                builder.append("'geometryName': '" + geometryName + "',");
            }

            builder.append("}");
        }

        return builder.toString();
    }
}
