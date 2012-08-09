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
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.gmap.js.Constructor;

/**
 * Represents an Google Maps API's <a href="http://www.google.com/apis/maps/documentation/reference.html#GMarker"
 * >GMarker</a>.
 */
public class GMarker extends GOverlay
{

    private static final long serialVersionUID = 1L;
    private final GMarkerOptions options;

    public GMarker(GMarkerOptions options)
    {
        super();
        this.options = options;
    }

    public GLatLng getLatLng()
    {
        return options.getLatLng();
    }

    public GMarkerOptions getMarkerOptions()
    {
        return options;
    }

    /**
     * @see wicket.contrib.gmap.api.GOverlay#getJSconstructor()
     */
    @Override
    public String getJSconstructor()
    {
        Constructor constructor = new Constructor("google.maps.Marker").add(options.getJSconstructor());
        return constructor.toJS();
    }

    /**
     * @see wicket.contrib.gmap.api.GOverlay#updateOnAjaxCall(org.apache.wicket.ajax.AjaxRequestTarget,
     * wicket.contrib.gmap.api.GEvent)
     */
    @Override
    protected void updateOnAjaxCall(AjaxRequestTarget target, GEvent overlayEvent)
    {
        Request request = RequestCycle.get().getRequest();
        options.setLatLng(GLatLng.parse(request.getRequestParameters().getParameterValue("overlay.latLng").toString()));
    }
}
