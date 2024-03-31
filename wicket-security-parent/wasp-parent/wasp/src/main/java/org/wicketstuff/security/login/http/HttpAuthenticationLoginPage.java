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
package org.wicketstuff.security.login.http;

import jakarta.servlet.http.HttpServletResponse;

import java.util.Base64;

import org.apache.wicket.Application;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.pages.AccessDeniedPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.security.WaspSession;
import org.wicketstuff.security.authentication.LoginException;
import org.wicketstuff.security.strategies.WaspAuthorizationStrategy;

/**
 * Login Page that uses httpauthentication to login. Currently it only supports Basic Http
 * authentication as defined in RFC 2616 section 14.47 HTTP 1.1. But the way it is setup it should
 * be able to support addition protocols like RFC 2617. Thanks go to Jesse Barnum and Johan
 * Compagner.
 *
 * @author marrink
 * @see <a href="http://tools.ietf.org/html/rfc2616#section-14.47">rfc2616</a>
 */
public abstract class HttpAuthenticationLoginPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(HttpAuthenticationLoginPage.class);

	private boolean doAuthentication = false;

	/**
	 * Basic constructor.
	 *
	 * @see WebPage#WebPage()
	 */
	public HttpAuthenticationLoginPage()
	{
	}

	/**
	 * Constructor.
	 *
	 * @param model
	 * @see WebPage#WebPage(IModel)
	 */
	protected HttpAuthenticationLoginPage(IModel<?> model)
	{
		super(model);
	}

	/**
	 * Constructor.
	 *
	 * @param parameters
	 * @see WebPage#WebPage(PageParameters)
	 */
	protected HttpAuthenticationLoginPage(PageParameters parameters)
	{
		super(parameters);
	}

	@Override
	protected void configureResponse(final WebResponse response)
	{
		super.configureResponse(response);
		if (doAuthentication)
		{
			WebRequest request = (WebRequest)RequestCycle.get().getRequest();
			String auth = request.getHeader("Authorization");
			if (Strings.isEmpty(auth))
			{
				requestAuthentication(request, response);
			}
			else
			{
				int index = auth.indexOf(' ');
				if (index < 1)
				{
					requestAuthentication(request, response);
				}
				String type = auth.substring(0, index);
				try
				{
					handleAuthentication(request, response, type, auth.substring(index + 1));
				}
				catch (LoginException e)
				{
					log.error(type + " Http authentication failed", e);
					error(e);
					requestAuthentication(request, response);
				}
			}
		}
	}

	/**
	 * Sets a flag to handle authentication headers and sets response to request authentication if
	 * required. This method needs to be called manually. I recommend 1 of these 2 locations to call
	 * this method from.<br/>
	 * <ol>
	 * <li>from within the constructor</li>
	 * <li>from within a {@link Link#onClick()} or {@link Form#onSubmit()}</li>
	 * </ol>
	 * The benefit of the second method is that you can render the login page at least once before
	 * activating the http authentication by the click of a button. Using the first option, this
	 * page is not shown by the browser.
	 */
	protected final void doAuthentication()
	{
		doAuthentication = true;
	}

	/**
	 * Sets the statuscode of the response to 401. setting headers is delegated to
	 * {@link #addBasicHeaders(WebRequest, WebResponse)}. Subclasses should override this method to
	 * set their custom headers.
	 *
	 * @param request
	 * @param response
	 */
	protected void requestAuthentication(WebRequest request, WebResponse response)
	{
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		addBasicHeaders(request, response);
		// add more "WWW-Authenticate" headers as required
	}

	/**
	 * Adds a "WWW-Authenticate" header for basic authentication to the response.
	 *
	 * @param request
	 * @param response
	 */
	protected void addBasicHeaders(WebRequest request, WebResponse response)
	{
		response.setHeader("WWW-Authenticate", "Basic realm=\"" + getRealm(request, response) +
			"\"");
	}

	/**
	 * The authentication realm. The realm is required by the authentication headers and will be
	 * shown by the browser.
	 *
	 * @param request
	 * @param response
	 *
	 * @return the realm
	 */
	public abstract String getRealm(WebRequest request, WebResponse response);

	/**
	 * Delegates authentication. Subclasses should first try there custom authentication scheme
	 * before letting super handle the call. Subclasses should either return a boolean value (see
	 * {@link #handleBasicAuthentication(WebRequest, WebResponse, String, String)} ) if processing
	 * should continue or throw an exception.
	 *
	 * @param request
	 * @param response
	 * @param scheme
	 *            the authentication scheme like "Basic" or "Digest"
	 * @param param
	 *            the parameters after the scheme from the header
	 * @throws LoginException
	 *             if the user could not be logged in.
	 * @throws RestartResponseAtInterceptPageException
	 *             to an {@link AccessDeniedPage} if the scheme is not supported
	 */
	protected void handleAuthentication(WebRequest request, WebResponse response, String scheme,
		String param) throws LoginException
	{
		if (!handleBasicAuthentication(request, response, scheme, param))
		{
			return;
		}
		log.error("Unsupported Http authentication type: " + scheme);
		throw new RestartResponseAtInterceptPageException(Application.get()
			.getApplicationSettings()
			.getAccessDeniedPage());
	}

	/**
	 * Handles authentication for the "Basic" scheme. If the scheme is not the basic scheme true is
	 * returned so another implementation may try it. In general authentication attempts by the next
	 * scheme should only proceed if the scheme was of the wrong type. False will generally be
	 * returned when a) the user has been authenticated or b) the scheme is correct but another
	 * problem arises, like missing additional headers.
	 *
	 * @param request
	 * @param response
	 * @param scheme
	 * @param param
	 *            username:password in base 64
	 * @return true if authentication by another scheme should be attempted, false if authentication
	 *         by another scheme should not be attempted.
	 * @throws LoginException
	 *             If the supplied credentials do not grant enough credits for the requested
	 *             resource
	 * @throws RestartResponseAtInterceptPageException
	 *             to the home page if the login was successfull but when there is no page to
	 *             continue to.
	 */
	protected boolean handleBasicAuthentication(WebRequest request, WebResponse response,
		String scheme, String param) throws LoginException
	{
		if (!"Basic".equalsIgnoreCase(scheme))
		{
			return true;
		}
		if (param == null)
		{
			log.error("Username, password not supplied");
			return false;
		}
		byte[] decoded = Base64.getDecoder().decode(param.getBytes());
		String[] split = new String(decoded).split(":");
		if (split == null || split.length != 2)
		{
			throw new LoginException("Could not decrypt username / password");
		}
		Object loginContext = getBasicLoginContext(split[0], split[1]);
		Session session = Session.get();
		if (session instanceof WaspSession)
		{
			if (!isAuthenticated())
			{
				((WaspSession)session).login(loginContext);
			}


			continueToOriginalDestination();
			// or
			throw new RestartResponseAtInterceptPageException(Application.get().getHomePage());
		}
		else
		{
			log.error("Unable to find WaspSession");
		}
		return false;
	}

	/**
	 * Check if already someone is authenticated to prevent duplicate logins. By default this checks
	 * if the home page is authenticated.
	 *
	 * @return true if the user is already authenticated, false otherwise
	 */
	protected boolean isAuthenticated()
	{
		WaspAuthorizationStrategy strategy = (WaspAuthorizationStrategy)Session.get()
			.getAuthorizationStrategy();
		return strategy.isClassAuthenticated(Application.get().getHomePage());
	}

	/**
	 * Delivers a context suitable for logging in with the specified username and password. Please
	 * refer to your specific wasp implementation for a suitable context.
	 *
	 * @param username
	 * @param password
	 * @return the login context or null if none could be created
	 */
	protected abstract Object getBasicLoginContext(String username, String password);
}
