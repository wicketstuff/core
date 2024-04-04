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

import org.wicketstuff.gmap.api.GLatLng;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Equivalent POJO for the google geometry JSON-Object of a geocoder request
 * <p>
 * See also: <a href="https://developers.google.com/maps/documentation/geocoding/?hl=en#JSON">
 * Google Geocoder JSONResult Doc</a><br/>
 * 
 * <b>Note:</b><br/>
 * The most documentation in this class a have been adopted by Google documentation.<br/>
 * Say thank you to Google!
 * </p>
 * 
 * @author Mathias Born - Contact: www.mathiasborn.de
 */
public class GeocoderGeometry
{

	private NortheastSoutwestInfo bounds;
	/** contains the geocoded latitude,longitude value */
	private GLatLng location;
	/** stores additional data about the specified location */
	@JsonProperty("location_type")
	private String locationType;
	private NortheastSoutwestInfo viewport;

	/**
	 * @return the bounds
	 */
	public NortheastSoutwestInfo getBounds()
	{
		return bounds;
	}

	/**
	 * @param bounds
	 *            the bounds to set
	 */
	public void setBounds(NortheastSoutwestInfo bounds)
	{
		this.bounds = bounds;
	}


	/**
	 * @return the location
	 */
	public GLatLng getLocation()
	{
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(GLatLng location)
	{
		this.location = location;
	}

	/**
	 * @return the location_type
	 */
	public String getLocation_type()
	{
		return locationType;
	}

	/**
	 * @param location_type
	 *            the location_type to set
	 */
	public void setLocation_type(String location_type)
	{
		this.locationType = location_type;
	}

	/**
	 * @return the viewport
	 */
	public NortheastSoutwestInfo getViewport()
	{
		return viewport;
	}

	/**
	 * @param viewport
	 *            the viewport to set
	 */
	public void setViewport(NortheastSoutwestInfo viewport)
	{
		this.viewport = viewport;
	}

}
