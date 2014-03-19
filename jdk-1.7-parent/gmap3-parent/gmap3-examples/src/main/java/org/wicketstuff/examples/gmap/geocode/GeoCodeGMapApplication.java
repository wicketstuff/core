package org.wicketstuff.examples.gmap.geocode;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class GeoCodeGMapApplication extends WebApplication
{

    @Override
    public Class<? extends Page> getHomePage()
    {
        return HomePage.class;
    }
}
