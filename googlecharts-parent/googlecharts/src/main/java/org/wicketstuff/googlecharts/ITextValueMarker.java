package org.wicketstuff.googlecharts;

import java.awt.*;
import java.io.Serializable;

public interface ITextValueMarker extends Serializable {

    public TextValueMarkerType getType();

    public Color getColor();

    public int getIndex();

    public double getPoint();

    public int getSize();

    public String getText();
}
