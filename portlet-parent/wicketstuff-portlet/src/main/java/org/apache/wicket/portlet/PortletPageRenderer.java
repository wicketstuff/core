package org.apache.wicket.portlet;

import javax.portlet.PortletRequest;

import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler.RedirectPolicy;
import org.apache.wicket.protocol.http.BufferedWebResponse;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.render.PageRenderer;
import org.apache.wicket.request.handler.render.WebPageRenderer;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;

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
	
	/*
	 * TODO: simplify the code below. See WICKET-3347
	 */
	@Override
	public void respond(RequestCycle requestCycle)
	{
		Url currentUrl = requestCycle.getUrlRenderer().getBaseUrl();
		Url targetUrl = requestCycle.mapUrlFor(getRenderPageRequestHandler());

		//
		// the code below is little hairy but we have to handle 3 redirect policies,
		// 3 rendering strategies and two kind of requests (ajax and normal)
		//

		boolean isAjax = isAjax(requestCycle);

		boolean shouldPreserveClientUrl = ((WebRequest)requestCycle.getRequest()).shouldPreserveClientUrl();

		if (((String) ThreadPortletContext.getPortletRequest().getAttribute(
				PortletRequest.LIFECYCLE_PHASE))
				.equals(PortletRequest.RENDER_PHASE))
		{
			// if the policy is never to redirect
			// or one pass render mode is on
			// or the targetUrl matches current url and the page is not stateless
			// or the targetUrl matches current url, page is stateless but it's redirect-to-render
			// or the request determines that the current url should be preserved
			// just render the page
			BufferedWebResponse response = renderPage(currentUrl, requestCycle);
			if (response != null)
			{
				response.writeTo((WebResponse)requestCycle.getResponse());
			}
		}
		else if (getRedirectPolicy() == RedirectPolicy.ALWAYS_REDIRECT //
			||
			isRedirectToRender() //
			|| (isAjax && targetUrl.equals(currentUrl)))
		{
			// if target URL is different
			// and render policy is always-redirect or it's redirect-to-render
			redirectTo(targetUrl, requestCycle);
		}
		else if (!targetUrl.equals(currentUrl) //
			&&
			(getPageProvider().isNewPageInstance() || (isSessionTemporary() && getPage().isPageStateless())))
		{
			// if target URL is different and session is temporary and page is stateless
			// this is special case when page is stateless but there is no session so we can't
			// render it to buffer

			// alternatively if URLs are different and we have a page class and not an instance we
			// can redirect to the url which will instantiate the instance of us

			// note: if we had session here we would render the page to buffer and then redirect to
			// URL generated *after* page has been rendered (the statelessness may change during
			// render). this would save one redirect because now we have to render to URL generated
			// *before* page is rendered, render the page, get URL after render and if the URL is
			// different (meaning page is not stateless), save the buffer and redirect again (which
			// is pretty much what the next step does)
			redirectTo(targetUrl, requestCycle);
		}
		else
		{
			// force creation of possible stateful page to get the final target url
			getPage();

			Url beforeRenderUrl = requestCycle.mapUrlFor(getRenderPageRequestHandler());

			// redirect to buffer
			BufferedWebResponse response = renderPage(beforeRenderUrl, requestCycle);

			if (response == null)
			{
				return;
			}

			// the url might have changed after page has been rendered (e.g. the
			// stateless flag might have changed because stateful components
			// were added)
			final Url afterRenderUrl = requestCycle.mapUrlFor(getRenderPageRequestHandler());

			if (beforeRenderUrl.getSegments().equals(afterRenderUrl.getSegments()) == false)
			{
				// the amount of segments is different - generated relative URLs
				// will not work, we need to rerender the page. This can happen
				// with IRequestHandlers that produce different URLs with
				// different amount of segments for stateless and stateful pages
				response = renderPage(afterRenderUrl, requestCycle);
			}

			if (currentUrl.equals(afterRenderUrl))
			{
				// no need to redirect when both urls are exactly the same
				response.writeTo((WebResponse)requestCycle.getResponse());
			}
			// if page is still stateless after render
			else if (getPage().isPageStateless() && !enableRedirectForStatelessPage())
			{
				// we don't want the redirect to happen for stateless page
				// example:
				// when a normal mounted stateful page is hit at /mount/point
				// wicket renders the page to buffer and redirects to /mount/point?12
				// but for stateless page the redirect is not necessary
				// also for listener interface on stateful page we want to redirect
				// after the listener is invoked, but on stateless page the user
				// must ask for redirect explicitly
				response.writeTo((WebResponse)requestCycle.getResponse());
			}
			else
			{
				storeBufferedResponse(afterRenderUrl, response);

				redirectTo(afterRenderUrl, requestCycle);
			}
		}
	}
	
	private boolean isAjax(RequestCycle requestCycle)
	{
		boolean isAjax = false;

		Request request = requestCycle.getRequest();
		if (request instanceof WebRequest)
		{
			WebRequest webRequest = (WebRequest)request;
			isAjax = webRequest.isAjax();
		}

		return isAjax;
	}
}
