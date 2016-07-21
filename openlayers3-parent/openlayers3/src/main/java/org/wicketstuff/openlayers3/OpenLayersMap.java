package org.wicketstuff.openlayers3;

import java.util.HashMap;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.openlayers3.api.Extent;
import org.wicketstuff.openlayers3.api.Feature;
import org.wicketstuff.openlayers3.api.JavascriptObject;
import org.wicketstuff.openlayers3.api.Map;
import org.wicketstuff.openlayers3.api.PersistentFeature;
import org.wicketstuff.openlayers3.api.geometry.Point;
import org.wicketstuff.openlayers3.api.interaction.Interaction;
import org.wicketstuff.openlayers3.api.layer.Layer;
import org.wicketstuff.openlayers3.api.layer.Vector;
import org.wicketstuff.openlayers3.api.source.vector.Cluster;
import org.wicketstuff.openlayers3.api.source.vector.VectorSource;
import org.wicketstuff.openlayers3.api.source.vector.loader.DefaultGeoJsonLoader;
import org.wicketstuff.openlayers3.api.source.vector.loader.VectorFeatureDataLoadedListener;
import org.wicketstuff.openlayers3.api.source.vector.loader.VectorFeaturesLoadedListener;

import com.google.gson.JsonArray;

/**
 * Provides the base class for all panels containing an OpenLayers map.
 */
public abstract class OpenLayersMap extends GenericPanel<Map> {

    private final static Logger logger = LoggerFactory.getLogger(OpenLayersMap.class);

    /**
     * Map of layers to the data loaded handler that notifies their listeners.
     */
    private java.util.Map<Layer, VectorFeatureDataLoadedListener> layerDataLoadedMap =
            new HashMap<Layer, VectorFeatureDataLoadedListener>();

    /**
     * Map of layers to the data loaded handler that notifies their listeners.
     */
    private java.util.Map<Layer, VectorFeaturesLoadedListener> layerLoadedMap =
            new HashMap<Layer, VectorFeaturesLoadedListener>();

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


