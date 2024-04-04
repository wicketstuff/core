package org.wicketstuff.openlayers.event;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.openlayers.api.LonLat;

public abstract class ClickEventListenerBehavior extends EventListenerBehavior
{

	private static final long serialVersionUID = 1L;

	@Override
	public String getJSaddListener()
	{
		return getOpenLayersMap().getJSinvoke("addClickListener('" + getCallbackUrl() + "')");
	}

	@Override
	protected String getEvent()
	{
		return "click";
	}

	@Override
	protected void onEvent(AjaxRequestTarget target)
	{
		Request request = RequestCycle.get().getRequest();
		String lon = request.getRequestParameters().getParameterValue("lon").toString();
		String lat = request.getRequestParameters().getParameterValue("lat").toString();
		LonLat lonLat = new LonLat(Double.parseDouble(lon), Double.parseDouble(lat));
		onClick(lonLat, target);

	}

	protected abstract void onClick(LonLat lonLat, AjaxRequestTarget target);

}
