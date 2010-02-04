package wicket.contrib.gmap.util;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import wicket.contrib.gmap.api.GLatLng;

public class GeocoderTest {

	/**
	 * martin-g: 
	 * according to http://www.backups.nl/geocoding/ the provided address is correct but for some reason
	 * the test fails with status 602 (Unknown address)
	 * @maintainer: please remove @Ignore when the issue is fixed.
	 */
	@Ignore
	@Test
	public void testGeocoding() throws IOException {
		Geocoder coder = new Geocoder("ABQIAAAADk60rPazQzTdmKT7CvyKXRQEr9A52YiMqcOFyKYFGBLG6IXC6RQpL6leG0_YYqR-KhNEFosZSJieGQ");
		GLatLng result = coder.geocode("Salzburgerstraﬂe 205, 4030 Linz, ÷sterreich");
		Assert.assertNotNull(result);
	}
}
