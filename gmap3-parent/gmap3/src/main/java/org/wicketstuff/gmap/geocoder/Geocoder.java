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

import org.apache.wicket.ajax.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GLatLngBounds;
import org.wicketstuff.gmap.geocoder.pojos.GeocoderResult;
import org.wicketstuff.gmap.geocoder.pojos.NortheastSoutwestInfo;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Geocoder. See: https://developers.google.com/maps/documentation/geocoding/
 *
 * @author Thijs Vonk
 * @author Dieter Tremel
 */
public class Geocoder implements Serializable
{

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(Geocoder.class);

    // Constants
    public static final String OUTPUT_XML = "xml";
    public static final String OUTPUT_JSON = "json";
    private final String output = OUTPUT_JSON;
    /**
     * Result-Object of a gecoder request
     */
    private GeocoderResult geocoderResult;
    private ObjectMapper objectMapper;


    /**
     * <b>Default Constructor.</b><br/>
     * Create an {@link ObjectMapper}.<br/>
     * The {@link ObjectMapper} ignore unknown properties when mapping from JSON to POJO.<br/>
     * <b>Use</b> {@link #Geocoder(ObjectMapper)} to customize
     */
    public Geocoder()
    {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * <b>Configuration Constructor.</b><br/>
     * If you have to customize the default {@link ObjectMapper}
     * 
     * @see Geocoder#Geocoder()
     */
    public Geocoder(ObjectMapper mapper)
    {
        this.objectMapper = mapper;
    }

    /**
     * Decode an response of an geocoder request to POJOs.<br/>
     * Following the successful mapping from JSON to POJO<br/>
     * all hits are available in {@link #geocoderResult}
     * 
     * @param response
     *            - JSON respone from Google Geocoder Service
     * @return firstHit - First hit of {@link #geocoderResult}
     * @throws GeocoderException
     *             - When GeocoderStatus unequal to {@link GeocoderStatus#OK}
     * @throws JSONException
     *             - The JSONException is thrown by the JSON.org classes when things are amiss.
     */
    public GLatLng decode(String response) throws GeocoderException, JSONException {
        try
        {
            geocoderResult = objectMapper.readValue(response, GeocoderResult.class);
        }
        catch (JsonParseException jpe)
        {
            LOGGER.error("Geocoder JSON parsing failed", jpe);
        }
        catch (JsonMappingException jme)
        {
            LOGGER.error(
                "Something went wrong during json mapping. Check your ObjectMapper when customize.",
                jme);
        }
        catch (IOException ioe)
        {
            LOGGER.error("Upps. Panic!", ioe);
        }

        GeocoderStatus status = geocoderResult.getStatus();

        if (status != GeocoderStatus.OK)
        {
            throw new GeocoderException(status);
        }

        if (geocoderResult.getResults().length < 1)
        {
            throw new IllegalStateException(
                "GecoderStatus return GeocoderStatus.OK but size of the results was less then 1");
        }
        // Only return the first Element
        return geocoderResult.getResults()[0].getGeometry().getLocation();
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
     * Invoke a geocoder request to the GoogleMaps API.<br/>
     * Only return the first element of {@link #geocoderResult} to be backward compatible.<br/>
     * After succeful call of {@link #geocode(String)} you can get all results from
     * {@link #geocoderResult}
     * 
     * @param address
     *            - Requested address
     * @return {@link GLatLng} - only first hit of the request
     * @throws IOException
     */
    public GLatLng geocode(final String address) throws IOException
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

    /**
     * Get the Result of the last geocoder Request
     * @return the result of the last geocoder request
     */
    public GeocoderResult getGecoderResult()
    {
        return geocoderResult;
    }

    /**
     * Convenience method to center and fit the zoom for an address, on the given map.
     * <p>
     * <b>Example:</b>
     * 
     * <pre>
     * GMap myMap = GMap(&quot;wicketId&quot;);
     * new Geocoder().centerAndFitZoomForAdress(myMap, &quot;Frankfurt am Main&quot;);
     * </pre>
     * 
     * <b>Result:</b><br/>
     * Frankfurt is centered and the zoom is suitable
     * </p>
     * 
     * @param map
     *            - the map where the address should shown
     * @param address
     *            - address as string for the google geocoder
     * @throws Exception
     */
    public void centerAndFitZoomForAdress(GMap map, String address) throws Exception
    {
        this.geocode(address);
        NortheastSoutwestInfo port = getGecoderResult().getResults()[0].getGeometry().getViewport();
        GLatLng sw = new GLatLng(port.getSouthwest().getLat(), port.getSouthwest().getLng());
        GLatLng ne = new GLatLng(port.getNortheast().getLat(), port.getNortheast().getLng());
        map.setBounds(new GLatLngBounds(sw, ne));
    }
}
