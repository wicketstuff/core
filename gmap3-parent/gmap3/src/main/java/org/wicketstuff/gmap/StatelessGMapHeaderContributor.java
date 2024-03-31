package org.wicketstuff.gmap;

import org.apache.wicket.Component;

/**
 *
 * @author robsonke
 *
 */
public class StatelessGMapHeaderContributor extends GMapHeaderContributor
{

    private static final long serialVersionUID = 1L;

    /**
     * @param scheme http or https?
     * @param apiKey your Google Maps API-key
     */
    public StatelessGMapHeaderContributor(final String scheme, final String apiKey)
    {
        super(scheme, apiKey);
    }

    @Override
    public boolean getStatelessHint(Component component)
    {
        return true;
    }
}
