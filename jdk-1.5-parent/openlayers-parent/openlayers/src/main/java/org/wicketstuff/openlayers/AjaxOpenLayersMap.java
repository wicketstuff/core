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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.WicketAjaxReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WicketEventReference;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.wicketstuff.openlayers.api.Bounds;
import org.wicketstuff.openlayers.api.IJavascriptComponent;
import org.wicketstuff.openlayers.api.LonLat;
import org.wicketstuff.openlayers.api.Marker;
import org.wicketstuff.openlayers.api.Overlay;
import org.wicketstuff.openlayers.api.feature.Feature;
import org.wicketstuff.openlayers.api.feature.FeatureStyle;
import org.wicketstuff.openlayers.api.layer.Layer;
import org.wicketstuff.openlayers.api.layer.Vector;
import org.wicketstuff.openlayers.event.EventType;
import org.wicketstuff.openlayers.event.OverlayListenerBehavior;

/**
 * 
 * @author Marin Mandradjiev (marinsm@hotmail.com)
 * 
 */
public class AjaxOpenLayersMap extends WebMarkupContainer implements
		IOpenLayersMap {
	private static final long serialVersionUID = 159201381315392564L;

	private List<Layer> layers;
	private final HashMap<String, String> options;
	private final List<Feature> features;
	private final List<IJavascriptComponent> controls = new ArrayList<IJavascriptComponent>();
	private final List<Overlay> overlays = new ArrayList<Overlay>();
	private final List<FeatureStyle> featureStyles;
	private final HashMap<String, Vector> featureVectors = new HashMap<String, Vector>();
	private boolean externalControls = false;
	private Bounds bounds = null;
	private LonLat center = null;
	private Integer zoom = null;
	private String businessLogicProjection = null;
	private String markersLayerName = null;

	public AjaxOpenLayersMap(final String id, final List<Layer> layers) {
		this(id, layers, null);
	}

	public AjaxOpenLayersMap(final String id, final List<Layer> layers,
			final HashMap<String, String> options) {
		this(id, layers, options, null);
	}

	public AjaxOpenLayersMap(final String id, final List<Layer> layers,
			final HashMap<String, String> options, final List<Feature> features) {
		this(id, layers, options, features, null);
	}

	public AjaxOpenLayersMap(final String id, final List<Layer> layers,
			final HashMap<String, String> options,
			final List<Feature> features, final List<FeatureStyle> featureStyles) {
		super(id);
		setOutputMarkupId(true);
		this.layers = (layers == null) ? new ArrayList<Layer>() : layers;
		this.options = (options == null) ? new HashMap<String, String>()
				: options;
		this.features = (features == null) ? new ArrayList<Feature>()
				: features;
		this.featureStyles = (featureStyles == null) ? new ArrayList<FeatureStyle>()
				: featureStyles;
		add(new HeaderContributor(new IHeaderContributor() {
			private static final long serialVersionUID = 1L;

			public void renderHead(IHeaderResponse response) {
				response.renderOnDomReadyJavascript(getJSinit());
			}
		}));
		for (Feature feature : this.features) {
			getFeatureVector(feature.getDisplayInLayer());
		}
	}

	public static void onPageRenderHead(IHeaderResponse response,
			String pathToOpenLayersJS) {
		if (pathToOpenLayersJS == null
				|| pathToOpenLayersJS.trim().length() == 0) {
			pathToOpenLayersJS = "http://openlayers.org/api/";
		} else {
			pathToOpenLayersJS = pathToOpenLayersJS.trim();
			if (!pathToOpenLayersJS.endsWith("/")) {
				pathToOpenLayersJS = pathToOpenLayersJS + "/";
			}
		}
		pathToOpenLayersJS = pathToOpenLayersJS + "OpenLayers.js";
		response.renderJavascriptReference(pathToOpenLayersJS);
		// TODO Import all other JS files which will be used later on
		response.renderJavascriptReference(WicketEventReference.INSTANCE);
		response.renderJavascriptReference(WicketAjaxReference.INSTANCE);
		response.renderJavascriptReference(new JavascriptResourceReference(
				AjaxOpenLayersMap.class, "wicket-openlayersmap.js"));
	}

	public void setExternalControls(boolean externalControls) {
		this.externalControls = externalControls;
	}

	public boolean isExternalControls() {
		return externalControls;
	}

	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}

	public Bounds getBounds() {
		return bounds;
	}

	public void setCenter(LonLat center) {
		this.center = center;
		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavascript(getJSSetCenter());
		}
	}

	public LonLat getCenter() {
		return center;
	}

	public void setZoom(Integer zoom) {
		this.zoom = zoom;
		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavascript(getJSSetCenter());
		}
	}

	public Integer getZoom() {
		return zoom;
	}

	public void setCenter(LonLat center, Integer zoom) {
		this.center = center;
		this.zoom = zoom;
		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavascript(getJSSetCenter());
		}
	}

	private String getJSSetCenter() {
		if (center == null) {
			return "";
		}
		String transformation = "";
		if (getBusinessLogicProjection() != null) {
			transformation = ".transform(new OpenLayers.Projection(\""
					+ getBusinessLogicProjection() + "\"), "
					+ getJSinvokeNoLineEnd("map") + ".getProjectionObject())";
		}

		if (zoom == null) {
			return getJSinvoke("setCenter(" + center.getJSconstructor()
					+ transformation + ")");
		}
		return getJSinvoke("setCenter(" + center.getJSconstructor()
				+ transformation + ", " + zoom.toString() + ")");
	}

	private String getJSSetBusinessLogicProjection() {
		if (businessLogicProjection == null) {
			return getJSinvoke("setBusinessLogicProjection(null)");
		}
		return getJSinvoke("setBusinessLogicProjection('"
				+ businessLogicProjection + "')");
	}

	/**
	 * Add a control.
	 * 
	 * @param control
	 *            control to add
	 * @return This
	 */
	public IOpenLayersMap addControl(IJavascriptComponent control) {
		controls.add(control);
		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavascript(control.getJSadd(this));
		}
		return this;
	}

	/**
	 * Add an overlay.
	 * 
	 * @param overlay
	 *            overlay to add
	 * @return This
	 */
	public IOpenLayersMap addOverlay(Overlay overlay) {
		overlays.add(overlay);
		if (overlay instanceof Marker) {
			((Marker) overlay).setMap(this);
		}
		for (OverlayListenerBehavior behavior : overlay.getBehaviors()) {
			add(behavior);
		}
		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavascript(getJsOverlay(overlay));
		}
		return this;
	}

	/**
	 * Add a layer.
	 * 
	 * @param layer
	 *            layer to add
	 * @return This
	 */
	public IOpenLayersMap addLayer(Layer layer) {
		layers.add(layer);
		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavascript(layer.getJSAddLayer(this));
		}
		return this;
	}

	/**
	 * Add a feature.
	 * 
	 * @param feature
	 *            feature to add
	 * @return This
	 */
	public IOpenLayersMap addFeature(Feature feature) {
		features.add(feature);
		feature.setMap(this);
		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavascript(
					feature.getJSAddFeature(this, getFeatureVector(feature
							.getDisplayInLayer())));
		}
		return this;
	}

	/**
	 * Add a feature style.
	 * 
	 * @param featureStyle
	 *            featureStyle to add
	 * @return This
	 */
	public IOpenLayersMap addFeatureStyle(FeatureStyle featureStyle) {
		featureStyles.add(featureStyle);
		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavascript(
					featureStyle.getJSAddStyle(this));
		}
		return this;
	}

	public IOpenLayersMap removeFeatureStyle(FeatureStyle featureStyle) {
		while (featureStyles.contains(featureStyle)) {
			featureStyles.remove(featureStyle);
		}
		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavascript(
					featureStyle.getJSRemoveStyle(this));
		}
		return this;
	}

	/**
	 * Clear all overlays.
	 * 
	 * @return This
	 */
	public IOpenLayersMap clearOverlays() {
		for (Overlay overlay : overlays) {
			for (OverlayListenerBehavior behavior : overlay.getBehaviors()) {
				remove(behavior);
			}
		}
		overlays.clear();
		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavascript(
					getJSinvoke("clearOverlays()"));
		}
		return this;
	}

	/**
	 * Generates the JavaScript used to instantiate this AjaxOpenLayersMap as an
	 * JavaScript class on the client side.
	 * 
	 * @return The generated JavaScript
	 */
	protected String getJSinit() {
		StringBuffer js = new StringBuffer();
		String jsMarkersLayerName = getMarkersLayerName() == null ? ", null"
				: ", '" + getMarkersLayerName() + "'";
		if (options.size() > 0) {
			js.append("\nvar options = {");
			boolean first = true;
			for (String key : options.keySet()) {
				if (first) {
					first = false;
				} else {
					js.append(",\n");
				}
				js.append(key + ":" + options.get(key));
			}
			js.append("};\n");
			js.append("new WicketOMap('" + this.getMarkupId() + "', options"
					+ jsMarkersLayerName + ", true);\n");
		} else {
			js.append("new WicketOMap('" + this.getMarkupId() + "', null"
					+ jsMarkersLayerName + ", true);\n");
		}
		for (FeatureStyle featureStyle : featureStyles) {
			js.append(featureStyle.getJSAddStyle(this));
		}
		// This will add all layers which are used in features
		for (Feature feature : features) {
			getFeatureVector(feature.getDisplayInLayer());
		}
		for (Layer layer : getLayers()) {
			js.append(layer.getJSAddLayer(this));
		}
		for (Feature feature : features) {
			js.append(feature.getJSAddFeature(this, getFeatureVector(feature
					.getDisplayInLayer())));
		}
		js.append(getJSSetCenter());
		if (center == null || zoom == null) {
			js.append(getJSinvoke("zoomToMaxExtent()"));
		}
		for (IJavascriptComponent control : controls) {
			js.append(control.getJSadd(this));
		}
		// Add the overlays.
		for (Overlay overlay : overlays) {
			js.append(getJsOverlay(overlay));
		}
		if (businessLogicProjection != null) {
			js.append(getJSSetBusinessLogicProjection());
		}
		return js.toString();
	}

	/**
	 * Convenience method for generating a JavaScript call on this
	 * AjaxOpenLayersMap with the given invocation.
	 * 
	 * @param invocation
	 *            The JavaScript call to invoke on this AjaxOpenLayersMap.
	 * @return The generated JavaScript.
	 */
	public String getJSinvoke(String invocation) {
		return "Wicket.omaps['" + this.getMarkupId() + "']." + invocation
				+ ";\n";
	}

	public String getJSinvokeNoLineEnd(String invocation) {
		return "Wicket.omaps['" + this.getMarkupId() + "']." + invocation;
	}

	private String getJsOverlay(Overlay overlay) {
		String jsToRun = overlay.getJSadd(this) + "\n";
		if (overlay instanceof Marker) {
			Marker marker = (Marker) overlay;
			// if marker has popup and there are no events attached then attach
			// default listener
			if (marker.getPopup() != null
					&& (marker.getEvents() == null || marker.getEvents().length == 0)) {
				// add mousedown listener!
				marker.addEvent(EventType.mousedown);
			}
			// TODO add listeners
			if (marker.getIcon() != null) {
				// prepend icon stuff
				jsToRun = marker.getIcon().getSize().getJSadd()
						+ marker.getIcon().getOffset().getJSadd()
						+ marker.getIcon().getJSadd() + jsToRun;
			}
		}
		return jsToRun;
	}

	/**
	 * Remove a control.
	 * 
	 * @param control
	 *            control to remove
	 * @return This
	 */
	public IOpenLayersMap removeControl(IJavascriptComponent control) {
		controls.remove(control);
		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavascript(control.getJSremove(this));
		}
		return this;
	}

	/**
	 * Remove an overlay.
	 * 
	 * @param overlay
	 *            overlay to remove
	 * @return This
	 */
	public IOpenLayersMap removeOverlay(Overlay overlay) {
		while (overlays.contains(overlay)) {
			overlays.remove(overlay);
		}
		for (OverlayListenerBehavior behavior : overlay.getBehaviors()) {
			remove(behavior);
		}
		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavascript(overlay.getJSremove(this));
		}
		return this;
	}

	/**
	 * Remove an feature.
	 * 
	 * @param feature
	 *            feature to remove
	 * @return This
	 */
	public IOpenLayersMap removeFeature(Feature feature) {
		while (features.contains(feature)) {
			features.remove(feature);
		}
		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavascript(
					feature.getJSRemoveFeature(this, getFeatureVector(feature
							.getDisplayInLayer())));
		}
		return this;
	}

	/**
	 * Update state from a request to an AJAX target.
	 */
	public void update(AjaxRequestTarget target) {
		Request request = RequestCycle.get().getRequest();
		// Attention: don't use setters as this will result in an endless
		// AJAX request loop
		center = LonLat.parseWithNames(request.getParameter("centerConverted"));
		zoom = Integer.parseInt(request.getParameter("zoomConverted"));
		bounds = Bounds.parseWithNames(request.getParameter("boundsConverted"));
	}

	public List<Layer> getLayers() {
		return layers;
	}

	public List<IJavascriptComponent> getControls() {
		return controls;
	}

	public String getJSInstance() {
		return "Wicket.omaps['" + this.getMarkupId() + "']";
	}

	public List<Overlay> getOverlays() {
		return Collections.unmodifiableList(overlays);
	}

	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}

	public void setOverlays(List<Overlay> overlays) {
		clearOverlays();
		for (Overlay overlay : overlays) {
			addOverlay(overlay);
		}
	}

	public void setBusinessLogicProjection(String businessLogicProjection) {
		this.businessLogicProjection = businessLogicProjection;
		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavascript(
					getJSSetBusinessLogicProjection());
		}
	}

	public String getBusinessLogicProjection() {
		return businessLogicProjection;
	}

	public Vector getFeatureVector(String name) {
		Vector vector = featureVectors.get(name);
		if (vector == null) {
			vector = new Vector(name == null ? "Default" : name);
			addLayer(vector);
			featureVectors.put(name, vector);
		}
		return vector;
	}

	public void setMarkersLayerName(String markersLayerName) {
		this.markersLayerName = markersLayerName;
	}

	public String getMarkersLayerName() {
		return markersLayerName;
	}

	public void setVisibleOnlyLayers(String... names) {
		if (AjaxRequestTarget.get() != null) {
			List<String> invisibleNames = new ArrayList<String>();
			invisibleNames.add(markersLayerName);
			invisibleNames.addAll(featureVectors.keySet());
			StringBuffer visibleLayers = new StringBuffer();
			String layerId = null;
			for (String name : names) {
				layerId = findLayerId(name);
				if (layerId != null) {
					if (visibleLayers.length() > 0)
						visibleLayers.append(",");
					visibleLayers.append(layerId);
				}
				invisibleNames.remove(name);
			}
			StringBuffer invisibleLayers = new StringBuffer();
			for (String name : invisibleNames) {
				layerId = findLayerId(name);
				if (layerId != null) {
					if (invisibleLayers.length() > 0)
						invisibleLayers.append(",");
					invisibleLayers.append(layerId);
				}
			}
			AjaxRequestTarget.get().appendJavascript(
					getJSinvoke("setLayersVisibility(["
							+ visibleLayers.toString() + "], ["
							+ invisibleLayers.toString() + "])"));
		}
	}

	public void toggleLayer(String name) {
		String layerId = findLayerId(name);
		if (layerId != null && AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavascript(
					getJSinvoke("toggleLayer(" + layerId + ")"));
		}
	}

	private String findLayerId(String name) {
		Vector vector = featureVectors.get(name);
		if (vector != null)
			return vector.getId();
		return markersLayerName != null && markersLayerName.equals(name) ? "1"
				: null;
	}
}
