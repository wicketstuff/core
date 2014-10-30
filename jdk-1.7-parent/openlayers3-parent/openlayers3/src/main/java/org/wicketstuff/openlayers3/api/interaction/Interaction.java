package org.wicketstuff.openlayers3.api.interaction;

import org.wicketstuff.openlayers3.api.JavascriptObject;

import java.io.Serializable;

/**
 * Provides an object that models the abstract map interaction.
 */
public abstract class Interaction extends JavascriptObject implements Serializable {

    /**
     * Creates a new instance.
     */
    public Interaction() {
        super();
    }

    @Override
    public String getJsType() {
        return "ol.interaction.Interaction";
    }

    @Override
    public String renderJs() {
        return "";
    }
}
