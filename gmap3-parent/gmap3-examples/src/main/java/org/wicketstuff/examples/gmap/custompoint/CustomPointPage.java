package org.wicketstuff.examples.gmap.custompoint;

import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GIcon;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GMarker;
import org.wicketstuff.gmap.api.GMarkerOptions;
import org.wicketstuff.gmap.api.GOverlay;
import org.wicketstuff.gmap.api.GSize;

/**
 * Demonstrates how to customize markers.
 */
public class CustomPointPage extends WicketExamplePage
{

    private static final long serialVersionUID = 1L;

    public CustomPointPage()
    {
        GMap map = new GMap("map");
        map.setCenter(new GLatLng(52.37649, 4.888573));
        add(map);

        GIcon icon =
                new GIcon("../pin.gif").setScaledSize(
                new GSize(64, 64)).setSize(new GSize(64, 64));
        GOverlay marker = new GMarker(new GMarkerOptions(map, new GLatLng(52.37649, 4.888573), "My Title", icon));

        map.addOverlay(marker);
    }
}