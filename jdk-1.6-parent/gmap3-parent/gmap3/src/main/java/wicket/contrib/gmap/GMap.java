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
package wicket.contrib.gmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
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

import wicket.contrib.gmap.api.GEvent;
import wicket.contrib.gmap.api.GInfoWindow;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GLatLngBounds;
import wicket.contrib.gmap.api.GMapType;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GMarkerOptions;
import wicket.contrib.gmap.api.GOverlay;
import wicket.contrib.gmap.event.GEventListenerBehavior;

/**
 * Wicket component to embed <a href="http://maps.google.com">Google Maps</a> into your pages.
 * <p>
 * The Google Maps API requires an API key to use it. You will need to generate one for each deployment context you
 * have. See the <a href="https://developers.google.com/maps/signup">Google Maps API sign up page</a> for more
 * information.
 */
public class GMap extends Panel implements GOverlayContainer
{
	/** log. */
	private static final Logger log = LoggerFactory.getLogger(GMap.class);

	private static final long serialVersionUID = 1L;

	private GLatLng center = new GLatLng(37.4419, -122.1419);

	private boolean draggingEnabled = true;

	private boolean doubleClickZoomEnabled = false;

	private boolean scrollWheelZoomEnabled = false;

	private GMapType mapType = GMapType.ROADMAP;

	private int zoom = 13;

	private final Map<String, GOverlay> overlays = new HashMap<String, GOverlay>();

	private boolean directUpdate = false;

	private final WebMarkupContainer map;

	private final GInfoWindow infoWindow;

	private GLatLngBounds bounds;

	private OverlayListener overlayListener = null;

	private final boolean initOnFirstView;

	private boolean mapLoaded;

	/**
	 * Construct.
	 * 
	 * Default the header contributor of the component will added and the gmap will be inited directly on rendering of the map. 
	 * 
	 * @param id wicket id
	 */
	public GMap(final String id)
	{
		this(id, false);
	}

