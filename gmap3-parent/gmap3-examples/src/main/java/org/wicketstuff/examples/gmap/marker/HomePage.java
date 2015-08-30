package org.wicketstuff.examples.gmap.marker;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GAnimation;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GMarker;
import org.wicketstuff.gmap.api.GMarkerOptions;
import org.wicketstuff.gmap.api.GOverlay;
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

                    // stop the animation for the markers which are already on the map
                    for (GOverlay overlay : map.getOverlays())
                    {      
                       ((GMarker)overlay).setAnimation(null);
                    }

                    // create a new marker with a bounce animation
                    // the animation continues until it is set explicitly to null
                    GMarkerOptions opt = new GMarkerOptions(map, latLng, "Demonstrate how to escape\n \"special sequences\"  \n in the tooltip", true);
                    opt.setAnimation(GAnimation.BOUNCE);
                    map.addOverlay(new GMarker(opt));

                    // if you don't want to use any animation and just add a marker
                    // the following line would be sufficient:
//                    map.addOverlay(new GMarker(new GMarkerOptions(map, latLng)));
                 }
            }
        });
    }
}