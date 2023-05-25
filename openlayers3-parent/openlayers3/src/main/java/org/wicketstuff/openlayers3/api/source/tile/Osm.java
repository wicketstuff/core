package org.wicketstuff.openlayers3.api.source.tile;

/**
 * Provides an object that models an OpenStreetMap source of map data.
 */
public class Osm extends TileSource {

    /**
     * Creates a new instance.
     */
    public Osm() {
        super();
    }

    @Override
    public String getJsType() {
        return "ol.source.OSM";
    }

    @Override
    public String renderJs() {
        return "";
    }
}
