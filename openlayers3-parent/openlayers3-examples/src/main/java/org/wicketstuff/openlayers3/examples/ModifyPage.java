package org.wicketstuff.openlayers3.examples;

import com.google.gson.JsonObject;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;
import org.wicketstuff.openlayers3.DefaultOpenLayersMap;
import org.wicketstuff.openlayers3.OpenLayersMap;
import org.wicketstuff.openlayers3.api.Feature;
import org.wicketstuff.openlayers3.api.Map;
import org.wicketstuff.openlayers3.api.PersistentFeature;
import org.wicketstuff.openlayers3.api.View;
import org.wicketstuff.openlayers3.api.coordinate.LongLat;
import org.wicketstuff.openlayers3.api.geometry.Point;
import org.wicketstuff.openlayers3.api.interaction.Interaction;
import org.wicketstuff.openlayers3.api.interaction.Modify;
import org.wicketstuff.openlayers3.api.layer.Layer;
import org.wicketstuff.openlayers3.api.layer.Tile;
import org.wicketstuff.openlayers3.api.layer.Vector;
import org.wicketstuff.openlayers3.api.source.tile.Osm;
import org.wicketstuff.openlayers3.api.source.vector.VectorSource;
import org.wicketstuff.openlayers3.api.style.Icon;
import org.wicketstuff.openlayers3.api.style.Style;
import org.wicketstuff.openlayers3.api.util.CorsPolicy;
import org.wicketstuff.openlayers3.behavior.FeatureChangeListener;
import org.wicketstuff.openlayers3.component.Marker;
import org.wicketstuff.openlayers3.examples.base.BasePage;

import java.util.Arrays;

/**
 * Provides a page with a mpa that includes a marker.
 */
@MountPath("/modify")
public class ModifyPage extends BasePage {

    private final static Logger logger = LoggerFactory.getLogger(MarkerPage.class);

    /**
     * Marker over Miles' office.
     */
    private Marker marker;

    /**
     * Map in view.
     */
    private OpenLayersMap map;

    /**
     * Label with the marker's location.
     */
    private Label featureLocationLabel;

    /**
     * Model with the features location.
     */
    private IModel<LongLat> featureLocationModel;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        // model with our features location
        featureLocationModel = Model.of(new LongLat(-72.638382, 42.313181, "EPSG:4326"));

        // style for markers
        Style style = new Style(new Icon("http://api.tiles.mapbox.com/mapbox.js/v2.0.1/images/marker-icon.png")
                .crossOrigin(CorsPolicy.ANONYMOUS));

        // feature for the site
        Feature feature = new PersistentFeature(
                new Point(featureLocationModel.getObject().transform(View.DEFAULT_PROJECTION)),
                "Miles Office");
        feature.setStyle(style);

        // model for our features
        Vector vectorFeatures = new Vector(new VectorSource(Arrays.asList(feature)));

        // label with our feature location
        add(featureLocationLabel = new Label("featureLocationLabel", new LoadableDetachableModel<String>() {
            @Override
            protected String load() {

                StringBuilder builder = new StringBuilder();

                if(featureLocationModel.getObject() != null) {
                    builder.append(featureLocationModel.getObject().getX());
                    builder.append(", ");
                    builder.append(featureLocationModel.getObject().getY());
                } else {
                    builder.append("Unknown...");
                }

                return builder.toString();
            }
        }));
        featureLocationLabel.setOutputMarkupId(true);

        // create and add our marker
        add(map = new DefaultOpenLayersMap("map",

                // create the model for our map
                Model.of(new Map(

                        // list of layers
                        Arrays.<Layer>asList(

                                // a new tile layer with the map of the world
                                new Tile("Streets",

                                        // a new web map service tile layer
                                        new Osm()),

                                // vector of features
                                vectorFeatures),

                        // view for this map
                        new View(

                                // coordinate of Miles' office
                                new LongLat(-72.638382, 42.313181, "EPSG:4326").transform(View.DEFAULT_PROJECTION),

                                // zoom level for the view
                                16))
                        .interactions(Arrays.<Interaction>asList(new Modify(vectorFeatures))))));

        map.add(new FeatureChangeListener(feature) {

            @Override
            public void handleChange(AjaxRequestTarget target, String featureId, LongLat longLat, JsonObject properties) {

                featureLocationModel.setObject(longLat);
                target.add(featureLocationLabel);
            }
        });
    }
}
