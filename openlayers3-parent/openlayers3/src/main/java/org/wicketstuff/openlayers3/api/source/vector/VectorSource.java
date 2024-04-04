package org.wicketstuff.openlayers3.api.source.vector;

import org.wicketstuff.openlayers3.api.Feature;
import org.wicketstuff.openlayers3.api.JavascriptObject;
import org.wicketstuff.openlayers3.api.format.FeatureFormat;
import org.wicketstuff.openlayers3.api.proj.Projection;
import org.wicketstuff.openlayers3.api.source.vector.loader.Loader;

import java.io.Serializable;
import java.util.List;

/**
 * Provides an object that models a server provided vector source.
 */
public class VectorSource extends JavascriptObject implements Serializable {

    /**
     * Projection used to transform the data.
     */
    private Projection projection;

    /**
     * Format for the features.
     */
    private FeatureFormat format;

    /**
     * The loader will handle fetching data and loading it into the data source.
     */
    private Loader loader;

    /**
     * List of static features for this data source.
     */
    private List<Feature> features;

    /**
     * Creates a new instance.
     *
     * @param features
     *         List of features for this source
     */
    public VectorSource(final List<Feature> features) {
        this(features, null, null, null);
    }

    /**
     * Creates a new instance.
     *
     * @param features
     *         List of features for this source
     * @param projection
     *         Projection for this source
     */
    public VectorSource(final List<Feature> features, final Projection projection) {
        this(features, null, null, projection);
    }

    /**
     * Creates a new instance.
     *
     * @param format
     *         Format for this source's features
     */
    public VectorSource(final FeatureFormat format) {
        this(null, format, null, null);
    }

    /**
     * Creates a new instance.
     *
     * @param format
     *         Format for this source's features
     * @param projection
     *         Projection for this source
     */
    public VectorSource(final FeatureFormat format, final Projection projection) {
        this(null, format, null, projection);
    }

    /**
     * Creates a new instance.
     *
     * @param format
     *         Format for the feature data
     * @param loader
     *         Loader for fetching remote data
     * @param projection
     *         Projection for transforming loaded data
     */
    public VectorSource(final FeatureFormat format, final Loader loader, final Projection projection) {
        this(null, format, loader, projection);
    }

    /**
     * Creates a new instance.
     *
     * @param features
     *         List of features for this source
     * @param format
     *         Format for the feature data
     * @param loader
     *         Loader for fetching remote data
     * @param projection
     *         Projection for transforming loaded data
     */
    public VectorSource(final List<Feature> features, final FeatureFormat format, final Loader loader,
                        final Projection projection) {
        this.features = features;
        this.projection = projection;
        this.format = format;
        this.loader = loader;

        // pass handle on this source to the loader
        if(this.loader != null) {
            this.loader.source(this);
        }
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

    /**
     * Returns the format for the features.
     *
     * @return Feature format
     */
    public FeatureFormat getFormat() {
        return format;
    }

    /**
     * Sets the format for the features.
     *
     * @param format
     *         New value
     */
    public void setFormat(FeatureFormat format) {
        this.format = format;
    }

    /**
     * Sets the format for the features.
     *
     * @param format
     *         New value
     * @return This instance
     */
    public VectorSource format(FeatureFormat format) {
        setFormat(format);
        return this;
    }

    /**
     * Returns the loader used to fetch remote data.
     *
     * @return Loader used to fetch remote data
     */
    public Loader getLoader() {
        return loader;
    }

    /**
     * Sets the loader used to fetch remote data.
     *
     * @param loader
     *         New value
     */
    public void setLoader(Loader loader) {
        this.loader = loader;
        this.loader.source(this);
    }

    /**
     * Sets the loader used to fetch remote data.
     *
     * @param loader
     *         New value
     * @return This instance
     */
    public VectorSource loader(Loader loader) {
        setLoader(loader);
        return this;
    }

    /**
     * Returns the projection for this source.
     *
     * @return Projection for this source
     */
    public Projection getProjection() {
        return projection;
    }

    /**
     * Sets the projection for this source.
     *
     * @param projection Projection for this source
     */
    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    /**
     * Sets the projection for this source.
     *
     * @param projection Projection for this source
     * @return This instance
     */
    public VectorSource projection(Projection projection) {
        this.setProjection(projection);
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

            builder.append("],");
        }

        if (loader != null) {
            builder.append("'loader': " + loader.renderJs() + ",");
            builder.append("'strategy': ol.loadingstrategy.bbox,");
        }

        if (getProjection() != null) {
            builder.append("'projection': new " + getProjection().getJsType() + "("
                    + getProjection().renderJs() + "),");
        }

        builder.append("}");
        return builder.toString();
    }
}
