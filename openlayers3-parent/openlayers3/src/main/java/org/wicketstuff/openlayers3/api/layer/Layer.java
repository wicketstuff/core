package org.wicketstuff.openlayers3.api.layer;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.openlayers3.api.JavascriptObject;
import org.wicketstuff.openlayers3.api.source.Source;

import java.io.Serializable;

/**
 * Provides an object that models a layer.
 */
public abstract class Layer extends JavascriptObject implements Serializable {

    /**
     * The source for the data for this layer.
     */
    private Source source;

    /**
     * Returns the source for this layer.
     *
     * @return Source for this layer
     */
    public Source getSource() {
        return source;
    }

    /**
     * Sets the source for this layer.
     *
     * @param source
     *         New value
     */
    public void setSource(Source source) {
        this.source = source;
    }

    /**
     * Sets the source for this layer.
     *
     * @param source
     *         New value
     * @return this instance
     */
    public Layer source(Source source) {
        setSource(source);
        return this;
    }

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
