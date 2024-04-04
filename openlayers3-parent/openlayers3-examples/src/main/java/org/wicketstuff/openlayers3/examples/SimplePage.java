package org.wicketstuff.openlayers3.examples;

import java.util.Arrays;

import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;
import org.wicketstuff.openlayers3.DefaultOpenLayersMap;
import org.wicketstuff.openlayers3.api.Map;
import org.wicketstuff.openlayers3.api.View;
import org.wicketstuff.openlayers3.api.coordinate.Coordinate;
import org.wicketstuff.openlayers3.api.layer.Layer;
import org.wicketstuff.openlayers3.api.layer.Tile;
import org.wicketstuff.openlayers3.api.source.tile.Osm;
import org.wicketstuff.openlayers3.examples.base.BasePage;

/**
 * Provides a simple map with one layer.
 */
@MountPath("/simple")
public class SimplePage extends BasePage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        // create and add our mape
        add(new DefaultOpenLayersMap("map",

                // create the model for our map
                Model.of(new Map(

                        // list of layers
                        Arrays.<Layer>asList(

                                // a new tile layer with the map of the world
                                new Tile("Open Street Maps",

                                        // a new web map service tile layer
                                        new Osm())),

                        // view for this map
                        new View(new Coordinate(0, 0), 2)))));
    }
}
