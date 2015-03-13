package org.wicketstuff.openlayers3;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.model.IModel;
import org.wicketstuff.openlayers3.api.Map;
import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;

/**
 * Provides an OpenLayers map that fetches resources from the OpenLayers public website.
 */
public class DefaultOpenLayersMap extends OpenLayersMap {

    /**
     * Creates a new instance
     *
     * @param id
     *         Wicket element ID
     * @param model
     *         Backing modep for this map
     */
    public DefaultOpenLayersMap(final String id, final IModel<Map> model) {
        super(id, model);
    }

    @Override
    public void renderHead(final IHeaderResponse response) {
		response.render(CssHeaderItem.forReference(new WebjarsJavaScriptResourceReference("openlayers/current/ol.css")));

        if (RuntimeConfigurationType.DEVELOPMENT.equals(getApplication().getConfigurationType())) {
			response.render(JavaScriptHeaderItem.forReference(new WebjarsJavaScriptResourceReference("openlayers/current/ol-debug.js")));
        } else {
			response.render(JavaScriptHeaderItem.forReference(new WebjarsJavaScriptResourceReference("openlayers/current/ol.js")));
        }

        response.render(OnDomReadyHeaderItem.forScript(this.renderJs()));
    }
}
