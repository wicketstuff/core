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

/**
 * Represents an Google Maps API's <a href= "https://developers.google.com/maps/documentation/javascript/reference?csw=1#Animation"
 * >Animation</a>.
 */
public enum GAnimation
{

    /** Marker bounces until animation is stopped. */
    BOUNCE,  
    /** Marker falls from the top of the map ending with a small bounce. */
    DROP;    

    @Override
    public String toString() 
    {
        return "google.maps.Animation." + name();
    }
    
    
}
