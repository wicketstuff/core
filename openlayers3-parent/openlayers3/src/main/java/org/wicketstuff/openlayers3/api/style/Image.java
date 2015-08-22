package org.wicketstuff.openlayers3.api.style;

import org.wicketstuff.openlayers3.api.JavascriptObject;

import java.io.Serializable;

/**
 * Provides a class that models an image style for vector features.
 */
public class Image extends JavascriptObject implements Serializable {

    /**
     * The rotation of the image.
     */
    private Number rotation;

    /**
     * The scale of the image.
     */
    private Number scale;

    /**
     * Creates a new instance.
     */
    public Image() {
        super();
    }

    /**
     * Returns the rotation of the image.
     *
     * @return Rotation value
     */
    public Number getRotation() {
        return rotation;
    }

    /**
     * Sets the rotation of the image.
     *
     * @param rotation
     *         New value
     */
    public void setRotation(Number rotation) {
        this.rotation = rotation;
    }

    /**
     * Sets the rotation of the image.
     *
     * @param number
     *         New value
     * @return This instance
     */
    public Image rotation(Number number) {
        setRotation(number);
        return this;
    }

    /**
     * Returns the scale of the image.
     *
     * @return Scale value
     */
    public Number getScale() {
        return scale;
    }

    /**
     * Sets the scale of the image.
     *
     * @param scale
     *         New value
     */
    public void setScale(Number scale) {
        this.scale = scale;
    }

    /**
     * Sets the scale of the image.
     *
     * @param scale
     *         New value
     * @return This instance
     */
    public Image scale(Number scale) {
        setScale(scale);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.style.Image";
    }

    protected String renderAttributesJs() {

        StringBuilder builder = new StringBuilder();

        if (getRotation() != null) {
            builder.append("'rotation': " + getRotation() + ",");
        }

        if (getScale() != null) {
            builder.append("'scale': " + getScale() + ",");
        }

        return builder.toString();
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append(renderAttributesJs());
        builder.append("}");
        return builder.toString();
    }
}
