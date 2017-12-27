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

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.protocol.http.WebSession;

import com.googlecode.wicket.jquery.core.ajax.FeedbackPayload;

/**
 * Utility class for handling feedback session messages and feedback ajax messages.<br>
 * The hosting page should implement a code like:
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
 *             payload.getHandler().add(this.feedbackPanel);
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
	/**
	 * Utility class
	 */
	private FeedbackUtils()
	{
		// noop
	}

	/**
	 * Aims to reload a {@link FeedbackPanel} using {@link Broadcast#BREADTH} mode.<br>
	 * The hosting page should implement a code like:<br>
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
	 *             payload.getHandler().add(this.feedbackPanel);
	 *         }
	 *     }
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public static void reload(IPartialPageRequestHandler handler)
	{
		BroadcastUtils.breadth(handler, new FeedbackPayload(handler));
	}

	/**
	 * Register a debug at session level so the message is available even if the page is redirected
	 * 
	 * @param message the message
	 */
	public static void debug(String message)
	{
		IPartialPageRequestHandler handler = RequestCycleUtils.getRequestHandler();

		if (handler != null)
		{
			FeedbackUtils.debug(handler, message);
		}
		else
		{
			WebSession.get().debug(message);
		}
	}

	/**
	 * Sends an ajax {@link FeedbackMessage#DEBUG} message to the hosting page
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param message the message
	 */
	public static void debug(IPartialPageRequestHandler handler, String message)
	{
		BroadcastUtils.breadth(handler, new FeedbackPayload(handler, FeedbackMessage.DEBUG, message));
	}

	/**
	 * Register an info at session level so the message is available even if the page is redirected
	 * 
	 * @param message the message
	 */
	public static void info(String message)
	{
		IPartialPageRequestHandler handler = RequestCycleUtils.getRequestHandler();

		if (handler != null)
		{
			FeedbackUtils.info(handler, message);
		}
		else
		{
			WebSession.get().info(message);
		}
	}

	/**
	 * Sends an ajax {@link FeedbackMessage#INFO} message to the hosting page
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param message the message
	 */
	public static void info(IPartialPageRequestHandler handler, String message)
	{
		BroadcastUtils.breadth(handler, new FeedbackPayload(handler, FeedbackMessage.INFO, message));
	}

	/**
	 * Register a success at session level so the message is available even if the page is redirected
	 * 
	 * @param message the message
	 */
	public static void success(String message)
	{
		IPartialPageRequestHandler handler = RequestCycleUtils.getRequestHandler();

		if (handler != null)
		{
			FeedbackUtils.success(handler, message);
		}
		else
		{
			WebSession.get().success(message);
		}
	}

	/**
	 * Sends an ajax {@link FeedbackMessage#SUCCESS} message to the hosting page
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param message the message
	 */
	public static void success(IPartialPageRequestHandler handler, String message)
	{
		BroadcastUtils.breadth(handler, new FeedbackPayload(handler, FeedbackMessage.SUCCESS, message));
	}

	/**
	 * Register a warn at session level so the message is available even if the page is redirected
	 * 
	 * @param e the {@link Exception}
	 */
	public static void warn(Exception e)
	{
		FeedbackUtils.warn(e.getMessage());
	}

	/**
	 * Register a warn at session level so the message is available even if the page is redirected
	 * 
	 * @param message the message
	 */
	public static void warn(String message)
	{
		IPartialPageRequestHandler handler = RequestCycleUtils.getRequestHandler();

		if (handler != null)
		{
			FeedbackUtils.warn(handler, message);
		}
		else
		{
			WebSession.get().warn(message);
		}
	}

	/**
	 * Sends an ajax {@link FeedbackMessage#WARNING} message to the hosting page
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param message the message
	 */
	public static void warn(IPartialPageRequestHandler handler, String message)
	{
		BroadcastUtils.breadth(handler, new FeedbackPayload(handler, FeedbackMessage.WARNING, message));
	}

	/**
	 * Register an error at session level so the message is available even if the page is redirected
	 * 
	 * @param e the {@link Exception}
	 */
	public static void error(Exception e)
	{
		FeedbackUtils.error(e.getMessage());
	}

	/**
	 * Sends an ajax {@link FeedbackMessage#ERROR} exception message to the hosting page
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param exception the {@link Exception}
	 */
	public static void error(IPartialPageRequestHandler handler, Exception exception)
	{
		FeedbackUtils.error(handler, exception.getMessage());
	}

	/**
	 * Register an error at session level so the message is available even if the page is redirected
	 * 
	 * @param message the message
	 */
	public static void error(String message)
	{
		IPartialPageRequestHandler handler = RequestCycleUtils.getRequestHandler();

		if (handler != null)
		{
			FeedbackUtils.error(handler, message);
		}
		else
		{
			WebSession.get().error(message);
		}
	}

	/**
	 * Sends an ajax {@link FeedbackMessage#ERROR} message to the hosting page
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param message the message
	 */
	public static void error(IPartialPageRequestHandler handler, String message)
	{
		BroadcastUtils.breadth(handler, new FeedbackPayload(handler, FeedbackMessage.ERROR, message));
	}

	/**
	 * Register a fatal at session level so the message is available even if the page is redirected
	 * 
	 * @param e the {@link Exception}
	 */
	public static void fatal(Exception e)
	{
		FeedbackUtils.fatal(e.getMessage());
	}

	/**
	 * Sends an ajax {@link FeedbackMessage#FATAL} exception message to the hosting page
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param exception the {@link Exception}
	 */
	public static void fatal(IPartialPageRequestHandler handler, Exception exception)
	{
		FeedbackUtils.fatal(handler, exception.getMessage());
	}

	/**
	 * Register a fatal at session level so the message is available even if the page is redirected
	 * 
	 * @param message the message
	 */
	public static void fatal(String message)
	{
		IPartialPageRequestHandler handler = RequestCycleUtils.getRequestHandler();

		if (handler != null)
		{
			FeedbackUtils.fatal(handler, message);
		}
		else
		{
			WebSession.get().fatal(message);
		}
	}

	/**
	 * Sends an ajax {@link FeedbackMessage#FATAL} message to the hosting page
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param message the message
	 */
	public static void fatal(IPartialPageRequestHandler handler, String message)
	{
		BroadcastUtils.breadth(handler, new FeedbackPayload(handler, FeedbackMessage.FATAL, message));
	}
}
