package org.wicketstuff.examples.gmap.listen;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class ListenGMapApplication extends WebApplication
{

    @Override
    public Class<? extends Page> getHomePage()
    {
        return HomePage.class;
    }
}
