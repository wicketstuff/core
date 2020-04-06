package org.wicketstuff.examples.gmap.clustering;

import org.apache.wicket.Page;
import org.wicketstuff.examples.gmap.CommonGmapApplication;

public class ClusteringGMapApplication extends CommonGmapApplication
{

    @Override
    public Class<? extends Page> getHomePage()
    {
        return HomePage.class;
    }
}
