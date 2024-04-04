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
package com.googlecode.wicket.jquery.ui.widget.dialog;

import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;

/**
 * Predefined icons to be used in a {@link MessageDialog}
 */
public enum DialogIcon
{
	NONE(""),
	INFO(JQueryFeedbackPanel.INFO_ICO, JQueryFeedbackPanel.INFO_CSS),
	WARN(JQueryFeedbackPanel.WARN_ICO, JQueryFeedbackPanel.WARN_CSS),
	ERROR(JQueryFeedbackPanel.ERROR_ICO, JQueryFeedbackPanel.ERROR_CSS),
	LIGHT(JQueryFeedbackPanel.LIGHT_ICO, JQueryFeedbackPanel.LIGHT_CSS);

	private final String icon;
	private final String style;

	/**
	 * Private constructor
	 * @param icon the icon jQuery UI class
	 */
	private DialogIcon(String icon)
	{
		this(icon, "");
	}

	/**
	 * Private constructor
	 * @param icon the icon jQuery UI class
	 * @param style the surrounding style
	 */
	private DialogIcon(String icon, String style)
	{
		this.icon = icon;
		this.style = style;
	}

	/**
	 * Gets the style variation (the jQuery style)
	 * @return the style
	 */
	public String getStyle()
	{
		return this.style;
	}

	@Override
	public String toString()
	{
		return this.icon;
	}
}
