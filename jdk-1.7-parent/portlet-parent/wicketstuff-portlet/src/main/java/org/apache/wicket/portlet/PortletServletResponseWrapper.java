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
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * General class for all Portal responses, wrapping Servlet responses with
 * Portal specific functionality. Records various settings in the
 * {@link ResponseState}, like the redirect location, which as when running as a
 * portlet wicket uses the {@link org.apache.wicket.settings.RequestCycleSettings.RenderStrategy#REDIRECT_TO_RENDER}
 * strategy, is used in the render phase to know what Wicket url to request
 * Wicket Core to render.
 * 
 * @see WicketPortlet#processActionResponseState
 * @see WicketPortlet#processMimeResponseRequest
 * @see org.apache.wicket.settings.RequestCycleSettings.RenderStrategy#REDIRECT_TO_RENDER
 * 
 * @author Ate Douma
 * @author <a href="http://sebthom.de/">Sebastian Thomschke</a>
 */
public class PortletServletResponseWrapper extends HttpServletResponseWrapper {
	private final ResponseState responseState;

	public PortletServletResponseWrapper(final HttpServletResponse response, final ResponseState responseState) {
		super(response);
		this.responseState = responseState;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCookie(final Cookie cookie) {
		responseState.addCookie(cookie);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addDateHeader(final String name, final long date) {
		responseState.addDateHeader(name, date);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addHeader(final String name, final String value) {
		responseState.addHeader(name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addIntHeader(final String name, final int value) {
		responseState.addIntHeader(name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsHeader(final String name) {
		return responseState.containsHeader(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String encodeRedirectUrl(final String url) {
		return encodeRedirectURL(url);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String encodeRedirectURL(final String url) {
		return url;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String encodeUrl(final String url) {
		return encodeURL(url);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String encodeURL(final String url) {
		if (url.indexOf("://") == -1 && !url.startsWith("/"))
			// Also note: Tomcat does *not* encode the url when called from
			// within a context other
			// than the originating request context (e.g. during cross-context
			// calls) anyway...
			return url;
		return super.encodeURL(url);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void flushBuffer() throws IOException {
		responseState.flushBuffer();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getBufferSize() {
		return responseState.getBufferSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCharacterEncoding() {
		return responseState.getCharacterEncoding();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getContentType() {
		return responseState.getContentType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Locale getLocale() {
		return responseState.getLocale();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		final ServletOutputStream os = responseState.getOutputStream();
		return os != null ? os : super.getOutputStream();
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public PrintWriter getWriter() throws IOException {
		final PrintWriter pw = responseState.getWriter();
		return pw != null ? pw : super.getWriter();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCommitted() {
		return responseState.isCommitted();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		responseState.reset();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void resetBuffer() {
		responseState.resetBuffer();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendError(final int errorCode) throws IOException {
		responseState.sendError(errorCode);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendError(final int errorCode, final String errorMessage) throws IOException {
		responseState.sendError(errorCode, errorMessage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendRedirect(final String redirectLocation) throws IOException {
		responseState.sendRedirect(redirectLocation);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setBufferSize(final int size) {
		responseState.setBufferSize(size);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCharacterEncoding(final String charset) {
		responseState.setCharacterEncoding(charset);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setContentLength(final int len) {
		responseState.setContentLength(len);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setContentType(final String type) {
		responseState.setContentType(type);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDateHeader(final String name, final long date) {
		responseState.setDateHeader(name, date);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setHeader(final String name, final String value) {
		responseState.setHeader(name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setIntHeader(final String name, final int value) {
		responseState.setIntHeader(name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setLocale(final Locale locale) {
		responseState.setLocale(locale);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStatus(final int statusCode) {
		responseState.setStatus(statusCode);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setStatus(final int statusCode, final String message) {
		responseState.setStatus(statusCode, message);
	}
}
