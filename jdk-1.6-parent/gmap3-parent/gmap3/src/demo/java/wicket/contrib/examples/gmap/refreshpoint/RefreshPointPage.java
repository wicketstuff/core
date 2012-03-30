package wicket.contrib.examples.gmap.refreshpoint;

import java.util.Collections;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.time.Duration;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.GMarker;
import wicket.contrib.gmap3.api.GMarkerOptions;
import wicket.contrib.gmap3.api.GOverlay;
import wicket.contrib.gmap3.api.GSize;
import wicket.contrib.gmap3.api.LatLng;
import wicket.contrib.gmap3.api.MarkerImage;

/**
 * SimplePage for the wicket-contrib-gmap2 project
 */
public class RefreshPointPage extends WicketExamplePage {

    private static final long serialVersionUID = 1L;

    private final GMap _map;

    public RefreshPointPage() {
        _map = new GMap( "map" );
        add( _map );

        GOverlay overlay = createOverlay( "Amsterdam", new LatLng( 52.37649, 4.888573 ), "image.gif", "shadow.png" );

        _map.addOverlay( overlay );

        _map.add( new GMapAutoUpdatingBehavior( Duration.seconds( 5 ) ) {
            private static final long serialVersionUID = 1L;

            private int i = 1;

            @Override
            protected void onTimer( AjaxRequestTarget target, GMap map ) {
                GOverlay newOverlay;
                if ( i % 3 == 0 ) {
                    newOverlay = createOverlay( "Amsterdam", new LatLng( 52.37649, 4.888573 ), "image.gif", "shadow.png" );
                    i = 0;
                } else if ( i % 3 == 1 ) {
                    newOverlay = createOverlay( "Amsterdam", new LatLng( 52.37649, 4.888573 ), "image2.gif", "shadow2.png" );
                } else {
                    newOverlay = createOverlay( "Toulouse", new LatLng( 43.604363, 1.442951 ), "image2.gif", "shadow2.png" );
                }
                i++;
                map.setOverlays( Collections.singletonList( newOverlay ) );
            }
        } );
    }

    private GOverlay createOverlay( String title, LatLng latLng, String image, String shadow ) {

        MarkerImage icon =
                new MarkerImage( urlFor( new ResourceReference( RefreshPointPage.class, image ) ).toString() ).setScaledSize(
                        new GSize( 64, 64 ) ).setSize( new GSize( 64, 64 ) );
        MarkerImage shadowIcon =
                new MarkerImage( urlFor( new ResourceReference( RefreshPointPage.class, shadow ) ).toString() ).setScaledSize(
                        new GSize( 64, 64 ) ).setSize( new GSize( 64, 64 ) );

        _map.setCenter( latLng );
        return new GMarker( new GMarkerOptions( _map, latLng, title, icon, shadowIcon ) );
    }
}