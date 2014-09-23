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

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.gmap.GMap;

/**
 * Represents an Google Maps API's <a href= "http://www.google.com/apis/maps/documentation/reference.html#GOverlay"
 * >GOverlay</a>.
 */
public abstract class GOverlay implements Serializable
{

    private static final long serialVersionUID = 1L;
    /**
     * id of this object. it is session unique.
     */
    private final String id;
    private GMap parent = null;
    private final Map<GEvent, GEventHandler> events = new EnumMap<GEvent, GEventHandler>(GEvent.class);
    private final Map<GEvent, String> functions = new EnumMap<GEvent, String>(GEvent.class);

    /**
     * Construct.
     */
    public GOverlay()
    {
        // id is session unique
        id = String.valueOf(Session.get().nextSequenceValue());
    }
    
    /**
     * Used in stateless environment as session id's are changing continuously without state.
     */
    public GOverlay(String id)
    {
        this.id = id;
    }

    /**
     * @return String representing the JavaScript add command for the corresponding JavaScript object.
     */
    public String getJS()
    {
        return addFunctions(addEvents(addOverlays(new StringBuilder()))).toString();
    }

    private StringBuilder addOverlays(final StringBuilder js)
    {
        js.append(parent.getJSinvoke("addOverlay('overlay" + getId() + "', " + getJSconstructor() + ")"));
        js.append(parent.getJSinvoke("overlays['overlay" + getId() + "'].setMap(Wicket.maps['" + parent.getMapId() + "'].map)"));
        return js;
    }

    private CharSequence addFunctions(final StringBuilder js)
    {
        for (final GEvent event : functions.keySet())
        {
            js.append(event.getJSadd(this, functions.get(event)));
        }
        return js;

    }

    private StringBuilder addEvents(final StringBuilder js)
    {
        for (final GEvent event : events.keySet())
        {
            js.append(event.getJSadd(this));
        }
        return js;

    }

    /**
     * @return String representing the JavaScript remove command for the corresponding JavaScript object.
     */
    public String getJSremove()
    {
        final StringBuilder js = new StringBuilder();
        // clear the Events
        for (final GEvent event : events.keySet())
        {
            js.append(event.getJSclear(this));
        }
        js.append(parent.getJSinvoke("removeOverlay('overlay" + getId() + "')"));

        return js.toString();
    }

    /**
     * @return The session unique id of this object as a String.
     */
    public String getId()
    {
        return id;
    }

    /**
     * Implement the needed JavaScript constructor for the corresponding JavaScript object.
     *
     * @return String representing the JavaScript constructor.
     */
    public abstract String getJSconstructor();

    public GMap getParent()
    {
        return parent;
    }

    protected Page getPage()
    {
        Page page = null;
        if (getParent() != null)
        {
            page = getParent().getPage();
        }
        return page;
    }

    public void setParent(final GMap parent)
    {
        this.parent = parent;
    }

    public GOverlay addListener(final GEvent event, final GEventHandler handler)
    {
        events.put(event, handler);

        AjaxRequestTarget target = RequestCycle.get().find(AjaxRequestTarget.class);
        if (target != null && getPage() != null)
        {
            target.appendJavaScript(event.getJSadd(this));
        }

        return this;
    }

    /**
     * Add a none Ajax Event.
     */
    public GOverlay addFunctionListener(final GEvent event, final String jsFunction)
    {
        functions.put(event, jsFunction);

        AjaxRequestTarget target = RequestCycle.get().find(AjaxRequestTarget.class);
        if (target != null && getPage() != null)
        {
            target.appendJavaScript(event.getJSadd(this, functions.get(event)));
        }

        return this;
    }

    /**
     * Return all registered Listeners.
     *
     * @return registered listeners
     */
    public Map<GEvent, GEventHandler> getListeners()
    {
        return Collections.unmodifiableMap(events);
    }

    /**
     * Return all registered Listeners.
     *
     * @return registered listeners
     */
    public Map<GEvent, String> getFunctionListeners()
    {
        return Collections.unmodifiableMap(functions);
    }

    /**
     * Clear listeners.
     *
     * @param event
     * event to be cleared.
     * @return This
     */
    public GOverlay clearListeners(final GEvent event)
    {
        events.remove(event);

        AjaxRequestTarget target = RequestCycle.get().find(AjaxRequestTarget.class);
        if (target != null)
        {
            target.appendJavaScript(event.getJSclear(this));
        }

        return this;
    }

    /**
     * Called when an Ajax call occurs.
     *
     * @param target
     * @param overlayEvent
     */
    public void onEvent(final AjaxRequestTarget target, final GEvent overlayEvent)
    {
        updateOnAjaxCall(target, overlayEvent);
        events.get(overlayEvent).onEvent(target);
    }

    /**
     * Implement to handle Ajax calls to your needs.
     *
     * @param target
     * @param overlayEvent
     */
    protected abstract void updateOnAjaxCall(AjaxRequestTarget target, GEvent overlayEvent);
}
