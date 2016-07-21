package org.wicketstuff.examples.gmap.trigger;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public class TriggerGMapApplication extends WebApplication
{

    @Override
    public Class<? extends Page> getHomePage()
    {
        return HomePage.class;
    }
}
