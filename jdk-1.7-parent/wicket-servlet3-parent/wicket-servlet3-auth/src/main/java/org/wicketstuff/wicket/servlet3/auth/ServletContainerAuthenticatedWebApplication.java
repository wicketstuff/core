/*
 * Copyright 2014 WicketStuff.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.wicket.servlet3.auth;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.core.request.handler.IPageClassRequestHandler;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.wicket.mount.core.AutoMounter;
import org.wicketstuff.wicket.mount.core.annotation.MountPath;
import org.wicketstuff.wicket.servlet3.auth.annotation.SecureAutoMount;

/**
 * A web application subclass that does role-based authentication. This class
 * requires authentication to be configured using the Servlet Container.
 *
 * @author jsarman
 */
@SecureAutoMount
public abstract class ServletContainerAuthenticatedWebApplication extends AuthenticatedWebApplication
{

	private final static Logger LOG = LoggerFactory.getLogger(ServletContainerAuthenticatedWebApplication.class);

	/**
	 * @see org.apache.wicket.protocol.http.WebApplication#init()
	 */
	@Override
	protected void init()
	{
		super.init();

		// Add overrides for bookmarkable and nonbookmarkable page creations to allow servlet container
		// authorization mechanism to handle redirect to login page.
		final ContainerSecurityInterceptorListener listener = new ContainerSecurityInterceptorListener();
		getSecuritySettings().setUnauthorizedComponentInstantiationListener(listener);
		getRequestCycleListeners().add(listener);

		autoMountPages();
	}

	protected boolean autoMountPages()
	{
		final boolean mountOk = AutoMounter.mountAll(this);
		if (!mountOk)
		{
			LOG.warn("Unable to Automount pages.");
		}

		final Class<? extends WebPage> authPage = getSignInPageClass();
		if (authPage != null)
		{
			final MountPath mp = authPage.getAnnotation(MountPath.class);
			if (mp == null)
			{
				final SecureAutoMount annot = getClass().getAnnotation(SecureAutoMount.class);
				String root = annot.defaultRoot().trim();
				if (!root.isEmpty() && !root.endsWith("/"))
				{
					root = root + "/";
				}
				String mime = annot.defaultMimeExtension().trim();
				if (!mime.isEmpty() && !mime.startsWith("."))
				{
					mime = "." + mime;
				}
				final String loginPagePath = root + "login" + mime;
				LOG.info("Mounting SignInPageClass: {} to path {}", authPage, loginPagePath);
				mountPage(loginPagePath, authPage);
			}
		}
		return mountOk;
	}

	@Override
	protected final Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass()
	{
		return getContainerManagedWebSessionClass();
	}

	protected abstract Class<? extends ServletContainerAuthenticatedWebSession> getContainerManagedWebSessionClass();

	/**
	 * ContainerSecurityInterceptorListener forces all pages requiring
	 * authentication to use servlet containers authentication strategy. In
	 * cases where a bookmarkable page is requested the class redirects to itself
	 * forcing an instantiation. When a page requiring authentication is
	 * instantiated, the page is unauthorized for Rendering. If the mount path
	 * is configured such that the servlet container will intercept the request
	 * then the container will apply the auth required. If the pages url isn't
	 * matched by a security-constraint then wicket will respond with
	 * unauthorized access.
	 */
	private class ContainerSecurityInterceptorListener extends AbstractRequestCycleListener
			implements IUnauthorizedComponentInstantiationListener
	{

		@Override
		public void onRequestHandlerScheduled(RequestCycle cycle, IRequestHandler handler)
		{
			if (handler instanceof IPageClassRequestHandler)
			{
				final IPageClassRequestHandler classHandler = (IPageClassRequestHandler) handler;
				final Class<Page> pgClass = (Class<Page>) classHandler.getPageClass();
				final boolean authorized = getSecuritySettings().getAuthorizationStrategy().isInstantiationAuthorized(pgClass);
				if (!authorized)
				{
					if (!ServletContainerAuthenticatedWebSession.get().isSignedIn())
					{
						// A secure Page is scheduled that is not authenticated.
						// Setting the RestartResponse to the class forces a request with a URL
						// that the servlet container intercepts and redirects to the login page.
						// If a wicket login page is used then continueToOriginalDestination will
						// redirect to the page.  If a non wicket page is used then the servlet container
						// will redirect to the page using its mechanism.
						// If the page is not mounted to a path that matches a security-constraint in web.xml
						// then unauthorized page will result.
						final PageParameters pp = classHandler.getPageParameters();
						throw new RestartResponseAtInterceptPageException(pgClass, pp);
					}
				}
			}
		}

		@Override
		public void onUnauthorizedInstantiation(Component component)
		{
			if (!AbstractAuthenticatedWebSession.get().isSignedIn())
			{
				// If a component is not authenticated unauthorize it for Rendering.
				// If the page is properly mounted such that the servlet container will intercept
				// the request and redirect to login page then authentication will occur normally.
				// If page is not mounted properly ie mount path does not match a security-constraint
				// then unauthorized page will be returned.
				MetaDataRoleAuthorizationStrategy.unauthorizeAll(component, Component.RENDER);
			} else
			{
				//Use Default implementation if authenticated.
				ServletContainerAuthenticatedWebApplication.this.onUnauthorizedInstantiation(component);
			}
		}
	}
}
