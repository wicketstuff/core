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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GEvent;
import wicket.contrib.gmap.api.GInfoWindow;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GLatLngBounds;
import wicket.contrib.gmap.api.GMapType;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GOverlay;
import wicket.contrib.gmap.event.GEventListenerBehavior;

/**
 * Wicket component to embed <a href="http://maps.google.com">Google Maps</a>
 * into your pages.
 * <p>
 * The Google Maps API requires an API key to use it. You will need to generate
 * one for each deployment context you have. See the <a
 * href="http://www.google.com/apis/maps/signup.html">Google Maps API sign up
 * page</a> for more information.
 */
public class GMap2 extends Panel implements GOverlayContainer
{
	/** log. */
	private static final Logger log = LoggerFactory.getLogger(GMap2.class);

	private static final long serialVersionUID = 1L;

	private GLatLng center = new GLatLng(37.4419, -122.1419);

	private boolean googleBarEnabled = false;
	
	private boolean draggingEnabled = true;

	private boolean doubleClickZoomEnabled = false;

	private boolean scrollWheelZoomEnabled = false;

	private GMapType mapType = GMapType.G_NORMAL_MAP;

	private int zoom = 13;

	private final Set<GControl> controls = new HashSet<GControl>();

	private final List<GOverlay> overlays = new ArrayList<GOverlay>();

	private final WebMarkupContainer map;

	private final GInfoWindow infoWindow;

	private GLatLngBounds bounds;

	private final OverlayListener overlayListener;

	/**
	 * Construct.
	 *
	 * @param id
	 * @param gMapKey
	 *            Google gmap API KEY
	 */
	public GMap2(final String id, final String gMapKey)
	{
		this(id, new GMapHeaderContributor(gMapKey), new ArrayList<GOverlay>());
	}

	/**
	 * Construct.
	 *
	 * @param id
	 * @param gMapKey
	 *            Google gmap API KEY
	 * @param overlays
	 * @deprecated The usage is discouraged. Use this(String, String) instead
	 *             and add the overlays later on.
	 */
	@Deprecated
	public GMap2(final String id, final String gMapKey, List<GOverlay> overlays)
	{
		this(id, new GMapHeaderContributor(gMapKey), overlays);
	}

	/**
	 * Construct.
	 *
	 * @param id
	 * @param headerContrib
	 */
	public GMap2(final String id, final GMapHeaderContributor headerContrib)
	{
		super(id);

		add(headerContrib);

		infoWindow = new GInfoWindow();
		add(infoWindow);

		map = new WebMarkupContainer("map");
		map.setOutputMarkupId(true);
		add(map);
		overlayListener = new OverlayListener();
		add(overlayListener);
	}

	/**
	 * Construct.
	 *
	 * @param id
	 * @param headerContrib
	 * @param overlays
	 * @deprecated The usage is discouraged. Use this(String, String) instead
	 *             and add the overlays later on.
	 */
	@Deprecated
	public GMap2(final String id, final GMapHeaderContributor headerContrib, List<GOverlay> overlays)
	{
		this(id, headerContrib);

		for (GOverlay overlay : overlays)
		{
			addOverlay(overlay);
		}
	}

	
	/**
	 * @see org.apache.wicket.Component#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderOnDomReadyJavascript(getJSinit());
	}


	/**
	 * @see org.apache.wicket.Component#onBeforeRender()
	 */
	@Override
	protected void onBeforeRender() {
		if (Application.DEVELOPMENT.equalsIgnoreCase(Application.get().getConfigurationType())
				&& !Application.get().getMarkupSettings().getStripWicketTags())
		{
			log.warn("Application is in DEVELOPMENT mode && Wicket tags are not stripped,"
					+ " Firefox 3.0 will not render the GMap."
					+ " Change to DEPLOYMENT mode  || turn on Wicket tags stripping." + " See:"
					+ " http://www.nabble.com/Gmap2-problem-with-Firefox-3.0-to18137475.html.");
		}
		super.onBeforeRender();
	}


