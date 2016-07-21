package org.wicketstuff.examples.gmap.controls;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class ControlsGMapApplication extends WebApplication
{

    @Override
    public Class<? extends Page> getHomePage()
    {
        return HomePage.class;
    }
}
