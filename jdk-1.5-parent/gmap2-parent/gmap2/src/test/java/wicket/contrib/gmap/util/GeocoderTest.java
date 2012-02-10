package wicket.contrib.gmap.util;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import wicket.contrib.gmap.api.GLatLng;

public class GeocoderTest
{

	/**
	 * martin-g: according to http://www.backups.nl/geocoding/ the provided address is correct but
	 * for some reason the test fails with status 602 (Unknown address)
	 * 
	 * @maintainer: please remove @Ignore when the issue is fixed.
	 * 
	 *              akiraly: I am not the maintainer but this file was not in UTF-8 encoding. After
	 *              correcting that test seems to pass. So I commented @Ignore for now.
	 */
	// @Ignore
	@Test
	public void testGeocoding() throws IOException
	{
		Geocoder coder = new Geocoder(
			"ABQIAAAADk60rPazQzTdmKT7CvyKXRQEr9A52YiMqcOFyKYFGBLG6IXC6RQpL6leG0_YYqR-KhNEFosZSJieGQ");
		GLatLng result = coder.geocode("Salzburgerstraße 205, 4030 Linz, Österreich");
		Assert.assertNotNull(result);
	}
}
