package org.wicketstuff.openlayers3.api.layer;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.openlayers3.api.JavascriptObject;

import java.io.Serializable;

/**
 * Provides an object that models a layer.
 */
public abstract class Layer extends JavascriptObject implements Serializable {

    /**
     * Sets the visiblity of the layer.
     *
     * @param target
     *         Ajax request target
     * @param visible
     *         Flag with the visibility of the layer
     */
    public void setVisible(AjaxRequestTarget target, boolean visible) {
        target.appendJavaScript(getJsId() + ".setVisible(" + visible + ")");
    }
}
