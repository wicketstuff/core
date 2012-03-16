package wicket.contrib.examples.gmap.polygones;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.GPolygon;
import wicket.contrib.gmap3.api.GPolyline;
import wicket.contrib.gmap3.api.LatLng;

/**
 * SimplePage for the wicket-contrib-gmap2 project
 */
public class PolygonesPage extends WicketExamplePage {

    private static final long serialVersionUID = 1L;

    public PolygonesPage() {
        GMap map = new GMap( "topPanel" );
        map.addOverlay( new GPolygon( "#000000", 4, 0.7f, "#E9601A", 0.7f, new LatLng( 37.3, -122.4 ), new LatLng( 37.2, -122.2 ),
                new LatLng( 37.3, -122.0 ), new LatLng( 37.4, -122.2 ), new LatLng( 37.3, -122.4 ) ) );
        map.addOverlay( new GPolyline( "#FFFFFF", 8, 1.0f, new LatLng( 37.35, -122.3 ), new LatLng( 37.25, -122.25 ), new LatLng(
                37.3, -122.2 ), new LatLng( 37.25, -122.15 ), new LatLng( 37.35, -122.1 ) ) );
        map.setZoom( 10 );
        add( map );
    }
}
