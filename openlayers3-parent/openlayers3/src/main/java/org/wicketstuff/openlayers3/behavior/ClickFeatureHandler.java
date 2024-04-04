package org.wicketstuff.openlayers3.behavior;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.Strings;
import org.wicketstuff.openlayers3.api.coordinate.LongLat;
import org.wicketstuff.openlayers3.api.util.HeaderUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Provides a behavior that handles clicking on features on the map.
 */
public abstract class ClickFeatureHandler extends AbstractDefaultAjaxBehavior {

    /**
     * Default projection.
     */
    public final static String DEFAULT_PROJECTION = "EPSG:4326";
    /**
     * Counter for generating instance identifiers.
     */
    public static Long counter = 0L;
    /**
     * The projection for this behavior, used to translate the 'clicked' coordinates.
     */
    private final String projection;

    /**
     * Creates a new instance.
     */
    public ClickFeatureHandler() {
        this(DEFAULT_PROJECTION);
    }

    /**
     * Creates a new instance.
     *
     * @param projection
     *         The clicked coordinate will be transformed into this projection
     */
    public ClickFeatureHandler(String projection) {
        this.projection = projection;
    }

    /**
     * Callback for handling mouse clicks on map features.
     *
     * @param target
     *         Ajax request target
     * @param featureId
     *         Unique element ID of the clicked feature
     * @param longLat
     *         Coordinate of the feature
     * @param properties
     *         JsonObject with the clicked feature's properties
     */
    public abstract void handleClick(AjaxRequestTarget target, String featureId, LongLat longLat,
                                     JsonObject properties);

    /**
     * Callback for handling mouse clicks on the map that do not intersect with features.
     *
     * @param target
     *         Ajax request target
     * @param longLat
     *         Coordinate of the click
     */
    public void handleClickMiss(AjaxRequestTarget target, LongLat longLat) {

    }

    @Override
    protected void respond(AjaxRequestTarget target) {

        IRequestParameters params = RequestCycle.get().getRequest().getRequestParameters();
        String coordinateRaw = params.getParameterValue("coordinate").toString();
        String featureId = params.getParameterValue("id").toString();
        String properties = params.getParameterValue("properties").toString();

        JsonObject propertiesJson = null;
        JsonElement propertiesParsed = JsonParser.parseString(properties);
        if (!(propertiesParsed instanceof JsonNull)) {
            propertiesJson = propertiesParsed.getAsJsonObject();
        }

        String[] coordinates = Strings.split(coordinateRaw, ',');
        Double longitude = Double.parseDouble(coordinates[0]);
        Double latitude = Double.parseDouble(coordinates[1]);

        if (!properties.isEmpty()) {

            handleClick(target, featureId, new LongLat(longitude, latitude, projection), propertiesJson);
        } else {

            handleClickMiss(target, new LongLat(longitude, latitude, projection));
        }
    }

    @Override
    public void renderHead(final Component component, final IHeaderResponse response) {
        super.renderHead(component, response);

        final Map<String, CharSequence> params = new HashMap<String, CharSequence>();
        params.put("callbackUrl", getCallbackUrl());
        params.put("clickHandlerId", (counter++).toString());
        params.put("componentId", component.getMarkupId());
        params.put("projection", projection != null ? projection : "NULL");

        HeaderUtils.renderOnDomReady(response, ClickFeatureHandler.class,
                "ClickFeatureHandler.js", params);
    }
}
