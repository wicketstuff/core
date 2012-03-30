package wicket.contrib.examples.gmap.marker;

import org.apache.wicket.ajax.AjaxRequestTarget;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.GMarker;
import wicket.contrib.gmap3.api.GMarkerOptions;
import wicket.contrib.gmap3.api.GOverlay;
import wicket.contrib.gmap3.api.LatLng;
import wicket.contrib.gmap3.event.ClickListener;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage {

    private static final long serialVersionUID = 1L;

    public HomePage() {
        final GMap map = new GMap( "topPanel" );
        add( map );
        map.add( new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onClick( AjaxRequestTarget target, LatLng latLng, GOverlay overlay ) {
                if ( latLng != null ) {
                    if ( map.getOverlays().size() >= 3 ) {
                        map.removeOverlay( map.getOverlays().get( 0 ) );
                    }
                    map.addOverlay( new GMarker( new GMarkerOptions( map, latLng ) ) );
                }
            }
        } );
    }
}