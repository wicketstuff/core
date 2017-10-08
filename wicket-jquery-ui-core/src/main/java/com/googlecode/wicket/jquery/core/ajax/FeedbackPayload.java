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
package com.googlecode.wicket.jquery.core.ajax;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.feedback.FeedbackMessage;

/**
 * Payload object for feedback messages<br>
 * 
 * <pre>
 * <code>
 * &#64;Override
 * public void onEvent(IEvent&lt;?&gt; event)
 * {
 * 	super.onEvent(event);
 *
 * 	if (event.getPayload() instanceof FeedbackPayload)
 * 	{
 * 		FeedbackPayload payload = (FeedbackPayload) event.getPayload();
 * 		IPartialPageRequestHandler handler = payload.getHandler();
 * 		String message = payload.getMessage();
 *
 * 		if (payload.getLevel() == FeedbackMessage.DEBUG)
 * 		{
 * 			this.debug(message);
 * 		}
 *
 * 		handler.add(feedback);
 * 	}
 * }
 * </code>
 * </pre>
 * 
 * @author Sebastien Briquet - sebfz1
 * @see Component#send(org.apache.wicket.event.IEventSink, org.apache.wicket.event.Broadcast, Object)
 *
 */
public class FeedbackPayload extends HandlerPayload
{
	private final String message;
	private final int level;

	/**
	 * Constructor, designed to only refresh
	 * 
	 * @param handler the {@link AjaxRequestTarget}
	 */
	public FeedbackPayload(IPartialPageRequestHandler handler)
	{
		this(handler, FeedbackMessage.UNDEFINED, null);
	}

	/**
	 * Constructor
	 * 
	 * @param handler the {@link AjaxRequestTarget}
	 * @param level the level (ie: {@link FeedbackMessage#INFO}, etc)
	 * @param message the message
	 */
	public FeedbackPayload(IPartialPageRequestHandler handler, int level, String message)
	{
		super(handler);

		this.level = level;
		this.message = message;
	}

	/**
	 * Gets the feedback level (ie: {@link FeedbackMessage#INFO}, etc)
	 * 
	 * @return the feedback level
	 */
	public int getLevel()
	{
		return this.level;
	}

	/**
	 * Gets the feedback message
	 * 
	 * @return the feedback message
	 */
	public String getMessage()
	{
		return this.message;
	}
}
