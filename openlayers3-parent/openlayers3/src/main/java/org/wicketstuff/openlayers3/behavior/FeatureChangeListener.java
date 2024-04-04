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
import org.wicketstuff.openlayers3.api.Feature;
import org.wicketstuff.openlayers3.api.coordinate.LongLat;
import org.wicketstuff.openlayers3.api.util.HeaderUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Provides a behavior that reports changes to a feature.
 */
public abstract class FeatureChangeListener extends AbstractDefaultAjaxBehavior {

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
     * The feature that will be monitored for changed.
     */
    private final Feature feature;

    /**
     * Creates a new instance.
     *
     * @param feature Feature to which we are listening
     */
    public FeatureChangeListener(Feature feature) {
        this(DEFAULT_PROJECTION, feature);
    }

    /**
     * Creates a new instance.
     *
     * @param projection Projection for returned data
     * @param feature Feature to which we are listening
     */
    public FeatureChangeListener(String projection, Feature feature) {
        this.projection = projection;
        this.feature = feature;
    }

    /**
     * Invoked when the feature has been modified.
     *
     * @param target Ajax request target
     * @param featureId Element ID of the feature modified
     * @param longLat The new coordinate of the modified feature
     * @param properties The properties of the modified feature
     */
    public abstract void handleChange(AjaxRequestTarget target, String featureId, LongLat longLat,
                                      JsonObject properties);

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

        handleChange(target, featureId, new LongLat(longitude, latitude, projection), propertiesJson);
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        final Map<String, CharSequence> params = new HashMap<String, CharSequence>();
        params.put("callbackUrl", getCallbackUrl());
        params.put("changeHandlerId", (counter++).toString());
        params.put("componentId", component.getMarkupId());
        params.put("featureId", feature.getJsId());
        params.put("projection", projection != null ? projection : "NULL");

        HeaderUtils.renderOnDomReady(response, FeatureChangeListener.class,
                "FeatureChangeListener.js", params);
    }
}
