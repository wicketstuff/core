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
	private IOpenLayersMap map = null;
	private String projection = "EPSG:4326";
	private String displayInLayer = null;

	public Feature() {
		this(null, null);
	}

	public Feature(FeatureStyle featureStyle) {
		this(featureStyle, null);
	}

	public Feature(IOpenLayersMap map) {
		this(null, map);
	}

	public Feature(FeatureStyle featureStyle, IOpenLayersMap map) {
		this.featureStyle = featureStyle;
		this.map = map;
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
				+ ", null, "
				+ (featureStyle != null ? "layer_style" + featureStyle.getId()
						: "null")
				+ ");\n"
				+ map.getJSinvoke("addFeature(draw" + getId() + ", layer"
						+ vector.getId() + ")");
	}

	public String getJSRemoveFeature(IOpenLayersMap map, Vector vector) {
		return map.getJSinvoke("removeFeature(draw" + getId() + ", layer"
				+ vector.getId() + ")");
	}

	protected String getJScoordinate(Coordinate coordinate) {
		String transformation = "";
		if (map != null && projection != null) {
			transformation = ".transform(new OpenLayers.Projection(\""
					+ projection + "\"), " + map.getJSinvokeNoLineEnd("map")
					+ ".getProjectionObject())";
		}
		return "new OpenLayers.Geometry.Point(" + coordinate.x + ", "
				+ coordinate.y + ")" + transformation;
	}

	public abstract String getJSconstructor();

	public void setFeatureStyle(FeatureStyle featureStyle) {
		this.featureStyle = featureStyle;
	}

	public FeatureStyle getFeatureStyle() {
		return featureStyle;
	}

	public void setMap(IOpenLayersMap map) {
		this.map = map;
	}

	public IOpenLayersMap getMap() {
		return map;
	}

	public void setProjection(String projection) {
		this.projection = projection;
	}

	public String getProjection() {
		return projection;
	}

	public void setDisplayInLayer(String displayInLayer) {
		this.displayInLayer = displayInLayer;
	}

	public String getDisplayInLayer() {
		return displayInLayer;
	}
}
