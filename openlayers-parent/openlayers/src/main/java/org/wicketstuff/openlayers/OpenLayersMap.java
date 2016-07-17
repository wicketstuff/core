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

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.openlayers.api.Bounds;
import org.wicketstuff.openlayers.api.IJavascriptComponent;
import org.wicketstuff.openlayers.api.InfoWindow;
import org.wicketstuff.openlayers.api.LonLat;
import org.wicketstuff.openlayers.api.SphericalMercatorLonLat;
import org.wicketstuff.openlayers.api.Marker;
import org.wicketstuff.openlayers.api.Overlay;
import org.wicketstuff.openlayers.api.layer.Layer;
import org.wicketstuff.openlayers.api.layer.OSM;
import org.wicketstuff.openlayers.api.layer.GMap;
import org.wicketstuff.openlayers.api.layer.WFS;
import org.wicketstuff.openlayers.api.layer.WMS;
import org.wicketstuff.openlayers.api.layer.Vector;
import org.wicketstuff.openlayers.event.EventType;
import org.wicketstuff.openlayers.event.OverlayListenerBehavior;
import org.wicketstuff.openlayers.event.PopupListener;
import org.wicketstuff.openlayers.js.JSUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Wicket component to embed <a href="http://www.openlayers.org/">Openlayers Maps</a> into your
 * pages.
 */
public class OpenLayersMap extends Panel implements IOpenLayersMap
{

	private static final String OPEN_LAYERS_VERSION = "2.13.1";

	private static Logger log = LoggerFactory.getLogger(OpenLayersMap.class);
	private String businessLogicProjection = null;

	private abstract class JSMethodBehavior extends Behavior
	{

		private static final long serialVersionUID = 1L;

		private final String attribute;

		public JSMethodBehavior(final String attribute)
		{
			this.attribute = attribute;
		}

		protected abstract String getJSinvoke();

		/**
		 * @see Behavior#onComponentTag(org.apache.wicket.Component,
		 *      org.apache.wicket.markup.ComponentTag)
		 */
		@Override
		public void onComponentTag(Component component, ComponentTag tag)
		{
			String invoke = getJSinvoke();

			if (attribute.equalsIgnoreCase("href"))
			{
				invoke = "javascript:" + invoke;
			}

			tag.put(attribute, invoke);
		}
	}

	public class PanDirectionBehavior extends JSMethodBehavior
	{
		private static final long serialVersionUID = 1L;

		private final int dx;

		private final int dy;

		public PanDirectionBehavior(String event, final int dx, final int dy)
		{
			super(event);
			this.dx = dx;
			this.dy = dy;
		}

		@Override
		protected String getJSinvoke()
		{
			return getJSpanDirection(dx, dy);
		}
	}

	public class SetCenterBehavior extends JSMethodBehavior
	{
		private static final long serialVersionUID = 1L;

		private final LonLat gLatLng;
		private final Integer zoom;

		public SetCenterBehavior(String event, LonLat gLatLng, Integer zoom)
		{
			super(event);
			this.gLatLng = gLatLng;
			this.zoom = zoom;
		}

		@Override
		protected String getJSinvoke()
		{
			return getJSsetCenter(gLatLng, zoom);
		}
	}

	public class SetZoomBehavior extends JSMethodBehavior
	{
		private static final long serialVersionUID = 1L;

		private final Integer zoom;

		public SetZoomBehavior(final String event, final Integer zoom)
		{
			super(event);
			this.zoom = zoom;
		}

		@Override
		protected String getJSinvoke()
		{
			return getJSsetZoom(zoom);
		}
	}

	public class ZoomInBehavior extends JSMethodBehavior
	{
		private static final long serialVersionUID = 1L;

		public ZoomInBehavior(String event)
		{
			super(event);
		}

		@Override
		protected String getJSinvoke()
		{
			return getJSzoomIn();
		}
	}

	public class ZoomOutBehavior extends JSMethodBehavior
	{
		private static final long serialVersionUID = 1L;

