package org.wicketstuff.examples.gmap.both;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class BothGMapApplication extends WebApplication
{

    @Override
    public Class<? extends Page> getHomePage()
    {
        return HomePage.class;
    }
}
