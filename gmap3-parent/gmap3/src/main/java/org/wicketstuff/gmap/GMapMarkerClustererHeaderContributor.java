package org.wicketstuff.gmap;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * Header contributor for marker clusterer file
 * 
 */
public class GMapMarkerClustererHeaderContributor extends Behavior
{
    private static final long serialVersionUID = 1L;

    public static final JavaScriptResourceReference INSTANCE = new JavaScriptResourceReference(GMapMarkerClustererHeaderContributor.class, "markerclusterer.js");

    /**
     * Constructor.
     *
     */
    public GMapMarkerClustererHeaderContributor()
    {
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response)
    {
        super.renderHead(component, response);
        response.render(JavaScriptHeaderItem.forReference(INSTANCE));
    }
}
