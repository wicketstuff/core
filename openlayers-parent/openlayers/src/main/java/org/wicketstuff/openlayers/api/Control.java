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

import java.io.Serializable;

import org.wicketstuff.openlayers.OpenLayersMap;

/**
 * Represents an OpenLayers
 * http://dev.openlayers.org/apidocs/files/OpenLayers/Control-js.html
 */
public enum Control implements Serializable {
	PanZoomBar(true), MouseToolbar(false), LayerSwitcher(true), Permalink(true), MousePosition(
			true), OverviewMap(false), KeyboardDefaults(false), PanZoom(false), Navigation(
			false);

	private final boolean externalizable;

	private Control(boolean externalizable) {
		this.externalizable = externalizable;

	}

	public String getJSadd(OpenLayersMap map) {

		String jsControlCreate = "";

		if (map.isExternalControls() && externalizable) {
			jsControlCreate = map.getJSinvoke("addControl('" + name()
					+ "', new OpenLayers.Control." + name()
					+ "({div: OpenLayers.Util.getElement('wicketOpenlayer"
					+ name() + "')}))");
		} else {
			jsControlCreate = map.getJSinvoke("addControl('" + name()
					+ "', new OpenLayers.Control." + name() + "())");
		}
		return jsControlCreate;
	}

	public String getJSremove(OpenLayersMap map) {
		return map.getJSinvoke("removeControl('" + name() + "')");
	}
}
