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

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author Mathias Born - Contact: www.mathiasborn.de
 */
public class GeocoderResultData
{
	@JsonProperty("address_components")
	private GeocoderAdress[] addressComponents;
	@JsonProperty("formatted_address")
	private String formattedAddress;
	private GeocoderGeometry geometry;
	private String[] types;

	/**
	 * @return the addressComponents
	 */
	public GeocoderAdress[] getAddressComponents()
	{
		return addressComponents;
	}

	/**
	 * @param addressComponents
	 *            the addressComponents to set
	 */
	public void setAddressComponents(GeocoderAdress[] addressComponents)
	{
		this.addressComponents = addressComponents;
	}

	/**
	 * @return the formattedAdress
	 */
	public String getFormattedAdress()
	{
		return formattedAddress;
	}

	/**
	 * @param formattedAdress
	 *            the formattedAdress to set
	 */
	public void setFormattedAdress(String formattedAdress)
	{
		this.formattedAddress = formattedAdress;
	}

	/**
	 * @return the geometry
	 */
	public GeocoderGeometry getGeometry()
	{
		return geometry;
	}

	/**
	 * @param geometry
	 *            the geometry to set
	 */
	public void setGeometry(GeocoderGeometry geometry)
	{
		this.geometry = geometry;
	}

	/**
	 * @return the types
	 */
	public String[] getTypes()
	{
		return types;
	}

	/**
	 * @param types
	 *            the types to set
	 */
	public void setTypes(String[] types)
	{
		this.types = types;
	}
}
