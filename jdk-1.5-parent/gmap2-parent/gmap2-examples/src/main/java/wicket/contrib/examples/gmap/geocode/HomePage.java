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
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.GMapHeaderContributor;
import wicket.contrib.gmap.api.GClientGeocoder;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GInfoWindowTab;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMapType;
import wicket.contrib.gmap.util.GeocoderException;

/**
 * Example HomePage for the wicket-contrib-gmap2 project
 */
public class HomePage extends WicketExamplePage
{

	private static final long serialVersionUID = 1L;

	private final FeedbackPanel feedback;


	public HomePage()
	{
		feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		add(feedback);

		final GMap2 bottomMap = new GMap2("bottomPanel", new GMapHeaderContributor(
				GMapExampleApplication.get().getGoogleMapsAPIkey()));
		bottomMap.setOutputMarkupId(true);
		bottomMap.setMapType(GMapType.G_SATELLITE_MAP);
		bottomMap.addControl(GControl.GSmallMapControl);
		add(bottomMap);

		Form<Object> geocodeForm = new Form<Object>("geocoder");
		add(geocodeForm);

		final TextField<String> addressTextField = new TextField<String>("address",
				new Model<String>(""));
		geocodeForm.add(addressTextField);

		Button button = new Button("client");
		// Using GClientGeocoder the geocoding request
		// is performed on the client using JavaScript
		button.add(new GClientGeocoder("onclick", addressTextField, GMapExampleApplication.get()
				.getGoogleMapsAPIkey())
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onGeoCode(AjaxRequestTarget target, int status, String address,
					GLatLng latLng)
			{
				if (status == GeocoderException.G_GEO_SUCCESS)
				{
					bottomMap.getInfoWindow().open(latLng,
							new GInfoWindowTab(address, new Label(address, address)));
				}
				else
				{
					error("Unable to geocode (" + status + ")");
					target.addComponent(feedback);
				}
			};
		});
		geocodeForm.add(button);

		// Using ServerGeocoder the geocoding request
		// is performed on the server using Googles HTTP interface.
		// http://www.google.com/apis/maps/documentation/services.html#Geocoding_Direct
		geocodeForm.add(new AjaxButton("server", geocodeForm)
		{

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				try
				{
					String address = addressTextField.getDefaultModelObjectAsString();

					GLatLng latLng = GeoCodeGMapApplication.get().getServerGeocoder().findAddress(address);

					bottomMap.getInfoWindow().open(latLng,
							new GInfoWindowTab(address, new Label(address, address)));
				}
				catch (IOException e)
				{
					target.appendJavascript("Unable to geocode (" + e.getMessage() + ")");
				}
			}

			/**
			 * @see org.apache.wicket.ajax.markup.html.form.AjaxButton#onError(org.apache.wicket.ajax.AjaxRequestTarget, org.apache.wicket.markup.html.form.Form)
			 */
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.appendJavascript("Unable to geocode (ajax button onError)");
			}
			
		});
	}
}
