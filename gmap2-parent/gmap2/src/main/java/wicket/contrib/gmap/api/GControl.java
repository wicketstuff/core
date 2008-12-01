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

import java.io.Serializable;

import wicket.contrib.gmap.GMap2;

/**
 * Represents an Google Maps API's
 * <a href="http://www.google.com/apis/maps/documentation/reference.html#GControl">GControl</a>.
 */
public enum GControl implements Serializable
{
	GSmallMapControl, GLargeMapControl, GSmallZoomControl, GScaleControl, GMapTypeControl;

	public String getJSadd(GMap2 map)
	{
		return map.getJSinvoke("addControl('" + name() + "', new " + name() + "())");
	}

	public String getJSremove(GMap2 map)
	{
		return map.getJSinvoke("removeControl('" + name() + "')");
	}
}
