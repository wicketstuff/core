package org.wicketstuff.openlayers3.api.source.vector;

import org.wicketstuff.openlayers3.api.format.FeatureFormat;
import org.wicketstuff.openlayers3.api.proj.Projection;

/**
 * Provides an object that models a source of map data that provides clusters.
 */
public class Cluster extends VectorSource {

    /**
     * Minimum distance (in pixels) between clusters.
     */
    private Number distance;

    /**
     * Data source that provides the data to be clustered.
     */
    private VectorSource source;

    /**
     * Creates a new instance.
     *
     * @param format
     *         Format used for parsing features
     * @param projection
     *         Projection to use
     * @param distance
     *         Minimum distance (in pixels) between clusters
     * @param source
     *         Data source that is being clustered
     */
    public Cluster(final FeatureFormat format, final Projection projection, final Number distance, final VectorSource source) {
        super(format, projection);

        this.distance = distance;
        this.source = source;
    }

    /**
     * Returns the minimum distance (in pixels) between clusters.
     *
     * @return Minimum distance between clusters
     */
    public Number getDistance() {
        return distance;
    }

    /**
     * Sets the minimum distance between clusters (in pixels).
     *
     * @param distance Minimum distances between clusters
     */
    public void setDistance(Number distance) {
        this.distance = distance;
    }

    /**
     * Sets the minimum distance between clusters (in pixels).
     *
     * @param distance Minimum distance between clusters
     * @return This instance
     */
    public Cluster distance(Number distance) {
        setDistance(distance);
        return this;
    }

    /**
     * Returns the data source being clustered.
     *
     * @return Data source being clustered
     */
    public VectorSource getSource() {
        return source;
    }

    /**
     * Sets the data source being clustered.
     *
     * @param source Data source being clustered.
     */
    public void setSource(VectorSource source) {
        this.source = source;
    }

    /**
     * Sets the data source being clustered.
     *
     * @param source Data source being clustered
     * @return This instance
     */
    public Cluster source(VectorSource source) {
        setSource(source);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.source.Cluster";
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
        builder.append("{");

        if (distance != null) {
            builder.append("'distance': " + distance + ",");
        }

        if (source != null) {
            builder.append("'source': " + source.getJsId() + ",");
        }

        builder.append("}");
        return builder.toString();
    }
}
