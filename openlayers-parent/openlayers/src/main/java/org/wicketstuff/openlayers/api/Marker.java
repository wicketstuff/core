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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.wicketstuff.openlayers.IOpenLayersMap;
import org.wicketstuff.openlayers.event.EventType;
import org.wicketstuff.openlayers.js.Constructor;

/**
 * Represents an Openlayers API's http://dev.openlayers.org/apidocs/files/OpenLayers/Marker-js.html
 */
public class Marker extends Overlay
{
	private static final long serialVersionUID = 1L;

	private EventType[] events = new EventType[] { };

	private Icon icon = null;

	private LonLat lonLat;

	private PopupWindowPanel popup = null;

	private IOpenLayersMap map = null;

	/**
	 * @param gLatLng
	 *            the point on the map where this marker will be anchored
	 */
	public Marker(LonLat gLatLng)
	{
		this(gLatLng, new EventType[] { }, null);
	}

	public Marker(LonLat gLatLng, IOpenLayersMap map)
	{
		this(gLatLng, new EventType[] { }, null);
		this.map = map;
	}

	public Marker(LonLat lonLat, EventType[] events, PopupWindowPanel popup)
	{
		this(lonLat, popup, events, null);
	}

	public Marker(LonLat lonLat, EventType[] events)
	{
		this(lonLat, events, null);
	}

	public Marker(LonLat gLatLng, PopupWindowPanel popup)
	{
		this(gLatLng, null, popup);
	}

	public Marker(LonLat lonLat, PopupWindowPanel popup, EventType[] events, Icon icon)
	{
		super();
		this.lonLat = lonLat;
		this.popup = popup;
		this.icon = icon;
		this.events = events;
	}

	public Marker(LonLat lonLat, PopupWindowPanel popup, Icon icon)
	{
		this(lonLat, popup, new EventType[] { }, icon);
	}

	public void addEvent(EventType evt)
	{
		if (events == null)
		{
			events = new EventType[] { };
		}

		List<EventType> eventList = new ArrayList<EventType>(Arrays.asList(events));
		eventList.add(evt);

		events = new EventType[eventList.size()];
		eventList.toArray(events);
	}

	public EventType[] getEvents()
	{
		return events;
	}

	public Icon getIcon()
	{
		return icon;
	}

	@Override
	protected String getJSconstructor()
	{
		String transformation = "";
		if (map != null && map.getBusinessLogicProjection() != null)
		{
			transformation = ".transform(new OpenLayers.Projection(\"" +
				map.getBusinessLogicProjection() + "\"), " + map.getJSinvokeNoLineEnd("map") +
				".getProjectionObject())";
		}
		Constructor constructor = new Constructor("OpenLayers.Marker").add(lonLat.getJSconstructor() +
			transformation);
		if (icon != null)
		{
			constructor.add(icon.getId());
		}

		return constructor.toJS();
	}

	public LonLat getLonLat()
	{
		return lonLat;
	}

	public PopupWindowPanel getPopup()
	{
		return popup;
	}

	public void setIcon(Icon icon)
	{
		this.icon = icon;
	}

	public void setLagLng(LonLat gLatLng)
	{
		lonLat = gLatLng;
	}

	public void setMap(IOpenLayersMap map)
	{
		this.map = map;
	}

	public IOpenLayersMap getMap()
	{
		return map;
	}
}
