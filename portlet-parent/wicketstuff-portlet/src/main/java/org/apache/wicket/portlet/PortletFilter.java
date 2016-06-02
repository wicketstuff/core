/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.portlet;

import java.io.IOException;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.wicket.IPageRendererProvider;
import org.apache.wicket.IRequestCycleProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.request.UrlRenderer;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.cycle.RequestCycleContext;
import org.apache.wicket.request.handler.render.PageRenderer;
import org.apache.wicket.settings.IRequestCycleSettings.RenderStrategy;
import org.apache.wicket.util.crypt.Base64;

/**
 * This class subclasses the original WicketFilter to add the necessary porlet
 * functionality. It is responsible for the initialization of all portlet
 * specific settings of the WebApplication and wraps the portlet request and
 * portlet response objects by an http servlet request / response wrapper.
 * 
 * @author Peter Pastrnak
 * @author Konstantinos Karavitis
 */
public class PortletFilter extends WicketFilter {
	public static final String SHARED_RESOURCE_URL_PORTLET_WINDOW_ID_PREFIX = "/ps:";

	private FilterConfig filterConfig;
	
	@Override
	public void init(boolean isServlet, FilterConfig filterConfig) throws ServletException {
		super.init(isServlet, filterConfig);
		this.filterConfig = filterConfig;
		getApplication().getRequestCycleSettings().setRenderStrategy(RenderStrategy.REDIRECT_TO_RENDER);
		getApplication().getRequestCycleSettings().addResponseFilter(new PortletInvalidMarkupFilter());
		//fix for https://github.com/wicketstuff/core/issues/487
		getApplication().setMarkupIdGenerator(new PortletMarkupIdGenerator());
		getApplication().setRootRequestMapper(new PortletRequestMapper(getApplication()));
		//Application must use the portlet specific page renderer provider.
		getApplication().setPageRendererProvider(new IPageRendererProvider() {
			@Override
			public PageRenderer get(RenderPageRequestHandler handler) {
				return new PortletPageRenderer(handler);
			}
		});
		// fix for https://github.com/wicketstuff/core/issues/478 issue
		getApplication().setRequestCycleProvider(new IRequestCycleProvider() {
			@Override
			public RequestCycle get(RequestCycleContext context) {
				return new RequestCycle(context) {
					@Override
					protected UrlRenderer newUrlRenderer() {
						return new PortletUrlRenderer(getRequest());
					}
				};
			}
		});
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		String filterPath = getFilterPath(httpServletRequest);

		PortletRequest portletRequest = (PortletRequest) httpServletRequest.getAttribute("javax.portlet.request");
		if (portletRequest != null) {
			final PortletConfig portletConfig = (PortletConfig) httpServletRequest.getAttribute("javax.portlet.config");
			final ResponseState responseState = (ResponseState) httpServletRequest.getAttribute(WicketPortlet.RESPONSE_STATE_ATTR);

			if (portletConfig != null) {
				if (responseState == null) {
					filterChain.doFilter(httpServletRequest, httpServletResponse);
					return;
				}

				HttpSession proxiedSession = PortletHttpSessionWrapper.createProxy(
					httpServletRequest, portletRequest.getWindowID(), getApplication()
						.getSessionAttributePrefix(null, filterConfig.getFilterName()));
				
				httpServletRequest = new PortletServletRequestWrapper(filterConfig.getServletContext(), httpServletRequest, proxiedSession, filterPath);
				httpServletResponse = new PortletServletResponseWrapper(httpServletResponse, responseState);
			}
		}
		else {
			// look for windowId and serve it as a shared resource

			String pathInfo = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length() + filterPath.length());
			if (pathInfo.startsWith(SHARED_RESOURCE_URL_PORTLET_WINDOW_ID_PREFIX)) {

				int nextSeparator = pathInfo.indexOf('/', 1);
				if (nextSeparator > 0) {
					String windowId = new String(Base64.decodeBase64(pathInfo.substring(SHARED_RESOURCE_URL_PORTLET_WINDOW_ID_PREFIX.length(), nextSeparator)));
					
					HttpSession proxiedSession = PortletHttpSessionWrapper.createProxy(
						httpServletRequest,
						windowId,
						getApplication().getSessionAttributePrefix(null,
							filterConfig.getFilterName()));
					
					pathInfo = pathInfo.substring(nextSeparator);
					httpServletRequest = new PortletServletRequestWrapper(filterConfig.getServletContext(), httpServletRequest, proxiedSession, filterPath, pathInfo);
				}
			}
		}

		super.doFilter(httpServletRequest, httpServletResponse, filterChain);
	}
}
