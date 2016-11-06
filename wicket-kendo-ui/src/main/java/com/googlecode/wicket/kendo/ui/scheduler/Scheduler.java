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

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.util.lang.Args;

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

	private SchedulerEventFactory factory;
	private SchedulerModelBehavior modelBehavior; // load events

	private final ResourceListModel resourceListModel;

	// templates //
	private IJQueryTemplate editTemplate;
	private IJQueryTemplate eventTemplate;
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
	 * Gets the {@link SchedulerEventFactory}
	 * 
	 * @return the {@code SchedulerEventFactory}
	 */
	protected final SchedulerEventFactory getSchedulerEventFactory()
	{
		if (this.factory == null)
		{
			this.factory = this.newSchedulerEventFactory();
		}

		return this.factory;
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

	// Events //

	@Override
	protected void onInitialize()
	{
		super.onInitialize();

		this.modelBehavior = this.newSchedulerModelBehavior(this.getModel(), this.getSchedulerEventFactory());
		this.add(this.modelBehavior);

		// templates //

		this.editTemplate = this.newEditTemplate();

		if (this.editTemplate != null)
		{
			this.editTemplateBehavior = new KendoTemplateBehavior(this.editTemplate);
			this.add(this.editTemplateBehavior);
		}

		this.eventTemplate = this.newEventTemplate();

		if (this.eventTemplate != null)
		{
			this.eventTemplateBehavior = new KendoTemplateBehavior(this.eventTemplate);
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
			Options options = new Options();
			options.set("resources", Options.asString(groups));
			options.set("orientation", Options.asString(this.getGroupOrientation()));
			behavior.setOption("group", options);
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
	public void onEdit(AjaxRequestTarget target, SchedulerEvent event, SchedulerViewType view)
	{
		// noop
	}

	@Override
	public void onNavigate(AjaxRequestTarget target, SchedulerViewType oldView, SchedulerViewType newView)
	{
		this.refresh(target);
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

	// IJQueryWidget //

	/**
	 * see {@link JQueryContainer#newWidgetBehavior(String)}
	 */
	@Override
	public JQueryBehavior newWidgetBehavior(String selector)
	{
		return new SchedulerBehavior(selector, this.options, this.getSchedulerEventFactory(), this) {

			private static final long serialVersionUID = 1L;

			// Properties //

			@Override
			protected CharSequence getDataSourceUrl()
			{
				return Scheduler.this.getCallbackUrl();
			}

			@Override
			protected ResourceListModel getResourceListModel()
			{
				return Scheduler.this.resourceListModel;
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
	 * Gets a new {@link SchedulerEventFactory}
	 * 
	 * @return a new {@code SchedulerEventFactory}
	 */
	protected SchedulerEventFactory newSchedulerEventFactory()
	{
		return new SchedulerEventFactory();
	}

	/**
	 * Gets a new {@link SchedulerModelBehavior}
	 *
	 * @param model the {@code SchedulerModel}
	 * @param factory the {@code SchedulerEventFactory}
	 * @return the {@code SchedulerModelBehavior}
	 */
	protected SchedulerModelBehavior newSchedulerModelBehavior(final SchedulerModel model, final SchedulerEventFactory factory)
	{
		return new SchedulerModelBehavior(model, factory);
	}
}
