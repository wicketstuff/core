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
package org.wicketstuff.gmap.api;

import org.wicketstuff.gmap.GMap;

/**
 * Represents an Google Maps API's <a href= "http://www.google.com/apis/maps/documentation/reference.html#GMapType"
 * >GMapType</a>.
 */
public enum GMapType
{

    ROADMAP, //
    SATELLITE, //
    HYBRID, //
    TERRAIN;//

    public String getJSsetMapType(GMap map)
    {
        return map.getJSinvoke("setMapType(google.maps.MapTypeId." + name() + ")");
    }
}
