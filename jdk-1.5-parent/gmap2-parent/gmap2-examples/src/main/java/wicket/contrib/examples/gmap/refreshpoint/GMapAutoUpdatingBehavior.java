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

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.util.time.Duration;

import wicket.contrib.gmap.GMap2;

/**
 * @author Maes Gregory, Anyware Technologies
 */
public abstract class GMapAutoUpdatingBehavior extends AbstractDefaultAjaxBehavior {

	private static final long serialVersionUID = 1L;

    /** The update interval */
    private final Duration updateInterval;

    private boolean stopped = false;

	
    /**
     * Construct.
     * 
     * @param updateInterval
     *            Duration between AJAX callbacks
     */
    public GMapAutoUpdatingBehavior(final Duration updateInterval) {
        this.updateInterval = updateInterval;
    }
	
	@Override
	protected void onBind() {
		if (!(getComponent() instanceof GMap2)) {
			throw new IllegalArgumentException("must be bound to GMap2");
		}
		getGMap2().setOutputMarkupId(true);
	}
	
	protected final GMap2 getGMap2() {
		return (GMap2)getComponent();
	}

    /**
     * Stops the timer
     */
    public final void stop() {
        stopped = true;
    }

    @Override
    protected void onComponentRendered() {
        if(!stopped) {
            Response response = RequestCycle.get().getResponse();
            response.write("<script type=\"text/javascript\">");
            response.write(getJsTimeoutCall(updateInterval));
            response.write("</script>");
        }
    }

    /**
     * @param updateInterval
     *            Duration between AJAX callbacks
     * @return JS script
     */
    protected final String getJsTimeoutCall(final Duration updateInterval) {
        return "window.GmapAutoTimer = setTimeout(\"" + getCallbackScript() + "\", " + updateInterval.getMilliseconds() + ");";
    }

    /**
     * 
     * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    protected final void respond(final AjaxRequestTarget target) {
    	if(!stopped) {
    		target.appendJavaScript(getJsTimeoutCall(updateInterval));
    	}
        onTimer(target, getGMap2());
    }

    /**
     * Listener method for the AJAX timer event.
     * 
     * @param target
     *            The request target
     */
    protected abstract void onTimer(final AjaxRequestTarget target, GMap2 map);

}
