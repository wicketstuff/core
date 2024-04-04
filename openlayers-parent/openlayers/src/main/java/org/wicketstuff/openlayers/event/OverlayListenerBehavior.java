/*
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
package org.wicketstuff.openlayers.event;

import org.wicketstuff.openlayers.api.Overlay;

public abstract class OverlayListenerBehavior extends EventListenerBehavior
{

	private static final long serialVersionUID = 1L;
	private Overlay gOverlay;

	protected Overlay getGOverlay()
	{
		return gOverlay;
	}

	public void setGOverlay(Overlay gOverlay)
	{
		this.gOverlay = gOverlay;
	}

	@Override
	public String getJSaddListener()
	{
		return getOpenLayersMap().getJSinvoke(
			"register('" + getEvent() + "', '" + gOverlay.getId() + "', '" + getCallbackUrl() +
				"')");
	}
}
