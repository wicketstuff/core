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
package org.wicketstuff.examples.gmap.ggroundOverlay;

import org.wicketstuff.examples.gmap.WicketExamplePage;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GGroundOverlay;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GLatLngBounds;

/**
 * Demonstrates the use of ground-overlays.
 */
public class GGroundOverlayPage extends WicketExamplePage
{

    private static final long serialVersionUID = 1L;
    private final GMap map;

    public GGroundOverlayPage()
    {
        map = new GMap("map");
        map.setCenter(new GLatLng(40.740, -74.18));
        map.setZoom(12);

        // ground overlay

        GLatLngBounds boundaries = new GLatLngBounds(new GLatLng(40.716216, -74.213393), new GLatLng(40.765641, -74.139235));
        GGroundOverlay oldmap = new GGroundOverlay("http://www.lib.utexas.edu/maps/historical/newark_nj_1922.jpg", boundaries);
        map.addOverlay(oldmap);
        add(map);
    }
}
