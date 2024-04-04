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
package com.googlecode.wicket.kendo.ui.settings;

import org.apache.wicket.request.resource.ResourceReference;

import com.googlecode.wicket.kendo.ui.resource.ConsoleJavaScriptResourceReference;
import com.googlecode.wicket.kendo.ui.resource.ConsoleStyleSheetResourceReference;

/**
 * Provides library settings for Kendo UI Console resource references<br>
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
 *         ConsoleLibrarySettings settings = ConsoleLibrarySettings.get();
 *         settings.setJavaScriptReference(new JavaScriptResourceReference(...));
 *         settings.setStyleSheetReference(new CssResourceReference(...));
 *     }
 * }
 * </code>
 * </pre>
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class ConsoleLibrarySettings
{
	private static ConsoleLibrarySettings instance = null;

	private ResourceReference javascriptReference = ConsoleJavaScriptResourceReference.get();
	private ResourceReference stylesheetReference = ConsoleStyleSheetResourceReference.get();

	/**
	 * Gets the {@link ConsoleLibrarySettings} instance
	 *
	 * @return the {@link ConsoleLibrarySettings} instance
	 */
	public static synchronized ConsoleLibrarySettings get()
	{
		if (ConsoleLibrarySettings.instance == null)
		{
			ConsoleLibrarySettings.instance = new ConsoleLibrarySettings();
		}

		return ConsoleLibrarySettings.instance;
	}

	/**
	 * Constructor
	 */
	private ConsoleLibrarySettings()
	{
	}

	/**
	 * Gets the Kendo UI Console javascript resource reference
	 *
	 * @return the {@link ResourceReference}
	 */
	public ResourceReference getJavaScriptReference()
	{
		return this.javascriptReference;
	}

	/**
	 * Sets the Kendo UI Console javascript resource reference
	 *
	 * @param reference the {@link ResourceReference}
	 */
	public void setJavascriptReference(ResourceReference reference)
	{
		this.javascriptReference = reference;
	}

	/**
	 * Gets the Kendo UI Console stylesheet resource reference
	 *
	 * @return the {@link ResourceReference}
	 */
	public ResourceReference getStyleSheetReference()
	{
		return this.stylesheetReference;
	}

	/**
	 * Sets the Kendo UI Console stylesheet resource reference
	 *
	 * @param reference the {@link ResourceReference}
	 */
	public void setStylesheetReference(ResourceReference reference)
	{
		this.stylesheetReference = reference;
	}
}
