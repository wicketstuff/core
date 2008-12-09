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
 * Represents an Openlayer Map API's 
 * http://dev.openlayers.org/docs/files/OpenLayers/Geometry/Polygon-js.html.
 */
public class Polygon extends Overlay
{
	private static final long serialVersionUID = 1L;

	private LonLat[] gLatLngs;
	private String strokeColor;
	private int strokeWeight;
	private float strokeOpacity;
	private String fillColor;
	private float fillOpacity;

	public Polygon(String strokeColor, int strokeWeight, float strokeOpacity, String fillColor, float fillOpacity, LonLat... gLatLngs)
	{
		super();
		
		this.gLatLngs = gLatLngs;
		
		this.strokeColor = strokeColor;
		this.strokeWeight = strokeWeight;
		this.strokeOpacity = strokeOpacity;
		this.fillColor = fillColor;
		this.fillOpacity = fillOpacity;
	}

	@Override
	protected String getJSconstructor()
	{
		Constructor constructor = new Constructor("Polygon");

		Array array = new Array();
		for (LonLat gLatLng : gLatLngs) {
			array.add(gLatLng.getJSconstructor());
		}
		constructor.add(array.toJS());
		
		constructor.addString(strokeColor);
		constructor.addString(strokeWeight);
		constructor.addString(strokeOpacity);
		constructor.addString(fillColor);
		constructor.addString(fillOpacity);

		return constructor.toJS();
	}
}
