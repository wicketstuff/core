package org.wicketstuff.openlayers3.examples;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;
import org.wicketstuff.openlayers3.DefaultOpenLayersMap;
import org.wicketstuff.openlayers3.OpenLayersMap;
import org.wicketstuff.openlayers3.api.Map;
import org.wicketstuff.openlayers3.api.View;
import org.wicketstuff.openlayers3.api.coordinate.LongLat;
import org.wicketstuff.openlayers3.api.format.GeoJsonFormat;
import org.wicketstuff.openlayers3.api.layer.Layer;
import org.wicketstuff.openlayers3.api.layer.Tile;
import org.wicketstuff.openlayers3.api.layer.Vector;
import org.wicketstuff.openlayers3.api.overlay.Overlay;
import org.wicketstuff.openlayers3.api.proj.Projection;
import org.wicketstuff.openlayers3.api.source.Cluster;
import org.wicketstuff.openlayers3.api.source.Osm;
import org.wicketstuff.openlayers3.api.source.ServerVector;
import org.wicketstuff.openlayers3.api.source.loader.DefaultGeoJsonLoader;
import org.wicketstuff.openlayers3.api.style.*;
import org.wicketstuff.openlayers3.api.util.Color;
import org.wicketstuff.openlayers3.behavior.ClickHandler;
import org.wicketstuff.openlayers3.component.MarkerPopover;
import org.wicketstuff.openlayers3.component.PopoverPanel;
import org.wicketstuff.openlayers3.examples.base.BasePage;

import java.util.Arrays;

/**
 * Provides a page demonstrating clustered data.
 */
@MountPath("/cluster")
public class ClusterPage extends BasePage {

    private static Logger logger = LoggerFactory.getLogger(WfsPage.class);

    /**
     * Popover for providing detail on markers.
     */
    private PopoverPanel popoverPanel;

    /**
     * Map instance.
     */
    private OpenLayersMap map;

    /**
     * Marker over Miles' office.
     */
    private MarkerPopover markerPopover;

    /**
     * Layer with our clusters of features.
     */
    private Vector cluster;

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
                Model.of(new Color("#8b0000")),
                popoverPanel,
                Model.of("Miles' Office"),
                Model.of("<p>This is where Miles' labors away on his code</p>"),
                Model.of(longLat)));

        // create our map
        map = new DefaultOpenLayersMap("map",

                // create the model for our map
                Model.of(new Map(

                        // list of layers
                        Arrays.<Layer>asList(

                                // a new tile layer with the map of the world
                                new Tile("Streets",

                                        // a OpenStreetMaps tile source
                                        new Osm()),

                                // add our vector layer for the clusters
                                cluster = new Vector(

                                        // our cluster data source
                                        new Cluster(40,

                                                // vector data source for calculating clusters
                                                new ServerVector(new GeoJsonFormat(),
                                                        new DefaultGeoJsonLoader(
                                                                "http://mhc-macris.net:8080/geoserver/MHC/ows?"
                                                                        + "service=WFS"
                                                                        + "&version=1.0.0&request=GetFeature"
                                                                        + "&typeName=MHC:in_pts&outputFormat=json",
                                                                "EPSG:3857"),

                                                        // projection for our data source
                                                        new Projection("EPSG:3857", "degrees", "nue"))),

                                        // style for our cluster layer
                                        new ClusterStyle(

                                                // the circle that we'll draw for clusters
                                                new Circle(new Fill("#3399cc"), 10, new Stroke("#ffffff")),

                                                // the text that we'll draw inside the circle
                                                new Text(null, new Fill("#ffffff"))))),

                        // list of overlays
                        Arrays.<Overlay>asList(

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

                                // center the map on Miles' office
                                longLat,

                                // zoom level for the view
                                16))));

        map.add(new ClickHandler() {

            @Override
            public void handleClick(AjaxRequestTarget target, LongLat longLat) {

                popoverPanel.hide(target);
            }
        });

        add(map);
    }
}
