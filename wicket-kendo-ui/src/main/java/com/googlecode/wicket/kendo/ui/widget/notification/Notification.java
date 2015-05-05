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

import org.apache.wicket.ajax.AjaxRequestTarget;
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

		this.add(this.widgetBehavior = JQueryWidget.newWidgetBehavior(this));
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
	 * @param target the {@link AjaxRequestTarget}
	 * @param message the message to format
	 * @param level the level, ie: info, success, warning, error
	 */
	public void show(AjaxRequestTarget target, Serializable message, String level)
	{
		this.widgetBehavior.show(target, message, level);
	}

	/**
	 * Shows an info message
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param message the message to format
	 */
	public void info(AjaxRequestTarget target, Serializable message)
	{
		this.show(target, message, INFO);
	}

	/**
	 * Shows a success message
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param message the message to format
	 */
	public void success(AjaxRequestTarget target, Serializable message)
	{
		this.show(target, message, SUCCESS);
	}

	/**
	 * Shows a warn message
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param message the message to format
	 */
	public void warn(AjaxRequestTarget target, Serializable message)
	{
		this.show(target, message, WARNING);
	}

	/**
	 * Shows an error message
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param message the message to format
	 */
	public void error(AjaxRequestTarget target, Serializable message)
	{
		this.show(target, message, ERROR);
	}

	/**
	 * Hides all notifications
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void hide(AjaxRequestTarget target)
	{
		this.widgetBehavior.hide(target);
	}

	// IJQueryWidget //

	@Override
	public NotificationBehavior newWidgetBehavior(String selector)
	{
		return new NotificationBehavior(selector, this.options);
	}
}
