package org.wicketstuff.openlayers3.api.source;

/**
 * Provides an object that models a source of map data that provides clusters.
 */
public class Cluster extends Source {

    private Number distance;

    private Source source;

    public Cluster(Number distance, Source source) {
        super();

        this.distance = distance;
        this.source = source;
    }

    public Number getDistance() {
        return distance;
    }

    public void setDistance(Number distance) {
        this.distance = distance;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
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
