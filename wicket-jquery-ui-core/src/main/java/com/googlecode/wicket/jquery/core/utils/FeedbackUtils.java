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
package com.googlecode.wicket.jquery.core.utils;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.protocol.http.WebSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.wicket.jquery.core.ajax.FeedbackPayload;

/**
 * Utility class for handling feedback session messages and feedback ajax messages. The hosting page should implement a code like:<br>
 * 
 * <pre>
 * <code>
 * public void onEvent(IEvent&lt;?&gt; event)
 * {
 *     super.onEvent(event);
 * 
 *     if (event.getPayload() instanceof FeedbackPayload)
 *     {
 *         FeedbackPayload payload = (FeedbackPayload) event.getPayload();
 * 
 *         if (payload.getLevel() == FeedbackMessage.INFO)
 *         {
 *             this.info(payload.getMessage());
 *             payload.getTarget().add(this.feedbackPanel);
 *         }
 *     }
 * }
 * </code>
 * </pre>
 * 
 * @author Sebastien Briquet - sebfz1
 *
 */
public class FeedbackUtils
{
	private static final Logger LOG = LoggerFactory.getLogger(FeedbackUtils.class);

	/**
	 * Utility class
	 */
	private FeedbackUtils()
	{
	}

	// Session based //

	/**
	 * Register an info at session level so the message is available even if the page is redirected
	 * 
	 * @param message the message
	 */
	public static void info(Serializable message)
	{
		WebSession.get().info(message);
	}

	/**
	 * Register a success at session level so the message is available even if the page is redirected
	 * 
	 * @param message the message
	 */
	public static void success(String message)
	{
		WebSession.get().success(message);
	}

	/**
	 * Register a warn at session level so the message is available even if the page is redirected
	 * 
	 * @param message the message
	 */
	public static void warn(String message)
	{
		WebSession.get().warn(message);
	}

	/**
	 * Register a warn at session level so the message is available even if the page is redirected
	 * 
	 * @param e the {@link Exception}
	 */
	public static void warn(Exception e)
	{
		String message = e.getMessage();

		LOG.warn(message);
		FeedbackUtils.warn(message);
	}

	/**
	 * Register an error at session level so the message is available even if the page is redirected
	 * 
	 * @param message the message
	 */
	public static void error(String message)
	{
		WebSession.get().error(message);
	}

	/**
	 * Register an error at session level so the message is available even if the page is redirected
	 * 
	 * @param e the {@link Exception}
	 */
	public static void error(Exception e)
	{
		String message = e.getMessage();

		LOG.error(message, e);
		FeedbackUtils.error(message);
	}

	// Ajax based //

	/**
	 * Sends an ajax {@link FeedbackMessage#DEBUG} message to the hosting page
	 * 
	 * @param component the {@link Component} sending the event
	 * @param target the {@link AjaxRequestTarget}
	 * @param message the message
	 */
	public static void debug(Component component, AjaxRequestTarget target, String message)
	{
		BroadcastUtils.breadth(component.getPage(), new FeedbackPayload(target, FeedbackMessage.DEBUG, message));
	}

	/**
	 * Sends an ajax {@link FeedbackMessage#INFO} message to the hosting page
	 * 
	 * @param component the {@link Component} sending the event
	 * @param target the {@link AjaxRequestTarget}
	 * @param message the message
	 */
	public static void info(Component component, AjaxRequestTarget target, String message)
	{
		BroadcastUtils.breadth(component.getPage(), new FeedbackPayload(target, FeedbackMessage.INFO, message));
	}

	/**
	 * Sends an ajax {@link FeedbackMessage#SUCCESS} message to the hosting page
	 * 
	 * @param component the {@link Component} sending the event
	 * @param target the {@link AjaxRequestTarget}
	 * @param message the message
	 */
	public static void success(Component component, AjaxRequestTarget target, String message)
	{
		BroadcastUtils.breadth(component.getPage(), new FeedbackPayload(target, FeedbackMessage.SUCCESS, message));
	}

	/**
	 * Sends an ajax {@link FeedbackMessage#WARNING} message to the hosting page
	 * 
	 * @param component the {@link Component} sending the event
	 * @param target the {@link AjaxRequestTarget}
	 * @param message the message
	 */
	public static void warn(Component component, AjaxRequestTarget target, String message)
	{
		BroadcastUtils.breadth(component.getPage(), new FeedbackPayload(target, FeedbackMessage.WARNING, message));
	}

	/**
	 * Sends an ajax {@link FeedbackMessage#ERROR} exception message to the hosting page
	 * 
	 * @param component the {@link Component} sending the event
	 * @param target the {@link AjaxRequestTarget}
	 * @param exception the {@link Exception}
	 */
	public static void error(Component component, AjaxRequestTarget target, Exception exception)
	{
		FeedbackUtils.error(component, target, exception.getMessage());
	}

	/**
	 * Sends an ajax {@link FeedbackMessage#ERROR} message to the hosting page
	 * 
	 * @param component the {@link Component} sending the event
	 * @param target the {@link AjaxRequestTarget}
	 * @param message the message
	 */
	public static void error(Component component, AjaxRequestTarget target, String message)
	{
		BroadcastUtils.breadth(component.getPage(), new FeedbackPayload(target, FeedbackMessage.ERROR, message));
	}

	/**
	 * Sends an ajax {@link FeedbackMessage#FATAL} exception message to the hosting page
	 * 
	 * @param component the {@link Component} sending the event
	 * @param target the {@link AjaxRequestTarget}
	 * @param exception the {@link Exception}
	 */
	public static void fatal(Component component, AjaxRequestTarget target, Exception exception)
	{
		FeedbackUtils.fatal(component, target, exception.getMessage());
	}

	/**
	 * Sends an ajax {@link FeedbackMessage#FATAL} message to the hosting page
	 * 
	 * @param component the {@link Component} sending the event
	 * @param target the {@link AjaxRequestTarget}
	 * @param message the message
	 */
	public static void fatal(Component component, AjaxRequestTarget target, String message)
	{
		BroadcastUtils.breadth(component.getPage(), new FeedbackPayload(target, FeedbackMessage.FATAL, message));
	}

	/**
	 * Aims the reload a {@link FeedbackPanel}. The hosting page should implement a code like:<br>
	 * 
	 * <pre>
	 * <code>
	 * public void onEvent(IEvent&lt;?&gt; event)
	 * {
	 *     super.onEvent(event);
	 * 
	 *     if (event.getPayload() instanceof FeedbackPayload)
	 *     {
	 *         FeedbackPayload payload = (FeedbackPayload) event.getPayload();
	 * 
	 *         if (payload.getLevel() == FeedbackMessage.UNDEFINED)
	 *         {
	 *             payload.getTarget().add(this.feedbackPanel);
	 *         }
	 *     }
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param component the {@link Component} sending the event
	 * @param target the {@link AjaxRequestTarget}
	 */
	public static void reload(Component component, AjaxRequestTarget target)
	{
		BroadcastUtils.breadth(component.getPage(), new FeedbackPayload(target));
	}
}
