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
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.string.StringValue;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.scheduler.resource.ResourceList;
import com.googlecode.wicket.kendo.ui.scheduler.resource.ResourceListModel;
import com.googlecode.wicket.kendo.ui.scheduler.views.SchedulerViewType;

/**
 * Provides the Kendo UI scheduler behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class SchedulerBehavior extends KendoUIBehavior implements IJQueryAjaxAware, ISchedulerListener
{
	private static final long serialVersionUID = 1L;
	private static final JavaScriptResourceReference JS = new JavaScriptResourceReference(SchedulerBehavior.class, "SchedulerBehavior.js");

	static final String METHOD = "kendoScheduler";

	private final SchedulerDataSource dataSource;

	private JQueryAjaxBehavior onEditAjaxBehavior = null;
	private JQueryAjaxBehavior onNavigateAjaxBehavior;

	private JQueryAjaxBehavior onCreateAjaxBehavior;
	private JQueryAjaxBehavior onUpdateAjaxBehavior;
	private JQueryAjaxBehavior onDeleteAjaxBehavior;

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

		this.dataSource = new SchedulerDataSource("schedulerDataSource");
		this.add(this.dataSource);
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		// events //
		if (this.isEditEnabled())
		{
			this.onEditAjaxBehavior = this.newOnEditAjaxBehavior(this);
			component.add(this.onEditAjaxBehavior);
		}

		this.onNavigateAjaxBehavior = this.newOnNavigateAjaxBehavior(this);
		component.add(this.onNavigateAjaxBehavior);

		this.onCreateAjaxBehavior = this.newOnCreateAjaxBehavior(this);
		component.add(this.onCreateAjaxBehavior);

		this.onUpdateAjaxBehavior = this.newOnUpdateAjaxBehavior(this);
		component.add(this.onUpdateAjaxBehavior);

		this.onDeleteAjaxBehavior = this.newOnDeleteAjaxBehavior(this);
		component.add(this.onDeleteAjaxBehavior);
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		response.render(JavaScriptHeaderItem.forReference(JS));
	}

	// Properties //

	/**
	 * Gets the data-source behavior's url
	 *
	 * @return the data-source behavior's url
	 */
	protected abstract CharSequence getDataSourceUrl();

	/**
	 * Gets the {@code ResourceListModel}
	 *
	 * @return the {@link ResourceListModel}
	 */
	protected abstract ResourceListModel getResourceListModel();

	/**
	 * Gets the 'read' callback function<br/>
	 * As create, update and destroy need to be supplied, we should declare read as a function. Weird...
	 *
	 * @return the 'read' callback function
	 */
	private String getReadCallbackFunction()
	{
		String widget = this.widget();
		String start = widget + ".view().startDate().getTime()";
		String end = String.format("calculateKendoSchedulerViewEndPeriod(%s.view().endDate()).getTime()", widget);

		return "function(options) {" // lf
				+ " jQuery.ajax({" // lf
				+ "		url: '" + this.getDataSourceUrl() + "'," // lf
				+ "		data: { start: " + start + ",  end: " + end + "}," // lf
				+ "		cache: false," // lf
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

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		// options //
		this.setOption("autoBind", true);

		// events //
		this.setOption("navigate", this.onNavigateAjaxBehavior.getCallbackFunction());

		if (this.onEditAjaxBehavior != null)
		{
			this.setOption("edit", this.onEditAjaxBehavior.getCallbackFunction());
		}

		// data-source //
		this.setOption("dataSource", this.dataSource.getName());

		this.dataSource.setTransportRead(this.getReadCallbackFunction());
		this.dataSource.setTransportCreate(this.onCreateAjaxBehavior.getCallbackFunction());
		this.dataSource.setTransportUpdate(this.onUpdateAjaxBehavior.getCallbackFunction());
		this.dataSource.setTransportDelete(this.onDeleteAjaxBehavior.getCallbackFunction());

		// schema //
		// this.setOption("schema", new Options("timezone", Options.asString("Etc/UTC")));

		// resource //
		this.setOption("resources", this.getResourceListModel());
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof NavigateEvent)
		{
			this.onNavigate(target, ((NavigateEvent) event).getView());
		}

		if (event instanceof SchedulerPayload)
		{
			SchedulerPayload payload = (SchedulerPayload) event;

			if (event instanceof EditEvent)
			{
				this.onEdit(target, payload.getEvent(), payload.getView());
			}

			if (event instanceof CreateEvent)
			{
				this.onCreate(target, payload.getEvent());
			}

			if (event instanceof UpdateEvent)
			{
				this.onUpdate(target, payload.getEvent());
			}

			if (event instanceof DeleteEvent)
			{
				this.onDelete(target, payload.getEvent());
			}
		}
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'navigate' event, triggered when the user is navigating in the scheduler
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link OnNavigateAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnNavigateAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnNavigateAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'edit' event, triggered when an event is edited
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link OnNavigateAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnEditAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnEditAjaxBehavior(source) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new EditEvent(SchedulerBehavior.this.getResourceListModel());
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the datasource's 'create' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link DataSourceAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnCreateAjaxBehavior(IJQueryAjaxAware source)
	{
		return new DataSourceAjaxBehavior(source) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new CreateEvent(SchedulerBehavior.this.getResourceListModel());
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the datasource's 'update' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link DataSourceAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnUpdateAjaxBehavior(IJQueryAjaxAware source)
	{
		return new DataSourceAjaxBehavior(source) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new UpdateEvent(SchedulerBehavior.this.getResourceListModel());
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the datasource's 'delete' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@link DataSourceAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnDeleteAjaxBehavior(IJQueryAjaxAware source)
	{
		return new DataSourceAjaxBehavior(source) {

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new DeleteEvent(SchedulerBehavior.this.getResourceListModel());
			}
		};
	}

	// Ajax classes //

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'navigate' event
	 */
	protected static class OnNavigateAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnNavigateAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e"), // lf
					CallbackParameter.resolved("view", "e.sender.view().name") };
		}

		@Override
		protected JQueryEvent newEvent()
		{
			return new NavigateEvent();
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} that aims to be wired to the 'edit' event
	 */
	protected abstract class OnEditAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnEditAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			List<CallbackParameter> parameters = new ArrayList<CallbackParameter>();

			parameters.add(CallbackParameter.context("e"));

			// event //
			parameters.add(CallbackParameter.resolved("id", "e.event.id")); // retrieved
			parameters.add(CallbackParameter.resolved("title", "e.event.title")); // retrieved
			parameters.add(CallbackParameter.resolved("description", "e.event.description")); // retrieved

			parameters.add(CallbackParameter.resolved("start", "e.event.start.getTime()")); // retrieved
			parameters.add(CallbackParameter.resolved("end", "e.event.end.getTime()")); // retrieved
			parameters.add(CallbackParameter.resolved("isAllDay", "e.event.isAllDay")); // retrieved

			// recurrence //
			parameters.add(CallbackParameter.resolved("recurrenceId", "e.event.recurrenceId")); // retrieved
			parameters.add(CallbackParameter.resolved("recurrenceRule", "e.event.recurrenceRule")); // retrieved
			parameters.add(CallbackParameter.resolved("recurrenceException", "e.event.recurrenceException")); // retrieved

			// resources //
			for (String field : SchedulerBehavior.this.getResourceListModel().getFields())
			{
				parameters.add(CallbackParameter.resolved(field, String.format("jQuery.makeArray(e.event.%s)", field))); // retrieved
			}

			parameters.add(CallbackParameter.resolved("view", "e.sender.view().name")); // retrieved

			// view //
			return parameters.toArray(new CallbackParameter[] {});
		}

		@Override
		public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
		{
			return super.getCallbackFunctionBody(parameters) + " e.preventDefault();"; // avoid propagation of KendoUIs edit-event on client-side
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} for handling datasource operations
	 */
	protected abstract class DataSourceAjaxBehavior extends JQueryAjaxBehavior
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

			parameters.add(CallbackParameter.context("e"));

			// event //
			parameters.add(CallbackParameter.resolved("id", "e.data.id")); // retrieved
			parameters.add(CallbackParameter.resolved("title", "e.data.title")); // retrieved
			parameters.add(CallbackParameter.resolved("description", "e.data.description")); // retrieved

			parameters.add(CallbackParameter.resolved("start", "e.data.start.getTime()")); // retrieved
			parameters.add(CallbackParameter.resolved("end", "e.data.end.getTime()")); // retrieved
			parameters.add(CallbackParameter.resolved("isAllDay", "e.data.isAllDay")); // retrieved

			// recurrence //
			parameters.add(CallbackParameter.resolved("recurrenceId", "e.data.recurrenceId")); // retrieved
			parameters.add(CallbackParameter.resolved("recurrenceRule", "e.data.recurrenceRule")); // retrieved
			parameters.add(CallbackParameter.resolved("recurrenceException", "e.data.recurrenceException")); // retrieved

			// resources //
			for (String field : SchedulerBehavior.this.getResourceListModel().getFields())
			{
				parameters.add(CallbackParameter.resolved(field, "e.data." + field)); // retrieved
			}

			return parameters.toArray(new CallbackParameter[] {});
		}

		@Override
		public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
		{
			return super.getCallbackFunctionBody(parameters) + " e.success();";
		}
	}

	// Event objects //

	/**
	 * Provides an event object that will be broadcasted by the {@link OnNavigateAjaxBehavior} callback
	 */
	protected static class NavigateEvent extends JQueryEvent
	{
		private SchedulerViewType view = null;

		public NavigateEvent()
		{
			String view = RequestCycleUtils.getQueryParameterValue("view").toString();

			if (view != null)
			{
				this.view = SchedulerViewType.get(view);
			}
		}

		public SchedulerViewType getView()
		{
			return this.view;
		}
	}

	/**
	 * Provides a base class for {@link SchedulerBehavior} event objects
	 */
	protected static class SchedulerPayload extends JQueryEvent
	{
		private final SchedulerEvent event;
		private SchedulerViewType view = null;

		public SchedulerPayload(final ResourceListModel listModel)
		{
			int id = RequestCycleUtils.getQueryParameterValue("id").toInt();
			String title = RequestCycleUtils.getQueryParameterValue("title").toString();
			String description = RequestCycleUtils.getQueryParameterValue("description").toString();

			long start = RequestCycleUtils.getQueryParameterValue("start").toLong();
			long end = RequestCycleUtils.getQueryParameterValue("end").toLong();
			boolean allDay = RequestCycleUtils.getQueryParameterValue("isAllDay").toBoolean();

			String recurrenceId = RequestCycleUtils.getQueryParameterValue("recurrenceId").toString();
			String recurrenceRule = RequestCycleUtils.getQueryParameterValue("recurrenceRule").toString();
			String recurrenceException = RequestCycleUtils.getQueryParameterValue("recurrenceException").toString();

			this.event = new SchedulerEvent(id, title, start, end);
			this.event.setAllDay(allDay);
			this.event.setDescription(description);
			this.event.setRecurrenceId(recurrenceId);
			this.event.setRecurrenceRule(recurrenceRule);
			this.event.setRecurrenceException(recurrenceException);

			// Resources //
			Pattern pattern = Pattern.compile("([\\w-]+)");

			for (ResourceList list : listModel.getObject())
			{
				String field = list.getField();
				StringValue value = RequestCycleUtils.getQueryParameterValue(field);

				List<String> values = new ArrayList<String>();

				if (value != null)
				{
					Matcher matcher = pattern.matcher(value.toString());

					while (matcher.find())
					{
						values.add(matcher.group());
					}
				}

				if (list.isMultiple())
				{
					this.event.setResource(field, values);
				}
				else if (!values.isEmpty())
				{
					this.event.setResource(field, values.get(0)); // if the underlying value is a number (even a string-number), it will handled by Id#valueOf(I)
				}
			}

			// View //
			String view = RequestCycleUtils.getQueryParameterValue("view").toString();

			if (view != null)
			{
				this.view = SchedulerViewType.get(view);
			}
		}

		public SchedulerEvent getEvent()
		{
			return this.event;
		}

		public SchedulerViewType getView()
		{
			return this.view;
		}
	}

	/**
	 * Provides an event object that will be broadcasted by the {@link OnEditAjaxBehavior} callback
	 */
	protected static class EditEvent extends SchedulerPayload
	{
		public EditEvent(ResourceListModel listModel)
		{
			super(listModel);
		}
	}

	/**
	 * Provides an event object that will be broadcasted by {@link SchedulerBehavior#newOnCreateAjaxBehavior(IJQueryAjaxAware)} callback
	 */
	protected static class CreateEvent extends SchedulerPayload
	{
		public CreateEvent(ResourceListModel listModel)
		{
			super(listModel);
		}
	}

	/**
	 * Provides an event object that will be broadcasted by {@link SchedulerBehavior#newOnUpdateAjaxBehavior(IJQueryAjaxAware)} callback
	 */
	protected static class UpdateEvent extends SchedulerPayload
	{
		public UpdateEvent(ResourceListModel listModel)
		{
			super(listModel);
		}
	}

	/**
	 * Provides an event object that will be broadcasted by {@link SchedulerBehavior#newOnDeleteAjaxBehavior(IJQueryAjaxAware)} callback
	 */
	protected static class DeleteEvent extends SchedulerPayload
	{
		public DeleteEvent(ResourceListModel listModel)
		{
			super(listModel);
		}
	}
}
