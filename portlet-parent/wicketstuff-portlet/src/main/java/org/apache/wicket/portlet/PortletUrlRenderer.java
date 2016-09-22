package org.apache.wicket.portlet;

import javax.portlet.PortletRequest;

import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.UrlRenderer;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.PrependingStringBuffer;
import org.apache.wicket.util.string.Strings;

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
	
	
	private Request request;
	
	/**
	 * @param request
	 * 
	 * 
	 * 
	 */
	public PortletUrlRenderer(Request request)
	{
		super(request);
		this.request = request;
		
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
	
	/**
	 * Renders the Url
	 * 
	 * @param url
	 * @return Url rendered as string
	 */
	public String renderUrl(final Url url)
	{
		String renderedUrl = super.renderUrl(url);
		
		Url absoluteUrl = Url.parse(renderedUrl);
		Url clientUrl = request.getClientUrl();
		if (!shouldRedirectToExternalUrl(absoluteUrl, clientUrl)) {
			if (absoluteUrl.getProtocol() != null) {
				renderedUrl = renderedUrl.replace(absoluteUrl.getProtocol() + "://", "/");
			}
			if (absoluteUrl.getHost() != null) {
				renderedUrl = renderedUrl.replace("/" + absoluteUrl.getHost(), "/");
			}
			if (absoluteUrl.getPort() != null) {
				renderedUrl = renderedUrl.replace("/:" + absoluteUrl.getPort(), "/");
			}
			renderedUrl = renderedUrl.replace("//", "/");
		}
		
		return renderedUrl;
	}
	
	private boolean shouldRedirectToExternalUrl(Url url, Url clientUrl) 
	{
		if (!Strings.isEmpty(url.getProtocol()) && !url.getProtocol().equals(clientUrl.getProtocol())) 
		{
			return true;
		}
		if (!Strings.isEmpty(url.getHost()) && !url.getHost().equals(clientUrl.getHost())) 
		{
			return true;
		}
		if ((url.getPort() != null) && !url.getPort().equals(clientUrl.getPort())) 
		{
			return true;
		}
		
		return false;
	}
}