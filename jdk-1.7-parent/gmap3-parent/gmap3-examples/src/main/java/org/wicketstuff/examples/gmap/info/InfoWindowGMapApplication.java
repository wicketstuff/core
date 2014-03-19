package org.wicketstuff.examples.gmap.info;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class InfoWindowGMapApplication extends WebApplication
{

    @Override
    public Class<? extends Page> getHomePage()
    {
        return HomePage.class;
    }
}
