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
package com.googlecode.wicket.jquery.ui.calendar;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.googlecode.wicket.jquery.ui.JQueryBehavior;

/**
 * Provides the jQuery fullCalendar behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
class CalendarBehavior extends JQueryBehavior
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "fullCalendar";

	public CalendarBehavior(final String selector)
	{
		super(selector, METHOD);

		this.add(new CssResourceReference(CalendarBehavior.class, "fullcalendar.css"));
		this.add(new JavaScriptResourceReference(CalendarBehavior.class, "fullcalendar.min.js"));
		this.add(new JavaScriptResourceReference(CalendarBehavior.class, "gcal.js"));
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		IRequestHandler handler = new ResourceReferenceRequestHandler(AbstractDefaultAjaxBehavior.INDICATOR);

		/* adds and configure the busy indicator */
		response.renderJavaScript("jQuery(function(){ "
				+ "jQuery('<img />')"
				+ ".attr('src', '" + RequestCycle.get().urlFor(handler).toString() + "')"
				+ ".ajaxStart(function() { jQuery(this).show(); })"
				+ ".ajaxStop(function() { jQuery(this).hide(); })"
				+ ".appendTo('.fc-header-center');"
				+ " });", this.getClass().getName());
	}
}
