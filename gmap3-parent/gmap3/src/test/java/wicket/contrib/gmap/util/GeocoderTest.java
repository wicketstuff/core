package wicket.contrib.gmap.util;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.geocoder.Geocoder;

@RunWith(JUnit4.class)
public class GeocoderTest
{
	private Geocoder coder;

	@Before
	public void setUp()
	{
		coder = new Geocoder();

	}

	@Test
	public void testEncode()
	{
		String encode = coder.encode("Salzburgerstraße 205, 4030 Linz, Österreich");
		Assert.assertEquals(
			"http://maps.googleapis.com/maps/api/geocode/json?address=Salzburgerstra%C3%9Fe+205%2C+4030+Linz%2C+%C3%96sterreich&sensor=false",
			encode);
	}

	@Test
	public void testGeocoding() throws Exception
	{
		Geocoder coder = new Geocoder();
		GLatLng result = coder.geocode("Salzburgerstraße 205, 4030 Linz, Österreich");
		Assert.assertNotNull(result);
		Assert.assertEquals(48.25763170, result.getLat(), 0.00001);
		Assert.assertEquals(14.29231840, result.getLng(), 0.00001);
	}

	/**
	 * Integration test for loading geocoder information<br/>
	 * from google geocoder service and center and fit the<br/>
	 * zoom of the map
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCenterAndFitZoomForAdress() throws Exception
	{
		WicketTester tester = new WicketTester();
		GMap map = new GMap("gmap");
		tester.startComponentInPage(map);
		Geocoder gecoder = new Geocoder();
		gecoder.centerAndFitZoomForAdress(map, "Avignon, France");
		Assert.assertEquals(43.9966409, map.getBounds().getNE().getLat(), 0.00001);
		Assert.assertEquals(4.927226, map.getBounds().getNE().getLng(), 0.00001);
		Assert.assertEquals(43.8864731, map.getBounds().getSW().getLat(), 0.00001);
		Assert.assertEquals(4.739279, map.getBounds().getSW().getLng(), 0.00001);
		Assert.assertEquals(37.4419, map.getCenter().getLat(), 0.00001);
		Assert.assertEquals(-122.1419, map.getCenter().getLng(), 0.00001);
	}
}
