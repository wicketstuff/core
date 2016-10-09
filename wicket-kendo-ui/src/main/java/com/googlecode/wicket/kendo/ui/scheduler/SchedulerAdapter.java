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

import com.googlecode.wicket.kendo.ui.scheduler.views.SchedulerViewType;

/**
 * Adapter class for {@link ISchedulerListener}
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class SchedulerAdapter implements ISchedulerListener
{
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isEditEnabled()
	{
		return false;
	}

	@Override
	public void onEdit(AjaxRequestTarget target, SchedulerEvent event, SchedulerViewType view)
	{
		// noop
	}

	@Override
	public void onNavigate(AjaxRequestTarget target, SchedulerViewType oldView,  SchedulerViewType newView)
	{
		// noop
	}

	@Override
	public void onCreate(AjaxRequestTarget target, SchedulerEvent event)
	{
		// noop
	}

	@Override
	public void onUpdate(AjaxRequestTarget target, SchedulerEvent event)
	{
		// noop
	}

	@Override
	public void onDelete(AjaxRequestTarget target, SchedulerEvent event)
	{
		// noop
	}
}
