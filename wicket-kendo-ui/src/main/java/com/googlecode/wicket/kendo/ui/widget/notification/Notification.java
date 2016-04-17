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
package com.googlecode.wicket.kendo.ui.widget.notification;

import java.io.Serializable;

import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.util.lang.Args;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.Options;

/**
 * Provides a Kendo UI notification widget
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.15.0
 */
public class Notification extends WebMarkupContainer implements IJQueryWidget
{
	private static final long serialVersionUID = 1L;

	public static final String INFO = "info";
	public static final String SUCCESS = "success";
	public static final String WARNING = "warning";
	public static final String ERROR = "error";

	private final Options options;
	private NotificationBehavior widgetBehavior = null;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public Notification(String id)
	{
		this(id, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public Notification(String id, Options options)
	{
		super(id);

		this.options = Args.notNull(options, "options");
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.widgetBehavior = JQueryWidget.newWidgetBehavior(this);
		this.add(this.widgetBehavior);
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		// noop
	}

	@Override
	public void onBeforeRender(JQueryBehavior behavior)
	{
		// noop
	}

	// Methods //

	/**
	 * Shows the message
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param message the message to format
	 * @param level the level, ie: info, success, warning, error
	 */
	public void show(IPartialPageRequestHandler handler, Serializable message, String level)
	{
		this.widgetBehavior.show(handler, message, level);
	}

	/**
	 * Shows an info message
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param message the message to format
	 */
	public void info(IPartialPageRequestHandler handler, Serializable message)
	{
		this.show(handler, message, INFO);
	}

	/**
	 * Shows a success message
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param message the message to format
	 */
	public void success(IPartialPageRequestHandler handler, Serializable message)
	{
		this.show(handler, message, SUCCESS);
	}

	/**
	 * Shows a warn message
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param message the message to format
	 */
	public void warn(IPartialPageRequestHandler handler, Serializable message)
	{
		this.show(handler, message, WARNING);
	}

	/**
	 * Shows an error message
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 * @param message the message to format
	 */
	public void error(IPartialPageRequestHandler handler, Serializable message)
	{
		this.show(handler, message, ERROR);
	}

	/**
	 * Hides all notifications
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void hide(IPartialPageRequestHandler handler)
	{
		this.widgetBehavior.hide(handler);
	}

	// IJQueryWidget //

	@Override
	public NotificationBehavior newWidgetBehavior(String selector)
	{
		return new NotificationBehavior(selector, this.options);
	}
}
