package wicket.contrib.examples.gmap.controls;

import org.apache.wicket.markup.html.WebMarkupContainer;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.GControl;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage {

    private static final long serialVersionUID = 1L;

    public HomePage() {
        final GMap map = new GMap( "topPanel" );
        map.addControl( GControl.GMapTypeControl );
        add( map );

        final WebMarkupContainer zoomIn = new WebMarkupContainer( "zoomIn" );
        zoomIn.add( map.new ZoomInBehavior( "onclick" ) );
        add( zoomIn );

        final WebMarkupContainer zoomOut = new WebMarkupContainer( "zoomOut" );
        zoomOut.add( map.new ZoomOutBehavior( "onclick" ) );
        add( zoomOut );
    }
}
