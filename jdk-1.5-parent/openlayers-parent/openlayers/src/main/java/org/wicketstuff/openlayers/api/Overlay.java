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
package org.wicketstuff.openlayers.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.wicketstuff.openlayers.IOpenLayersMap;
import org.wicketstuff.openlayers.event.OverlayListenerBehavior;

/**
 * Represents an Openlayers API's // legacy to be removed
 */
public abstract class Overlay implements IJavascriptComponent
{
	private static final long serialVersionUID = 1L;
	List<OverlayListenerBehavior> behaviors = new ArrayList<OverlayListenerBehavior>();

	public Overlay addBehavior(OverlayListenerBehavior behavior)
	{
		behavior.setGOverlay(this);
		behaviors.add(behavior);

		return this;
	}

	public Overlay removeBehavior(OverlayListenerBehavior behavior)
	{
		while (behaviors.contains(behavior))
		{
			behaviors.remove(behavior);
		}

		behavior.setGOverlay(null);
		return this;
	}

	public Overlay clearBehaviors()
	{
		behaviors.clear();

		return this;
	}

	public List<OverlayListenerBehavior> getBehaviors()
	{
		return Collections.unmodifiableList(behaviors);
	}

	public String getOverlayJSVar()
	{
		return "overlay" + getId();
	}

	public String getJSadd(IOpenLayersMap map)
	{
		StringBuffer js = new StringBuffer();
		js.append("var " + getOverlayJSVar() + " = " + getJSconstructor() + ";\n");
		js.append(map.getJSinvoke("addOverlay('" + getId() + "', overlay" + getId() + ")"));
		return js.toString();
	}

	public String getJSremove(IOpenLayersMap map)
	{
		return map.getJSinvoke("removeOverlay('" + getId() + "')");
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.wicketstuff.openlayers.api.IJavascriptComponent#getJSResourceReference()
	 */
	public JavaScriptResourceReference[] getJSResourceReferences()
	{
		// intentionally not implemented.
		return null;
	}

	public String getId()
	{
		return String.valueOf(System.identityHashCode(this));
	}

	protected abstract String getJSconstructor();
}
