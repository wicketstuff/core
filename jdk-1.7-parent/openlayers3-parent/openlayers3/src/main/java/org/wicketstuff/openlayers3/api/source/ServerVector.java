package org.wicketstuff.openlayers3.api.source;

import org.wicketstuff.openlayers3.api.format.Feature;
import org.wicketstuff.openlayers3.api.proj.Projection;
import org.wicketstuff.openlayers3.api.source.loader.Loader;

/**
 * Provides an object that models a server provided vector source.
 */
public class ServerVector extends StaticVector {

    /**
     * Format for the features.
     */
    private Feature format;

    /**
     * The loader will handle fetching data and loading it into the data source.
     */
    private Loader loader;

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
    public ServerVector(final Feature format, final Loader loader, final Projection projection) {
        super(projection);

        this.format = format;
        this.loader = loader;
        this.loader.source(this);
    }

    /**
     * Returns the format for the features.
     *
     * @return Feature format
     */
    public Feature getFormat() {
        return format;
    }

    /**
     * Sets the format for the features.
     *
     * @param format
     *         New value
     */
    public void setFormat(Feature format) {
        this.format = format;
    }

    /**
     * Sets the format for the features.
     *
     * @param format
     *         New value
     * @return This instance
     */
    public ServerVector format(Feature format) {
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
    public ServerVector loader(Loader loader) {
        setLoader(loader);
        return this;
    }

    @Override
    public ServerVector projection(Projection projection) {
        this.setProjection(projection);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.source.ServerVector";
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
        builder.append("{");

        if (format != null) {
            builder.append("'format': new " + format.getJsType() + "(" + format.renderJs() + "),");
        }

        if (loader != null) {
            builder.append("'loader': " + loader.renderJs() + ",");
        }

        if (getProjection() != null) {
            builder.append("'projection': new " + getProjection().getJsType() + "("
                    + getProjection().renderJs() + "),");
        }

        builder.append("}");
        return builder.toString();
    }
}
