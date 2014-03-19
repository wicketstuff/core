/*
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.gmap.geocoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.wicketstuff.gmap.api.GLatLng;

/**
 * Geocoder. See: https://developers.google.com/maps/documentation/geocoding/
 *
 * @author Thijs Vonk
 * @author Dieter Tremel
 */
public class Geocoder implements Serializable
{

    private static final long serialVersionUID = 1L;
    // Constants
    public static final String OUTPUT_XML = "xml";
    public static final String OUTPUT_JSON = "json";
    private final String output = OUTPUT_JSON;

    public Geocoder()
    {
    }

    public GLatLng decode(String response) throws GeocoderException, JSONException {
        JSONObject jsonResponse = new JSONObject(response);
        GeocoderStatus status = GeocoderStatus.valueOf(jsonResponse.getString("status"));
        if (status != GeocoderStatus.OK) {
            throw new GeocoderException(status);
        }
        JSONArray results = jsonResponse.getJSONArray("results");
        JSONObject location = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
        return new GLatLng(location.getDouble("lat"), location.getDouble("lng"));
    }

    /**
     * builds the google geo-coding url
     *
     * @param address
     * @return
     */
    public String encode(final String address)
    {
        StringBuilder sb = new StringBuilder("http://maps.googleapis.com/maps/api/geocode/");
        sb.append(output);
        sb.append("?");
        sb.append("address=").append(urlEncode(address));
        sb.append("&sensor=false");
        return sb.toString();
    }

    /**
     * @param address
     * @return
     * @throws IOException
     */
    public GLatLng geocode(final String address) throws Exception
    {
        InputStream is = invokeService(encode(address));
        if (is != null)
        {
            try
            {
                String content = org.apache.wicket.util.io.IOUtils.toString(is);
                return decode(content);
            }
            finally
            {
                is.close();
            }
        }
        return null;
    }

    /**
     * fetches the url content
     *
     * @param address
     * @return
     * @throws IOException
     */
    protected InputStream invokeService(final String address) throws IOException
    {
        URL url = new URL(address);
        return url.openStream();
    }

    /**
     * url-encode a value
     *
     * @param value
     * @return
     */
    private String urlEncode(final String value)
    {
        try
        {
            return URLEncoder.encode(value, "UTF-8");
        }
        catch (UnsupportedEncodingException ex)
        {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
