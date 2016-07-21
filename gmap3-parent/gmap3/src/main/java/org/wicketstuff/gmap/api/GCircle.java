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
package org.wicketstuff.gmap.api;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.lang.Checks;
import org.wicketstuff.gmap.js.ObjectLiteral;

/**
 * Represents an Google Maps API's
 * <a href= "https://developers.google.com/maps/documentation/javascript/reference?#Circle">Circle</a>.<br/>
 * Following events can be attached to this overlay:<br/>
 * <li>center_changed</li>
 * <li>click</li>
 * <li>dblckick</li>
 * <li>dragend</li>
 * <li>dragstart</li>
 * <li>mousedown</li>
 * <li>mousemove</li>
 * <li>mouseout</li>
 * <li>mouseover</li>
 * <li>mouseup</li>
 * <li>radius_changed</li>
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
 * @see GEvent#radius_changed
 * @see GEvent#rightclick
 */
public class GCircle extends GOverlay
{

	private static final long serialVersionUID = 1L;

	/**
	 * The <a href="https://developers.google.com/maps/documentation/javascript/reference?#CircleOptions">options</a>
	 */
	private final GLatLng center;
	private final double radius;
	private final String strokeColor;
	private final int strokeWeight;
	private final float strokeOpacity;
	private final String fillColor;
	private final float fillOpacity;
	private boolean clickable = true;
	private boolean draggable;
	private boolean editable;
	private boolean visible = true;
	private Integer zIndex;

	public GCircle(GLatLng center, double radius, String strokeColor, int strokeWeight, float strokeOpacity, String fillColor, float fillOpacity)
	{
		this.center = Args.notNull(center, "center");

		Checks.withinRangeShort(1d, Double.MAX_VALUE, radius, "radius");
		this.radius = radius;

		this.strokeColor = strokeColor;
		this.strokeWeight = strokeWeight;
		this.strokeOpacity = strokeOpacity;
		this.fillColor = fillColor;
		this.fillOpacity = fillOpacity;
	}

	@Override
	public String getJSconstructor()
	{
		return ("new google.maps.Circle(" + getSettings().toJS() + ")");
	}

	private ObjectLiteral getSettings()
	{
		ObjectLiteral settings = new ObjectLiteral();

		settings.set("strokeWeight", String.valueOf(strokeWeight));
		settings.setString("strokeColor", strokeColor);
		settings.set("strokeOpacity", String.valueOf(strokeOpacity));
		settings.setString("fillColor", fillColor);
		settings.set("fillOpacity", String.valueOf(fillOpacity));
		settings.set("center", center.toString());
		settings.set("radius", String.valueOf(radius));

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

	public GCircle setClickable(boolean clickable)
	{
		this.clickable = clickable;
		return this;
	}

	public GCircle setDraggable(boolean draggable)
	{
		this.draggable = draggable;
		return this;
	}

	public GCircle setEditable(boolean editable)
	{
		this.editable = editable;
		return this;
	}

	public GCircle setVisible(boolean visible)
	{
		this.visible = visible;
		return this;
	}

	public GCircle setZIndex(Integer zIndex)
	{
		if (zIndex != null)
		{
			Checks.withinRangeShort(0, Integer.MAX_VALUE, zIndex, "zIndex");
		}
		this.zIndex = zIndex;
		return this;
	}
}
