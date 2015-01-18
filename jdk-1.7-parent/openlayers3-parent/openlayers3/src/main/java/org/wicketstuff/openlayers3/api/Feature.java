package org.wicketstuff.openlayers3.api;

import org.wicketstuff.openlayers3.api.geometry.Point;
import org.wicketstuff.openlayers3.api.style.Style;
import org.apache.wicket.util.string.Strings;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides an vector object for geographic features with geometry and other attribute properties.
 */
public class Feature extends JavascriptObject implements Serializable {

    /**
     * The geometry for this feature.
     */
    private Point geometry;

    /**
     * The name of this feature.
     */
    private String name;

    /**
     * The style used for rendering this feature on the map.
     */
    private Style style;

    /**
     * String attributes for this feature.
     */
    private Map<String, String> stringValues;

    /**
     * Numeric attributes for this feature.
     */
    private Map<String, Number> numberValues;

    /**
     * Creates a new feature.
     *
     * @param geometry
     *         The geometry of the feature
     * @param name
     *         The name for this feature
     */
    public Feature(final Point geometry, final String name) {
        this(geometry, name, null);
    }

    /**
     * Creates a new feature.
     *
     * @param geometry
     *         The geometry of the feature
     * @param name
     *         The name for this feature
     * @param style
     *         The style used to render this feature
     */
    public Feature(final Point geometry, final String name, final Style style) {
        super();
        this.geometry = geometry;
        this.name = name;
        this.style = style;
        stringValues = new HashMap<String, String>();
        numberValues = new HashMap<String, Number>();
    }

    /**
     * Returns the geometry of this feature.
     *
     * @return Geometry of the feature.
     */
    public Point getGeometry() {
        return geometry;
    }

    /**
     * Sets the geometry of the feature.
     *
     * @param geometry
     *         New value
     */
    public void setGeometry(Point geometry) {
        this.geometry = geometry;
    }

    /**
     * Sets the geometry of the feature.
     *
     * @param geometry
     *         New value
     * @return This instance
     */
    public Feature geometry(Point geometry) {
        setGeometry(geometry);
        return this;
    }

    /**
     * Returns the name of the feature.
     *
     * @return Name of this feature
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this feature.
     *
     * @param name
     *         New value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the name of this feature.
     *
     * @param name
     *         New value
     * @return This instance
     */
    public Feature name(String name) {
        setName(name);
        return this;
    }

    /**
     * Returns the style used to render this feature.
     *
     * @return Style used to render this feature
     */
    public Style getStyle() {
        return style;
    }

    /**
     * Sets the style used to render this feature.
     *
     * @param style
     *         New value
     */
    public void setStyle(Style style) {
        this.style = style;
    }

    /**
     * Sets the style used to render this feature.
     *
     * @param style
     *         New value
     * @return This instance
     */
    public Feature style(Style style) {
        setStyle(style);
        return this;
    }

    /**
     * Adds an attribute to this feature.
     *
     * @param key
     *         Name of the attribute
     * @param value
     *         String with the value of the attribute
     * @return This instance
     */
    public Feature putString(String key, String value) {
        stringValues.put(key, value);
        return this;
    }

    /**
     * Returns the value of this feature's attribute or null if it is not present.
     *
     * @param key
     *         Name of the attribute
     * @return String with the attribute's value
     */
    public String getString(String key) {
        return stringValues.get(key);
    }

    /**
     * Adds an attribute to this feature.
     *
     * @param key
     *         Name of the attribute
     * @param value
     *         Number with the value of the attribute
     * @return This instance
     */
    public Feature putNumber(String key, Number value) {
        numberValues.put(key, value);
        return this;
    }

    /**
     * Returns the value of this feature's attribute or null if it is not present.
     *
     * @param key
     *         Name of the attribute
     * @return Numer with the value of the attribute
     */
    public Number getNumber(String key) {
        return numberValues.get(key);
    }

    @Override
    public String getJsType() {
        return "ol.Feature";
    }

    public String renderAfterConstructorJs() {
        StringBuilder builder = new StringBuilder();

        if (getStyle() != null) {
            builder.append(getJsId());
            builder.append(".setStyle(new ");
            builder.append(getStyle().getJsType());
            builder.append("(" + getStyle().renderJs() + "));");
        }

        return builder.toString();
    }

    @Override
    public String renderJs() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");

        builder.append("'id': \"" + getJsId() + "\",");

        if (getGeometry() != null) {
            builder.append("'geometry': new ");
            builder.append(geometry.getJsType());
            builder.append("(" + getGeometry().renderJs() + "),");
        }

        if (getName() != null) {
            builder.append("'name': '" + Strings.escapeMarkup(getName()).toString() + "',");
        }

        for (Map.Entry<String, String> entry : stringValues.entrySet()) {
            builder.append("'" + entry.getKey() + "': '" + Strings.escapeMarkup(entry.getValue()).toString() + "',");
        }

        for (Map.Entry<String, Number> entry : numberValues.entrySet()) {
            builder.append("'" + entry.getKey() + "': " + entry.getValue() + ",");
        }

        builder.append("}");
        return builder.toString();
    }
}
