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
package org.wicketstuff.gmap.geocoder.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The {@link GeocoderResultData} is equivalent POJO to Googles Results as JSON Object.<br/>
 * See also the documentation of Google.<br/>
 * <a href="https://developers.google.com/maps/documentation/geocoding/?hl=en#Results"> Google
 * Geocoder Result Documentation</a><br/>
 * 
 * <b>Note:</b><br/>
 * The most documentation in this class a have been adopted by Google documentation.<br/>
 * Say thank you to Google!
 * 
 * @author Mathias Born - Contact: www.mathiasborn.de
 */
public class GeocoderResultData
{
	/** array containing the separate address components */
	@JsonProperty("address_components")
	private GeocoderAddress[] addressComponents;
	/** containing the human-readable address of this location */
	@JsonProperty("formatted_address")
	private String formattedAddress;
	/** containing the geometry data of a geocoder request result */
	private GeocoderGeometry geometry;
	/**
	 * This array contains a set of zero or more tags identifying the type of feature returned in
	 * the result. <br/>
	 * <br/>
	 * For example, a geocode of "Chicago" returns "locality" which indicates that "Chicago" is a
	 * city, and also returns "political" which indicates it is a political entity.
	 */
	private String[] types;

	/**
	 * Get an array containing the separate address components
	 * 
	 * @return the addressComponents
	 */
	public GeocoderAddress[] getAddressComponents()
	{
		return addressComponents;
	}

	/**
	 * Set an array containing the separate address components
	 * 
	 * @param addressComponents
	 *            the addressComponents to set
	 */
	public void setAddressComponents(GeocoderAddress[] addressComponents)
	{
		this.addressComponents = addressComponents;
	}

	/**
	 * Get the human-readable address of this location
	 * 
	 * @return the formattedAdress
	 */
	public String getFormattedAdress()
	{
		return formattedAddress;
	}

	/**
	 * Set the human-readable address of this location
	 * 
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
	 * Get zero or more tags identifying the type of feature
	 * 
	 * @return the types
	 */
	public String[] getTypes()
	{
		return types;
	}

	/**
	 * Set zero or more tags identifying the type of feature
	 * 
	 * @param types
	 *            the types to set
	 */
	public void setTypes(String[] types)
	{
		this.types = types;
	}
}
