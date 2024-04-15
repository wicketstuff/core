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

import java.time.ZoneOffset;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.util.lang.Args;
import com.github.openjson.JSONObject;

import com.googlecode.wicket.jquery.core.JQueryBehavior;
import com.googlecode.wicket.jquery.core.JQueryContainer;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.scheduler.resource.ResourceList;
import com.googlecode.wicket.kendo.ui.scheduler.resource.ResourceListModel;
import com.googlecode.wicket.kendo.ui.scheduler.views.SchedulerViewType;
import com.googlecode.wicket.kendo.ui.template.KendoTemplateBehavior;

/**
 * Provides the Kendo UI Scheduler
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class Scheduler extends JQueryContainer implements ISchedulerListener
{
	private static final long serialVersionUID = 1L;

	/** Provides an enum of available group orientation */
	protected enum GroupOrientation
	{
		horizontal, vertical
	}

	private ISchedulerConverter converter;
	private SchedulerModelBehavior modelBehavior; // load events

	private final ResourceListModel resourceListModel;

	// templates //
	private KendoTemplateBehavior editTemplateBehavior = null;
	private KendoTemplateBehavior eventTemplateBehavior = null;

	protected final Options options;

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 */
	public Scheduler(String id)
	{
		this(id, null, new ResourceListModel(), new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param options the {@link Options}
	 */
	public Scheduler(String id, Options options)
	{
		this(id, null, new ResourceListModel(), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link SchedulerModel}
	 */
	public Scheduler(String id, SchedulerModel model)
	{
		this(id, model, new ResourceListModel(), new Options());
	}

	/**
	 * Main constructor
	 *
	 * @param id the markup id
	 * @param model the {@link SchedulerModel}
	 * @param options the {@link Options}
	 */
	public Scheduler(String id, SchedulerModel model, Options options)
	{
		this(id, model, new ResourceListModel(), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link SchedulerModel}
	 * @param resourceList a {@link ResourceList}
	 */
	public Scheduler(String id, SchedulerModel model, ResourceList resourceList)
	{
		this(id, model, new ResourceListModel(resourceList), new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link SchedulerModel}
	 * @param resourceList a {@link ResourceList}
	 * @param options the {@link Options}
	 */
	public Scheduler(String id, SchedulerModel model, ResourceList resourceList, Options options)
	{
		this(id, model, new ResourceListModel(resourceList), options);
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link SchedulerModel}
	 * @param resourceListModel the {@link ResourceListModel}
	 */
	public Scheduler(String id, SchedulerModel model, ResourceListModel resourceListModel)
	{
		this(id, model, resourceListModel, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param id the markup id
	 * @param model the {@link SchedulerModel}
	 * @param resourceListModel the {@link ResourceListModel}
	 * @param options the {@link Options}
	 */
	public Scheduler(String id, SchedulerModel model, ResourceListModel resourceListModel, Options options)
	{
		super(id, model);

		this.resourceListModel = resourceListModel;
		this.options = Args.notNull(options, "options");
	}

	// Methods //

	/**
	 * Adds a {@link ResourceList} to the internal {@link ResourceListModel}
	 * 
	 * @param resourceList the {@code ResourceList}
	 * @see #getResourceListModel()
	 */
	public void add(ResourceList resourceList)
	{
		this.resourceListModel.add(resourceList);
	}

	/**
	 * Gets the Kendo UI widget
	 *
	 * @return the jQuery object
	 */
	public String widget()
	{
		return KendoUIBehavior.widget(this, SchedulerBehavior.METHOD);
	}

	/**
	 * Refreshes the events currently available in the selected view.
	 *
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	public void refresh(IPartialPageRequestHandler handler)
	{
		handler.appendJavaScript(String.format("var $w = %s; if ($w) { $w.dataSource.read(); $w.refresh(); }", this.widget()));

		this.onRefresh(handler);
	}

	/**
	 * Utility method that converts a {@link JSONObject} event to a {@link SchedulerEvent}
	 * 
	 * @param object the {@link JSONObject}
	 * @return a new {@link SchedulerEvent}
	 */
	protected SchedulerEvent eventOf(JSONObject object)
	{
		return this.getConverter().toObject(object, this.getResourceListModel().getObject());
	}

	// Properties //

	@Override
	public boolean isEditEnabled()
	{
		return false;
	}

	/**
	 * Gets the sheduler's model
	 *
	 * @return the {@link SchedulerModel}
	 */
	public SchedulerModel getModel()
	{
		return (SchedulerModel) this.getDefaultModel();
	}

	/**
	 * Gets the {@link SchedulerModelBehavior} callback url
	 * 
	 * @return the {@code SchedulerModelBehavior} callback url
	 */
	protected CharSequence getCallbackUrl()
	{
		return this.modelBehavior.getCallbackUrl();
	}

	/**
	 * Gets the edit-template script token/id
	 * 
	 * @return the template script token/id
	 */
	public String getEditTemplateToken()
	{
		if (this.editTemplateBehavior != null)
		{
			return this.editTemplateBehavior.getToken();
		}

		return null;
	}

	/**
	 * Gets the event-template script token/id
	 * 
	 * @return the template script token/id
	 */
	public String getEventTemplateToken()
	{
		if (this.eventTemplateBehavior != null)
		{
			return this.eventTemplateBehavior.getToken();
		}

		return null;
	}

	/**
	 * Gets the {@link ZoneOffset} to be used by the {@link ISchedulerConverter}
	 * 
	 * @return {@link ZoneOffset#UTC} by default
	 */
	protected ZoneOffset getZoneOffset()
	{
		return ZoneOffset.UTC;
	}

	/**
	 * Gets the {@link ISchedulerConverter}
	 * 
	 * @return the {@link ISchedulerConverter}
	 */
	protected final ISchedulerConverter getConverter()
	{
		if (this.converter == null)
		{
			this.converter = this.newConverter(this.getZoneOffset());
		}

		return this.converter;
	}

	/**
	 * Gets the sheduler's {@link ResourceListModel}
	 *
	 * @return the {@link ResourceListModel}
	 */
	public ResourceListModel getResourceListModel()
	{
		return this.resourceListModel;
	}

	/**
	 * Gets the orientation of the group headers
	 *
	 * @return {@link GroupOrientation#horizontal} by default
	 */
	protected GroupOrientation getGroupOrientation()
	{
		return GroupOrientation.horizontal;
	}

	/**
	 * Indicates whether the resources are grouped by date.
	 *
	 * @return {@code false} by default
	 */
	protected boolean isGroupedByDate()
	{
		return false;
	}

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.modelBehavior = this.newSchedulerModelBehavior(this.getModel(), this.getConverter());
		this.add(this.modelBehavior);

		// templates //

		final IJQueryTemplate editTemplate = this.newEditTemplate();

		if (editTemplate != null)
		{
			this.editTemplateBehavior = new KendoTemplateBehavior(editTemplate, "edit-template");
			this.add(this.editTemplateBehavior);
		}

		final IJQueryTemplate eventTemplate = this.newEventTemplate();

		if (eventTemplate != null)
		{
			this.eventTemplateBehavior = new KendoTemplateBehavior(eventTemplate, "event-template");
			this.add(this.eventTemplateBehavior);
		}
	}

	@Override
	public void onConfigure(JQueryBehavior behavior)
	{
		super.onConfigure(behavior);

		behavior.setOption("timezone", Options.asString("Etc/UTC"));

		// groups //
		List<String> groups = this.resourceListModel.getGroups();

		if (!groups.isEmpty())
		{
			Options groupOptions = new Options();
			groupOptions.set("date", this.isGroupedByDate());
			groupOptions.set("resources", Options.asString(groups));
			groupOptions.set("orientation", Options.asString(this.getGroupOrientation()));

			behavior.setOption("group", groupOptions);
		}

		// set templates (if any) //
		if (this.editTemplateBehavior != null)
		{
			behavior.setOption("editable", String.format("{ template: jQuery('#%s').html() }", this.getEditTemplateToken()));
		}

		if (this.eventTemplateBehavior != null)
		{
			behavior.setOption("eventTemplate", String.format("jQuery('#%s').html()", this.getEventTemplateToken()));
		}

		// resource //
		behavior.setOption("resources", this.getResourceListModel());
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

	/**
	 * Triggered when {@link #refresh(IPartialPageRequestHandler)} has been called
	 * 
	 * @param handler the {@link IPartialPageRequestHandler}
	 */
	protected void onRefresh(IPartialPageRequestHandler handler)
	{
		// noop
	}

	@Override
	public final void onEdit(AjaxRequestTarget target, JSONObject object, SchedulerViewType view)
	{
		this.onEdit(target, this.eventOf(object), view);
	}

	/**
	 * Triggered when a {@link SchedulerEvent} is edited.
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param event the {@link SchedulerEvent}
	 * @param view the {@link SchedulerViewType}
	 */
	public void onEdit(AjaxRequestTarget target, SchedulerEvent event, SchedulerViewType view)
	{
		// noop
	}

	@Override
	public void onNavigate(AjaxRequestTarget target, SchedulerViewType oldView, SchedulerViewType newView)
	{
		// noop
	}

	/**
	 * {@inheritDoc}<br>
	 * <b>Warning:</b> to be overridden with care!
	 */
	@Override
	public void onCreate(AjaxRequestTarget target, JSONObject object)
	{
		this.onCreate(target, this.eventOf(object));
	}

	/**
	 * Triggered when a {@link SchedulerEvent} is created.
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param event the {@link SchedulerEvent}
	 */
	public void onCreate(AjaxRequestTarget target, SchedulerEvent event)
	{
		// noop
	}

	/**
	 * {@inheritDoc}<br>
	 * <b>Warning:</b> to be overridden with care!
	 */
	@Override
	public void onUpdate(AjaxRequestTarget target, JSONObject object)
	{
		this.onUpdate(target, this.eventOf(object));
	}

	/**
	 * Triggered when a {@link SchedulerEvent} is updated
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param event the {@link SchedulerEvent}
	 */
	public void onUpdate(AjaxRequestTarget target, SchedulerEvent event)
	{
		// noop
	}

	/**
	 * {@inheritDoc}<br>
	 * <b>Warning:</b> to be overridden with care!
	 */
	@Override
	public void onDelete(AjaxRequestTarget target, JSONObject object)
	{
		this.onDelete(target, this.eventOf(object));
	}

	/**
	 * Triggered when a {@link SchedulerEvent} is deleted
	 * 
	 * @param target the {@link AjaxRequestTarget}
	 * @param event the {@link SchedulerEvent}
	 */
	public void onDelete(AjaxRequestTarget target, SchedulerEvent event)
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
		return new SchedulerBehavior(selector, this.options, this) { // NOSONAR

			private static final long serialVersionUID = 1L;

			// Properties //

			@Override
			protected CharSequence getDataSourceUrl()
			{
				return Scheduler.this.getCallbackUrl();
			}

			// Events //

			@Override
			protected void onConfigure(SchedulerDataSource dataSource)
			{
				Scheduler.this.onConfigure(dataSource);
			}
		};
	}

	// Factory methods //

	/**
	 * Gets a new {@link ISchedulerConverter}
	 * 
	 * @param offset the {@link ZoneOffset}
	 * 
	 * @return a new {@code SchedulerConverter} by default
	 */
	protected ISchedulerConverter newConverter(ZoneOffset offset)
	{
		return new SchedulerConverter(offset);
	}

	/**
	 * Gets a new {@link IJQueryTemplate} to customize the built-in edit window
	 * 
	 * @return null by default
	 * @see <a href="http://docs.telerik.com/kendo-ui/controls/scheduling/scheduler/how-to/custom-edit-and-event-templates">http://docs.telerik.com/kendo-ui/controls/scheduling/scheduler/how-to/custom-edit-and-event-templates</a>
	 */
	protected IJQueryTemplate newEditTemplate()
	{
		return null;
	}

	/**
	 * Gets a new {@link IJQueryTemplate} to customize the event rendering
	 * 
	 * @return null by default
	 * @see <a href="http://docs.telerik.com/kendo-ui/controls/scheduling/scheduler/how-to/custom-edit-and-event-templates">http://docs.telerik.com/kendo-ui/controls/scheduling/scheduler/how-to/custom-edit-and-event-templates</a>
	 */
	// TODO: add ISchedulerTemplate? (#getTextProperties seems to be useless, to be double checked)
	protected IJQueryTemplate newEventTemplate()
	{
		return null;
	}

	/**
	 * Gets a new {@link SchedulerModelBehavior}
	 *
	 * @param model the {@code SchedulerModel}
	 * @param converter the {@code SchedulerEventFactory}
	 * @return the {@code SchedulerModelBehavior}
	 */
	protected SchedulerModelBehavior newSchedulerModelBehavior(final SchedulerModel model, final ISchedulerConverter converter)
	{
		return new SchedulerModelBehavior(model, converter);
	}
}
