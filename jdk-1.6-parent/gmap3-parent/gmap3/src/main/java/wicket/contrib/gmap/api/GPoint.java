/*
 * $Id: GPoint.java 577 2006-02-12 20:46:53Z syca $
 * $Revision: 577 $
 * $Date: 2006-02-12 12:46:53 -0800 (Sun, 12 Feb 2006) $
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wicket.contrib.gmap.api;

import wicket.contrib.gmap.js.Constructor;

/**
 * Represents an Maps API's GPoint that contains x and y coordinates.
 * 
 * @author Iulian-Corneliu Costan
 */
public class GPoint implements GValue
{
	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private final float _longitude;

	private final float _latitude;

	public GPoint(float longitude, float latitude)
	{
		_longitude = longitude;
		_latitude = latitude;
	}

	public float getLongitude()
	{
		return _longitude;
	}

	public float getLatitude()
	{
		return _latitude;
	}

	/**
	 * @see wicket.contrib.gmap.api.GValue#getJSconstructor()
	 */
	public String getJSconstructor()
	{
		return new Constructor("google.maps.Point").add(_longitude).add(_latitude).toJS();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + Float.floatToIntBits(_latitude);
		result = PRIME * result + Float.floatToIntBits(_longitude);
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final GPoint other = (GPoint) obj;
		if (Float.floatToIntBits(_latitude) != Float.floatToIntBits(other._latitude))
			return false;
		if (Float.floatToIntBits(_longitude) != Float.floatToIntBits(other._longitude))
			return false;
		return true;
	}
}
