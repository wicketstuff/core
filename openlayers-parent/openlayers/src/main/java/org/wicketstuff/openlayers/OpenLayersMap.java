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

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.openlayers.api.Bounds;
import org.wicketstuff.openlayers.api.Control;
import org.wicketstuff.openlayers.api.InfoWindow;
import org.wicketstuff.openlayers.api.LonLat;
import org.wicketstuff.openlayers.api.Marker;
import org.wicketstuff.openlayers.api.Overlay;
import org.wicketstuff.openlayers.api.layer.GMap;
import org.wicketstuff.openlayers.api.layer.Layer;
import org.wicketstuff.openlayers.api.layer.OSM;
import org.wicketstuff.openlayers.api.layer.WMS;
import org.wicketstuff.openlayers.event.EventType;
import org.wicketstuff.openlayers.event.OverlayListenerBehavior;
import org.wicketstuff.openlayers.event.PopupListener;

/**
 * Wicket component to embed <a href="http://www.openlayers.org/">Openlayers
 * Maps</a> into your pages.
 */
public class OpenLayersMap extends Panel implements IOpenLayersMap {
	private static Logger log = LoggerFactory.getLogger(OpenLayersMap.class);

	private abstract class JSMethodBehavior extends AbstractBehavior {

		private static final long serialVersionUID = 1L;

		private String attribute;

		public JSMethodBehavior(final String attribute) {
			this.attribute = attribute;
		}

		protected abstract String getJSinvoke();

		/**
		 * @see org.apache.wicket.behavior.AbstractBehavior#onComponentTag(org.apache.wicket.Component,
		 *      org.apache.wicket.markup.ComponentTag)
		 */
		@Override
		public void onComponentTag(Component component, ComponentTag tag) {
			String invoke = getJSinvoke();

			if (attribute.equalsIgnoreCase("href")) {
				invoke = "javascript:" + invoke;
			}

			tag.put(attribute, invoke);
		}
	}

	public class PanDirectionBehavior extends JSMethodBehavior {
		private static final long serialVersionUID = 1L;

		private int dx;

		private int dy;

		public PanDirectionBehavior(String event, final int dx, final int dy) {
			super(event);
			this.dx = dx;
			this.dy = dy;
		}

		@Override
		protected String getJSinvoke() {
			return getJSpanDirection(dx, dy);
		}
	}

	public class SetCenterBehavior extends JSMethodBehavior {
		private static final long serialVersionUID = 1L;

		private LonLat gLatLng;
		private Integer zoom;

		public SetCenterBehavior(String event, LonLat gLatLng, Integer zoom) {
			super(event);
			this.gLatLng = gLatLng;
			this.zoom = zoom;
		}

		@Override
		protected String getJSinvoke() {
			return getJSsetCenter(gLatLng, zoom);
		}
	}

	public class SetZoomBehavior extends JSMethodBehavior {
		private static final long serialVersionUID = 1L;

		private Integer zoom;

		public SetZoomBehavior(final String event, final Integer zoom) {
			super(event);
			this.zoom = zoom;
		}

		@Override
		protected String getJSinvoke() {
			return getJSsetZoom(zoom);
		}
	}

	public class ZoomInBehavior extends JSMethodBehavior {
		private static final long serialVersionUID = 1L;

		public ZoomInBehavior(String event) {
			super(event);
		}

		@Override
		protected String getJSinvoke() {
			return getJSzoomIn();
		}
	}

	public class ZoomOutBehavior extends JSMethodBehavior {
		private static final long serialVersionUID = 1L;

		public ZoomOutBehavior(String event) {
			super(event);
		}

		@Override
		protected String getJSinvoke() {
			return getJSzoomOut();
		}
	}

	private static final long serialVersionUID = 1L;

	private Bounds bounds;

	private PopupListener callbackListener = null;

	private LonLat center = new LonLat(37.4419, -122.1419);

	private List<Control> controls = new ArrayList<Control>();

	private boolean externalControls = false;

	private InfoWindow infoWindow;

	private List<Layer> layers = new ArrayList<Layer>();

	private final WebMarkupContainer map;

	private HashMap<String, String> options = new HashMap<String, String>();

	private List<Overlay> overlays = new ArrayList<Overlay>();

