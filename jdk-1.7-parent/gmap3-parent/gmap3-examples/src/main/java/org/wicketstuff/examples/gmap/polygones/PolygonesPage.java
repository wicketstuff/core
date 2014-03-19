package org.wicketstuff.examples.gmap.polygones;

import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GPolygon;
import org.wicketstuff.gmap.api.GPolyline;

/**
 * Demonstrates the usage of polygones.
 */
public class PolygonesPage extends WicketExamplePage
{

    private static final long serialVersionUID = 1L;

    public PolygonesPage()
    {
        GMap map = new GMap("topPanel");
        map.addOverlay(new GPolygon("#000000", 4, 0.7f, "#E9601A", 0.7f, new GLatLng(37.3, -122.4), new GLatLng(37.2, -122.2),
                new GLatLng(37.3, -122.0), new GLatLng(37.4, -122.2), new GLatLng(37.3, -122.4)));
        map.addOverlay(new GPolyline("#FFFFFF", 8, 1.0f, new GLatLng(37.35, -122.3), new GLatLng(37.25, -122.25), new GLatLng(
                37.3, -122.2), new GLatLng(37.25, -122.15), new GLatLng(37.35, -122.1)));
        map.setZoom(10);
        add(map);
    }
}
