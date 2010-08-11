package org.wicketstuff.openlayers.event;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.openlayers.api.LonLat;

public abstract class ClickEventListenerBehavior extends EventListenerBehavior {

	@Override
	public String getJSaddListener() {
		return getOpenLayersMap().getJSinvoke(
				"addClickListener('" + getCallbackUrl() + "')");
	}

	@Override
	protected String getEvent() {
		return "click";
	}

	@Override
	protected void onEvent(AjaxRequestTarget target) {
		Request request = RequestCycle.get().getRequest();
		String lon = request.getParameter("lon");
		String lat = request.getParameter("lat");
		LonLat lonLat = new LonLat(Double.parseDouble(lon), Double
				.parseDouble(lat));
		onClick(lonLat, target);

	}

	protected abstract void onClick(LonLat lonLat, AjaxRequestTarget target);

}
