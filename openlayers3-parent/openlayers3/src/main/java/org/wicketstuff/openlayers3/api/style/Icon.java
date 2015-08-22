package org.wicketstuff.openlayers3.api.style;

import org.wicketstuff.openlayers3.api.coordinate.Coordinate;
import org.wicketstuff.openlayers3.api.util.CorsPolicy;

/**
 * Provides a class that models an icon style for vector features.
 */
public class Icon extends Image {

    /**
     * Default anchor coordinate for new instances (center the icon relative to the feature).
     */
    public final static Coordinate DEFAULT_COORDINATE = new Coordinate(0.5, 0.5);
    /**
     * Coordinate for the anchor.
     */
    private Coordinate anchor;
    /**
     * Position of the anchor relative to the feature.
     */
    private Origin anchorOrigin;
    /**
     * Unit for calculating the anchor's "X" position.
     */
    private Unit anchorXUnits;
    /**
     * Unit for calculating the anchor's "Y" position.
     */
    private Unit anchorYUnits;
    /**
     * The opacity of the icon.
     */
    private Number opacity;
    /**
     * A URL with the source image for the icon.
     */
    private String src;
    /**
     * Keyword denoting the CORS (Cross Origin Resource Sharing) policy for this icon.
     */
    private CorsPolicy crossOrigin; // cross origin keyword

    /**
     * Creates a new instance.
     *
     * @param src
     *         String with the URL for the image for this icon
     */
    public Icon(String src) {
        this(DEFAULT_COORDINATE, src);
    }

    /**
     * Creates a new instance.
     *
     * @param anchor
     *         Anchor position for this icon
     * @param src
     *         String with the URL for the image for this icon
     */
    public Icon(final Coordinate anchor, String src) {
        this(anchor, Origin.TOP_LEFT, Unit.FRACTION, Unit.FRACTION, 1, src, null);
    }

    /**
     * Creates a new instance.
     *
     * @param anchor
     *         Anchor position for this icon
     * @param anchorOrigin
     *         Position of the anchor relative to the feature
     * @param anchorXUnits
     *         Unit for calculating the anchor's "X" position
     * @param anchorYUnits
     *         Unit for calculating the anchor's "Y" position
     * @param opacity
     *         Opacity of the icon
     * @param src
     *         String with the URL for the image for this icon
     * @param crossOrigin
     *         CORS policy
     */
    public Icon(final Coordinate anchor, final Origin anchorOrigin, final Unit anchorXUnits, final Unit anchorYUnits,
                final Number opacity, final String src, final CorsPolicy crossOrigin) {
        super();

        this.anchor = anchor;
        this.anchorOrigin = anchorOrigin;
        this.anchorXUnits = anchorXUnits;
        this.anchorYUnits = anchorYUnits;
        this.opacity = opacity;
        this.src = src;
        this.crossOrigin = crossOrigin;
    }

    /**
     * Returns the anchor's coordinate.
     *
     * @return Coordinate
     */
    public Coordinate getAnchor() {
        return anchor;
    }

    /**
     * Sets the anchor's coordinate.
     *
     * @param anchor
     *         New value
     */
    public void setAnchor(Coordinate anchor) {
        this.anchor = anchor;
    }

    /**
     * Sets the anchor's coordinate.
     *
     * @param anchor
     *         New value
     * @return This instance
     */
    public Icon anchor(Coordinate anchor) {
        setAnchor(anchor);
        return this;
    }

    /**
     * Sets the position of the anchor relative to the feature.
     *
     * @return Anchor origin
     */
    public Origin getAnchorOrigin() {
        return anchorOrigin;
    }

    /**
     * Sets the position of the anchor relative to the feature.
     *
     * @param anchorOrigin
     *         New value
     */
    public void setAnchorOrigin(Origin anchorOrigin) {
        this.anchorOrigin = anchorOrigin;
    }

    /**
     * Sets the position of the anchor relative to the feature.
     *
     * @param anchorOrigin
     *         New value
     * @return This instancep
     */
    public Icon anchorOrigin(Origin anchorOrigin) {
        setAnchorOrigin(anchorOrigin);
        return this;
    }

    /**
     * Returns the unit for calculating the anchor's "X" position.
     *
     * @return Anchor "X" unit
     */
    public Unit getAnchorXUnits() {
        return anchorXUnits;
    }

    /**
     * Sets the unit for calculating the anchor's "X" position.
     *
     * @param anchorXUnits
     */
    public void setAnchorXUnits(Unit anchorXUnits) {
        this.anchorXUnits = anchorXUnits;
    }

