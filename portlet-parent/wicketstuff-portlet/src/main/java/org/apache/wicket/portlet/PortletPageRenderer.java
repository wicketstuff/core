package org.apache.wicket.portlet;

import javax.portlet.PortletRequest;

import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.render.PageRenderer;
import org.apache.wicket.request.handler.render.WebPageRenderer;

/**
 * {@link PageRenderer} for portlet applications.
 * 
 * @author Konstantinos Karavitis
 * 
 */
public class PortletPageRenderer extends WebPageRenderer
{
	/**
	 * @param renderPageRequestHandler
	 */
	public PortletPageRenderer(RenderPageRequestHandler renderPageRequestHandler)
	{
		super(renderPageRequestHandler);
	}

	/**
	 * @see org.apache.wicket.request.handler.render.WebPageRenderer#shouldRenderPageAndWriteResponse(org.apache.wicket.request.cycle.RequestCycle,
	 *      org.apache.wicket.request.Url, org.apache.wicket.request.Url)
	 */
	@Override
	protected boolean shouldRenderPageAndWriteResponse(RequestCycle cycle, Url currentUrl,
		Url targetUrl)
	{
		PortletRequest portletRequest = ThreadPortletContext.getPortletRequest();
		String lifecyclePhase = (String)portletRequest.getAttribute(PortletRequest.LIFECYCLE_PHASE);

		return lifecyclePhase.equals(PortletRequest.RENDER_PHASE);
	}
}
