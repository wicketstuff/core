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
public class GeocoderAdress
{

	@JsonProperty("long_name")
	private String longName;
	@JsonProperty("short_name")
	private String shortName;
	private String[] types;

	/**
	 * @return the longName
	 */
	public String getLongName()
	{
		return longName;
	}

	/**
	 * @param longName
	 *            the longName to set
	 */
	public void setLongName(String longName)
	{
		this.longName = longName;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName()
	{
		return shortName;
	}

	/**
	 * @param shortName
	 *            the shortName to set
	 */
	public void setShortName(String shortName)
	{
		this.shortName = shortName;
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
