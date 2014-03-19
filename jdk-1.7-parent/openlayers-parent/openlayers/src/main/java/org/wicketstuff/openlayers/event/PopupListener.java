/*
 * $Revision: 5004 $
 * $Date: 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) $
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
package org.wicketstuff.openlayers.event;


import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.openlayers.OpenLayersMap;
import org.wicketstuff.openlayers.api.LonLat;
import org.wicketstuff.openlayers.api.Marker;
import org.wicketstuff.openlayers.api.Overlay;
import org.wicketstuff.openlayers.api.Size;

/**
 * event Listener
 */
public abstract class PopupListener extends AbstractDefaultAjaxBehavior
{

	private static final long serialVersionUID = 1L;

	private boolean wantEvents;

	private OpenLayersMap openLayersMap;

	public OpenLayersMap getOpenLayersMap()
	{
		return openLayersMap;
	}

	public void setOpenLayersMap(OpenLayersMap openLayersMap)
	{
		this.openLayersMap = openLayersMap;
	}

	public PopupListener(boolean wantEvents)
	{
		this.wantEvents = wantEvents;

	}

	@Override
	protected void onBind()
	{
		if (!(getComponent() instanceof OpenLayersMap))
		{
			throw new IllegalArgumentException("must be bound to OpenlayersMap");
		}
	}

	@Override
	protected final void respond(AjaxRequestTarget target)
	{

		onEvent(target);
	}

	protected final OpenLayersMap getOpenLayerMap()
	{
		return (OpenLayersMap)getComponent();
	}

	protected void onEvent(AjaxRequestTarget target)
	{
		Request request = RequestCycle.get().getRequest();

		Overlay overlay = null;

		String markerParameter = request.getRequestParameters()
			.getParameterValue("marker")
			.toString();
		if (markerParameter != null)
		{
			OpenLayersMap map = getOpenLayerMap();
			for (Overlay ovl : map.getOverlays())
			{
				if (ovl.getId().equals(markerParameter))
				{
					overlay = ovl;
					break;
				}
			}
		}
		String markerEvent = request.getRequestParameters().getParameterValue("event").toString();

		if (wantEvents)
		{
			// Translate from string to type!
			EventType eventType = EventType.valueOf(markerEvent);
			onEvent(target, overlay, eventType);
		}
		else
		{
			onClick(target, overlay);
		}
	}

	public String getCallBackForMarker(Marker marker)
	{
		return getCallbackUrl() + "&marker=" + marker.getId();

	}

	/**
	 * helper method
	 * 
	 * @param marker
	 */
	public void clickAndOpenPopup(Marker marker, AjaxRequestTarget target)
	{
		String mapId = getOpenLayerMap().getJSInstance();
		String jsToRun = "if (" + mapId + ".popup != null) {" + "		" + mapId + ".map.removePopup(" +
			mapId + ".popup);" + "		" + mapId + ".popup.destroy();" + "		" + mapId +
			".popup = null;" + "}";

		target.prependJavaScript(jsToRun);

		// Currently only support clicking on markers!
		getOpenLayerMap().getInfoWindow().getContent().replaceWith(marker.getPopup());
		getOpenLayerMap().getInfoWindow().setContent(marker.getPopup());
		target.add(marker.getPopup());
		jsToRun = mapId + ".popup = new OpenLayers.Popup('map', " +
			new LonLat(marker.getLonLat().getLng(), marker.getLonLat().getLat()) + ", " +
			new Size(195, 250).getJSconstructor() + ", document.getElementById(" + mapId +
			".popupId).innerHTML, true);" + mapId + ".popup.setBackgroundColor('white');" + mapId +
			".map.addPopup(" + mapId + ".popup);";

		// open info window
		target.appendJavaScript(jsToRun);
	}

	/**
	 * close the popup
	 * 
	 * @param target
	 */
	public void closePopup(AjaxRequestTarget target)
	{
		String mapId = getOpenLayerMap().getJSInstance();
		String jsToRun = "if (" + mapId + ".popup != null) {" + "		" + mapId + ".map.removePopup(" +
			mapId + ".popup);" + "		" + mapId + ".popup.destroy();" + "		" + mapId +
			".popup = null;" + "}";

		target.appendJavaScript(jsToRun);
	}

	/**
	 * Override this method to provide handling of a click on the marker. Only passes onClick events
	 * 
	 * @param overlay
	 *            The clicked overlay.
	 * @param target
	 *            The target that initiated the click.
	 */
	protected void onClick(AjaxRequestTarget target, Overlay overlay)
	{
	};

	/**
	 * Override this method to provide handling of a event on the marker.
	 * <Strong>Remember:</Strong>nullevent are passed by popupMarkerInfoAttributeAppender
	 * 
	 * @param overlay
	 *            The clicked overlay.
	 * @param target
	 *            The target that initiated the click.
	 */
	protected void onEvent(AjaxRequestTarget target, Overlay overlay, EventType event)
	{

	};
}
