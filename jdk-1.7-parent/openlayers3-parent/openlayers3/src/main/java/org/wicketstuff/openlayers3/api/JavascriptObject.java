package org.wicketstuff.openlayers3.api;

import java.util.HashMap;

/**
 * Provides an interface that all Javascript objects must implement in order to be rendered properly.
 */
public abstract class JavascriptObject {

    /**
     * Counter for generating instance identifiers.
     */
    public static long counter = 0;

    /**
     * Map for starting object Ids.
     */
    public static java.util.Map<Object, String> objectIds = new HashMap<Object, String>();

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
    public String getJsId() {

        String objectId = null;

        if (objectIds.get(this) != null) {
            objectId = objectIds.get(this);
        } else {
            objectId = getClass().getSimpleName().toLowerCase() + counter++;
            objectIds.put(this, objectId);
        }

        return objectId;
    }

    /**
     * Returns a String with containing the rendered Javascript code for this object.
     *
     * @return String with rendered Javascript code
     */
    public abstract String renderJs();

    /**
     * Escapes single quotation marks in the provided String. When outputting Javascript code, we're using single
     * quotation marks to surround the content.
     *
     * @param text Text to escape
     * @return String with escaped text
     */
    protected String escapeQuoteJs(String text) {
        return text.replaceAll("\'", "&apos;");
    }
}
