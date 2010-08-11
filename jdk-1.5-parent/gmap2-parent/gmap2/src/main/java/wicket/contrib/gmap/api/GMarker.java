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

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;

import wicket.contrib.gmap.js.Constructor;

/**
 * Represents an Google Maps API's <a
 * href="http://www.google.com/apis/maps/documentation/reference.html#GMarker">GMarker</a>.
 */
public class GMarker extends GOverlay
{
	private static final long serialVersionUID = 1L;

	private GLatLng latLng;

	private final GMarkerOptions options;

	/**
	 * @param gLatLng
	 *            the point on the map where this marker will be anchored
	 */
	public GMarker(GLatLng gLatLng)
	{
		this(gLatLng, null);
	}

	public GMarker(GLatLng gLatLng, GMarkerOptions options)
	{
		super();
		this.latLng = gLatLng;
		this.options = options;
	}

	public GLatLng getLatLng()
	{
		return latLng;
	}

	public void setLatLng(GLatLng gLatLng)
	{
		this.latLng = gLatLng;
	}

	public GMarkerOptions getMarkerOptions()
	{
		return this.options;
	}

	@Override
	public String getJSconstructor()
	{
		Constructor constructor = new Constructor("GMarker").add(latLng.getJSconstructor());
		if (options != null)
		{
			constructor.add(options.getJSconstructor());
		}
		return constructor.toJS();
	}

	@Override
	protected void updateOnAjaxCall(AjaxRequestTarget target, GEvent overlayEvent)
	{
		Request request = RequestCycle.get().getRequest();
		this.latLng = GLatLng.parse(request.getParameter("overlay.latLng"));
	}
}
