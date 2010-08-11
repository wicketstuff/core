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

import org.wicketstuff.openlayers.OpenLayersMapUtils;
import org.wicketstuff.openlayers.js.Constructor;

import com.vividsolutions.jts.geom.Point;

/**
 * Represents an Open layers
 * http://dev.openlayers.org/apidocs/files/OpenLayers/BaseTypes/LonLat-js.html
 */
public class LonLat implements Value {
	/**
	 * Default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	private final Point point;

	/**
	 * Construct.
	 * 
	 * @param lat
	 * @param lng
	 */
	public LonLat(double lng, double lat) {

		this.point = OpenLayersMapUtils.createPoint(lng, lat);
	}

	public LonLat(Point point) {
		this.point = point;
	}

	public double getLat() {
		return point.getY();
	}

	public double getLng() {
		return point.getX();
	}

	@Override
	public String toString() {
		return getJSconstructor();
	}

	/**
	 */
	public String getJSconstructor() {
		return new Constructor("OpenLayers.LonLat").add(getLng()).add(getLat())
				.toJS();
	}

	@Override
	public int hashCode() {
		return new Double(getLat()).hashCode()
				^ new Double(getLng()).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LonLat) {
			LonLat t = (LonLat) obj;
			return t.getLat() == getLat() && t.getLng() == getLng();
		}
		return false;
	}

	public Point getPoint() {
		return point;
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

	/**
	 * (lon=37.34068368469045, lat=-122.48519897460936)
	 */
	public static LonLat parseWithNames(String value) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(value, "(, )");
			float lng = 0;
			float lat = 0;
			for (int i = 0; i < 2; i++) {
				String item = tokenizer.nextToken().toLowerCase();
				if (item.startsWith("lon=")) {
					lng = Float.parseFloat(item.substring(4));
				} else if (item.startsWith("lat=")) {
					lat = Float.parseFloat(item.substring(4));
				} else {
					return null;
				}
			}
			return new LonLat(lng, lat);
		} catch (Exception e) {
			return null;
		}
	}
}