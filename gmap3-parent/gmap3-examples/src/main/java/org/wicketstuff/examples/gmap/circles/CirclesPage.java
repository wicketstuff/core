package org.wicketstuff.examples.gmap.circles;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.util.string.*;
import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GCircle;
import org.wicketstuff.gmap.api.GEvent;
import org.wicketstuff.gmap.api.GEventHandler;
import org.wicketstuff.gmap.api.GLatLng;

/**
 * Demonstrates the usage of circles.
 */
public class CirclesPage extends WicketExamplePage
{
    public CirclesPage()
    {
        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);
        feedback.setOutputMarkupId(true);

        GMap map = new GMap("topPanel");
        GCircle circle = new GCircle(new GLatLng(37.35, -121.9), 10000, "#000000", 4, 0.7f, "#E9601A", 0.7f);
        circle.setEditable(true);
        circle.setDraggable(true);
        GEventHandler eventHandler = new GEventHandler() {
            @Override
            public void onEvent(AjaxRequestTarget target) {
                WebRequest request = (WebRequest) getRequest();
                StringValue radius = request.getRequestParameters().getParameterValue("overlay.radius");
                StringValue center = request.getRequestParameters().getParameterValue("overlay.latLng");
                feedback.success("Radius (in meters): " + radius);
                feedback.success("Coordinates: " + center);
                target.add(feedback);
            }
        };
        circle.addListener(GEvent.radius_changed, eventHandler);
        circle.addListener(GEvent.center_changed, eventHandler);
        map.addOverlay(circle);
        map.setZoom(9);
        add(map);
    }
}
