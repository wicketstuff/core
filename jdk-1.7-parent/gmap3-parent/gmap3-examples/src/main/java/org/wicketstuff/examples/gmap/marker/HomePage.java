package org.wicketstuff.examples.gmap.marker;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GMarker;
import org.wicketstuff.gmap.api.GMarkerOptions;
import org.wicketstuff.gmap.event.ClickListener;

/**
 * Demonstrates how to add markers to the map.
 */
public class HomePage extends WicketExamplePage
{

    private static final long serialVersionUID = 1L;

    public HomePage()
    {
        final GMap map = new GMap("topPanel");
        add(map);
        map.add(new ClickListener()
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onClick(AjaxRequestTarget target, GLatLng latLng)
            {
                if (latLng != null)
                {
                    if (map.getOverlays().size() >= 3)
                    {
                        map.removeOverlay(map.getOverlays().get(0));
                    }
                    map.addOverlay(new GMarker(new GMarkerOptions(map, latLng)));
                }
            }
        });
    }
}