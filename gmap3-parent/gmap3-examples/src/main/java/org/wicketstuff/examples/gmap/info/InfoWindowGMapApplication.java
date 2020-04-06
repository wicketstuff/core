package org.wicketstuff.examples.gmap.info;

import org.apache.wicket.Page;
import org.wicketstuff.examples.gmap.CommonGmapApplication;

public class InfoWindowGMapApplication extends CommonGmapApplication
{

    @Override
    public Class<? extends Page> getHomePage()
    {
        return HomePage.class;
    }
}
