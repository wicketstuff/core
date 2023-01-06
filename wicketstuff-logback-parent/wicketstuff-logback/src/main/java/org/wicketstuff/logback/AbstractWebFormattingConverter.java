/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * <p>
 * A {@link ClassicConverter} implementation that can produce web information based on a
 * {@link HttpServletRequest}. This class is abstract, subclasses must implement
 * {@link #getRequest()}. If no request is available it produces an empty string. This class does
 * not depend on wicket so in theory subclasses can be used in non-wicket web applications too.
 * </p>
 * <p>
 * Message format:
 * <code>$method $requestUrl?$queryString $sessionId $remoteUser $remoteAddr:$remotePort $localAddr:$localPort $x-forwarded-for $user-agent</code>
 * </p>
 * 
 * @author akiraly
 */
public abstract class AbstractWebFormattingConverter extends ClassicConverter
{
	/**
	 * Used as request attribute key to cache web information String for a request.
	 */
	public static final String RA_WEB_INFO = AbstractWebFormattingConverter.class.getName() +
		".RA_WEB_INFO";

	@Override
	public String convert(ILoggingEvent event)
	{
		String webString = getWebString();
		return webString == null ? "" : webString;
	}

	/**
	 * @return created web string or null if no request was available
	 */
	protected String getWebString()
	{
		HttpServletRequest request = getRequest();

		if (request == null)
			return null;

		Object raAttribute = request.getAttribute(RA_WEB_INFO);
		if (raAttribute != null)
			return raAttribute.toString();

		HttpSession session = request.getSession(false);

		String webString = request.getMethod() + " " + request.getRequestURL() + "?" +
			request.getQueryString() + " " + (session != null ? session.getId() : null) + " " +
			request.getRemoteUser() + " " + request.getRemoteAddr() + ":" +
			request.getRemotePort() + " " + request.getLocalAddr() + ":" + request.getLocalPort() +
			" " + request.getHeader("X-Forwarded-For") + " " + request.getHeader("User-Agent");

		request.setAttribute(RA_WEB_INFO, webString);

		return webString;
	}

	/**
	 * @return current request object or null if none available
	 */
	protected abstract HttpServletRequest getRequest();
}
