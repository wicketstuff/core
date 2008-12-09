package org.wicketstuff.openlayers.event;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.wicketstuff.openlayers.OpenLayersMap;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public abstract class DrawListenerBehavior extends AbstractDefaultAjaxBehavior {

	@Override
	public void renderHead(IHeaderResponse response) {
		// TODO Auto-generated method stub
		super.renderHead(response);
		response.renderOnDomReadyJavascript(getJSaddListener());
	}

	@Override
	protected void onBind() {
		if (!(getComponent() instanceof OpenLayersMap)) {
			throw new IllegalArgumentException(
					"must be bound to Openlayers map");
		}
	}

	public String getJSaddListener() {
		return getOpenLayersMap().getJSinvoke(
				"addDrawFeature('" + getCallbackUrl() + "')");
	}

	protected final OpenLayersMap getOpenLayersMap() {
		return (OpenLayersMap) getComponent();
	}

	/**
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected final void respond(AjaxRequestTarget target) {
		onEvent(target);
	}

	/**
	 * Typically response parameters that are meant for this event are picket up
	 * and made available for the further processing.
	 * 
	 * @param target
	 *            Target to add the Components, that need to be redrawn, to.
	 * @throws RuntimeException 
	 */
	protected void onEvent(AjaxRequestTarget target) throws RuntimeException {
		Request request = RequestCycle.get().getRequest();
		String wkt = request.getParameter("wkt");

		WKTReader wktReader = new WKTReader(OpenLayersMap.getGeoFactory());

		Geometry geom=null;
		try {
			geom = wktReader.read(wkt);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Could not parse wkt",e);
		}

		
		onDrawEnded(geom, target);

	}

	protected abstract void onDrawEnded(Geometry poly, AjaxRequestTarget target);

}
