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
package wicket.contrib.gmap.api;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;

import wicket.contrib.gmap.GMap2;

/**
 * Represents an Google Maps API's <a href=
 * "http://www.google.com/apis/maps/documentation/reference.html#GOverlay"
 * >GOverlay</a>.
 */
public abstract class GOverlay implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * id of this object. it is session unique.
	 */
	private final String id;

	private GMap2 parent = null;

	private final Map<GEvent, GEventHandler> events = new EnumMap<GEvent, GEventHandler>(
			GEvent.class);

	/**
	 * Construct.
	 */
	public GOverlay()
	{
		// id is session unique
		id = String.valueOf(Session.get().nextSequenceValue());
	}

	/**
	 * @return String representing the JavaScript add command for the
	 *         corresponding JavaScript object.
	 */
	public String getJSadd()
	{
		StringBuffer js = new StringBuffer(parent.getJSinvoke("addOverlay('" + getId() + "', "
				+ getJSconstructor() + ")"));
		// Add the Events
		for (GEvent event : events.keySet())
		{
			js.append(event.getJSadd(this));
		}
		return js.toString();
	}

	/**
	 * @return String representing the JavaScript remove command for the
	 *         corresponding JavaScript object.
	 */
	public String getJSremove()
	{
		StringBuffer js = new StringBuffer();
		// clear the Events
		for (GEvent event : events.keySet())
		{
			js.append(event.getJSclear(this));
		}
		js.append(parent.getJSinvoke("removeOverlay('" + getId() + "')"));
		return js.toString();
	}

	/**
	 * @return The session unique id of this object as a String.
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Implement the needed JavaScript constructor for the corresponding
	 * JavaScript object.
	 * 
	 * @return String representing the JavaScript constructor.
	 */
	public abstract String getJSconstructor();

	public GMap2 getParent()
	{
		return parent;
	}

	public void setParent(GMap2 parent)
	{
		this.parent = parent;
	}

	/**
	 * Add a control.
	 * 
	 * @param control
	 *            control to add
	 * @return This
	 */
	public GOverlay addListener(GEvent event, GEventHandler handler)
	{
		events.put(event, handler);

		if (AjaxRequestTarget.get() != null)
		// TODO
		// && getParent().findPage() != null)
		{
			AjaxRequestTarget.get().appendJavascript(event.getJSadd(this));
		}

		return this;
	}

	/**
	 * Return all registered Listeners.
	 * 
	 * @return registered listeners
	 */
	public Map<GEvent, GEventHandler> getListeners()
	{
		return Collections.unmodifiableMap(events);
	}

	/**
	 * Clear listeners.
	 * 
	 * @param event
	 *            event to be cleared.
	 * @return This
	 */
	public GOverlay clearListeners(GEvent event)
	{
		events.remove(event);

		if (AjaxRequestTarget.get() != null)
		// TODO
		// && findPage() != null)
		{
			AjaxRequestTarget.get().appendJavascript(event.getJSclear(this));
		}

		return this;
	}

	/**
	 * Called when an Ajax call occurs.
	 * 
	 * @param target
	 * @param overlayEvent
	 */
	public void onEvent(AjaxRequestTarget target, GEvent overlayEvent)
	{
		updateOnAjaxCall(target, overlayEvent);
		events.get(overlayEvent).onEvent(target);
	}

	/**
	 * Implement to handle Ajax calls to your needs.
	 * 
	 * @param target
	 * @param overlayEvent
	 */
	protected abstract void updateOnAjaxCall(AjaxRequestTarget target, GEvent overlayEvent);
}