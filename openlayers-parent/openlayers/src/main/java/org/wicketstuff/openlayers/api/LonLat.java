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

import java.util.StringTokenizer;

import org.wicketstuff.openlayers.js.Constructor;

/**
 * Represents an Open layers
 * http://dev.openlayers.org/apidocs/files/OpenLayers/BaseTypes/LonLat-js.html
 */
public class LonLat implements GValue {
	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	private final double lat;
	private final double lng;

	/**
	 * Construct.
	 * 
	 * @param lat
	 * @param lng
	 */
	public LonLat(double lng, double lat) {
		this.lat = lat;
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}

	@Override
	public String toString() {
		return getJSconstructor();
	}

	/**
	 */
	public String getJSconstructor() {
		return new Constructor("OpenLayers.LonLat").add(lng).add(lat).toJS();
	}

	@Override
	public int hashCode() {
		return new Double(lat).hashCode() ^ new Double(lng).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LonLat) {
			LonLat t = (LonLat) obj;
			return t.lat == lat && t.lng == lng;
		}
		return false;
	}

	/**
	 * (37.34068368469045, -122.48519897460936)
	 */
	public static LonLat parse(String value) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(value, "(, )");

			float lat = Float.valueOf(tokenizer.nextToken());
			float lng = Float.valueOf(tokenizer.nextToken());
			return new LonLat(lat, lng);
		} catch (Exception e) {
			return null;
		}
	}
}