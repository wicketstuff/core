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
    private IModel<? extends Number> opacityModel;

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
        if (opacityModel != null) {
            target.appendJavaScript(getJsId() + ".setOpacity(" + opacityModel.getObject() + ")");
        }
    }

    /**
     * @return the model of the visibility of the layer
     */
    public IModel<Boolean> getVisibleModel() {
        return visibleModel;
    }

    /**
     * @param visibleModel visible model to set
     * @return this
     */
    public Layer setVisibleModel(IModel<Boolean> visibleModel) {
        this.visibleModel = visibleModel;
        return this;
    }

    /**
     * @return the model of the opacity of the layer
     */
    public IModel<? extends Number> getOpacityModel() {
        return opacityModel;
    }

    /**
     * @param opacityModel opacity model to set
     * @return this
     */
    public Layer setOpacityModel(IModel<? extends Number> opacityModel) {
        this.opacityModel = opacityModel;
        return this;
    }

    /**
     * Callback for detaching any resources (e.g., models).
     */
    public void detach() {
        if (visibleModel != null) {
            visibleModel.detach();
        }
        if (opacityModel != null) {
            opacityModel.detach();
        }
    }

}
