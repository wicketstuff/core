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
package wicket.contrib.examples.gmap.refreshpoint;

import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.time.Duration;

import wicket.contrib.gmap3.GMap;

/**
 * @author Maes Gregory, Anyware Technologies
 */
public abstract class GMapAutoUpdatingBehavior extends AbstractDefaultAjaxBehavior {

    private static final long serialVersionUID = 1L;

    private final Duration _updateInterval;

    private boolean stopped = false;

    /**
     * Construct.
     * 
     * @param updateInterval
     *            Duration between AJAX callbacks
     */
    public GMapAutoUpdatingBehavior( final Duration updateInterval ) {
        _updateInterval = updateInterval;
    }

    @Override
    protected void onBind() {
        if ( !( getComponent() instanceof GMap ) ) {
            throw new IllegalArgumentException( "must be bound to GMap2" );
        }
        getGMap().setOutputMarkupId( true );
    }

    protected final GMap getGMap() {
        return (GMap) getComponent();
    }

    /**
     * Stops the timer
     */
    public final void stop() {
        stopped = true;
    }

    @Override
    protected void onComponentRendered() {
        if ( !stopped ) {
            Response response = RequestCycle.get().getResponse();
            response.write( "<script type=\"text/javascript\">" );
            response.write( getJsTimeoutCall( _updateInterval ) );
            response.write( "</script>" );
        }
    }

    /**
     * @param updateInterval
     *            Duration between AJAX callbacks
     * @return JS script
     */
    protected final String getJsTimeoutCall( final Duration updateInterval ) {
        return "window.GmapAutoTimer = setTimeout(\"" + getCallbackScript() + "\", " + updateInterval.getMilliseconds() + ");";
    }

    /**
     * 
     * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    protected final void respond( final AjaxRequestTarget target ) {
        if ( !stopped ) {
            target.appendJavascript( getJsTimeoutCall( _updateInterval ) );
        }
        onTimer( target, getGMap() );
    }

    /**
     * Listener method for the AJAX timer event.
     * 
     * @param target
     *            The request target
     */
    protected abstract void onTimer( final AjaxRequestTarget target, GMap map );

}
