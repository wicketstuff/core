package wicket.contrib.examples.gmap.simple;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.LatLng;

/**
 * SimplePage for the wicket-contrib-gmap3 project
 */
public class SimplePage extends WicketExamplePage {

    public SimplePage() {
        GMap map = new GMap( "map" );
        map.setScrollWheelZoomEnabled( true );
        map.setCenter( new LatLng( 52.47649, 13.228573 ) );
        add( map );
    }
}
