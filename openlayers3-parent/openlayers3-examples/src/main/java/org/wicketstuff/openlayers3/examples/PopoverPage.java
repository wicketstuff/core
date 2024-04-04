package org.wicketstuff.openlayers3.examples;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;
import org.wicketstuff.openlayers3.DefaultOpenLayersMap;
import org.wicketstuff.openlayers3.OpenLayersMap;
import org.wicketstuff.openlayers3.api.Map;
import org.wicketstuff.openlayers3.api.View;
import org.wicketstuff.openlayers3.api.coordinate.LongLat;
import org.wicketstuff.openlayers3.api.layer.Layer;
import org.wicketstuff.openlayers3.api.layer.Tile;
import org.wicketstuff.openlayers3.api.overlay.Overlay;
import org.wicketstuff.openlayers3.api.source.tile.Osm;
import org.wicketstuff.openlayers3.api.util.Color;
import org.wicketstuff.openlayers3.behavior.ClickHandler;
import org.wicketstuff.openlayers3.component.MarkerPopover;
import org.wicketstuff.openlayers3.component.PopoverPanel;
import org.wicketstuff.openlayers3.examples.base.BasePage;

import java.util.Arrays;

/**
 * Provides a page demonstrating a popover.
 */
@MountPath("/popover")
public class PopoverPage extends BasePage {

    /**
     * Popover for providing detail on markers.
     */
    private PopoverPanel popoverPanel;

    /**
     * Marker over Miles' office.
     */
    private MarkerPopover markerPopover;

    /**
     * Map instance.
     */
    private OpenLayersMap map;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        // create our popover panel
        add(popoverPanel = new PopoverPanel("popover"));

        // location of Miles' office
        LongLat longLat = new LongLat(-72.638429, 42.313229, "EPSG:4326").transform(View.DEFAULT_PROJECTION);

        // add a marker that will update the content of the popover, move the popover and then make it visible when
        // the marker is clicked
        add(markerPopover = new MarkerPopover("marker",
					      Model.of(new Color("#4169E1")),
					      popoverPanel,
					      Model.of("Miles' Office"),
					      Model.of("<p>This is where Miles' labors away on his code</p>"),
					      Model.of(longLat)));

        // create and add our marker
        add(map = new DefaultOpenLayersMap("map",

                // create the model for our map
                Model.of(new Map(

                        // list of layers
                        Arrays.<Layer>asList(

                                // a new tile layer with the map of the world
                                new Tile("Streets",

                                        // a new web map service tile layer
                                        new Osm())),

                        // list of overlays
                        Arrays.asList(

                                // overlay with the popover
                                popoverPanel.getPopover(),

                                // overlay with our marker and popover
                                new Overlay(markerPopover,

                                        // position of the overlay
                                        longLat,

                                        // position of the overlay relative to the point
                                        Overlay.Positioning.BottomCenter)),

                        // view for this map
                        new View(

                                // coordinate of Miles' office
                                longLat,

                                // zoom level for the view
                                16)))));

        // click handler to hide popup when someone clicks on the map
        map.add(new ClickHandler() {

            @Override
            public void handleClick(AjaxRequestTarget target, LongLat longLat) {
                popoverPanel.hide(target);
            }
        });
    }
}
