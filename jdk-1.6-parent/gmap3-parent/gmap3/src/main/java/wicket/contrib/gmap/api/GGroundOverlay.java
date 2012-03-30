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
package wicket.contrib.gmap.api;

import org.apache.wicket.ajax.AjaxRequestTarget;

import wicket.contrib.gmap.js.Constructor;

public class GGroundOverlay extends GOverlay
{

	private static final long serialVersionUID = 1L;

	private final GLatLngBounds _bounds;

	private final String _imageUrl;

	public GGroundOverlay(String imageUrl, GLatLngBounds bounds)
	{
		super();
		_bounds = bounds;
		_imageUrl = imageUrl;
	}

	/**
	 * @see wicket.contrib.gmap.api.GOverlay#getJSconstructor()
	 */
	@Override
	public String getJSconstructor()
	{
		Constructor constructor = new Constructor("GGroundOverlay");
		constructor.addString(_imageUrl);
		constructor.add(_bounds.getJSconstructor());
		return constructor.toJS();
	}

	/**
	 * @see wicket.contrib.gmap.api.GOverlay#updateOnAjaxCall(org.apache.wicket.ajax.AjaxRequestTarget,
	 *      wicket.contrib.gmap.api.GEvent)
	 */
	@Override
	protected void updateOnAjaxCall(AjaxRequestTarget target, GEvent overlayEvent)
	{
		// TODO
	}

}