/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) eelco12 $
 * $Revision: 5004 $
 * $Date: 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) $
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
package org.wicketstuff.gmap.event;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.string.StringValueConversionException;
import org.wicketstuff.gmap.api.GLatLng;

/**
 * This event is fired when the user clicks on the map (but not when they click on a marker or infowindow).
 * See "click" in the event section of <a href="https://developers.google.com/maps/documentation/javascript/reference#Map">Map</a>.
 */
public abstract class ClickListener extends GEventListenerBehavior
{

    private static final long serialVersionUID = -6265838789648579340L;

    @Override
    protected String getEvent()
    {
        return "click";
    }

    @Override
    protected void onEvent(AjaxRequestTarget target)
    {
        Request request = RequestCycle.get().getRequest();
        NumberFormat fmt = DecimalFormat.getInstance(Locale.US);
        GLatLng latLng = null;

        String latStr = request.getRequestParameters().getParameterValue("lat").toString();
        String lngStr = request.getRequestParameters().getParameterValue("lng").toString();
        Double lat = null;
        Double lng = null;
        try
        {
          lat = fmt.parse(latStr).doubleValue();
          lng = fmt.parse(lngStr).doubleValue();
        } catch (ParseException e)
        {
          throw new StringValueConversionException("Unable to convert 'lat/lng' to a double value", e);
        }
        latLng = new GLatLng(lat, lng);

        onClick(target, latLng);
    }

    /**
     * Override this method to provide handling of a click on the map. See the event section of <a href=
     * "https://developers.google.com/maps/documentation/javascript/reference#Map" >GMap</a>.
     *
     * @param target
     * The target that initiated the click.
     * @param latLng
     * The clicked GLatLng. 
     */
    protected abstract void onClick(AjaxRequestTarget target, GLatLng latLng);
}