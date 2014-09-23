package org.wicketstuff.openlayers.api;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.openlayers.OpenLayersMap;

public class InfoWindow extends WebMarkupContainer
{

	private static final long serialVersionUID = 1L;

	private LonLat latLng;

	private Marker marker;

	private PopupWindowPanel content = new PopupWindowPanel();

	public InfoWindow()
	{
		super("infoWindow");

		setOutputMarkupId(true);
		add(content);
	}

	/**
	 * Update state from a request to an AJAX target.
	 */
	public void update(AjaxRequestTarget target)
	{
		Request request = RequestCycle.get().getRequest();

		if (Boolean.parseBoolean(request.getRequestParameters()
			.getParameterValue("hidden")
			.toString()))
		{
			// Attention: don't use close() as this might result in an
			// endless AJAX request loop
			marker = null;
			latLng = null;
		}
	}

	public final String getJSinit()
	{
		if (latLng != null)
		{
			return getJSopen(latLng);
		}

		if (marker != null)
		{
			return getJSopen(marker);
		}

		return "";
	}

	public InfoWindow open(LonLat latLng)
	{

		this.latLng = latLng;
		marker = null;

		AjaxRequestTarget ajaxRequestTarget = getRequestCycle().find(AjaxRequestTarget.class);
		if (ajaxRequestTarget != null) {
			ajaxRequestTarget.appendJavaScript(getJSopen(latLng));
			ajaxRequestTarget.add(this);
		}

		return this;
	}

	public InfoWindow open(Marker marker)
	{

		latLng = null;
		this.marker = marker;

		AjaxRequestTarget ajaxRequestTarget = getRequestCycle().find(AjaxRequestTarget.class);
		if (ajaxRequestTarget != null) {
			ajaxRequestTarget.appendJavaScript(getJSopen(marker));
			ajaxRequestTarget.add(this);
		}

		return this;
	}

	public boolean isOpen()
	{
		return latLng != null || marker != null;
	}

	public void close()
	{
		marker = null;
		latLng = null;

		AjaxRequestTarget ajaxRequestTarget = getRequestCycle().find(AjaxRequestTarget.class);
		if (ajaxRequestTarget != null) {
			ajaxRequestTarget.appendJavaScript(getJSclose());
			ajaxRequestTarget.add(this);
		}
	}

	private String getJSopen(LonLat latLng)
	{
		StringBuffer buffer = new StringBuffer();

		buffer.append("openInfoWindowTabs(");
		buffer.append(latLng.getJSconstructor());


		return getGMap2().getJSinvoke(buffer.toString());
	}

	private String getJSopen(Marker marker)
	{
		StringBuffer buffer = new StringBuffer();

		buffer.append("openMarkerInfoWindowTabs('");
		buffer.append(marker.getId());


		return getGMap2().getJSinvoke(buffer.toString());
	}

	private String getJSclose()
	{
		return getGMap2().getJSinvoke("closeInfoWindow()");
	}

	private OpenLayersMap getGMap2()
	{
		return findParent(OpenLayersMap.class);
	}

	public WebMarkupContainer getContent()
	{
		return content;
	}

	public void setContent(PopupWindowPanel content)
	{
		this.content = content;
	}


}
