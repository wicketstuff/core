package org.wicketstuff.openlayers3.api.style;

import org.wicketstuff.openlayers3.api.JavascriptObject;

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
     * The stroke for this style.
     */
    private Stroke stroke;

    /**
     * The text for this style.
     */
    private Text text;

    /**
     * The z-index for this style.
     */
    private Number zIndex;

    /**
     * Creates a new instance.
     */
    public Style() {

    }

    /**
     * Creates a new instance.
     *
     * @param image
     *         The image for the style
     */
    public Style(final Image image) {
        this(null, image, null, null, null);
    }

    /**
     * Creates a new instance.
     *
     * @param fill
     *         The fill for the style.
     */
    public Style(final Fill fill) {
        this(fill, null, null, null, null);
    }

    /**
     * Creates a new instance.
     *
     * @param fill
     *         The fill for this style
     * @param image
     *         The image for this style
     * @param stroke
     *         The stroke for this style
     * @param text
     *         The text for this style
     * @param zIndex
     *         The z-index for this style
     */
    public Style(Fill fill, Image image, Stroke stroke, Text text, Number zIndex) {
        super();

        this.fill = fill;
        this.image = image;
        this.stroke = stroke;
        this.text = text;
        this.zIndex = zIndex;
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

    /**
     * Returns the stroke for this style.
     *
     * @return Current value
     */
    public Stroke getStroke() {
        return stroke;
    }

    /**
     * Sets the stroke for this style
     *
     * @param stroke
     *         New value
     */
    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    /**
     * Sets the stroke for this style.
     *
     * @param stroke
     *         New value
     * @return This instance
     */
    public Style stroke(Stroke stroke) {
        setStroke(stroke);
        return this;
    }

    /**
     * Returns the text for this style.
     *
     * @return Current value
     */
    public Text getText() {
        return text;
    }

    /**
     * Sets the text for this instance.
     *
     * @param text
     *         New value
     */
    public void setText(Text text) {
        this.text = text;
    }

    /**
     * Sets the text for this style.
     *
     * @param text
     *         New value
     * @return This instance
     */
    public Style text(Text text) {
        setText(text);
        return this;
    }

    /**
     * Returns the current z-index for this style.
     *
     * @return Current value
     */
    public Number getzIndex() {
        return zIndex;
    }

    /**
     * Sets the z-index for this style.
     *
     * @param zIndex
     *         New value
     */
    public void setzIndex(Number zIndex) {
        this.zIndex = zIndex;
    }

    /**
     * Sets the z-index for this style.
     *
     * @param zIndex
     *         New value
     * @return This instance
     */
    public Style zIndex(Number zIndex) {
        setzIndex(zIndex);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.style.Style";
    }

    public String renderAttributesJs() {

        StringBuilder builder = new StringBuilder();

        if (getFill() != null) {
            builder.append("'fill': new " + getFill().getJsType() + "(" + getFill().renderJs() + "),");
        }

        if (getImage() != null) {
            builder.append("'image': new " + getImage().getJsType() + "(" + getImage().renderJs() + "),");
        }

        if (getStroke() != null) {
            builder.append("'stroke': new " + getStroke().getJsType() + "(" + getStroke().renderJs() + "),");
        }

        if (getText() != null) {
            builder.append("'text': new " + getText().getJsType() + "(" + getText().renderJs() + "),");
        }

        if (getzIndex() != null) {
            builder.append("'zIndex': " + getzIndex() + ",");
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
