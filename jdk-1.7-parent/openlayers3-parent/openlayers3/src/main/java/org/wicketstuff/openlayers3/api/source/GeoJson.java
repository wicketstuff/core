package org.wicketstuff.openlayers3.api.source;

import org.wicketstuff.openlayers3.api.proj.Projection;

/**
 * Provides an object that models a GeoJSON vector data source.
 */
public class GeoJson extends StaticVector {

    /**
     * URL from which the GeoJSON formatted data will be fetched.
     */
    private String url;

    /**
     * Creates a new instance.
     *
     * @param url
     *         URL from which the GeoJSON formatted data will be fetched
     * @param projection
     *         Projection for the fetched data
     */
    public GeoJson(final String url, final Projection projection) {
        super(projection);
        this.url = url;
    }

    /**
     * Returns the URL from which the data will be fetched.
     *
     * @return String with the URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL from which the data will be fetched.
     *
     * @param url
     *         New value
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Sets the URL from which the data will be fetched.
     *
     * @param url
     *         New value
     * @return This instance
     */
    public GeoJson url(String url) {
        setUrl(url);
        return this;
    }

    @Override
    public String getJsType() {
        return "ol.source.GeoJSON";
    }

    @Override
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
        builder.append("{");

        if (url != null) {
            builder.append("'url': '" + url + "',");
        }

        if (getProjection() != null) {
            builder.append("'projection': new " + getProjection().getJsType() + "("
                    + getProjection().renderJs() + "),");
        }

        builder.append("}");
        return builder.toString();
    }
}
