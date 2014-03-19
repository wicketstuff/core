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
 * See "load" in the event section of <a href="http://www.google.com/apis/maps/documentation/reference.html#GMap2"
 * >GMap2</a>.
 */
public abstract class LoadListener extends GEventListenerBehavior
{

    /** */
    private static final long serialVersionUID = 1026370336005792416L;

    /**
     * @see wicket.contrib.gmap.event.GEventListenerBehavior#getEvent()
     */
    @Override
    protected String getEvent()
    {
        return "load";
    }

    /**
     * @see wicket.contrib.gmap.event.GEventListenerBehavior#getJSaddListener()
     */
    @Override
    public String getJSaddListener()
    {
        // where notifying immediately (instead of adding a listener) because
        // GMap2 will not fire the "load" event when we are finished adding our
        // listeners :(
        return getGMap().getJSinvoke("onEvent('" + getCallbackUrl() + "', {})");
    }

    /**
     * @see wicket.contrib.gmap.event.GEventListenerBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    protected void onEvent(AjaxRequestTarget target)
    {
        onLoad(target);
    }

    protected abstract void onLoad(AjaxRequestTarget target);
}