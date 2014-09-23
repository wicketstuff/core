/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wicketstuff.rest;

import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.wicketstuff.rest.contenthandling.mimetypes.RestMimeTypes;
import org.wicketstuff.rest.contenthandling.webserialdeserial.JsonTestWebSerialDeserial;
import org.wicketstuff.rest.contenthandling.webserialdeserial.MultiFormatSerialDeserial;
import org.wicketstuff.rest.contenthandling.webserialdeserial.XmlTestWebSerialDeserial;
import org.wicketstuff.rest.resource.MultiFormatRestResource;
import org.wicketstuff.rest.resource.RegExpRestResource;
import org.wicketstuff.rest.resource.RestResourceFullAnnotated;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 */
public class WicketApplication extends WebApplication implements
	IRoleCheckingStrategy
{
    private final Roles roles;

    public WicketApplication(Roles roles)
    {
	this.roles = roles;
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage()
    {
	return WebPage.class;
    }

    @Override
    public boolean hasAnyRole(Roles roles)
    {
	return roles.hasAnyRole(this.roles);
    }

    @Override
    public void init()
    {
	super.init();

	mountResource("/api", new ResourceReference("restReference")
	{

	    @Override
	    public IResource getResource()
	    {
		return new RestResourceFullAnnotated(
			new JsonTestWebSerialDeserial(), WicketApplication.this);
	    }

	});

	mountResource("/api2", new ResourceReference("regExpRestResource")
	{

	    @Override
	    public IResource getResource()
	    {
		return new RegExpRestResource(new JsonTestWebSerialDeserial(),
			WicketApplication.this);
	    }

	});

	mountResource("/api3", new ResourceReference("multiFormatRestResource")
	{

	    @Override
	    public IResource getResource()
	    {
		MultiFormatSerialDeserial multiFormat = new MultiFormatSerialDeserial();

		multiFormat.registerSerDeser(new JsonTestWebSerialDeserial(),
			RestMimeTypes.APPLICATION_JSON);
		multiFormat.registerSerDeser(new XmlTestWebSerialDeserial(),
			RestMimeTypes.APPLICATION_XML);

		return new MultiFormatRestResource(multiFormat);
	    }

	});
    }

    @Override
    public Session newSession(Request request, Response response)
    {
	Session session = super.newSession(request, response);
	session.setLocale(Locale.ENGLISH);

	return session;
    }
}
