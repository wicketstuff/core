package org.wicketstuff.openlayers.api;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.openlayers.OpenLayersMap;

public class InfoWindow extends WebMarkupContainer {

	private LonLat latLng;

	private Marker marker;

	private PopupWindowPanel content = new PopupWindowPanel();

	public InfoWindow() {
		super("infoWindow");

		setOutputMarkupId(true);
		add(content);
	}

	/**
	 * Update state from a request to an AJAX target.
	 */
	public void update(AjaxRequestTarget target) {
		Request request = RequestCycle.get().getRequest();

		if (Boolean.parseBoolean(request.getRequestParameters().getParameterValue("hidden").toString())) {
			// Attention: don't use close() as this might result in an
			// endless AJAX request loop
			marker = null;
			latLng = null;
		}
	}

	public final String getJSinit() {
		if (latLng != null) {
			return getJSopen(latLng);
		}

		if (marker != null) {
			return getJSopen(marker);
		}

		return "";
	}

	/**
	 * Open an info window.
	 * 
	 * @param content
	 *            content to open in info window
	 * @return This
	 */
	public InfoWindow open(LonLat latLng, Component content) {
		return open(latLng, content);
	}

	/**
	 * Open an info window.
	 * 
	 * @param content
	 *            content to open in info window
	 * @return This
	 */
	public InfoWindow open(Marker marker, Component content) {
		return open(marker, content);
	}

	public InfoWindow open(LonLat latLng) {

		this.latLng = latLng;
		this.marker = null;

		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavaScript(getJSopen(latLng));
			AjaxRequestTarget.get().addComponent(this);
		}

		return this;
	}

	public InfoWindow open(Marker marker) {

		this.latLng = null;
		this.marker = marker;

		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavaScript(getJSopen(marker));
			AjaxRequestTarget.get().addComponent(this);
		}

		return this;
	}

	public boolean isOpen() {
		return (latLng != null || marker != null);
	}

	public void close() {
		marker = null;
		latLng = null;

		if (AjaxRequestTarget.get() != null) {
			AjaxRequestTarget.get().appendJavaScript(getJSclose());
			AjaxRequestTarget.get().addComponent(this);
		}
	}

	private String getJSopen(LonLat latLng) {
		StringBuffer buffer = new StringBuffer();

		buffer.append("openInfoWindowTabs(");
		buffer.append(latLng.getJSconstructor());


		return getGMap2().getJSinvoke(buffer.toString());
	}

	private String getJSopen(Marker marker) {
		StringBuffer buffer = new StringBuffer();

		buffer.append("openMarkerInfoWindowTabs('");
		buffer.append(marker.getId());


		return getGMap2().getJSinvoke(buffer.toString());
	}

	private String getJSclose() {
		return getGMap2().getJSinvoke("closeInfoWindow()");
	}

	private OpenLayersMap getGMap2() {
		return (OpenLayersMap) findParent(OpenLayersMap.class);
	}

	public WebMarkupContainer getContent() {
		return content;
	}

	public void setContent(PopupWindowPanel content) {
		this.content = content;
	}
	

}
