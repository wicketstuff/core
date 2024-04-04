package org.wicketstuff.openlayers3.api.source.tile;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

/**
 * Provides an object that models an ESRI ArcGIS tile end-point.
 * <p/>
 * Example: <code>new TileArcGISRest().setUrl("http://www.orthos.dhses.ny.gov/arcgis/rest/services/Latest/MapServer")</code>
 * <p/>
 * @see <a href="http://openlayers.org/en/latest/apidoc/ol.source.TileArcGISRest.html">http://openlayers.org/en/latest/apidoc/ol.source.TileArcGISRest.html</a>
 */
public class TileArcGISRest extends TileSource {

    private String url;

    @Override
    public String getJsType() {
        return "ol.source.TileArcGISRest";
    }

    @Override
    public String renderJs() {
        List<String> list = new ArrayList<>();
        list.add("'url': '" + getUrl() + "'");
        return "{" + Joiner.on(", ").join(list) + "}";
    }

    public String getUrl() {
        return url;
    }

    public TileArcGISRest setUrl(String url) {
        this.url = url;
        return this;
    }

}
