package org.wicketstuff.examples.gmap.bounds;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class BoundsGMapApplication extends WebApplication
{

    @Override
    public Class<? extends Page> getHomePage()
    {
        return HomePage.class;
    }
}
