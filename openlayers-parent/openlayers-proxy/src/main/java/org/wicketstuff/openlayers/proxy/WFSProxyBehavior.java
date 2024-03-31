/*
 *
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.openlayers.proxy;

import jakarta.servlet.http.HttpServletResponse;

import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.IRequestCycle;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mocleiri
 *
 *         When working with Web Feature Service (WFS) Requests a proxy needs to be used to get
 *         around cross site scripting restrictions that are present in browsers.
 *
 *         The Openlayers.org site lists several CGI approaches for proxying. In this approach we
 *         implement a proxy within a Wicket Ajax Behavior.
 *
 *         In order to make the behaviour work it needs to be added onto the OpenLayersMap; and
 *         emitted when the map is created in javascript.
 *
 *         Then the OpenLayers.ProxyHost=behaviour.getProxyUrl() method can be used.
 *
 *         See openlayers-examples for an example (e.g. MapWithWMSGetFeatureInfo.class)
 *
 *         Using this behaviour binds the lifecycle of map access to an active session which is
 *         useful in some cases but might be a problem in others.
 *
 *         The ProxyRequestTarget implementation could be used for example in a Servlet Filter to
 *         proxy without caring about the current session and/or the users authorization to view the
 *         map data.
 *
 */
public class WFSProxyBehavior extends AbstractAjaxBehavior
{

	/**
	 *
	 */
	private static final long serialVersionUID = -4710843334047637872L;

	private static final Logger log = LoggerFactory.getLogger(WFSProxyBehavior.class);

	/**
	 *
	 */
	public WFSProxyBehavior()
	{
	}

	static class ProxyResponseTarget implements IRequestHandler
	{

		/*
		 * (non-Javadoc)
		 *
		 * @see org.apache.wicket.IRequestTarget#detach(org.apache.wicket.RequestCycle)
		 */
		@Override
		public void detach(IRequestCycle requestCycle)
		{
			// TODO Auto-generated method stub

		}

		/*
		 * (non-Javadoc)
		 *
		 * @see org.apache.wicket.IRequestTarget#respond(org.apache.wicket.RequestCycle)
		 */
		@Override
		public void respond(IRequestCycle requestCycle)
		{

			WebRequest wr = (WebRequest)requestCycle.getRequest();


			WebResponse response = (WebResponse)requestCycle.getResponse();

// HttpServletRequest request = wr.getHttpServletRequest();


			String requestURL = wr.getRequestParameters().getParameterValue("url").toString();


			if (requestURL == null)
			{
				log.warn("requestURL could not be resolved.");
				response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED,
					"request URL could not be resolved.");
				return;
// response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
			}
// try {
//
// HttpClient client = new HttpClient();

			/*
			 * TODO: need to find a way in 1.5 to detect if the requset is a get or post request.
			 */
// if (request.getMethod().toLowerCase().equals("get")) {
//
// String decodedURL = URLDecoder.decode(requestURL, "UTF-8");
//
// StringBuffer getUrl = new StringBuffer(decodedURL);
//
//
// Set<String> parameters = request.getParameterMap().keySet();
//
// boolean first = true;
//
// for (String p : parameters) {
//
// if (p.equals("url"))
// continue; // skip the url parameter
//
// if (p.startsWith("wicket:"))
// continue; // skip the wicket parameters
//
// String value = request.getParameter(p);
//
// if (first) {
// // first parameter needs to applied with question mark.
// getUrl.append("?");
// first = false;
// }
// else {
// getUrl.append("&");
// }
//
// getUrl.append(p);
// getUrl.append("=");
// getUrl.append(URLEncoder.encode(value, "UTF-8"));
//
// }
//
// log.debug("Get = " + getUrl.toString());
//
// GetMethod getMethod = new GetMethod (getUrl.toString());
//
// int proxyResponseCode = client.executeMethod( getMethod);
//
// log.debug("redirected get, code = " + proxyResponseCode);
//
// if (proxyResponseCode != 200) {
// throw new AbortWithHttpStatusException(proxyResponseCode, false);
// }
//
// // Pass response headers back to the client
// Header[] headerArrayResponse = getMethod.getResponseHeaders();
// for(Header header : headerArrayResponse) {
// response.setHeader(header.getName(), header.getValue());
// }
//
// // Send the content to the client
// InputStream inputStreamProxyResponse = getMethod.getResponseBodyAsStream();
// BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStreamProxyResponse);
//
// response.write(bufferedInputStream);
//
//
//
// } else if (request.getMethod().toLowerCase().equals("post")) {
//
// // need to get the first line and get the url.
//
// String decodedURL = URLDecoder.decode(requestURL, "UTF-8");
//
// PostMethod pm = new PostMethod(decodedURL);
//
// Set<String> parameters = request.getParameterMap().keySet();
//
// for (String p : parameters) {
//
// String value = request.getParameter(p);
//
// pm.setParameter(p, value);
//
// }
//
// pm.setContentChunked(true);
//
// pm.setRequestEntity(new InputStreamRequestEntity (request.getInputStream()));
//
// int proxyResponseCode = client.executeMethod(pm);
//
// log.debug("redirected post, code = " + proxyResponseCode);
//
// if (proxyResponseCode != 200) {
// throw new AbortWithHttpStatusException(proxyResponseCode, false);
// }
//
// // Pass response headers back to the client
// Header[] headerArrayResponse = pm.getResponseHeaders();
// for(Header header : headerArrayResponse) {
// response.setHeader(header.getName(), header.getValue());
// }
//
// // Send the content to the client
// InputStream inputStreamProxyResponse = pm.getResponseBodyAsStream();
// BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStreamProxyResponse);
//
// response.write(bufferedInputStream);
//
//
// } else {
// // unsupported
// // fall through
// // response.getHttpServletResponse().setStatus(HttpServletResponse.SC_NOT_FOUND);
// throw new AbortWithHttpStatusException(HttpServletResponse.SC_NOT_FOUND, false);
// }
// } catch (Exception e) {
// log.error("getInputStream() failed", e);
// // fall through
// // response.getHttpServletResponse().setStatus(HttpServletResponse.SC_NOT_FOUND);
// throw new AbortWithHttpStatusException(HttpServletResponse.SC_NOT_FOUND, false);
// }

		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.wicket.behavior.IBehaviorListener#onRequest()
	 */
	@Override
	public void onRequest()
	{

// FIXME: howto setrequesttarget in 1.5
// RequestCycle rc = RequestCycle.get();
//
//
// wrc.setRequestTarget(new ProxyResponseTarget());
//
// wrc.setRedirect(false);
//

	}


	/**
	 *
	 * @return the proxy url that should be appended to OpenLayers.ProxyHost
	 */
	public String getProxyUrl()
	{

		return getCallbackUrl() + "&url=";
	}


}
