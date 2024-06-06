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
package com.googlecode.wicket.jquery.ui.calendar6;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.util.io.IClusterable;

/**
 * Event listener shared by the {@link Calendar} widget and the {@link CalendarBehavior}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public interface ICalendarListener extends IClusterable
{
	/**
	 * Indicates whether a cell can be selected.<br>
	 * If true, the {@link #onSelect(AjaxRequestTarget, CalendarView, LocalDateTime, LocalDateTime, boolean)} event will be triggered
	 *
	 * @return false by default
	 */
	boolean isSelectable();

	/**
	 * Indicates whether a day can be clicked.<br>
	 * If true, the {@link #onDateClick(AjaxRequestTarget, CalendarView, LocalDateTime, boolean)} event will be triggered<br>
	 * <b>Note:</b> {@code true} will enable the global 'editable' option to {@code true}.
	 *
	 * @return false by default
	 * @see CalendarEvent#setEditable(Boolean)
	 */
	boolean isDateClickEnabled();

	/**
	 * Indicates whether an event can be clicked.<br>
	 * If true, the {@link #onEventClick(AjaxRequestTarget, CalendarView, String)} event will be triggered<br>
	 * <b>Note:</b> {@code true} will enable the global 'editable' option to {@code true}.
	 *
	 * @return false by default
	 * @see CalendarEvent#setEditable(Boolean)
	 */
	boolean isEventClickEnabled();

	/**
	 * Indicates whether the {@link #onObjectDrop(AjaxRequestTarget, String, LocalDateTime, boolean)} event will be triggered
	 *
	 * @return false by default
	 */
	boolean isObjectDropEnabled();

	/**
	 * Indicates whether the event can be dragged &#38; dropped. If true, the {@link #onEventDrop(AjaxRequestTarget, String, long, boolean)} event will be triggered
	 *
	 * @return false by default
	 */
	boolean isEventDropEnabled();

	/**
	 * Indicates whether the event can be resized. If true, the {@link #onEventResize(AjaxRequestTarget, String, long)} event will be triggered
	 *
	 * @return false by default
	 */
	boolean isEventResizeEnabled();

	/**
	 * Indicates whether the {@link #onViewDidMount(AjaxRequestTarget, CalendarView, LocalDate, LocalDate)} event will be triggered
	 *
	 * @return false by default
	 */
	boolean isViewDidMountEnabled();

	/**
	 * Gets the javascript statement which will be executed before {@link #onEventDrop(AjaxRequestTarget, String, long, boolean)} event is triggered<br>
	 * A common use case is to call the {@code revertFunc} callback to cancel the event.
	 *
	 * @return the javascript statement, empty string by default
	 * @see <a href="http://arshaw.com/fullcalendar/docs/event_ui/eventDrop/">http://arshaw.com/fullcalendar/docs/event_ui/eventDrop/</a>
	 */
	CharSequence getEventDropPrecondition();

	/**
	 * Gets the javascript statement which will be executed before {@link #onEventResize(AjaxRequestTarget, String, long)} event is triggered<br>
	 * A common use case is to call the {@code revertFunc} callback to cancel the event.
	 *
	 * @return the javascript statement, empty string by default
	 * @see <a href="http://arshaw.com/fullcalendar/docs/event_ui/eventResize/">http://arshaw.com/fullcalendar/docs/event_ui/eventResize/</a>
	 */
	CharSequence getEventResizePrecondition();

	/**
	 * Triggered when an cell is selected.<br>
	 * {@link #isSelectable()} should return true for this event to be triggered.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param view the current calendar view
	 * @param start the event start {@link LocalDateTime}
	 * @param end the event end {@link LocalDateTime}
	 * @param allDay the event all-day property
	 */
	void onSelect(AjaxRequestTarget target, CalendarView view, LocalDateTime start, LocalDateTime end, boolean allDay);

	/**
	 * Triggered when a calendar day is clicked<br>
	 * {@link #isDateClickEnabled()} should return true for this event to be triggered.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param view the current calendar view
	 * @param date the day
	 * @param allDay the event all-day property
	 */
	void onDateClick(AjaxRequestTarget target, CalendarView view, LocalDateTime date, boolean allDay);

	/**
	 * Triggered when an event is clicked.<br>
	 * {@link #isEventClickEnabled()} should return true for this event to be triggered.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param view the current calendar view
	 * @param eventId the {@link CalendarEvent} id
	 */
	void onEventClick(AjaxRequestTarget target, CalendarView view, String eventId);

	/**
	 * Triggered when an event is dropped (after being dragged).<br>
	 * {@link #isEventDropEnabled()} should return true for this event to be triggered.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param eventId the {@link CalendarEvent} id
	 * @param delta the delta (time) with the original event date
	 * @param allDay the event all-day property
	 */
	void onEventDrop(AjaxRequestTarget target, String eventId, long delta, boolean allDay);

	/**
	 * Triggered when an event is dropped (after being dragged).<br>
	 * {@link #isEventResizeEnabled()} should return true for this event to be triggered.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param eventId the {@link CalendarEvent} id
	 * @param delta the delta (time) with the original event date
	 */
	void onEventResize(AjaxRequestTarget target, String eventId, long delta);

	/**
	 * Triggered when an event-object is dropped.<br>
	 * {@link #isObjectDropEnabled()} should return true for this event to be triggered.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param title the title
	 * @param date the day
	 * @param allDay the event all-day property
	 */
	void onObjectDrop(AjaxRequestTarget target, String title, LocalDateTime date, boolean allDay);

	/**
	 * Triggered when the calendar loads and every time a different date-range is displayed.<br>
	 * {@link #isViewDidMountEnabled()} should return true for this event to be triggered.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param view the current calendar view
	 * @param start the start {@link LocalDate} of the current view
	 * @param end the event end {@link LocalDate} of the current view
	 */
	void onViewDidMount(AjaxRequestTarget target, CalendarView view, LocalDate start, LocalDate end);
}
