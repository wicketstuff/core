package org.wicketstuff.openlayers3.api.source.loader;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.openlayers3.api.layer.Vector;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides a behavior that invokes a callback after feature data has been loaded into the vector layer. This is used to
 * support the ability to add VectorFeaturesLoadedListener instance on vector layers, this likely isn't the class for
 * which you are looking.
 */
public abstract class VectorFeaturesLoadedListener extends AbstractDefaultAjaxBehavior {

    private final static Logger logger = LoggerFactory.getLogger(VectorFeatureDataLoadedListener.class);

    /**
     * Counter for generating instance identifiers.
     */
    public static Long counter = 0L;

    /**
     * Map for storing object Ids.
     */
    public static java.util.Map<Object, String> objectIds = new HashMap<Object, String>();

    /**
     * Vector layer for which we are monitoring the loading of features.
     */
    public Vector vector;

    /**
     * Creates a new instance.
     *
     * @param vector
     *         The vector layer for which we are monitoring the loading of features.
     */
    public VectorFeaturesLoadedListener(Vector vector) {
        super();
        getId();
        this.vector = vector;
    }

    /**
     * Callback method that is invoked when feature data has been loaded into the vector layer.
     *
     * @param target
     *         Ajax request target
     */
    public abstract void handleDataLoaded(AjaxRequestTarget target);

    /**
     * Returns the vector layer to which we are listening.
     *
     * @return Vector layer
     */
    public Vector getVector() {
        return vector;
    }

    /**
     * Javascript name of the callback function to invoke when feature data has been loaded into the layer.
     *
     * @return String with the Javascript callback function name
     */
    public String getCallbackFunctionName() {
        return "featuresLoadedHandler_" + getId();
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
        handleDataLoaded(target);
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        final Map<String, CharSequence> params = new HashMap<String, CharSequence>();
        params.put("callbackUrl", getCallbackUrl());
        params.put("componentId", vector.getJsId());
        params.put("dataLoaderId", getId());

        PackageTextTemplate template = new PackageTextTemplate(VectorFeatureDataLoadedListener.class,
                "VectorFeaturesLoadedListener.js");
        response.render(OnDomReadyHeaderItem.forScript(template.asString(params)));
    }
}
