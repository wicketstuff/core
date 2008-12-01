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
package wicket.contrib.gmap.util;

import java.io.Serializable;
import java.util.StringTokenizer;

import wicket.contrib.gmap.api.GLatLng;

/**
 * Geocoder. See:
 * http://www.google.com/apis/maps/documentation/services.html#Geocoding_Direct
 * 
 * @author Thijs Vonk
 */
public class Geocoder implements Serializable
{

	private static final long serialVersionUID = 1L;

	// Constants
	public static final String OUTPUT_CSV = "csv";

	public static final String OUTPUT_XML = "xml";

	public static final String OUTPUT_KML = "kml";

	public static final String OUTPUT_JSON = "json";

	private final String gMapKey;

	private final String output = OUTPUT_CSV;

	/**
	 * @param gMapKey
	 *            Gmap API key
	 * @throws IllegalArgumentException
	 *             If the API key is <code>null</code>
	 */
	public Geocoder(String gMapKey)
	{
		if (gMapKey == null)
		{
			throw new IllegalArgumentException("API key cannot be null");
		}

		this.gMapKey = gMapKey;
	}

	public String encode(String address)
	{
		return "http://maps.google.com/maps/geo?q=" + address.replace(' ', '+') + "&output="
				+ output + "&key=" + gMapKey;
	}

	public GLatLng decode(String response) throws GeocoderException
	{

		StringTokenizer gLatLng = new StringTokenizer(response, ",");

		String status = gLatLng.nextToken();
		gLatLng.nextToken(); // skip precision
		String latitude = gLatLng.nextToken();
		String longitude = gLatLng.nextToken();

		if (Integer.parseInt(status) != GeocoderException.G_GEO_SUCCESS)
		{
			throw new GeocoderException(Integer.parseInt(status));
		}

		return new GLatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
	}
}
