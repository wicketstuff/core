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

import org.wicketstuff.openlayers.js.Array;
import org.wicketstuff.openlayers.js.Constructor;

/**
 * Represents an OpenLayers Map API's
 * http://dev.openlayers.org/docs/files/OpenLayers/Geometry/LineString-js.html
 */
public class Polyline extends Overlay
{
	private static final long serialVersionUID = 1L;

	private LonLat[] gLatLngs;
	private String color;
	private int weight;
	private float opacity;

	public Polyline(String color, int weight, float opacity, LonLat... gLatLngs)
	{
		super();
		
		this.gLatLngs = gLatLngs;
		this.color = color;
		this.weight = weight;
		this.opacity = opacity;
	}

	@Override
	protected String getJSconstructor()
	{
		Constructor constructor = new Constructor("Polyline");
		
		Array array = new Array();
		for (LonLat gLatLng : gLatLngs) {
			array.add(gLatLng.getJSconstructor());
		}
		constructor.add(array.toJS());
		
		constructor.addString(color);
		constructor.addString(weight);
		constructor.addString(opacity);
		
		return constructor.toJS();
	}
}
