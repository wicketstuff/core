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
package com.googlecode.wicket.jquery.ui.calendar.settings;

import org.apache.wicket.request.resource.ResourceReference;

import com.googlecode.wicket.jquery.core.settings.JQueryLibrarySettings;
import com.googlecode.wicket.jquery.ui.calendar.resource.CalendarJavaScriptResourceReference;
import com.googlecode.wicket.jquery.ui.calendar.resource.CalendarStyleSheetResourceReference;
import com.googlecode.wicket.jquery.ui.calendar.resource.GCalJavaScriptResourceReference;

/**
 * Default implementation of {@link ICalendarLibrarySettings}.<br/>
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class CalendarLibrarySettings extends JQueryLibrarySettings implements ICalendarLibrarySettings
{
	private static CalendarLibrarySettings instance = null;

	private ResourceReference calendarStyleSheetReference = CalendarStyleSheetResourceReference.get();
	private ResourceReference calendarJavaScriptReference = CalendarJavaScriptResourceReference.get();
	private ResourceReference gcalJavaScriptReference = GCalJavaScriptResourceReference.get();

	/**
	 * INTERNAL USE<br/>
	 * Gets the {@link CalendarLibrarySettings} instance
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
	protected CalendarLibrarySettings()
	{
	}

	@Override
	public ResourceReference getCalendarStyleSheetReference()
	{
		return this.calendarStyleSheetReference;
	}

	@Override
	public ResourceReference getCalendarJavaScriptReference()
	{
		return this.calendarJavaScriptReference;
	}

	@Override
	public ResourceReference getGCalJavaScriptReference()
	{
		return this.gcalJavaScriptReference;
	}
}
