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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.gmap.js.Array;

/**
 * Represents an Google Maps API's <a href= "http://www.google.com/apis/maps/documentation/reference.html#GPolyline"
 * >GPolyline</a>.
 */
public class GPolyline extends GOverlay
{

    private static final long serialVersionUID = 1L;
    private final GLatLng[] latLngs;
    private final String color;
    private final int weight;
    private final float opacity;

    public GPolyline(String color, int weight, float opacity, GLatLng... gLatLngs)
    {
        super();

        this.latLngs = gLatLngs;
        this.color = color;
        this.weight = weight;
        this.opacity = opacity;
    }

    /**
     * @see GOverlay#getJSconstructor()
     */
    @Override
    public String getJSconstructor()
    {
        Array array = new Array();
        for (GLatLng gLatLng : latLngs)
        {
            array.add(gLatLng.getJSconstructor());
        }
        return ("new google.maps.Polyline({strokeWeight: " + weight + ", strokeColor: '" + color + "', strokeOpacity: " + opacity + ", path: " + array.toJS() + "})");
    }

    /**
     * @see GOverlay#updateOnAjaxCall(org.apache.wicket.ajax.AjaxRequestTarget, GEvent)
     */
    @Override
    protected void updateOnAjaxCall(AjaxRequestTarget target, GEvent overlayEvent)
    {
        // empty method
    }
}
