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
package com.googlecode.wicket.jquery.ui.calendar.resource;

import java.util.List;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.apache.wicket.util.lang.Generics;

import com.googlecode.wicket.jquery.core.resource.JQueryMomentResourceReference;

/**
 * Provides the resource reference for the fullcalendar javascript library.
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class CalendarJavaScriptResourceReference extends JQueryPluginResourceReference
{
	private static final long serialVersionUID = 1L;

	private static final CalendarJavaScriptResourceReference INSTANCE = new CalendarJavaScriptResourceReference();

	/**
	 * Gets the instance of the resource reference
	 *
	 * @return the single instance of the resource reference
	 */
	public static CalendarJavaScriptResourceReference get()
	{
		return INSTANCE;
	}

	/**
	 * Private constructor
	 */
	private CalendarJavaScriptResourceReference()
	{
		super(CalendarJavaScriptResourceReference.class, "fullcalendar.js");
	}

	@Override
	public List<HeaderItem> getDependencies()
	{
		List<HeaderItem> dependencies = Generics.newArrayList();

		for (HeaderItem item : super.getDependencies())
		{
			dependencies.add(item);
		}

		dependencies.add(JavaScriptHeaderItem.forReference(JQueryMomentResourceReference.get()));

		return dependencies;
	}
}
