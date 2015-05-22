/*
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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Checks;
import org.wicketstuff.gmap.js.ObjectLiteral;

/**
 * Represents an Google Maps API's
 * <a href="https://developers.google.com/maps/documentation/javascript/reference?#Rectangle">Rectangle</a>.<br/>
 * Following events can be attached to this overlay:<br/>
 * <li>bounds_changed</li>
 * <li>click</li>
 * <li>dblckick</li>
 * <li>dragend</li>
 * <li>dragstart</li>
 * <li>mousedown</li>
 * <li>mousemove</li>
 * <li>mouseout</li>
 * <li>mouseover</li>
 * <li>mouseup</li>
 * <li>rightclick</li>
 *
 * @see GEvent#bounds_changed
 * @see GEvent#click
 * @see GEvent#dblclick
 * @see GEvent#drag
 * @see GEvent#dragend
 * @see GEvent#dragstart
 * @see GEvent#mousedown
 * @see GEvent#mousemove
 * @see GEvent#mouseout
 * @see GEvent#mouseover
 * @see GEvent#mouseup
 * @see GEvent#rightclick
 */
public class GRectangle extends GOverlay
{

	private static final long serialVersionUID = 1L;

	/**
	 * The <a href="https://developers.google.com/maps/documentation/javascript/reference?#RectangleOptions">options</a>.
	 */
	private final GLatLngBounds bounds;
	private boolean clickable = true;
	private boolean draggable;
	private boolean editable;
	private final String fillColor;
	private final float fillOpacity;
	private final String strokeColor;
	private final float strokeOpacity;
	private final int strokeWeight;
	private boolean visible = true;
	private Integer zIndex;

	public GRectangle(GLatLngBounds bounds, String strokeColor, int strokeWeight, float strokeOpacity, String fillColor, float fillOpacity)
	{
		this.bounds = Args.notNull(bounds, "bounds");
		this.strokeColor = strokeColor;
		this.strokeWeight = strokeWeight;
		this.strokeOpacity = strokeOpacity;
		this.fillColor = fillColor;
		this.fillOpacity = fillOpacity;
	}

	@Override
	public String getJSconstructor()
	{
		return ("new google.maps.Rectangle(" + getSettings().toJS() + ")");
	}

	private ObjectLiteral getSettings()
	{
		ObjectLiteral settings = new ObjectLiteral();

		settings.set("strokeWeight", String.valueOf(strokeWeight));
		settings.setString("strokeColor", strokeColor);
		settings.set("strokeOpacity", String.valueOf(strokeOpacity));
		settings.setString("fillColor", fillColor);
		settings.set("fillOpacity", String.valueOf(fillOpacity));
		settings.set("bounds", bounds.toString());

		if (!clickable)
		{
			settings.set("clickable", "false");
		}

		if (draggable)
		{
			settings.set("draggable", "true");
		}

		if (editable)
		{
			settings.set("editable", "true");
		}

		if (!visible)
		{
			settings.set("visible", "false");
		}

		if (zIndex != null)
		{
			settings.set("zIndex", String.valueOf(zIndex));
		}

		return settings;
	}

	@Override
	protected void updateOnAjaxCall(AjaxRequestTarget target, GEvent overlayEvent)
	{
		// empty method
	}

	public GRectangle setClickable(boolean clickable)
	{
		this.clickable = clickable;
		return this;
	}

	public GRectangle setDraggable(boolean draggable)
	{
		this.draggable = draggable;
		return this;
	}

	public GRectangle setEditable(boolean editable)
	{
		this.editable = editable;
		return this;
	}

	public GRectangle setVisible(boolean visible)
	{
		this.visible = visible;
		return this;
	}

	public GRectangle setZIndex(Integer zIndex)
	{
		if (zIndex != null)
		{
			Checks.withinRangeShort(0, Integer.MAX_VALUE, zIndex, "zIndex");
		}
		this.zIndex = zIndex;
		return this;
	}
}
