/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) eelco12 $
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
package org.wicketstuff.gmap.api;

/**
 * An enumeration of all supported events by Google Maps v3
 * Taken from <a href="http://gmaps-samples-v3.googlecode.com/svn/trunk/map_events/map_events.html">Map Events</a>
 */
public enum GEvent
{

	closeclick, // Close-click event for InfoWindow
	content_changed, // Content changed event for InfoWindow
	domready, // Event for InfoWindow which is fired when the <div> containing the InfoWindow's content is attached to the DOM
	bounds_changed, // fired when the viewport bounds have changed.
	center_changed, // fired when the map center property changes.
	radius_changed, // fired when the radius property changes (Circle).
	click, // fired when the user clicks on the map (but not when they click on a marker or infowindow).
	dblclick, // fired when the user double-clicks on the map. Note that the click event will also fire, right before this one.
	drag, // repeatedly fired while the user drags the map.
	dragend, // fired when the user stops dragging the map.
	dragstart, // fired when the user starts dragging the map.
	heading_changed, // fired when the map heading property changes.
	idle, // fired when the map becomes idle after panning or zooming.
	maptypeid_changed, // fired when the mapTypeId property changes.
	mousedown, // fired when the DOM mousedown event is fired on the overlay.
	mousemove, // fired whenever the user's mouse moves over the map container.
	mouseout, // fired when the user's mouse exits the map container.
	mouseover, // fired when the user's mouse enters the map container.
	mouseup, // fired when the DOM mouseup event is fired on the overlay.
	projection_changed, // fired when the projection has changed.
	resize, // Developers should trigger this event on the map when the div changes size: google.maps.event.trigger(map, 'resize') .
	rightclick, // fired when the DOM contextmenu event is fired on the map container.
	tilesloaded, // fired when the visible tiles have finished loading.
	tilt_changed, // fired when the map tilt property changes.
	zoom_changed;        // fired when the map zoom property changes.

	/**
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString()
	{
		return super.toString().toLowerCase();
	}

	public String getJSadd(final GOverlay overlay, final String function)
	{
		String mapMarkupId = overlay.getParent().getMapId();
		return String.format("google.maps.event.addListener(Wicket.maps['%s'].overlays['overlay%s'], '%s', %s);\n",
				mapMarkupId, overlay.getId(), name(), function);
	}

	public String getJSadd(final GOverlay overlay)
	{
		return overlay.getParent().getJSinvoke(
				"addOverlayListener('overlay" + overlay.getId() + "', '" + name() + "');\n");
	}

	public String getJSclear(final GOverlay overlay)
	{
		return overlay.getParent().getJSinvoke("clearOverlayListeners('overlay" + overlay.getId() + "', '" + name() + "')");
	}
}
