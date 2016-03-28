package org.apache.wicket.portlet;

import javax.portlet.PortletRequest;

import org.apache.wicket.request.Request;
import org.apache.wicket.request.UrlRenderer;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.PrependingStringBuffer;

/**
 * {@link UrlRenderer} for Portlet Applications.
 * 
 * It overrides the {@link #renderContextRelativeUrl(String)} method in order to give the capability
 * to wicket portlets to serve static and context-relative resources.
 * 
 * @author Konstantinos Karavitis
 * 
 */
public class PortletUrlRenderer extends UrlRenderer
{

	/**
	 * @param request
	 */
	public PortletUrlRenderer(Request request)
	{
		super(request);
	}

	/**
	 * @see {@link UrlRenderer#renderContextRelativeUrl(String)}
	 **/
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
