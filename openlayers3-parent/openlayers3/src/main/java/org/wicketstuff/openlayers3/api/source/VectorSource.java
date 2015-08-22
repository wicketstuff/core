package org.wicketstuff.openlayers3.api.source;

import org.wicketstuff.openlayers3.api.Feature;

import java.util.List;

/**
 * Provides an object that models a source of vector data.
 */
public class VectorSource extends Source {

    /**
     * List of features for this data source.
     */
    private List<Feature> features;

    /**
     * Creates a new instance.
     *
     * @param features
     *         Features for this source
     */
    public VectorSource(List<Feature> features) {
        super();

        this.features = features;
    }

    /**
     * Returns a list of features for this source.
     *
     * @return List of Feature instances
     */
    public List<Feature> getFeatures() {
        return features;
    }

    /**
     * Sets the list of features for this source.
     *
     * @param features
     *         List of Feature instances
     */
    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    /**
     * Sets the list of features for this source.
     *
     * @param features
     *         List of Feature instances
     * @return this instance
     */
    public VectorSource features(List<Feature> features) {
        setFeatures(features);
        return this;
    }

    /**
     * Adds a feature to the list of features for this data source.
     *
     * @param feature
     *         Feature to add to this layer
     * @return This instance
     */
    public VectorSource addFeature(Feature feature) {
        getFeatures().add(feature);
        return this;
    }

    /**
     * Removes a feature from the list of features for this layer.
     *
     * @param feature
     *         Feature instance to remove
     * @return This instance
     */
    public VectorSource removeFeature(Feature feature) {
        getFeatures().remove(feature);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.source.Vector";
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
        builder.append("{");

        if (getFeatures() != null) {
            builder.append("'features': [");

            for (Feature feature : getFeatures()) {
                builder.append(feature.getJsId());
                builder.append(",");
            }

            builder.append("]");
        }

        builder.append("}");
        return builder.toString();
    }
}
