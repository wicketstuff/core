package wicket.contrib.examples.gmap.both;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.GControl;
import wicket.contrib.gmap3.api.GMapType;
import wicket.contrib.gmap3.event.MapTypeChangedListener;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage {

    private static final long serialVersionUID = 1L;

    private final GMap map;

    private final Label mapTypeLabel;

    public HomePage() {
        map = new GMap( "panel" );
        map.addControl( GControl.GMapTypeControl );
        add( map );
        map.add( new MapTypeChangedListener() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onMapTypeChanged( AjaxRequestTarget target ) {
                target.addComponent( mapTypeLabel );
            }
        } );
        mapTypeLabel = new Label( "switchLabel", new PropertyModel<GMapType>( map, "mapType" ) );
        mapTypeLabel.add( map.new SetMapTypeBehavior( "onclick", GMapType.HYBRID ) );
        mapTypeLabel.setOutputMarkupId( true );
        add( mapTypeLabel );
    }
}
