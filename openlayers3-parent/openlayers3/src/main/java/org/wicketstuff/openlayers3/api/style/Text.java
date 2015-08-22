package org.wicketstuff.openlayers3.api.style;

import org.wicketstuff.openlayers3.api.JavascriptObject;

import java.io.Serializable;

/**
 * Provides an object that models a text style.
 */
public class Text extends JavascriptObject implements Serializable {

    public String font;

    public Number offsetX;

    public Number offsetY;

    public Number scale;

    public Number rotation;

    public String text;

    public String textAlign;

    public String textBaseLine;

    public Fill fill;

    public Stroke stroke;

    public Text(String text, Fill fill) {
        this(null, 0, 0, null, null, text, null, null, fill, null);
    }

    public Text(String font, Number offsetX, Number offsetY, Number scale, Number rotation, String text,
                String textAlign, String textBaseLine, Fill fill, Stroke stroke) {
        super();

        this.font = font;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.textAlign = textAlign;
        this.textBaseLine = textBaseLine;
        this.fill = fill;
        this.scale = scale;
        this.rotation = rotation;
        this.text = text;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public Number getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(Number offsetX) {
        this.offsetX = offsetX;
    }

    public Number getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(Number offsetY) {
        this.offsetY = offsetY;
    }

    public Number getScale() {
        return scale;
    }

    public void setScale(Number scale) {
        this.scale = scale;
    }

    public Number getRotation() {
        return rotation;
    }

    public void setRotation(Number rotation) {
        this.rotation = rotation;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    public String getTextBaseLine() {
        return textBaseLine;
    }

    public void setTextBaseLine(String textBaseLine) {
        this.textBaseLine = textBaseLine;
    }

    public Fill getFill() {
        return fill;
    }

    public void setFill(Fill fill) {
        this.fill = fill;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    @Override
    public String getJsType() {
        return "ol.style.Text";
    }

    public String renderAttributesJs() {

        StringBuilder builder = new StringBuilder();

        if (getFont() != null) {
            builder.append("'font': '" + getFont() + "',");
        }

        if (getOffsetX() != null) {
            builder.append("'offsetX': " + getOffsetX() + ",");
        }

        if (getOffsetY() != null) {
            builder.append("'offsetY': " + getOffsetY() + ",");
        }

        if (getScale() != null) {
            builder.append("'scale': " + getScale() + ",");
        }
        if (getRotation() != null) {
            builder.append("'rotation': " + getRotation() + ",");
        }

        if (getText() != null) {
            builder.append("'text': '" + escapeQuoteJs(getText()) + "',");
        }

        if (getTextBaseLine() != null) {
            builder.append("'textBaseline': '" + getTextBaseLine() + "',");
        }

        if (getTextAlign() != null) {
            builder.append("'textAlign': '" + getTextAlign() + "',");
        }

        if (getFill() != null) {
            builder.append("'fill': new " + getFill().getJsType());
            builder.append("(" + getFill().renderJs() + "),");
        }

        if (getStroke() != null) {
            builder.append("'stroke': new " + getStroke().getJsType());
            builder.append("(" + getStroke().renderJs() + "),");
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
