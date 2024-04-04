package org.wicketstuff.openlayers3.api.source.tile;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

/**
 * Provides an object that models an XYZ data source where the requested tiles are part of the URL.
 * The placeholders {x}, {y} and {z} are used to indicate how the URL is to be constructed.
 * <p/>
 * Example: <code>new XYZ().setUrl("http://tiles.arcgis.com/tiles/hGdibHYSPO59RG1h/arcgis/rest/services/USGS_Orthos_2013_2014/MapServer/tile/{z}/{y}/{x}")</code>
 * <p/>
 * @see <a href="http://openlayers.org/en/latest/apidoc/ol.source.XYZ.html">http://openlayers.org/en/latest/apidoc/ol.source.XYZ.html</a>
 */
public class XYZ extends TileSource {

    private String url;

    public String getUrl() {
        return url;
    }

    public XYZ setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.source.XYZ";
    }

    @Override
    public String renderJs() {
        List<String> list = new ArrayList<>();
        list.add("'url': '" + getUrl() + "'");
        return "{" + Joiner.on(", ").join(list) + "}";
    }

}
