package org.wicketstuff.openlayers3.api.layer;

import com.google.gson.JsonArray;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.openlayers3.api.source.vector.VectorSource;
import org.wicketstuff.openlayers3.api.style.ClusterStyle;
import org.wicketstuff.openlayers3.api.style.Style;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides an object that models a vector layer.
 */
public class Vector extends Layer {

    /**
     * The source for the data for this layer.
     */
    private VectorSource source;

    /**
     * List of listeners, these will be notified that data is loaded and receive that data.
     */
    private List<VectorFeatureDataLoadedListener> dataLoadedListeners =
            new ArrayList<VectorFeatureDataLoadedListener>();

    /**
     * List of listeners, these will be notified that data is loaded.
     */
    private List<VectorFeaturesLoadedListener> loadedListeners = new ArrayList<VectorFeaturesLoadedListener>();

    /**
     * Style for the vector layer.
     */
    private Style style;

    /**
     * Cluster style for the vector layer.
     */
    private ClusterStyle clusterStyle;

    /**
     * Creates a new instance.
     *
     * @param source
     *         Source of data for this layer
     */
    public Vector(VectorSource source) {
        this(source, null, null);
    }

    /**
     * Creates a new instance.
     *
     * @param source
     *         Source Source of data for this layer
     * @param style
     *         Style used when drawing features
     */
    public Vector(VectorSource source, Style style) {
        super();

        setSource(source);
        this.style = style;
    }

    /**
     * Creates a new instance.
     *
     * @param source
     *         Source Source of data for this layer
     * @param clusterStyle
     *         Style used when drawing features
     */
    public Vector(VectorSource source, ClusterStyle clusterStyle) {
        setSource(source);
        this.clusterStyle = clusterStyle;
    }

    /**
     * Creates a new instance.
     *
     * @param source
     *         Source of the data for this layer
     * @param style
     *         Style used when drawing features
     * @param clusterStyle
     *         Style used for drawing clustered features
     */
    private Vector(VectorSource source, Style style, ClusterStyle clusterStyle) {
        setSource(source);
        this.style = style;
        this.clusterStyle = clusterStyle;
    }

    /**
     * Returns the source for this layer.
     *
     * @return Source for this layer
     */
    public VectorSource getSource() {
        return source;
    }

    /**
     * Sets the source for this layer.
     *
     * @param source
     *         New value
     */
    public void setSource(VectorSource source) {
        this.source = source;
    }

    /**
     * Sets the source for this layer.
     *
     * @param source Source for the layer
     * @return This layer instance
     */
    public Vector source(VectorSource source) {
        this.setSource(source);
        return this;
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
    public Vector addFeatureDataLoadedListener(VectorFeatureDataLoadedListener listener) {
        dataLoadedListeners.add(listener);
        return this;
    }

    /**
     * Removes a listener from the list of listeners that will be invoked when feature data is loaded into this layer.
     *
     * @param listener
     *         Listener to remove
     * @return This instance
     */
    public Vector removeFeatureDataLoadedListener(VectorFeatureDataLoadedListener listener) {
        dataLoadedListeners.remove(listener);
        return this;
    }

    /**
     * Adds a new listener that will be invoked with feature data is loaded into this layer.
     *
     * @param listener
     *         Listener to invoke when feature data is loaded
     * @return This instance
     */
    public Vector addFeaturesLoadedListener(VectorFeaturesLoadedListener listener) {
        loadedListeners.add(listener);
        return this;
    }

    /**
     * Removes a listener from the list of listeners that will be invoked when feature data is loaded into this layer.
     *
     * @param listener
     *         Listener to remove
     * @return This instance
     */
    public Vector removeFeaturesLoadedListener(VectorFeaturesLoadedListener listener) {
        loadedListeners.remove(listener);
        return this;
    }

    /**
     * Returns the list of feature data loaded listeners.
     *
     * @return List of listeners
     */
    public List<VectorFeatureDataLoadedListener> getFeatureDataLoadedListeners() {
        return dataLoadedListeners;
    }

    /**
     * Returns the list of features loaded listeners.
     *
     * @return List of listeners
     */
    public List<VectorFeaturesLoadedListener> getFeaturesLoadedListeners() {
        return loadedListeners;
    }

    /**
     * Notifies all registered listeners that features have been loaded into this layer.
     *
     * @param target
     *         Ajax request target
     * @param features
     *         JsonArray with the list of loaded features
     */
    public void notifyFeatureDataLoadedListeners(AjaxRequestTarget target, JsonArray features) {
        for (VectorFeatureDataLoadedListener listener : dataLoadedListeners) {
            listener.layerLoaded(target, this, features);
        }
    }

    /**
     * Notifies all registered listeners that features have been loaded into this layer.
     *
     * @param target
     *         Ajax request target
     */
    public void notifyFeaturesLoadedListeners(AjaxRequestTarget target) {
        for (VectorFeaturesLoadedListener listener : loadedListeners) {
            listener.layerLoaded(target, this);
        }
    }

    @Override
    public String getJsType() {
        return "ol.layer.Vector";
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
        builder.append("{");

        builder.append("'id': \"" + getJsId() + "\",");

        if (getSource() instanceof VectorSource) {
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

        if (clusterStyle != null) {
            builder.append("'style': ");
            builder.append(clusterStyle.renderJs());
            builder.append(",");
        }

        builder.append("}");
        return builder.toString();
    }
}
