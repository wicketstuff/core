package org.wicketstuff.examples.gmap.circles;

import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GCircle;
import org.wicketstuff.gmap.api.GLatLng;

/**
 * Demonstrates the usage of circles.
 */
public class CirclesPage extends WicketExamplePage
{
    public CirclesPage()
    {
        GMap map = new GMap("topPanel");
        GCircle circle = new GCircle(new GLatLng(37.35, -121.9), 10000, "#000000", 4, 0.7f, "#E9601A", 0.7f);
        circle.setEditable(true);
        circle.setDraggable(true);
        map.addOverlay(circle);
        map.setZoom(9);
        add(map);
    }
}
