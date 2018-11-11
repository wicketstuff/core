package wicket.contrib.gmap.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static wicket.contrib.gmap.util.GeocoderTest.DEFAULT_API_KEY;
import static wicket.contrib.gmap.util.GeocoderTest.getApiKey;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GLatLngBounds;

/**
 * Include test cases for the {@link GMap}
 *
 * @author Mathias Born <a href="http://www.mathiasborn.de">GETTING CONTACT.</a>
 */
public class GMapTest
{
	private String apiKey = DEFAULT_API_KEY;

	@BeforeEach
	public void setUp()
	{
		apiKey = getApiKey();
	}

	/**
	 * Test if the JavaScript for fitBounds() isn't rendered,<br/>
	 * if >not< bounds set on the map
	 */
	@Test
	public void testIfBoundJSIsntRenderedIfBoundsPropertyIsntSet()
	{
		WicketTester tester = new WicketTester();
		GMap map = new GMap("someId", apiKey);
		tester.startComponentInPage(map);
		assertFalse(
				map.getJSinit().contains("fitBounds(")
				, "If the property bounds is not set, it shouldn't render the JS-method"
			);
	}


	/**
	 * Test if the JavaScript for fitBounds() is rendered,<br/>
	 * if bounds set on the map
	 */
	@Test
	public void testIfBoundJSIsRenderedIfBoundsPropertyIsSet()
	{
		WicketTester tester = new WicketTester();
		GMap map = new GMap("someId", apiKey);
		map.setBounds(new GLatLngBounds(//
			new GLatLng(43.8864731, 4.739279), //
			new GLatLng(43.9966409, 4.927226)//
		));
		tester.startComponentInPage(map);
		assertTrue(
				map.getJSinit()//
					.contains(
						"fitBounds(new google.maps.LatLngBounds("
							+ "new google.maps.LatLng(43.8864731, 4.739279, false), "
							+ "new google.maps.LatLng(43.9966409, 4.927226, false)))"//
					)
				, "If the property bounds is not set, it shouldn't render the JS-method"
				);
	}
}
