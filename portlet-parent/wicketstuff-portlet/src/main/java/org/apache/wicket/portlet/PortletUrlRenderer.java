package org.apache.wicket.portlet;

import javax.portlet.PortletRequest;

import org.apache.wicket.request.Request;
import org.apache.wicket.request.UrlRenderer;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.PrependingStringBuffer;

/**
 * <h1>{@link UrlRenderer} for Portlet Applications.</h1>
 * <p>
 * It corrects the base Url. All the generated URLs will be relative to this Url.
 * </p>
 * <p>
 * The base Url depends on the lifecycle phase of the current portlet request.
 * </p>
 * <ul>
 * <li>During the RENDER or the ACTION phase the base url must be set with the request url which is
 * relative to the wicket filter path {@link Request#getUrl()}.  
 * </li>
 * <li>During the RESOURCE phase the base url must be set with the client url
 * {@link Request#getClientUrl()}. Every Ajax request to the portal is a RESOURCE request and the
 * comments of the {@link Request#getClientUrl()} method full explain the reasons that this url must
 * be the base url during this phase.</li> </u>
 * 
 * <p>
 * It overrides the {@link #renderContextRelativeUrl(String)} method in order to give the capability
 * to wicket portlets to serve static and context-relative resources.
 * </p>
 * 
 * @author Konstantinos Karavitis
 * 
 */
public class PortletUrlRenderer extends UrlRenderer
{
	/**
	 * @param request
	 * 
	 * 
	 * 
	 */
	public PortletUrlRenderer(Request request)
	{
		super(request);
		
		PortletRequest portletRequest = ThreadPortletContext.getPortletRequest();
		String lifecyclePhase = (String) portletRequest.getAttribute(PortletRequest.LIFECYCLE_PHASE);
		if (!PortletRequest.RESOURCE_PHASE.equals(lifecyclePhase)) {
			//request is not an ajax request, so put as base url the relative to Wicket filter path url.
			setBaseUrl(request.getUrl());
		}
	}
	
	@Override
	public String renderContextRelativeUrl(String url)
	{
		Args.notNull(url, "url");

		if (url.startsWith("/"))
		{
			url = url.substring(1);
		}

		PrependingStringBuffer buffer = new PrependingStringBuffer(url);
		buffer.prepend("/").prepend(ThreadPortletContext.getPortletRequest().getContextPath());

		return buffer.toString();
	}
}