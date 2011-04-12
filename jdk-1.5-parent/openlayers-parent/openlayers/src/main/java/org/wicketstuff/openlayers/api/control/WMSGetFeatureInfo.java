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
import java.util.Map;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.wicketstuff.openlayers.IOpenLayersMap;

/**
 * @author Michael O'Cleirigh
 * 
 *         See:
 *         http://dev.openlayers.org/releases/OpenLayers-2.8/doc/apidocs/files/OpenLayers/Control
 *         /WMSGetFeatureInfo-js.html
 * 
 */
public class WMSGetFeatureInfo extends AbstractControl
{

	private static final long serialVersionUID = -4288329995840372517L;
	private final Map<String, String> parameters;
	private final AbstractReadOnlyModel<String> onEventJavascript;

	/**
	 * @param parameters
	 *            a list of parameters that will be passed into the javascript constructor.
	 * 
	 *            See: http://openlayers.org/dev/examples/getfeatureinfo-control.html url:
	 *            'http://demo.opengeo.org/geoserver/wms', title: 'Identify features by clicking',
	 *            layers: [water], hover: true, formatOptions: { typeName: 'water_bodies',
	 *            featureNS: 'http://www.openplans.org/topp' }, queryVisible: true
	 * @param onEventJavascript
	 *            any javascript to be called when the 'getfeatureinfo' event is fired (one
	 *            parameter evt in scope).
	 */
	public WMSGetFeatureInfo(HashMap<String, String> parameters,
		AbstractReadOnlyModel<String> onEventJavascript)
	{
		super("WMSGetFeatureInfo", false);
		this.parameters = parameters;
		this.onEventJavascript = onEventJavascript;

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

		if (onEventJavascript != null)
			parameters.put("eventListeners",
				"{getfeatureinfo: function(evt) {" + onEventJavascript.getObject() + "}}");

		// is special as it requires an explicit activation.
		return super.getJSadd(map, parameters) + "\n" + super.getJSinvoke(map, "activate()");
	}


}
