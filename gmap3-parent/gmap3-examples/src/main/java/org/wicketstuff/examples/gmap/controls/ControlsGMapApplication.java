package org.wicketstuff.examples.gmap.controls;

import org.apache.wicket.Page;
import org.wicketstuff.examples.gmap.CommonGmapApplication;

public class ControlsGMapApplication extends CommonGmapApplication
{

    @Override
    public Class<? extends Page> getHomePage()
    {
        return HomePage.class;
    }
}
