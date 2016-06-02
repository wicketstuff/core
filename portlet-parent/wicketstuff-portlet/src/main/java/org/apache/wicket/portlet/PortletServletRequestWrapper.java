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

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * Wraps servlet request object with Portlet specific functionality by
 * overriding the {@link HttpServletRequestWrapper} retrieval of the context
 * path, path info, request URI etc... to return the portal specific
 * translations.
 * 
 * FIXME javadoc
 * 
 * @author Ate Douma
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 * @author Peter Pastrnak
 * @author Ronny Pscheidl
 */
public class PortletServletRequestWrapper extends HttpServletRequestWrapper {
	/**
	 * Converts from a filterPath (path with a trailing slash), to a servletPath
	 * (path with a leading slash).
	 * 
	 * @param filterPath
	 * @return the filterPath prefixed with a leading slash and with the
	 *         trailing slash removed
	 */
	private static String makeServletPath(final String filterPath) {
		return "/" + filterPath.substring(0, filterPath.length() - 1);
	}

	/**
	 * Context path.
	 */
	private String contextPath;

	/**
	 * Servlet path.
	 */
	private final String servletPath;

	/**
	 * Path info - the url relative to the context and filter path.
	 */
	private String pathInfo;

	/**
	 * Request URI.
	 */
	private String requestURI;

	/**
	 * Query string.
	 */
	private String queryString;

	/**
	 * HTTP session.
	 */
	private HttpSession session;

	/**
	 * FIXME javadoc
	 * 
	 * <p>
	 * Public constructor which internally builds the path info from request
	 * URI, instead of deriving it.
	 * 
	 * @param context
	 * @param request
	 * @param proxiedSession
	 * @param filterPath
	 */
	public PortletServletRequestWrapper(final ServletContext context, final HttpServletRequest request, final HttpSession proxiedSession, final String filterPath) {
		this(context, proxiedSession, request, filterPath);

		// Liferay sometimes gives an incorrect requestURI
		final int pathInfoBegin = contextPath.length() + filterPath.length();
		final String pathInfo = pathInfoBegin >= requestURI.length() ? null : requestURI.substring(pathInfoBegin);
		this.pathInfo = pathInfo == null || pathInfo.length() < 2 ? null : pathInfo;
	}

	/**
	 * FIXME javadoc
	 * 
	 * <p>
	 * Public constructor called when not running in a portlet environment,
	 * which is passed in the path info instead of deriving it. It overrides the
	 * generated request URI from the internal constructor.
	 * 
	 * @param context
	 * @param request
	 * @param proxiedSession
	 * @param filterPath
	 *            ???
	 * @param pathInfo
	 *            ???
	 */
	public PortletServletRequestWrapper(final ServletContext context, final HttpServletRequest request, final HttpSession proxiedSession, final String filterPath, final String pathInfo) {
		this(context, proxiedSession, request, filterPath);

		this.pathInfo = pathInfo;
		// override requestURI which is setup in the protected constructor
		requestURI = contextPath + servletPath + (pathInfo != null ? pathInfo : "");
	}

	/**
	 * Package private constructor which is called from either of the two public
	 * constructors - sets up the various portlet specific versions of the
	 * context path, servlet path, request URI etc...
	 * 
	 * @param context
	 * @param proxiedSession
	 * @param request
	 * @param filterPath
	 */
	@SuppressWarnings("unchecked")
	protected PortletServletRequestWrapper(final ServletContext context, final HttpSession proxiedSession, final HttpServletRequest request, final String filterPath) {
		super(request);

		session = proxiedSession;
		if (proxiedSession == null) {
			session = request.getSession(false);
		}

		servletPath = makeServletPath(filterPath);

		// retrieve the correct contextPath, requestURI and queryString
		// if request is an include
		if ((contextPath = (String) request.getAttribute("javax.servlet.include.context_path")) != null) {
			requestURI = (String) request.getAttribute("javax.servlet.include.request_uri");
			String wicketQueryString = (String) request.getAttribute("javax.servlet.include.query_string");
			queryString = mergeQueryString(request.getParameterMap(), request.getQueryString(), wicketQueryString);
		}
		// else if request is a forward
		else if ((contextPath = (String) request.getAttribute("javax.servlet.forward.context_path")) != null) {
			requestURI = (String) request.getAttribute("javax.servlet.forward.request_uri");
			String wicketQueryString = (String) request.getAttribute("javax.servlet.forward.query_string");
			queryString = mergeQueryString(request.getParameterMap(), request.getQueryString(), wicketQueryString);
		}
		// else it is a normal request
		else {
			contextPath = request.getContextPath();
			requestURI = request.getRequestURI();
			queryString = request.getQueryString();
		}
	}
	
	private String mergeQueryString(Map<String, String[]> requestParameterMap, String requestQueryString, String wicketQueryString) {
		// Many Javascript based components append parameters directly to the URL, so they are not part of the '_wu' or 'resourceId' parameter.
		// Wicket can access these parameters, but they are not present in the querystring, so Wicket identifies them as POST parameters.
		// As a workaround, we add all parameters from the original request to the query string. 
		Map<String, String[]> parameterMap = Utils.parseQueryString(requestQueryString);
		parameterMap.putAll(requestParameterMap);
		parameterMap.putAll(Utils.parseQueryString(wicketQueryString));
		return Utils.buildQueryString(parameterMap);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getAttribute(final String name) {
		// TODO: check if these can possibly be set/handled
		// nullifying these for now to prevent Wicket
		// ServletWebRequest.getRelativePathPrefixToWicketHandler() going the
		// wrong route
		if ("javax.servlet.error.request_uri".equals(name) || "javax.servlet.forward.servlet_path".equals(name))
			return null;
		return super.getAttribute(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getContextPath() {
		return contextPath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPathInfo() {
		return pathInfo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getQueryString() {
		return queryString;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getRequestURI() {
		return requestURI;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getServletPath() {
		return servletPath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HttpSession getSession() {
		return getSession(true);
	}

	@Override
	public HttpSession getSession(final boolean create) {
		return session != null ? session : super.getSession(create);
	}

	@Override
	public String getHeader(String name) {
		String header = super.getHeader(name);
		if (header == null) {
			// GateIn: SimpleMultiValuedPropertyMap does not
			// load values with equalsIgnoreCase like MimeHeaders do
			// in Request
			@SuppressWarnings("unchecked")
			Enumeration<String> headerNames = this.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				if (headerName.equalsIgnoreCase(name)) {
					header = super.getHeader(headerName);
					break;
				}
			}
		}
		return header;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Enumeration getHeaders(String name) {		
		Enumeration headers = super.getHeaders(name);
		if (!headers.hasMoreElements()) {
			// GateIn: SimpleMultiValuedPropertyMap does not 
			// load values with equalsIgnoreCase like MimeHeaders do 
			// in Request
			Enumeration<String> headerNames = this.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				if (headerName.equalsIgnoreCase(name)) {
					 headers = super.getHeaders(headerName);
					 break;
				}
			}
		}
		return headers;
	}

	/**
	 * @see javax.servlet.ServletRequestWrapper#setCharacterEncoding(java.lang.String)
	 */
	@Override
	public void setCharacterEncoding(final String enc) throws UnsupportedEncodingException {
		try {
			super.setCharacterEncoding(enc);
		}
		catch (final UnsupportedEncodingException uex) {
			// TODO
			// SUN OpenPortal Portlet Container 2.0_01 BUG which only allows
			// setting an encoding as
			// provided by underlying request (then: what's the use?)
			// and throws UnsupportedEncodingException even when that one ==
			// null :(
			// ... so, ignoring for now
		}
	}
}
