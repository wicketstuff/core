package wicket.contrib.examples.gmap.geocode;

import java.io.IOException;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;

import wicket.contrib.examples.GMapExampleApplication;
import wicket.contrib.examples.WicketExamplePage;
import wicket.contrib.gmap3.GMap;
import wicket.contrib.gmap3.api.GClientGeocoder;
import wicket.contrib.gmap3.api.GControl;
import wicket.contrib.gmap3.api.GInfoWindowContent;
import wicket.contrib.gmap3.api.GMapType;
import wicket.contrib.gmap3.api.LatLng;
import wicket.contrib.gmap3.util.GeocoderException;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage {

    private static final long serialVersionUID = 1L;

    private final FeedbackPanel feedback;

    private final ServerGeocoder geocoder = new ServerGeocoder( GMapExampleApplication.get().getGoogleMapsAPIkey() );

    public HomePage() {
        feedback = new FeedbackPanel( "feedback" );
        feedback.setOutputMarkupId( true );
        add( feedback );

        final GMap bottomMap = new GMap( "bottomPanel" );
        bottomMap.setOutputMarkupId( true );
        bottomMap.setMapType( GMapType.SATELLITE );
        bottomMap.addControl( GControl.GSmallMapControl );
        add( bottomMap );

        Form<Object> geocodeForm = new Form<Object>( "geocoder" );
        add( geocodeForm );

        final TextField<String> addressTextField = new TextField<String>( "address", new Model<String>( "" ) );
        geocodeForm.add( addressTextField );

        Button button = new Button( "client" );
        // Using GClientGeocoder the geocoding request
        // is performed on the client using JavaScript
        button.add( new GClientGeocoder( "onclick", addressTextField ) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onGeoCode( AjaxRequestTarget target, int status, String address, LatLng latLng ) {
                if ( status == GeocoderException.G_GEO_SUCCESS ) {
                    bottomMap.getInfoWindow().open( latLng, new GInfoWindowContent( new Label( address, address ) ) );
                } else {
                    error( "Unable to geocode (" + status + ")" );
                    target.addComponent( feedback );
                }
            }
        } );
        geocodeForm.add( button );

        // Using ServerGeocoder the geocoding request
        // is performed on the server using Googles HTTP interface.
        // http://www.google.com/apis/maps/documentation/services.html#Geocoding_Direct
        geocodeForm.add( new AjaxButton( "server", geocodeForm ) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit( AjaxRequestTarget target, Form<?> form ) {
                try {
                    String address = addressTextField.getDefaultModelObjectAsString();

                    LatLng latLng = geocoder.findAddress( address );

                    bottomMap.getInfoWindow().open( latLng, new GInfoWindowContent( new Label( address, address ) ) );
                } catch ( IOException e ) {
                    target.appendJavascript( "Unable to geocode (" + e.getMessage() + ")" );
                }
            }
        } );
    }
}
