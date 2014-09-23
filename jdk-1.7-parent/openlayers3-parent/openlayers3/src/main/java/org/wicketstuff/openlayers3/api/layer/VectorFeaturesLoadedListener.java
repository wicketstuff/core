package org.wicketstuff.openlayers3.api.layer;

import org.apache.wicket.ajax.AjaxRequestTarget;

import java.io.Serializable;

/**
 * Provides a listener that is invoked when features have been loaded into the vector layer.
 */
public abstract class VectorFeaturesLoadedListener implements Serializable {

    /**
     * Callback method that is invoked when features have been loaded into the vector layer.
     *
     * @param target
     *         Ajax request target
     * @param layer
     *         Vector layer that has loaded feature data
     */
    public abstract void layerLoaded(AjaxRequestTarget target, Vector layer);
}
