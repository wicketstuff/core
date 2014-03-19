package org.wicketstuff.examples.gmap.controls;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.GMap.ZoomInBehavior;
import org.wicketstuff.gmap.GMap.ZoomOutBehavior;

/**
 * Demonstrates how to control the map via clicks on labels.
 */
public class HomePage extends WicketExamplePage
{

    private static final long serialVersionUID = 1L;

    public HomePage()
    {
        final GMap map = new GMap("topPanel");
        add(map);

        final WebMarkupContainer zoomIn = new WebMarkupContainer("zoomIn");
        zoomIn.add(map.new ZoomInBehavior("onclick"));
        add(zoomIn);

        final WebMarkupContainer zoomOut = new WebMarkupContainer("zoomOut");
        zoomOut.add(map.new ZoomOutBehavior("onclick"));
        add(zoomOut);
    }
}
