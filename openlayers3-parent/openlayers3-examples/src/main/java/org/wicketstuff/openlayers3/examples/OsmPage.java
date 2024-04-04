package org.wicketstuff.openlayers3.examples;

import java.util.Arrays;

import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;
import org.wicketstuff.openlayers3.DefaultOpenLayersMap;
import org.wicketstuff.openlayers3.api.Extent;
import org.wicketstuff.openlayers3.api.Map;
import org.wicketstuff.openlayers3.api.View;
import org.wicketstuff.openlayers3.api.coordinate.LongLat;
import org.wicketstuff.openlayers3.api.layer.Layer;
import org.wicketstuff.openlayers3.api.layer.Tile;
import org.wicketstuff.openlayers3.api.source.tile.Osm;
import org.wicketstuff.openlayers3.examples.base.BasePage;

/**
 * Provides a page with a map that uses OpenStreetMap tiles.
 */
@MountPath("openstreetmap")
public class OsmPage extends BasePage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        // create and add our map
        add(new DefaultOpenLayersMap("map",

                // create the model for our map
                Model.of(new Map(

                        // list of layers
                        Arrays.<Layer>asList(

                                // a new tile layer with the map of the world
                                new Tile("Streets",

                                        // a new web map service tile layer
                                        new Osm())),

                        // view for this map
                        new View(

                                // coordinate of Miles' office
                                new LongLat(-72.638429, 42.313229, "EPSG:4326").transform(View.DEFAULT_PROJECTION),

                                // zoom level for the view
                                16).extent(new Extent().minimum(new LongLat(-73.574935, 42.776520, "EPSG:4326"))
									.maximum(new LongLat(-69.754439, 41.398720, "EPSG:4326")))))));
    }
}
