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
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes.Method;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.util.lang.Args;
import com.github.openjson.JSONObject;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.resource.JavaScriptPackageHeaderItem;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.scheduler.views.SchedulerViewType;

/**
 * Provides the Kendo UI scheduler behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class SchedulerBehavior extends KendoUIBehavior implements IJQueryAjaxAware
{
	private static final long serialVersionUID = 1L;

	public static final String METHOD = "kendoScheduler";

	private final ISchedulerListener listener;
	private SchedulerDataSource dataSource;

	private JQueryAjaxBehavior onEditAjaxBehavior = null;
	private JQueryAjaxBehavior onNavigateAjaxBehavior;

	private JQueryAjaxBehavior onCreateAjaxBehavior;
	private JQueryAjaxBehavior onUpdateAjaxBehavior;
	private JQueryAjaxBehavior onDeleteAjaxBehavior;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param listener the {@link ISchedulerListener}
	 */
	public SchedulerBehavior(final String selector, ISchedulerListener listener)
	{
		this(selector, new Options(), listener);
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 * @param listener the {@link ISchedulerListener}
	 */
	public SchedulerBehavior(final String selector, Options options, ISchedulerListener listener)
	{
		super(selector, METHOD, options);

		this.listener = Args.notNull(listener, "listener");
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		// data-source //
		this.dataSource = new SchedulerDataSource(component);
		this.add(this.dataSource);

		// events //
		if (this.listener.isEditEnabled())
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

		response.render(new JavaScriptPackageHeaderItem(SchedulerBehavior.class));
	}

	// Events //

	@Override
	public void onConfigure(Component component)
	{
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
		this.dataSource.set("serverFiltering", true);
		this.dataSource.setTransportRead(this.getReadCallbackFunction());
		this.dataSource.setTransportCreate(this.onCreateAjaxBehavior.getCallbackFunction());
		this.dataSource.setTransportUpdate(this.onUpdateAjaxBehavior.getCallbackFunction());
		this.dataSource.setTransportDelete(this.onDeleteAjaxBehavior.getCallbackFunction());

		this.onConfigure(this.dataSource); // last chance to set options
		super.onConfigure(component);
	}

	/**
	 * Configure the {@link SchedulerDataSource} with additional options
	 * 
	 * @param dataSource the {@link SchedulerDataSource}
	 */
	protected void onConfigure(SchedulerDataSource dataSource)
	{
		// noop
	}

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof NavigateEvent)
		{
			NavigateEvent navigateEvent = (NavigateEvent) event;

			this.listener.onNavigate(target, navigateEvent.oldView, navigateEvent.newView);
		}

		if (event instanceof SchedulerPayload)
		{
			SchedulerPayload payload = (SchedulerPayload) event;
			JSONObject object = payload.getObject();

			if (event instanceof EditEvent)
			{
				this.listener.onEdit(target, object, payload.getView());
			}

			if (event instanceof CreateEvent)
			{
				this.listener.onCreate(target, object);
			}

			if (event instanceof UpdateEvent)
			{
				this.listener.onUpdate(target, object);
			}

			if (event instanceof DeleteEvent)
			{
				this.listener.onDelete(target, object);
			}
		}
	}

	// Properties //

	/**
	 * Gets the data-source behavior's url
	 *
	 * @return the data-source behavior's url
	 */
	protected abstract CharSequence getDataSourceUrl();

	/**
	 * Gets the 'read' callback function<br>
	 * As create, update and destroy need to be supplied, we should declare read as a function. Weird...
	 *
	 * @return the 'read' callback function
	 */
	private String getReadCallbackFunction()
	{
		String widget = this.widget();
		String start = String.format("%s.view().startDate().getTime()", widget);
		String until = String.format("calculateKendoSchedulerViewEndPeriod(%s.view().endDate()).getTime()", widget);

		return "function(options) { " // lf
				+ "jQuery.ajax({" // lf
				+ "		url: '" + this.getDataSourceUrl() + "'," // lf
				+ "		data: { start: " + start + ", end: " + until + " }," // lf
				+ "		cache: false," // lf
				+ "		dataType: 'json'," // lf
				+ "		success: function(result) { "// lf
				+ "			options.success(result);" // lf
				+ "		}," // lf
				+ "		error: function(result) {" // lf
				+ "			options.error(result);" // lf
				+ "		}" // lf
				+ "	});" // lf
				+ "}";
	}

	// Factories //

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'navigate' event, triggered when the user is navigating in the scheduler
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnNavigateAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnNavigateAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnNavigateAjaxBehavior(source);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the 'edit' event, triggered when an event is edited
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code OnNavigateAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnEditAjaxBehavior(IJQueryAjaxAware source)
	{
		return new OnEditAjaxBehavior(source) { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new EditEvent();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the datasource's 'create' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code DataSourceAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnCreateAjaxBehavior(IJQueryAjaxAware source)
	{
		return new DataSourceAjaxBehavior(source) { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new CreateEvent();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the datasource's 'update' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code DataSourceAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnUpdateAjaxBehavior(IJQueryAjaxAware source)
	{
		return new DataSourceAjaxBehavior(source) { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new UpdateEvent();
			}
		};
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be wired to the datasource's 'delete' event
	 *
	 * @param source the {@link IJQueryAjaxAware}
	 * @return a new {@code DataSourceAjaxBehavior} by default
	 */
	protected JQueryAjaxBehavior newOnDeleteAjaxBehavior(IJQueryAjaxAware source)
	{
		return new DataSourceAjaxBehavior(source) { // NOSONAR

			private static final long serialVersionUID = 1L;

			@Override
			protected JQueryEvent newEvent()
			{
				return new DeleteEvent();
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
					CallbackParameter.resolved("oldview", "e.sender.view().name"), // lf
					CallbackParameter.resolved("newview", "e.view") };
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
	protected abstract static class OnEditAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public OnEditAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
		{
			super.updateAjaxAttributes(attributes);

			attributes.setMethod(Method.POST); // #259
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e"), // lf
					CallbackParameter.resolved("data", "kendo.stringify(e.event)"), // lf
					CallbackParameter.resolved("view", "e.sender.view().name") };
		}

		@Override
		public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
		{
			String statement = "";

			// convert to timestamp before sending
			statement += "e.event.start = e.event.start.getTime();\n";
			statement += "e.event.end = e.event.end.getTime();\n";
			statement += super.getCallbackFunctionBody(parameters);
			statement += "e.preventDefault();"; // avoid propagation of KendoUI's edit-event on client-side

			return statement;
		}
	}

	/**
	 * Provides a {@link JQueryAjaxBehavior} for handling datasource operations
	 */
	protected abstract static class DataSourceAjaxBehavior extends JQueryAjaxBehavior
	{
		private static final long serialVersionUID = 1L;

		public DataSourceAjaxBehavior(IJQueryAjaxAware source)
		{
			super(source);
		}

		@Override
		protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
		{
			super.updateAjaxAttributes(attributes);

			attributes.setMethod(Method.POST); // #259
		}

		@Override
		protected CallbackParameter[] getCallbackParameters()
		{
			return new CallbackParameter[] { CallbackParameter.context("e"), // lf
					CallbackParameter.resolved("data", "kendo.stringify(e.data)") // lf
			};
		}

		@Override
		public CharSequence getCallbackFunctionBody(CallbackParameter... parameters)
		{
			String statement = "";

			// convert to timestamp before sending
			statement += "e.data.start = e.data.start.getTime();\n";
			statement += "e.data.end = e.data.end.getTime();\n";
			statement += super.getCallbackFunctionBody(parameters);
			statement += "e.success();";

			return statement;
		}
	}

	// Event objects //

	/**
	 * Provides an event object that will be broadcasted by the {@link OnNavigateAjaxBehavior} callback
	 */
	protected static class NavigateEvent extends JQueryEvent
	{
		private SchedulerViewType oldView = null;
		private SchedulerViewType newView = null;

		public NavigateEvent()
		{
			String oldView = RequestCycleUtils.getQueryParameterValue("oldview").toString();
			String newView = RequestCycleUtils.getQueryParameterValue("newview").toString();

			if (oldView != null)
			{
				this.oldView = SchedulerViewType.get(oldView);
			}

			if (newView != null)
			{
				this.newView = SchedulerViewType.get(newView);
			}
		}

		public SchedulerViewType getOldView()
		{
			return this.oldView;
		}

		public SchedulerViewType getNewView()
		{
			return this.newView;
		}
	}

	/**
	 * Provides a base class for {@link SchedulerBehavior} event objects
	 */
	protected static class SchedulerPayload extends JQueryEvent
	{
		private SchedulerViewType view = null;
		private JSONObject object;

		public SchedulerPayload()
		{
			String data = RequestCycleUtils.getRequestParameterValue("data").toString();

			if (data != null)
			{
				this.object = new JSONObject(data);
			}

			// View //
			String view = RequestCycleUtils.getRequestParameterValue("view").toString();

			if (view != null)
			{
				this.view = SchedulerViewType.get(view);
			}
		}

		public JSONObject getObject()
		{
			return this.object;
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
	}

	/**
	 * Provides an event object that will be broadcasted by {@link SchedulerBehavior#newOnCreateAjaxBehavior(IJQueryAjaxAware)} callback
	 */
	protected static class CreateEvent extends SchedulerPayload
	{
	}

	/**
	 * Provides an event object that will be broadcasted by {@link SchedulerBehavior#newOnUpdateAjaxBehavior(IJQueryAjaxAware)} callback
	 */
	protected static class UpdateEvent extends SchedulerPayload
	{
	}

	/**
	 * Provides an event object that will be broadcasted by {@link SchedulerBehavior#newOnDeleteAjaxBehavior(IJQueryAjaxAware)} callback
	 */
	protected static class DeleteEvent extends SchedulerPayload
	{
	}
}
