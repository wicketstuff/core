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

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.wicketstuff.openlayers.IOpenLayersMap;
import org.wicketstuff.openlayers.api.layer.WMS;
import org.wicketstuff.openlayers.js.JSUtils;
import org.wicketstuff.openlayers.proxy.WFSProxyBehavior;

/**
 * @author mocleiri a <i>propertyName</i> parameter that can be used to adjust which columns are
 *         returned by the request.
 * 
 *         The default OpenLayers implementation does not support this as it needs the spatial data
 *         for the selection of a specific feature.
 * 
 * 
 *         However for some cases you don't need the spatial definition but want to know values for
 *         certain non spatial columns.
 * 
 *         This control lets you specify which specific columns you want returned (in GML).
 * 
 */
public class GetSpecificFeature extends AbstractControl
{

	private static final long serialVersionUID = 1L;

	private static final JavaScriptResourceReference GetSpecificFeature_js = new JavaScriptResourceReference(
		GetSpecificFeature.class, "GetSpecificFeature.js");

	private static final JavaScriptResourceReference GetSpecificFeatureFormat_js = new JavaScriptResourceReference(
		GetSpecificFeature.class, "GetSpecificFeatureFormat.js");

	private Map<String, String> parameters = new LinkedHashMap<String, String>();

	private final WFSProxyBehavior wfsProxy;

	private final String wfsURL;

	private final AbstractAjaxBehavior featureSelectionBehaviour;

	/**
	 * @param layer
	 *            the wms layer that is used to provide the bounding box.
	 * @param featureSelectionBehaviour
	 *            this behaviour will be invoked with an &propertyValue parameter containing the
	 *            value retrieved through WFS.
	 * @param proxyURL
	 *            the full url including the ?url= if a proxy is being used.
	 * @param the
	 *            wfs url
	 * @param featureTypeName
	 *            the name of the feature to be queried.
	 * @param srsNumber
	 *            the EPSG number.
	 * @param the
	 *            property to be included in the response. (typically this is a a primary key or
	 *            unique id for the feature)
	 */
	public GetSpecificFeature(WMS layer, WFSProxyBehavior wfsProxy,
		AbstractAjaxBehavior featureSelectionBehaviour, String wfsURL, String featureTypePrefix,
		String featureTypeUrl, String featureTypeName, int srsNumber, String propertyName)
	{
		super("GetSpecificFeature", false);
		this.wfsProxy = wfsProxy;
		this.featureSelectionBehaviour = featureSelectionBehaviour;
		this.wfsURL = wfsURL;

		parameters.put("featureTypePrefix", JSUtils.getQuotedString(featureTypePrefix));
		parameters.put("featureTypeUrl", JSUtils.getQuotedString(featureTypeUrl));
		parameters.put("featureTypeName", JSUtils.getQuotedString(featureTypeName));
		parameters.put("srsNumber", String.valueOf(srsNumber));
		parameters.put("layer", "wms" + layer.getId());

		parameters.put("propertyName", JSUtils.getQuotedString(propertyName));


	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.wicketstuff.openlayers.api.control.AbstractControl#getJSadd(org.wicketstuff.openlayers
	 * .IOpenLayersMap)
	 */
	@Override
	public String getJSadd(IOpenLayersMap map)
	{

		// the proxy only has the url after it is added to the containing page so we have to declare
// it here not in the constructor.
		parameters.put("wfsUrl", JSUtils.getQuotedString(wfsProxy.getProxyUrl() + wfsURL));

		parameters.put("featureSelectedCallback",
			JSUtils.getQuotedString(featureSelectionBehaviour.getCallbackUrl() + "&propertyValue="));

		return super.getJSadd(map, "OpenLayers.Control.GetSpecificFeature", parameters) + "\n" +
			super.getJSinvoke(map, "activate()");
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wicketstuff.openlayers.api.control.AbstractControl#getJSResourceReference()
	 */
	@Override
	public JavaScriptResourceReference[] getJSResourceReferences()
	{
		return new JavaScriptResourceReference[] { GetSpecificFeatureFormat_js,
				GetSpecificFeature_js };
	}


}
