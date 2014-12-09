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

import org.apache.log4j.Logger;
import org.apache.wicket.ajax.json.JSONException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GLatLngBounds;
import org.wicketstuff.gmap.geocoder.GeocoderGeometry.Location;

/**
 * Geocoder. See: https://developers.google.com/maps/documentation/geocoding/
 *
 * @author Thijs Vonk
 * @author Dieter Tremel
 * @author Mathias Born
 */
public class Geocoder implements Serializable
{

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(Geocoder.class);
	// Constants
	public static final String OUTPUT_XML = "xml";
	public static final String OUTPUT_JSON = "json";
	private final String output = OUTPUT_JSON;
	/**
	 * Result-Object of an gecoder request
	 */
	private GeocoderResult geocoderResult;

	public Geocoder()
	{
	}

	public GLatLng decode(String response) throws GeocoderException, JSONException
	{
		try
		{
			geocoderResult = new ObjectMapper() //
			.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.readValue(response, GeocoderResult.class);
		}
		catch (JsonParseException e)
		{
			LOGGER.error(e);
		}
		catch (JsonMappingException e)
		{
			LOGGER.error(e);
		}
		catch (IOException e)
		{
			LOGGER.error(e);
		}

		GeocoderStatus status = geocoderResult.getStatus();

		if (status != GeocoderStatus.OK)
		{
			throw new GeocoderException(status);
		}

		if (geocoderResult.getResults().length < 1)
		{
			throw new RuntimeException(); // TODO: throw something better
		}

		Location location = geocoderResult.getResults()[0].getGeometry().getLocation();
		return new GLatLng(location.getLat(), location.getLng());

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

	/**
	 * READY-ONLY Get the Result of the last geocoder Request
	 * 
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
		GeocoderViewPort port = getGecoderResult().getResults()[0].getGeometry().getViewport();
		GLatLng sw = new GLatLng(port.getSouthwest().getLat(), port.getSouthwest().getLng());
		GLatLng ne = new GLatLng(port.getNortheast().getLat(), port.getNortheast().getLng());
		map.setBounds(new GLatLngBounds(sw, ne));
	}
}
