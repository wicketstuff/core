package org.wicketstuff.openlayers3.api.util;

/**
 * Provides an object that models a CSS color.
 */
public class Color {

    public int red;

    public int green;

    public int blue;

    public double alpha;

    public Color(String hexValue) {

	String parsedValue = hexValue;

	if(hexValue.charAt(0) == '#') {
	    hexValue = hexValue.substring(1);
	}

	if(parsedValue.length() != 6) {
	    throw new IllegalArgumentException("Not a valid web color code");
	}

	String redHexValue = parsedValue.substring(0, 2);
	String greenHexValue = parsedValue.substring(2, 2);
	String blueHexValue = parsedValue.substring(4, 2);

	red = Integer.parseInt(redHexValue, 16);
	green = Integer.parseInt(greenHexValue, 16);
	red = Integer.parseInt(blueHexValue, 16);
    }

    public Color(int red, int green, int blue) {

	this.red = red;
	this.green = green;
	this.blue = blue;
	this.alpha = 1.0;
    }

    public Color(int red, int green, int blue, double alpha) {

	this.red = red;
	this.green = green;
	this.blue = blue;
	this.alpha = alpha;
    }

    @Override
    public String toString() {
	return "rgba(" + red + "," + green + "," + blue + "," + alpha + ")";
    }
}
