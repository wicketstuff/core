package org.wicketstuff.openlayers.event;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.wicketstuff.openlayers.IOpenLayersMap;
import org.wicketstuff.openlayers.OpenLayersMapUtils;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public abstract class DrawListenerBehavior extends AbstractDefaultAjaxBehavior
{

	private static final long serialVersionUID = 1L;

	@Override
	public void renderHead(Component c, IHeaderResponse response)
	{
		// TODO Auto-generated method stub
		super.renderHead(c, response);
		response.render(OnDomReadyHeaderItem.forScript(getJSaddListener()));
	}

	@Override
	protected void onBind()
	{
		if (!(getComponent() instanceof IOpenLayersMap))
		{
			throw new IllegalArgumentException("must be bound to Openlayers map");
		}
	}

	public String getJSaddListener()
	{
		return getOpenLayersMap().getJSinvoke("addDrawFeature('" + getCallbackUrl() + "')");
	}

	protected final IOpenLayersMap getOpenLayersMap()
	{
		return (IOpenLayersMap)getComponent();
	}

	/**
	 * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected final void respond(AjaxRequestTarget target)
	{
		onEvent(target);
	}

	/**
	 * Typically response parameters that are meant for this event are picket up and made available
	 * for the further processing.
	 * 
	 * @param target
	 *            Target to add the Components, that need to be redrawn, to.
	 * @throws RuntimeException
	 */
	protected void onEvent(AjaxRequestTarget target) throws RuntimeException
	{
		Request request = RequestCycle.get().getRequest();
		String wkt = request.getRequestParameters().getParameterValue("wkt").toString();

		WKTReader wktReader = new WKTReader(OpenLayersMapUtils.getGeoFactory());

		Geometry geom = null;
		try
		{
			geom = wktReader.read(wkt);
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			throw new RuntimeException("Could not parse wkt", e);
		}


		onDrawEnded(geom, target);

	}

	protected abstract void onDrawEnded(Geometry poly, AjaxRequestTarget target);

}