		public ZoomOutBehavior(String event)
		{
			super(event);
		}

		@Override
		protected String getJSinvoke()
		{
			return getJSzoomOut();
		}
	}

	private static final long serialVersionUID = 1L;

	private Bounds bounds;

	private PopupListener callbackListener = null;

	private static final LonLat DEFAULT_CENTER = new SphericalMercatorLonLat(37.4419, -122.1419);
	private LonLat center = DEFAULT_CENTER;

	private final List<IJavascriptComponent> controls = new ArrayList<IJavascriptComponent>();

	private boolean externalControls = false;

	private InfoWindow infoWindow;

	private List<Layer> layers = new ArrayList<Layer>();

	private final WebMarkupContainer map;

	private HashMap<String, String> options = new HashMap<String, String>();

	private List<Overlay> overlays = new ArrayList<Overlay>();

	private static final int DEFAULT_ZOOM = 13;
	private int zoom = DEFAULT_ZOOM;

	// determines if the marker layer will be visible in the
	// OpenLayers.Control.LayerSwitcher
	private boolean showMarkersInLayerSwitcher = true;

	/**
	 *
	 * Constructs a map with a default layer : "OpenLayers WMS",
	 * "http://labs.metacarta.com/wms/vmap0"
	 *
	 * @param id
	 */
	public OpenLayersMap(final String id, boolean developmentMode)
	{
		this(id, new OpenLayersMapHeaderContributor(developmentMode, OPEN_LAYERS_VERSION),
			new ArrayList<Overlay>(), new ArrayList<Layer>(), new HashMap<String, String>());

		HashMap<String, String> layerOptions = new HashMap<String, String>();

		layerOptions.put("layers", JSUtils.getQuotedString("basic"));

		layers.add(new WMS("OpenLayers WMS", "http://labs.metacarta.com/wms/vmap0", layerOptions));
	}

	public OpenLayersMap(String id)
	{
		this(id, false);
	}

	/**
	 * Construct.
	 *
	 * @param id
	 */
	public OpenLayersMap(final String id, boolean developmentMode, List<Layer> defaultLayers,
		HashMap<String, String> options)
	{
		this(id, new OpenLayersMapHeaderContributor(developmentMode, OPEN_LAYERS_VERSION),
			new ArrayList<Overlay>(), defaultLayers, options);
	}

	public OpenLayersMap(final String id, List<Layer> defaultLayers, HashMap<String, String> options)
	{

		this(id, false, defaultLayers, options);
	}

	public OpenLayersMap(final String id, boolean developmentMode, List<Layer> defaultLayers,
		HashMap<String, String> options, List<Overlay> overlays)
	{
		this(id, new OpenLayersMapHeaderContributor(developmentMode, OPEN_LAYERS_VERSION),
			overlays, defaultLayers, options);
	}

	public OpenLayersMap(final String id, List<Layer> defaultLayers,
		HashMap<String, String> options, List<Overlay> overlays)
	{
		this(id, false, defaultLayers, options, overlays);
	}


	public OpenLayersMap(final String id, List<Layer> defaultLayers,
		HashMap<String, String> options, List<Overlay> overlays, PopupListener popupListener)
	{

		this(id, false, defaultLayers, options, overlays, popupListener);
	}

	public OpenLayersMap(final String id, boolean developmentMode, List<Layer> defaultLayers,
		HashMap<String, String> options, List<Overlay> overlays, PopupListener popupListener)
	{
		this(id, new OpenLayersMapHeaderContributor(developmentMode, OPEN_LAYERS_VERSION),
			overlays, popupListener, defaultLayers, options);
	}

