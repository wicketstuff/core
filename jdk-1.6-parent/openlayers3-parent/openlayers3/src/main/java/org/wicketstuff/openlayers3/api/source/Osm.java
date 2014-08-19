package org.wicketstuff.openlayers3.api.source;

/**
 * Provides an object that models an OpenStreetMap source of map data.
 */
public class Osm extends Source {

    @Override
    public String getJsType() {
        return "ol.source.OSM";
    }

    @Override
    public String renderJs() {
        return "";
    }
}
