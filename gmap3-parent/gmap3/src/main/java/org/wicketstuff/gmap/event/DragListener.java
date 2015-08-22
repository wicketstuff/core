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

/**
 * See "drag" in the event section of <a href="https://developers.google.com/maps/documentation/javascript/reference#Map">
 * google.maps.Map class </a>.
 */
public abstract class DragListener extends GEventListenerBehavior
{

    private static final long serialVersionUID = -7607546792206488705L;

    @Override
    protected String getEvent()
    {
        return "drag";
    }

    @Override
    protected void onEvent(AjaxRequestTarget target)
    {
        onDrag(target);
    }

    protected abstract void onDrag(AjaxRequestTarget target);
}