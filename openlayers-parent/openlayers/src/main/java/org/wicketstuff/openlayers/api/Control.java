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

import org.wicketstuff.openlayers.api.control.AbstractControl;

/**
 * Represents an OpenLayers http://dev.openlayers.org/apidocs/files/OpenLayers/Control-js.html
 * 
 * These are the controls that take no context specific options in the constructor (the
 * externalizable=true controls may include a div but this is not set by the caller).
 * 
 * See @link {@link IJavascriptComponent} for the hierarchy
 * 
 */
public class Control extends AbstractControl
{

	private static final long serialVersionUID = 1L;

	public static final Control PanZoomBar = new Control("PanZoomBar", true);

	public static final Control MouseToolbar = new Control("MouseToolbar", false);
	public static final Control LayerSwitcher = new Control("LayerSwitcher", true);
	public static final Control Permalink = new Control("Permalink", true);
	public static final Control MousePosition = new Control("MousePosition", true);
	public static final Control OverviewMap = new Control("OverviewMap", false);
	public static final Control KeyboardDefaults = new Control("KeyboardDefaults", false);
	public static final Control PanZoom = new Control("PanZoom", false);
	public static final Control Navigation = new Control("Navigation", false);
	public static final Control ScaleLine = new Control("ScaleLine", false);

	/**
	 * @param name
	 * @param externalizable
	 */
	public Control(String name, boolean externalizable)
	{
		super(name, externalizable);
	}


}
