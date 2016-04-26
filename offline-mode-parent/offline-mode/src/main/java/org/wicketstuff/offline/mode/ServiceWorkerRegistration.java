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
package org.wicketstuff.offline.mode;

import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.resource.JavaScriptPackageResource;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * The registration is going to initialize the service worker on client side.
 * 
 * @author Tobias Soloschenko
 *
 */
public class ServiceWorkerRegistration extends JavaScriptResourceReference
{
	private static final long serialVersionUID = -1356650275121621624L;

	private static final ServiceWorkerRegistration self = new ServiceWorkerRegistration();

	/**
	 * Creates a new service worker registration
	 */
	public ServiceWorkerRegistration()
	{
		super(ServiceWorkerRegistration.class,
			ServiceWorkerRegistration.class.getSimpleName() + ".js");
	}

	/**
	 * Gets the current service worker registration instance
	 * 
	 * @return the current service worker registration instance
	 */
	public static ServiceWorkerRegistration getInstance()
	{
		return self;
	}

	/**
	 * Override getResource() because it has be ensured that there is no cache applied to the
	 * resource
	 */
	@Override
	public JavaScriptPackageResource getResource()
	{
		final JavaScriptPackageResource resource = new JavaScriptPackageResource(getScope(),
			getName(), getLocale(), getStyle(), getVariation())
		{

			private static final long serialVersionUID = 6651316937259448962L;

			@Override
			protected void setResponseHeaders(ResourceResponse resourceResponse,
				Attributes attributes)
			{
				super.setResponseHeaders(resourceResponse, attributes);
				((WebResponse)attributes.getResponse()).disableCaching();
			}

		};
		removeCompressFlagIfUnnecessary(resource);
		return resource;
	}
}
