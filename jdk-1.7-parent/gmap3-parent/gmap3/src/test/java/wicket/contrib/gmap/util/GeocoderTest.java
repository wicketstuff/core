package wicket.contrib.gmap.util;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.Ignore;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.geocoder.Geocoder;

public class GeocoderTest {

    @Test
    public void testEncode() {
        System.out.println("encode");
        Geocoder coder = new Geocoder();
        String encode = coder.encode("Salzburgerstraße 205, 4030 Linz, Österreich");
        Assert.assertEquals("http://maps.googleapis.com/maps/api/geocode/json?address=Salzburgerstra%C3%9Fe+205%2C+4030+Linz%2C+%C3%96sterreich&sensor=false", encode);
    }

    @Test
    public void testGeocoding() throws Exception {
        System.out.println("geocoding");
        Geocoder coder = new Geocoder();
        GLatLng result = coder.geocode("Salzburgerstraße 205, 4030 Linz, Österreich");
        Assert.assertNotNull(result);
        Assert.assertEquals(48.25763170, result.getLat(), 0.00001);
        Assert.assertEquals(14.29231840, result.getLng(), 0.00001);
    }
}
