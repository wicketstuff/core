package org.wicketstuff.openlayers3.api.style;

import org.wicketstuff.openlayers3.api.JavascriptObject;
import org.wicketstuff.openlayers3.api.util.Color;
import org.wicketstuff.openlayers3.api.util.Joiner;

import java.io.Serializable;

/**
 * Provides a class that models a stroke style.
 */
public class Stroke extends JavascriptObject implements Serializable {

    /**
     * Enumeration of valid line cap values.
     */
    public enum LineCap {
        BUTT("butt"), ROUND("round"), SQUARE("square");

        String value;

        LineCap(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * Default line cap style.
     */
    private final static LineCap DEFAULT_LINE_CAP = LineCap.ROUND;

    /**
     * Default line join style.
     */
    private final static LineCap DEFAULT_LINE_JOIN = LineCap.ROUND;

    /**
     * Default miter limit.
     */
    private final static Integer DEFAULT_MITER_LIMIT = 10;

    /**
     * Color for this fill.
     */
    private Color color;

    /**
     * Line cap style.
     */
    private LineCap lineCap;

    /**
     * Line join style.
     */
    private LineCap lineJoin;

    /**
     * Line dash pattern.
     */
    private Number[] lineDash;

    /**
     * Miter limit.
     */
    private Number miterLimit;

    /**
     * Width of the stroke.
     */
    private Number width;

    /**
     * Creates a new instance.
     *
     * @param color
     *         Color for this instance
     */
    public Stroke(String color) {
        this(new Color(color));
    }

    /**
     * Creates a new instance.
     *
     * @param color
     *         Color for this instance
     */
    public Stroke(Color color) {
        this(color, DEFAULT_LINE_CAP, DEFAULT_LINE_JOIN, null, DEFAULT_MITER_LIMIT, null);
    }

    /**
     * Creates a new instance.
     *
     * @param color
     *         Color for this stroke
     * @param lineCap
     *         Line cap style for this stroke
     * @param lineJoin
     *         Line join style for this stroke
     * @param lineDash
     *         Line dash pattern
     * @param miterLimit
     *         Miter limit
     * @param width
     *         Width of the stroke
     */
    public Stroke(Color color, LineCap lineCap, LineCap lineJoin, Number[] lineDash, Number miterLimit, Number width) {
        super();

        this.color = color;
        this.lineCap = lineCap;
        this.lineJoin = lineJoin;
        this.lineDash = lineDash;
        this.miterLimit = miterLimit;
        this.width = width;
    }

    /**
     * Returns the stroke color.
     *
     * @return Current value
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the stroke color.
     *
     * @param color
     *         New value
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the stroke color.
     *
     * @param color
     *         New value
     * @return This instance
     */
    public Stroke color(Color color) {
        setColor(color);
        return this;
    }

    /**
     * Returns the line cap style.
     *
     * @return Current value
     */
    public LineCap getLineCap() {
        return lineCap;
    }

    /**
     * Sets the line cap style.
     *
     * @param lineCap
     *         New value
     */
    public void setLineCap(LineCap lineCap) {
        this.lineCap = lineCap;
    }

    /**
     * Sets the line cap style.
     *
     * @param lineCap
     *         New value
     * @return This instance
     */
    public Stroke lineCap(LineCap lineCap) {
        setLineCap(lineCap);
        return this;
    }

    /**
     * Returns the line join style.
     *
     * @return Current value
     */
    public LineCap getLineJoin() {
        return lineJoin;
    }

    /**
     * Sets the line join style.
     *
     * @param lineJoin
     *         New value
     */
    public void setLineJoin(LineCap lineJoin) {
        this.lineJoin = lineJoin;
    }

    /**
     * Sets the line join style.
     *
     * @param lineJoin
     *         New value
     * @return This instance
     */
    public Stroke lineJoin(LineCap lineJoin) {
        setLineJoin(lineJoin);
        return this;
    }

    /**
     * Returns the line dash style.
     *
     * @return Current value
     */
    public Number[] getLineDash() {
        return lineDash;
    }

    /**
     * Sets the line dash style.
     *
     * @param lineDash
     *         New value
     */
    public void setLineDash(Number... lineDash) {
        this.lineDash = lineDash;
    }

    /**
     * Sets the line dash style.
     *
     * @param lineDash
     *         New value
     * @return This instance
     */
    public Stroke lineDash(Number... lineDash) {
        setLineDash(lineDash);
        return this;
    }

    /**
     * Returns the miter limit.
     *
     * @return Current value
     */
    public Number getMiterLimit() {
        return miterLimit;
    }

    /**
     * Sets the miter limit.
     *
     * @param miterLimit
     *         New value
     */
    public void setMiterLimit(Number miterLimit) {
        this.miterLimit = miterLimit;
    }

    /**
     * Sets the miter limit.
     *
     * @param miterLimit
     *         New value
     * @return This instance
     */
    public Stroke miterLimit(Number miterLimit) {
        setMiterLimit(miterLimit);
        return this;
    }

    /**
     * Returns the stroke width.
     *
     * @return Current value
     */
    public Number getWidth() {
        return width;
    }

    /**
     * Sets the stroke width.
     *
     * @param width
     *         New value
     */
    public void setWidth(Number width) {
        this.width = width;
    }

    /**
     * Sets the stroke width.
     *
     * @param width
     *         New value
     * @return This instance
     */
    public Stroke width(Number width) {
        setWidth(width);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.style.Stroke";
    }

    public String renderAttributesJs() {

        StringBuilder builder = new StringBuilder();

        if (getColor() != null) {
            builder.append("'color': " + getColor().renderJs() + ",");
        }

        if (getLineCap() != null) {
            builder.append("'lineCap': '" + getLineCap() + "',");
        }

        if (getLineJoin() != null) {
            builder.append("'lineJoin': '" + getLineJoin() + "',");
        }

        if (getLineDash() != null) {
            builder.append("'lineDash': [" + Joiner.join(",", (Object[]) lineDash) + "],");
        }

        if (getMiterLimit() != null) {
            builder.append("'miterLimit': " + getMiterLimit() + ",");
        }

        if (getWidth() != null) {
            builder.append("'width': " + getWidth() + ",");
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
