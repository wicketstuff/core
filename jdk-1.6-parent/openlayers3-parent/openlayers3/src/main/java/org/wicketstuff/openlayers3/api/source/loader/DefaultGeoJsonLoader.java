package org.wicketstuff.openlayers3.api.source.loader;

import org.wicketstuff.openlayers3.api.source.Source;

import java.io.Serializable;

/**
 * Provides an object that models a vector source loading strategy for a GeoJSON data source.
 */
public class DefaultGeoJsonLoader extends Loader implements Serializable {

    /**
     * The URL from which new data is fetched.
     */
    private String url;

    /**
     * The projection used to transform the fetched data.
     */
    private String projection;

    /**
     * Handler to notify when the data is loaded.
     */
    private VectorFeatureDataLoadedListener vectorFeatureDataLoadedListener;

    /**
     * Creates a new instance.
     *
     * @param url
     *         The URL from which new data is featched
     * @param projection
     *         The projection used to transform the fetched data
     */
    public DefaultGeoJsonLoader(final String url, final String projection) {
        this.url = url;
        this.projection = projection;
    }

    /**
     * Returns the URL from which data is loaded.
     *
     * @return String with the URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL from which data is loaded.
     *
     * @param url
     *         New value
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Sets the URL from which data is loaded.
     *
     * @param url
     *         New value
     * @return This instance
     */
    public DefaultGeoJsonLoader url(String url) {
        setUrl(url);
        return this;
    }

    /**
     * Returns the projection used to transform fetched data.
     *
     * @return Projection used to transform fetched data
     */
    public String getProjection() {
        return projection;
    }

    /**
     * Sets the projection used to transform fetched data.
     *
     * @param projection
     *         New value
     */
    public void setProjection(String projection) {
        this.projection = projection;
    }

    /**
     * Sets the projection used to transform fetched data.
     *
     * @param projection
     *         New value
     * @return This instance
     */
    public DefaultGeoJsonLoader projection(String projection) {
        setProjection(projection);
        return this;
    }

    /**
     * Returns the listener invoked when feature data has been loaded.
     *
     * @return Feature data loaded listener
     */
    public VectorFeatureDataLoadedListener getVectorFeatureDataLoadedListener() {
        return vectorFeatureDataLoadedListener;
    }

    /**
     * Sets a listener that will be invoked when feature data has been loaded.
     *
     * @param vectorFeatureDataLoadedListener
     *         Listener to invoke when feature data has been loaded
     */
    public void setVectorFeatureDataLoadedListener(VectorFeatureDataLoadedListener vectorFeatureDataLoadedListener) {
        this.vectorFeatureDataLoadedListener = vectorFeatureDataLoadedListener;
    }

    /**
     * Sets a listener that will be invoked when feature data has been loaded.
     *
     * @param vectorFeatureDataLoadedListener
     *         Listener to invoke when feature data has been loaded
     * @return this instance
     */
    public DefaultGeoJsonLoader dataLoaded(VectorFeatureDataLoadedListener vectorFeatureDataLoadedListener) {
        setVectorFeatureDataLoadedListener(vectorFeatureDataLoadedListener);
        return this;
    }

    @Override
    public DefaultGeoJsonLoader source(Source source) {
        setSource(source);
        return this;
    }

    /**
     * Renders the callback function that handles the asynchronously requested data and loads it into our data source.
     *
     * @return String with the rendered Javascript
     */
    public String renderBeforeConstructorJs() {
        StringBuilder builder = new StringBuilder();
        builder.append(getJsId() + "_loadFeatures = function(response) {");
        builder.append("  " + getSource().getJsId() + ".addFeatures(" + getSource().getJsId()
                + ".readFeatures(response));");
        builder.append(vectorFeatureDataLoadedListener.getCallbackFunctionName() + "(" + getSource().getJsId() + ")");
        builder.append("};");
        return builder.toString();
    }

    @Override
    public String getJsType() {
        return "";
    }

    @Override
    public String renderJs() {
        StringBuilder builder = new StringBuilder();

        builder.append("function(extent, resolution, projection) {\n");
        builder.append("var url = '" + url + "&outputFormat=text/javascript");
        builder.append("&format_options=callback:" + getJsId() + "_loadFeatures&srsname=" + projection);
        builder.append("&bbox=' + extent.join(',') + '," + projection + "';\n");
        builder.append("$.ajax({ 'url': url, 'dataType': 'jsonp', 'jsonp': false, 'type': 'GET', 'async': false,});");
        builder.append("}\n");

        return builder.toString();
    }
}