	/**
	 *
	 * Popups up the window as default!
	 *
	 * is protected to allow subclasses to override the HeaderContributor that is used. @see
	 * OpenLayersMapHeaderContributor
	 *
	 * @param id
	 * @param headerContrib
	 * @param overlays
	 *
	 */
	protected OpenLayersMap(final String id, final OpenLayersMapHeaderContributor headerContrib,
		List<Overlay> overlays, List<Layer> defaultLayers, HashMap<String, String> options)
	{
		this(id, headerContrib, overlays, new PopupListener(false)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onClick(AjaxRequestTarget target, Overlay overlay)
			{
				// make sure that info window is closed
				if (Marker.class.isInstance(overlay))
				{
					clickAndOpenPopup((Marker)overlay, target);
				}
			}
		}, defaultLayers, options);

	}

	/**
	 * Construct.
	 *
	 * @param id
	 * @param headerContrib
	 * @param overlays
	 */
	private OpenLayersMap(final String id, final OpenLayersMapHeaderContributor headerContrib,
		List<Overlay> overlays, PopupListener popupListener, List<Layer> defaultLayers,
		HashMap<String, String> options)
	{
		super(id);
		popupListener.setOpenLayersMap(this);

		this.overlays = overlays;
		layers = defaultLayers;
		this.options = options;

		// always add callbacklistener dont know if its gonna be used later on!
		callbackListener = popupListener;
		add(callbackListener);

		add(headerContrib);
		addHeaderContributorsForLayers(layers);
		add(new Behavior()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void renderHead(Component component, IHeaderResponse response)
			{
				response.render(OnDomReadyHeaderItem.forScript(getJSinit()));
			}
		});

		setInfoWindow(new InfoWindow());
		add(getInfoWindow());

		map = new WebMarkupContainer("map");
		map.setOutputMarkupId(true);
		add(map);
	}

	private void addHeaderContributorsForLayers(List<Layer> layers)
	{
		for (Layer layer : layers)
		{
			layer.bindHeaderContributors(this);
		}
	}

	/**
	 * Add a control.
	 *
	 * @param control
	 *            control to add
	 * @return This
	 */
	public OpenLayersMap addControl(IJavascriptComponent control)
	{
		controls.add(control);

		final JavaScriptResourceReference[] jsReferences = control.getJSResourceReferences();

		if (jsReferences != null && jsReferences.length > 0)
		{

			add(new Behavior()
			{

				/**
				 *
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void renderHead(Component c, IHeaderResponse response)
				{

					for (JavaScriptResourceReference javascriptResourceReference : jsReferences)
					{
						response.render(JavaScriptHeaderItem.forReference(javascriptResourceReference));
					}
				}
			});


		}

		Optional<AjaxRequestTarget> target = getRequestCycle().find(AjaxRequestTarget.class);

		if (target.isPresent() && findPage() != null)
		{
			target.get().appendJavaScript(control.getJSadd(OpenLayersMap.this));

			if (jsReferences != null && jsReferences.length > 0)
			{

				for (JavaScriptResourceReference javascriptResourceReference : jsReferences)
				{
					target.get().getHeaderResponse().render(JavaScriptHeaderItem.forReference(
						javascriptResourceReference));
				}
			}

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
	public OpenLayersMap addOverlay(Overlay overlay)
	{
		overlays.add(overlay);
		overlay.getBehaviors().forEach(this::add);

		if (findPage() != null)
		{
			getRequestCycle().find(AjaxRequestTarget.class).ifPresent(target -> {
				String jsToRun = getJsOverlay(overlay);
				target.appendJavaScript(jsToRun);
			});
		}

		return this;
	}

	/**
	 * Clear all overlays.
	 *
	 * @return This
	 */
	public OpenLayersMap clearOverlays()
	{
		for (Overlay overlay : overlays)
		{
			for (OverlayListenerBehavior behavior : overlay.getBehaviors())
			{
				remove(behavior);
			}
		}
		overlays.clear();

		if (findPage() != null)
		{
			getRequestCycle().find(AjaxRequestTarget.class).ifPresent(target ->
					target.appendJavaScript(getJSinvoke("clearOverlays()")));
		}
		return this;
	}

	public Bounds getBounds()
	{
		return bounds;
	}

	public PopupListener getCallbackListener()
	{
		return callbackListener;
	}

	public LonLat getCenter()
	{
		return center;
	}

	public List<IJavascriptComponent> getControls()
	{
		return controls;
	}

	/**
	 * Generates the JavaScript used to instantiate this OpenlayersMap as an JavaScript class on the
	 * client side.
	 *
	 * @return The generated JavaScript
	 */
	protected String getJSinit()
	{

		StringBuffer js = new StringBuffer();

		if (options.size() > 0)
		{
			js.append("\nvar options = {");
			boolean first = true;
			for (String key : options.keySet())
			{
				if (first)
				{
					first = false;
				}
				else
				{
					js.append(",\n");
				}
				js.append(key + ":" + options.get(key));
			}
			js.append("};\n");
			js.append("new WicketOMap('" + map.getMarkupId() + "', options, null, " +
				String.valueOf(showMarkersInLayerSwitcher) + ");\n");
		}
		else
		{
			js.append("new WicketOMap('" + map.getMarkupId() + "', null, null, " +
				String.valueOf(showMarkersInLayerSwitcher) + ");\n");
		}

		for (Layer layer : layers)
		{
			if (layer instanceof WMS)
			{
				WMS wms = (WMS)layer;
				js.append("var wms" + wms.getId() + " =" + wms.getJSconstructor() + ";\n");
				js.append(getJSinvoke("addLayer(wms" + wms.getId() + "," + wms.getId() + ")"));
			}
			if (layer instanceof GMap)
			{
				GMap gmap = (GMap)layer;
				js.append("var gmap" + gmap.getId() + " =" + gmap.getJSconstructor() + ";\n");
				js.append(getJSinvoke("addLayer(gmap" + gmap.getId() + "," + gmap.getId() + ")"));
			}
			if (layer instanceof OSM)
			{
				OSM osm = (OSM)layer;
				js.append("var osm" + osm.getId() + " =" + osm.getJSconstructor() + ";\n");
				js.append(getJSinvoke("addLayer(osm" + osm.getId() + "," + osm.getId() + ")"));
			}
			if (layer instanceof WFS)
			{
				WFS wfs = (WFS)layer;
				js.append("var wfs" + wfs.getId() + " =" + wfs.getJSconstructor() + ";\n");
				js.append(getJSinvoke("addLayer(wfs" + wfs.getId() + "," + wfs.getId() + ")"));

			}
			if (layer instanceof Vector)
			{
				Vector vec = (Vector)layer;
				js.append("var vec" + vec.getId() + " =" + vec.getJSconstructor() + ";\n");
				js.append(getJSinvoke("addLayer(vec" + vec.getId() + "," + vec.getId() + ")"));

			}
		}

		/*
		 * If zoom and center are available then use them on the initial map rendering.
		 */
		if (zoom != DEFAULT_ZOOM)
		{

			if (!center.equals(DEFAULT_CENTER))
				js.append(getJSsetCenter(center, zoom));
			else
				js.append(getJSsetZoom(zoom));
		}
		else
		{
			js.append(getJSinvoke("zoomToMaxExtent()"));
		}


		for (IJavascriptComponent control : controls)
		{
			js.append(control.getJSadd(this));
		}
		// Add the overlays.
		for (Overlay overlay : overlays)
		{

			js.append(getJsOverlay(overlay));
		}
		js.append(getJSinvoke("setPopupId('" + getInfoWindow().getContent().getMarkupId() + "')"));

		if (businessLogicProjection != null)
		{
			js.append(getJSSetBusinessLogicProjection());
		}

		return js.toString();
	}

	public String getJSInstance()
	{
		return "Wicket.omaps['" + map.getMarkupId() + "']";
	}

	/**
	 * Convenience method for generating a JavaScript call on this Openlayermap with the given
	 * invocation.
	 *
	 * @param invocation
	 *            The JavaScript call to invoke on this Openlayermap.
	 * @return The generated JavaScript.
	 */
	// TODO Could this become default or protected?
	public String getJSinvoke(String invocation)
	{
		return "Wicket.omaps['" + map.getMarkupId() + "']." + invocation + ";\n";
	}

	public String getJSinvokeNoLineEnd(String invocation)
	{
		return "Wicket.omaps['" + map.getMarkupId() + "']." + invocation;
	}

	private String getJsOverlay(Overlay overlay)
	{
		String jsToRun = overlay.getJSadd(this) + "\n";
		if (overlay instanceof Marker)
		{
			Marker marker = (Marker)overlay;
			// if marker has popup and there are no events attached then attach
			// default listener
			if (marker.getPopup() != null &&
				(marker.getEvents() == null || marker.getEvents().length == 0))
			{
				// add mousedown listener!
				marker.addEvent(EventType.mousedown);
			}
			// add listeners
			for (EventType evt : marker.getEvents())
			{
				jsToRun += getJSinvoke("addMarkerListener('" + evt.name() + "','" +
					callbackListener.getCallBackForMarker(marker) + "'," +
					marker.getOverlayJSVar() + ")");
			}
			if (marker.getIcon() != null)
			{
				// prepend icon stuff
				jsToRun = marker.getIcon().getSize().getJSadd() +
					marker.getIcon().getOffset().getJSadd() + marker.getIcon().getJSadd() + jsToRun;
			}
		}
		return jsToRun;

	}

	private String getJSpanDirection(int dx, int dy)
	{
		return getJSinvoke("panDirection(" + dx + "," + dy + ")");
	}

	private String getJSsetCenter(LonLat center, Integer zoom)
	{
		if (center != null && zoom != null)
			return getJSinvoke("setCenter(" + center.getJSconstructor() + ", " + zoom + ")");
		else
			return "";
	}

	private String getJSsetDoubleClickZoomEnabled(boolean enabled)
	{
		return getJSinvoke("setDoubleClickZoomEnabled(" + enabled + ")");
	}

	private String getJSsetDraggingEnabled(boolean enabled)
	{
		return getJSinvoke("setDraggingEnabled(" + enabled + ")");
	}

	private String getJSsetScrollWheelZoomEnabled(boolean enabled)
	{
		return getJSinvoke("setScrollWheelZoomEnabled(" + enabled + ")");
	}

	private String getJSsetZoom(Integer zoom)
	{
		return zoom != null ? getJSinvoke("setZoom(" + zoom + ")") : "";
	}

	private String getJSzoomIn()
	{
		return getJSinvoke("zoomIn()");
	}

	private String getJSzoomOut()
	{
		return getJSinvoke("zoomOut()");
	}

	public List<Layer> getLayers()
	{
		return layers;
	}

	public List<Overlay> getOverlays()
	{
		return Collections.unmodifiableList(overlays);
	}

	public Integer getZoom()
	{
		return zoom;
	}

	public boolean isExternalControls()
	{
		return externalControls;
	}

	/**
	 * Remove a control.
	 *
	 * @param control
	 *            control to remove
	 * @return This
	 */
	public OpenLayersMap removeControl(IJavascriptComponent control)
	{
		controls.remove(control);

		if (findPage() != null)
		{
			getRequestCycle().find(AjaxRequestTarget.class).ifPresent(target ->
					target.appendJavaScript(control.getJSremove(OpenLayersMap.this)));
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
	public OpenLayersMap removeOverlay(Overlay overlay)
	{
		while (overlays.contains(overlay))
		{
			overlays.remove(overlay);
		}
		overlay.getBehaviors().forEach(this::remove);

		if (findPage() != null)
		{
			getRequestCycle().find(AjaxRequestTarget.class).ifPresent(target ->
					target.appendJavaScript(overlay.getJSremove(OpenLayersMap.this)));
		}

		return this;
	}

	/**
	 * Set the center.
	 *
	 * @param center
	 *            center to set
	 */
	public void setCenter(LonLat center, Integer zoom)
	{
		if (!this.center.equals(center))
		{
			this.center = center;
			this.zoom = zoom;

			if (findPage() != null)
			{
				getRequestCycle().find(AjaxRequestTarget.class).ifPresent(target ->
						target.appendJavaScript(getJSsetCenter(center, zoom)));
			}
		}
	}

	public void setExternalControls(boolean externalControls)
	{
		this.externalControls = externalControls;
	}

	public void setLayers(List<Layer> layers)
	{
		this.layers = layers;
	}

	public void setOverlays(List<Overlay> overlays)
	{
		clearOverlays();
		for (Overlay overlay : overlays)
		{
			addOverlay(overlay);
		}
	}

	public void setZoom(Integer level)
	{
		if (zoom != level)
		{
			zoom = level;

			if (findPage() != null)
			{
				getRequestCycle().find(AjaxRequestTarget.class).ifPresent(target ->
						target.appendJavaScript(getJSsetZoom(zoom)));
			}
		}
	}

	/**
	 * Update state from a request to an AJAX target.
	 */
	public void update(AjaxRequestTarget target)
	{
		Request request = RequestCycle.get().getRequest();

		// Attention: don't use setters as this will result in an endless
		// AJAX request loop
		center = LonLat.parseWithNames(request.getRequestParameters()
			.getParameterValue("centerConverted")
			.toString());
		zoom = Integer.parseInt(request.getRequestParameters()
			.getParameterValue("zoomConverted")
			.toString());
		bounds = Bounds.parseWithNames(request.getRequestParameters()
			.getParameterValue("boundsConverted")
			.toString());

		getInfoWindow().update(target);
	}

	public void setInfoWindow(InfoWindow infoWindow)
	{
		this.infoWindow = infoWindow;
	}

	public InfoWindow getInfoWindow()
	{
		return infoWindow;
	}


    /**
     * @see org.apache.wicket.Component#onRender()
     */
	@Override
	protected void onRender()
	{
		super.onRender();

		RuntimeConfigurationType configurationType = Application.get().getConfigurationType();
		if (configurationType.equals(RuntimeConfigurationType.DEVELOPMENT) &&
			!Application.get().getMarkupSettings().getStripWicketTags())
		{
			log.warn("Application is in DEVELOPMENT mode && Wicket tags are not stripped,"
				+ " Firefox 3.0 will not render the OMap."
				+ " Change to DEPLOYMENT mode  || turn on Wicket tags stripping." + " See:"
				+ " http://www.nabble.com/Gmap2-problem-with-Firefox-3.0-to18137475.html.");
		}
	}

	public void setBounds(Bounds bounds)
	{
		this.bounds = bounds;
	}

	public void setCenter(LonLat center)
	{
		setCenter(center, zoom);
	}

	/**
	 * @param showMarkersInLayerSwitcher
	 *            if true the internal markers layer will be visible in the
	 *            OpenLayers.Control.LayerSwitcher
	 *
	 *            Default is true.
	 *
	 *            Set to false to hide the markers layer from the LayerSwitcher.
	 */
	public void setShowMarkersInLayerSwitcher(boolean showMarkersInLayerSwitcher)
	{
		this.showMarkersInLayerSwitcher = showMarkersInLayerSwitcher;
	}

	public void setBusinessLogicProjection(String businessLogicProjection)
	{
		this.businessLogicProjection = businessLogicProjection;
		getRequestCycle().find(AjaxRequestTarget.class).ifPresent(target ->
				target.appendJavaScript(getJSSetBusinessLogicProjection()));
	}

	public String getBusinessLogicProjection()
	{
		return businessLogicProjection;
	}

	private String getJSSetBusinessLogicProjection()
	{
		if (businessLogicProjection == null)
		{
			return getJSinvoke("setBusinessLogicProjection(null)");
		}
		return getJSinvoke("setBusinessLogicProjection('" + businessLogicProjection + "')");
	}
}
