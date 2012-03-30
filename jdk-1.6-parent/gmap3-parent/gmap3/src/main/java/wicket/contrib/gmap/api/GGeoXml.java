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

import java.net.URL;

import org.apache.wicket.ajax.AjaxRequestTarget;

import wicket.contrib.gmap.js.Constructor;

/**
 * Represents an Google Maps API's <a href="http://www.google.com/apis/maps/documentation/reference.html#GGeoXml"
 * >GGeoXml</a>.
 */
public class GGeoXml extends GOverlay
{
	private static final long serialVersionUID = 1L;

	private final URL _kmlFileUrl;

	/**
	 * @param kmlFileUrl
	 *            the URL of the remote KML file
	 */
	public GGeoXml(URL kmlFileUrl)
	{
		_kmlFileUrl = kmlFileUrl;
	}

	/**
	 * @see wicket.contrib.gmap.api.GOverlay#getJSconstructor()
	 */
	@Override
	public String getJSconstructor()
	{
		Constructor constructor = new Constructor("GGeoXml").add("'" + _kmlFileUrl.toString() + "'");
		return constructor.toJS();
	}

	/**
	 * @see wicket.contrib.gmap.api.GOverlay#updateOnAjaxCall(org.apache.wicket.ajax.AjaxRequestTarget,
	 *      wicket.contrib.gmap.api.GEvent)
	 */
	@Override
	protected void updateOnAjaxCall(AjaxRequestTarget target, GEvent overlayEvent)
	{
	}
}
