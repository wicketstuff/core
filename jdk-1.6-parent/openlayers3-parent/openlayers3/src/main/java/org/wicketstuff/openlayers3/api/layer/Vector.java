package org.wicketstuff.openlayers3.api.layer;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.openlayers3.api.Style;
import org.wicketstuff.openlayers3.api.source.ServerVector;
import org.wicketstuff.openlayers3.api.source.Source;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides an object that models a vector layer.
 */
public class Vector extends Layer {

    private List<VectorFeaturesLoadedListener> loadListeners = new ArrayList<>();

    /**
     * Style for the vector layer.
     */
    private Style style;

    /**
     * Creates a new instance.
     *
     * @param source
     *         Source of data for this layer
     */
    public Vector(Source source) {
        this(source, null);
    }

    /**
     * Creates a new instance.
     *
     * @param source
     *         Source Source of data for this layer
     * @param style
     *         Style used when drawing features
     */
    public Vector(Source source, Style style) {
        setSource(source);
        this.style = style;
    }

    /**
     * Returns the style used to draw features.
     *
     * @return Style used to draw features
     */
    public Style getStyle() {
        return style;
    }

    /**
     * Sets the style used to draw features.
     *
     * @param style
     *         New value
     */
    public void setStyle(Style style) {
        this.style = style;
    }

    /**
     * Sets the style used to draw features.
     *
     * @param style
     *         New value
     * @return This instance
     */
    public Vector style(Style style) {
        this.style = style;
        return this;
    }

    /**
     * Adds a new listener that will be invoked with feature data is loaded into this layer.
     *
     * @param listener
     *         Listener to invoke when feature data is loaded
     * @return This instance
     */
    public Layer addFeaturesLoadedListener(VectorFeaturesLoadedListener listener) {
        loadListeners.add(listener);
        return this;
    }

    /**
     * Removes a listener from the list of listeners that will be invoked when feature data is loaded into this layer.
     *
     * @param listener
     *         Listener to remove
     * @return This instance
     */
    public Layer removeFeaturesLoadedListener(VectorFeaturesLoadedListener listener) {
        loadListeners.remove(listener);
        return this;
    }

    /**
     * Notifies all registered listeners that features have been loaded into this layer.
     *
     * @param target
     *         Ajax request target
     */
    public void notifyFeaturesLoadedListeners(AjaxRequestTarget target) {
        for (VectorFeaturesLoadedListener listener : loadListeners) {
            listener.layerLoaded(target, this);
        }
    }

    @Override
    public Vector source(Source source) {
        this.setSource(source);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.layer.Vector";
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
        builder.append("{");

        builder.append("'id': '" + getJsId() + "',");

        if (getSource() instanceof ServerVector) {
            builder.append("'source': " + getSource().getJsId() + ",");
        } else {
            builder.append("'source': new " + getSource().getJsType() + "(");
            builder.append(getSource().renderJs());
            builder.append("),");
        }

        if (style != null) {
            builder.append("'style': new " + getStyle().getJsType() + "(");
            builder.append(getStyle().renderJs());
            builder.append("),");
        }

        builder.append("}");
        return builder.toString();
    }
}
