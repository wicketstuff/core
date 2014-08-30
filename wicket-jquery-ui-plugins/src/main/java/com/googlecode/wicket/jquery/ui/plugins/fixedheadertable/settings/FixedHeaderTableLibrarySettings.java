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
package com.googlecode.wicket.jquery.ui.plugins.fixedheadertable.settings;

import org.apache.wicket.request.resource.ResourceReference;

import com.googlecode.wicket.jquery.ui.plugins.fixedheadertable.resource.FixedHeaderTableJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.plugins.fixedheadertable.resource.FixedHeaderTableStyleSheetResourceReference;

/**
 * Provides library settings for the jQuery FixedHeaderTable plugin resource references<br/>
 * <br/>
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
 *         FixedHeaderTableLibrarySettings settings = FixedHeaderTableLibrarySettings.get();
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
public class FixedHeaderTableLibrarySettings
{
	private static FixedHeaderTableLibrarySettings instance = null;

	private ResourceReference javascriptReference = FixedHeaderTableJavaScriptResourceReference.get();
	private ResourceReference stylesheetReference = FixedHeaderTableStyleSheetResourceReference.get();

	/**
	 * Gets the {@link FixedHeaderTableLibrarySettings} instance
	 *
	 * @return the {@link FixedHeaderTableLibrarySettings} instance
	 */
	public static synchronized FixedHeaderTableLibrarySettings get()
	{
		if (FixedHeaderTableLibrarySettings.instance == null)
		{
			FixedHeaderTableLibrarySettings.instance = new FixedHeaderTableLibrarySettings();
		}

		return FixedHeaderTableLibrarySettings.instance;
	}

	/**
	 * Constructor
	 */
	private FixedHeaderTableLibrarySettings()
	{
	}

	/**
	 * Gets the plugin javascript resource reference
	 *
	 * @return the {@link ResourceReference}
	 */
	public ResourceReference getJavaScriptReference()
	{
		return this.javascriptReference;
	}

	/**
	 * Sets the plugin javascript resource reference
	 *
	 * @param reference the {@link ResourceReference}
	 */
	public void setJavascriptReference(ResourceReference reference)
	{
		this.javascriptReference = reference;
	}

	/**
	 * Gets the plugin stylesheet resource reference
	 *
	 * @return the {@link ResourceReference}
	 */
	public ResourceReference getStyleSheetReference()
	{
		return this.stylesheetReference;
	}

	/**
	 * Sets the plugin stylesheet resource reference
	 *
	 * @param reference the {@link ResourceReference}
	 */
	public void setStylesheetReference(ResourceReference reference)
	{
		this.stylesheetReference = reference;
	}
}
