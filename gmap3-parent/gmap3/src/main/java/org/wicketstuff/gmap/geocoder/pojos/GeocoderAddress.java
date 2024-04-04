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
 * POJO for an entity in Google geocoders address_components Array <br/>
 * 
 * <p>
 * See also: <a href="https://developers.google.com/maps/documentation/geocoding/?hl=en#Results">
 * Google Geocoder Result Documentation</a><br/>
 * 
 * <b>Note:</b><br/>
 * The most documentation in this class a have been adopted by Google documentation.<br/>
 * Say thank you to Google!
 * </p>
 * 
 * @author Mathias Born - Contact: www.mathiasborn.de
 */
public class GeocoderAddress
{

	/** full text description or name of the address component */
	@JsonProperty("long_name")
	private String longName;
	/** an abbreviated textual name for the address component */
	@JsonProperty("short_name")
	private String shortName;
	/** array indicating the type of the address component. */
	private String[] types;

	/**
	 * @return the longName
	 */
	public String getLongName()
	{
		return longName;
	}

	/**
	 * Set the full text description or name of the address component
	 * 
	 * @param longName
	 *            the longName to set
	 */
	public void setLongName(String longName)
	{
		this.longName = longName;
	}

	/**
	 * Get the full text description or name of the address component
	 * 
	 * @return the shortName
	 */
	public String getShortName()
	{
		return shortName;
	}

	/**
	 * Set an abbreviated textual name for the address component
	 * 
	 * @param shortName
	 *            the shortName to set
	 */
	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}

	/**
	 * Get an array that indicating the type of the address component.
	 * 
	 * @return the types
	 */
	public String[] getTypes()
	{
		return types;
	}

	/**
	 * Set an array that indicating the type of the address component.
	 * 
	 * @param types
	 *            the types to set
	 */
	public void setTypes(String[] types)
	{
		this.types = types;
	}

}
