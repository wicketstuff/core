package org.wicketstuff.examples.gmap.geocode;

import org.apache.wicket.Page;
import org.wicketstuff.examples.gmap.CommonGmapApplication;

public class GeoCodeGMapApplication extends CommonGmapApplication
{

    @Override
    public Class<? extends Page> getHomePage()
    {
        return HomePage.class;
    }
}
