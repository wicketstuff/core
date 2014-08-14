package org.wicketstuff.openlayers3.api.source.loader;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.template.PackageTextTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides a behavior that invokes a callback after feature data has been loaded into the vector layer. This is used to
 * support the ability to add VectorFeaturesLoadedListener instance on vector layers, this likely isn't the class for
 * which you are looking.
 */
public abstract class VectorFeatureDataLoadedListener extends AbstractDefaultAjaxBehavior {

    /**
     * Counter for generating instance identifiers.
     */
    public static Long counter = 0L;

    /**
     * Map for starting object Ids.
     */
    public static java.util.Map<Object, String> objectIds = new HashMap<>();

    /**
     * Callback method that is invoked when feature data has been loaded into the vector layer.
     *
     * @param target
     *         Ajax request target
     */
    public abstract void handleDataLoaded(AjaxRequestTarget target);

    /**
     * Javascript name of the callback function to invoke when feature data has been loaded into the layer.
     *
     * @return String with the Javascript callback function name
     */
    public String getCallbackFunctionName() {
        return "dataLoadedHandler_" + getId();
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

        String sourceId = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("sourceId").toString();
        handleDataLoaded(target);
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        final Map<String, CharSequence> params = new HashMap<String, CharSequence>();
        params.put("callbackUrl", getCallbackUrl());
        params.put("componentId", component.getMarkupId());
        params.put("dataLoaderId", getId());

        PackageTextTemplate template = new PackageTextTemplate(VectorFeatureDataLoadedListener.class,
                "VectorFeatureDataLoadedListener.js");
        response.render(OnDomReadyHeaderItem.forScript(template.asString(params)));
    }
}
