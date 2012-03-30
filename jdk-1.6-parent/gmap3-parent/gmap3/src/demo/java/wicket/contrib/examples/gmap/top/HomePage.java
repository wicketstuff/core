package wicket.contrib.examples.gmap.top;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.GControl;
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

    private final FeedbackPanel feedback;

    private final Label markerLabel;

    public HomePage() {
        feedback = new FeedbackPanel( "feedback" );
        feedback.setOutputMarkupId( true );
        add( feedback );

        final GMap topMap = new GMap( "topPanel" );
        topMap.setDoubleClickZoomEnabled( true );
        topMap.add( new ClickListener() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onClick( AjaxRequestTarget target, LatLng latLng, GOverlay overlay ) {
                GMarker marker = ( overlay instanceof GMarker )
                    ? (GMarker) overlay
                    : null;
                if ( marker != null ) {
                    topMap.getInfoWindow().open( topMap, marker, new HelloPanel() );
                } else if ( latLng != null ) {
                    marker = new GMarker( new GMarkerOptions( topMap, latLng ) );
                    topMap.addOverlay( marker );
                }
                markerSelected( target, marker );
            }
        } );
        topMap.setZoom( 10 );
        GMarkerOptions options = new GMarkerOptions( topMap, new LatLng( 37.4, -122.1 ), "Home" ).draggable( true ).autoPan( true );
        topMap.addOverlay( new GMarker( options ) );
        topMap.addControl( GControl.GLargeMapControl );
        topMap.addControl( GControl.GMapTypeControl );
        add( topMap );

        final IModel<GMarker> markerModel = new Model<GMarker>( null );
        markerLabel = new Label( "markerLabel", markerModel );
        markerLabel.add( new AjaxEventBehavior( "onclick" ) {
            private static final long serialVersionUID = 1L;

            /**
             * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
             */
            @Override
            protected void onEvent( AjaxRequestTarget target ) {
                GMarker marker = markerModel.getObject();
                if ( marker != null ) {
                    LatLng point = marker.getLatLng();

                    GMarker random =
                            new GMarker( new GMarkerOptions( topMap, new LatLng(
                                    point.getLat() * ( 0.9995 + Math.random() / 1000 ), point.getLng()
                                            * ( 0.9995 + Math.random() / 1000 ) ) ) );

                    topMap.addOverlay( random );
                }
            }
        } );
        add( markerLabel );

        add( new Link<Object>( "reload" ) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
            }
        } );
    }

    private void markerSelected( AjaxRequestTarget target, GMarker marker ) {
        markerLabel.setDefaultModelObject( marker );
        target.addComponent( markerLabel );
    }
}