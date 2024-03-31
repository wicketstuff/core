package org.wicketstuff.examples.gmap.both;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GMapType;
import org.wicketstuff.gmap.event.MapTypeChangedListener;

/**
 * Demonstrates the how to listen to a events fired by the map and
 * how to control the map.
 */
public class HomePage extends WicketExamplePage
{

    private static final long serialVersionUID = 1L;
    private final GMap map;
    private final Label mapTypeLabel;

    public HomePage()
    {
        map = new GMap("panel", WicketExamplePage.API_KEY);
        add(map);
        map.add(new MapTypeChangedListener()
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onMapTypeChanged(AjaxRequestTarget target)
            {
                target.add(mapTypeLabel);
            }
        });
        mapTypeLabel = new Label("switchLabel", new PropertyModel<GMapType>(map, "mapType"));
        mapTypeLabel.add(map.new SetMapTypeBehavior("onclick", GMapType.HYBRID));
        mapTypeLabel.setOutputMarkupId(true);
        add(mapTypeLabel);
    }
}