	/**
	 * Construct.
	 * 
	 * @param id wicket id
	 * @param initOnFirstView 
	 * 			If true the map supports initialization on the first real view of the map. 
	 * 			Normally this will be done on the first render cycle of wicket even is is not visible.  
	 */
	public GMap(final String id, final boolean initOnFirstView)
	{
		this(id, new GMapHeaderContributor(), initOnFirstView);
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param headerContrib
	 * @param initOnFirstView 
	 * 			If true the map supports initialization on the first real view of the map. 
	 * 			Normally this will be done on the first render cycle of wicket even is is not visible. 
	 */
	public GMap(final String id, final HeaderContributor headerContrib, final boolean initOnFirstView)
	{
		super(id);
		this.initOnFirstView = initOnFirstView;

		if (headerContrib != null)
		{
			add(headerContrib);
		}
		add(new HeaderContributor(new IHeaderContributor()
		{
			private static final long serialVersionUID = 1L;

			/**
			 * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
			 */
			public void renderHead(final IHeaderResponse response)
			{
				if (directUpdate)
				{
					response.renderOnDomReadyJavascript(getJSinit());
				}
			}
		}));

		infoWindow = new GInfoWindow();
		add(infoWindow);

		map = new WebMarkupContainer("map");
		map.setOutputMarkupId(true);
		add(map);

		if (activateOverlayListener())
		{
			overlayListener = new OverlayListener();
			add(overlayListener);
		}
	}

	public String getMapId()
	{
		return map.getMarkupId();
	}

	protected boolean activateOverlayListener()
	{
		return true;
	}

	/**
	 * @see org.apache.wicket.MarkupContainer#onRender(org.apache.wicket.markup.MarkupStream)
	 */
	@Override
	protected void onRender(final MarkupStream markupStream)
	{
		super.onRender(markupStream);
		if (Application.DEVELOPMENT.equalsIgnoreCase(Application.get().getConfigurationType())
			&& !Application.get().getMarkupSettings().getStripWicketTags())
		{
			log.warn("Application is in DEVELOPMENT mode && Wicket tags are not stripped,"
				+ "Some Chrome Versions will not render the GMap."
				+ " Change to DEPLOYMENT mode  || turn on Wicket tags stripping." + " See:"
				+ " http://www.nabble.com/Gmap2-problem-with-Firefox-3.0-to18137475.html.");
		}
	}

	/**
	 * Add an overlay.
	 * 
	 * @see wicket.contrib.gmap.GOverlayContainer#addOverlay(wicket.contrib.gmap.api.GOverlay)
	 * @param overlay
	 *            overlay to add
	 * @return This
	 */
	public GMap addOverlay(final GOverlay overlay)
	{
		overlays.put(overlay.getId(), overlay);
		overlay.setParent(this);

		if (AjaxRequestTarget.get() != null && findPage() != null && directUpdate)
		{
			AjaxRequestTarget.get().appendJavascript(overlay.getJS());
		}

		return this;
	}

	/**
	 * Remove an overlay.
	 * 
	 * @see wicket.contrib.gmap.GOverlayContainer#removeOverlay(wicket.contrib.gmap.api.GOverlay)
	 * @param overlay
	 *            overlay to remove
	 * @return This
	 */
	public GMap removeOverlay(final GOverlay overlay)
	{
		while (overlays.containsKey(overlay.getId()))
		{
			overlays.remove(overlay.getId());
		}

		if (AjaxRequestTarget.get() != null && findPage() != null && directUpdate)
		{
			AjaxRequestTarget.get().appendJavascript(overlay.getJSremove());
		}

		overlay.setParent(null);

		return this;
	}

	/**
	 * Clear all overlays.
	 * 
	 * @see wicket.contrib.gmap.GOverlayContainer#removeAllOverlays()
	 * @return This
	 */
	public GMap removeAllOverlays()
	{
		for (final GOverlay overlay : overlays.values())
		{
			overlay.setParent(null);
		}
		overlays.clear();
		if (AjaxRequestTarget.get() != null && findPage() != null && directUpdate)
		{
			AjaxRequestTarget.get().appendJavascript(getJSinvoke("clearOverlays()"));
		}
		return this;
	}

	/**
	 * @see wicket.contrib.gmap.GOverlayContainer#getOverlays()
	 */
	public List<GOverlay> getOverlays()
	{
		return Collections.unmodifiableList(new ArrayList<GOverlay>(overlays.values()));
	}

	public GLatLng getCenter()
	{
		return center;
	}

	public GLatLngBounds getBounds()
	{
		return bounds;
	}

	public void setDraggingEnabled(final boolean enabled)
	{
		if (this.draggingEnabled != enabled)
		{
			draggingEnabled = enabled;

			if (AjaxRequestTarget.get() != null && findPage() != null && directUpdate)
			{
				AjaxRequestTarget.get().appendJavascript(getJSsetDraggingEnabled(enabled));
			}
		}
	}

	public boolean isDraggingEnabled()
	{
		return draggingEnabled;
	}

	public void setDoubleClickZoomEnabled(final boolean enabled)
	{
		if (this.doubleClickZoomEnabled != enabled)
		{
			doubleClickZoomEnabled = enabled;

			if (AjaxRequestTarget.get() != null && findPage() != null && directUpdate)
			{
				AjaxRequestTarget.get().appendJavascript(getJSsetDoubleClickZoomEnabled(enabled));
			}
		}
	}

	public boolean isDoubleClickZoomEnabled()
	{
		return doubleClickZoomEnabled;
	}

	public void setScrollWheelZoomEnabled(final boolean enabled)
	{
		if (this.scrollWheelZoomEnabled != enabled)
		{
			scrollWheelZoomEnabled = enabled;

			if (AjaxRequestTarget.get() != null && findPage() != null && directUpdate)
			{
				AjaxRequestTarget.get().appendJavascript(getJSsetScrollWheelZoomEnabled(enabled));
			}
		}
	}

	public boolean isScrollWheelZoomEnabled()
	{
		return scrollWheelZoomEnabled;
	}

	public GMapType getMapType()
	{
		return mapType;
	}

	public void setMapType(final GMapType mapType)
	{
		if (this.mapType != mapType)
		{
			this.mapType = mapType;

			if (AjaxRequestTarget.get() != null && findPage() != null && directUpdate)
			{
				AjaxRequestTarget.get().appendJavascript(mapType.getJSsetMapType(GMap.this));
			}
		}
	}

	public int getZoom()
	{
		return zoom;
	}

	public void setZoom(final int level)
	{
		if (this.zoom != level)
		{
			this.zoom = level;

			if (AjaxRequestTarget.get() != null && findPage() != null && directUpdate)
			{
				AjaxRequestTarget.get().appendJavascript(getJSsetZoom(zoom));
			}
		}
	}

	/**
	 * Set the center.
	 * 
	 * @param center
	 *            center to set
	 */
	public void setCenter(final GLatLng center)
	{
		if (!this.center.equals(center))
		{
			this.center = center;

			if (AjaxRequestTarget.get() != null && findPage() != null && directUpdate)
			{
				AjaxRequestTarget.get().appendJavascript(getJSsetCenter(center));
			}
		}
	}

	/**
	 * Changes the center point of the map to the given point. If the point is already visible in the current map view,
	 * change the center in a smooth animation.
	 * 
	 * @param center
	 *            the new center of the map
	 */
	public void panTo(final GLatLng center)
	{
		if (!this.center.equals(center))
		{
			this.center = center;

			if (AjaxRequestTarget.get() != null && findPage() != null && directUpdate)
			{
				AjaxRequestTarget.get().appendJavascript(getJSpanTo(center));
			}
		}
	}

	public GInfoWindow getInfoWindow()
	{
		return infoWindow;
	}

	/**
	 * Generates the JavaScript used to instantiate this GMap3 as an JavaScript class on the client side.
	 * 
	 * @return The generated JavaScript
	 */
	public String getJSinit()
	{
		final StringBuffer js = new StringBuffer("new WicketMap('" + map.getMarkupId() + "');\n");

		js.append(getJSinvoke("clearOverlays()"));
		if (activateOverlayListener())
		{
			js.append(overlayListener.getJSinit());
		}
		js.append(getJSsetCenter(getCenter()));
		js.append(getJSsetZoom(getZoom()));
		js.append(getJSsetDraggingEnabled(draggingEnabled));
		js.append(getJSsetDoubleClickZoomEnabled(doubleClickZoomEnabled));
		js.append(getJSsetScrollWheelZoomEnabled(scrollWheelZoomEnabled));

		js.append(mapType.getJSsetMapType(this));

		// Add the overlays.
		for (final GOverlay overlay : overlays.values())
		{
			js.append(overlay.getJS());
		}
		for (final Object behavior : getBehaviors(GEventListenerBehavior.class))
		{
			js.append(((GEventListenerBehavior) behavior).getJSaddListener());
		}

		return js.toString();
	}

	/**
	 * Convenience method for generating a JavaScript call on this GMap2 with the given invocation.
	 * 
	 * @param invocation
	 *            The JavaScript call to invoke on this GMap2.
	 * @return The generated JavaScript.
	 */
	public String getJSinvoke(final String invocation)
	{
		return getJsReference() + "." + invocation + ";\n";
	}

	/**
	 * Build a reference in JS-Scope.
	 */
	public String getJsReference()
	{
		return "Wicket.maps['" + map.getMarkupId() + "']";
	}

	/**
	 * @see #fitMarkers(List, boolean, double)
	 */
	public void fitMarkers(final List<GLatLng> markersToShow)
	{
		fitMarkers(markersToShow, false, 0.0);
	}

	/**
	 * @see #fitMarkers(List, boolean, double)
	 */
	public void fitMarkers(final List<GLatLng> markersToShow, final boolean showMarkersForPoints)
	{
		fitMarkers(markersToShow, showMarkersForPoints, 0.0);
	}

	/**
	 * <p>
	 * Makes the map zoom out and centre around all the GLatLng points in markersToShow.
	 * <p>
	 * Big ups to Doug Leeper for the code.
	 * 
	 * @see <a href= "http://www.nabble.com/Re%3A-initial-GMap2-bounds-question-p19886673.html" >Doug's Nabble post</a>
	 * @param markersToShow
	 *            the points to centre around.
	 * @param showMarkersForPoints
	 *            if true, will also add basic markers to the map for each point focused on. Just a simple convenience
	 *            method - you will probably want to turn this off so that you can show more information with each
	 *            marker when clicked etc.
	 */
	public void fitMarkers(final List<GLatLng> markersToShow, final boolean showMarkersForPoints,
		final double zoomAdjustment)
	{
		if (markersToShow.isEmpty())
		{
			log.warn("Empty list provided to GMap2.fitMarkers method.");
			return;
		}

		this.add(new HeaderContributor(new IHeaderContributor()
		{
			private static final long serialVersionUID = 1L;

			/**
			 * @see org.apache.wicket.markup.html.IHeaderContributor#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
			 */
			public void renderHead(final IHeaderResponse response)
			{
				final StringBuffer buf = new StringBuffer();
				buf.append("var bounds = new google.maps.LatLngBounds();\n");
				buf.append("var map = " + GMap.this.getJSinvoke("map"));

				// Ask google maps to keep extending the bounds to include each
				// point
				for (final GLatLng point : markersToShow)
				{
					buf.append("bounds.extend( " + point.getJSconstructor() + " );\n");
				}

				buf.append("map.fitBounds( bounds  );\n");

				response.renderOnDomReadyJavascript(buf.toString());
			}
		}));

		// show the markers
		if (showMarkersForPoints)
		{
			for (final GLatLng location : markersToShow)
			{
				this.addOverlay(new GMarker(new GMarkerOptions(this, location)));
			}
		}
	}

	private String getJSsetDraggingEnabled(final boolean enabled)
	{
		return getJSinvoke("setDraggingEnabled(" + enabled + ")");
	}

	private String getJSsetDoubleClickZoomEnabled(final boolean enabled)
	{
		return getJSinvoke("setDoubleClickZoomEnabled(" + enabled + ")");
	}

	private String getJSsetScrollWheelZoomEnabled(final boolean enabled)
	{
		return getJSinvoke("setScrollWheelZoomEnabled(" + enabled + ")");
	}

	private String getJSsetZoom(final int zoom)
	{
		return getJSinvoke("setZoom(" + zoom + ")");
	}

	private String getJSsetCenter(final GLatLng center)
	{
		if (center != null)
		{
			return getJSinvoke("setCenter(" + center.getJSconstructor() + ")");
		}
		return "";
	}

	private String getJSpanDirection(final int dx, final int dy)
	{
		return getJSinvoke("panDirection(" + dx + "," + dy + ")");
	}

	private String getJSpanTo(final GLatLng center)
	{
		return getJSinvoke("panTo(" + center.getJSconstructor() + ")");
	}

	private String getJSzoomOut()
	{
		return getJSinvoke("zoomOut()");
	}

	private String getJSzoomIn()
	{
		return getJSinvoke("zoomIn()");
	}

	/**
	 * Update state from a request to an AJAX target.
	 */
	public void update()
	{
		final Request request = RequestCycle.get().getRequest();

		// Attention: don't use setters as this will result in an endless
		// AJAX request loop
		bounds = GLatLngBounds.parse(request.getParameter("bounds"));
		center = GLatLng.parse(request.getParameter("center"));
		zoom = Integer.parseInt(request.getParameter("zoom"));
		mapType = GMapType.valueOf(request.getParameter("currentMapType"));

		infoWindow.update();
	}

	public void setOverlays(final List<GOverlay> overlays)
	{
		removeAllOverlays();
		for (final GOverlay overlay : overlays)
		{
			addOverlay(overlay);
		}
	}

	private abstract class JSMethodBehavior extends AbstractBehavior
	{

		private static final long serialVersionUID = 1L;

		private final String attribute;

		public JSMethodBehavior(final String attribute)
		{
			this.attribute = attribute;
		}

		/**
		 * @see org.apache.wicket.behavior.AbstractBehavior#onComponentTag(org.apache.wicket.Component,
		 *      org.apache.wicket.markup.ComponentTag)
		 */
		@Override
		public void onComponentTag(final Component component, final ComponentTag tag)
		{
			String invoke = getJSinvoke();

			if (attribute.equalsIgnoreCase("href"))
			{
				invoke = "javascript:" + invoke;
			}

			tag.put(attribute, invoke);
		}

		protected abstract String getJSinvoke();
	}

	public class ZoomOutBehavior extends JSMethodBehavior
	{
		private static final long serialVersionUID = 1L;

		public ZoomOutBehavior(final String event)
		{
			super(event);
		}

		@Override
		protected String getJSinvoke()
		{
			return getJSzoomOut();
		}
	}

	public class ZoomInBehavior extends JSMethodBehavior
	{
		private static final long serialVersionUID = 1L;

		public ZoomInBehavior(final String event)
		{
			super(event);
		}

		@Override
		protected String getJSinvoke()
		{
			return getJSzoomIn();
		}
	}

	public class PanDirectionBehavior extends JSMethodBehavior
	{
		private static final long serialVersionUID = 1L;

		private final int dx;

		private final int dy;

		public PanDirectionBehavior(final String event, final int dx, final int dy)
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

	public class SetZoomBehavior extends JSMethodBehavior
	{
		private static final long serialVersionUID = 1L;

		private final int zoomBehavior;

		public SetZoomBehavior(final String event, final int zoom)
		{
			super(event);
			zoomBehavior = zoom;
		}

		@Override
		protected String getJSinvoke()
		{
			return getJSsetZoom(zoomBehavior);
		}
	}

	public class SetCenterBehavior extends JSMethodBehavior
	{
		private static final long serialVersionUID = 1L;

		private final GLatLng gLatLng;

		public SetCenterBehavior(final String event, final GLatLng gLatLng)
		{
			super(event);
			this.gLatLng = gLatLng;
		}

		@Override
		protected String getJSinvoke()
		{
			return getJSsetCenter(gLatLng);
		}
	}

	public class SetMapTypeBehavior extends JSMethodBehavior
	{
		private static final long serialVersionUID = 1L;

		private final GMapType mapTypeBehavior;

		public SetMapTypeBehavior(final String event, final GMapType mapType)
		{
			super(event);
			mapTypeBehavior = mapType;
		}

		@Override
		protected String getJSinvoke()
		{
			return mapTypeBehavior.getJSsetMapType(GMap.this);
		}
	}

	public class OverlayListener extends AbstractDefaultAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		/**
		 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
		 */
		@Override
		protected void respond(final AjaxRequestTarget target)
		{
			final Request request = RequestCycle.get().getRequest();

			final String overlayId = request.getParameter("overlay.overlayId").replace("overlay", "");
			final String event = request.getParameter("overlay.event");
			final GOverlay overlay = overlays.get(overlayId);
			if (overlay != null)
			{
				overlay.onEvent(target, GEvent.valueOf(event));
			}
		}

		public Object getJSinit()
		{
			return GMap.this.getJSinvoke("overlayListenerCallbackUrl = '" + this.getCallbackUrl() + "'");

		}
	}

	public boolean isDirectUpdate()
	{
		return directUpdate;
	}

	public void setDirectUpdate(boolean directUpdate)
	{
		this.directUpdate = directUpdate;
	}

	public void reLoad(String jsInitExtension)
	{
		AjaxRequestTarget target = AjaxRequestTarget.get();
		if (target != null)
		{
			String jSinit = getJSinit();
			if (jsInitExtension != null)
			{
				jSinit += jsInitExtension;
			}
			if (!mapLoaded && initOnFirstView)
			{
				mapLoaded = true;

				target.appendJavascript("mapResourceRef = '"
					+ RequestCycle.get().urlFor(GMapHeaderContributor.WICKET_GMAP_JS).toString() + "'");
				target.appendJavascript("loadActualMap = '" + jSinit.replaceAll("'", "\"").replaceAll("\n", "")
					+ "';loadMapsAPI();");
			}
			else
			{
				target.appendJavascript(jSinit);
			}
		}
	}

	public boolean isLoaded()
	{
		return mapLoaded || !initOnFirstView;
	}
}
