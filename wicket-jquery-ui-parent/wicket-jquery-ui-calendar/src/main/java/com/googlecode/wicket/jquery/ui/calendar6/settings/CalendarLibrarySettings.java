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
package com.googlecode.wicket.jquery.ui.calendar6.settings;

import org.apache.wicket.request.resource.ResourceReference;

import com.googlecode.wicket.jquery.ui.calendar6.resource.CalendarJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.calendar6.resource.CalendarLocalesJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.calendar6.resource.GCalJavaScriptResourceReference;

/**
 * Provides library settings for FullCalendar resource references<br>
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
 *         CalendarLibrarySettings settings = CalendarLibrarySettings.get();
 *         settings.setJavaScriptReference(new JavaScriptResourceReference(...));
 *         settings.setStyleSheetReference(new CssResourceReference(...));
 *         settings.setGCalJavaScriptReference(new JavaScriptResourceReference(...));
 *     }
 * }
 * </code>
 * </pre>
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class CalendarLibrarySettings
{
	private static CalendarLibrarySettings instance = null;

	private ResourceReference javascriptReference = CalendarJavaScriptResourceReference.get();

	private ResourceReference gcalReference = GCalJavaScriptResourceReference.get();
	private ResourceReference localesReference = CalendarLocalesJavaScriptResourceReference.get();

	/**
	 * Gets the {@link CalendarLibrarySettings} instance
	 *
	 * @return the {@link CalendarLibrarySettings} instance
	 */
	public static synchronized CalendarLibrarySettings get()
	{
		if (CalendarLibrarySettings.instance == null)
		{
			CalendarLibrarySettings.instance = new CalendarLibrarySettings();
		}

		return CalendarLibrarySettings.instance;
	}

	/**
	 * Constructor
	 */
	private CalendarLibrarySettings()
	{
	}

	/**
	 * Gets the fullcalendar's javascript resource reference
	 *
	 * @return the {@link ResourceReference}
	 */
	public ResourceReference getJavaScriptReference()
	{
		return this.javascriptReference;
	}

	/**
	 * Sets the fullcalendar's javascript resource reference
	 *
	 * @param reference the {@link ResourceReference}
	 */
	public void setJavascriptReference(ResourceReference reference)
	{
		this.javascriptReference = reference;
	}

	/**
	 * Gets the gcal's javascript resource reference
	 *
	 * @return the {@link ResourceReference}
	 */
	public ResourceReference getGCalJavaScriptReference()
	{
		return this.gcalReference;
	}

	/**
	 * Sets the gcal's javascript resource reference
	 *
	 * @param reference the {@link ResourceReference}
	 */
	public void setGCalJavaScriptReference(ResourceReference reference)
	{
		this.gcalReference = reference;
	}

	/**
	 * Gets the locale-all's javascript resource reference
	 *
	 * @return the {@link ResourceReference}
	 */
	public ResourceReference getLocalesJavaScriptReference()
	{
		return this.localesReference;
	}

	/**
	 * Sets the locale-all's javascript resource reference
	 *
	 * @param reference the {@link ResourceReference}
	 */
	public void setLocalesJavaScriptReference(ResourceReference reference)
	{
		this.localesReference = reference;
	}
}