	/**
	 * Add a control.
	 *
	 * @param control
	 *            control to add
	 * @return This
	 */
	public GMap2 addControl(GControl control)
	{
		controls.add(control);

		if (AjaxRequestTarget.get() != null && findPage() != null)
		{
			AjaxRequestTarget.get().appendJavascript(control.getJSadd(GMap2.this));
		}

		return this;
	}

	/**
	 * Remove a control.
	 *
	 * @param control
	 *            control to remove
	 * @return This
	 */
	public GMap2 removeControl(GControl control)
	{
		controls.remove(control);

		if (AjaxRequestTarget.get() != null && findPage() != null)
		{
			AjaxRequestTarget.get().appendJavascript(control.getJSremove(GMap2.this));
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
	public GMap2 addOverlay(GOverlay overlay)
	{
		overlays.add(overlay);
		overlay.setParent(this);

		if (AjaxRequestTarget.get() != null && findPage() != null)
		{
			AjaxRequestTarget.get().appendJavascript(overlay.getJSadd());
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
	public GMap2 removeOverlay(GOverlay overlay)
	{
		while (overlays.contains(overlay))
		{
			overlays.remove(overlay);
		}

		if (AjaxRequestTarget.get() != null && findPage() != null)
		{
			AjaxRequestTarget.get().appendJavascript(overlay.getJSremove());
		}

		overlay.setParent(null);

		return this;
	}

	/**
	 * Clear all overlays.
	 *
	 * @return This
	 */
	public GMap2 removeAllOverlays()
	{
		for (GOverlay overlay : overlays)
		{
			overlay.setParent(null);
		}
		overlays.clear();
		if (AjaxRequestTarget.get() != null && findPage() != null)
		{
			AjaxRequestTarget.get().appendJavascript(getJSinvoke("clearOverlays()"));
		}
		return this;
	}

	public List<GOverlay> getOverlays()
	{
		return Collections.unmodifiableList(overlays);
	}

	public Set<GControl> getControls()
	{
		return Collections.unmodifiableSet(controls);
	}

	public GLatLng getCenter()
	{
		return center;
	}

	public GLatLngBounds getBounds()
	{
		return bounds;
	}
	
	public void enableGoogleBar(boolean enabled) {
		if (this.googleBarEnabled != enabled)
		{
			googleBarEnabled = enabled;

			if (AjaxRequestTarget.get() != null && findPage() != null)
			{
				AjaxRequestTarget.get().appendJavascript(getJSsetGoogleBarEnabled(enabled));
			}
		}
	}

	public void setDraggingEnabled(boolean enabled)
	{
		if (this.draggingEnabled != enabled)
		{
			draggingEnabled = enabled;

			if (AjaxRequestTarget.get() != null && findPage() != null)
			{
				AjaxRequestTarget.get().appendJavascript(getJSsetDraggingEnabled(enabled));
			}
		}
	}

	public boolean isDraggingEnabled()
	{
		return draggingEnabled;
	}

	public void setDoubleClickZoomEnabled(boolean enabled)
	{
		if (this.doubleClickZoomEnabled != enabled)
		{
			doubleClickZoomEnabled = enabled;

			if (AjaxRequestTarget.get() != null && findPage() != null)
			{
				AjaxRequestTarget.get().appendJavascript(getJSsetDoubleClickZoomEnabled(enabled));
			}
		}
	}

	public boolean isDoubleClickZoomEnabled()
	{
		return doubleClickZoomEnabled;
	}

	public void setScrollWheelZoomEnabled(boolean enabled)
	{
		if (this.scrollWheelZoomEnabled != enabled)
		{
			scrollWheelZoomEnabled = enabled;

			if (AjaxRequestTarget.get() != null && findPage() != null)
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

	public void setMapType(GMapType mapType)
	{
		if (this.mapType != mapType)
		{
			this.mapType = mapType;

			if (AjaxRequestTarget.get() != null && findPage() != null)
			{
				AjaxRequestTarget.get().appendJavascript(mapType.getJSsetMapType(GMap2.this));
			}
		}
	}

	public int getZoom()
	{
		return zoom;
	}

	public void setZoom(int level)
	{
		if (this.zoom != level)
		{
			this.zoom = level;

			if (AjaxRequestTarget.get() != null && findPage() != null)
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
	public void setCenter(GLatLng center)
	{
		if (!this.center.equals(center))
		{
			this.center = center;

			if (AjaxRequestTarget.get() != null && findPage() != null)
			{
				AjaxRequestTarget.get().appendJavascript(getJSsetCenter(center));
			}
		}
	}

	/**
	 * Changes the center point of the map to the given point. If the point is
	 * already visible in the current map view, change the center in a smooth
	 * animation.
	 *
	 * @param center
	 *            the new center of the map
	 */
	public void panTo(GLatLng center)
	{
		if (!this.center.equals(center))
		{
			this.center = center;

			if (AjaxRequestTarget.get() != null && findPage() != null)
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
	 * Generates the JavaScript used to instantiate this GMap2 as an JavaScript
	 * class on the client side.
	 *
	 * @return The generated JavaScript
	 */
	private String getJSinit()
	{
		StringBuffer js = new StringBuffer("new WicketMap2('" + map.getMarkupId() + "');\n");

		js.append(overlayListener.getJSinit());
		js.append(getJSsetCenter(getCenter()));
		js.append(getJSsetZoom(getZoom()));
		js.append(getJSsetDraggingEnabled(draggingEnabled));
		js.append(getJSsetDoubleClickZoomEnabled(doubleClickZoomEnabled));
		js.append(getJSsetScrollWheelZoomEnabled(scrollWheelZoomEnabled));
		js.append(getJSsetGoogleBarEnabled(googleBarEnabled));

		js.append(mapType.getJSsetMapType(this));

		// Add the controls.
		for (GControl control : controls)
		{
			js.append(control.getJSadd(this));
		}

		// Add the overlays.
		for (GOverlay overlay : overlays)
		{
			js.append(overlay.getJSadd());
		}

		js.append(infoWindow.getJSinit());

		for (Object behavior : getBehaviors(GEventListenerBehavior.class))
		{
			js.append(((GEventListenerBehavior)behavior).getJSaddListener());
		}


		return js.toString();
	}

	/**
	 * Convenience method for generating a JavaScript call on this GMap2 with
	 * the given invocation.
	 *
	 * @param invocation
	 *            The JavaScript call to invoke on this GMap2.
	 * @return The generated JavaScript.
	 */
	// TODO Could this become default or protected?
	public String getJSinvoke(String invocation)
	{
		return "Wicket.maps['" + map.getMarkupId() + "']." + invocation + ";\n";
	}

	/**
	 * @see #fitMarkers(List, boolean, double)
	 */
	public void fitMarkers(final List<GLatLng> markersToShow) {
		fitMarkers(markersToShow, false, 0.0);
	}
	
	/**
	 * @see #fitMarkers(List, boolean, double)
	 */
	public void fitMarkers(final List<GLatLng> markersToShow, boolean showMarkersForPoints) {
		fitMarkers(markersToShow, showMarkersForPoints, 0.0);
	}

	/**
	 * <p>
	 * Makes the map zoom out and centre around all the GLatLng points in
	 * markersToShow.
	 * <p>
	 * Big ups to Doug Leeper for the code.
	 *
	 * @see <a href=
	 *      "http://www.nabble.com/Re%3A-initial-GMap2-bounds-question-p19886673.html"
	 *      >Doug's Nabble post</a>
	 * @param markersToShow
	 *            the points to centre around.
	 * @param showMarkersForPoints
	 *            if true, will also add basic markers to the map for each point
	 *            focused on. Just a simple convenience method - you will probably
	 *            want to turn this off so that you can show more information
	 *            with each marker when clicked etc.
	 */
	public void fitMarkers(final List<GLatLng> markersToShow, boolean showMarkersForPoints, final double zoomAdjustment) {
		if (markersToShow.isEmpty()) {
			log.warn("Empty list provided to GMap2.fitMarkers method.");
			return;
		}

		this.add(new AbstractBehavior() {
			private static final long serialVersionUID = 1L;

			public void renderHead(IHeaderResponse response) {
				StringBuffer buf = new StringBuffer();
				buf.append("var bounds = new GLatLngBounds();\n");
				buf.append("var map = " + GMap2.this.getJSinvoke("map"));

				// Ask google maps to keep extending the bounds to include each
				// point
				for (GLatLng point : markersToShow) {
					buf.append("bounds.extend( " + point.getJSconstructor()
							+ " );\n");
				}

				// set the zoom level that shows the bounds
				buf.append("map.setZoom( map.getBoundsZoomLevel(bounds) + "
						+ zoomAdjustment + ");\n");

				// center in the middle of the bounds
				buf.append("map.setCenter( bounds.getCenter() );\n");

				response.renderOnDomReadyJavascript(buf.toString());
			}
		});

		// show the markers
		if (showMarkersForPoints) {
			for (GLatLng location : markersToShow) {
				this.addOverlay(new GMarker(location));
			}
		}
	}

	private String getJSsetGoogleBarEnabled(boolean enabled)
	{
		return getJSinvoke("setGoogleBarEnabled(" + enabled + ")");
	}

	private String getJSsetDraggingEnabled(boolean enabled)
	{
		return getJSinvoke("setDraggingEnabled(" + enabled + ")");
	}

	private String getJSsetDoubleClickZoomEnabled(boolean enabled)
	{
		return getJSinvoke("setDoubleClickZoomEnabled(" + enabled + ")");
	}

	private String getJSsetScrollWheelZoomEnabled(boolean enabled)
	{
		return getJSinvoke("setScrollWheelZoomEnabled(" + enabled + ")");
	}

	private String getJSsetZoom(int zoom)
	{
		return getJSinvoke("setZoom(" + zoom + ")");
	}

	private String getJSsetCenter(GLatLng center)
	{
		if (center != null)
			return getJSinvoke("setCenter(" + center.getJSconstructor() + ")");
		else
			return "";
	}

	private String getJSpanDirection(int dx, int dy)
	{
		return getJSinvoke("panDirection(" + dx + "," + dy + ")");
	}

	private String getJSpanTo(GLatLng center)
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
	public void update(AjaxRequestTarget target)
	{
		Request request = RequestCycle.get().getRequest();

		// Attention: don't use setters as this will result in an endless
		// AJAX request loop
		bounds = GLatLngBounds.parse(request.getRequestParameters().getParameterValue("bounds").toString());
		center = GLatLng.parse(request.getRequestParameters().getParameterValue("center").toString());
		zoom = request.getRequestParameters().getParameterValue("zoom").toInt();
		mapType = GMapType.valueOf(request.getRequestParameters().getParameterValue("currentMapType").toString());

		infoWindow.update(target);
	}

	public void setOverlays(List<GOverlay> overlays)
	{
		removeAllOverlays();
		for (GOverlay overlay : overlays)
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
		public void onComponentTag(Component component, ComponentTag tag)
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

	public class SetZoomBehavior extends JSMethodBehavior
	{
		private static final long serialVersionUID = 1L;

		private final int zoom;

		public SetZoomBehavior(final String event, final int zoom)
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

	public class SetCenterBehavior extends JSMethodBehavior
	{
		private static final long serialVersionUID = 1L;

		private final GLatLng gLatLng;

		public SetCenterBehavior(String event, GLatLng gLatLng)
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

		private final GMapType mapType;

		public SetMapTypeBehavior(String event, GMapType mapType)
		{
			super(event);
			this.mapType = mapType;
		}

		@Override
		protected String getJSinvoke()
		{
			return mapType.getJSsetMapType(GMap2.this);
		}
	}

	public class OverlayListener extends AbstractDefaultAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		@Override
		protected void respond(AjaxRequestTarget target)
		{
			Request request = RequestCycle.get().getRequest();

			String overlayId = request.getRequestParameters().getParameterValue("overlay.overlayId").toString();
			String event = request.getRequestParameters().getParameterValue("overlay.event").toString();
			// TODO this is ugly
			// the id's of the Overlays are unique within the ArrayList
			// maybe we should change that collection
			for (GOverlay overlay : overlays)
			{
				if (overlay.getId().equals(overlayId))
				{
					overlay.onEvent(target, GEvent.valueOf(event));
					break;
				}
			}
		}

		public Object getJSinit()
		{
			return GMap2.this.getJSinvoke("overlayListenerCallbackUrl = '" + this.getCallbackUrl() + "'");
		}
	}
}