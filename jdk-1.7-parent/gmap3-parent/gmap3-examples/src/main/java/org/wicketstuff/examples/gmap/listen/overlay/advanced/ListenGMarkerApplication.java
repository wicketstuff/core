package org.wicketstuff.examples.gmap.listen.overlay.advanced;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class ListenGMarkerApplication extends WebApplication
{

    @Override
    public Class<? extends Page> getHomePage()
    {
        return HomePage.class;
    }
}
