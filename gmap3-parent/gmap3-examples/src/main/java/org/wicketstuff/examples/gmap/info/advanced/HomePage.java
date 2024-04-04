package org.wicketstuff.examples.gmap.info.advanced;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GEvent;
import org.wicketstuff.gmap.api.GEventHandler;
import org.wicketstuff.gmap.api.GInfoWindow;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GMapType;
import org.wicketstuff.gmap.event.ClickListener;

/**
 * Demonstrates the usage of InfoWindows.
 */
public class HomePage extends WicketExamplePage
{

    private static final long serialVersionUID = 1L;
    private final FeedbackPanel feedback;
    private final GMap map;
    private final Label lbInfoWindow;
    private GInfoWindow infoWindow;
    private WebMarkupContainer hiddenContainersInfoPanel;
    private static final String INFOPANEL = "infoPanel";
    private static int i = 0;

    public HomePage()
    {
        feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        add(feedback);
        add(hiddenContainersInfoPanel = new WebMarkupContainer("hiddenContainer"));
        hiddenContainersInfoPanel.setOutputMarkupId(true);
        hiddenContainersInfoPanel.add(new WebMarkupContainer(INFOPANEL));

        final GEventHandler closeClickHandler = new GEventHandler()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void onEvent(AjaxRequestTarget target)
            {
                info("InfoWindow " + infoWindow.getId() + " was closed");
                target.add(feedback);
            }
        };

        map = new GMap("bottomPanel", WicketExamplePage.API_KEY);
        map.setOutputMarkupId(true);
        map.setMapType(GMapType.SATELLITE);
        map.setScrollWheelZoomEnabled(true);
        map.add(new ClickListener()
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onClick(AjaxRequestTarget target, GLatLng gLatLng)
            {
                if (gLatLng != null)
                {
                    if (infoWindow != null)
                    {
                        infoWindow.close();
                    }
                    //Create the infoPanel
                    Component c = new InfoPanel(INFOPANEL, i);
                    i++;
                    c.setOutputMarkupId(true);

                    //Add or replace it on the hiddenContainer
                    hiddenContainersInfoPanel.addOrReplace(c);

                    infoWindow = new GInfoWindow(gLatLng, c);
                    map.addOverlay(infoWindow);
                    feedback.info("InfoWindow " + infoWindow.getId() + " was added");
                    target.add(feedback);

                    //add the hiddenContainer to be repainted
                    target.add(hiddenContainersInfoPanel);
                    // IMPORTANT: you must have the InfoWindow already added to the map
                    // before you can add any listeners
                    infoWindow.addListener(GEvent.closeclick, closeClickHandler);


                }
            }
        });

        add(map);

        lbInfoWindow = new Label("infoWindow", "openInfoWindow");
        lbInfoWindow.add(new AjaxEventBehavior("click")
        {
            private static final long serialVersionUID = 1L;

            /**
             * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
             */
            @Override
            protected void onEvent(AjaxRequestTarget target)
            {
                GInfoWindow tmpInfoWindow = new GInfoWindow(new GLatLng(37.5 * (0.9995 + Math.random() / 1000), -122.1 * (0.9995 + Math.random() / 1000)), "Opened via button");
                map.addOverlay(tmpInfoWindow);
                // IMPORTANT: you must have the InfoWindow already added to the map
                // before you can add any listeners
                GEventHandler closeClickHandler = new GEventHandler()
                {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onEvent(AjaxRequestTarget target)
                    {
                        feedback.info("InfoWindow which was opened via a button was closed");
                        target.add(feedback);
                    }
                };
                tmpInfoWindow.addListener(GEvent.closeclick, closeClickHandler);
                feedback.info("InfoWindow was opened via button");
                target.add(feedback);
            }
        });
        add(lbInfoWindow);
    }
}
