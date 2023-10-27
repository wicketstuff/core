package org.wicketstuff.egrid.attribute;

import java.io.Serial;
import java.io.Serializable;

public class StyleAttribute implements Serializable {

    public static final String HEIGHT = "height";
    public static final String WIDTH = "width";
    public static final String DISPLAY = "display";
    public static final String VISIBILITY = "visibility";
    public static final String TOP = "top";
    public static final String LEFT = "left";
    public static final String TEXT_ALIGN = "text-align";
    public static final String COLOR = "color";
    public static final String FONT_WEIGHT = "font-weight";
    public static final String FONT_SIZE = "font-size";
    public static final String ALIGN_CENTER = "center";
    public static final String ALIGN_RIGHT = "right";
    public static final String ALIGN_LEFT = "left";
    public static final String FONT_BOLD = "bold";
    @Serial
    private static final long serialVersionUID = 1L;
    private Options styleOptions = null;

    public StyleAttribute() {
        super();
        styleOptions = new Options();
    }

    public final StyleAttribute setTop(final String top) {
        put(TOP, top);
        return this;
    }

    public final StyleAttribute setLeft(final String left) {
        put(LEFT, left);
        return this;
    }

    public final StyleAttribute setHeight(final String height) {
        put(HEIGHT, height);
        return this;
    }

    public final StyleAttribute setWidth(final String width) {
        put(WIDTH, width);
        return this;
    }

    public final StyleAttribute setDisplay(final String display) {
        put(DISPLAY, display);
        return this;
    }

    public final StyleAttribute setVisibility(final String visibility) {
        put(VISIBILITY, visibility);
        return this;
    }

    public final StyleAttribute setTextAlign(final String alignment) {
        put(TEXT_ALIGN, alignment);
        return this;
    }

    public final StyleAttribute setColor(final String color) {
        put(COLOR, color);
        return this;
    }

    public final StyleAttribute setRedColor() {
        put(COLOR, "red");
        return this;
    }

    public final StyleAttribute setFontWeight(final String fontWeight) {
        put(FONT_WEIGHT, fontWeight);
        return this;
    }

    public final StyleAttribute setFontSize(final int size) {
        put(TOP, size + "px");
        return this;
    }

    public final StyleAttribute setBoldFont() {
        put(FONT_WEIGHT, FONT_BOLD);
        return this;
    }

    public final StyleAttribute alignCenter() {
        setTextAlign(ALIGN_CENTER);
        return this;
    }

    public final StyleAttribute alignLeft() {
        setTextAlign(ALIGN_LEFT);
        return this;
    }

    public final StyleAttribute alignRight() {
        setTextAlign(ALIGN_RIGHT);
        return this;
    }

    public final String getStyles() {
        return styleOptions.getCSSOptions();
    }

    public final void put(final String attribute, final String value) {
        styleOptions.put(attribute, value);
    }
}
