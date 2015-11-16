package org.wicketstuff.openlayers3.api.source.tile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.wicketstuff.openlayers3.api.proj.Projection;

import java.util.Map;

/**
 * Provides an object that models a Web Map Service (WMS) data source that provides images divided into a tile grid.
 */
public class TileWms extends TileSource {

    /**
     * The URL providing the tiles for this map data source.
     */
    private String url;
    /**
     * Parameters for this tile map data source.
     */
    private Map<String, String> params;
    /**
     * The type of remote WMS server.
     */
    private ServerType serverType;
    /**
     * Projection used to transform the fetched data.
     */
    private Projection projection;

    /**
     * Creates a new instance.
     *
     * @param url
     *         The URL providing the tile images for this map data source
     * @param params
     *         Parameters for this tile map data source
     */
    public TileWms(String url, Map<String, String> params) {
        super();

        this.url = url;
        this.params = params;
    }

    /**
     * Returns the URL providing tiles for this source.
     *
     * @return URL providing tiles
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL providing images for this source.
     *
     * @param url
     *         New value
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns the parameters for this source.
     *
     * @return Map of parameters
     */
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * Sets the parameters for this source.
     *
     * @param params
     *         New value
     */
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    /**
     * Sets the URL providing the images for this source.
     *
     * @param url
     *         New value
     * @return This instance
     */
    public TileWms url(String url) {
        setUrl(url);
        return this;
    }

    /**
     * Sets the parameters for this source.
     *
     * @param params
     *         New value
     * @return This instance
     */
    public TileWms params(Map<String, String> params) {
        setParams(params);
        return this;
    }

    /**
     * Sets the remote server type.
     *
     * @param serverType
     *         New value
     * @return This instance
     */
    public TileWms serverType(ServerType serverType) {
        this.serverType = serverType;
        return this;
    }

    /**
     * Sets the projection used to transform fetched data.
     *
     * @param projection
     *         New value
     * @return This instance
     */
    public TileWms projection(Projection projection) {
        this.projection = projection;
        return this;
    }

    /**
     * Returns the remote server type
     *
     * @return Remote server type
     */
    public ServerType getServerType() {
        return serverType;
    }

    /**
     * Sets the remote server type.
     *
     * @param serverType
     *         New value
     */
    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }

    /**
     * Returns the projection used to transform fetched data.
     *
     * @return Projection used to transform fetched data
     */
    public Projection getProjection() {
        return projection;
    }

    /**
     * Sets the projection used to transform fetched data.
     *
     * @param projection
     *         New value
     */
    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    @Override
    public String getJsType() {
        return "ol.source.TileWMS";
    }

    @Override
    public String renderJs() {

        Gson gson = new GsonBuilder().create();
        StringBuilder builder = new StringBuilder();

        builder.append("{");
        builder.append("'url': '" + getUrl() + "',");

        if (serverType != null) {
            builder.append("'serverType': '" + serverType + "',");
        }

        if (getProjection() != null) {
            builder.append("'projection': new " + getProjection().getJsType() + "("
                    + getProjection().renderJs() + "),");
        }

        builder.append("'params': " + gson.toJson(getParams()) + ",");
        builder.append("}");

        return builder.toString();
    }

    public enum ServerType {
        CARMENTASERVER("carmentaserver"), GEOSERVER("geoserver"), MAPSERVER("mapserver"), QGIS("qgis");

        String value;

        ServerType(String value) {
            this.value = value;
        }


        @Override
        public String toString() {
            return value;
        }
    }
}
