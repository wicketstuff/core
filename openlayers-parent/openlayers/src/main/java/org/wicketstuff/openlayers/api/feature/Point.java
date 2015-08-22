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

import java.util.ArrayList;

import org.wicketstuff.openlayers.IOpenLayersMap;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * 
 * @author Marin Mandradjiev (marinsm@hotmail.com)
 * 
 */
public class Point extends Feature
{
	private static final long serialVersionUID = -4295187268790863039L;

	public Point(Coordinate coordinate)
	{
		super();
		getCoordinates().add(coordinate);
	}

	public Point(Coordinate coordinate, FeatureStyle featureStyle)
	{
		super(featureStyle);
		getCoordinates().add(coordinate);
	}

	public Point(Coordinate coordinate, IOpenLayersMap map)
	{
		super(map);
		getCoordinates().add(coordinate);
	}

	public Point(Coordinate coordinate, FeatureStyle featureStyle, IOpenLayersMap map)
	{
		super(featureStyle, map);
		getCoordinates().add(coordinate);
	}

	public void setCoordinate(Coordinate coordinate)
	{
		if (getCoordinates() == null)
			setCoordinates(new ArrayList<Coordinate>());
		else
			getCoordinates().clear();
		getCoordinates().add(coordinate);
	}

	public Coordinate getCoordinate()
	{
		return getCoordinates() != null && !getCoordinates().isEmpty() ? getCoordinates().get(0)
			: null;
	}

	@Override
	protected String getType()
	{
		return null;
	}
}
