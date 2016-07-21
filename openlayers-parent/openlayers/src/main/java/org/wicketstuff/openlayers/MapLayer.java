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
package org.wicketstuff.openlayers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.wicketstuff.openlayers.api.IMapCenter;
import org.wicketstuff.openlayers.api.LonLat;
import org.wicketstuff.openlayers.api.Marker;
import org.wicketstuff.openlayers.api.feature.Feature;
import org.wicketstuff.openlayers.api.feature.FeatureStyle;
import org.wicketstuff.openlayers.api.layer.Layer;

/**
 * This is experimental! It might be deleted without notice!
 * 
 * @author Marin Mandradjiev (marinsm@hotmail.com)
 * 
 */
public class MapLayer implements Serializable
{
	private static final long serialVersionUID = 3495178737214944354L;

	private final IMapCenter init_location;
	private AjaxOpenLayersMap map = null;
	private LonLat center = null;
	private Integer zoom = null;
	private String businessLogicProjection = null;
	private String markersLayerName = null;
	private final Map<String, Marker> markerList = new HashMap<String, Marker>();
	private final Map<String, Feature2FeatureStyle> featureList = new HashMap<String, Feature2FeatureStyle>();
	private final Map<String, FeatureStyle> featureStyleList = new HashMap<String, FeatureStyle>();

	public MapLayer(IMapCenter init_location, Object... values)
	{
		this.init_location = init_location;
		init(values);
	}

	protected void init(Object... values)
	{
	};

	public void addMarker(String uniqKey, Marker marker)
	{
		if (markerList.containsKey(uniqKey))
		{
			removeMarker(uniqKey);
		}
		markerList.put(uniqKey, marker);
		if (map != null)
		{
			map.addOverlay(marker);
		}
	}

	public Marker getMarker(String uniqKey)
	{
		return markerList.get(uniqKey);
	}

	public void removeMarker(String uniqKey)
	{
		Marker remove = markerList.remove(uniqKey);
		if (remove != null && map != null)
		{
			map.removeOverlay(remove);
		}
	}

	public void addFeature(String uniqKey, Feature feature)
	{
		addFeature(uniqKey, feature, null);
	}

	public void addFeature(String uniqKey, Feature feature, String baseFeatureUniqKey, String color)
	{
		FeatureStyle baseFeatureStyle = featureStyleList.get(baseFeatureUniqKey);
		FeatureStyle featureStyle = baseFeatureStyle == null || color == null ? null
			: new FeatureStyle(baseFeatureStyle);
		if (featureStyle != null)
		{
			featureStyle.setFillColor(color);
			featureStyle.setStrokeColor(color);
		}
		addFeature(baseFeatureUniqKey, feature, featureStyle);
	}

	public void addFeature(String uniqKey, Feature feature, FeatureStyle featureStyle)
	{
		if (featureList.containsKey(uniqKey))
		{
			removeFeature(uniqKey);
		}
		if (featureStyle != null)
		{
			feature.setFeatureStyle(featureStyle);
		}
		featureList.put(uniqKey, new Feature2FeatureStyle(feature, featureStyle));
		if (map != null)
		{
			if (featureStyle != null)
			{
				map.addFeatureStyle(featureStyle);
			}
			map.addFeature(feature);
		}
	}

	public Feature getFeature(String uniqKey)
	{
		Feature2FeatureStyle result = featureList.get(uniqKey);
		return result == null ? null : result.getFeature();
	}

	public void removeFeature(String uniqKey)
	{
		Feature2FeatureStyle remove = featureList.remove(uniqKey);
		if (remove != null && map != null)
		{
			map.removeFeature(remove.getFeature());
			if (remove.getFeatureStyle() != null)
			{
				map.removeFeatureStyle(remove.getFeatureStyle());
			}
		}
	}

	public void addFeatureStyle(String uniqKey, FeatureStyle featureStyle)
	{
		if (featureStyleList.containsKey(uniqKey))
		{
			removeFeatureStyle(uniqKey);
		}
		featureStyleList.put(uniqKey, featureStyle);
		if (map != null)
		{
			map.addFeatureStyle(featureStyle);
		}
	}

	public FeatureStyle getFeatureStyle(String uniqKey)
	{
		return featureStyleList.get(uniqKey);
	}

	public void removeFeatureStyle(String uniqKey)
	{
		FeatureStyle remove = featureStyleList.remove(uniqKey);
		if (remove != null && map != null)
		{
			map.removeFeatureStyle(remove);
		}
	}

	public void setCenter(IMapCenter mapCenter)
	{
		setCenter(mapCenter.getLongitude(), mapCenter.getLatitude());
		setZoom(mapCenter.getZoom());
	}

