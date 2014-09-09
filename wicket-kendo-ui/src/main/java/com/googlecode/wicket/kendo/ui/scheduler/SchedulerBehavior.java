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

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;

/**
 * Provides the Kendo UI scheduler behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class SchedulerBehavior extends KendoUIBehavior implements IJQueryAjaxAware, ISchedulerListener
{
	private static final long serialVersionUID = 1L;

	static final String METHOD = "kendoScheduler";

	private JQueryAjaxBehavior onCreateBehavior = null;
	private JQueryAjaxBehavior onUpdateBehavior = null;
	private JQueryAjaxBehavior onDeleteBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 */
	public SchedulerBehavior(final String selector)
	{
		this(selector, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public SchedulerBehavior(final String selector, Options options)
	{
		super(selector, METHOD, options);
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		// events //
		component.add(this.onCreateBehavior = this.newOnCreateBehavior());
		component.add(this.onUpdateBehavior = this.newOnUpdateBehavior());
		component.add(this.onDeleteBehavior = this.newOnDeleteBehavior());
	}

	// Properties //

	protected abstract CharSequence getDataSourceUrl();

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		// data-sources //
		final SchedulerDataSource dataSource = this.newSchedulerDataSource("schedulerDataSource");
		this.add(dataSource);

		// options //
		this.setOption("autoBind", true);
		// this.setOption("autoSync", true); // client side, probably useless

		// events //
		// this.setOption("add", "function(e) { console.log('add'); console.log(e); }");
		// this.setOption("edit", "function(e) { console.log('edit'); console.log(e); }");
		// this.setOption("save", "function(e) { console.log('save'); console.log(e); }");
		// this.setOption("change", "function(e) { console.log('change'); console.log(e); }");
		// this.setOption("remove", "function(e) { console.log('remove'); console.log(e); }");

		// data source //
		this.setOption("dataSource", dataSource.getName());
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof CreateEvent)
		{
			this.onCreate(target, (SchedulerEvent) event);
		}

		if (event instanceof UpdateEvent)
		{
			this.onUpdate(target, (SchedulerEvent) event);
		}

		if (event instanceof DeleteEvent)
		{
			this.onDelete(target, (SchedulerEvent) event);
		}
	}

	// Factories //

	private SchedulerDataSource newSchedulerDataSource(String name)
	{
		SchedulerDataSource ds = new SchedulerDataSource(name);

		ds.setTransportRead(this.getReadCallbackFunction());
		ds.setTransportCreate(this.onCreateBehavior.getCallbackFunction());
		ds.setTransportUpdate(this.onUpdateBehavior.getCallbackFunction());
		ds.setTransportDelete(this.onDeleteBehavior.getCallbackFunction());

		return ds;
	}

	/**
	 * As create, update and destroy need to be supplied, we should declare read as a function. Weird...
	 *
	 * @return the 'read' callback function
	 */
	private String getReadCallbackFunction()
	{
		String widget = this.widget(METHOD);
		String start = widget + ".view().startDate().getTime()";
		String end = widget + ".view().endDate().getTime()";

		return "function(options) {" // lf
				+ "	jQuery.ajax({" // lf
				+ "		url: '" + this.getDataSourceUrl() + "'," // lf
				+ "		data: { start: " + start + ",  end: " + end + "}, " // lf
				+ "		dataType: 'json'," // lf
				+ "		success: function(result) {" // lf
				+ "			options.success(result);" // lf
				+ "		}," // lf
				+ "		error: function(result) {" // lf
				+ "			options.error(result);" // lf
				+ "		}" // lf
				+ "	});" // lf
				+ "}";
	}

	protected JQueryAjaxBehavior newOnCreateBehavior()
	{
		return new CallbackAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new CreateEvent();
			}
		};
	}

	protected JQueryAjaxBehavior newOnUpdateBehavior()
	{
		return new CallbackAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new UpdateEvent();
			}
		};
	}

	protected JQueryAjaxBehavior newOnDeleteBehavior()
	{
		return new CallbackAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new DeleteEvent();
			}
		};
	}

	// Event classes //

	abstract static class CallbackAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public CallbackAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("options"), // lf
					CallbackParameter.resolved("id", "options.data.id"), // retrieved
					CallbackParameter.resolved("start", "options.data.start.getTime()"), // retrieved
					CallbackParameter.resolved("end", "options.data.end.getTime()"), // retrieved
					CallbackParameter.resolved("title", "options.data.title") // retrieved
			};
		}

		@Override
		public CharSequence getCallbackFunctionBody(CallbackParameter... extraParameters)
		{
			return super.getCallbackFunctionBody(extraParameters) + " options.success();";
		}
	}

	protected static class CallbackSchedulerEvent extends SchedulerEvent
	{
		private static final long serialVersionUID = 1L;

		public CallbackSchedulerEvent()
		{
			int id = RequestCycleUtils.getQueryParameterValue("id").toInt();
			this.setId(id);

			String title = RequestCycleUtils.getQueryParameterValue("title").toString();
			this.setTitle(title);

			long start = RequestCycleUtils.getQueryParameterValue("start").toLong();
			this.setStart(start);

			long end = RequestCycleUtils.getQueryParameterValue("end").toLong();
			this.setEnd(end);
		}
	}

	protected static class CreateEvent extends CallbackSchedulerEvent
	{
		private static final long serialVersionUID = 1L;
	}

	protected static class UpdateEvent extends CallbackSchedulerEvent
	{
		private static final long serialVersionUID = 1L;
	}

	protected static class DeleteEvent extends CallbackSchedulerEvent
	{
		private static final long serialVersionUID = 1L;
	}
}
