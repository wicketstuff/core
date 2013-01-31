package org.wicketstuff.examples.gmap.simple;

import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GLatLng;

/**
 * SimplePage for the wicket-contrib-gmap3 project
 */
public class SimplePage extends WicketExamplePage
{
  private static final long serialVersionUID = 1L;

    public SimplePage()
    {
        GMap map = new GMap("map");
        map.setStreetViewControlEnabled(false);
        map.setScaleControlEnabled(true);
        map.setScrollWheelZoomEnabled(true);
        map.setCenter(new GLatLng(52.47649, 13.228573));        
        add(map);
    }
}
