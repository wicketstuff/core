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
package org.wicketstuff.openlayers;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.openlayers.api.Bounds;
import org.wicketstuff.openlayers.api.IJavascriptComponent;
import org.wicketstuff.openlayers.api.LonLat;
import org.wicketstuff.openlayers.api.Overlay;
import org.wicketstuff.openlayers.api.layer.Layer;

public interface IOpenLayersMap
{
	public String getJSinvoke(String invocation);

	public String getJSinvokeNoLineEnd(String invocation);

	public String getJSInstance();

	public boolean isExternalControls();

	public void setExternalControls(boolean externalControls);

	public void setBounds(Bounds bounds);

	public Bounds getBounds();

	public void setCenter(LonLat center);

	public LonLat getCenter();

	public void setZoom(Integer zoom);

	public Integer getZoom();

	public void setCenter(LonLat center, Integer zoom);

	public void update(AjaxRequestTarget target);

	public List<Layer> getLayers();

	public List<IJavascriptComponent> getControls();

	public List<Overlay> getOverlays();

	public IOpenLayersMap addControl(IJavascriptComponent control);

	public IOpenLayersMap addOverlay(Overlay overlay);

	public IOpenLayersMap clearOverlays();

	public IOpenLayersMap removeControl(IJavascriptComponent control);

	public IOpenLayersMap removeOverlay(Overlay overlay);

	public void setLayers(List<Layer> layers);

	public void setOverlays(List<Overlay> overlays);

	public void setBusinessLogicProjection(String businessLogicProjection);

	public String getBusinessLogicProjection();
}
