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
     * @deprecated since 22th June 2016 Google Maps requires an API-key,
     * therefore you should use
     * {@link #GMapHeaderContributor(java.lang.String, java.lang.String) }
     * instead of this constructor
     * @see
     * http://googlegeodevelopers.blogspot.de/2016/06/building-for-scale-updates-to-google.html
     */
    public GMapHeaderContributor()
    {
        this(HTTP, false);
    }

    /**
     * @param sensor this parameter will be ignored
     * @deprecated Since the sensor-parameter is no longer required from Google
     * you should use {@link #GMapHeaderContributor() } instead of this
     * constructor
     */
    public GMapHeaderContributor(final boolean sensor)
    {
        this(HTTP, sensor);
    }

    /**
     * @deprecated since 22th June 2016 Google Maps requires an API-key,
     * therefore you should use
     * {@link #GMapHeaderContributor(java.lang.String, java.lang.String) }
     * instead of this constructor
     * @see
     * http://googlegeodevelopers.blogspot.de/2016/06/building-for-scale-updates-to-google.html
     */
    public GMapHeaderContributor(final String scheme)
    {
        this(scheme, false);
    }

    /**
     * Constructor.
     *
     * Should be added to the page.
     *
     * @param scheme http or https?
     * @param sensor this parameter will be ignored
     *
     * @deprecated Since the sensor-parameter is no longer required from Google
     * you should use {@link #GMapHeaderContributor(java.lang.String) } instead
     * of this constructor
     */
    public GMapHeaderContributor(final String scheme, final boolean sensor)
    {
        this.scheme = scheme;
        if (sensor)
        {
            this.sensor = "true";
        }
    }

    
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

    /**
     * @deprecated Since the sensor-parameter is no longer required from Google
     * this method will be removed in future versions
     */
    public String getSensor()
    {
        return sensor;
    }
    
}
