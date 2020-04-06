package org.wicketstuff.examples.gmap.marker;

import org.apache.wicket.Page;
import org.wicketstuff.examples.gmap.CommonGmapApplication;

public class MarkerGMapApplication extends CommonGmapApplication
{

    @Override
    public Class<? extends Page> getHomePage()
    {
        return HomePage.class;
    }
}
