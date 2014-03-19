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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.gmap.GMap;

/**
 * See "zoom_changed" in the event section of <a href="https://developers.google.com/maps/documentation/javascript/reference#Map">
 * google.maps.Map class </a>.
 */
public abstract class ZoomChangedListener extends GEventListenerBehavior
{

    private static final long serialVersionUID = -5092475688071699463L;

    /**
     * @see wicket.contrib.gmap.event.GEventListenerBehavior#getEvent()
     */
    @Override
    protected String getEvent()
    {
        return "zoom_changed";
    }

    /**
     * @see wicket.contrib.gmap.event.GEventListenerBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    protected void onEvent(AjaxRequestTarget target)
    {
        onZoomChanged(target);
    }

    /**
     * Override this method to provide handling of a zoom_changed event.<br>
     * You can get the new center coordinates of the map by calling {@link GMap#getCenter()}.
     *
     * @param target
     * the target that initiated the move
     */
    protected abstract void onZoomChanged(AjaxRequestTarget target);
}