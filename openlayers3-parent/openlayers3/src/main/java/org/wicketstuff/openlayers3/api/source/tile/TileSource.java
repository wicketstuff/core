package org.wicketstuff.openlayers3.api.source.tile;

import org.wicketstuff.openlayers3.api.JavascriptObject;

import java.io.Serializable;

/**
 * Provides an object that models a Tile source.
 */
public class TileSource extends JavascriptObject implements Serializable {

    @Override
    public String getJsType() {
        return "ol.source.Tile";
    }

    @Override
    public String renderJs() {
        StringBuilder builder = new StringBuilder();
        builder.append("{ }");
        return builder.toString();
    }
}
