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

import org.wicketstuff.gmap.geocoder.GeocoderStatus;


/**
 * {@link GeocoderResult} is the Resultset of the Google Geocode Request.<br/>
 * This class encapsulates the full result of one geocoder request.
 * 
 * <p>
 * See also: <a href="https://developers.google.com/maps/documentation/geocoding/?hl=en#JSON">
 * Google Geocoder JSONResult Doc</a><br/>
 * 
 * <b>Note:</b><br/>
 * The most documentation in this class a have been adopted by Google documentation.<br/>
 * Say thank you to Google!
 * </p>
 * 
 * 
 * @author Mathias Born - Contact: www.mathiasborn.de
 */
public class GeocoderResult
{

	/** An Array of the gecode result */
	private GeocoderResultData[] results;
	/**
	 * contains the status of the request, and may contain debugging information to help you track
	 * down why geocoding is not working
	 */
	private GeocoderStatus status;

	/**
	 * Return the status of a geocoder request
	 * 
	 * @return {@link GeocoderStatus} geocoder request status
	 */
	public GeocoderStatus getStatus()
	{
		return status;
	}

	/**
	 * Set the status of a geocoder request
	 * 
	 * @param status
	 *            - the status to set
	 */
	public void setStatus(GeocoderStatus status)
	{
		this.status = status;
	}

	/**
	 * Get all {@link GeocoderResultData} of a geocoder request
	 * 
	 * @return all {@link GeocoderResultData} of a geocoder request
	 */
	public GeocoderResultData[] getResults()
	{
		return results;
	}

	/**
	 * Set all {@link GeocoderResultData} of a geocoder request
	 * 
	 * @param results
	 *            all {@link GeocoderResultData} of a geocoder request
	 */
	public void setResults(GeocoderResultData[] results)
	{
		this.results = results;
	}

}
