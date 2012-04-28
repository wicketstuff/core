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
package org.wicketstuff.openlayers.api;

import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.io.IClusterable;
import org.wicketstuff.openlayers.IOpenLayersMap;

/**
 * @author mocleiri
 * 
 *         Created as part of the Control conversion from an enumerated type to a class hierarchy.
 * 
 *         Only works for Control's right now but it might be possible to extend to other components
 * 
 */
public interface IJavascriptComponent extends IClusterable
{


	/**
	 * 
	 * @param map
	 * @return the rendered javascript to remove this component from the map.
	 */
	String getJSremove(IOpenLayersMap map);

	/**
	 * 
	 * @param map
	 * @return the rendered javascript to add this component to the map
	 */
	String getJSadd(IOpenLayersMap map);

	/**
	 * Note: currently the naming of a component is related to the concrete class i.e. a WMS layer
	 * would be 'wms' + getId() like: 'wms123456'.
	 * 
	 * The id returned here is the number part not the layer identifier part.
	 * 
	 * @return the unique id for this component. Used for naming purposes.
	 * 
	 */
	public String getId();

	/**
	 * In some cases a javascript component will provide its own javascript resource. This is used
	 * by the map to add in the references.
	 * 
	 * @return
	 * 
	 */
	public JavaScriptResourceReference[] getJSResourceReferences();

}
