package org.wicketstuff.gmap;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

public class GMapHeaderContributor extends Behavior
{

    private static final long serialVersionUID = 1L;
    // URL for Google Maps' API endpoint.
    private static final String GMAP_API_URL = "%s://maps.google.com/maps/api/js?v=3&sensor=%s";
    private static final String HTTP = "http";
    // We have some custom Javascript.
    private static final ResourceReference WICKET_GMAP_JS = new JavaScriptResourceReference(GMap.class, "wicket-gmap.js");
    private String schema;
    private String sensor = "false";

    public GMapHeaderContributor()
    {
        this(HTTP, false);
    }

    public GMapHeaderContributor(final boolean sensor)
    {
        this(HTTP, sensor);
    }

    public GMapHeaderContributor(final String schema)
    {
        this(schema, false);
    }

    /**
     * Constructor.
     *
     * Should be added to the page.
     *
     * @param schema http or https?
     * @param sensor
     */
    public GMapHeaderContributor(final String schema, final boolean sensor)
    {
        this.schema = schema;
        if (sensor)
        {
            this.sensor = "true";
        }
    }

    /**
     * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
     */
    @Override
    public void renderHead(Component component, IHeaderResponse response)
    {
        super.renderHead(component, response);
        response.render(JavaScriptHeaderItem.forUrl(String.format(GMAP_API_URL, schema, sensor)));
        response.render(JavaScriptHeaderItem.forReference(WICKET_GMAP_JS));
    }

    public String getSensor()
    {
        return sensor;
    }
    
    
}
