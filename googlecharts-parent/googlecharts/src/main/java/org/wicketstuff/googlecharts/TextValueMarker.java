package org.wicketstuff.googlecharts;

import java.awt.*;

public class TextValueMarker implements ITextValueMarker {
    private static final long serialVersionUID = -7521556909378737717L;
    private TextValueMarkerType type;
    private String text;
    private int index = -1;
    private Color color;
    private double point = -1;
    private int size = -1;

    public TextValueMarker(TextValueMarkerType type, String text, int index, Color color, double point, int size) {
        this.type = type;
        this.text = text;
        this.index = index;
        this.color = color;
        this.point = point;
        this.size = size;
    }

    public TextValueMarkerType getType() {
        return type;
    }

    public void setType(TextValueMarkerType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}