    /**
     * Sets the unit for calculating the anchor's "X" position.
     *
     * @param anchorXUnits
     *         New value
     * @return This instance
     */
    public Icon anchorXUnits(Unit anchorXUnits) {
        setAnchorXUnits(anchorXUnits);
        return this;
    }

    /**
     * Returns the unit for calculating the anchor's "Y" position.
     *
     * @return Unit for calculating the anchor's "Y" position
     */
    public Unit getAnchorYUnits() {
        return anchorYUnits;
    }

    /**
     * Sets the unit for calculating the anchor's "Y" position.
     *
     * @param anchorYUnits
     *         New value
     */
    public void setAnchorYUnits(Unit anchorYUnits) {
        this.anchorYUnits = anchorYUnits;
    }

    /**
     * Sets the unit for calculating the anchor's "Y" position.
     *
     * @param anchorYUnits
     *         New value
     * @return This instance
     */
    public Icon anchorYUnits(Unit anchorYUnits) {
        setAnchorYUnits(anchorYUnits);
        return this;
    }

    /**
     * Returns the opacity of the icon.
     *
     * @return Opacity of the icon
     */
    public Number getOpacity() {
        return opacity;
    }

    /**
     * Sets the opacity of the icon.
     *
     * @param opacity
     *         New value
     */
    public void setOpacity(Number opacity) {
        this.opacity = opacity;
    }

    /**
     * Sets the opacity of the icon.
     *
     * @param opacity
     *         New value
     * @return This instance
     */
    public Icon opacity(Number opacity) {
        setOpacity(opacity);
        return this;
    }

    /**
     * Returns the URL is the image for the icon.
     *
     * @return String with the URL for the image
     */
    public String getSrc() {
        return src;
    }

    /**
     * Sets the URL with the image for the icon.
     *
     * @param src
     *         New value
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * Sets the URL with the image for the icon.
     *
     * @param src
     *         New value
     * @return This instance
     */
    public Icon src(String src) {
        setSrc(src);
        return this;
    }

    /**
     * Returns the CORS (Cross Origin Resource Sharing) policy for this icon.
     *
     * @return CORS policy
     */
    public CorsPolicy getCrossOrigin() {
        return crossOrigin;
    }

    /**
     * Sets the CORS (Cross Origin Resource Sharing) policy for this icon.
     *
     * @param crossOrigin
     *         CORS policy
     */
    public void setCrossOrigin(CorsPolicy crossOrigin) {
        this.crossOrigin = crossOrigin;
    }

    /**
     * Sets the CORS (Cross Origin Resource Sharing) policy for this icon.
     *
     * @param crossOrigin
     *         CORS policy
     * @return This instance
     */
    public Icon crossOrigin(CorsPolicy crossOrigin) {
        setCrossOrigin(crossOrigin);
        return this;
    }

    @Override
    public Icon rotation(Number number) {
        super.rotation(number);
        return this;
    }

    @Override
    public Icon scale(Number scale) {
        super.scale(scale);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.style.Icon";
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append(super.renderAttributesJs());

        if (getAnchor() != null) {
            builder.append("'anchor': " + getAnchor().renderJs() + ",");
        }

        if (getAnchorOrigin() != null) {
            builder.append("'anchorOrigin': '" + getAnchorOrigin() + "',");
        }

        if (getAnchorXUnits() != null) {
            builder.append("'anchorXUnits': '" + getAnchorXUnits() + "',");
        }

        if (getAnchorYUnits() != null) {
            builder.append("'anchorYUnits': '" + getAnchorYUnits() + "',");
        }

        if (getOpacity() != null) {
            builder.append("'opacity': " + getOpacity() + ",");
        }

        if (getSrc() != null) {
            builder.append("'src': '" + getSrc() + "',");
        }

        if (getCrossOrigin() != null) {
            builder.append("'cross-origin': '" + getCrossOrigin() + "',");
        }

        builder.append("}");
        return builder.toString();
    }

    /**
     * Enumeration of value anchor origin values.
     */
    public enum Origin {
        BOTTOM_LEFT("bottom-left"), BOTTOM_RIGHT("bottom-right"), TOP_LEFT("top-left"), TOP_RIGHT("top-right");

        String value;

        Origin(String value) {
            this.value = value;
        }


        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * Enumeration of valid units by which the anchor's "X" or "Y" value is specified.
     */
    public enum Unit {
        FRACTION("fraction"), PIXEL("pixels");

        String value;

        Unit(String value) {
            this.value = value;
        }


        @Override
        public String toString() {
            return value;
        }
    }
}
