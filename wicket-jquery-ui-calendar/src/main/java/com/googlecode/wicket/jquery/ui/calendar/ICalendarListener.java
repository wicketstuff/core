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

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * TODO javadoc
 * @author Sebastien Briquet - sebfz1
 *
 */
interface ICalendarListener
{
	/**
	 * Indicates whether the event can be edited (ie, clicked).<br/>
	 * IIF true, an event can override this global setting to false by using CalendarEvent#setEditable(boolean);<br/>
	 * If true, the {@link #onEventClick(AjaxRequestTarget, int)} event and {@link #onDayClick(AjaxRequestTarget, Date)} event will be triggered<br/>
	 *
	 * @return false by default
	 */
	boolean isEditable();

	/**
	 * Indicated whether a cell can be selected.<br />
	 * If true, the {@link #onSelect(AjaxRequestTarget, Date, Date, boolean)} event will be triggered
	 *
	 * @return false by default
	 */
	boolean isSelectable();

	/**
	 * Indicates whether the event can be dragged &#38; dropped.
	 * If true, the {@link #onEventDrop(AjaxRequestTarget, int, long, boolean)} event will be triggered
	 *
	 * @return false by default
	 */
	boolean isEventDropEnabled();

	/**
	 * Indicates whether the event can be resized.
	 * If true, the {@link #onEventResize(AjaxRequestTarget, int, long)} event will be triggered
	 *
	 * @return false by default
	 */
	boolean isEventResizeEnabled();

	/**
	 * Triggered when an cell is selected.<br/>
	 * {@link #isSelectable()} should return true for this event to be triggered.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param start the event start {@link Date}
	 * @param end the event end {@link Date}
	 * @param allDay the event all-day property
	 */
	void onSelect(AjaxRequestTarget target, Date start, Date end, boolean allDay);

	/**
	 * Triggered when a calendar day is clicked
	 * @param target the {@link AjaxRequestTarget}
	 * @param date the day
	 */
	void onDayClick(AjaxRequestTarget target, Date date);

	/**
	 * Triggered when an event is clicked.<br/>
	 * {@link #isEditable()} should return true for this event to be triggered.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param eventId the {@link CalendarEvent} id
	 */
	void onEventClick(AjaxRequestTarget target, int eventId);

	/**
	 * Triggered when an event is dropped (after being dragged).<br/>
	 * {@link #isEventDropEnabled()} should return true for this event to be triggered.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param eventId the {@link CalendarEvent} id
	 * @param delta the delta (time) with the original event date
	 * @param allDay the event all-day property
	 */
	void onEventDrop(AjaxRequestTarget target, int eventId, long delta, boolean allDay);

	/**
	 * Triggered when an event is dropped (after being dragged).<br/>
	 * {@link #isEventResizeEnabled()} should return true for this event to be triggered.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param eventId the {@link CalendarEvent} id
	 * @param delta the delta (time) with the original event date
	 */
	void onEventResize(AjaxRequestTarget target, int eventId, long delta);
}
