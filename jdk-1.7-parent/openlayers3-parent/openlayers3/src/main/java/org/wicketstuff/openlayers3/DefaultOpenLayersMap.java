package org.wicketstuff.openlayers3;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.model.IModel;
import org.wicketstuff.openlayers3.api.Map;

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
        response.render(CssHeaderItem.forUrl("http://ol3js.org/en/master/css/ol.css"));

        if (RuntimeConfigurationType.DEVELOPMENT.equals(getApplication().getConfigurationType())) {
            response.render(JavaScriptHeaderItem.forUrl("http://ol3js.org/en/master/build/ol-debug.js"));
        } else {
            response.render(JavaScriptHeaderItem.forUrl("http://ol3js.org/en/master/build/ol.js"));
        }

        response.render(OnDomReadyHeaderItem.forScript(this.renderJs()));
    }
}
