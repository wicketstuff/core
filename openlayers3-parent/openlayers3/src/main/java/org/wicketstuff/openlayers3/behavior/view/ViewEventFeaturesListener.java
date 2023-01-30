package org.wicketstuff.openlayers3.behavior.view;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.openlayers3.api.layer.Vector;
import org.wicketstuff.openlayers3.api.util.HeaderUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;

/**
 * Provides a behavior that invokes a callback event whenever the map view changes. Specifically, this behavior will
 * monitor the map's ol.View for 'center' and 'resolution' events. In addition to providing information on the current
 * view, it will also provide all of the features in the view extent for the provided layer.
 */
public abstract class ViewEventFeaturesListener extends AbstractDefaultAjaxBehavior {

    /**
     * Default projection.
     */
    public final static String DEFAULT_PROJECTION = "EPSG:4326";

    /**
     * Counter for generating instance identifiers.
     */
    private static Long counter = 0L;

    /**
     * Map for storing object Ids.
     */
    private static java.util.Map<Object, String> objectIds = new HashMap<Object, String>();

    /**
     * Projection for this map.
     */
    private String projection;

    /**
     * Layer for which features will be enumerated.
     */
    private Vector vector;

    public ViewEventFeaturesListener(Vector vectorLayer) {
        this(DEFAULT_PROJECTION, vectorLayer);
    }

    public ViewEventFeaturesListener(String projection, Vector vectorLayer) {
        this.vector = vectorLayer;
        this.projection = projection;
    }

    /**
     * Callback method that is invoked when the map's view is changed.
     *
     * @param target Ajax request target
     * @param viewEvent The current view
     * @param features JSON array of features in the view
     */
    public abstract void handleViewEvent(AjaxRequestTarget target, ViewEvent viewEvent, JsonArray features);

    /**
     * Javascript name of the callback function to invoke when feature data has been loaded into the layer.
     *
     * @return String with the Javascript callback function name
     */
    public String getCallbackFunctionName() {
        return "viewEventFeaturesHandler_" + getId();
    }

    /**
     * Returns a unique ID for this behavior.
     *
     * @return String with a unique ID
     */
    public String getId() {

        String objectId = null;

        if (objectIds.get(this) != null) {
            objectId = objectIds.get(this);
        } else {
            objectId = getClass().getSimpleName().toLowerCase() + counter++;
            objectIds.put(this, objectId);
        }

        return objectId;
    }

    @Override
    protected void respond(AjaxRequestTarget target) {

        IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
        String viewJson = params.getParameterValue("view").toString();
        String featuresJson = params.getParameterValue("features").toString();

        ViewEvent viewEvent = null;
        JsonElement viewParsed = JsonParser.parseString(viewJson);
        if(!(viewParsed instanceof JsonNull)) {
            viewEvent = new ViewEvent(viewParsed.getAsJsonObject());
        }

        JsonArray features= null;
        JsonElement featuresParsed = JsonParser.parseString(featuresJson);
        if(!(featuresParsed instanceof JsonNull)) {
            features = featuresParsed.getAsJsonArray();
        }

        handleViewEvent(target, viewEvent, features);
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        final Map<String, CharSequence> params = new HashMap<String, CharSequence>();
        params.put("callbackUrl", getCallbackUrl());
        params.put("callbackFunctionName", getCallbackFunctionName());
        params.put("componentId", component.getMarkupId());
        params.put("layerId", vector.getJsId());
        params.put("projection", projection != null ? projection : "NULL");

        HeaderUtils.renderOnDomReady(response, ViewEventFeaturesListener.class,
                "ViewEventFeaturesListener.js", params);
    }
}
