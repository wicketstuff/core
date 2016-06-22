package org.wicketstuff.openlayers3.api.layer;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.wicketstuff.openlayers3.api.JavascriptObject;

/**
 * Provides an object that models a layer.
 */
public abstract class Layer extends JavascriptObject implements Serializable {

    private IModel<Boolean> visibleModel;

    /**
     * Creates a new Layer.
     */
    public Layer() {
        super();
    }

    /**
     * Creates a new Layer with the visibility determined by the
     * {@code visibleModel}
     *
     * @param visibleModel
     *            a modle of whether the layer is visible
     */
    public Layer(IModel<Boolean> visibleModel) {
        this.visibleModel = visibleModel;
    }

    /**
     * Sets the visiblity of the layer.
     *
     * @param target
     *            Ajax request target
     * @param visible
     *            Flag with the visibility of the layer
     */
    public void setVisible(AjaxRequestTarget target, boolean visible) {
        target.appendJavaScript(getJsId() + ".setVisible(" + visible + ")");
    }

    /**
     * Callback for updating the layer via ajax.
     *
     * @param target
     */
    public void onUpdate(AjaxRequestTarget target) {
        if (visibleModel != null) {
            target.appendJavaScript(getJsId() + ".setVisible(" + visibleModel.getObject() + ")");
        }
    }

    /**
     * @return the model of the visibility of the layer
     */
    protected IModel<Boolean> getVisibleModel() {
        return visibleModel;
    }

    /**
     * Callback for detaching any resources (e.g., models).
     */
    public void detach() {
        if (visibleModel != null) {
            visibleModel.detach();
        }
    }

}
