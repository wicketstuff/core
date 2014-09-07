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
package com.googlecode.wicket.kendo.ui.scheduler;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Event listener shared by the {@link Scheduler} widget and the {@link SchedulerBehavior}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public interface ISchedulerListener
{
	/**
	 * Triggered when a {@link SchedulerEvent} is created (or modified) through the Scheduler's dialog
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param event the {@link SchedulerEvent}
	 */
	void onCreate(AjaxRequestTarget target, SchedulerEvent event);

	/**
	 * Triggered when a {@link SchedulerEvent} is updated through the Scheduler's (ie: drag &#38; drop)
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param event the {@link SchedulerEvent}
	 */
	void onUpdate(AjaxRequestTarget target, SchedulerEvent event);

	/**
	 * Triggered when a {@link SchedulerEvent} is deleted, either through the Scheduler's dialog or the x-icon
	 *
	 * @param target the {@link AjaxRequestTarget}
	 * @param event the {@link SchedulerEvent}
	 */
	void onDelete(AjaxRequestTarget target, SchedulerEvent event);
}
