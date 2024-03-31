package org.wicketstuff.openlayers3.api.layer;

import com.google.gson.JsonArray;
import org.apache.wicket.ajax.AjaxRequestTarget;

import java.io.Serializable;

/**
 * Provides a listener that is invoked when feature data has been loaded into the vector layer.
 */
public abstract class VectorFeatureDataLoadedListener implements Serializable {

    /**
     * Callback method that is invoked when feature data has been loaded into the vector layer.
     *
     * @param target
     *         Ajax request target
     * @param layer
     *         Vector layer that has loaded feature data
     * @param features JsonArray with the list of loaded features
     */
    public abstract void layerLoaded(AjaxRequestTarget target, Vector layer, JsonArray features);
}
