package wicket.contrib.examples.gmap.custompoint;

import org.apache.wicket.ResourceReference;

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
public class CustomPointPage extends WicketExamplePage {

    private static final long serialVersionUID = 1L;

    public CustomPointPage() {
        GMap map = new GMap( "map" );
        map.setCenter( new LatLng( 52.37649, 4.888573 ) );
        add( map );

        MarkerImage icon =
                new MarkerImage( urlFor( new ResourceReference( CustomPointPage.class, "image.gif" ) ).toString() ).setScaledSize(
                        new GSize( 64, 64 ) ).setSize( new GSize( 64, 64 ) );
        MarkerImage shadow =
                new MarkerImage( urlFor( new ResourceReference( CustomPointPage.class, "shadow.png" ) ).toString() ).setScaledSize(
                        new GSize( 64, 64 ) ).setSize( new GSize( 64, 64 ) );
        GOverlay marker = new GMarker( new GMarkerOptions( map, new LatLng( 52.37649, 4.888573 ), "My Title", icon, shadow ) );

        map.addOverlay( marker );
    }
}