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
import java.util.ArrayList;
import java.util.List;

import org.wicketstuff.openlayers.IOpenLayersMap;
import org.wicketstuff.openlayers.api.layer.Vector;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * 
 * @author Marin Mandradjiev (marinsm@hotmail.com)
 * 
 */
public abstract class Feature implements Serializable
{
	private static final long serialVersionUID = 364944041007700590L;
	private FeatureStyle featureStyle = null;
	private IOpenLayersMap map = null;
	private String displayInLayer = null;
	private List<Coordinate> coordinates = new ArrayList<Coordinate>();

	public Feature()
	{
		this(null, null);
	}

	public Feature(FeatureStyle featureStyle)
	{
		this(featureStyle, null);
	}

	public Feature(IOpenLayersMap map)
	{
		this(null, map);
	}

	public Feature(FeatureStyle featureStyle, IOpenLayersMap map)
	{
		this.featureStyle = featureStyle;
		this.map = map;
	}

	public String getId()
	{
		return String.valueOf(System.identityHashCode(this));
	}

	protected abstract String getType();

	public String getJSAddFeature(IOpenLayersMap map, Vector vector)
	{
		return getJSconstructor() +
			"var draw" +
			getId() +
			" = new OpenLayers.Feature.Vector(feature" +
			getId() +
			", null, " +
			(featureStyle != null ? featureStyle.getJSGetStyleNoLineEnd(map) : "null") +
			");\n" +
			map.getJSinvoke("addFeature(" + vector.getId() + ", draw" + getId() + ", " + getId() +
				")");
	}

	public String getJSRemoveFeature(IOpenLayersMap map, Vector vector)
	{
		return map.getJSinvoke("removeFeature(" + vector.getId() + ", " + getId() + ")");
	}

	protected String getJScoordinateList(String type, String coordinateList)
	{
		return "var feature" +
			getId() +
			" = " +
			(type == null ? "" : "new " + type + "(") +
			(map != null && map.getBusinessLogicProjection() != null ? map.getJSinvokeNoLineEnd("")
				: "") +
			"convertArray([" +
			coordinateList +
			"]" +
			(map != null && map.getBusinessLogicProjection() != null ? ", \"" +
				map.getBusinessLogicProjection() + "\"" : "") + ")" + (type != null ? ")" : "") +
			";\n";
	}

	public String getJSconstructor()
	{
		StringBuffer coordinateList = new StringBuffer();
		for (Coordinate coordinate : coordinates)
		{
			if (coordinateList.length() > 0)
				coordinateList.append(", ");
			coordinateList.append(coordinate.x + ", " + coordinate.y);
		}
		StringBuffer result = new StringBuffer();
		result.append("var feature" + getId() + " = ");
		if (getType() != null)
			result.append("new " + getType() + "(");
		if (map != null && map.getBusinessLogicProjection() != null)
			result.append(map.getJSinvokeNoLineEnd(""));
		result.append("convertArray([" + coordinateList.toString() + "]");
		if (map != null && map.getBusinessLogicProjection() != null)
			result.append(", \"" + map.getBusinessLogicProjection() + "\"");
		result.append(")");
		if (getType() != null)
			result.append(")");
		result.append(";\n");
		return result.toString();
	}

	public void setFeatureStyle(FeatureStyle featureStyle)
	{
		this.featureStyle = featureStyle;
	}

	public FeatureStyle getFeatureStyle()
	{
		return featureStyle;
	}

	public void setMap(IOpenLayersMap map)
	{
		this.map = map;
	}

	public IOpenLayersMap getMap()
	{
		return map;
	}

	public void setDisplayInLayer(String displayInLayer)
	{
		this.displayInLayer = displayInLayer;
	}

	public String getDisplayInLayer()
	{
		return displayInLayer;
	}

	public void setCoordinates(List<Coordinate> coordinates)
	{
		this.coordinates = coordinates;
	}

	public List<Coordinate> getCoordinates()
	{
		return coordinates;
	}
}