	public void setCenter(Double longitude, Double latitude)
	{
		if (longitude != null && latitude != null)
		{
			setCenter(new LonLat(longitude.doubleValue(), latitude.doubleValue()));
		}
	}

	public void setCenter(LonLat center)
	{
		this.center = center;
		if (map != null)
		{
			map.setCenter(center);
		}
	}

	public LonLat getCenter()
	{
		if (map != null)
		{
			center = map.getCenter();
		}
		return center;
	}

	public void setZoom(Integer zoom)
	{
		this.zoom = zoom;
		if (map != null)
		{
			map.setZoom(zoom);
		}
	}

	public Integer getZoom()
	{
		if (map != null)
		{
			zoom = map.getZoom();
		}
		return zoom;
	}

	public void setBusinessLogicProjection(String businessLogicProjection)
	{
		this.businessLogicProjection = businessLogicProjection;
		if (map != null)
		{
			map.setBusinessLogicProjection(businessLogicProjection);
		}
	}

	public String getBusinessLogicProjection()
	{
		if (map != null)
		{
			businessLogicProjection = map.getBusinessLogicProjection();
		}
		return businessLogicProjection;
	}

	public void setMarkersLayerName(String markersLayerName)
	{
		this.markersLayerName = markersLayerName;
		if (map != null)
		{
			map.setMarkersLayerName(markersLayerName);
		}
	}

	public String getMarkersLayerName()
	{
		if (map != null)
		{
			markersLayerName = map.getMarkersLayerName();
		}
		return markersLayerName;
	}

	public void updateInitLocation()
	{
		if (init_location != null)
		{
			init_location.setLongitude(center == null ? null : center.getLng());
			init_location.setLatitude(center == null ? null : center.getLat());
			init_location.setZoom(zoom == null ? null : zoom.intValue());
		}
	}

	public AjaxOpenLayersMap getMapPanel(final String id, final List<Layer> layers,
		final HashMap<String, String> options)
	{
		if (map == null)
		{
			if (init_location != null)
			{
				if (center == null)
				{
					setCenter(init_location.getLongitude(), init_location.getLatitude());
				}
				if (zoom == null && init_location.getZoom() != null)
				{
					zoom = init_location.getZoom().intValue();
				}
			}
			map = new AjaxOpenLayersMap(id, layers, options);
			if (businessLogicProjection != null)
			{
				map.setBusinessLogicProjection(businessLogicProjection);
			}
			if (center != null && zoom != null)
			{
				map.setCenter(center, zoom);
			}
			if (markersLayerName != null)
			{
				map.setMarkersLayerName(markersLayerName);
			}
			for (Marker item : markerList.values())
			{
				map.addOverlay(item);
			}
			for (FeatureStyle item : featureStyleList.values())
			{
				map.addFeatureStyle(item);
			}
			for (Feature2FeatureStyle item : featureList.values())
			{
				if (item.getFeatureStyle() != null)
				{
					map.addFeatureStyle(item.getFeatureStyle());
				}
				map.addFeature(item.getFeature());
			}
		}
		return map;
	}

	public AjaxOpenLayersMap getMapPanel()
	{
		return map;
	}

	public void reset()
	{
		map = null;
		markerList.clear();
		featureList.clear();
		featureStyleList.clear();
		center = null;
		zoom = null;
		businessLogicProjection = null;
		markersLayerName = null;
	}

	public void cleanMapPanel(Object... values)
	{
		while (!featureList.isEmpty())
		{
			removeFeature(featureList.keySet().iterator().next());
		}
		while (!featureStyleList.isEmpty())
		{
			removeFeatureStyle(featureStyleList.keySet().iterator().next());
		}
		while (!markerList.isEmpty())
		{
			removeMarker(markerList.keySet().iterator().next());
		}
		if (init_location != null)
		{
			setCenter(init_location);
		}
		init(values);
	}

	public final Set<String> getMarkerIdList()
	{
		return markerList.keySet();
	}

	public final Set<String> getFeatureIdList()
	{
		return featureList.keySet();
	}

	public final Set<String> getFeatureStyleIdList()
	{
		return featureStyleList.keySet();
	}

	private static class Feature2FeatureStyle implements Serializable
	{
		private static final long serialVersionUID = 3495178735014944354L;

		private final Feature feature;
		private final FeatureStyle featureStyle;

		public Feature2FeatureStyle(Feature feature, FeatureStyle featureStyle)
		{
			this.feature = feature;
			this.featureStyle = featureStyle;
		}

		public Feature getFeature()
		{
			return feature;
		}

		public FeatureStyle getFeatureStyle()
		{
			return featureStyle;
		}
	}
}
