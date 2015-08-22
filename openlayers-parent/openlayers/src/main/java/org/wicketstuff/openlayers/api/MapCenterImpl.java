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
package org.wicketstuff.openlayers.api;

/**
 * 
 * @author Marin Mandradjiev (marinsm@hotmail.com)
 * 
 */
public class MapCenterImpl implements IMapCenter
{
	private static final long serialVersionUID = -5635112000623942613L;
	private Double longitude = null;
	private Double latitude = null;
	private Integer zoom = null;

	public MapCenterImpl()
	{
	}

	public MapCenterImpl(Double longitude, Double latitude, Integer zoom)
	{
		this.longitude = longitude;
		this.latitude = latitude;
		this.zoom = zoom;
	}

	public Double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(Double longitude)
	{
		this.longitude = longitude;
	}

	public Double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(Double latitude)
	{
		this.latitude = latitude;
	}

	public Integer getZoom()
	{
		return zoom;
	}

	public void setZoom(Integer zoom)
	{
		this.zoom = zoom;
	}
}