    @Override
    protected void onConfigure() {
        super.onConfigure();

        Map map = getModelObject();
        map.setTarget(getMarkupId());

        if (map.getLayers() != null) {

            for (Layer layer : map.getLayers()) {

                if (layer instanceof Vector) {

                    final Vector vectorLayer = (Vector) layer;

                    if(vectorLayer.getFeatureDataLoadedListeners() != null
                            && vectorLayer.getFeatureDataLoadedListeners().size() > 0) {

                        // add a behavior to be notified when new data is loaded
                        VectorFeatureDataLoadedListener vectorFeatureDataLoadedListener =
                                new VectorFeatureDataLoadedListener(vectorLayer) {

                                    @Override
                                    public void handleDataLoaded(AjaxRequestTarget target, JsonArray features) {
                                        vectorLayer.notifyFeatureDataLoadedListeners(target, features);
                                    }
                                };
                        add(vectorFeatureDataLoadedListener);

                        // map the layer to the data loaded handler
                        layerDataLoadedMap.put(layer, vectorFeatureDataLoadedListener);
                    }

                    if(vectorLayer.getFeaturesLoadedListeners() != null
                            && vectorLayer.getFeaturesLoadedListeners().size() > 0) {

                        // add a behavior to be notified when new features are loaded
                        VectorFeaturesLoadedListener vectorFeatureLoadedListener =
                                new VectorFeaturesLoadedListener(vectorLayer) {

                                    @Override
                                    public void handleDataLoaded(AjaxRequestTarget target) {
                                        vectorLayer.notifyFeaturesLoadedListeners(target);
                                    }
                                };
                        add(vectorFeatureLoadedListener);

                        // map the layer to the data loaded handler
                        layerLoadedMap.put(layer, vectorFeatureLoadedListener);
                    }
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

        String jsId = getModelObject().getJsId();
        builder.append(jsId + ".getView().fitExtent(extent, " + jsId + ".getSize());");

        target.appendJavaScript(builder.toString());
    }

    /**
     * Sets the center of the map's current view.
     *
     * @param target Ajax request target
     * @param point New center location for the map
     */
    public void setViewCenter(AjaxRequestTarget target, Point point) {

        // update our model
        getModelObject().getView().setCenter(point.getCoordinate());

        // update the map
        target.appendJavaScript(JavascriptObject.JS_GLOBAL + "['map_" + getMarkupId() + "'].getView().setCenter("
                + point.renderJs() + ");");
    }

    /**
     * Adds the provided interaction to the map.
     *
     * @param target Ajax request target
     * @param interaction Interaction to add
     */
    public void addInteraction(AjaxRequestTarget target, Interaction interaction) {

        // update our model
        getModelObject().getInteractions().add(interaction);

        // update the map
        target.appendJavaScript(interaction.getJsId() + " = new " + interaction.getJsType()
                + "(" + interaction.renderJs() + ");"
                + JavascriptObject.JS_GLOBAL + "['map_" + getMarkupId() + "'].addInteraction(" + interaction.getJsId() + ");");
    }

    /**
     * Removes provided interaction from the map.
     *
     * @param target Ajax request target
     * @param interaction Interaction to add
     */
    public void removeInteraction(AjaxRequestTarget target, Interaction interaction) {

        // update our model
        getModelObject().getInteractions().remove(interaction);

        // update the map
        target.appendJavaScript(JavascriptObject.JS_GLOBAL + "['map_" + getMarkupId() + "'].removeInteraction("
			+ interaction.getJsId() + ");");
    }

	/**
     * Sets the extent for the map and zooms to that extent.
     *
     * @param target Ajax request target
     * @param extent Extent to which the map will be zoomed
     */
	public void zoomToExtent(AjaxRequestTarget target, Extent extent) {
		target.appendJavaScript(JavascriptObject.JS_GLOBAL + "['map_" + getMarkupId() + "'].getView().fitExtent("
			+ getModelObject().getView().getExtent().renderJsForView(getModelObject().getView()) + ","
			+ JavascriptObject.JS_GLOBAL + "['map_" + getMarkupId() + "']" + ".getSize());");
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

		// make sure our global variable exists
		builder.append("if(typeof " + JavascriptObject.JS_GLOBAL + " === 'undefined') { "
			+ JavascriptObject.JS_GLOBAL + " = []};\n\n");

        Map map = getModelObject();
        if (map != null) {

            for (Layer layer : map.getLayers()) {

                // create vector data sources before the map
                if (layer instanceof Vector) {

                    Vector vectorLayer = (Vector) layer;
                    VectorSource vectorSource = vectorLayer.getSource();

                    // render any persistent features
                    if(vectorSource.getFeatures() != null) {
                        for(Feature feature : vectorSource.getFeatures()) {
                            if(feature instanceof PersistentFeature) {
                                builder.append(feature.getJsId() + " = new " + feature.getJsType() + "("
                                        + feature.renderJs() + ");\n");
                            }
                        }
                    }

                    // ensure our loader has a reference to our source
                    if(vectorSource.getLoader() != null) {
                        vectorSource.getLoader().setSource(vectorSource);
                    }

                    // render the source for the cluster source
                    if(vectorSource instanceof Cluster) {
                        Cluster source = (Cluster) vectorSource;
                        builder.append(renderServerVectorJs(source.getSource(), layer));
                    }

                    // render the vector source
                    builder.append(renderServerVectorJs(vectorSource, layer));
                }

                builder.append(layer.getJsId() + " = new " + layer.getJsType() + "(" + layer.renderJs()
                        + ");\n");
            }
        }

        return builder.toString();
    }

    private String renderServerVectorJs(VectorSource vectorSource, Layer layer) {

        StringBuilder builder = new StringBuilder();

        builder.append(vectorSource.getJsId() + " = new " + vectorSource.getJsType() + "("
                + vectorSource.renderJs() + ");\n");

        if (vectorSource.getLoader() instanceof DefaultGeoJsonLoader) {
            DefaultGeoJsonLoader loader = (DefaultGeoJsonLoader) vectorSource.getLoader();

            if (layerDataLoadedMap.get(layer) != null) {

                // add our listener for the feature data loading
                loader.vectorFeatureDataLoadedListener(layerDataLoadedMap.get((Vector) layer));
            }

            if (layerLoadedMap.get(layer) != null) {

                // add our listener for the feature loading
                loader.vectorFeaturesLoadedListener(layerLoadedMap.get((Vector) layer));
            }
            builder.append(loader.renderBeforeConstructorJs() + ";\n");
        }

        return builder.toString();
    }

    /**
     * Renders the Javascript after the construction of our OL3 objects (but not the map).
     *
     * @return String with the rendered Javascript
     */
    public String renderAfterConstructorJs() {

        StringBuilder builder = new StringBuilder();
        Map map = getModelObject();

        // handle additional map building code
        if (map.getLayers() != null) {

            for (Layer layer : map.getLayers()) {

                if(layer instanceof Vector) {

                    Vector vectorLayer = (Vector) layer;
                    VectorSource vectorSource = vectorLayer.getSource();

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
     * Renders the Javascript after the construction of the map.
     *
     * @return String with the rendered Javascript
     */
    public String renderAfterMapConstructorJs() {
        StringBuilder builder = new StringBuilder();

        Map map = getModelObject();

        if(map.getView().getExtent() != null) {
            builder.append(map.getJsId() + ".getView().fitExtent(" + map.getView().getExtent().renderJsForView(map.getView()) + ","
                    + map.getJsId() + ".getSize());");
        }

        return builder.toString();
    }

    /**
     * Renders the Javascript for this map.
     *
     * @return String of rendered Javascript
     */
    public String renderJs() {

	Map map = getModelObject();

        StringBuilder builder = new StringBuilder();
        builder.append(renderBeforeConstructorJs() + "\n\n");
        builder.append(renderAfterConstructorJs());
        builder.append(map.getJsId() + " = new " + map.getJsType() + "(");
        builder.append(map.renderJs());
        builder.append(");\n\n");
        builder.append(renderAfterMapConstructorJs());
        return builder.toString();
    }
}
