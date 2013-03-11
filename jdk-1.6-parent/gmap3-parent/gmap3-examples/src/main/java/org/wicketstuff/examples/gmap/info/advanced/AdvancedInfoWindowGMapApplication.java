package org.wicketstuff.examples.gmap.info.advanced;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class AdvancedInfoWindowGMapApplication extends WebApplication
{

    @Override
    public Class<? extends Page> getHomePage()
    {
        return HomePage.class;
    }
}
