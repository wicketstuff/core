package org.wicketstuff.openlayers3.api;

import org.wicketstuff.openlayers3.api.style.Fill;
import org.wicketstuff.openlayers3.api.style.Image;

import java.io.Serializable;

/**
 * Provides an object that models a style for rendering vector features.
 */
public class Style extends JavascriptObject implements Serializable {

    /**
     * The fill for the style.
     */
    private Fill fill;

    /**
     * The image for the style.
     */
    private Image image;

    /**
     * Creates a new instance.
     *
     * @param image
     *         The image for the style
     */
    public Style(final Image image) {
        this.image = image;
    }

    /**
     * Creates a new instance.
     *
     * @param fill
     *         The fill for the style.
     */
    public Style(final Fill fill) {
        this.fill = fill;
    }

    /**
     * Returns the fill for the style.
     *
     * @return This style's fill
     */
    public Fill getFill() {
        return fill;
    }

    /**
     * Sets the fill for the style.
     *
     * @param fill
     *         New value
     */
    public void setFill(Fill fill) {
        this.fill = fill;
    }

    /**
     * Sets the fill for the style.
     *
     * @param fill
     *         New value
     * @return This instance
     */
    public Style fill(Fill fill) {
        setFill(fill);
        return this;
    }

    /**
     * Returns the image for this style.
     *
     * @return This style's image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets the image for this style.
     *
     * @param image
     *         New value
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Sets the image for this style.
     *
     * @param image
     *         New value
     * @return This instance
     */
    public Style image(Image image) {
        setImage(image);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.style.Style";
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
        builder.append("{");

        if (getFill() != null) {
            builder.append("'fill': new " + fill.getJsType() + "(" + fill.renderJs() + "),");
        }

        if (getImage() != null) {
            builder.append("'image': new " + image.getJsType() + "(" + image.renderJs() + "),");
        }

        builder.append("}");
        return builder.toString();
    }
}
