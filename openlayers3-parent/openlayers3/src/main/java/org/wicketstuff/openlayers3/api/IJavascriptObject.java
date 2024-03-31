package org.wicketstuff.openlayers3.api;

/**
 * Provides an interface for all objects that render to Javascript.
 */
public interface IJavascriptObject {

    /**
     * Returns a String with the type of Javascript object that this object represents. This will be used when creating
     * new instances.
     *
     * @return String with the Javascript object type
     */
    public abstract String getJsType();

    /**
     * Returns a String with the unique ID used to identify this object.
     *
     * @return String with the object's unique ID
     */
    public abstract String getJsId();

    /**
     * Returns a String with containing the rendered Javascript code for this object.
     *
     * @return String with rendered Javascript code
     */
    public abstract String renderJs();
}
