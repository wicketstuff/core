package org.wicketstuff.gmap.geocoder.pojos;

/**
 * contains the geocoded latitude,longitude value
 */
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