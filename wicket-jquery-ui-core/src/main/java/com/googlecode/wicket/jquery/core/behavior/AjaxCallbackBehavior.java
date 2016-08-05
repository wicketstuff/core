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
package com.googlecode.wicket.jquery.core.behavior;

import org.apache.wicket.Application;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.settings.RequestCycleSettings;

/**
 * Provides an ajax endpoint that sends a "text" response<br/>
 * The default content type is "application/json" <br/>
 * The default encoding is the current {@code Application}'s {@link RequestCycleSettings} 
 * 
 * @author Sebastien Briquet - sebfz1
 * @see TextRequestHandler
 */
public abstract class AjaxCallbackBehavior extends AbstractAjaxBehavior
{
	private static final long serialVersionUID = 1L;

	private final String mimetype;
	private final String encoding;

	/**
	 * Constructor with default "application/json" content type
	 */
	public AjaxCallbackBehavior()
	{
		this("application/json");
	}

	// Properties //

	/**
	 * Constructor
	 * 
	 * @param mimetype the content type
	 */
	public AjaxCallbackBehavior(String mimetype)
	{
		this(mimetype, Application.get().getRequestCycleSettings().getResponseRequestEncoding());
	}

	/**
	 * Constructor
	 * 
	 * @param mimetype the content type
	 * @param encoding the encoding
	 */
	public AjaxCallbackBehavior(String mimetype, String encoding)
	{
		this.mimetype = mimetype;
		this.encoding = encoding;
	}

	@Override
	public boolean rendersPage()
	{
		return false;
	}

	// Methods //

	/**
	 * Gets the response text
	 * 
	 * @param parameters the {@link IRequestParameters}
	 * @return the response text
	 */
	protected abstract String getResponse(IRequestParameters parameters);

	// Events //

	/**
	 * {@inheritDoc}<br/>
	 * <br/>
	 * <b>Warning:</b> Overriden methods should call {@code super.onRequest()}
	 */
	@Override
	public void onRequest()
	{
		RequestCycle requestCycle = RequestCycle.get();
		requestCycle.scheduleRequestHandlerAfterCurrent(new TextRequestHandler(this.mimetype, this.encoding, this.getResponse(requestCycle.getRequest().getQueryParameters())));
	}
}
