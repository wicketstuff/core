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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.threeten.bp.LocalDateTime;

/**
 * Adapter class for {@link ICalendarListener}
 * 
 * @author Sebastien Briquet - sebfz1
 */
public class CalendarAdapter implements ICalendarListener
{
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isSelectable()
	{
		return false;
	}

	@Override
	public boolean isDayClickEnabled()
	{
		return false;
	}

	@Override
	public boolean isEventClickEnabled()
	{
		return false;
	}

	@Override
	public boolean isObjectDropEnabled()
	{
		return false;
	}

	@Override
	public boolean isEventDropEnabled()
	{
		return false;
	}

	@Override
	public boolean isEventResizeEnabled()
	{
		return false;
	}

	@Override
	public boolean isViewRenderEnabled()
	{
		return false;
	}

	@Override
	public CharSequence getEventDropPrecondition()
	{
		return "";
	}

	@Override
	public CharSequence getEventResizePrecondition()
	{
		return "";
	}

	@Override
	public void onSelect(AjaxRequestTarget target, CalendarView view, LocalDateTime start, LocalDateTime end, boolean allDay)
	{
		// noop
	}

	@Override
	public void onDayClick(AjaxRequestTarget target, CalendarView view, LocalDateTime date, boolean allDay)
	{
		// noop
	}

	@Override
	public void onEventClick(AjaxRequestTarget target, CalendarView view, int eventId)
	{
		// noop
	}

	@Override
	public void onEventDrop(AjaxRequestTarget target, int eventId, long delta, boolean allDay)
	{
		// noop
	}

	@Override
	public void onEventResize(AjaxRequestTarget target, int eventId, long delta)
	{
		// noop
	}

	@Override
	public void onObjectDrop(AjaxRequestTarget target, String title, LocalDateTime date, boolean allDay)
	{
		// noop
	}

	@Override
	public void onViewRender(AjaxRequestTarget target, CalendarView view, LocalDateTime start, LocalDateTime end)
	{
		// noop
	}
}
