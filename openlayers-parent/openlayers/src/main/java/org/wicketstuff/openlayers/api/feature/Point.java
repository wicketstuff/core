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

import com.vividsolutions.jts.geom.Coordinate;

/**
 * 
 * @author Marin Mandradjiev (marinsm@hotmail.com)
 * 
 */
public class Point extends Feature {
	private static final long serialVersionUID = -4295187268790863039L;
	private final Coordinate coordinate;

	public Point(Coordinate coordinate) {
		super();
		this.coordinate = coordinate;
	}

	public Point(Coordinate coordinate, FeatureStyle featureStyle) {
		super(featureStyle);
		this.coordinate = coordinate;
	}

	@Override
	public String getJSconstructor() {
		return "var feature" + getId() + " = " + getJScoordinate(coordinate)
				+ ";\n";
	}
}
