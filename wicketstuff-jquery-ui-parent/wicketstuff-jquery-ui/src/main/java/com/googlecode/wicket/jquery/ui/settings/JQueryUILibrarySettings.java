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
package com.googlecode.wicket.jquery.ui.settings;

import org.apache.wicket.request.resource.ResourceReference;

import com.googlecode.wicket.jquery.ui.resource.JQueryUIResourceReference;

/**
 * Provides library settings for jQuery UI resource references<br>
 * <br>
 * Usage:
 *
 * <pre>
 * <code>
 * public class MyApplication extends WebApplication
 * {
 *     public void init()
 *     {
 *         super.init();
 *
 *         JQueryUILibrarySettings settings = JQueryUILibrarySettings.get();
 *         settings.setJavaScriptReference(new JavaScriptResourceReference(...)); // if you want to change the js version
 *         settings.setStyleSheetReference(new CssResourceReference(MyApplication.class, "jquery-ui.custom.min.css"));
 *     }
 * }
 * </code>
 * </pre>
 *
 * @author Sebastien Briquet - sebfz1
 */
public class JQueryUILibrarySettings
{
	private static JQueryUILibrarySettings instance = null;

	private ResourceReference javascriptReference = JQueryUIResourceReference.get();
	private ResourceReference stylesheetReference = null;

	/**
	 * Gets the {@link JQueryUILibrarySettings} instance
	 *
	 * @return the {@link JQueryUILibrarySettings} instance
	 */
	public static synchronized JQueryUILibrarySettings get()
	{
		if (JQueryUILibrarySettings.instance == null)
		{
			JQueryUILibrarySettings.instance = new JQueryUILibrarySettings();
		}

		return JQueryUILibrarySettings.instance;
	}

	/**
	 * Constructor
	 */
	private JQueryUILibrarySettings()
	{
	}

	/**
	 * Gets the JQuery UI javascript resource reference
	 *
	 * @return the {@link ResourceReference}
	 */
	public ResourceReference getJavaScriptReference()
	{
		return this.javascriptReference;
	}

	/**
	 * Sets the jQuery UI javascript resource reference
	 *
	 * @param reference the {@link ResourceReference}
	 */
	public void setJavaScriptReference(ResourceReference reference)
	{
		this.javascriptReference = reference;
	}

	/**
	 * Gets the JQuery UI stylesheet resource reference
	 *
	 * @return {@code null} by default, meaning the style is supplied through the HTML page (&lt;link rel="stylesheet" type="text/css" href="..." /&gt;)
	 * @see #setStyleSheetReference(ResourceReference)
	 */
	public ResourceReference getStyleSheetReference()
	{
		return this.stylesheetReference;
	}

	/**
	 * Sets the jQuery UI stylesheet resource reference
	 *
	 * @param reference the {@link ResourceReference}
	 */
	public void setStyleSheetReference(ResourceReference reference)
	{
		this.stylesheetReference = reference;
	}
}
