package wicket.contrib.examples.gmap.listen.overlay.simple;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.GControl;
import wicket.contrib.gmap3.api.GEvent;
import wicket.contrib.gmap3.api.GEventHandler;
import wicket.contrib.gmap3.api.GMarker;
import wicket.contrib.gmap3.api.GMarkerOptions;
import wicket.contrib.gmap3.api.LatLng;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage {

    private static final long serialVersionUID = 1L;

    public HomePage() {
        final GMap topMap = new GMap( "topPanel" );
        topMap.addControl( GControl.GLargeMapControl );
        add( topMap );

        GMarkerOptions options = new GMarkerOptions( topMap, topMap.getCenter() ).draggable( true );
        final GMarker marker = new GMarker( options );
        final Label label = new Label( "label", new PropertyModel<LatLng>( marker, "latLng" ) );
        label.setOutputMarkupId( true );
        add( label );
        marker.addListener( GEvent.dragend, new GEventHandler() {
            private static final long serialVersionUID = 1L;

            @Override
            public void onEvent( AjaxRequestTarget target ) {
                target.addComponent( label );
            }
        } );
        topMap.addOverlay( marker );
    }
}
