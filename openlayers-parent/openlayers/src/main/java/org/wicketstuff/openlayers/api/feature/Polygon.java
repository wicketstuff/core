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
package org.wicketstuff.openlayers.api.feature;

import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;

public class Polygon extends Feature {
	private static final long serialVersionUID = 2381878612322151640L;
	private final List<Coordinate> coordinates;

	public Polygon(List<Coordinate> coordinates) {
		super();
		this.coordinates = coordinates;
	}

	public Polygon(List<Coordinate> coordinates, FeatureStyle featureStyle) {
		super(featureStyle);
		this.coordinates = coordinates;
	}

	@Override
	public String getJSconstructor() {
		StringBuffer result = new StringBuffer();
		result.append("var points" + getId() + " = [];\n");
		for (Coordinate coordinate : coordinates) {
			result.append("points" + getId() + ".push("
					+ getJScoordinate(coordinate) + ");\n");
		}
		result.append("var feature" + getId()
				+ " = new OpenLayers.Geometry.LinearRing(points" + getId()
				+ ");\n");
		return result.toString();
	}
}
