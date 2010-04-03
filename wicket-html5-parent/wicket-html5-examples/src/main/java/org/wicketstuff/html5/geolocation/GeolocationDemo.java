package org.wicketstuff.html5.geolocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wicketstuff.html5.BasePage;

/**
 * See http://www.ibm.com/developerworks/web/library/wa-html5webapp/index.html
 */
public class GeolocationDemo extends BasePage {

	private static final String BASE_URL = "http://api.foursquare.com/v1/venues.json?";
	
	public GeolocationDemo() {

		final WebMarkupContainer locations = new WebMarkupContainer("locations");
		locations.setOutputMarkupId(true);
		add(locations);
		locations.add(new WebMarkupContainer("location"));

		final WebMarkupContainer geoLocator = new WebMarkupContainer(
				"geolocator");
		
		geoLocator.add(new AjaxGeolocationBehavior() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onGeoAvailable(AjaxRequestTarget target,
					String latitude, String longitude) {

				String locationsJson = proxyRequest(latitude, longitude);
				RepeatingView venues = new RepeatingView("location");
				
				try {
					JSONObject obj = new JSONObject(locationsJson);
					
					JSONArray groupsArray = obj.getJSONArray("groups");
					JSONObject groupOne = groupsArray.getJSONObject(0);
					JSONArray venuesArray = groupOne.getJSONArray("venues");
					int length = venuesArray.length();
					for (int i = 0; i < length; i++) {
						JSONObject venueObject = venuesArray.getJSONObject(i);
						StringBuilder venue = new StringBuilder();
						venue
							.append("City: ").append(venueObject.getString("city"))
							.append(", State: ").append(venueObject.getString("state"))
							.append(", near by: ").append(venueObject.getString("name"));
						
						venues.add(new Label(venues.newChildId(), venue.toString()));
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				locations.addOrReplace(venues);
				
				target.addComponent(locations);
			}
		});
		add(geoLocator);
	}

	private String proxyRequest(final String latitude, final String longitude) {

		final StringBuilder sb = new StringBuilder();
		InputStream stream = null;

		try {
			
			String urlStr = BASE_URL + "geolat=" + latitude + "&geolong=" + longitude;

			final URL url = new URL(urlStr);
			stream = url.openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream));
			String line = "";
			while (line != null) {
				line = reader.readLine();
				if (line != null) {
					sb.append(line);
				}
			}
			
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) { 
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}
}
