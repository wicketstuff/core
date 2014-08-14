package org.wicketstuff.openlayers3;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.openlayers3.api.Feature;
import org.wicketstuff.openlayers3.api.Map;
import org.wicketstuff.openlayers3.api.layer.Layer;
import org.wicketstuff.openlayers3.api.layer.Vector;
import org.wicketstuff.openlayers3.api.source.ServerVector;
import org.wicketstuff.openlayers3.api.source.VectorSource;
import org.wicketstuff.openlayers3.api.source.loader.DefaultGeoJsonLoader;
import org.wicketstuff.openlayers3.api.source.loader.VectorFeatureDataLoadedListener;

import java.util.HashMap;

/**
 * Provides the base class for all panels containing an OpenLayers map.
 */
public abstract class OpenLayersMap extends Panel {

    private final static Logger logger = LoggerFactory.getLogger(OpenLayersMap.class);

    /**
     * Map of layers to the data loaded handler that notifies their listeners.
     */
    private java.util.Map<Layer, VectorFeatureDataLoadedListener> layerDataLoadedMap =
            new HashMap<Layer, VectorFeatureDataLoadedListener>();

    /**
     * Creates a new instance
     *
     * @param id
     *         Wicket element ID
     * @param model
     *         Backing modep for this map
     */
    public OpenLayersMap(final String id, final IModel<Map> model) {
        super(id, model);
    }

    /**
     * Returns the backing model for this map.
     *
     * @return Model wrapping a Map instance
     */
    public IModel<Map> getModel() {
        return (IModel<Map>) getDefaultModel();
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        getModel().getObject().setTarget(getMarkupId());

        if (getModel().getObject().getLayers() != null) {

            for (Layer layer : getModel().getObject().getLayers()) {

                if (layer instanceof Vector) {

                    final Vector vectorLayer = (Vector) layer;

                    // add a behavior to be notified when new data is loaded
                    VectorFeatureDataLoadedListener vectorFeatureDataLoadedListener = new VectorFeatureDataLoadedListener() {

                        @Override
                        public void handleDataLoaded(AjaxRequestTarget target) {
                            vectorLayer.notifyFeaturesLoadedListeners(target);
                        }
                    };
                    add(vectorFeatureDataLoadedListener);

                    // map the layer to the data loaded handler
                    layerDataLoadedMap.put(layer, vectorFeatureDataLoadedListener);
                }
            }
        }
    }

    /**
     * Zooms the map to an extent that includes all of the features on the provided vector layer.
     *
     * @param target
     *         Ajax request target
     * @param vector
     *         Vector layer containing features
     */
    public void zoomToFeatureExtent(AjaxRequestTarget target, Vector vector) {
        this.zoomToFeatureExtent(target, vector, 0);
    }

    /**
     * Zooms the map to an extent that includes all of the features on the provided vector layer.
     *
     * @param target
     *         Ajax request target
     * @param vector
     *         Vector layer containing features
     * @param buffer
     *         Buffer around the calculated extent
     */
    public void zoomToFeatureExtent(AjaxRequestTarget target, Vector vector, Number buffer) {
        StringBuilder builder = new StringBuilder();

        builder.append("var points = [];");
        builder.append(vector.getJsId() + ".getSource().forEachFeature(function(feature) {");
        builder.append("  points.push(feature.getGeometry().getCoordinates());");
        builder.append("});");
        builder.append("var extent = ol.extent.boundingExtent(points);");
        builder.append("extent = ol.extent.buffer(extent, parseFloat('" + buffer + "'));");
        builder.append(getModel().getObject().getJsId() + ".getView().fitExtent(extent, "
                + getModel().getObject().getJsId() + ".getSize());");

        target.appendJavaScript(builder.toString());
    }

    @Override
    public abstract void renderHead(final IHeaderResponse response);

    /**
     * Renders the Javascript before the construction of the map.
     *
     * @return String with the rendered Javascript
     */
    public String renderBeforeConstructorJs() {

        StringBuilder builder = new StringBuilder();

        if (getModel().getObject().getLayers() != null) {

            for (Layer layer : getModel().getObject().getLayers()) {

                // create vector data sources before the map
                if (layer.getSource() != null && layer.getSource() instanceof VectorSource) {

                    VectorSource vectorSource = (VectorSource) layer.getSource();

                    if (vectorSource.getFeatures() != null) {
                        for (Feature feature : vectorSource.getFeatures()) {
                            builder.append("var " + feature.getJsId() + " = new " + feature.getJsType() + "("
                                    + feature.renderJs() + ");\n");
                        }
                    }
                }

                // create server vector data source before the map
                if (layer.getSource() != null && layer.getSource() instanceof ServerVector) {

                    ServerVector vectorSource = (ServerVector) layer.getSource();
                    builder.append("var " + vectorSource.getJsId() + " = new " + vectorSource.getJsType() + "("
                            + vectorSource.renderJs() + ");\n");

                    // for the GeoJSON loader, render the loading function
                    if (vectorSource.getLoader() instanceof DefaultGeoJsonLoader) {
                        DefaultGeoJsonLoader loader = (DefaultGeoJsonLoader) vectorSource.getLoader();

                        if (layerDataLoadedMap.get(layer) != null) {
                            loader.dataLoaded(layerDataLoadedMap.get((Vector) layer));
                        }
                        builder.append(loader.renderBeforeConstructorJs() + ";\n");
                    }
                }

                builder.append(layer.getJsId() + " = new " + layer.getJsType() + "(" + layer.renderJs() + ");\n");
            }
        }

        return builder.toString();
    }

    /**
     * Renders the Javascript after the construction of the map.
     *
     * @return String with the rendered Javascript
     */
    public String renderAfterConstructorJs() {

        StringBuilder builder = new StringBuilder();

        // handle additional map building code
        if (getModel().getObject().getLayers() != null) {

            for (Layer layer : getModel().getObject().getLayers()) {

                if (layer.getSource() != null && layer.getSource() instanceof VectorSource) {

                    VectorSource vectorSource = (VectorSource) layer.getSource();

                    if (vectorSource.getFeatures() != null) {
                        for (Feature feature : vectorSource.getFeatures()) {
                            builder.append(feature.renderAfterConstructorJs());
                        }
                    }
                }
            }
        }

        return builder.toString();
    }

    /**
     * Renders the Javascript for this map.
     *
     * @return String of rendered Javascript
     */
    public String renderJs() {

        StringBuilder builder = new StringBuilder();
        builder.append(renderBeforeConstructorJs() + "\n\n");
        builder.append(getModel().getObject().getJsId() + " = new "
                + getModel().getObject().getJsType() + "(");
        builder.append(getModel().getObject().renderJs());
        builder.append(");\n\n");
        builder.append(renderAfterConstructorJs());
        return builder.toString();
    }
}
