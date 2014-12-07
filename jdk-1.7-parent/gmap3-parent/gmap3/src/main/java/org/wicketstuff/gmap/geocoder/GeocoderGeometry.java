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

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * @author Mathias Born - Contact: www.mathiasborn.de
 */
public class GeocoderGeometry
{

	private Bounds bounds;
	private Location location;
	private String location_type;
	private GeocoderViewPort viewport;

	public class Bounds
	{
		private Northeast northeast;
		private Southwest southwest;

		public class Northeast
		{
			double lat, lng;

			/**
			 * @return the lat
			 */
			public double getLat()
			{
				return lat;
			}

			/**
			 * @param lat
			 *            the lat to set
			 */
			public void setLat(double lat)
			{
				this.lat = lat;
			}

			/**
			 * @return the lng
			 */
			public double getLng()
			{
				return lng;
			}

			/**
			 * @param lng
			 *            the lng to set
			 */
			public void setLng(double lng)
			{
				this.lng = lng;
			}

		}

		public class Southwest
		{
			double lat, lng;

			/**
			 * @return the lat
			 */
			public double getLat()
			{
				return lat;
			}

			/**
			 * @param lat
			 *            the lat to set
			 */
			public void setLat(double lat)
			{
				this.lat = lat;
			}

			/**
			 * @return the lng
			 */
			public double getLng()
			{
				return lng;
			}

			/**
			 * @param lng
			 *            the lng to set
			 */
			public void setLng(double lng)
			{
				this.lng = lng;
			}

		}

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

		public String toJSONObject() throws JsonGenerationException, JsonMappingException,
			IOException
		{
			return new ObjectMapper().writeValueAsString(this);
		}

	}

	/**
	 * @return the bounds
	 */
	public Bounds getBounds()
	{
		return bounds;
	}

	/**
	 * @param bounds
	 *            the bounds to set
	 */
	public void setBounds(Bounds bounds)
	{
		this.bounds = bounds;
	}

	public class Location
	{
		double lat, lng;

		/**
		 * @return the lat
		 */
		public double getLat()
		{
			return lat;
		}

		/**
		 * @param lat
		 *            the lat to set
		 */
		public void setLat(double lat)
		{
			this.lat = lat;
		}

		/**
		 * @return the lng
		 */
		public double getLng()
		{
			return lng;
		}

		/**
		 * @param lng
		 *            the lng to set
		 */
		public void setLng(double lng)
		{
			this.lng = lng;
		}

	}

	/**
	 * @return the location
	 */
	public Location getLocation()
	{
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location)
	{
		this.location = location;
	}

	/**
	 * @return the location_type
	 */
	public String getLocation_type()
	{
		return location_type;
	}

	/**
	 * @param location_type
	 *            the location_type to set
	 */
	public void setLocation_type(String location_type)
	{
		this.location_type = location_type;
	}

	/**
	 * @return the viewport
	 */
	public GeocoderViewPort getViewport()
	{
		return viewport;
	}

	/**
	 * @param viewport
	 *            the viewport to set
	 */
	public void setViewport(GeocoderViewPort viewport)
	{
		this.viewport = viewport;
	}

}
