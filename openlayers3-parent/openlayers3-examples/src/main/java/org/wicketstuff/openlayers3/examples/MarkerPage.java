package org.wicketstuff.openlayers3.examples;

import java.util.Arrays;
import java.util.Collection;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.wicketstuff.annotation.mount.MountPath;
import org.wicketstuff.openlayers3.DefaultOpenLayersMap;
import org.wicketstuff.openlayers3.api.Map;
import org.wicketstuff.openlayers3.api.View;
import org.wicketstuff.openlayers3.api.coordinate.LongLat;
import org.wicketstuff.openlayers3.api.layer.Layer;
import org.wicketstuff.openlayers3.api.layer.Tile;
import org.wicketstuff.openlayers3.api.overlay.Overlay;
import org.wicketstuff.openlayers3.api.source.tile.Osm;
import org.wicketstuff.openlayers3.api.source.tile.TileArcGISRest;
import org.wicketstuff.openlayers3.api.source.tile.XYZ;
import org.wicketstuff.openlayers3.api.util.Color;
import org.wicketstuff.openlayers3.component.Marker;
import org.wicketstuff.openlayers3.examples.base.BasePage;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.select.BootstrapSelect;

/**
 * Provides a page with a mpa that includes a marker.
 */
@MountPath("/marker")
public class MarkerPage extends BasePage {

    private static final String MA_ORTHO_URL = "http://tiles.arcgis.com/tiles/hGdibHYSPO59RG1h/arcgis/rest/services/USGS_Orthos_2013_2014/MapServer/tile/{z}/{y}/{x}";
    private static final String MA_OPEN_SPACE_URL = "http://gisprpxy.itd.state.ma.us/arcgisserver/rest/services/AGOL/OpenSpaceLevProt/MapServer";

    private enum LayerOption {
        STREET,
        SATELLITE,
        OPEN_SPACE;
    }

    private static class LayerSelectedModel extends LoadableDetachableModel<Boolean> {

        private IModel<LayerOption> model;
        private Collection<LayerOption> layerOption;

        public LayerSelectedModel(IModel<LayerOption> model, LayerOption... layerOption) {
            this.model = model;
            this.layerOption = Arrays.asList(layerOption);
        }

        @Override
        protected Boolean load() {
            return layerOption.contains(model.getObject());
        }

    }

    public MarkerPage() {
        super();

        // model of the selected layer
        final IModel<LayerOption> model = Model.of(LayerOption.STREET);

        // create and add our marker over Miles' office
        Marker marker = new Marker("marker", Model.of(new Color("#4169E1")));
        add(marker);

        // create and add our marker
        final DefaultOpenLayersMap map = new DefaultOpenLayersMap("map",

                // create the model for our map
                Model.of(new Map(

                        // list of layers
                        Arrays.<Layer>asList(

                                // a new tile layer with the street map of Noho
                                new Tile("Streets",

                                        // a new web map service tile layer
                                        new Osm(),
                                        // visible when the layer selector is street
                                        new LayerSelectedModel(model, LayerOption.STREET)),

                                // a new tile layer with the satellite map of Noho
                                new Tile("Ortho",

                                        // MA ortho-imagery layer
                                        new XYZ().setUrl(MA_ORTHO_URL),
                                        // visible when the layer selector is satellite or open space
                                        new LayerSelectedModel(model, LayerOption.OPEN_SPACE, LayerOption.SATELLITE)),

                                // a new tile layer with the open space map of Noho
                                new Tile("Open Space",

                                        // MA open space layer
                                        new TileArcGISRest().setUrl(MA_OPEN_SPACE_URL),
                                        // visible when the layer selector is open space
                                        new LayerSelectedModel(model, LayerOption.OPEN_SPACE))
                                    // fractional opacity so we can see the base map underneath
                                    .setOpacityModel(Model.of(.5))),

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
                                16))));
        add(map);

        // form for changing the layer
        Form<LayerOption> form = new BootstrapForm<>("form", model);
        add(form);

        // layer selector -- refresh the map's layers on change
        form.add(new FormGroup("layer")
                .add(new BootstrapSelect<>("layerSelector", model,
                        new ListModel<>(Arrays.asList(LayerOption.values())),
                        new EnumChoiceRenderer<LayerOption>())
                    .setLabel(Model.of("Layer"))
                    .add(new AjaxFormComponentUpdatingBehavior("change") {

                        @Override
                        protected void onUpdate(AjaxRequestTarget target) {
                            map.updateLayers(target);
                        }

                    })));

    }

}
