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
import org.apache.wicket.core.util.string.JavaScriptUtils;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides a Kendo UI notification behavior
 *
 * @author Sebastien Briquet - sebfz1
 * @since 6.15.0
 */
public class NotificationBehavior extends KendoUIBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "kendoNotification";

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 */
	public NotificationBehavior(String selector)
	{
		super(selector, METHOD);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public NotificationBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
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
		target.appendJavaScript(this.$(message, level));
	}

	/**
	 * Hides all notifications
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void hide(AjaxRequestTarget target)
	{
		target.appendJavaScript(String.format("%s.hide();", this.widget()));
	}

	/**
	 * Formats the message (escaping, etc)
	 *
	 * @param message the message to format
	 * @param level the level, ie: info, success, warning, error
	 * @return the formated message
	 */
	protected CharSequence format(CharSequence message, String level)
	{
		return JavaScriptUtils.escapeQuotes(message);
	}

	/**
	 * Gets the jQuery statement that logs the message<br/>
	 * <b>Warning: </b> This method is *not* called by the behavior directly (only {@link #$()} is).
	 *
	 * @param message the message to log
	 * @param level the level of the message
	 * @return the jQuery statement
	 */
	protected final String $(Serializable message, String level)
	{
		return String.format("%s.show('%s', '%s');", this.widget(), this.format(String.valueOf(message), level), level);
	}
}
