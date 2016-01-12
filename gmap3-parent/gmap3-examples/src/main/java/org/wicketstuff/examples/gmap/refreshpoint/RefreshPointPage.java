package org.wicketstuff.examples.gmap.refreshpoint;

import java.util.Collections;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.time.Duration;
import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GIcon;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GMarker;
import org.wicketstuff.gmap.api.GMarkerOptions;
import org.wicketstuff.gmap.api.GOverlay;
import org.wicketstuff.gmap.api.GSize;

/**
 * Demonstrates how to update overlays periodically.
 */
public class RefreshPointPage extends WicketExamplePage
{

    private static final long serialVersionUID = 1L;
    private final GMap map;
    private boolean updateOverlays = true;
    private String buttonCaption;

    public RefreshPointPage()
    {
        map = new GMap("map");
        add(map);

        GOverlay overlay = createOverlay("Amsterdam", new GLatLng(52.37649, 4.888573), "pin.gif");

        map.addOverlay(overlay);

        map.add(new AbstractAjaxTimerBehavior(Duration.seconds(5))
        {
            private static final long serialVersionUID = 1L;
            private int i = 1;

            @Override
            protected void onTimer(AjaxRequestTarget target)
            {
                if (updateOverlays)
                {
                    GOverlay newOverlay;
                    if (i % 3 == 0)
                    {
                        newOverlay = createOverlay("Amsterdam", new GLatLng(52.37649, 4.888573), "pin.gif");
                        i = 0;
                    }
                    else if (i % 3 == 1)
                    {
                        newOverlay = createOverlay("Amsterdam", new GLatLng(52.37649, 4.888573), "pin2.gif");
                    }
                    else
                    {
                        newOverlay = createOverlay("Toulouse", new GLatLng(43.604363, 1.442951), "pin2.gif");
                    }
                    i++;
                    map.setOverlays(Collections.singletonList(newOverlay));
                }
            }
        });

        buttonCaption = "Updating: " + updateOverlays + " (Click to toggle)";
        Model<String> caption = new Model<String>()
        {
            @Override
            public String getObject()
            {
                return buttonCaption;
            }
        };

        AjaxLink toggleLink = new AjaxLink("toggleUpdateLink", caption)
        {
            @Override
            public void onClick(AjaxRequestTarget target)
            {
                updateOverlays = !updateOverlays;
                buttonCaption = "Updating: " + updateOverlays + " (Click to toggle)";
                target.add(this);
            }
        };
        toggleLink.add(new Label("caption", caption));
        add(toggleLink);
    }

    private GOverlay createOverlay(String title, GLatLng latLng, String image)
    {
        GIcon icon =
                new GIcon("../" + image).setScaledSize(
                new GSize(64, 64)).setSize(new GSize(64, 64));
        map.setCenter(latLng);
        return new GMarker(new GMarkerOptions(map, latLng, title, icon));
    }
}