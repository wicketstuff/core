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

import com.googlecode.wicket.kendo.ui.resource.KendoUIJavaScriptResourceReference;

/**
 * Provides library settings for Kendo UI resource references<br/>
 * <br/>
 * Usage:
 *
 * <pre>
 * <code>
 * public class MyApplication extends WebApplication
 * {
 * 	public void init()
 * 	{
 * 		super.init();
 *
 * 		this.getMarkupSettings().setStripWicketTags(true); // important
 *
 * 		KendoUILibrarySettings settings = KendoUILibrarySettings.get();
 * 		settings.setJavaScriptReference(new JavaScriptResourceReference(...)); // if you want to change the js version
 * 		settings.setCommonStyleSheetReference(new CssResourceReference(MyApplication.class, "kendo.common.min.css"));
 * 		settings.setThemeStyleSheetReference(new CssResourceReference(MyApplication.class, "kendo.custom.min.css"));
 * 	}
 * }
 * </code>
 * </pre>
 *
 * @author Sebastien Briquet - sebfz1
 */
public class KendoUILibrarySettings
{
	private static KendoUILibrarySettings instance = null;

	/**
	 * Gets the {@link KendoUILibrarySettings} instance
	 *
	 * @return the {@link KendoUILibrarySettings} instance
	 */
	public static synchronized KendoUILibrarySettings get()
	{
		if (KendoUILibrarySettings.instance == null)
		{
			KendoUILibrarySettings.instance = new KendoUILibrarySettings();
		}

		return KendoUILibrarySettings.instance;
	}

	private ResourceReference javascriptReference = KendoUIJavaScriptResourceReference.get();
	private ResourceReference stylesheetReferenceC = null;
	private ResourceReference stylesheetReferenceT = null;

	/**
	 * Constructor
	 */
	private KendoUILibrarySettings()
	{
	}

	/**
	 * Gets the Kendo UI javascript resource reference
	 *
	 * @return the {@link ResourceReference}
	 * @see #setJavaScriptReference(ResourceReference)
	 */
	public ResourceReference getJavaScriptReference()
	{
		return this.javascriptReference;
	}

	/**
	 * Sets the Kendo UI javascript resource reference
	 *
	 * @param reference the {@link ResourceReference}
	 */
	public void setJavaScriptReference(ResourceReference reference)
	{
		this.javascriptReference = reference;
	}

	/**
	 * Gets the Kendo UI common stylesheet resource reference
	 *
	 * @return <tt>null</tt> by default, meaning the style is supplied through the HTML page (&lt;link rel="stylesheet" type="text/css" href="..." /&gt;)
	 * @see #setCommonStyleSheetReference(ResourceReference)
	 */
	public ResourceReference getCommonStyleSheetReference()
	{
		return this.stylesheetReferenceC;
	}

	/**
	 * Sets the Kendo UI common stylesheet resource reference
	 *
	 * @param reference the {@link ResourceReference}
	 */
	public void setCommonStyleSheetReference(ResourceReference reference)
	{
		this.stylesheetReferenceC = reference;
	}

	/**
	 * Gets the Kendo UI theme stylesheet resource reference
	 *
	 * @return <tt>null</tt> by default, meaning the style is supplied through the HTML page (&lt;link rel="stylesheet" type="text/css" href="..." /&gt;)
	 * @see #setThemeStyleSheetReference(ResourceReference)
	 */
	public ResourceReference getThemeStyleSheetReference()
	{
		return this.stylesheetReferenceT;
	}

	/**
	 * Sets the Kendo UI theme stylesheet resource reference
	 *
	 * @param reference the {@link ResourceReference}
	 */
	public void setThemeStyleSheetReference(ResourceReference reference)
	{
		this.stylesheetReferenceT = reference;
	}
}
