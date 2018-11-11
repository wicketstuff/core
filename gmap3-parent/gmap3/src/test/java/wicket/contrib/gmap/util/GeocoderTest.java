package wicket.contrib.gmap.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.geocoder.Geocoder;

public class GeocoderTest
{
	private static final String PROP_NAME = "wicketstuff.gmap3.apiKey";
	public static final String DEFAULT_API_KEY = "YOUR_API_KEY";
	private String apiKey = DEFAULT_API_KEY;

	public static String getApiKey() {
		String key = System.getenv(PROP_NAME);
		if (key != null) {
			return key;
		}
		key = System.getProperty(PROP_NAME);
		if (key != null) {
			return key;
		}
		return DEFAULT_API_KEY;
	}

	@BeforeEach
	public void setUp()
	{
		apiKey = getApiKey();
	}

	@Test
	public void testEncode()
	{
		Geocoder coder = new Geocoder(apiKey);
		String encode = coder.encode("Salzburgerstraße 205, 4030 Linz, Österreich");
		assertEquals(new StringBuilder("https://maps.googleapis.com/maps/api/geocode/json?key=")
				.append(apiKey).append("&address=Salzburgerstra%C3%9Fe+205%2C+4030+Linz%2C+%C3%96sterreich").toString()
			, encode);
	}

	@Test
	@Disabled // Disabled for now due to too much OVER_QUERY_LIMIT errors
	public void testGeocoding() throws Exception
	{
		Geocoder coder = new Geocoder(apiKey);
		GLatLng result = coder.geocode("Salzburgerstraße 205, 4030 Linz, Österreich");
		assertNotNull(result);
		assertEquals(48.25728790, result.getLat(), 0.00001);
		assertEquals(14.29231840, result.getLng(), 0.00001);
	}

	/**
	 * Integration test for loading geocoder information<br/>
	 * from google geocoder service and center and fit the<br/>
	 * zoom of the map
	 *
	 * @throws Exception
	 */
	@Test
	@Disabled // Disabled for now due to too much OVER_QUERY_LIMIT errors
	public void testCenterAndFitZoomForAdress() throws Exception
	{
		WicketTester tester = new WicketTester();
		GMap map = new GMap("gmap", apiKey);
		tester.startComponentInPage(map);
		Geocoder gecoder = new Geocoder(apiKey);
		gecoder.centerAndFitZoomForAdress(map, "Avignon, France");
		assertEquals(43.9966409, map.getBounds().getNE().getLat(), 0.00001);
		assertEquals(4.927226, map.getBounds().getNE().getLng(), 0.00001);
		assertEquals(43.8864731, map.getBounds().getSW().getLat(), 0.00001);
		assertEquals(4.739279, map.getBounds().getSW().getLng(), 0.00001);
		assertEquals(37.4419, map.getCenter().getLat(), 0.00001);
		assertEquals(-122.1419, map.getCenter().getLng(), 0.00001);
	}
}
