package org.wicketstuff.gmap;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;

public class GMapHeaderContributor extends Behavior
{

    private static final long serialVersionUID = 1L;
    // URL for Google Maps' API endpoint.
    private static final String GMAP_API_URL = "%s://maps.googleapis.com/maps/api/js?v=3&";
    private static final String HTTP = "http";
    // We have some custom Javascript.
    private String scheme;
    private String sensor = "false";
    private String apiKey = null;

    
    /**
     * @param scheme http or https?
     * @param apiKey your Google Maps API-key
     */
    public GMapHeaderContributor(final String scheme, final String apiKey)
    {
        this.scheme = scheme;
        this.apiKey = apiKey;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response)
    {
        super.renderHead(component, response);
        response.render(JavaScriptHeaderItem.forReference(WicketGMapJsReference.INSTANCE));
        String url = String.format(GMAP_API_URL, scheme);
        if (apiKey != null)
        {
            url = url + "key=" + apiKey;            
        }
        response.render(JavaScriptHeaderItem.forUrl(url));
    }
    
}
