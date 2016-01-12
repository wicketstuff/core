package org.wicketstuff.gmap;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;

/**
 * Header contributor for marker clusterer file
 * 
 */
public class GMapMarkerClustererHeaderContributor extends Behavior
{
    private static final long serialVersionUID = 1L;

    private static final String GMAP_CLUSTERER_URL = "//google-maps-utility-library-v3.googlecode.com/svn/trunk/markerclusterer/src/markerclusterer.js";

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
        response.render(JavaScriptHeaderItem.forUrl(GMAP_CLUSTERER_URL));
    }
}
