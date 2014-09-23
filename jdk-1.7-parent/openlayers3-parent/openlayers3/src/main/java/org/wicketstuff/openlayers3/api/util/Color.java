package org.wicketstuff.openlayers3.api.util;

import java.io.Serializable;

/**
 * Provides an object that models a CSS color.
 */
public class Color implements Serializable {

    /**
     * The red value.
     */
    public int red;

    /**
     * The green value.
     */
    public int green;

    /**
     * The blue value.
     */
    public int blue;

    /**
     * The alpha value (for transparency).
     */
    public double alpha;

    /**
     * Creates a new color.
     *
     * @param hexValue The hexadecimal color code used to populate the color values.
     */
    public Color(String hexValue) {

        String parsedValue = hexValue;

        if (parsedValue.charAt(0) == '#') {
            parsedValue = parsedValue.substring(1);
        }

        if (parsedValue.length() != 6) {
            throw new IllegalArgumentException("Not a valid web color code");
        }

        String redHexValue = parsedValue.substring(0, 2);
        String greenHexValue = parsedValue.substring(2, 4);
        String blueHexValue = parsedValue.substring(4, 6);

        red = Integer.parseInt(redHexValue, 16);
        green = Integer.parseInt(greenHexValue, 16);
        blue = Integer.parseInt(blueHexValue, 16);
        alpha = 1.0;
    }

    /**
     * Creates a new color.
     *
     * @param red The red value
     * @param green The green value
     * @param blue The blue value
     */
    public Color(int red, int green, int blue) {

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = 1.0;
    }

    /**
     * Creates a new color.
     *
     * @param red The red value
     * @param green The green value
     * @param blue The blue value
     * @param alpha The alpha value
     */
    public Color(int red, int green, int blue, double alpha) {

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    /**
     * Returns the red value.
     *
     * @return int with the red value
     */
    public int getRed() {
        return red;
    }

    /**
     * Sets the red value.
     *
     * @param red New value
     */
    public void setRed(int red) {
        this.red = red;
    }

    /**
     * Sets the red value.
     *
     * @param red New value
     * @return This instance
     */
    public Color red(int red) {
        setRed(red);
        return this;
    }

    /**
     * Returns the green value.
     *
     * @return int with the green value
     */
    public int getGreen() {
        return green;
    }

    /**
     * Sets the green value.
     *
     * @param green New value
     */
    public void setGreen(int green) {
        this.green = green;
    }

    /**
     * Sets the green value.
     *
     * @param green int with the new value
     * @return This instance
     */
    public Color green(int green) {
        setGreen(green);
        return this;
    }

    /**
     * Returns the blue value.
     *
     * @return int with the blue value
     */
    public int getBlue() {
        return blue;
    }

    /**
     * Sets the blue value.
     *
     * @param blue New value
     */
    public void setBlue(int blue) {
        this.blue = blue;
    }

    /**
     * Sets the blue value.
     *
     * @param blue New value
     * @return This instance
     */
    public Color blue(int blue) {
        setBlue(blue);
        return this;
    }

    /**
     * Returns the alpha value.
     *
     * @return int with the alpha value
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * Sets the alpha value.
     *
     * @param alpha New value
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    /**
     * Sets the alpha value.
     *
     * @param alpha New instance
     * @return This instance
     */
    public Color alpha(double alpha) {
        setAlpha(alpha);
        return this;
    }

    public String renderJs() {
        return "[" + red + "," + green + "," + blue + "," + alpha + "]";
    }

    @Override
    public String toString() {
        return "rgba(" + red + "," + green + "," + blue + "," + alpha + ")";
    }
}
