package org.wicketstuff.examples.gmap.zoomlimit;

import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GLatLng;

/**
 * ZoomLimitPage for the wicket-contrib-gmap3 project. This page creates a GMap with a set min and max zoom level.
 */
public class ZoomLimitPage extends WicketExamplePage
{
  private static final long serialVersionUID = 1L;

    public ZoomLimitPage()
    {
        GMap map = new GMap("map");
        map.setStreetViewControlEnabled(false);
        map.setScaleControlEnabled(true);
        map.setScrollWheelZoomEnabled(true);
        map.setCenter(new GLatLng(52.47649, 13.228573));        
        map.setMinZoom(6);
        map.setMaxZoom(10);
        add(map);
    }
}
