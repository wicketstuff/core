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


/**
 * POJO for a google geocoder JSON Object viewport <br/>
 * Contains the recommended viewport for displaying the returned result, specified as two
 * latitude,longitude values defining the southwest and northeast corner of the viewport bounding
 * box. Generally the viewport is used to frame a result when displaying it to a user.
 * 
 * <p>
 * See also: <a href="https://developers.google.com/maps/documentation/geocoding/?hl=en#Results">
 * Google Geocoder JSONResult Doc</a><br/>
 * 
 * <b>Note:</b><br/>
 * The most documentation in this class a have been adopted by Google documentation.<br/>
 * Say thank you to Google!
 * </p>
 * 
 * @author Mathias Born - Contact: www.mathiasborn.de
 */
public class GeocoderViewPort
{

	private Northeast northeast;
	private Southwest southwest;

	/**
	 * @return the northeast
	 */
	public Northeast getNortheast()
	{
		return northeast;
	}

	/**
	 * @param northeast
	 *            the northeast to set
	 */
	public void setNortheast(Northeast northeast)
	{
		this.northeast = northeast;
	}

	/**
	 * @return the southwest
	 */
	public Southwest getSouthwest()
	{
		return southwest;
	}

	/**
	 * @param southwest
	 *            the southwest to set
	 */
	public void setSouthwest(Southwest southwest)
	{
		this.southwest = southwest;
	}

}
