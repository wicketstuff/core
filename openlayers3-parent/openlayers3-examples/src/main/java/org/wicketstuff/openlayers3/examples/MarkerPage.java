package org.wicketstuff.openlayers3.examples;

import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;
import org.wicketstuff.openlayers3.DefaultOpenLayersMap;
import org.wicketstuff.openlayers3.api.Map;
import org.wicketstuff.openlayers3.api.View;
import org.wicketstuff.openlayers3.api.coordinate.LongLat;
import org.wicketstuff.openlayers3.api.layer.Layer;
import org.wicketstuff.openlayers3.api.layer.Tile;
import org.wicketstuff.openlayers3.api.overlay.Overlay;
import org.wicketstuff.openlayers3.api.source.tile.Osm;
import org.wicketstuff.openlayers3.api.util.Color;
import org.wicketstuff.openlayers3.component.Marker;
import org.wicketstuff.openlayers3.examples.base.BasePage;

import java.util.Arrays;

/**
 * Provides a page with a mpa that includes a marker.
 */
@MountPath("/marker")
public class MarkerPage extends BasePage {

    private final static Logger logger = LoggerFactory.getLogger(MarkerPage.class);

    /**
     * Marker over Miles' office.
     */
    private Marker marker;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        // create and add our marker
        add(marker = new Marker("marker", Model.of(new Color("#4169E1"))));

        // create and add our marker
        add(new DefaultOpenLayersMap("map",

                // create the model for our map
                Model.of(new Map(

                        // list of layers
                        Arrays.<Layer>asList(

                                // a new tile layer with the map of the world
                                new Tile("Streets",

                                        // a new web map service tile layer
                                        new Osm())),

                        // list of overlays
                        Arrays.<Overlay>asList(

                                // overlay with our marker
                                new Overlay(marker,

                                        // position of this overlay
                                        new LongLat(-72.638429, 42.313229, "EPSG:4326")
                                                .transform(View.DEFAULT_PROJECTION),

                                        // position of the overlay relative to the point
                                        Overlay.Positioning.BottomCenter)),

                        // view for this map
                        new View(

                                // coordinate of Miles' office
                                new LongLat(-72.638382, 42.313181, "EPSG:4326").transform(View.DEFAULT_PROJECTION),

                                // zoom level for the view
                                16)))));
    }
}
