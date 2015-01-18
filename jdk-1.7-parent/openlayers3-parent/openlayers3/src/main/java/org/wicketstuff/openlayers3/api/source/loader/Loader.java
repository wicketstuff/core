package org.wicketstuff.openlayers3.api.source.loader;


import org.wicketstuff.openlayers3.api.JavascriptObject;
import org.wicketstuff.openlayers3.api.source.Source;

/**
 * Provides an object that models a vector source loading strategy.
 */
public abstract class Loader extends JavascriptObject {

    /**
     * The data source that will store the loaded features.
     */
    private Source source;

    /**
     * Creates a new instance.
     */
    public Loader() {
        super();
    }

    /**
     * Sets the data source.
     *
     * @param source
     *         New value
     * @return This instance
     */
    public Loader source(final Source source) {
        this.source = source;
        return this;
    }

    /**
     * Sets the data source.
     *
     * @return New value
     */
    public Source getSource() {
        return source;
    }

    /**
     * Sets the data source.
     *
     * @param source
     *         New value
     */
    public void setSource(Source source) {
        this.source = source;
    }
}
