package wicket.contrib.gmap.util;

import java.io.IOException;
import junit.framework.Assert;
import org.junit.Test;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.geocoder.Geocoder;

public class GeocoderTest
{

	@Test
	public void testGeocoding() throws IOException
	{
		Geocoder coder = new Geocoder();
		GLatLng result = coder.geocode("Salzburgerstraße 205, 4030 Linz, Österreich");
		Assert.assertNotNull(result);
	}
}
