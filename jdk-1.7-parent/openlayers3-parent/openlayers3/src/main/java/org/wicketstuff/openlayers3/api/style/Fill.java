package org.wicketstuff.openlayers3.api.style;

import org.wicketstuff.openlayers3.api.JavascriptObject;

import java.awt.*;
import java.io.Serializable;

/**
 * Provides an object that models a fill style for vector features.
 */
public class Fill extends JavascriptObject implements Serializable {

    /**
     * Color used for this fill.
     */
    private Color color;

    /**
     * Creates a new instance.
     *
     * @param color
     *         String with the hexadecimal color code for this fill
     */
    public Fill(String color) {
        this(Color.decode(color));
    }

    /**
     * Creates a new instance.
     *
     * @param color
     *         Color for this fill
     */
    public Fill(Color color) {
        this.color = color;
    }

    /**
     * Returns the color for this fill.
     *
     * @return Color instance
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color for this fill.
     *
     * @param color
     *         String with the hexadecimal color code for this fill
     */
    public void setColor(String color) {
        setColor(Color.decode(color));
    }

    /**
     * Sets the color for this fill.
     *
     * @param color
     *         New value
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the color for this fill.
     *
     * @param color
     *         New value
     * @return This instance
     */
    public Fill color(Color color) {
        setColor(color);
        return this;
    }

    /**
     * Creates a new instance.
     *
     * @param color
     *         String with the hexadecimal color code for this fill
     * @return This instance
     */
    public Fill color(String color) {
        setColor(color);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.style.Fill";
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
        builder.append("{");

        if (getColor() != null) {
            builder.append("'color':  rgba(" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue()
                    + ", " + color.getAlpha() + "),");
        }

        builder.append("}");
        return builder.toString();
    }
}