	private Integer zoom = 13;

	/**
	 * 
	 * Constructs a map with a default layer : "OpenLayers WMS",
	 * "http://labs.metacarta.com/wms/vmap0"
	 * 
	 * @param id
	 */
	public OpenLayersMap(final String id) {
		this(id, new OpenLayersMapHeaderContributor(),
				new ArrayList<Overlay>(), new ArrayList<Layer>(),
				new HashMap<String, String>());
		options.put("layers", "'basic'");
		layers.add(new WMS("OpenLayers WMS",
				"http://labs.metacarta.com/wms/vmap0", options));
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 */
	public OpenLayersMap(final String id, List<Layer> defaultLayers,
			HashMap<String, String> options) {
		this(id, new OpenLayersMapHeaderContributor(),
				new ArrayList<Overlay>(), defaultLayers, options);
	}

	public OpenLayersMap(final String id, List<Layer> defaultLayers,
			HashMap<String, String> options, List<Overlay> overlays) {
		this(id, new OpenLayersMapHeaderContributor(), overlays, defaultLayers,
				options);
	}

	public OpenLayersMap(final String id, List<Layer> defaultLayers,
			HashMap<String, String> options, List<Overlay> overlays,
			PopupListener popupListener) {
		this(id, new OpenLayersMapHeaderContributor(), overlays, popupListener,
				defaultLayers, options);
	}

	/**
	 * 
	 * Popups up the window as default!
	 * 
	 * @param id
	 * @param headerContrib
	 * @param overlays
	 * 
	 */
	private OpenLayersMap(final String id,
			final OpenLayersMapHeaderContributor headerContrib,
			List<Overlay> overlays, List<Layer> defaultLayers,
			HashMap<String, String> options) {
		this(id, headerContrib, overlays, new PopupListener(false) {
			@Override
			protected void onClick(AjaxRequestTarget target, Overlay overlay) {
				// make sure that info window is closed
				if (Marker.class.isInstance(overlay)) {
					clickAndOpenPopup((Marker) overlay, target);
				}
			}
		}, defaultLayers, options);

	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param OpenLayerMapHeaderContributor
	 * @param overlays
	 */
	private OpenLayersMap(final String id,
			final OpenLayersMapHeaderContributor headerContrib,
			List<Overlay> overlays, PopupListener popupListener,
			List<Layer> defaultLayers, HashMap<String, String> options) {
		super(id);
		popupListener.setOpenLayersMap(this);

		this.overlays = overlays;
		this.layers = defaultLayers;
		this.options = options;

		// always add callbacklistener dont know if its gonna be used later on!
		callbackListener = popupListener;
		add(callbackListener);

		add(headerContrib);
		addHeaderContributorsForLayers(layers);
		add(new HeaderContributor(new IHeaderContributor() {
			private static final long serialVersionUID = 1L;

			public void renderHead(IHeaderResponse response) {
				response.renderOnDomReadyJavascript(getJSinit());
			}
		}));

		setInfoWindow(new InfoWindow());
		add(getInfoWindow());

		map = new WebMarkupContainer("map");
		map.setOutputMarkupId(true);
		add(map);
	}

	private void addHeaderContributorsForLayers(List<Layer> layers) {
		for(Layer layer : layers)
		{
			for(HeaderContributor contributor : layer.getHeaderContributors())
			{
				add(contributor);
			}
		}
	}
	/**
	 * Add a control.
	 * 
	 * @param control
	 *            control to add
	 * @return This
	 */
	public OpenLayersMap addControl(Control control) {
		controls.add(control);

		if (AjaxRequestTarget.get() != null && findPage() != null) {
			AjaxRequestTarget.get().appendJavascript(
					control.getJSadd(OpenLayersMap.this));
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
	public OpenLayersMap addOverlay(Overlay overlay) {
		overlays.add(overlay);
		for (OverlayListenerBehavior behavior : overlay.getBehaviors()) {
			add(behavior);
		}
		if (AjaxRequestTarget.get() != null && findPage() != null) {
			String jsToRun = getJsOverlay(overlay);
			AjaxRequestTarget.get().appendJavascript(jsToRun);

		}

		return this;
	}

	/**
	 * Clear all overlays.
	 * 
	 * @return This
	 */
	public OpenLayersMap clearOverlays() {
		for (Overlay overlay : overlays) {
			for (OverlayListenerBehavior behavior : overlay.getBehaviors()) {
				remove(behavior);
			}
		}
		overlays.clear();
		if (AjaxRequestTarget.get() != null && findPage() != null) {
			AjaxRequestTarget.get().appendJavascript(
					getJSinvoke("clearOverlays()"));
		}
		return this;
	}

	public Bounds getBounds() {
		return bounds;
	}

	public PopupListener getCallbackListener() {
		return callbackListener;
	}

	public LonLat getCenter() {
		return center;
	}

	public List<Control> getControls() {
		return controls;
	}

	/**
	 * Generates the JavaScript used to instantiate this OpenlayersMap as an
	 * JavaScript class on the client side.
	 * 
	 * @return The generated JavaScript
	 */
	private String getJSinit() {

		StringBuffer js = new StringBuffer();

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
			js
					.append("new WicketOMap('" + map.getMarkupId()
							+ "', options);\n");
		} else {
			js.append("new WicketOMap('" + map.getMarkupId() + "', null);\n");
		}

		for (Layer layer : layers) {
			if (layer instanceof WMS) {
				WMS wms = (WMS) layer;
				js.append("var wms" + wms.getId() + " ="
						+ wms.getJSconstructor() + ";\n");
				js.append(getJSinvoke("addLayer(wms" + wms.getId() + ","
						+ wms.getId() + ")"));
			}
			if (layer instanceof GMap) {
				GMap gmap = (GMap) layer;
				js.append("var gmap" + gmap.getId() + " ="
						+ gmap.getJSconstructor() + ";\n");
				js.append(getJSinvoke("addLayer(gmap" + gmap.getId() + ","
						+ gmap.getId() + ")"));
			}
			if (layer instanceof OSM) {
				OSM osm = (OSM) layer;
				js.append("var osm" + osm.getId() + " ="
						+ osm.getJSconstructor() + ";\n");
				js.append(getJSinvoke("addLayer(osm" + osm.getId() + ","
						+ osm.getId() + ")"));
			}
		}
		js.append(getJSinvoke("zoomToMaxExtent()"));
		for (Control control : controls) {
			js.append(control.getJSadd(this));
		}
		// Add the overlays.
		for (Overlay overlay : overlays) {

			js.append(getJsOverlay(overlay));
		}
		js.append(getJSinvoke("setPopupId('"
				+ getInfoWindow().getContent().getMarkupId() + "')"));

		return js.toString();
	}

	public String getJSInstance() {
		return "Wicket.omaps['" + map.getMarkupId() + "']";
	}

	/**
	 * Convenience method for generating a JavaScript call on this Openlayermap
	 * with the given invocation.
	 * 
	 * @param invocation
	 *            The JavaScript call to invoke on this Openlayermap.
	 * @return The generated JavaScript.
	 */
	// TODO Could this become default or protected?
	public String getJSinvoke(String invocation) {
		return "Wicket.omaps['" + map.getMarkupId() + "']." + invocation
				+ ";\n";
	}

	public String getJSinvokeNoLineEnd(String invocation) {
		return "Wicket.omaps['" + map.getMarkupId() + "']." + invocation;
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
			// add listeners
			for (EventType evt : marker.getEvents()) {
				jsToRun += getJSinvoke("addMarkerListener('" + evt.name()
						+ "','" + callbackListener.getCallBackForMarker(marker)
						+ "'," + marker.getOverlayJSVar() + ")");
			}
			if (marker.getIcon() != null) {
				// prepend icon stuff
				jsToRun = marker.getIcon().getSize().getJSadd()
						+ marker.getIcon().getOffset().getJSadd()
						+ marker.getIcon().getJSadd() + jsToRun;
			}
		}
		return jsToRun;

	}

	private String getJSpanDirection(int dx, int dy) {
		return getJSinvoke("panDirection(" + dx + "," + dy + ")");
	}

	private String getJSsetCenter(LonLat center, Integer zoom) {
		if (center != null && zoom != null)
			return getJSinvoke("setCenter(" + center.getJSconstructor() + ", " + zoom + ")");
		else
			return "";
	}

	private String getJSsetDoubleClickZoomEnabled(boolean enabled) {
		return getJSinvoke("setDoubleClickZoomEnabled(" + enabled + ")");
	}

	private String getJSsetDraggingEnabled(boolean enabled) {
		return getJSinvoke("setDraggingEnabled(" + enabled + ")");
	}

	private String getJSsetScrollWheelZoomEnabled(boolean enabled) {
		return getJSinvoke("setScrollWheelZoomEnabled(" + enabled + ")");
	}

	private String getJSsetZoom(Integer zoom) {
		return zoom != null ? getJSinvoke("setZoom(" + zoom + ")") : "";
	}

	private String getJSzoomIn() {
		return getJSinvoke("zoomIn()");
	}

	private String getJSzoomOut() {
		return getJSinvoke("zoomOut()");
	}

	public List<Layer> getLayers() {
		return layers;
	}

	public List<Overlay> getOverlays() {
		return Collections.unmodifiableList(overlays);
	}

	public Integer getZoom() {
		return zoom;
	}

	public boolean isExternalControls() {
		return externalControls;
	}

	/**
	 * Remove a control.
	 * 
	 * @param control
	 *            control to remove
	 * @return This
	 */
	public OpenLayersMap removeControl(Control control) {
		controls.remove(control);

		if (AjaxRequestTarget.get() != null && findPage() != null) {
			AjaxRequestTarget.get().appendJavascript(
					control.getJSremove(OpenLayersMap.this));
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
	public OpenLayersMap removeOverlay(Overlay overlay) {
		while (overlays.contains(overlay)) {
			overlays.remove(overlay);
		}
		for (OverlayListenerBehavior behavior : overlay.getBehaviors()) {
			remove(behavior);
		}

		if (AjaxRequestTarget.get() != null && findPage() != null) {
			AjaxRequestTarget.get().appendJavascript(
					overlay.getJSremove(OpenLayersMap.this));
		}

		return this;
	}

	/**
	 * Set the center.
	 * 
	 * @param center
	 *            center to set
	 */
	public void setCenter(LonLat center, Integer zoom) {
		if (!this.center.equals(center)) {
			this.center = center;
			this.zoom = zoom;

			if (AjaxRequestTarget.get() != null && findPage() != null) {
				AjaxRequestTarget.get()
						.appendJavascript(getJSsetCenter(center, zoom));
			}
		}
	}

	public void setExternalControls(boolean externalControls) {
		this.externalControls = externalControls;
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

	public void setZoom(Integer level) {
		if (this.zoom != level) {
			this.zoom = level;

			if (AjaxRequestTarget.get() != null && findPage() != null) {
				AjaxRequestTarget.get().appendJavascript(getJSsetZoom(zoom));
			}
		}
	}

	/**
	 * Update state from a request to an AJAX target.
	 */
	public void update(AjaxRequestTarget target) {
		Request request = RequestCycle.get().getRequest();

		// Attention: don't use setters as this will result in an endless
		// AJAX request loop
		bounds = Bounds.parse(request.getParameter("bounds"));
		center = LonLat.parseWithNames(request.getParameter("center"));
		zoom = Integer.parseInt(request.getParameter("zoom"));

		getInfoWindow().update(target);
	}

	public void setInfoWindow(InfoWindow infoWindow) {
		this.infoWindow = infoWindow;
	}

	public InfoWindow getInfoWindow() {
		return infoWindow;
	}

	/**
	 * @see org.apache.wicket.MarkupContainer#onRender(org.apache.wicket.markup.MarkupStream)
	 */
	@Override
	protected void onRender(MarkupStream markupStream) {
		super.onRender(markupStream);
		if (Application.DEVELOPMENT.equalsIgnoreCase(Application.get()
				.getConfigurationType())
				&& !Application.get().getMarkupSettings().getStripWicketTags()) {
			log
					.warn("Application is in DEVELOPMENT mode && Wicket tags are not stripped,"
							+ " Firefox 3.0 will not render the OMap."
							+ " Change to DEPLOYMENT mode  || turn on Wicket tags stripping."
							+ " See:"
							+ " http://www.nabble.com/Gmap2-problem-with-Firefox-3.0-to18137475.html.");
		}
	}

	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}

	public void setCenter(LonLat center) {
		setCenter(center, zoom);
	}
}