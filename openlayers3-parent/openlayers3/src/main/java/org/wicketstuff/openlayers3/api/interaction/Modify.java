package org.wicketstuff.openlayers3.api.interaction;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.openlayers3.api.Feature;
import org.wicketstuff.openlayers3.api.layer.Vector;
import org.wicketstuff.openlayers3.api.style.Style;

/**
 * Provides an object that models a map modify interaction.
 */
public class Modify extends Interaction {

    /**
     * Features for this interaction.
     */
    private Feature[] features;

    /**
     * Source of features for this interaction.
     */
    private Vector vector;

    /**
     * Style for this interaction.
     */
    private Style style;

    /**
     * Creates a new instance.
     *
     * @param features
     *         Features for this interaction
     */
    public Modify(Feature... features) {
        this(new Style(), features);
    }

    /**
     * Creates a new instance.
     *
     * @param style
     *         Style for this interaction (applied to feature when interacting)
     * @param features
     *         Features for this interaction
     */
    public Modify(Style style, Feature... features) {
        this(style, null, features);
    }

    /**
     * Creates a new instance.
     *
     * @param vector
     *         Vector of features for this interaction
     */
    public Modify(Vector vector) {
        this(null, vector);
    }

    /**
     * Creates a new instance.
     *
     * @param style
     *         Style for this interaction (applied to feature when interacting)
     * @param vector
     *         Vector of features for this interaction
     */
    public Modify(Style style, Vector vector) {
        this(style, vector, (Feature[]) null);
    }

    /**
     * Creates a new instance.
     *
     * @param style
     *         Style for this interaction (applied to feature when interacting)
     * @param vector
     *         Vector of features for this interaction
     * @param features
     *         Features for this interaction
     */
    public Modify(Style style, Vector vector, Feature... features) {
        super();

        this.features = features;
        this.vector = vector;
        this.style = style;
    }

    /**
     * Returns the features for this interaction.
     *
     * @return Array of features
     */
    public Feature[] getFeatures() {
        return features;
    }

    /**
     * Sets the features for this interaction.
     *
     * @param features
     *         New value
     */
    public void setFeatures(Feature[] features) {
        this.features = features;
    }

    /**
     * Sets the features for this interaction.
     *
     * @param features
     *         New value
     * @return This instance
     */
    public Modify features(Feature[] features) {
        setFeatures(features);
        return this;
    }

    /**
     * Sets the features for this interaction.
     *
     * @param featuresList
     *         A list of feature instances
     * @return This instance
     */
    public Modify features(List<Feature> featuresList) {
        setFeatures(featuresList.toArray(new Feature[]{}));
        return this;
    }

    /**
     * Returns the vector source for this interaction's features.
     *
     * @return Vector source of features
     */
    public Vector getVector() {
        return vector;
    }

    /**
     * Sets the vector source for this interaction's features.
     *
     * @param vector
     *         Vector source of features
     */
    public void setVector(Vector vector) {
        this.vector = vector;
    }

    /**
     * Sets the vector source for this interaction's features.
     *
     * @param vector
     *         Vector source of features
     * @return This instance
     */
    public Modify vector(Vector vector) {
        setVector(vector);
        return this;
    }

    /**
     * Returns the style for this interaction.
     *
     * @return Current value
     */
    public Style getStyle() {
        return style;
    }

    /**
     * Sets the style for this interaction.
     *
     * @param style
     *         New value
     */
    public void setStyle(Style style) {
        this.style = style;
    }

    /**
     * Sets the style for this interaction.
     *
     * @param style
     *         New value
     * @return This instance
     */
    public Modify style(Style style) {
        setStyle(style);
        return this;
    }

    /**
     * Notifies the interaction that it has changed.
     *
     * @param target AJAX request target
     */
    public void changed(AjaxRequestTarget target) {
        target.appendJavaScript(getJsId() + ".changed();");
    }

    @Override
    public String getJsType() {
        return "ol.interaction.Modify";
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append(renderAttributesJs());
        builder.append("}");

        return builder.toString();
    }

    protected String renderAttributesJs() {

        StringBuilder builder = new StringBuilder();

        if (features != null && features.length > 0) {
            builder.append("'features': new ol.Collection([");

            for (Feature feature : features) {
                builder.append(feature.getJsId() + ",");
            }

            builder.append("]),");
        }

        if (vector != null) {
            builder.append("'features': new ol.Collection(");
            builder.append(vector.getJsId() + ".getSource().getFeatures()");
            builder.append("),");
        }

        if (style != null) {
            builder.append("'style': new " + style.getJsType() + "(" + style.renderJs() + "), ");
        } else {
            builder.append("'style': new ol.style.Style(), ");
        }

        return builder.toString();
    }
}
