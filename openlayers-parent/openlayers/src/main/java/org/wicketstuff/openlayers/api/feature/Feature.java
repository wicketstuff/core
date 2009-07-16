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

import java.io.Serializable;

import org.wicketstuff.openlayers.IOpenLayersMap;
import org.wicketstuff.openlayers.api.layer.Vector;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * 
 * @author Marin Mandradjiev (marinsm@hotmail.com)
 * 
 */
public abstract class Feature implements Serializable {
	private static final long serialVersionUID = 364944041007700590L;
	private FeatureStyle featureStyle = null;

	public Feature() {}
	
	public Feature(FeatureStyle featureStyle) {
		this.featureStyle = featureStyle;
	}
	
	public String getId() {
		return String.valueOf(System.identityHashCode(this));
	}

	public String getJSAddFeature(IOpenLayersMap map, Vector vector) {
		return getJSconstructor()
				+ "var draw"
				+ getId()
				+ " = new OpenLayers.Feature.Vector(feature"
				+ getId()
				+ ", null, " + (featureStyle != null ? "layer_style" + featureStyle.getId() : "null") + ");\n"
				+ map.getJSinvoke("addFeature(draw" + getId() + ", layer"
						+ vector.getId() + ")");
	}

	protected static String getJScoordinate(Coordinate coordinate) {
		return "new OpenLayers.Geometry.Point(" + coordinate.x + ", "
				+ coordinate.y + ")";
	}

	public abstract String getJSconstructor();

	public void setFeatureStyle(FeatureStyle featureStyle) {
		this.featureStyle = featureStyle;
	}

	public FeatureStyle getFeatureStyle() {
		return featureStyle;
	}
}
