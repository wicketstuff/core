package wicket.contrib.examples.gmap.geocode;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.wicket.util.io.Streams;

import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.util.Geocoder;
import wicket.contrib.gmap.util.GeocoderException;

/**
 * A serverside Geocoder.
 */
public class ServerGeocoder {

	private Geocoder geocoder;

	/**
	 * @param gMapKey
	 *            Gmap API key
	 * @throws IllegalArgumentException
	 *             If the API key is <code>null</code>
	 */
	public ServerGeocoder(String gMapKey) {
		if (gMapKey == null) {
			throw new IllegalArgumentException("API key cannot be null");
		}

		this.geocoder = new Geocoder(gMapKey);
	}

	/**
	 * @param address
	 *            The address for which a coordinate must be found.
	 * @return GLatLng point for the address found by the Geocoder
	 * @throws GeocoderException
	 *             If a error happened on the side of Google
	 * @throws IOException
	 *             If a connection error happened
	 */
	public GLatLng findAddress(String address) throws IOException {

		URL url = new URL(geocoder.encode(address));

		URLConnection connection = url.openConnection();

		String content = Streams.readString(connection.getInputStream());

		return geocoder.decode(content);
	}
}
