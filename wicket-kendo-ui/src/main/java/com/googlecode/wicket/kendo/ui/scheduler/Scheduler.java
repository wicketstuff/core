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

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.wicket.ajax.AjaxRequestTarget;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryContainer;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.kendo.ui.scheduler.views.SchedulerViewType;

/**
 * Provides the Kendo UI scheduler
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class Scheduler extends JQueryContainer implements ISchedulerListener
{
	private static final long serialVersionUID = 1L;

	private final Options options;
	private SchedulerModelBehavior modelBehavior; // load events

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options {@link Options}.
	 */
	public Scheduler(String id, Options options)
	{
		super(id);

		this.options = options;
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link SchedulerModel}
	 */
	public Scheduler(String id, SchedulerModel model)
	{
		this(id, model, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link SchedulerModel}
	 * @param options {@link Options}.
	 */
	public Scheduler(String id, SchedulerModel model, Options options)
	{
		super(id, model);

		this.options = options;
	}

	// Methods //

	/**
	 * Gets the Kendo (jQuery) object
	 *
	 * @return the jQuery object
	 */
	protected String widget()
	{
		return String.format("jQuery('%s').data('%s')", JQueryWidget.getSelector(this), SchedulerBehavior.METHOD);
	}

	/**
	 * Refreshes the events currently available in the selected view.
	 *
	 * @param target the {@link AjaxRequestTarget}
	 */
	public void refresh(AjaxRequestTarget target)
	{
		target.appendJavaScript(String.format("%s.dataSource.read(); %s.refresh(); ", this.widget(), this.widget()));
	}

	// Properties //

	@Override
	public boolean isEditEnabled() {
		return false;
	}
	
	/**
	 * Gets the calendar's model
	 *
	 * @return a {@link SchedulerModel}
	 */
	public SchedulerModel getModel()
	{
		return (SchedulerModel) this.getDefaultModel();
	}

	/**
	 * Gets the data-source behavior's url
	 *
	 * @return the data-source behavior's url
	 */
	protected final CharSequence getDataSourceUrl()
	{
		return this.modelBehavior.getCallbackUrl();
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.add(this.modelBehavior = this.newSchedulerModelBehavior(this.getModel()));
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		super.onConfigure(behavior);

		behavior.setOption("timezone", Options.asString("Etc/UTC"));
	}

	@Override
	public void onCreate(AjaxRequestTarget target, SchedulerEvent event, SchedulerViewType view)
	{
		// noop
	}

	@Override
	public void onEdit(AjaxRequestTarget target, SchedulerEvent event, SchedulerViewType view)
	{
		// noop
	}

	@Override
	public void onUpdate(AjaxRequestTarget target, SchedulerEvent event, SchedulerViewType view)
	{
		// noop
	}

	@Override
	public void onDelete(AjaxRequestTarget target, SchedulerEvent event, SchedulerViewType view)
	{
		// noop
	}

	// IJQueryWidget //

	/**
	 * see {@link JQueryContainer#newWidgetBehavior(String)}
	 */
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new SchedulerBehavior(selector, this.options) {

			private static final long serialVersionUID = 1L;

			// Properties //

			@Override
			public boolean isEditEnabled()
			{
				return Scheduler.this.isEditEnabled();
			}
			
			@Override
			protected CharSequence getDataSourceUrl()
			{
				return Scheduler.this.getDataSourceUrl();
			}

			// Events //

			@Override
			public void onCreate(AjaxRequestTarget target, SchedulerEvent event, SchedulerViewType view)
			{
				Scheduler.this.onCreate(target, event, view);
			}

			@Override
			public void onEdit(AjaxRequestTarget target, SchedulerEvent event, SchedulerViewType view)
			{
				Scheduler.this.onEdit(target, event, view);
			}

			@Override
			public void onUpdate(AjaxRequestTarget target, SchedulerEvent event, SchedulerViewType view)
			{
				Scheduler.this.onUpdate(target, event, view);
			}

			@Override
			public void onDelete(AjaxRequestTarget target, SchedulerEvent event, SchedulerViewType view)
			{
				Scheduler.this.onDelete(target, event, view);
			}
		};
	}

	// Factory methods //

	/**
	 * Gets a new {@link SchedulerModelBehavior}
	 *
	 * @param model the {@link SchedulerModel}
	 * @return the {@link SchedulerModelBehavior}
	 */
	protected SchedulerModelBehavior newSchedulerModelBehavior(final SchedulerModel model)
	{
		return new SchedulerModelBehavior(model) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void setEndDate(SchedulerModel model, Date date)
			{
				Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));//TODO investigate; getting static from DateUtils ran into NoSuchFieldException UTC, dont know why / Patrick
				calendar.setTime(date);
				calendar.add(Calendar.HOUR_OF_DAY, 23);
				calendar.add(Calendar.MINUTE, 59);
				calendar.add(Calendar.SECOND, 59);
				calendar.add(Calendar.MILLISECOND, 999);

				model.setEnd(calendar.getTime());
			}
		};
	}
}
