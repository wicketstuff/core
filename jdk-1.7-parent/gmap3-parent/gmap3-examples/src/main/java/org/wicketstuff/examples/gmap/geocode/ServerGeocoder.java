package org.wicketstuff.examples.gmap.geocode;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import org.apache.wicket.util.io.Streams;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.geocoder.Geocoder;
import org.wicketstuff.gmap.geocoder.GeocoderException;

/**
 * A serverside Geocoder.
 */
public class ServerGeocoder
{

    private final Geocoder geocoder;

    public ServerGeocoder()
    {
        this.geocoder = new Geocoder();
    }

    /**
     * @param address
     * The address for which a coordinate must be found.
     * @return GLatLng point for the address found by the Geocoder
     * @throws GeocoderException
     * If a error happened on the side of Google
     * @throws IOException
     * If a connection error happened
     */
    public GLatLng findAddress(String address) throws Exception
    {
        URL url = new URL(geocoder.encode(address));
        URLConnection connection = url.openConnection();
        String content = Streams.readString(connection.getInputStream());
        return geocoder.decode(content);
    }
}
