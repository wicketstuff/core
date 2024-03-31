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
package org.wicketstuff.openlayers.api.control;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.wicketstuff.openlayers.IOpenLayersMap;
import org.wicketstuff.openlayers.api.layer.WMS;
import org.wicketstuff.openlayers.js.JSUtils;

/**
 * @author mocleiri
 * 
 *         See:
 *         http://dev.openlayers.org/releases/OpenLayers-2.8/doc/apidocs/files/OpenLayers/Control
 *         /GetFeature-js.html
 */
public class GetFeature extends AbstractControl
{

	private static final long serialVersionUID = 1L;
	private final HashMap<String, String> parameters;

	/**
	 * Useful if you want full control over the parameters
	 * 
	 * @param parameters
	 * 
	 */
	public GetFeature(HashMap<String, String> parameters)
	{
		super("GetFeature", false);
		this.parameters = parameters;

	}

	/*
	 * Standard constructor that builds the parameter map as required.
	 * 
	 * @param layer
	 * 
	 * @param box
	 * 
	 * @param hover
	 * 
	 * @param multipleKey
	 * 
	 * @param toggleKey
	 */
	public GetFeature(WMS layer, boolean box, boolean hover, String multipleKey, String toggleKey)
	{

		super("GetFeature", false);
		parameters = new LinkedHashMap<String, String>();

		// default to getting the protocol from the existing WMS layer.
		parameters.put("protocol", "OpenLayers.Protocol.WFS.fromWMSLayer(wms" + layer.getId() + ")");

		parameters.put("box", String.valueOf(box));
		parameters.put("hover", String.valueOf(hover));

		if (multipleKey != null && multipleKey.trim().length() > 0)
			parameters.put("multipleKey", JSUtils.getQuotedString(multipleKey));

		if (toggleKey != null && toggleKey.trim().length() > 0)
			parameters.put("toggleKey", JSUtils.getQuotedString(toggleKey));

	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wicketstuff.openlayers.api.IJavascriptControl#getJSadd(org.wicketstuff.openlayers.
	 * IOpenLayersMap)
	 */
	@Override
	public String getJSadd(IOpenLayersMap map)
	{

		// is special as it requires an explicit activation.
		// so we append the activate() command after the control is created.
		return super.getJSadd(map, parameters) + "\n" + super.getJSinvoke(map, "activate()");
	}


}
