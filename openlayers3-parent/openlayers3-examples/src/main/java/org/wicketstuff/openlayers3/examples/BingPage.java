package org.wicketstuff.openlayers3.examples;

import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;
import org.wicketstuff.openlayers3.DefaultOpenLayersMap;
import org.wicketstuff.openlayers3.api.Map;
import org.wicketstuff.openlayers3.api.View;
import org.wicketstuff.openlayers3.api.coordinate.LongLat;
import org.wicketstuff.openlayers3.api.layer.Layer;
import org.wicketstuff.openlayers3.api.layer.Tile;
import org.wicketstuff.openlayers3.api.source.BingMaps;
import org.wicketstuff.openlayers3.examples.base.BasePage;

import java.util.Arrays;

/**
 * Provides a page with a map that uses Bing tiles.
 */
@MountPath("bing")
public class BingPage extends BasePage {

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
                                new Tile("Hybrid",

                                        // a new web map service tile layer
                                        new BingMaps("ApKGY_6eT29ygwiAHgAggG-s7mPuZW2zwS0sr1igUwHFJa2PK7xqnYA7aBUgSFjf",
                                                BingMaps.ImagerySet.AerialWithLabels))),

                        // view for this map
                        new View(

                                // coordinate of Miles' office
                                new LongLat(-72.638429, 42.313229, "EPSG:4326").transform(View.DEFAULT_PROJECTION),

                                // zoom level for the view
                                16,

                                // maximum zoom level for the view
                                19)))));
    }
}
