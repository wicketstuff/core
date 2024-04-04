package org.wicketstuff.openlayers3.api.layer;

import org.wicketstuff.openlayers3.api.JavascriptObject;

import java.io.Serializable;

/**
 * Provides an object that models a source of map data.
 */
public abstract class LayerSource extends JavascriptObject implements Serializable {

    /**
     * Creates a new instance.
     */
    public LayerSource() {
        super();
    }

    @Override
    public abstract String getJsType();

    @Override
    public abstract String renderJs();
}
