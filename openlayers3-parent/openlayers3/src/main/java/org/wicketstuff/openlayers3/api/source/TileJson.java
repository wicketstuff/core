package org.wicketstuff.openlayers3.api.source;

import org.wicketstuff.openlayers3.api.util.CorsPolicy;

/**
 * Provides an object that models a TileJSON source of map data.
 */
public class TileJson extends Source {

    /**
     * URL providing the tiles for this source.
     */
    private String url;

    /**
     * CORS (Cross Origin Resource Sharing) policy for this source.
     */
    private CorsPolicy crossOrigin;

    /**
     * Creates a new instance.
     *
     * @param url
     *         URL providing data for this source
     */
    public TileJson(String url) {
        this(url, CorsPolicy.ANONYMOUS);
    }

    /**
     * Creates a new instance.
     *
     * @param url
     *         URL providing data for this source
     * @param crossOrigin
     *         CORS (Cross Origin Resource Sharing) policy for this source
     */
    public TileJson(String url, CorsPolicy crossOrigin) {
        super();

        this.url = url;
        this.crossOrigin = crossOrigin;
    }

    /**
     * Returns the URL with the source of tiles.
     *
     * @return URL with the tile source
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL for the source of tiles.
     *
     * @param url
     *         New value
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Sets the URL for the source of tiles.
     *
     * @param url
     *         New value
     * @return This instance
     */
    public TileJson url(String url) {
        setUrl(url);
        return this;
    }

    /**
     * Returns the CORS (Cross Origin Resource Sharing) policy for this source.
     *
     * @return CORS policy
     */
    public CorsPolicy getCrossOrigin() {
        return crossOrigin;
    }

    /**
     * Sets the CORS (Cross Origin Resource Sharing) policy for this source.
     *
     * @param crossOrigin
     *         New value
     */
    public void setCrossOrigin(CorsPolicy crossOrigin) {
        this.crossOrigin = crossOrigin;
    }

    /**
     * Sets the CORS (Cross Origin Resource Sharing) policy for this source.
     *
     * @param crossOrigin
     *         New value
     * @return This instance
     */
    public TileJson crossOrigin(CorsPolicy crossOrigin) {
        setCrossOrigin(crossOrigin);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.source.TileJSON";
    }

    @Override
    public String renderJs() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");

        if (url != null) {
            builder.append("'url': '" + url + "',");
        }

        if (crossOrigin != null) {
            builder.append("'crossOrigin': '" + crossOrigin + "',");
        }

        builder.append("}");
        return builder.toString();
    }
}
