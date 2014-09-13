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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.util.string.StringValue;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.scheduler.resource.ResourceList;
import com.googlecode.wicket.kendo.ui.scheduler.resource.ResourceListModel;

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

	/**
	 * Gets the data-source behavior's url
	 *
	 * @return the data-source behavior's url
	 */
	protected abstract CharSequence getDataSourceUrl();

	/**
	 * Gets the <tt>ResourceListModel</tt>
	 *
	 * @return the {@link ResourceListModel}
	 */
	protected abstract ResourceListModel getResourceListModel();

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

		// resource //
		this.setOption("resources", this.getResourceListModel());
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

	/**
	 * Gets a new <tt>SchedulerDataSource</tt>
	 *
	 * @param name the name of the data-source
	 * @return a new {@link SchedulerDataSource}
	 */
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
	 * Gets the 'read' callback function<br/>
	 * As create, update and destroy need to be supplied, we should declare read as a function. Weird...
	 *
	 * @return the 'read' callback function
	 */
	private String getReadCallbackFunction()
	{
		String widget = this.widget(METHOD);
		String start = widget + "._selectedView._startDate.getTime()";
		String end = widget + "._selectedView._endDate.getTime()";

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

	/**
	 * Gets the data-source's ajax behavior that will be triggered when an event is created
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnCreateBehavior()
	{
		return new DataSourceAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new CreateEvent(SchedulerBehavior.this.getResourceListModel());
			}
		};
	}

	/**
	 * Gets the data-source's ajax behavior that will be triggered when an event is updated
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnUpdateBehavior()
	{
		return new DataSourceAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new UpdateEvent(SchedulerBehavior.this.getResourceListModel());
			}
		};
	}

	/**
	 * Gets the data-source's ajax behavior that will be triggered when an event is deleted
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnDeleteBehavior()
	{
		return new DataSourceAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new DeleteEvent(SchedulerBehavior.this.getResourceListModel());
			}
		};
	}

	// Event classes //

	/**
	 * Base class for data-source's ajax behavior
	 */
	private abstract class DataSourceAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public DataSourceAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			List<CallbackParameter> parameters = new ArrayList<CallbackParameter>();

			parameters.add(CallbackParameter.context("options"));
			parameters.add(CallbackParameter.resolved("id", "options.data.id")); // retrieved
			parameters.add(CallbackParameter.resolved("start", "options.data.start.getTime()")); // retrieved
			parameters.add(CallbackParameter.resolved("end", "options.data.end.getTime()")); // retrieved
			parameters.add(CallbackParameter.resolved("title", "options.data.title")); // retrieved
			parameters.add(CallbackParameter.resolved("description", "options.data.description")); // retrieved

			for (String field : SchedulerBehavior.this.getResourceListModel().getFields())
			{
				parameters.add(CallbackParameter.resolved(field, "options.data." + field)); // retrieved
			}

			return parameters.toArray(new CallbackParameter[] {});
		}

		@Override
		public CharSequence getCallbackFunctionBody(CallbackParameter... extraParameters)
		{
			return super.getCallbackFunctionBody(extraParameters) + " options.success();";
		}
	}

	/**
	 * Base class for scheduler event object (payload)
	 */
	protected static class CallbackSchedulerEvent extends SchedulerEvent
	{
		private static final long serialVersionUID = 1L;

		// TODO: wrap SchedulerEvent & SchedulerViewType

		public CallbackSchedulerEvent(final ResourceListModel listModel)
		{
			int id = RequestCycleUtils.getQueryParameterValue("id").toInt();
			this.setId(id);

			String title = RequestCycleUtils.getQueryParameterValue("title").toString();
			this.setTitle(title);

			long start = RequestCycleUtils.getQueryParameterValue("start").toLong();
			this.setStart(start);

			long end = RequestCycleUtils.getQueryParameterValue("end").toLong();
			this.setEnd(end);

			String description = RequestCycleUtils.getQueryParameterValue("description").toString();
			this.setDescription(description);

			// Resources //
			Pattern pattern = Pattern.compile("(\\d+)");

			for (ResourceList list : listModel.getObject())
			{
				String field = list.getField();
				StringValue value = RequestCycleUtils.getQueryParameterValue(field);

				List<Integer> values = new ArrayList<Integer>();

				if (value != null)
				{
					Matcher matcher = pattern.matcher(value.toString());

					while (matcher.find())
					{
						values.add(Integer.valueOf(matcher.group()));
					}

					if (list.isMutiple())
					{
						this.setResource(field, values);
					}
					else if (values.size() > 0) /* defensive, should never happens */
					{
						this.setResource(field, values.get(0));
					}
				}
			}
		}
	}


	/**
	 * An event object that will be broadcasted when a scheduler event is created
	 */
	protected static class CreateEvent extends CallbackSchedulerEvent
	{
		private static final long serialVersionUID = 1L;

		public CreateEvent(ResourceListModel listModel)
		{
			super(listModel);
		}
	}

	/**
	 * An event object that will be broadcasted when a scheduler event is updated
	 */
	protected static class UpdateEvent extends CallbackSchedulerEvent
	{
		private static final long serialVersionUID = 1L;

		public UpdateEvent(ResourceListModel listModel)
		{
			super(listModel);
		}
	}

	/**
	 * An event object that will be broadcasted when a scheduler event is deleted
	 */
	protected static class DeleteEvent extends CallbackSchedulerEvent
	{
		private static final long serialVersionUID = 1L;

		public DeleteEvent(ResourceListModel listModel)
		{
			super(listModel);
		}
	}
}
